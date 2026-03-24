package com.client.definitions.anim;

import com.client.Configuration;
import com.client.audio.SoundData;
import com.client.definitions.anim.skeleton.SkeletalFrameHandler;
import com.client.collection.node.DualNode;
import com.client.collection.table.EvictingDualNodeHashTable;
import com.client.entity.model.Model;
import com.client.Buffer;
import com.client.js5.Js5List;
import com.client.js5.util.Js5ConfigType;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSSequenceDefinition;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SequenceDefinition extends DualNode implements RSSequenceDefinition {

    static boolean field2206 = false;
    public int field2479;
    public boolean field2493 = false;
    private static final int EQUIP_ITEM_OFFSET = 1024;

    public static EvictingDualNodeHashTable cached = new EvictingDualNodeHashTable(64);
    public static EvictingDualNodeHashTable cachedFrames = new EvictingDualNodeHashTable(100);
    public static EvictingDualNodeHashTable cachedModel = new EvictingDualNodeHashTable(100);

    public static int length() {
        return Js5List.getConfigSize(Js5ConfigType.SEQUENCE);
    }

    public static SequenceDefinition get(int id) {
        SequenceDefinition cached = (SequenceDefinition)SequenceDefinition.cached.get(id);
        if (cached == null) {
            byte[] data = Js5List.configs.takeFile(Js5ConfigType.SEQUENCE, id);
            cached = new SequenceDefinition();
            if (data != null) {
                cached.decode(new Buffer(data));
                cached.id = id;
            }
            AnimationDefinitionCustom.custom(id,cached);

            cached.postDecode();
            SequenceDefinition.cached.put(cached, id);
        }
       // customAnimations(id);

        return cached;
    }

    private static void customAnimations(int id) {
        if (id == 3186) {
            SequenceDefinition sequenceDefinition = get(3186);
            sequenceDefinition.forcedPriority = 6;
            sequenceDefinition.moveStyle = 2;
            sequenceDefinition.idleStyle = 2;
            sequenceDefinition.loopCount = 1;
            for (int a = 0; a < sequenceDefinition.frameLengths.length; ++a) {
                if (sequenceDefinition.frameLengths[a] != 9) continue;
                sequenceDefinition.frameLengths[a] = 25;
            }
            SequenceDefinition.cached.put(sequenceDefinition, id);
        }
    }


    void decode(Buffer buffer) {
        while(true) {
            int opcode = buffer.readUnsignedByte();
            if (opcode == 0) {
                return;
            }

            this.decodeNext(buffer, opcode);
        }
    }

    void decodeNext(Buffer buffer, int opcode) {
        if (opcode == 1) {
            int frameCount = buffer.readUShort();
            frameLengths = new int[frameCount];

            for(int index = 0; index < frameCount; ++index) {
                frameLengths[index] = buffer.readUShort();
            }

            frameIds = new int[frameCount];

            for(int index = 0; index < frameCount; ++index) {
                frameIds[index] = buffer.readUShort();
            }

            for(int index = 0; index < frameCount; ++index) {
                frameIds[index] += buffer.readUShort() << 16;
            }

        } else if (opcode == 2) {
            this.frameCount = buffer.readUShort();
        } else if (opcode == 3) {
            int count = buffer.readUnsignedByte();
            this.masks = new int[count + 1];
            for(int index = 0; index < count; ++index) {
                this.masks[index] = buffer.readUnsignedByte();
            }
            masks[count] = 9999999;
        } else if (opcode == 4) {
            this.stretches = true;
        } else if (opcode == 5) {
            this.forcedPriority = buffer.readUnsignedByte();
        } else if (opcode == 6) {
            this.leftHandItem = buffer.readUShort();
        } else if (opcode == 7) {
            this.rightHandItem = buffer.readUShort();
        } else if (opcode == 8) {
            this.loopCount = buffer.readUnsignedByte();
            this.replay = true;
        } else if (opcode == 9) {
            this.moveStyle = buffer.readUnsignedByte();
        } else if (opcode == 10) {
            this.idleStyle = buffer.readUnsignedByte();
        } else if (opcode == 11) {
            this.delayType = buffer.readUnsignedByte();
        } else if (opcode == 12) {
            int count = buffer.readUnsignedByte();
            this.chatFrameIds = new int[count];

            for(int index = 0; index < count; ++index) {
                this.chatFrameIds[index] = buffer.readUShort();
            }

            for(int index = 0; index < count; ++index) {
                this.chatFrameIds[index] += buffer.readUShort() << 16;
            }
        } else if (opcode == 13) {
            this.skeletalId = buffer.readInt();
        } else if (opcode == 14) {
            int var3 = buffer.readUnsignedShort();
            if (this.skeletalSounds == null) {
                this.skeletalSounds = new HashMap();
            }

            for (int var4 = 0; var4 < var3; ++var4) {
                int var5;
                SoundData var6;
                label162: {
                    var5 = buffer.readUnsignedShort();
                    if (buffer != null) {
                        boolean var7 = false;
                        boolean var8 = true;
                        boolean var9 = false;
                        boolean var10 = false;
                        boolean var11 = false;
                        int var13 = buffer.readUnsignedShort();
                        int var14 = buffer.readUnsignedByte();
                        int var15 = buffer.readUnsignedByte();
                        int var16 = buffer.readUnsignedByte();
                        int var17 = buffer.readUnsignedByte();
                        if (var13 >= 1 && var15 >= 1 && var16 >= 0 && var17 >= 0) {
                            var6 = new SoundData(var13, var14, var15, var16, var17);
                            break label162;
                        }
                    }

                    var6 = null;
                }

                if (var6 != null) {
                    if (!this.skeletalSounds.containsKey(var5)) {
                        this.skeletalSounds.put(var5, new ArrayList());
                    }

                    ((ArrayList)this.skeletalSounds.get(var5)).add(var6);
                }
            }
        } else if (opcode == 15) {
            this.rangeBegin = buffer.readUShort();
            this.rangeEnd = buffer.readUShort();
        } else if (opcode == 16) {
            this.animationHeightOffset = buffer.readByte();
        } else if (opcode == 17) {
            this.booleanMasks = new boolean[256];

            Arrays.fill(this.booleanMasks, false);

            int count = buffer.readUnsignedByte();

            for(int index = 0; index < count; ++index) {
                this.booleanMasks[buffer.readUnsignedByte()] = true;
            }
        } else if (opcode == 19) {
            this.field2493 = true;
        }
    }


    public int duration(int i) {
        int j = isCachedModelIdSet() ? 1 : frameLengths[i];
        if (j == 0)
            j = 1;
        return j;
    }


    public void postDecode() {
        normalizeHandItems();
        if (moveStyle == -1) {
            if (masks == null && booleanMasks == null) {
                moveStyle = 0;
            } else {
                moveStyle = 2;
            }
        }

        if (idleStyle == -1) {
            if (masks == null && booleanMasks == null) {
                idleStyle = 0;
            } else {
                idleStyle = 2;
            }
        }
        if (this.frameLengths != null) {
            this.field2479 = 0;

            for (int var1 = 0; var1 < this.frameLengths.length; ++var1) {
                this.field2479 += this.frameLengths[var1];
            }
        }
    }
    private void normalizeHandItems() {
        leftHandItem = normalizeHandItem(leftHandItem);
        rightHandItem = normalizeHandItem(rightHandItem);
    }

    private int normalizeHandItem(int value) {
        if (value == -1 || value == 65535) {
            return -1;
        }
        if (value == 0) {
            return 0;
        }
        int itemCount = Js5List.getConfigSize(Js5ConfigType.ITEM);
        if (value < itemCount) {
            return value + EQUIP_ITEM_OFFSET;
        }
        if (value >= EQUIP_ITEM_OFFSET && (value - EQUIP_ITEM_OFFSET) < itemCount) {
            return value;
        }
        return value;
    }

    public int id;

    public int[] frameIds;
    private int[] secondaryFrameIds;
    public int[] frameLengths;
    public int frameCount = -1;
    public int[] masks;
    private boolean[] booleanMasks;
    public boolean stretches;
    public int forcedPriority = 5;
    public boolean replay = false;
    public int leftHandItem = -1;
    public int rightHandItem = -1;
    public int loopCount = 99;
    public int moveStyle = -1;
    public int idleStyle = -1;
    public int delayType = 2;
    private int[] chatFrameIds;
    public SoundData[] soundEffects;
    private int soundSize = 0;
    private int skeletalId = -1;
    private int rangeBegin = 0;
    private int rangeEnd = 0;
    public int animationHeightOffset  = 0;

    public Map skeletalSounds;

    public static void clear() {
        SequenceDefinition.cached.clear();
        SequenceDefinition.cachedFrames.clear();
        SequenceDefinition.cachedModel.clear();
    }

    public int getSkeletalLength() {
        return this.rangeEnd - this.rangeBegin;
    }

    public boolean isCachedModelIdSet() {
        return skeletalId >= 0;
    }

    /**
     * Custom animation overrides that set explicit frameIds/frameLengths should
     * not use cached skeletal playback from the original cache sequence.
     */
    void forceLegacyFramePlayback() {
        this.skeletalId = -1;
        this.rangeBegin = 0;
        this.rangeEnd = 0;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Model transformWidgetModel(Model model, int primaryIndex) {
        if(isCachedModelIdSet()) {
            return this.transformActorModel(model, primaryIndex);
        }
        int regularFrame = frameIds[primaryIndex];
        Frames regularFrameset = Frames.getFrames(regularFrame >> 16);
        int regularFrameindex = regularFrame & 0xffff;
        if (regularFrameset == null) {
            return model.toSharedSpotAnimationModel(true);
        }
        Frames frameSet = null;
        int fameID = 0;
        int ifFrameindex = 0;
        if (chatFrameIds != null && primaryIndex < chatFrameIds.length) {
            fameID = chatFrameIds[primaryIndex];
            frameSet = Frames.getFrames(fameID >> 16);
            ifFrameindex = fameID & 0xffff;
        }

        Model animatedModel;
        if (frameSet == null || fameID == 0xffff) {
            animatedModel = model.toSharedSpotAnimationModel(!regularFrameset.hasAlphaTransform(regularFrameindex));
            animatedModel.animate(regularFrameset, regularFrameindex);
        } else {
            animatedModel = model.toSharedSpotAnimationModel(!regularFrameset.hasAlphaTransform(regularFrameindex) & !frameSet.hasAlphaTransform(ifFrameindex));
            animatedModel.animate(regularFrameset, regularFrameindex);
            animatedModel.animate(frameSet, ifFrameindex);
        }
        return animatedModel;
    }

    public Model transformActorModel(Model originalModel, int frameIndex) {
        Model transformedModel;
        if (!this.isCachedModelIdSet()) {
            if (this.frameIds == null || frameIndex < 0 || frameIndex >= this.frameIds.length) {
                return originalModel.toSharedSequenceModel(true);
            }
            frameIndex = this.frameIds[frameIndex];
            Frames frameSet = Frames.getFrames(frameIndex >> 16);
            frameIndex &= 65535;
            if (frameSet == null || frameSet.frames == null || frameIndex < 0 || frameIndex >= frameSet.frames.length || frameSet.frames[frameIndex] == null) {
                return originalModel.toSharedSequenceModel(true);
            } else {
                transformedModel = originalModel.toSharedSequenceModel(!frameSet.hasAlphaTransform(frameIndex));
                try {
                    transformedModel.animate(frameSet, frameIndex);
                } catch (RuntimeException ex) {
                    return originalModel.toSharedSequenceModel(true);
                }
                return transformedModel ;
            }
        } else {
            SkeletalFrameHandler skeletalFrames = SkeletalFrameHandler.getFrames(this.skeletalId);
            if (skeletalFrames == null) {
                return originalModel.toSharedSequenceModel(true);
            } else {
                transformedModel = originalModel.toSharedSequenceModel(!skeletalFrames.hasAlphaTransforms());
                try {
                    transformedModel.applySkeletalTransform(skeletalFrames, frameIndex);
                } catch (RuntimeException ex) {
                    return originalModel.toSharedSequenceModel(true);
                }
                return transformedModel;
            }
        }
    }


    private SkeletalFrameHandler getSkeletalFrameHandler() {
        return this.isCachedModelIdSet() ? SkeletalFrameHandler.getFrames(this.skeletalId) : null;
    }

    public Model applyTransformations(Model originalModel, int frameIndex, SequenceDefinition sequenceDef, int sequenceFrameIndex) {
        if (field2206 && !this.isCachedModelIdSet() && !sequenceDef.isCachedModelIdSet()) {
            return this.transformModelSequence(originalModel, frameIndex, sequenceDef, sequenceFrameIndex);
        } else {
            Model transformedModel = originalModel.toSharedSequenceModel(false);
            boolean sequenceCachedModelFlag = false;
            Frames primaryFrames = null;
            Skeleton skeleton = null;
            SkeletalFrameHandler skeletalFrameHandler;
            if (this.isCachedModelIdSet()) {
                skeletalFrameHandler = this.getSkeletalFrameHandler();
                if (skeletalFrameHandler == null) {
                    return transformedModel;
                }

                if (sequenceDef.isCachedModelIdSet() && this.booleanMasks == null) {
                    transformedModel.applySkeletalTransform(skeletalFrameHandler, frameIndex);
                    return transformedModel;
                }

                skeleton = skeletalFrameHandler.base;
                transformedModel.applyComplexTransform(skeleton, skeletalFrameHandler, frameIndex, this.booleanMasks, false, !sequenceDef.isCachedModelIdSet());
            } else {
                frameIndex = this.frameIds[frameIndex];
                primaryFrames = Frames.getFrames(frameIndex >> 16);
                frameIndex &= 65535;
                if (primaryFrames == null) {
                    return sequenceDef.transformActorModel(originalModel, sequenceFrameIndex);
                }

                if (!sequenceDef.isCachedModelIdSet() && (this.masks == null || sequenceFrameIndex == -1)) {
                    transformedModel.animate(primaryFrames, frameIndex);
                    return transformedModel;
                }

                if (this.masks == null || sequenceFrameIndex == -1) {
                    transformedModel.animate(primaryFrames, frameIndex);
                    return transformedModel;
                }

                sequenceCachedModelFlag = sequenceDef.isCachedModelIdSet();
                if (!sequenceCachedModelFlag) {
                    transformedModel.animate(primaryFrames, frameIndex, this.masks, false);
                }
            }

            if (sequenceDef.isCachedModelIdSet()) {
                skeletalFrameHandler = sequenceDef.getSkeletalFrameHandler();
                if (skeletalFrameHandler == null) {
                    return transformedModel;
                }

                if (skeleton == null) {
                    skeleton = skeletalFrameHandler.base;
                }

                transformedModel.applyComplexTransform(skeleton, skeletalFrameHandler, sequenceFrameIndex, this.booleanMasks, true, true);
            } else {
                sequenceFrameIndex = sequenceDef.frameIds[sequenceFrameIndex];
                Frames var10 = Frames.getFrames(sequenceFrameIndex >> 16);
                sequenceFrameIndex &= 65535;
                if (var10 == null) {
                    return this.transformActorModel(originalModel, frameIndex);
                }

                transformedModel.animate(var10, sequenceFrameIndex, this.masks, true);
            }

            if (sequenceCachedModelFlag && primaryFrames != null) {
                transformedModel.animate(primaryFrames, frameIndex, this.masks, false);
            }

            transformedModel.resetBounds();
            return transformedModel;
        }
    }

    Model transformModelSequence(Model originalModel, int frameIndex, SequenceDefinition sequence, int sequenceFrameIndex) {
        frameIndex = this.frameIds[frameIndex];
        Frames var5 = Frames.getFrames(frameIndex >> 16);
        frameIndex &= 65535;
        if (var5 == null) {
            return sequence.transformActorModel(originalModel, sequenceFrameIndex);
        } else {
            sequenceFrameIndex = sequence.frameIds[sequenceFrameIndex];
            Frames frameSet = Frames.getFrames(sequenceFrameIndex >> 16);
            sequenceFrameIndex &= 65535;
            Model transformedModel;
            if (frameSet == null) {
                transformedModel = originalModel.toSharedSequenceModel(!var5.hasAlphaTransform(frameIndex));
                transformedModel.animate(var5, frameIndex);
            } else {
                transformedModel = originalModel.toSharedSequenceModel(!var5.hasAlphaTransform(frameIndex) & !frameSet.hasAlphaTransform(sequenceFrameIndex));
                transformedModel.animate2(var5, frameIndex, frameSet, sequenceFrameIndex, this.masks);
            }
            return transformedModel;
        }
    }


    Model transformSpotAnimationModel(Model originalModel, int frameIndex) {
        Model transformedModel;
        if (!this.isCachedModelIdSet()) {
            if (this.frameIds == null || frameIndex < 0 || frameIndex >= this.frameIds.length) {
                return originalModel.toSharedSpotAnimationModel(true);
            }
            frameIndex = this.frameIds[frameIndex];
            Frames frameSet = Frames.getFrames(frameIndex >> 16);
            frameIndex &= 65535;
            if (frameSet == null || frameSet.frames == null || frameIndex < 0 || frameIndex >= frameSet.frames.length || frameSet.frames[frameIndex] == null) {
                return originalModel.toSharedSpotAnimationModel(true);
            } else {
                transformedModel = originalModel.toSharedSpotAnimationModel(!frameSet.hasAlphaTransform(frameIndex));
                try {
                    transformedModel.animate(frameSet, frameIndex);
                } catch (RuntimeException ex) {
                    return originalModel.toSharedSpotAnimationModel(true);
                }
                return transformedModel;
            }
        } else {
            SkeletalFrameHandler var3 = SkeletalFrameHandler.getFrames(this.skeletalId);
            if (var3 == null) {
                return originalModel.toSharedSpotAnimationModel(true);
            } else {
                transformedModel = originalModel.toSharedSpotAnimationModel(!var3.hasAlphaTransforms());
                try {
                    transformedModel.applySkeletalTransform(var3, frameIndex);
                } catch (RuntimeException ex) {
                    return originalModel.toSharedSpotAnimationModel(true);
                }
                return transformedModel;
            }
        }
    }

    public Model transformObjectModel(Model originalModel, int frameIndex, int rotationIndex) {
        if (!this.isCachedModelIdSet()) {
            frameIndex = this.frameIds[frameIndex];
            Frames frameSet = Frames.getFrames(frameIndex >> 16);
            frameIndex &= 65535;
            if (frameSet == null) {
                return originalModel.toSharedSequenceModel(true);
            } else {
                Model transformedModel = originalModel.toSharedSequenceModel(!frameSet.hasAlphaTransform(frameIndex));
                rotationIndex &= 3;
                if (rotationIndex == 1) {
                    transformedModel.rs$rotateY270Ccw();
                } else if (rotationIndex == 2) {
                    transformedModel.rs$rotateY180Ccw();
                } else if (rotationIndex == 3) {
                    transformedModel.rs$rotateY90Ccw();
                }

                transformedModel.animate(frameSet, frameIndex);
                if (rotationIndex == 1) {
                    transformedModel.rs$rotateY90Ccw();
                } else if (rotationIndex == 2) {
                    transformedModel.rs$rotateY180Ccw();
                } else if (rotationIndex == 3) {
                    transformedModel.rs$rotateY270Ccw();
                }

                return transformedModel;
            }
        } else {
            SkeletalFrameHandler skeletalFrameHandler = SkeletalFrameHandler.getFrames(this.skeletalId);

            if (skeletalFrameHandler == null) {
                return originalModel.toSharedSequenceModel(true);
            } else {
                Model transformedModel = originalModel.toSharedSequenceModel(!skeletalFrameHandler.hasAlphaTransforms());
                rotationIndex &= 3;
                if (rotationIndex == 1) {
                    transformedModel.rs$rotateY270Ccw();
                } else if (rotationIndex == 2) {
                    transformedModel.rs$rotateY180Ccw();
                } else if (rotationIndex == 3) {
                    transformedModel.rs$rotateY90Ccw();
                }

                transformedModel.applySkeletalTransform(skeletalFrameHandler, frameIndex);
                if (rotationIndex == 1) {
                    transformedModel.rs$rotateY90Ccw();
                } else if (rotationIndex == 2) {
                    transformedModel.rs$rotateY180Ccw();
                } else if (rotationIndex == 3) {
                    transformedModel.rs$rotateY270Ccw();
                }

                return transformedModel;
            }
        }
    }

    public SoundData[] getSoundEffects() {
        return soundEffects;
    }


    public Map getSkeletalSounds() {
        return skeletalSounds;
    }

    @Override
    public net.runelite.api.Skeleton getSkeleton() {
        return null;
    }

    @Override
    public int getTransformCount() {
        return 0;
    }

    @Override
    public int[] getTransformTypes() {
        return new int[0];
    }

    @Override
    public int[] getTranslatorX() {
        return new int[0];
    }

    @Override
    public int[] getTranslatorY() {
        return new int[0];
    }

    @Override
    public int[] getTranslatorZ() {
        return new int[0];
    }

    @Override
    public boolean isShowing() {
        return false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getNumFrames() {
        return getFrameLengths().length;
    }

    @Override
    public int getRestartMode() {
        return delayType;
    }

    @Override
    public void setRestartMode(int restartMode) {
        delayType = restartMode;
    }

    @Override
    public int getFrameCount() {
        return frameCount;
    }

    @Override
    public int[] getFrameIDs() {
        return frameIds;
    }

    @Override
    public int[] getFrameLengths() {
        return frameLengths;
    }

    @Override
    public int[] getChatFrameIds() {
        return chatFrameIds;
    }

    @Override
    public RSModel transformSpotAnimationModel(RSModel var1, int var2) {
        return transformSpotAnimationModel((Model) var1,var2);
    }
}
