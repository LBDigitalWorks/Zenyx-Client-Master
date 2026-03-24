package com.client.definitions.anim;

import com.client.collection.node.DualNode;
import com.client.collection.node.NodeDeque;
import com.client.js5.Js5List;

public class Frames extends DualNode {

    public Animation[] frames;
    private int loadedAnimationCount;

    private static byte[] getSkeletonData(int skeletonId, boolean altSkeletonIndexing) {
        byte[] data;
        if (altSkeletonIndexing) {
            data = Js5List.skeletons.getFile(0, skeletonId);
            if (data == null) {
                data = Js5List.skeletons.getFile(skeletonId, 0);
            }
        } else {
            data = Js5List.skeletons.getFile(skeletonId, 0);
            if (data == null) {
                data = Js5List.skeletons.getFile(0, skeletonId);
            }
        }
        return data;
    }

    Frames(int groupId, boolean altSkeletonIndexing) {
        NodeDeque skeletonCache = new NodeDeque();

        // File IDs in a group can be sparse (e.g., 0, 1, 5, 114...)
        int[] fileIds = Js5List.animations.getGroupFileIds(groupId);
        if (fileIds == null || fileIds.length == 0) {
            this.frames = new Animation[0];
            return;
        }

        // Size array by max fileId + 1, NOT by file count
        int maxFileId = -1;
        for (int id : fileIds) {
            if (id > maxFileId) maxFileId = id;
        }
        this.frames = new Animation[maxFileId + 1];

        for (int i = 0; i < fileIds.length; i++) {
            int fileId = fileIds[i];

            byte[] animData = Js5List.animations.takeFile(groupId, fileId);
            if (animData == null || animData.length < 2) {
                continue; // missing/corrupt file, leave null
            }

            int skeletonId = ((animData[0] & 255) << 8) | (animData[1] & 255);

            Skeleton skeleton = null;
            for (Skeleton s = (Skeleton) skeletonCache.last(); s != null; s = (Skeleton) skeletonCache.previous()) {
                if (skeletonId == s.id) {
                    skeleton = s;
                    break;
                }
            }

            if (skeleton == null) {
                byte[] skelData = getSkeletonData(skeletonId, altSkeletonIndexing);

                if (skelData == null) {
                    continue; // can't build animation without skeleton
                }

                skeleton = new Skeleton(skeletonId, skelData);
                skeletonCache.addFirst(skeleton);
            }

            // Store by the actual fileId (sparse-safe)
            if (fileId >= 0 && fileId < this.frames.length) {
                Animation animation = null;
                Animation alternateAnimation = null;
                Skeleton alternateSkeleton = null;

                try {
                    animation = new Animation(animData, skeleton);
                } catch (RuntimeException ignored) {
                    animation = null;
                }

                byte[] altSkelData = getSkeletonData(skeletonId, !altSkeletonIndexing);
                if (altSkelData != null) {
                    try {
                        alternateSkeleton = new Skeleton(skeletonId, altSkelData);
                        alternateAnimation = new Animation(animData, alternateSkeleton);
                    } catch (RuntimeException ignored) {
                        alternateAnimation = null;
                        alternateSkeleton = null;
                    }
                }

                // If both variants are decodable, prefer the one with more transforms.
                // Wrong skeleton matches often produce very low transform counts (head-only movement).
                if (alternateAnimation != null
                        && (animation == null || alternateAnimation.transformCount > animation.transformCount)) {
                    animation = alternateAnimation;
                    skeleton = alternateSkeleton;
                }
                if (animation != null) {
                    this.frames[fileId] = animation;
                    loadedAnimationCount++;
                }
            }
        }
    }

    public static Frames getFrames(int id) {
        Frames cached = (Frames) SequenceDefinition.cachedFrames.get((long) id);
        if (cached != null) return cached;

        cached = getFrames(id, false);
        if (cached == null) {
            cached = getFrames(id, true);
        }
        if (cached != null) {
            SequenceDefinition.cachedFrames.put(cached, (long) id);
        }
        return cached;
    }

    public static Frames getFrames(int groupId, boolean altSkeletonIndexing) {
        int[] fileIds = Js5List.animations.getGroupFileIds(groupId);
        if (fileIds == null || fileIds.length == 0) {
            return null;
        }
        Frames frames;
        try {
            frames = new Frames(groupId, altSkeletonIndexing);
        } catch (Exception e) {
            frames = null;
        }
        if (frames != null && frames.loadedAnimationCount <= 0) {
            frames = null;
        }

        if (frames != null) {
            SequenceDefinition.cachedFrames.put(frames, (long) groupId);
        }

        return frames;
    }

    public boolean hasAlphaTransform(int frameIndex) {
        if (frameIndex < 0 || frameIndex >= this.frames.length) {
            return false;
        }
        Animation a = this.frames[frameIndex];
        return a != null && a.hasAlphaTransform;
    }
}
