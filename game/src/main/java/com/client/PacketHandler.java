package com.client;

import com.client.audio.StaticSound;
import com.client.broadcast.Broadcast;
import com.client.broadcast.BroadcastManager;
import com.client.camera.Camera;
import com.client.collection.node.Node;
import com.client.collection.node.NodeDeque;
import com.client.definitions.ItemDefinition;
import com.client.definitions.NpcDefinition;
import com.client.definitions.ObjectDefinition;
import com.client.draw.ImageCache;
import com.client.entity.model.Model;
import com.client.features.EntityTarget;
import com.client.features.ExperienceDrop;
import com.client.features.gametimers.GameTimerHandler;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.daily.DailyRewards;
import com.client.graphics.interfaces.eventcalendar.EventCalendar;
import com.client.graphics.interfaces.impl.Autocast;
import com.client.graphics.interfaces.builder.impl.GroupIronmanBank;
import com.client.graphics.interfaces.impl.Bank;
import com.client.graphics.interfaces.impl.DonatorRewards;
import com.client.graphics.interfaces.impl.Interfaces;
import com.client.graphics.interfaces.impl.MonsterDropViewer;
import com.client.graphics.interfaces.impl.Nightmare;
import com.client.graphics.interfaces.impl.QuestTab;
import com.client.js5.Js5List;
import com.client.scene.Projectile;
import com.client.scene.SceneObject;
import com.client.scene.SpotAnimEntity;
import com.client.scene.object.GroundDecoration;
import com.client.scene.object.InteractiveObject;
import com.client.scene.object.Wall;
import com.client.scene.object.WallDecoration;
import com.client.script.ClientScripts;
import com.client.sign.Signlink;
import com.client.sound.SoundType;
import com.client.utilities.ObjectKeyUtil;
import com.google.common.base.Preconditions;
import dorkbox.notify.Notify;
import dorkbox.notify.Pos;
import dorkbox.util.ActionHandler;
import net.runelite.api.GameState;
import net.runelite.api.ItemContainer;
import net.runelite.api.Skill;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.StatChanged;
import net.runelite.api.hooks.Callbacks;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Handles incoming server packets by processing the opcode switch statement.
 * Extracted from Client.java to reduce its size and improve organization.
 *
 * <p>This class contains:
 * <ul>
 *   <li>{@link #parsePacket()} - Main packet dispatcher (~70 opcodes)</li>
 *   <li>{@link #processScenePacket(Buffer, int)} - Scene/ground-item opcode handler (formerly method137)</li>
 *   <li>{@link #handledPacket34(int)} - Special item container handler for packet 34</li>
 * </ul>
 */
public class PacketHandler {

    private final Client client;

    private static final Comparator<ExperienceDrop> HIGHEST_POSITION = (o1, o2) ->
            Integer.compare(o2.getYPosition(), o1.getYPosition());

    public PacketHandler(Client client) {
        this.client = client;
    }

    public boolean parsePacket() {
        if (client.socketStream == null)
            return false;
        try {
            // Local alias for the frequently-used input buffer
            final Buffer inStream = client.inStream;

            int i = client.socketStream.available();
            if (i == 0)
                return false;
            if (client.incomingPacket == -1) {
                client.socketStream.flushInputStream(inStream.payload, 1);
                client.incomingPacket = inStream.payload[0] & 0xff;
                if (client.encryption != null)
                    client.incomingPacket = client.incomingPacket - client.encryption.getNextIntKey() & 0xff;
                client.packetSize = SizeConstants.packetSizes[client.incomingPacket];
                i--;
            }
            if (client.packetSize == -1)
                if (i > 0) {
                    client.socketStream.flushInputStream(inStream.payload, 1);
                    client.packetSize = inStream.payload[0] & 0xff;
                    i--;
                } else {
                    return false;
                }
            if (client.packetSize == -2)
                if (i > 1) {
                    client.socketStream.flushInputStream(inStream.payload, 2);
                    inStream.currentPosition = 0;
                    client.packetSize = inStream.readUShort();
                    i -= 2;
                } else {
                    return false;
                }
            if (i < client.packetSize) {
                return false;
            }

            inStream.currentPosition = 0;
            client.socketStream.flushInputStream(inStream.payload, client.packetSize);
            client.anInt1009 = 0;

            client.previousPacketSize2 = client.previousPacketSize1;
            client.previousPacket2 = client.previousPacket1;

            client.previousPacketSize1 = client.dealtWithPacketSize;
            client.previousPacket1 = client.dealtWithPacket;

            client.dealtWithPacket = client.incomingPacket;
            client.dealtWithPacketSize = client.packetSize;

            PacketLog.add(client.incomingPacket, client.packetSize, inStream.payload);

            if (client.incomingPacket != 65 && client.incomingPacket != 81) {
                // Ignore the update packets
            }
            switch (client.incomingPacket) {
                case 13: // Client script
                    String types = inStream.readString();
                    Object[] arguments = new Object[types.length()];

                    for (int index = 0; index < types.length(); index++) {
                        if (types.charAt(index) == 's') {
                            arguments[index] = inStream.readNullTerminatedString();
                        } else { // 'i'
                            arguments[index] = inStream.readDWord();
                        }
                    }

                    int scriptId = inStream.readUShort();
                    ClientScripts.execute(scriptId, arguments);
                    client.incomingPacket = -1;
                    return true;
                case 12:
                    int soundId = inStream.readUShort();
                    SoundType incomingSoundType = SoundType.values()[inStream.readUnsignedByte()];
                    int entitySoundSource = inStream.readUShort();
                    Entity entity = null;
                    if (entitySoundSource != 0) {
                        if (entitySoundSource >= Short.MAX_VALUE) {
                            entitySoundSource -= Short.MAX_VALUE;
                            entity = client.players[entitySoundSource];
                        } else {
                            entity = client.npcs[entitySoundSource];
                        }
                    }

                    switch (incomingSoundType) {
                        case MUSIC:
                            StaticSound.playSong(soundId);
                            break;
                        case AREA_SOUND:
                            if (entity != null) {
                                StaticSound.queueAreaSoundEffect(soundId, entity.x, entity.y, 12, 0);
                            } else {
                                StaticSound.queueSoundEffect(soundId, 1, 0);
                            }
                            break;
                        case SOUND:
                        default:
                            StaticSound.queueSoundEffect(soundId, 1, 0);
                            break;
                    }
                    client.incomingPacket = -1;
                    return true;
                case 10:
                    int flashColor = inStream.readDWord();
                    int maxIntensity = inStream.readUnsignedByte();
                    boolean autoFade = inStream.readUnsignedByte() == 0;

                    if (flashColor == Client.MUD_SPLASH_SENTINEL) {
                        client.triggerMudSplash(maxIntensity);
                        client.incomingPacket = -1;
                        return true;
                    }

                    client.screenFlashDrawing = true;
                    client.screenFlashOpacityDownward = false;
                    client.screenFlashOpacity = 0d;
                    client.screenFlashColor = flashColor;
                    client.screenFlashMaxIntensity = maxIntensity;
                    client.screenFlashAutoFadeOut = autoFade;
                    client.incomingPacket = -1;
                    return true;

                // Set strings inside string container
                case 5:
                    int stringContainerId = inStream.readUShort();
                    int strings = inStream.readUShort();
                    RSInterface stringContainer = RSInterface.get(stringContainerId);
                    Preconditions.checkState(stringContainer != null && stringContainer.stringContainer != null, "No string container at id: " + stringContainer);
                    stringContainer.stringContainer.clear();
                    for (int index = 0; index < strings; index++) {
                        stringContainer.stringContainer.add(inStream.readString());
                    }
                    EventCalendar.getCalendar().onStringContainerUpdated(stringContainerId);
                    client.incomingPacket = -1;
                    return true;

                // Reset scroll position
                case 2:
                    int resetScrollInterfaceId = inStream.readUShort();
                    RSInterface.interfaceCache[resetScrollInterfaceId].scrollPosition = 0;
                    client.incomingPacket = -1;
                    return true;

                case 3:
                    int setScrollMaxInterfaceId = inStream.readUShort();
                    int scrollMax = inStream.readUShort();
                    RSInterface.interfaceCache[setScrollMaxInterfaceId].scrollMax = scrollMax;
                    client.incomingPacket = -1;
                    return true;


                /**
                 * Progress Bar Update Packet
                 */
                case 77:
                    while (inStream.currentPosition < client.packetSize) {
                        int interfaceId = inStream.readDWord();
                        byte progressBarState = inStream.readSignedByte();
                        byte progressBarPercentage = inStream.readSignedByte();

                        RSInterface rsInterface = RSInterface.interfaceCache[interfaceId];
                        rsInterface.progressBarState = progressBarState;
                        rsInterface.progressBarPercentage = progressBarPercentage;
                    }
                    client.incomingPacket = -1;
                    return true;

                case 137:
                    client.specialAttack = inStream.readUnsignedByte();
                    client.incomingPacket = -1;
                    return true;

                /**
                 * EntityTarget Update Packet
                 */
                case 222:
                    byte entityState = (byte) inStream.readUnsignedByte();
                    if (entityState != 0) {
                        short entityIndex = (short) inStream.readUShort();
                        short currentHealth = (short) inStream.readUShort();
                        short maximumHealth = (short) inStream.readUShort();
                        client.entityTarget = new EntityTarget(entityState, entityIndex, currentHealth, maximumHealth, client.newSmallFont);
                    } else {
                        if (client.entityTarget != null) {
                            client.entityTarget.stop();
                        }
                        client.entityTarget = null;
                    }
                    client.incomingPacket = -1;
                    return true;

                /**
                 * Timer Update Packet
                 */
                case 223:
                    int timerId = inStream.readUnsignedByte();
                    int secondsToAdd = inStream.readUShort();
                    GameTimerHandler.getSingleton().startGameTimer(timerId, TimeUnit.SECONDS, secondsToAdd, 0);
                    client.incomingPacket = -1;
                    return true;

                case 224:
                    timerId = inStream.readUShort();
                    int itemId = inStream.readUShort();
                    secondsToAdd = inStream.readUShort();
                    GameTimerHandler.getSingleton().startGameTimer(timerId, TimeUnit.SECONDS, secondsToAdd, itemId);
                    client.incomingPacket = -1;
                    return true;

                case 11:
                    long experience = inStream.readQWord();
                    byte length = inStream.readSignedByte();
                    int[] skills = new int[length];

                    for (int j = 0; j < length; j++) {
                        skills[j] = inStream.readSignedByte();
                    }

                    ExperienceDrop drop = new ExperienceDrop(experience, skills);

                    if (!client.experienceDrops.isEmpty()) {
                        List<ExperienceDrop> sorted = new ArrayList<ExperienceDrop>(client.experienceDrops);
                        Collections.sort(sorted, HIGHEST_POSITION);
                        ExperienceDrop highest = sorted.get(0);
                        if (highest.getYPosition() >= ExperienceDrop.START_Y - 5) {
                            drop.increasePosition(highest.getYPosition() - ExperienceDrop.START_Y + 20);
                        }
                    }

                    client.experienceDrops.offer(drop);
                    client.incomingPacket = -1;
                    return true;

                case 81: // update player packet
                    client.updatePlayers(client.packetSize, inStream);
                    client.incomingPacket = -1;
                    return true;

                case 176:
                    client.daysSinceRecovChange = inStream.readNegUByte();
                    client.unreadMessages = inStream.readUShortA();
                    client.membersInt = inStream.readUnsignedByte();
                    client.anInt1193 = inStream.method440();
                    client.daysSinceLastLogin = inStream.readUShort();
                    if (client.anInt1193 != 0 && Client.openInterfaceID == -1) {
                        Signlink.dnslookup(StringUtils.method586(client.anInt1193));
                        client.clearTopInterfaces();
                        char c = '\u028A';
                        if (client.daysSinceRecovChange != 201 || client.membersInt == 1)
                            c = '\u028F';
                        client.reportAbuseInput = "";
                        client.canMute = false;
                        for (int k9 = 0; k9 < RSInterface.interfaceCache.length; k9++) {
                            if (RSInterface.interfaceCache[k9] == null || RSInterface.interfaceCache[k9].contentType != c)
                                continue;
                            Client.openInterfaceID = RSInterface.interfaceCache[k9].parentID;

                        }
                    }
                    client.incomingPacket = -1;
                    return true;

                case 64:
                    client.localX = inStream.readNegUByte();
                    client.localY = inStream.method428();
                    for (int j = client.localX; j < client.localX + 8; j++) {
                        for (int l9 = client.localY; l9 < client.localY + 8; l9++)
                            if (client.groundItems[client.plane][j][l9] != null) {
                                client.groundItems[client.plane][j][l9] = null;
                                client.updateItemPile(j, l9);

                            }
                    }
                    for (SpawnedObject spawnedObject = (SpawnedObject) client.spawns
                            .reverseGetFirst(); spawnedObject != null; spawnedObject = (SpawnedObject) client.spawns
                            .reverseGetNext())
                        if (spawnedObject.x >= client.localX && spawnedObject.x < client.localX + 8
                                && spawnedObject.y >= client.localY && spawnedObject.y < client.localY + 8
                                && spawnedObject.plane == client.plane)
                            spawnedObject.getLongetivity = 0;
                    client.incomingPacket = -1;
                    return true;

                case 9:
                    String text = inStream.readString();
                    byte state = inStream.readSignedByte();
                    byte seconds = inStream.readSignedByte();
                    int drawingWidth = !Client.instance.isResized() ? 519 : Client.canvasWidth;
                    int drawingHeight = !Client.instance.isResized() ? 338 : Client.canvasHeight;

                    client.incomingPacket = -1;
                    return true;

                case 185:
                    int k = inStream.method436();
                    RSInterface.interfaceCache[k].defaultMediaType = 3;
                    if (Client.localPlayer.npcDefinition == null)
                        RSInterface.interfaceCache[k].defaultMedia = (Client.localPlayer.appearanceColors[0] << 25)
                                + (Client.localPlayer.appearanceColors[4] << 20) + (Client.localPlayer.equipment[0] << 15)
                                + (Client.localPlayer.equipment[8] << 10) + (Client.localPlayer.equipment[11] << 5) + Client.localPlayer.equipment[1];
                    else
                        RSInterface.interfaceCache[k].defaultMedia = (int) (0x12345678L + Client.localPlayer.npcDefinition.npcId);
                    client.incomingPacket = -1;
                    return true;

                case 107:
                    Camera.isCameraUpdating = false;
                    client.isCameraLocked = false;
                    Camera.followCameraMode = false;
                    Camera.staticCameraMode = false;
                    Camera.staticCameraX = 0;
                    Camera.staticCameraY = 0;
                    Camera.staticCameraAltitudeOffset = 0;
                    Camera.fixedCamera = false;
                    Camera.cameraPitchStep = 0;
                    Camera.cameraYawSpeed = 0;
                    Camera.cameraInterpolationSpeed = 0;
                    Camera.cameraMinimumStep = 0;
                    Camera.cameraTargetX = 0;
                    Camera.cameraTargetY = 0;
                    Camera.cameraAltitudeOffset = 0;
                    Camera.camera = null;
                    Camera.cameraPitch = null;
                    Camera.cameraYaw = null;

                    for (int var20 = 0; var20 < 5; ++var20) {
                        client.quakeDirectionActive[var20] = false;
                    }
                    client.xpCounter = 0;
                    client.incomingPacket = -1;
                    return true;

                case 72:
                    int i1 = inStream.method434();
                    RSInterface class9 = RSInterface.interfaceCache[i1];
                    for (int k15 = 0; k15 < class9.inventoryItemId.length; k15++) {
                        class9.inventoryItemId[k15] = -1;
                        class9.inventoryItemId[k15] = 0;
                    }
                    client.incomingPacket = -1;
                    return true;

                case 214:
                    client.ignoreCount = client.packetSize / 8;
                    for (int j1 = 0; j1 < client.ignoreCount; j1++)
                        client.ignoreListAsLongs[j1] = inStream.readQWord();
                    client.incomingPacket = -1;
                    return true;

                case 166:
                    client.isCameraLocked = true;
                    Camera.isCameraUpdating = false;
                    Camera.followCameraMode = false;
                    Camera.cameraTargetX = inStream.readUnsignedByte() * 16384;
                    Camera.cameraTargetY = inStream.readUnsignedByte() * 16384;
                    Camera.cameraAltitudeOffset = inStream.readUShort();
                    Camera.cameraMinimumStep = inStream.readUnsignedByte();
                    Camera.cameraInterpolationSpeed = inStream.readUnsignedByte();
                    if (Camera.cameraInterpolationSpeed >= 100) {
                        client.cameraX = Camera.cameraTargetX * 128 + 64;
                        client.cameraZ = Camera.cameraTargetY * 128 + 64;
                        client.cameraY = client.getCenterHeight(client.plane, client.cameraZ, client.cameraX) - Camera.cameraAltitudeOffset;
                    }
                    client.incomingPacket = -1;
                    return true;

                case 134:
                    Client.needDrawTabArea = true;
                    int skillId = inStream.readUnsignedByte();
                    int experience2 = inStream.method439();
                    int currentLevel = inStream.readUnsignedByte();
                    int xp = client.currentExp[skillId];

                    client.currentExp[skillId] = experience2;
                    client.currentLevels[skillId] = currentLevel;
                    client.maximumLevels[skillId] = 1;
                    client.xpCounter += client.currentExp[skillId] - xp;
                    client.expAdded = client.currentExp[skillId] - xp;

                    try {
                        client.getCallbacks().post(new StatChanged(
                                Skill.valueOf(Skills.SKILL_NAMES_ORDER[skillId].toUpperCase()),
                                experience2,
                                currentLevel,
                                3
                        ));

                    }catch (Exception ex) {}


                    for (int k20 = 0; k20 < 98; k20++)
                        if (experience2 >= Client.SKILL_EXPERIENCE[k20])
                            client.maximumLevels[skillId] = k20 + 2;
                    client.updateSkillStrings(skillId);
                    client.incomingPacket = -1;
                    return true;

                case 71:
                    int l1 = inStream.readUShort();
                    int j10 = inStream.method426();
                    if (l1 == 65535)
                        l1 = -1;
                    if (l1 == 49200) {
                        Interfaces.resetArceuusSprites();
                        RSInterface arceuus = RSInterface.interfaceCache[49200];
                        if (arceuus == null || arceuus.children == null || arceuus.children.length == 0) {
                            Interfaces.arceuus(RSInterface.defaultTextDrawingAreas);
                        }
                    }
                    Client.tabInterfaceIDs[j10] = l1;
                    Client.needDrawTabArea = true;
                    Client.tabAreaAltered = true;
                    client.incomingPacket = -1;
                    return true;

                case 74:
                    int songId = inStream.method434();
                    if (songId == 65535) {
                        songId = -1;
                    }
                    if (songId != client.currentSong && client.musicEnabled && !Client.lowMem && client.prevSong == 0) {
                        client.nextSong = songId;
                        client.songChanging = true;
                        StaticSound.playSong(songId);
                    }
                    client.currentSong = songId;
                    client.incomingPacket = -1;
                    return true;

                case 121:
                    int j2 = inStream.method436();
                    int k10 = inStream.readUShortA();
                    if (client.musicEnabled && !Client.lowMem) {
                        client.nextSong = j2;
                        client.songChanging = false;
                        StaticSound.playSong(j2);
                        client.prevSong = k10;
                    }
                    client.incomingPacket = -1;
                    return true;

                case 7:
                    int componentId = inStream.readDWord();
                    byte spriteIndex = inStream.readSignedByte();
                    RSInterface component = RSInterface.interfaceCache[componentId];
                    if (component != null) {
                        if (component.backgroundSprites != null && spriteIndex <= component.backgroundSprites.length - 1) {
                            Sprite sprite = component.backgroundSprites[spriteIndex];
                            if (sprite != null) {
                                component.sprite1 = component.backgroundSprites[spriteIndex];
                            }
                        }
                    }
                    client.incomingPacket = -1;
                    return true;

                case 109:
                    client.resetLogout();
                    client.incomingPacket = -1;
                    return false;

                case 70:
                    int k2 = inStream.readSignedWord();
                    int l10 = inStream.method437();
                    int i16 = inStream.method434();
                    RSInterface class9_5 = RSInterface.interfaceCache[i16];
                    class9_5.anInt263 = k2;
                    class9_5.anInt265 = l10;
                    client.incomingPacket = -1;
                    return true;

                case 243:
                    int fog = inStream.readUnsignedByte();
                    client.fogEnabled = fog == 1;
                    client.fogOpacity = inStream.readDWord();
                    client.incomingPacket = -1;
                    return true;

                case 73:
                case 241:
                        client.setGameState(GameState.LOADING);
                        int mapRegionX = client.currentRegionX;
                        int mapRegionY = client.currentRegionY;
                        if (client.incomingPacket == 73) {
                            mapRegionX = inStream.readUShortA();
                            mapRegionY = inStream.readUShort();
                            client.isInInstance = false;
                        }
                        if (client.incomingPacket == 241) {
                            mapRegionY = inStream.readUShortA();
                            inStream.initBitAccess();
                            for (int z = 0; z < 4; z++) {
                                for (int x = 0; x < 13; x++) {
                                    for (int y = 0; y < 13; y++) {
                                        int visible = inStream.readBits(1);
                                        if (visible == 1)
                                            client.constructRegionData[z][x][y] = inStream.readBits(26);
                                        else
                                            client.constructRegionData[z][x][y] = -1;
                                    }
                                }
                            }
                            inStream.finishBitAccess();
                            mapRegionX = inStream.readUShort();
                            client.isInInstance = true;
                        }
                        if (client.incomingPacket != 241 && client.currentRegionX == mapRegionX && client.currentRegionY == mapRegionY && client.gameState == GameState.LOGGED_IN.getState()) {
                            client.incomingPacket = -1;
                            return true;
                        }
                        client.currentRegionX = mapRegionX;
                        client.currentRegionY = mapRegionY;
                        Client.baseX = (client.currentRegionX - 6) * 8;
                        Client.baseY = (client.currentRegionY - 6) * 8;
                        client.inTutorialIsland = (client.currentRegionX / 8 == 48 || client.currentRegionX / 8 == 49) && client.currentRegionY / 8 == 48;
                        if (client.currentRegionX / 8 == 48 && client.currentRegionY / 8 == 148)
                            client.inTutorialIsland = true;

                        client.longStartTime = System.currentTimeMillis();

                        client.setGameState(GameState.LOADING);

                        if (client.incomingPacket == 73) {
                            int regionCount = 0;
                            for (int x = (client.currentRegionX - 6) / 8; x <= (client.currentRegionX + 6) / 8; x++) {
                                for (int y = (client.currentRegionY - 6) / 8; y <= (client.currentRegionY + 6) / 8; y++)
                                    regionCount++;
                            }
                            client.regionLandArchives = new byte[regionCount][];
                            client.regionMapArchives = new byte[regionCount][];
                            client.regions = new int[regionCount];
                            client.regionLandIds = new int[regionCount];
                            client.regionLocIds = new int[regionCount];
                            regionCount = 0;

                            for (int x = (client.currentRegionX - 6) / 8; x <= (client.currentRegionX + 6) / 8; x++) {
                                for (int y = (client.currentRegionY - 6) / 8; y <= (client.currentRegionY + 6) / 8; y++) {
                                    client.regions[regionCount] = (x << 8) + y;
                                    if (client.inTutorialIsland && (y == 49 || y == 149 || y == 147 || x == 50 || x == 49 && y == 47)) {
                                        client.regionLandIds[regionCount] = -1;
                                        client.regionLocIds[regionCount] = -1;
                                        regionCount++;
                                    } else {
                                        int id = y + (x << 8);
                                        client.regions[regionCount] = id;
                                        client.regionLandIds[regionCount] = Js5List.maps.getGroupId("m" + x + "_" + y);
                                        client.regionLocIds[regionCount] = Js5List.maps.getGroupId("l" + x + "_" + y);
                                        regionCount++;
                                    }
                                }
                            }

                        }
                        if (client.incomingPacket == 241) {
                            int totalLegitChunks = 0;
                            int totalChunks[] = new int[676];
                            for (int z = 0; z < 4; z++) {
                                for (int x = 0; x < 13; x++) {
                                    for (int y = 0; y < 13; y++) {
                                        int tileBits = client.constructRegionData[z][x][y];
                                        if (tileBits != -1) {
                                            int xCoord = tileBits >> 14 & 0x3ff;
                                            int yCoord = tileBits >> 3 & 0x7ff;
                                            int mapRegion = (xCoord / 8 << 8) + yCoord / 8;
                                            for (int idx = 0; idx < totalLegitChunks; idx++) {
                                                if (totalChunks[idx] != mapRegion)
                                                    continue;
                                                mapRegion = -1;

                                            }
                                            if (mapRegion != -1)
                                                totalChunks[totalLegitChunks++] = mapRegion;
                                        }
                                    }
                                }
                            }
                            client.regionLandArchives = new byte[totalLegitChunks][];
                            client.regionMapArchives = new byte[totalLegitChunks][];
                            client.regions = new int[totalLegitChunks];
                            client.regionLandIds = new int[totalLegitChunks];
                            client.regionLocIds = new int[totalLegitChunks];
                            for (int idx = 0; idx < totalLegitChunks; idx++) {
                                int region = client.regions[idx] = totalChunks[idx];
                                int constructedRegionX = region >> 8 & 0xff;
                                int constructedRegionY = region & 0xff;
                                client.regionLandIds[idx] = Js5List.maps.getGroupId("m" + constructedRegionX + "_" + constructedRegionY);
                                client.regionLocIds[idx] = Js5List.maps.getGroupId("l" + constructedRegionX + "_" + constructedRegionY);
                            }
                        }
                        int dx = Client.baseX - client.previousAbsoluteX;
                        int dy = Client.baseY - client.previousAbsoluteY;
                        client.previousAbsoluteX = Client.baseX;
                        client.previousAbsoluteY = Client.baseY;
                        for (int index = 0; index < 65536; index++) {
                            Npc npc = client.npcs[index];
                            if (npc != null) {
                                for (int poiint = 0; poiint < 10; poiint++) {
                                    npc.pathX[poiint] -= dx;
                                    npc.pathY[poiint] -= dy;
                                }
                                npc.x -= dx * 128;
                                npc.y -= dy * 128;
                            }
                        }
                        for (int index = 0; index < client.maxPlayers; index++) {
                            Player player = client.players[index];
                            if (player != null) {
                                for (int i31 = 0; i31 < 10; i31++) {
                                    player.pathX[i31] -= dx;
                                    player.pathY[i31] -= dy;
                                }
                                player.x -= dx * 128;
                                player.y -= dy * 128;
                            }
                        }

                        byte startX = 0;
                        byte endX = 104;
                        byte stepX = 1;
                        if (dx < 0) {
                            startX = 103;
                            endX = -1;
                            stepX = -1;
                        }
                        byte startY = 0;
                        byte endY = 104;
                        byte stepY = 1;
                        if (dy < 0) {
                            startY = 103;
                            endY = -1;
                            stepY = -1;
                        }
                        for (int x = startX; x != endX; x += stepX) {
                            for (int y = startY; y != endY; y += stepY) {
                                int shiftedX = x + dx;
                                int shiftedY = y + dy;
                                for (int plane = 0; plane < 4; plane++)
                                    if (shiftedX >= 0 && shiftedY >= 0 && shiftedX < 104 && shiftedY < 104)
                                        client.groundItems[plane][x][y] = client.groundItems[plane][shiftedX][shiftedY];
                                    else
                                        client.groundItems[plane][x][y] = null;
                            }
                        }
                        for (SpawnedObject object = (SpawnedObject) client.spawns
                                .reverseGetFirst(); object != null; object = (SpawnedObject) client.spawns
                                .reverseGetNext()) {
                            object.x -= dx;
                            object.y -= dy;
                            if (object.x < 0 || object.y < 0 || object.x >= 104
                                    || object.y >= 104)
                                object.unlink();
                        }
                        if (client.destX != 0) {
                            client.destX -= dx;
                            client.destY -= dy;
                        }
                        StaticSound.resetSoundCount();
                        client.isCameraLocked = false;
                        client.oculusOrbFocalPointX -= dx << 7;
                        client.oculusOrbFocalPointY -= dy << 7;
                        client.setGameState(GameState.LOADING);
                        client.incomingPacket = -1;
                    return true;

                case 208:
                    int i3 = inStream.readUShort();
                    if (i3 == 65535)
                        i3 = -1; // Changed to unsigned short so need to manually make it -1
                    if (i3 >= 0)
                        client.resetAnimation(i3);
                    client.openWalkableWidgetID = i3;
                    client.incomingPacket = -1;
                    return true;

                case 99:
                    client.minimapState = inStream.readUnsignedByte();
                    client.incomingPacket = -1;
                    return true;

                case 75:
                    int j3 = inStream.method436();
                    int j11 = inStream.method436();
                    RSInterface.interfaceCache[j11].defaultMediaType = 2;
                    RSInterface.interfaceCache[j11].defaultMedia = j3;
                    client.incomingPacket = -1;
                    return true;

                case 114:
                    client.anInt1104 = inStream.method434() * 30;
                    client.broadcastTimer = 0;
                    client.incomingPacket = -1;
                    return true;

                case 60:
                    client.localY = inStream.readUnsignedByte();
                    client.localX = inStream.readNegUByte();
                    while (inStream.currentPosition < client.packetSize) {
                        int k3 = inStream.readUnsignedByte();
                        processScenePacket(inStream, k3);
                    }
                    client.incomingPacket = -1;
                    return true;

                case 35:
                    int quakeDirection = inStream.readUnsignedByte();
                    int quakeMagnitude = inStream.readUnsignedByte();
                    int quakeAmplitude = inStream.readUnsignedByte();
                    int fourPiOverPeriod = inStream.readUnsignedByte();
                    client.quakeDirectionActive[quakeDirection] = true;
                    client.quakeMagnitude[quakeDirection] = quakeMagnitude;
                    client.quakeAmplitude[quakeDirection] = quakeAmplitude;
                    client.fourPiOverPeriod[quakeDirection] = fourPiOverPeriod;
                    client.cameraUpdateCounters[quakeDirection] = 0;
                    client.incomingPacket = -1;
                    return true;

                case 174:
                    int id = inStream.readUShort();
                    int type = inStream.readUnsignedByte();
                    int delay = inStream.readUShort();
                    StaticSound.queueSoundEffect(id, type, delay);
                    client.incomingPacket = -1;
                    return true;

                case 104:
                    int j4 = inStream.readNegUByte();
                    int i12 = inStream.method426();
                    String s6 = inStream.readString();
                    if (j4 >= 1 && j4 <= client.playerOptions.length) {
                        if (s6.equalsIgnoreCase("null"))
                            s6 = null;
                        client.playerOptions[j4 - 1] = s6;
                        client.playerOptionsHighPriority[j4 - 1] = i12 == 0;
                    }
                    client.incomingPacket = -1;
                    return true;

                case 78:
                    client.destX = 0;
                    client.incomingPacket = -1;
                    return true;

                case 253:
                    String s = inStream.readString();
                    if (s.equals(":prayertrue:")) {
                        client.prayClicked = true;
                    } else if (s.equals(":prayerfalse:")) {
                        client.prayClicked = false;
                    } else if (s.equals(":spin")) {
                        client.beginMysteryBoxSpin();
                        client.spin();
                        client.incomingPacket = -1;
                        return true;
                    } else if (s.equals(":resetpost:")) {
                        RSInterface listingWidget = RSInterface.interfaceCache[48020];
                        if (listingWidget != null) {
                            listingWidget.scrollPosition = 0;
                        }
                    }
                    else if (s.equals(":resetBox")) {
                        client.reset();
                        client.incomingPacket = -1;
                        return true;
                    } else if (s.endsWith(":gamblereq:")) {
                        String name = s.substring(0, s.indexOf(":"));
                        long nameToLongFormat = StringUtils.longForName(name);
                        boolean isIgnored = false;
                        for (int index = 0; index < client.ignoreCount; index++) {
                            if (client.ignoreListAsLongs[index] != nameToLongFormat)
                                continue;
                            isIgnored = true;
                        }
                        if (!isIgnored && client.anInt1251 == 0)
                            client.pushMessage("wishes to Gamble with you.", 31, name);
                    } else if (s.endsWith(":tradereq:")) {
                        String s3 = s.substring(0, s.indexOf(":"));
                        long l17 = StringUtils.longForName(s3);
                        boolean flag2 = false;
                        for (int j27 = 0; j27 < client.ignoreCount; j27++) {
                            if (client.ignoreListAsLongs[j27] != l17)
                                continue;
                            flag2 = true;

                        }
                        if (!flag2 && client.anInt1251 == 0)
                            client.pushMessage("wishes to trade with you.", 4, s3);
                    } else if (s.startsWith("//")) {
                        s = s.replaceAll("//", "");
                        client.pushMessage(s, 13, "");
                    } else if (s.startsWith("/")) {
                        s = s.replaceFirst("/", "");
                        client.pushMessage(s, 11, "");
                    } else if (s.endsWith("#url#")) {
                        String link = s.substring(0, s.indexOf("#"));
                        client.pushMessage("Join us at: ", 9, link);
                    } else if (s.endsWith(":duelreq:")) {
                        String s4 = s.substring(0, s.indexOf(":"));
                        long l18 = StringUtils.longForName(s4);
                        boolean flag3 = false;
                        for (int k27 = 0; k27 < client.ignoreCount; k27++) {
                            if (client.ignoreListAsLongs[k27] != l18)
                                continue;
                            flag3 = true;

                        }
                        if (!flag3 && client.anInt1251 == 0)
                            client.pushMessage("wishes to duel with you.", 8, s4);
                    } else if (s.endsWith(":chalreq:")) {
                        String s5 = s.substring(0, s.indexOf(":"));
                        long l19 = StringUtils.longForName(s5);
                        boolean flag4 = false;
                        for (int l27 = 0; l27 < client.ignoreCount; l27++) {
                            if (client.ignoreListAsLongs[l27] != l19)
                                continue;
                            flag4 = true;

                        }
                        if (!flag4 && client.anInt1251 == 0) {
                            String s8 = s.substring(s.indexOf(":") + 1, s.length() - 9);
                            client.pushMessage(s8, 8, s5);
                        }
                    } else {
                        client.pushMessage(s, 0, "");
                    }
                    client.incomingPacket = -1;
                    return true;

                case 1:
                    for (int k4 = 0; k4 < client.players.length; k4++)
                        if (client.players[k4] != null)
                            client.players[k4].sequence = -1;
                    for (int j12 = 0; j12 < client.npcs.length; j12++)
                        if (client.npcs[j12] != null)
                            client.npcs[j12].sequence = -1;
                    client.incomingPacket = -1;
                    return true;

                case 50:
                    long l4 = inStream.readQWord();
                    int i18 = inStream.readUnsignedByte();
                    for (int k24 = 0; k24 < client.friendsCount; k24++) {
                        if (l4 != client.friendsListAsLongs[k24])
                            continue;
                        if (client.friendsNodeIDs[k24] != i18) {
                            client.friendsNodeIDs[k24] = i18;
                            Client.needDrawTabArea = true;
                            if (i18 > 0)
                                client.pushMessage(client.friendsList[k24] + " has logged in.", 5, "");
                            if (i18 == 0)
                                client.pushMessage(client.friendsList[k24] + " has logged out.", 5, "");
                        }
                        break;
                    }
                    client.sortFriendsList();
                    client.incomingPacket = -1;
                    return true;

                case 18: // Add friend or ignore
                    String displayName = inStream.readNullTerminatedString();
                    long displayNameLong = StringUtils.longForName(displayName.toLowerCase());
                    boolean friend = inStream.readSignedByte() == 1;
                    int world = friend ? inStream.readUnsignedByte() : 0;

                    if (friend) {
                        client.friendsList[client.friendsCount] = displayName;
                        client.friendsListAsLongs[client.friendsCount] = displayNameLong;
                        client.friendsNodeIDs[client.friendsCount] = world;
                        client.friendsCount++;
                    } else {
                        client.ignoreListAsLongs[client.ignoreCount++] = displayNameLong;
                    }

                    client.sortFriendsList();
                    Client.needDrawTabArea = true;
                    client.incomingPacket = -1;
                    return true;

                case 19: // Friend updates display name
                    String oldDisplayName = inStream.readNullTerminatedString();
                    String newDisplayName = inStream.readNullTerminatedString();
                    long oldDisplayNameLong = StringUtils.longForName(oldDisplayName.toLowerCase());
                    long newDisplayNameLong = StringUtils.longForName(newDisplayName.toLowerCase());

                    for (int i22 = 0; i22 < client.friendsList.length; i22++) {
                        if (client.friendsList[i22] == null)
                            continue;
                        if (client.friendsListAsLongs[i22] == oldDisplayNameLong) {
                            client.friendsList[i22] = newDisplayName;
                            client.friendsListAsLongs[i22] = newDisplayNameLong;
                            Client.needDrawTabArea = true;
                            client.pushMessage(String.format("Friend '%s' updated display name to '%s'.", oldDisplayName, newDisplayName));
                        }
                    }

                    client.sortFriendsList();
                    client.incomingPacket = -1;
                    return true;

                case 110:
                    if (Client.tabID == 12)
                        Client.needDrawTabArea = true;
                    client.energy = inStream.readUnsignedByte();
                    client.incomingPacket = -1;
                    return true;

                case 254:
                    client.hintArrowType = inStream.readUnsignedByte();
                    if (client.hintArrowType == 1)
                        client.hintArrowNpcIndex = inStream.readUShort();
                    /**
                     * For static icons
                     */
                    if (client.hintArrowType >= 2 && client.hintArrowType <= 6) {
                        if (client.hintArrowType == 2) {
                            client.anInt937 = 64;
                            client.anInt938 = 64;
                        }
                        if (client.hintArrowType == 3) {
                            client.anInt937 = 0;
                            client.anInt938 = 64;
                        }
                        if (client.hintArrowType == 4) {
                            client.anInt937 = 128;
                            client.anInt938 = 64;
                        }
                        if (client.hintArrowType == 5) {
                            client.anInt937 = 64;
                            client.anInt938 = 0;
                        }
                        if (client.hintArrowType == 6) {
                            client.anInt937 = 64;
                            client.anInt938 = 128;
                        }
                        client.hintArrowType = 2;
                        client.hintIconX = inStream.readUShort();
                        client.hintIconYpos = inStream.readUShort();
                        client.hintIconFloorPos = inStream.readUnsignedByte();
                    }
                    if (client.hintArrowType == 10)
                        client.hintArrowPlayerIndex = inStream.readUShort();
                    client.incomingPacket = -1;
                    return true;

                case 248:
                    int i5 = inStream.readUShortA();
                    int k12 = inStream.readUShort();
                    if (Client.backDialogID != -1) {
                        Client.backDialogID = -1;
                        Client.inputTaken = true;
                    }
                    if (Client.inputDialogState != 0) {
                        Client.inputDialogState = 0;
                        Client.inputTaken = true;
                    }
                    if (i5 == 55000) {
                        RSInterface.interfaceCache[55010].scrollPosition = 0;
                        RSInterface.interfaceCache[55050].scrollPosition = 0;
                    }
                    Client.openInterfaceID = i5;
                    client.invOverlayInterfaceID = k12;
                    Client.needDrawTabArea = true;
                    Client.tabAreaAltered = true;
                    client.continuedDialogue = false;
                    client.incomingPacket = -1;
                    return true;

                case 79:
                    int j5 = inStream.method434();
                    int l12 = inStream.readUShortA();
                    RSInterface class9_3 = RSInterface.interfaceCache[j5];
                    if (class9_3 != null && class9_3.type == 0) {
                        if (l12 < 0)
                            l12 = 0;
                        if (l12 > class9_3.scrollMax - class9_3.height)
                            l12 = class9_3.scrollMax - class9_3.height;
                        class9_3.scrollPosition = l12;
                    }
                    client.incomingPacket = -1;
                    return true;

                case 68:
                    for (int k5 = 0; k5 < client.variousSettings.length; k5++)
                        if (client.variousSettings[k5] != client.anIntArray1045[k5]) {
                            client.variousSettings[k5] = client.anIntArray1045[k5];
                            client.updateVarp(k5);
                            Client.needDrawTabArea = true;
                        }
                    client.incomingPacket = -1;
                    return true;

                case 196:
                    String l5 = inStream.readNullTerminatedString();
                    long l5Long = StringUtils.longForName(l5.toLowerCase());
                    inStream.readDWord();
                    Pair<Integer, PlayerRights[]> rights = PlayerRights.readRightsFromPacket(inStream);
                    boolean flag5 = false;
                    if (!PlayerRights.hasRightsBetween(rights.getRight(), 1, 4)) {
                        for (int l29 = 0; l29 < client.ignoreCount; l29++) {
                            if (client.ignoreListAsLongs[l29] != l5Long)
                                continue;
                            flag5 = true;

                        }
                    }
                    if (!flag5 && client.anInt1251 == 0)
                        try {
                            String s9 = TextInput.method525(client.packetSize - 4 - (l5.length() + 1) - rights.getLeft() - 1, inStream);
                            String rightsString = PlayerRights.buildCrownString(rights.getRight());
                            client.pushMessage(s9, 7, rightsString + l5);
                            if (client.preferences().isPmNotificationsEnabled() && !Client.appFrame.isFocused()) {
                                Notify.create()
                                        .title(Configuration.CLIENT_TITLE + " private message from " + l5)
                                        .text(s9)
                                        .position(Pos.BOTTOM_RIGHT)
                                        .onAction( new ActionHandler<Notify>() {
                                            @Override
                                            public void handle(Notify value) {
                                                client.pmTabToReply(l5);
                                            }
                                        })
                                        .hideAfter(5000)
                                        .shake(250, 5)
                                        .darkStyle()
                                        .showConfirm();
                            }
                        } catch (Exception exception1) {
                            exception1.printStackTrace();
                            Signlink.reporterror("cde1");
                        }
                    client.incomingPacket = -1;
                    return true;

                case 85:
                    client.localY = inStream.readNegUByte();
                    client.localX = inStream.readNegUByte();
                    client.incomingPacket = -1;
                    return true;

                case 24:
                    Client.tabID = inStream.readUnsignedByte();
                    client.incomingPacket = -1;
                    return true;

                case 246:
                    int i6 = inStream.method434();
                    int i13 = inStream.readUShort();
                    int k18 = inStream.readUShort();
                    if (k18 == 65535) {
                        RSInterface.interfaceCache[i6].defaultMediaType = 0;
                        client.incomingPacket = -1;
                        return true;
                    } else {
                        ItemDefinition itemDef = ItemDefinition.lookup(k18);
                        RSInterface.interfaceCache[i6].defaultMediaType = 4;
                        RSInterface.interfaceCache[i6].defaultMedia = k18;
                        RSInterface.interfaceCache[i6].modelRotation1 = itemDef.spriteYRotation;
                        RSInterface.interfaceCache[i6].modelRotation2 = itemDef.spriteZRotation;
                        RSInterface.interfaceCache[i6].modelZoom = (itemDef.spriteScale * 100) / i13;
                        client.incomingPacket = -1;
                        return true;
                    }

                case 171:
                    boolean flag1 = inStream.readUnsignedByte() == 1;
                    int j13 = inStream.readUShort();
                    if (RSInterface.interfaceCache[j13] != null) {
                        RSInterface.interfaceCache[j13].isMouseoverTriggered = flag1;
                        RSInterface.interfaceCache[j13].interfaceHidden = flag1;
                    }
                    client.incomingPacket = -1;
                    return true;

                case 142:
                    int j6 = inStream.readUShort();
                    if (j6 != 0) {
                        client.resetAnimation(j6);
                    }
                    if (Client.backDialogID != -1) {
                        Client.backDialogID = -1;
                        Client.inputTaken = true;
                    }
                    if (Client.inputDialogState != 0) {
                        Client.inputDialogState = 0;
                        Client.inputTaken = true;
                    }
                    client.invOverlayInterfaceID = j6;
                    Client.needDrawTabArea = true;
                    Client.tabAreaAltered = true;
                    Client.openInterfaceID = -1;
                    client.continuedDialogue = false;
                    client.incomingPacket = -1;
                    return true;

                case 126:
                    try {
                        text = inStream.readString();
                        int frame = inStream.readUShortA();
                        if (isTeleportListLine(frame)) {
                            text = sanitizeTeleportListLine(text);
                        }
                        if ((frame >= 1675 && frame <= 1687) || (frame >= 17508 && frame <= 17519)) {
                            text = text
                                    .replace("\r", "")
                                    .replace("\n", "")
                                    .replace("\\n", "")
                                    .replace("<br>", "")
                                    .replace("<br/>", "")
                                    .replace("<br />", "");
                            text = text.replaceAll("[\\x00-\\x1F\\x7F]", "");
                        }

                        client.handleScrollPosition(text, frame);
                        Client.handleRunePouch(text, frame);

                        if (text.startsWith("www.") || text.startsWith("http")) {
                            client.launchURL(text);
                            client.incomingPacket = -1;
                            return true;
                        }
                        if (text.startsWith(":pollHeight")) {
                            int rows = Integer.parseInt(text.split("-")[1]);
                            RSInterface rsi = RSInterface.interfaceCache[21406];
                            rsi.childY[0] = (rows * 14);
                            client.incomingPacket = -1;
                            return true;
                        }
                        if (text.startsWith(":pollOn")) {
                            String option[] = text.split("-");
                            client.pollActive = Boolean.parseBoolean(option[1]);
                            client.incomingPacket = -1;
                            return true;
                        }
                        client.updateStrings(text, frame);
                        client.sendFrame126(text, frame);
                        if (frame >= 18144 && frame <= 18244) {
                            client.clanList[frame - 18144] = text;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    client.incomingPacket = -1;
                    return true;

                case 170:
                    try {
                        text = inStream.readString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    client.incomingPacket = -1;
                    return true;

                case 179: {
                    try {
                        int broadcastType = inStream.readUnsignedByte();

                        int broadcastIndex = inStream.readUnsignedByte();

                        if (broadcastType == -1) {
                            BroadcastManager.removeIndex(broadcastIndex);
                            return true;
                        }

                        Broadcast broadcast = new Broadcast();

                        broadcast.type = broadcastType;

                        broadcast.index = broadcastIndex;

                        broadcast.message = inStream.readString();

                        if (broadcastType == 1) {
                            broadcast.url = inStream.readString();
                        } else if (broadcastType == 2) {
                            broadcast.x = inStream.readDWord();
                            broadcast.y = inStream.readDWord();
                            broadcast.z = inStream.readUnsignedByte();
                        }
                        broadcast.setExpireDelay();
                        BroadcastManager.addBoradcast(broadcast);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    client.incomingPacket = -1;
                    return true;
                }

                case 206:
                    client.publicChatMode = inStream.readUnsignedByte();
                    client.privateChatMode = inStream.readUnsignedByte();
                    client.tradeMode = inStream.readUnsignedByte();
                    Client.inputTaken = true;
                    client.incomingPacket = -1;
                    return true;
                case 204:
                    client.specialAttack = inStream.readSignedByte();
                    client.specialEnabled = inStream.readSignedByte();
                    client.incomingPacket = -1;
                    return true;
                case 240:
                    if (Client.tabID == 12)
                        Client.needDrawTabArea = true;
                    client.weight = inStream.readSignedWord();
                    client.incomingPacket = -1;
                    return true;

                case 8:
                    int k6 = inStream.method436();
                    int l13 = inStream.readUShort();
                    RSInterface.interfaceCache[k6].defaultMediaType = 1;
                    RSInterface.interfaceCache[k6].defaultMedia = l13;
                    client.incomingPacket = -1;
                    return true;

                case 122:
                    int l6 = inStream.method436();
                    int i14 = inStream.method436();
                    int i19 = i14 >> 10 & 0x1f;
                    int i22 = i14 >> 5 & 0x1f;
                    int l24 = i14 & 0x1f;
                    RSInterface.interfaceCache[l6].textColor = (i19 << 19) + (i22 << 11) + (l24 << 3);
                    client.incomingPacket = -1;
                    return true;

                case 53:
                    Client.needDrawTabArea = true;
                    int i7 = inStream.readUShort();
                    RSInterface widget = RSInterface.interfaceCache[i7];
                    int j19 = inStream.readUShort();

                    try {
                        HashMap<Integer, net.runelite.api.Item> items = new HashMap<>();

                        for (int j22 = 0; j22 < j19; j22++) {
                            int i25 = inStream.readUnsignedByte();
                            if (i25 == 255) {
                                i25 = inStream.method440();
                            }
                            widget.inventoryItemId[j22] = inStream.method436();
                            widget.inventoryAmounts[j22] = i25;
                            items.put(j22,new net.runelite.api.Item(widget.inventoryItemId[j22] == -1 ? -1 : widget.inventoryItemId[j22] - 1, widget.inventoryAmounts[j22]));
                        }
                        for (int j25 = j19; j25 < widget.inventoryItemId.length; j25++) {
                            widget.inventoryItemId[j25] = 0;
                            widget.inventoryAmounts[j25] = 0;
                        }
                        ItemContainer itemContainer = new ItemContainer() {

                            @Override
                            public int getId() {
                                return i7;
                            }

                            @NotNull
                            @Override
                            public net.runelite.api.Item[] getItems() {
                                net.runelite.api.Item[] list = new net.runelite.api.Item[j19];
                                for (Map.Entry<Integer, net.runelite.api.Item> item : items.entrySet()) {
                                    list[item.getKey()] = item.getValue();
                                }
                                return list;
                            }

                            @Nullable
                            @Override
                            public net.runelite.api.Item getItem(int slot) {
                                return items.get(slot);
                            }

                            @Override
                            public boolean contains(int itemId2) {
                                return items.values().stream().anyMatch(list -> list.getId() == itemId2);
                            }

                            @Override
                            public int count(int itemId2) {
                                return items.values().stream().filter(list -> list.getId() == itemId2).findFirst().get().getQuantity();
                            }

                            @Override
                            public int size() {
                                return j19;
                            }

                            @Override
                            public Node getNext() {
                                return null;
                            }

                            @Override
                            public Node getPrevious() {
                                return null;
                            }

                            @Override
                            public void unlink() {

                            }

                            @Override
                            public long getHash() {
                                return 0;
                            }


                        };


                        client.containers.put(i7, itemContainer);
                        ItemContainerChanged item = new ItemContainerChanged(i7, itemContainer);
                        client.getCallbacks().post(item);
                    } catch (Exception e) {
                        int actualLength = widget != null && widget.inventoryItemId != null ? widget.inventoryItemId.length : -1;
                        System.err.println("Error in container " + i7 + ", length " + j19 + ", actual length " + actualLength);
                        e.printStackTrace();
                    }
                    client.incomingPacket = -1;
                    return true;

                case 230:
                    int j7 = inStream.readUShortA();
                    int j14 = inStream.readUShort();
                    int k19 = inStream.readUShort();
                    int k22 = inStream.method436();
                    RSInterface.interfaceCache[j14].modelRotation1 = k19;
                    RSInterface.interfaceCache[j14].modelRotation2 = k22;
                    RSInterface.interfaceCache[j14].modelZoom = j7;
                    client.incomingPacket = -1;
                    return true;

                case 221:
                    client.anInt900 = inStream.readUnsignedByte();
                    Client.needDrawTabArea = true;
                    client.incomingPacket = -1;
                    return true;

                case 177:
                    client.isCameraLocked = true;
                    Camera.isCameraUpdating = false;
                    Camera.staticCameraMode = false;
                    Camera.staticCameraX = inStream.readUnsignedByte();
                    Camera.staticCameraY = inStream.readUnsignedByte();
                    Camera.staticCameraAltitudeOffset = inStream.readUShort();
                    Camera.cameraPitchStep = inStream.readUnsignedByte();
                    Camera.cameraYawSpeed = inStream.readUnsignedByte();
                    if (Camera.cameraYawSpeed >= 100) {
                        int var20 = Camera.staticCameraX * 16384 + 64;
                        int var5 = Camera.staticCameraY * 16384 + 64;
                        int var22 = client.getCenterHeight(client.plane, var5, var20) - Camera.staticCameraAltitudeOffset;
                        int var7 = var20 - client.cameraX;
                        int var8 = var22 - client.cameraY;
                        int var24 = var5 - client.cameraZ;
                        int var10 = (int)Math.sqrt((double)(var24 * var24 + var7 * var7));
                        client.cameraPitch = (int)(Math.atan2((double)var8, (double)var10) * 325.9490051269531D) & 2047;
                        client.cameraYaw = (int)(Math.atan2((double)var7, (double)var24) * -325.9490051269531D) & 2047;
                        if (client.cameraPitch < 128) {
                            client.cameraPitch = 128;
                        }

                        if (client.cameraPitch > 383) {
                            client.cameraPitch = 383;
                        }
                        Client.onCameraPitchChanged(0);
                    }
                    client.incomingPacket = -1;
                    return true;

                case 249:
                    client.anInt1046 = inStream.method426();
                    client.localPlayerIndex = inStream.method436();
                    client.incomingPacket = -1;
                    return true;

                case 65:
                    client.updateNPCs(inStream, client.packetSize);
                    client.incomingPacket = -1;
                    return true;

                case 27:
                    client.enterInputInChatString = inStream.readString();
                    client.messagePromptRaised = false;
                    Client.inputDialogState = 1;
                    client.amountOrNameInput = "";
                    Client.inputTaken = true;
                    client.incomingPacket = -1;
                    return true;

                case 28:
                    client.messagePromptRaised = false;
                    Client.inputDialogState = 1;
                    client.amountOrNameInput = "";
                    Client.inputTaken = true;
                    client.incomingPacket = -1;
                    return true;

                case 187:
                    client.enterInputInChatString = inStream.readString();
                    client.messagePromptRaised = false;
                    Client.inputDialogState = 2;
                    client.amountOrNameInput = "";
                    Client.inputTaken = true;
                    client.incomingPacket = -1;
                    return true;

                case 191:
                    client.messagePromptRaised = false;
                    Client.inputDialogState = 7;
                    client.amountOrNameInput = "";
                    Client.inputTaken = true;
                    client.incomingPacket = -1;
                    return true;

                case 192:
                    client.messagePromptRaised = false;
                    Client.inputDialogState = 8;
                    client.amountOrNameInput = "";
                    Client.inputTaken = true;
                    client.incomingPacket = -1;
                    return true;

                case 98:
                    int componentID = inStream.readUShort();
                    int normal = inStream.readUShort();
                    int hover = inStream.readUShort();
                    if (RSInterface.interfaceCache[componentID] != null) {
                        RSInterface.interfaceCache[componentID].sprite1 = ImageCache.get(normal);
                        RSInterface.interfaceCache[componentID].sprite2 = ImageCache.get(hover);
                    }
                    client.incomingPacket = -1;
                    return true;

                case 100:
                    int componentIDD = inStream.readUShort();
                    int statee = inStream.readByte();
                    if (RSInterface.interfaceCache[componentIDD] != null) {
                        RSInterface.interfaceCache[componentIDD].clickingDisabled = statee == 1 ? true : false;
                    }
                    client.incomingPacket = -1;
                    return true;



                case 102:
                    int scene = inStream.readByte();
                    int id1 = inStream.readUShort();
                    String[] text1 = inStream.readString().split("&", 6);

                    String[] temp_options = new String[] { null, null, null, null, null };
                    for (int i2 = 0; i2 < text1.length; i2++) {
                        temp_options[i2] = text1[i2];
                    }

                    if(scene == 0) {
                        ObjectDefinition.lookup(id1).actions = temp_options;
                    } else if(scene == 1) {
                        NpcDefinition.get(id1).actions = temp_options;
                    }
                    client.incomingPacket = -1;
                    return true;

                case 97:
                    int interfaceId = inStream.readUShort();
                    if (interfaceId == Client.INTERFACE_ID) {
                        ensureMysteryInterfaceDefinition();
                    }
                    client.resetAnimation(interfaceId);
                    if (client.invOverlayInterfaceID != 0) {
                        client.invOverlayInterfaceID = 0;
                        Client.needDrawTabArea = true;
                        Client.tabAreaAltered = true;
                    }
                    if (Client.backDialogID != -1) {
                        Client.backDialogID = -1;
                        Client.inputTaken = true;
                    }
                    if (Client.inputDialogState != 0) {
                        Client.inputDialogState = 0;
                        Client.inputTaken = true;
                    }

                    if (interfaceId == 15244) {
                        Client.openInterfaceID = 15767;
                        client.fullscreenInterfaceID = 15244;
                    }

                    if (interfaceId == 44900) {
                        Client.openInterfaceID = 44900;
                        client.fullscreenInterfaceID = 44900;
                    }




                    Client.openInterfaceID = interfaceId;
                    client.continuedDialogue = false;
                    client.incomingPacket = -1;
                    return true;

                case 15:
                    client.autocast = inStream.readUnsignedByte() == 1 ? true : false;
                    client.incomingPacket = -1;
                    return true;

                case 218:
                    int i8 = inStream.method438();
                    client.dialogID = i8;
                    Client.inputTaken = true;
                    client.incomingPacket = -1;
                    return true;

                case 87:
                    int j8 = inStream.method434();
                    int l14 = inStream.method439();
                    client.anIntArray1045[j8] = l14;
                    Bank.onConfigChanged(j8, l14);
                    GroupIronmanBank.onConfigChanged(j8, l14);
                    Nightmare.instance.handleConfig(j8, l14);
                    Autocast.getSingleton().onConfigChanged(j8, l14);
                    DailyRewards.get().onConfigReceived(j8, l14);
                    DonatorRewards.getInstance().onConfigChanged(j8, l14);
                    if (client.variousSettings[j8] != l14) {
                        QuestTab.onConfigChanged(j8, l14);
                        MonsterDropViewer.onConfigChanged(j8, l14);
                        client.variousSettings[j8] = l14;
                        client.updateVarp(j8);
                        Client.needDrawTabArea = true;
                        if (client.dialogID != -1)
                            Client.inputTaken = true;
                    }
                    client.incomingPacket = -1;
                    return true;

                case 36:
                    int k8 = inStream.method434();
                    byte byte0 = inStream.readSignedByte();
                    client.anIntArray1045[k8] = byte0;
                    Bank.onConfigChanged(k8, byte0);
                    GroupIronmanBank.onConfigChanged(k8, byte0);
                    EventCalendar.getCalendar().onConfigReceived(k8, byte0);
                    Nightmare.instance.handleConfig(k8, byte0);
                    Autocast.getSingleton().onConfigChanged(k8, byte0);
                    DailyRewards.get().onConfigReceived(k8, byte0);
                    DonatorRewards.getInstance().onConfigChanged(k8, byte0);
                    if (client.variousSettings[k8] != byte0) {
                        QuestTab.onConfigChanged(k8, byte0);
                        MonsterDropViewer.onConfigChanged(k8, byte0);
                        client.variousSettings[k8] = byte0;
                        client.updateVarp(k8);
                        Client.needDrawTabArea = true;
                        if (client.dialogID != -1)
                            Client.inputTaken = true;
                    }
                    client.incomingPacket = -1;
                    return true;

                case 61:
                    client.anInt1055 = inStream.readUnsignedByte();
                    client.incomingPacket = -1;
                    return true;

                case 197:
                    String message = inStream.readString();
                    int npcIndex = inStream.readDWord();
                    NPCDropInfo info = new NPCDropInfo();
                    info.message = message;
                    info.npcIndex = npcIndex;
                    NPCDropInfo.addEntry(info);
                    System.err.println(" "+message+" - "+npcIndex);
                    client.pushMessage(message, 99, "");
                    client.incomingPacket = -1;
                    return true;

                case PacketIdentifiers.SEND_ANIMATE_CHAT_INTERFACE:
                    int l8 = inStream.readUShort();
                    int i15 = inStream.readSignedWord();
                    RSInterface class9_4 = RSInterface.interfaceCache[l8];
                    class9_4.disabledAnimationId = i15;
                    if (i15 == 591 || i15 == 588) {
                        class9_4.modelZoom = 900; // anInt269
                    }
                    if (i15 == -1) {
                        class9_4.currentFrame = 0;
                        class9_4.anInt208 = 0;
                    }
                    client.incomingPacket = -1;
                    return true;

                case PacketIdentifiers.CLOSE_INTERFACE:
                    client.closeInterface();
                    client.incomingPacket = -1;
                    return true;

                case 34:
                Client.needDrawTabArea = true;
                int i9 = inStream.readUShort();
                if (handledPacket34(i9)) {
                    client.incomingPacket = -1;
                    return true;
                }

                RSInterface class9_2 = RSInterface.interfaceCache[i9];
                while (inStream.currentPosition < client.packetSize) {
                    int j20 = (i9 == 23231 ? inStream.readUnsignedByte() : inStream.readDWord());
                    int i23 = inStream.readUShort(); //Item ID
                    int l25 = inStream.readUnsignedByte(); // Amount
                    if (l25 == 255)
                        l25 = inStream.readDWord();
                    if (j20 >= 0 && j20 < class9_2.inventoryItemId.length) {
                        class9_2.inventoryItemId[j20] = i23;
                        class9_2.inventoryAmounts[j20] = l25;
                    }
                    System.out.println(i9 + " / " + i23 + " / " + l25  + " / " + j20) ;
                }
                client.incomingPacket = -1;
                return true;

                case 4:
                case 44:
                case 84:
                case 101:
                case 105:
                case 117:
                case 147:
                case 151:
                case 156:
                case 160:
                case 215:
                    processScenePacket(inStream, client.incomingPacket);
                    client.incomingPacket = -1;
                    return true;

                case 106:
                    Client.tabID = inStream.readNegUByte();
                    Client.needDrawTabArea = true;
                    Client.tabAreaAltered = true;
                    client.incomingPacket = -1;
                    return true;

                case 164:
                    int j9 = inStream.method434();
                    client.resetAnimation(j9);
                    if (client.invOverlayInterfaceID != 0) {
                        client.invOverlayInterfaceID = 0;
                        Client.needDrawTabArea = true;
                        Client.tabAreaAltered = true;
                    }
                    Client.backDialogID = j9;
                    Client.inputTaken = true;
                    Client.openInterfaceID = -1;
                    client.continuedDialogue = false;
                    client.incomingPacket = -1;
                    return true;

            }

            Signlink.reporterror("T1 - Packet: " + client.incomingPacket + ", Packet Size: " + client.packetSize
                    + " - Previous packet: " + client.previousPacket1 + " Previous packet size: " + client.previousPacketSize1
                    + ", 2nd Previous packet: " + client.previousPacket2 + ", 2nd Previous packet size: "
                    + client.previousPacketSize2);
            PacketLog.log();
            client.resetLogout();
        } catch (IOException _ex) {
            PacketLog.log();
            _ex.printStackTrace();
            client.dropClient();
        } catch (Exception exception) {
            PacketLog.log();
            exception.printStackTrace();
            String s2 = "T2 - " + client.incomingPacket + "," + client.previousPacket1 + "," + client.previousPacket2 + " - " + client.packetSize
                    + "," + (Client.baseX + Client.localPlayer.pathX[0]) + "," + (Client.baseY + Client.localPlayer.pathY[0]) + " - ";
            for (int j15 = 0; j15 < client.packetSize && j15 < 50; j15++)
                s2 = s2 + client.inStream.payload[j15] + ",";
            Signlink.reporterror(s2);
            client.resetLogout();
        }
        return true;
    }

    /**
     * Processes scene-related packets (ground items, objects, animations, projectiles, sounds).
     * Formerly known as method137 in Client.java.
     */
    private void processScenePacket(Buffer stream, int j) {
        if (j == 84) {
            int k = stream.readUnsignedByte();
            int j3 = client.localX + (k >> 4 & 7);
            int i6 = client.localY + (k & 7);
            int l8 = stream.readUShort();
            int k11 = stream.readUShort();
            int l13 = stream.readUShort();// edit
            if (j3 >= 0 && i6 >= 0 && j3 < 104 && i6 < 104) {
                NodeDeque class19_1 = client.groundItems[client.plane][j3][i6];
                if (class19_1 != null) {
                    for (TileItem class30_sub2_sub4_sub2_3 = (TileItem) class19_1
                            .reverseGetFirst(); class30_sub2_sub4_sub2_3 != null; class30_sub2_sub4_sub2_3 = (TileItem) class19_1
                            .reverseGetNext()) {
                        if (class30_sub2_sub4_sub2_3.id != (l8 & 0x7fff) || class30_sub2_sub4_sub2_3.quantity != k11)
                            continue;
                        class30_sub2_sub4_sub2_3.quantity = l13;
                        class30_sub2_sub4_sub2_3.quantityChanged(l13);
                        break;
                    }

                    client.updateItemPile(j3, i6);
                }
            }
            return;
        }
        if (j == 105) {
            int l = stream.readUnsignedByte();
            int k3 = client.localX + (l >> 4 & 7);
            int j6 = client.localY + (l & 7);
            int i9 = stream.readUShort();
            int l11 = stream.readUnsignedByte();
            int i14 = l11 >> 4 & 0xf;
            int i16 = l11 & 7;
            if (Client.localPlayer.pathX[0] >= k3 - i14 && Client.localPlayer.pathX[0] <= k3 + i14 && Client.localPlayer.pathY[0] >= j6 - i14
                    && Client.localPlayer.pathY[0] <= j6 + i14 && Client.soundEffectVolume != 0 && client.aBoolean848 && !Client.lowMem
                    && client.soundCount < 50) {
                client.sound[client.soundCount] = i9;
                client.soundType[client.soundCount] = i16;
                client.soundDelay[client.soundCount] = Sounds.anIntArray326[i9];
                client.soundCount++;
            }
        }
        if (j == 215) {
            int i1 = stream.readUShortA();
            int l3 = stream.method428();
            int k6 = client.localX + (l3 >> 4 & 7);
            int j9 = client.localY + (l3 & 7);
            int i12 = stream.readUShortA();
            int j14 = stream.readUShort();
            if (k6 >= 0 && j9 >= 0 && k6 < 104 && j9 < 104 && i12 != client.localPlayerIndex) {
                TileItem class30_sub2_sub4_sub2_2 = new TileItem();
                class30_sub2_sub4_sub2_2.id = i1;
                class30_sub2_sub4_sub2_2.quantity = j14;
                class30_sub2_sub4_sub2_2.quantityChanged(j14);

                if (client.groundItems[client.plane][k6][j9] == null)
                    client.groundItems[client.plane][k6][j9] = new NodeDeque();
                client.groundItems[client.plane][k6][j9].insertHead(class30_sub2_sub4_sub2_2);
                client.updateItemPile(k6, j9);
            }
            return;
        }
        if (j == 156) {
            int j1 = stream.method426();
            int i4 = client.localX + (j1 >> 4 & 7);
            int l6 = client.localY + (j1 & 7);
            int k9 = stream.readUShort();
            if (i4 >= 0 && l6 >= 0 && i4 < 104 && l6 < 104) {
                NodeDeque class19 = client.groundItems[client.plane][i4][l6];
                if (class19 != null) {
                    for (TileItem item = (TileItem) class19.reverseGetFirst(); item != null; item = (TileItem) class19.reverseGetNext()) {
                        if (item.id != k9)
                            continue;
                        item.unlink();
                        ItemDespawned itemDespawned = new ItemDespawned(client.scene.tileArray[client.plane][i4][l6], item);
                        Client.instance.getCallbacks().post(itemDespawned);
                        break;
                    }

                    if (class19.reverseGetFirst() == null) {
                        client.groundItems[client.plane][i4][l6] = null;
                    }
                    client.updateItemPile(i4, l6);
                }
            }
            return;
        }
        if (j == 160) {
            int offset = stream.method428();
            int xLoc = client.localX + (offset >> 4 & 7);
            int yLoc = client.localY + (offset & 7);
            int objectTypeFace = stream.method428();
            int objectType = objectTypeFace >> 2;
            int objectFace = objectTypeFace & 3;
            int objectGenre = client.objectTypes[objectType];
            int animId = stream.readUShortA();
            if (xLoc >= 0 && yLoc >= 0 && xLoc < 103 && yLoc < 103) {
                if (objectGenre == 0) {// WallObject
                    Wall wallObjectObject = client.scene.get_wall(client.plane, xLoc, yLoc);
                    if (wallObjectObject != null) {
                        int objectId = ObjectKeyUtil.getObjectId(wallObjectObject.uid);
                        if (objectType == 2) {
                            wallObjectObject.wall = new SceneObject(objectId, 4 + objectFace, 2, client.plane, xLoc, yLoc, animId, false, wallObjectObject.wall);
                            wallObjectObject.corner = new SceneObject(objectId, objectFace + 1 & 3, 2, client.plane, xLoc, yLoc, animId, false, wallObjectObject.corner);
                        } else {
                            wallObjectObject.wall = new SceneObject(objectId, objectFace, objectType, client.plane, xLoc, yLoc, animId, false, wallObjectObject.wall);
                        }
                    }
                }
                if (objectGenre == 1) { // WallDecoration
                    WallDecoration wallDecoration = client.scene.get_wall_decor(xLoc, yLoc, client.plane);
                    if (wallDecoration != null)
                        wallDecoration.node = new SceneObject(ObjectKeyUtil.getObjectId(wallDecoration.uid), 0, 4, client.plane, xLoc, yLoc, animId, false, wallDecoration.node);
                }
                if (objectGenre == 2) { // TiledObject
                    InteractiveObject tiledObject = client.scene.get_interactive_object(xLoc, yLoc, client.plane);
                    if (objectType == 11)
                        objectType = 10;
                    if (tiledObject != null)
                        tiledObject.renderable = new SceneObject(ObjectKeyUtil.getObjectId(tiledObject.uid), objectFace, objectType, client.plane, xLoc, yLoc, animId, false, tiledObject.renderable);
                }
                if (objectGenre == 3) { // GroundDecoration
                    GroundDecoration groundDecoration = client.scene.get_ground_decor(yLoc, xLoc, client.plane);
                    if (groundDecoration != null)
                        groundDecoration.node = new SceneObject(ObjectKeyUtil.getObjectId(groundDecoration.uid), objectFace, 22, client.plane, xLoc, yLoc, animId, false, groundDecoration.node);
                }
            }
            return;
        }
        if (j == 147) {
            int offset = stream.method428();
            int xLoc = client.localX + (offset >> 4 & 7);
            int yLoc = client.localY + (offset & 7);
            int playerIndex = stream.readUShort();
            byte byte0GreaterXLoc = stream.method430();
            int startDelay = stream.method434();
            byte byte1GreaterYLoc = stream.method429();
            int stopDelay = stream.readUShort();
            int objectTypeFace = stream.method428();
            int objectType = objectTypeFace >> 2;
            int objectFace = objectTypeFace & 3;
            int objectGenre = client.objectTypes[objectType];
            byte byte2LesserXLoc = stream.readSignedByte();
            int objectId = stream.readUShort();
            byte byte3LesserYLoc = stream.method429();
            Player player;
            if (playerIndex == client.localPlayerIndex)
                player = Client.localPlayer;
            else
                player = client.players[playerIndex];
            if (player != null) {
                ObjectDefinition objectDefinition = ObjectDefinition.lookup(objectId);
                int sizeX;
                int sizeY;
                if (objectFace != 1 && objectFace != 3) {
                    sizeX = objectDefinition.sizeX;
                    sizeY = objectDefinition.sizeY;
                } else {
                    sizeX = objectDefinition.sizeY;
                    sizeY = objectDefinition.sizeX;
                }

                int left = xLoc + (sizeX >> 1);
                int right = xLoc + (sizeX + 1 >> 1);
                int top = yLoc + (sizeY >> 1);
                int bottom = yLoc + (sizeY + 1 >> 1);
                int[][] heights = client.tileHeights[client.plane];
                int mean = heights[left][bottom] + heights[right][top] + heights[left][top] + heights[right][bottom] >> 2;
                int middleX = (xLoc << 7) + (sizeX << 6);
                int middleY = (yLoc << 7) + (sizeY << 6);

                Model model = objectDefinition.getModel(objectType, objectFace, heights, middleX, mean, middleY);
                if (model != null) {
                    client.requestSpawnObject(stopDelay + 1, -1, 0, objectGenre, yLoc, 0, client.plane, xLoc, startDelay + 1);
                    player.animationCycleStart = startDelay + Client.loopCycle;
                    player.animationCycleEnd = stopDelay + Client.loopCycle;
                    player.playerModel = model;
                    int playerSizeX = objectDefinition.sizeX;
                    int playerSizeY = objectDefinition.sizeY;
                    if (objectFace == 1 || objectFace == 3) {
                        playerSizeX = objectDefinition.sizeY;
                        playerSizeY = objectDefinition.sizeX;
                    }
                    player.field1117 = xLoc * 128 + playerSizeX * 64;
                    player.field1123 = yLoc * 128 + playerSizeY * 64;
                    player.tileHeight2 = client.getCenterHeight(client.plane, player.field1123, player.field1117);
                    if (byte2LesserXLoc > byte0GreaterXLoc) {
                        byte tmp = byte2LesserXLoc;
                        byte2LesserXLoc = byte0GreaterXLoc;
                        byte0GreaterXLoc = tmp;
                    }
                    if (byte3LesserYLoc > byte1GreaterYLoc) {
                        byte tmp = byte3LesserYLoc;
                        byte3LesserYLoc = byte1GreaterYLoc;
                        byte1GreaterYLoc = tmp;
                    }
                    player.minX = xLoc + byte2LesserXLoc;
                    player.maxX = xLoc + byte0GreaterXLoc;
                    player.minY = yLoc + byte3LesserYLoc;
                    player.maxY = yLoc + byte1GreaterYLoc;
                }
            }
        }
        if (j == 151) {
            int i2 = stream.method426();
            int l4 = client.localX + (i2 >> 4 & 7);
            int k7 = client.localY + (i2 & 7);
            int j10 = stream.method434();
            int k12 = stream.method428();
            int i15 = k12 >> 2;
            int k16 = k12 & 3;
            int l17 = client.objectTypes[i15];
            if (l4 >= 0 && k7 >= 0 && l4 < 104 && k7 < 104)
                client.requestSpawnObject(-1, j10, k16, l17, k7, i15, client.plane, l4, 0);
            return;
        }
        if (j == 4) {
            int j2 = stream.readUnsignedByte();
            int i5 = client.localX + (j2 >> 4 & 7);
            int l7 = client.localY + (j2 & 7);
            int k10 = stream.readUShort();
            int l12 = stream.readUnsignedByte();
            int j15 = stream.readUShort();
            if (i5 >= 0 && l7 >= 0 && i5 < 104 && l7 < 104) {
                i5 = i5 * 128 + 64;
                l7 = l7 * 128 + 64;
                SpotAnimEntity class30_sub2_sub4_sub3 = new SpotAnimEntity(client.plane, Client.loopCycle, j15, k10,
                        client.getCenterHeight(client.plane, l7, i5) - l12, l7, i5);
                client.incompleteAnimables.insertHead(class30_sub2_sub4_sub3);
            }
            return;
        }
        if (j == 44) {
            int k2 = stream.method436();
            int j5 = stream.method439();
            int i8 = stream.readUnsignedByte();
            int l10 = client.localX + (i8 >> 4 & 7);
            int i13 = client.localY + (i8 & 7);
            if (l10 >= 0 && i13 >= 0 && l10 < 104 && i13 < 104) {
                TileItem class30_sub2_sub4_sub2_1 = new TileItem();
                class30_sub2_sub4_sub2_1.id = k2;
                class30_sub2_sub4_sub2_1.quantity = j5;
                class30_sub2_sub4_sub2_1.quantityChanged(j5);
                if (client.groundItems[client.plane][l10][i13] == null)
                    client.groundItems[client.plane][l10][i13] = new NodeDeque();
                client.groundItems[client.plane][l10][i13].insertHead(class30_sub2_sub4_sub2_1);
                client.updateItemPile(l10, i13);
            }
            return;
        }
        if (j == 101) {
            int l2 = stream.readNegUByte();
            int k5 = l2 >> 2;
            int j8 = l2 & 3;
            int i11 = client.objectTypes[k5];
            int j13 = stream.readUnsignedByte();
            int k15 = client.localX + (j13 >> 4 & 7);
            int l16 = client.localY + (j13 & 7);
            if (k15 >= 0 && l16 >= 0 && k15 < 104 && l16 < 104)
                client.requestSpawnObject(-1, -1, j8, i11, l16, k5, client.plane, k15, 0);
            return;
        }
        if (j == 117) {
            int offset = stream.readUnsignedByte();
            int sourceX = client.localX + (offset >> 4 & 7);
            int sourceY = client.localY + (offset & 7);
            int destX = sourceX + stream.readSignedByte();
            int destY = sourceY + stream.readSignedByte();
            int target = stream.readShort3();
            int gfxMoving = stream.readUShort();
            int startHeight = stream.readUnsignedByte() * 4;
            int endHeight = stream.readUnsignedByte() * 4;
            int startDelay = stream.readUShort();
            int speed = stream.readUShort();
            int initialSlope = stream.readUnsignedByte();
            int frontOffset = stream.readUnsignedByte();
            System.out.println("PROJECTILE");
            if (sourceX >= 0 && sourceY >= 0 && sourceX < 104 && sourceY < 104 && destX >= 0 && destY >= 0 && destX < 104 && destY < 104
                    && gfxMoving != 65535) {
                sourceX = sourceX * 128 + 64;
                sourceY = sourceY * 128 + 64;
                destX = destX * 128 + 64;
                destY = destY * 128 + 64;
                Projectile projectile = new Projectile(initialSlope, endHeight, startDelay + Client.loopCycle,
                        speed + Client.loopCycle, frontOffset, client.plane, client.getCenterHeight(client.plane, sourceY, sourceX) - startHeight, sourceY, sourceX,
                        target, gfxMoving);
                projectile.setDestination(destX, destY, client.getCenterHeight(client.plane, destY, destX) - endHeight,
                        startDelay + Client.loopCycle);
                client.projectiles.insertBack(projectile);
            }
        }
    }

    /**
     * Special handler for packet 34 when the spin interface is open.
     */
    private boolean handledPacket34(int frame) {
        if (Client.openInterfaceID != Client.INTERFACE_ID) {
            return false;
        }

        final Buffer inStream = client.inStream;
        if (frame < 0 || frame >= RSInterface.interfaceCache.length) {
            return false;
        }
        RSInterface items = RSInterface.interfaceCache[frame];
        if (items == null || items.inventoryItemId == null || items.inventoryAmounts == null) {
            return false;
        }
        while (inStream.currentPosition < client.packetSize) {
            int slot = inStream.readDWord();
            int itemId2 = inStream.readUShort();
            int amount = inStream.readUnsignedByte();
            if (amount == 255) {
                amount = inStream.readDWord();
            }

            if (slot >= 0 && slot < items.inventoryItemId.length) {
                items.inventoryItemId[slot] = itemId2;
                items.inventoryAmounts[slot] = amount;
            }
        }
        return true;
    }

    private void ensureMysteryInterfaceDefinition() {
        RSInterface mystery = (Client.INTERFACE_ID >= 0 && Client.INTERFACE_ID < RSInterface.interfaceCache.length)
            ? RSInterface.interfaceCache[Client.INTERFACE_ID]
            : null;
        boolean invalid = mystery == null || mystery.type != 0 || mystery.children == null || mystery.children.length == 0;
        if (!invalid) {
            return;
        }
        int[] existingIds = null;
        int[] existingAmounts = null;
        RSInterface existingItems = (47101 >= 0 && 47101 < RSInterface.interfaceCache.length) ? RSInterface.interfaceCache[47101] : null;
        if (existingItems != null && existingItems.inventoryItemId != null && existingItems.inventoryAmounts != null) {
            existingIds = existingItems.inventoryItemId.clone();
            existingAmounts = existingItems.inventoryAmounts.clone();
        }
        try {
            Interfaces.mysteryBox(RSInterface.defaultTextDrawingAreas);
            if (existingIds != null && existingAmounts != null) {
                RSInterface rebuiltItems = RSInterface.interfaceCache[47101];
                if (rebuiltItems != null && rebuiltItems.inventoryItemId != null && rebuiltItems.inventoryAmounts != null) {
                    int copy = Math.min(rebuiltItems.inventoryItemId.length, Math.min(existingIds.length, existingAmounts.length));
                    System.arraycopy(existingIds, 0, rebuiltItems.inventoryItemId, 0, copy);
                    System.arraycopy(existingAmounts, 0, rebuiltItems.inventoryAmounts, 0, copy);
                }
            }
        } catch (Exception ex) {
            return;
        }
    }

    private static boolean isTeleportListLine(int frame) {
        return frame >= 46045 && frame <= 46205 && (frame - 46045) % 4 == 0;
    }

    private static String sanitizeTeleportListLine(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        String normalized = text
                .replace("\r", "")
                .replace("\\n", "\n")
                .replace("<br />", "\n")
                .replace("<br/>", "\n")
                .replace("<br>", "\n");

        String[] lines = normalized.split("\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                return trimmed;
            }
        }
        return "";
    }
}
