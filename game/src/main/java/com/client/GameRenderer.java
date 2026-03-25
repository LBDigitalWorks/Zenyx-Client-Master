package com.client;

import com.client.audio.StaticSound;
import com.client.broadcast.BroadcastManager;
import com.client.camera.Camera;
import com.client.definitions.NpcDefinition;
import com.client.definitions.healthbar.HealthBar;
import com.client.definitions.healthbar.HealthBarDefinition;
import com.client.definitions.healthbar.HealthBarUpdate;
import com.client.definitions.HitSplatDefinition;
import com.client.definitions.ItemDefinition;
import com.client.draw.Rasterizer3D;
import com.client.draw.font.osrs.RSFontOSRS;
import com.client.draw.rasterizer.Clips;
import com.client.entity.model.Model;
import com.client.entity.model.ViewportMouse;
import com.client.engine.impl.MouseHandler;
import com.client.features.ExperienceDrop;
import com.client.features.gametimers.GameTimerHandler;
import com.client.features.settings.Preferences;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.Bank;
import com.client.graphics.interfaces.impl.Nightmare;
import com.client.graphics.interfaces.impl.QuestTab;
import com.client.graphics.interfaces.Configs;
import com.client.graphics.textures.TextureProvider;
import com.client.scene.SceneGraph;
import com.client.util.ColorUtils;
import net.runelite.api.GameState;
import net.runelite.api.events.BeforeRender;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.callback.ClientThread;
import net.runelite.api.events.BeforeMenuRender;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Handles all rendering orchestration for the game client.
 * Extracted from Client.java to improve code organization.
 *
 * Responsible for: frame dispatch, 3D scene setup, minimap, HUD overlays,
 * status orbs, chat area, tab area, split PM chat, XP drops, screen flash,
 * debug overlays, and loading screens.
 */
public class GameRenderer {

    final Client client;

    public GameRenderer(Client client) {
        this.client = client;
    }

    /**
     * Main frame entry point — dispatches rendering by gameState.
     * Called from Client.draw(boolean) override.
     */
    public void draw(boolean redraw) {
        StaticSound.draw();
        client.callbacks.frame();
        client.updateCamera();

        if (client.gameState == 0) {
            client.callDrawInitial(client.loadingPercent, client.loadingText, false);
        } else if (client.gameState == 5) {
            client.drawLoginScreen();
        } else if (client.gameState != 10 && client.gameState != 11) {
            if (client.gameState == 20) {
                client.drawLoginScreen();
            } else if (client.gameState == 50) {
                client.drawLoginScreen();
            } else if (client.gameState == 25) {
                int percentage;
                if (Client.loadingType == 1) {
                    if (Client.mapsLoaded > Client.totalMaps) {
                        Client.totalMaps = Client.mapsLoaded;
                    }
                    percentage = (Client.totalMaps * 50 - Client.mapsLoaded * 50) / Client.totalMaps;
                    drawLoadingMessage("Loading - please wait." + "<br>" + " (" + percentage + "%" + ")");
                } else if (Client.loadingType == 2) {
                    if (Client.objectsLoaded > Client.totalObjects) {
                        Client.totalObjects = Client.objectsLoaded;
                    }
                    percentage = (Client.totalObjects * 50 - Client.objectsLoaded * 50) / Client.totalObjects + 50;
                    drawLoadingMessage("Loading - please wait." + "<br>" + " (" + percentage + "%" + ")");
                } else {
                    drawLoadingMessage("Loading - please wait.");
                }
            } else if (client.gameState == 30) {
                drawGameScreen();
            } else if (client.gameState == 40) {
                drawLoadingMessage("Connection lost" + "<br>" + "Please wait - attempting to reestablish");
            } else if (client.gameState == 45) {
                drawLoadingMessage("Please wait...");
            }
        } else {
            client.drawLoginScreen();
        }

        if (client.gameState > 0) {
            Client.rasterProvider.drawFull(0, 0);
        }

        client.mouseClickCount = 0;
    }

    void drawLoadingMessage(String messages) {
        int width = 0;
        for (String message : messages.split("<br>")) {
            int size = client.newRegularFont.getTextWidth(message);
            if (width <= client.newRegularFont.getTextWidth(message)) {
                width = size;
            }
        }

        int offset = client.isResized() ? 3 : 6;

        int height = (12 * messages.split("<br>").length) + 4;

        Rasterizer2D.drawBox(offset, offset, width + 16, height + 6, 0x000000);
        Rasterizer2D.drawBoxOutline(offset, offset, width + 16, height + 6, 0xFFFFFF);

        int offsetY = 0;
        for (String message : messages.split("<br>")) {
            client.newRegularFont.drawCenteredString(message, offset + (width + 16) / 2, offset + 15 + offsetY, 0xffffff, 1);
            offsetY += 12;
        }
    }

    private void drawGameScreen() {
        if (client.fullscreenInterfaceID != -1 && client.gameState == GameState.LOGGED_IN.getState()) {
            if (client.gameState == GameState.LOGGED_IN.getState()) {
                try {
                    client.processWidgetAnimations(client.tickDelta, client.fullscreenInterfaceID);
                    if (client.openInterfaceID != -1) {
                        client.processWidgetAnimations(client.tickDelta, client.openInterfaceID);
                    }
                } catch (Exception ex) {
                }
                client.tickDelta = 0;
                client.getCallbacks().post(net.runelite.api.events.ClientTick.INSTANCE);

                Rasterizer2D.Rasterizer2D_clear();
                Client.jagexNetThread.writePacket(true);
                client.welcomeScreenRaised = true;
                if (client.openInterfaceID != -1) {
                    RSInterface rsInterface_1 = RSInterface.interfaceCache[client.openInterfaceID];
                    if (rsInterface_1.width == 512 && rsInterface_1.height == 334
                            && rsInterface_1.type == 0) {
                        rsInterface_1.width = 765;
                        rsInterface_1.height = 503;
                    }
                    try {
                        client.drawInterface(0, 0, rsInterface_1, 8);
                    } catch (Exception ex) {
                    }
                }
                RSInterface rsInterface = RSInterface.interfaceCache[client.fullscreenInterfaceID];
                if (rsInterface.width == 512 && rsInterface.height == 334
                        && rsInterface.type == 0) {
                    rsInterface.width = 765;
                    rsInterface.height = 503;
                }
                try {
                    client.drawInterface(0, 0, rsInterface, 8);
                } catch (Exception ex) {
                }

                if (!client.isMenuOpen) {
                    client.processRightClick();
                    drawTopLeftTooltip();
                }
            }
            client.drawCount++;
            return;
        }

        if (client.welcomeScreenRaised) {
            client.welcomeScreenRaised = false;
            client.inputTaken = true;
            client.tabAreaAltered = true;
        }
        if (client.backDialogID != -1) {
            try {
                client.processWidgetAnimations(client.tickDelta, client.backDialogID);
            } catch (Exception ex) {
            }
        }

        if (client.backDialogID == -1) {
            client.aClass9_1059.scrollPosition = client.chatAreaScrollLength - client.anInt1089 - 110;
            if (MouseHandler.mouseX >= 496 && MouseHandler.mouseX <= 511
                    && MouseHandler.mouseY > (!client.isResized() ? 345
                    : Client.canvasHeight - 158))
                client.method65(494, 110, MouseHandler.mouseX,
                        MouseHandler.mouseY - (!client.isResized() ? 345
                                : Client.canvasHeight - 158),
                        client.aClass9_1059, 0, false, client.chatAreaScrollLength);
            int i = client.chatAreaScrollLength - 110 - client.aClass9_1059.scrollPosition;
            if (i < 0) {
                i = 0;
            }
            if (i > client.chatAreaScrollLength - 110) {
                i = client.chatAreaScrollLength - 110;
            }
            if (client.anInt1089 != i) {
                client.anInt1089 = i;
                client.inputTaken = true;
            }
        }
        if (client.backDialogID != -1) {
            boolean flag2 = false;
            try {
                flag2 = client.processWidgetAnimations(client.tickDelta, client.backDialogID);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (flag2) {
                client.inputTaken = true;
            }
        }
        if (client.atInventoryInterfaceType == 3)
            client.inputTaken = true;
        if (client.activeInterfaceType == 3)
            client.inputTaken = true;
        if (client.clickToContinueString != null)
            client.inputTaken = true;
        if (client.isMenuOpen)
            client.inputTaken = true;
        if (client.inputTaken) {
            client.inputTaken = false;
        }
        if (client.gameState == GameState.LOGGED_IN.getState()) {
            int offsetX = client.isResized() ? 0 : 4;
            int offsetY = client.isResized() ? 0 : 4;
            drawEntities(offsetX, offsetY, !client.isResized() ? 512 : Client.canvasWidth, !client.isResized() ? 334 : Client.canvasHeight);
        }

        com.client.audio.ObjectSound.updateObjectSounds();
        client.tickDelta = 0;
        client.getCallbacks().post(net.runelite.api.events.ClientTick.INSTANCE);
    }

    public void drawEntities(int viewportOffsetX, int viewportOffsetY, int width, int height) {
        client.viewportDrawCount++;
        if (Client.localPlayer.x >> 7 == client.destX && Client.localPlayer.y >> 7 == client.destY) {
            client.destX = 0;
        }
        client.showPrioritizedPlayers();
        client.showOtherPlayers();

        client.showPrioritizedNPCs();
        client.showOtherNpcs();

        client.createProjectiles();
        client.method104();

        int viewportXOffset = client.getViewportXOffset();
        int viewportYOffset = client.getViewportYOffset();

        Client.setViewportShape(viewportOffsetX, viewportOffsetY, width, height, true);

        Rasterizer2D.setClip(viewportXOffset, viewportYOffset, viewportXOffset + client.getViewportWidth(), client.getViewportHeight() + viewportYOffset);
        Rasterizer3D.resetRasterClipping();
        Rasterizer2D.resetDepth();

        int var37 = client.camAngleX;
        if (Camera.cameraAltitudeAdjustment / 256 > var37) {
            var37 = Camera.cameraAltitudeAdjustment / 256;
        }

        if (client.quakeDirectionActive[4] && client.quakeAmplitude[4] + 128 > var37) {
            var37 = client.quakeAmplitude[4] + 128;
        }

        int var5 = client.camAngleY & 2047;
        int var6 = client.oculusOrbFocalPointX;
        int var7 = Camera.oculusOrbAltitude;
        int var8 = client.oculusOrbFocalPointY;
        int var9 = var37 * 3 + 600;
        int var12 = Client.viewportHeight - 334;
        if (var12 < 0) {
            var12 = 0;
        } else if (var12 > 100) {
            var12 = 100;
        }

        int var13 = (Client.zoomWidth - Client.zoomHeight) * var12 / 100 + Client.zoomHeight;
        int var11 = var13 * var9 / 256;
        var12 = 2048 - var37 & 2047;
        var13 = 2048 - var5 & 2047;
        int var14 = 0;
        int var15 = 0;
        int var16 = var11;
        int var17;
        int var18;
        int var19;
        if (var12 != 0) {
            var17 = Rasterizer3D.Rasterizer3D_sine[var12];
            var18 = Rasterizer3D.Rasterizer3D_cosine[var12];
            var19 = var18 * var15 - var17 * var11 >> 16;
            var16 = var18 * var11 + var15 * var17 >> 16;
            var15 = var19;
        }

        if (var13 != 0) {
            var17 = Rasterizer3D.Rasterizer3D_sine[var13];
            var18 = Rasterizer3D.Rasterizer3D_cosine[var13];
            var19 = var17 * var16 + var14 * var18 >> 16;
            var16 = var18 * var16 - var14 * var17 >> 16;
            var14 = var19;
        }

        if (client.isCameraLocked) {
            Camera.targetCameraX = var6 - var14;
            Camera.targetCameraY = var7 - var15;
            Camera.targetCameraZ = var8 - var16;
            Camera.targetCameraPitch = var37;
            Camera.targetCameraYaw = var5;
        } else {
            client.cameraX = var6 - var14;
            client.cameraY = var7 - var15;
            client.cameraZ = var8 - var16;
            client.cameraPitch = var37;
            client.cameraYaw = var5;
            client.onCameraPitchChanged(0);
        }

        if (!client.isCameraLocked) {
            var11 = Camera.determineRoofHeight();
        } else {
            var11 = Camera.calculateCameraPlane();
        }

        var12 = client.cameraX;
        var13 = client.cameraY;
        var14 = client.cameraZ;
        var15 = client.cameraPitch;
        var16 = client.cameraYaw;

        for (var17 = 0; var17 < 5; ++var17) {
            if (client.quakeDirectionActive[var17]) {
                var18 = (int) (Math.random() * (double) (client.quakeMagnitude[var17] * 2 + 1) - (double) client.quakeMagnitude[var17] + Math.sin((double) client.cameraUpdateCounters[var17] * ((double) client.fourPiOverPeriod[var17] / 100.0D)) * (double) client.quakeAmplitude[var17]);
                if (var17 == 0) {
                    client.cameraX += var18;
                }
                if (var17 == 1) {
                    client.cameraY += var18;
                }
                if (var17 == 2) {
                    client.cameraZ += var18;
                }
                if (var17 == 3) {
                    client.cameraYaw = var18 + client.cameraYaw & 2047;
                }
                if (var17 == 4) {
                    client.cameraPitch += var18;
                    if (client.cameraPitch < 128) {
                        client.cameraPitch = 128;
                        client.onCameraPitchChanged(0);
                    }
                    if (client.cameraPitch > 383) {
                        client.cameraPitch = 383;
                        client.onCameraPitchChanged(0);
                    }
                }
            }
        }

        int mouseX = MouseHandler.mouseX;
        int mouseY = MouseHandler.mouseY;
        if (MouseHandler.clickMode3 != 0) {
            mouseX = MouseHandler.saveClickX;
            mouseY = MouseHandler.saveClickY;
        }

        if (mouseX >= viewportXOffset && mouseX < viewportXOffset + client.getViewportWidth() && mouseY >= viewportYOffset && mouseY < client.getViewportHeight() + viewportYOffset) {
            int mouseXLoc = mouseX - viewportXOffset;
            int mouseYLoc = mouseY - viewportYOffset;
            ViewportMouse.setClick(mouseXLoc, mouseYLoc);
        } else {
            ViewportMouse.method3167();
        }

        Rasterizer2D.Rasterizer2D_clear();

        client.callbacks.post(BeforeRender.INSTANCE);
        Rasterizer3D.toggleZBuffering(Configuration.ZBUFF);
        Rasterizer3D.clips.viewportZoom = Client.viewportZoom;
        client.scene.render(client.cameraX, client.cameraZ, client.cameraYaw, client.cameraY, var11, client.cameraPitch);
        Rasterizer3D.toggleZBuffering(false);
        Rasterizer3D.clips.viewportZoom = Clips.get3dZoom();
        Client.overheadTextCount = 0;
        updateEntities(Client.viewportOffsetX, Client.viewportOffsetY);
        Client.rasterProvider.init();
        client.scene.reset_interactive_obj();
        drawHeadIcon();

        ((TextureProvider) Rasterizer3D.clips.Rasterizer3D_textureLoader).animate(client.tickDelta);

        draw3dScreen();

        drawMinimap();
        drawTabArea();
        drawChatArea();

        if (com.client.hover.HoverMenuManager.showMenu) {
            com.client.hover.HoverMenuManager.drawHintMenu();
        }

        if (client.isMenuOpen()) {
            BeforeMenuRender event = new BeforeMenuRender();
            client.getCallbacks().post(event);
            if (!event.isConsumed()) {
                client.drawOriginalMenu(255);
            }
        }

        processExperienceCounter();
        viewportInterfaceCallback();

        if (Client.isLoading && Client.jagexNetThread.method1968(true, false) == 0) {
            Client.isLoading = false;
        }

        if (Client.isLoading) {
            Rasterizer2D.fillRectangle(0, 0, Client.canvasWidth, Client.canvasHeight, 0);
            drawLoadingMessage("Loading - please wait.");
        }

        client.cameraX = var12;
        client.cameraY = var13;
        client.cameraZ = var14;
        client.cameraPitch = var15;
        client.cameraYaw = var16;
        client.onCameraPitchChanged(0);
    }

    private void draw3dScreen() {
        if (!client.isResized()) {
            client.mapArea[1].drawSprite(516, 0);
            client.mapArea[7].drawSprite(0, 0);
        }
        if (Client.snowVisible && Configuration.CHRISTMAS && !Client.instance.isResized()) {
            client.processWidgetAnimations(client.tickDelta, 11877);
            client.drawInterface(0, 0, RSInterface.interfaceCache[11877], 0);
        }

        if (client.getUserSettings().isShowEntityTarget()) {
            if (client.entityTarget != null) {
                client.entityTarget.draw();
            }
        }

        if (client.getUserSettings().isGameTimers()) {
            try {
                int startX = 516;
                int startY = !Client.instance.isResized() ? 294 : Client.canvasHeight - 209;
                GameTimerHandler.getSingleton().drawGameTimers(client, startX, startY);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        drawSplitPrivateChat();

        if (client.crossType == 1) {
            client.crosses[client.crossIndex / 100].drawSprite(client.crossX - 8, client.crossY - 8);
            client.anInt1142++;
            if (client.anInt1142 > 67) {
                client.anInt1142 = 0;
                client.stream.createFrame(78);
            }
        }
        if (client.crossType == 2) {
            client.crosses[4 + client.crossIndex / 100].drawSprite(client.crossX - 8, client.crossY - 8);
        }

        if (client.openWalkableWidgetID != -1) {
            client.processWidgetAnimations(client.tickDelta, client.openWalkableWidgetID);
            if (!Nightmare.instance.drawNightmareInterfaces(client.openWalkableWidgetID)) {
                RSInterface rsinterface = RSInterface.interfaceCache[client.openWalkableWidgetID];
                if (!client.isResized()) {
                    client.drawInterface(0, 0, rsinterface, 0);
                } else {
                    if (client.openWalkableWidgetID == 28000 || client.openWalkableWidgetID == 28020 || client.openWalkableWidgetID == 16210
                            || client.openWalkableWidgetID == 27500 || client.openWalkableWidgetID == 196 || client.openWalkableWidgetID == 27553) {
                        client.drawInterface(0, Client.canvasWidth - 730, rsinterface, 20);
                    } else if (client.openWalkableWidgetID == 197) {
                        client.drawInterface(0, Client.canvasWidth - 530, rsinterface, -70);
                    } else if (client.openWalkableWidgetID == 27553) {
                    } else if (client.openWalkableWidgetID == 21100 || client.openWalkableWidgetID == 21119
                            || client.openWalkableWidgetID == 29230) {
                        client.drawInterface(0, 0, rsinterface, 0);
                    } else if (client.openWalkableWidgetID == 201) {
                        client.drawInterface(0, Client.canvasWidth - 510, rsinterface, -110);
                    } else if (Client.centerInterface() || client.openInterfaceID == 29230) {
                        client.drawInterface(0, (Client.canvasWidth / 2) - 356, rsinterface,
                                !client.isResized() ? 0 : (Client.canvasHeight / 2) - 230);
                    } else {
                        if (Client.canvasWidth >= 900 && Client.canvasHeight >= 650) {
                            client.drawInterface(0, (Client.canvasWidth / 2) - 356, RSInterface.interfaceCache[client.openWalkableWidgetID], !client.isResized() ? 0 : (Client.canvasHeight / 2) - 230);
                        } else {
                            client.drawInterface(0, 0, RSInterface.interfaceCache[client.openWalkableWidgetID], 0);
                        }
                    }
                }
            }
        }

        BroadcastManager.display(client);

        if (client.anInt1055 == 1) {
            int x = !client.isResized() ? 476 : (Client.canvasWidth - 30);
            int y = !client.isResized() ? 300 : 175;
            if (!client.isResized() && GameTimerHandler.getSingleton().hasActiveGameTimers()) {
                y -= 32;
            }
            client.multiOverlay.drawSprite(x, y);
        }

        if (client.fpsOn) {
            int yPosition = 30;
            int xPosition = !client.isResized() ? 515 : Client.canvasWidth - 222;
            int textColor = 0xffff00;
            if (client.getFpsValue() < 15)
                textColor = 0xff0000;
            client.aTextDrawingArea_1271.method380("Fps: " + client.getFpsValue(), xPosition, textColor, yPosition);
            yPosition += 15;
            Runtime runtime = Runtime.getRuntime();
            int j1 = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
            textColor = 0xffff00;
            if (j1 > 0x2000000 && Client.lowMem)
                textColor = 0xff0000;
            client.aTextDrawingArea_1271.method380("Mem: " + j1 / 1000 + " mb", xPosition, textColor, yPosition);
            yPosition += 15;
        }

        int x = client.baseX + (Client.localPlayer.x - 6 >> 7);
        int y = client.baseY + (Client.localPlayer.y - 6 >> 7);
        int var131 = x >> 6;
        int var12 = y >> 6;
        int chunkX = x >> 3;
        int var141 = y >> 3;
        int regionid = var131 * 256 + var12;
        int mapx = client.currentRegionX;
        int mapy = client.currentRegionY;
        if (client.clientData) {
            Runtime runtime = Runtime.getRuntime();
            int j1 = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
            client.aTextDrawingArea_1271.method385(0x00FF00, "Players Nearby: " + client.playerCount, 27, 5);
            client.aTextDrawingArea_1271.method385(0x00FF00, "Npcs Nearby: " + client.npcCount, 41, 5);

            if (mapx > 1000 || mapy > 1000) {
                client.aTextDrawingArea_1271.method385(0xffff00, "Current Region: " + mapx + ", " + mapy + ", Region ID: " + regionid, 55, 5);
            } else {
                client.aTextDrawingArea_1271.method385(0xffff00, "Current Region: 0" + mapx + ", 0" + mapy + ", Region ID: " + regionid, 55, 5);
            }
            for (int num = 0; num < client.regionLandIds.length; num++) {
                int[] flo = client.regionLandIds;
                client.aTextDrawingArea_1271.method385(0xffff00, "Floor map: " + Arrays.toString(flo), 69, 5);
            }
            for (int num = 0; num < client.regionLocIds.length; num++) {
                int[] obj = client.regionLocIds;
                client.aTextDrawingArea_1271.method385(0xffff00, "Object map: " + Arrays.toString(obj), 83, 5);
            }

            client.aTextDrawingArea_1271.method385(0xffff00, "Map Data: " + client.regionLandIds[0] + ".dat", 97, 5);
            client.aTextDrawingArea_1271.method385(0xffff00, "Fps: " + client.getFpsValue(), 111, 5);
            client.aTextDrawingArea_1271.method385(0xffff00, "Memory Used: " + j1 / 1024 + "MB", 125, 5);
            client.aTextDrawingArea_1271.method385(0xffff00,
                    "Mouse: [" + MouseHandler.mouseX + ", " + MouseHandler.mouseY + "] ", 139, 5);
            client.aTextDrawingArea_1271.method385(0xffff00, "Coordinates: X: " + x + ", Y: " + y, 153, 5);

            client.aTextDrawingArea_1271.method385(0xffff00,
                    "Camera Position: X: " + client.cameraX + ", Y: " + client.cameraZ + ", Z: " + client.cameraY, 167, 5);
            client.aTextDrawingArea_1271.method385(0xffff00, "Camera Curve: X: " + client.cameraYaw + ", Y: " + client.cameraPitch, 181, 5);
            y = 181;
            y += 15;
        }

        if (client.openInterfaceID != -1) {
            client.processWidgetAnimations(client.tickDelta, client.openInterfaceID);
            RSInterface rsinterface = RSInterface.interfaceCache[client.openInterfaceID];
            client.anInt886 = 0;
            if (!client.isResized()) {
                client.buildInterfaceMenu(0, rsinterface, client.getMouseX(), 0, client.getMouseY(), 0);
            } else {
                client.buildInterfaceMenu((Client.canvasWidth / 2) - 356, rsinterface, client.getMouseX(), (Client.canvasHeight / 2) - 230, client.getMouseY(), 0);
            }
            if (client.anInt886 != client.anInt1026) {
                client.anInt1026 = client.anInt886;
            }
            if (!client.isResized())
                client.drawInterface(0, 0, rsinterface, 0);
            else
                client.drawInterface(0, (Client.canvasWidth / 2) - 356, rsinterface, !client.isResized() ? 0 : (Client.canvasHeight / 2) - 230);
        }
        client.method70();

        if (!client.isMenuOpen) {
            client.processRightClick();
            drawTopLeftTooltip();
        }

        if (client.drawGrid) {
            drawGrid();
        }

        if (client.anInt1104 != 0) {
            int j = client.anInt1104 / 50;
            int l = j / 60;
            j %= 60;
            int yPosition = !client.isResized() ? 329 : Client.canvasHeight - 165;
            if (j < 10)
                client.aTextDrawingArea_1271.method385(0xffff00, "Zenyx will be updating: " + l + ":0" + j, yPosition, 5);
            else
                client.aTextDrawingArea_1271.method385(0xffff00, "Zenyx will be updating: " + l + ":" + j, yPosition, 5);

            client.anInt849++;
            if (client.anInt849 > 75) {
                client.anInt849 = 0;
                client.stream.createFrame(148);
            }
        }

        drawScreenBox();
        client.devConsole.draw_console();
    }

    private void drawGrid() {
        for (int index = 0; index < 516; index += 10) {
            if (index < 334) {
                Rasterizer2D.drawHorizontalLine2(0, index, 516, 0xff0000);
            }
            Rasterizer2D.drawVerticalLine2(index, 0, 334, 0xff0000);
        }

        int xPos = MouseHandler.mouseX - 4 - ((MouseHandler.mouseX - 4) % 10);
        int yPos = MouseHandler.mouseY - 4 - ((MouseHandler.mouseY - 4) % 10);

        Rasterizer2D.drawTransparentBox(xPos, yPos, 10, 10, 0xffffff, 255);
        client.newSmallFont.drawCenteredString("(" + (xPos + 4) + ", " + (yPos + 4) + ")", xPos + 4, yPos - 1, 0xffff00, 0);
    }

    public void drawScreenBox() {
        if (client.screenFlashDrawing) {
            double rate = 1_000d / client.getFpsValue() / 20d;
            if (client.screenFlashOpacityDownward) {
                client.screenFlashOpacity -= rate;
                if (client.screenFlashOpacity <= 10) {
                    client.screenFlashOpacityDownward = false;
                    client.screenFlashDrawing = false;
                }
            } else {
                client.screenFlashOpacity += rate;
                if (client.screenFlashOpacity >= client.screenFlashMaxIntensity) {
                    client.screenFlashOpacityDownward = true;
                }
            }
            Rasterizer2D.drawAlphaGradient(0, 0, Client.canvasWidth, Client.canvasHeight, client.screenFlashColor, client.screenFlashColor, (int) client.screenFlashOpacity);
        }

        if (client.isMudSplashActive()) {
            int total = Math.max(1, client.getMudSplashTotalTicks());
            int alpha = (int) (180.0 * client.getMudSplashTicks() / total);
            if (alpha > 0) {
                Sprite splash = client.getMudSplashSprite(Client.canvasWidth, Client.canvasHeight);
                if (splash != null) {
                    splash.drawAdvancedSprite(0, 0, alpha);
                }
            }
            client.consumeMudSplashTick();
        }

        if (client.fogEnabled) {
            Rasterizer2D.drawAlphaBox(0, 0, Client.canvasWidth, Client.canvasHeight, 0xE67D2D, client.fogOpacity);
        }
    }

    public void drawTopLeftTooltip() {
        if (client.devConsole.console_open)
            return;

        if (client.fullscreenInterfaceID != -1) {
            return;
        }

        if (Client.tabInterfaceIDs[Client.tabID] == 17200 && Client.menuTargets[1].contains("ctivate")) {
            Client.menuTargets[1] = "Select";
            Client.menuOpcodes[1] = 850;
            client.menuOptionsCount = 2;
        }
        if (client.menuOptionsCount < 2 && client.itemSelected == 0 && client.spellSelected == 0)
            return;
        String s;
        if (client.itemSelected == 1 && client.menuOptionsCount < 2)
            s = "Use " + client.selectedItemName + " with...";
        else if (client.spellSelected == 1 && client.menuOptionsCount < 2)
            s = client.spellTooltip + "...";
        else
            s = Client.getMenuString(client.menuOptionsCount - 1);

        if (client.menuOptionsCount > 2) {
            s = s + Client.colorStartTag(16777215) + " " + '/' + " " + (client.menuOptionsCount - 2) + " more options";
        }

        client.newBoldFont.drawString(s, 8, 19, 0xffffff, 0, 255);
    }

    public void drawHeadIcon() {
        if (client.hintArrowType != 2)
            return;
        client.calcEntityScreenPos((client.hintIconX - client.baseX << 7) + client.anInt937, client.hintIconFloorPos * 2, (client.hintIconYpos - client.baseY << 7) + client.anInt938);
        if (client.viewportTempX > -1 && Client.loopCycle % 20 < 10)
            client.headIconsHint[0].drawSprite(client.viewportTempX - 12, client.viewportTempY - 28);
    }

    private void drawSplitPrivateChat() {
        if (!client.splitPrivateChat)
            return;
        RSFont font = client.newRegularFont;

        int listPosition = 0;
        boolean update = client.anInt1104 != 0;
        int broadcastLines = BroadcastManager.getWrappedLineCount(client);
        if (update) {
            listPosition += 1;
        }
        if (broadcastLines > 0) {
            listPosition += broadcastLines;
        }
        int xPosition = 0;
        int yPosition = 0;
        for (int j = 0; j < 100; j++) {
            if (client.chatMessages[j] != null) {
                int k = client.chatTypes[j];
                String s = client.chatNames[j];
                List<Integer> crowns = new ArrayList<>();
                while (s.startsWith("@cr")) {
                    String s2 = s.substring(3, s.length());
                    int index = s2.indexOf("@");
                    if (index != -1) {
                        s2 = s2.substring(0, index);
                        crowns.add(Integer.parseInt(s2));
                        s = s.substring(4 + s2.length());
                    }
                }
                if ((k == 3 || k == 7) && (k == 7 || client.privateChatMode == 0 || client.privateChatMode == 1 && client.isFriendOrSelf(s))) {
                    int baseY = (!client.isResized() ? 330 : Client.canvasHeight - 173) - listPosition * 13;
                    int baseX = !client.isResized() ? 5 : 0;
                    int crownWidth = 0;
                    if (!crowns.isEmpty()) {
                        for (int crown : crowns) {
                            for (int right = 0; right < client.modIconss.length; right++) {
                                if (right == (crown - 1) && client.modIconss[right] != null) {
                                    crownWidth += client.modIconss[right].myWidth;
                                    crownWidth += 2;
                                    break;
                                }
                            }
                        }
                    }
                    int nameStartX = baseX + font.getTextWidth("From ") + crownWidth;
                    int messageX = nameStartX + font.getTextWidth(s + ": ");
                    int maxLineWidth = Rasterizer2D.Rasterizer2D_xClipEnd - 5;
                    int availableWidth = Math.max(20, maxLineWidth - messageX);
                    String[] wrappedLines = font.wrap(client.chatMessages[j], availableWidth);
                    int linesDrawn = Math.max(1, wrappedLines.length);
                    for (int lineIndex = 0; lineIndex < linesDrawn; lineIndex++) {
                        int lineOffset = linesDrawn - 1 - lineIndex;
                        int lineYPos = baseY - lineOffset * 13;
                        if (lineIndex == 0) {
                            int drawX = baseX;
                            font.drawBasicString("From", drawX, lineYPos, 65535, 0);
                            drawX += font.getTextWidth("From ");
                            if (!crowns.isEmpty()) {
                                for (int crown : crowns) {
                                    for (int right = 0; right < client.modIconss.length; right++) {
                                        if (right == (crown - 1) && client.modIconss[right] != null) {
                                            client.modIconss[right].drawAdvancedSprite(drawX, lineYPos - client.modIconss[right].myHeight);
                                            drawX += client.modIconss[right].myWidth;
                                            drawX += 2;
                                            break;
                                        }
                                    }
                                }
                            }
                            font.drawBasicString(s + ": ", drawX, lineYPos, 65535, 0);
                            font.drawBasicString(wrappedLines[0], messageX, lineYPos, 65535, 0);
                        } else {
                            font.drawBasicString(wrappedLines[lineIndex], nameStartX, lineYPos, 65535, 0);
                        }
                        if (++listPosition >= 5)
                            return;
                    }
                }
                if (k == 5 && client.privateChatMode < 2) {
                    int baseY = (!client.isResized() ? 330 : Client.canvasHeight - 173) - listPosition * 13;
                    int baseX = !client.isResized() ? 5 : 0;
                    int maxLineWidth = Rasterizer2D.Rasterizer2D_xClipEnd - 5;
                    int availableWidth = Math.max(20, maxLineWidth - baseX);
                    String[] wrappedLines = font.wrap(client.chatMessages[j], availableWidth);
                    int linesDrawn = Math.max(1, wrappedLines.length);
                    for (int lineIndex = 0; lineIndex < linesDrawn; lineIndex++) {
                        int lineOffset = linesDrawn - 1 - lineIndex;
                        int lineYPos = baseY - lineOffset * 13;
                        font.drawBasicString(wrappedLines[lineIndex], baseX, lineYPos, 65535, 0);
                        if (++listPosition >= 5)
                            return;
                    }
                }
                if (k == 6 && client.privateChatMode < 2) {
                    int baseY = (!client.isResized() ? 330 : Client.canvasHeight - 173) - listPosition * 13;
                    int baseX = !client.isResized() ? 5 : 0;
                    int nameStartX = baseX + font.getTextWidth("To ");
                    int messageX = nameStartX + font.getTextWidth(s + ": ");
                    int maxLineWidth = Rasterizer2D.Rasterizer2D_xClipEnd - 5;
                    int availableWidth = Math.max(20, maxLineWidth - messageX);
                    String[] wrappedLines = font.wrap(client.chatMessages[j], availableWidth);
                    int linesDrawn = Math.max(1, wrappedLines.length);
                    for (int lineIndex = 0; lineIndex < linesDrawn; lineIndex++) {
                        int lineOffset = linesDrawn - 1 - lineIndex;
                        int lineYPos = baseY - lineOffset * 13;
                        if (lineIndex == 0) {
                            font.drawBasicString("To " + s + ": ", baseX, lineYPos, 65535, 0);
                            font.drawBasicString(wrappedLines[0], messageX, lineYPos, 65535, 0);
                        } else {
                            font.drawBasicString(wrappedLines[lineIndex], nameStartX, lineYPos, 65535, 0);
                        }
                        if (++listPosition >= 5)
                            return;
                    }
                }
            }
        }
    }

    void drawChannelButtons() {
        String text[] = {"On", "Friends", "Off", "Hide"};
        int textColor[] = {65280, 0xffff00, 0xff0000, 65535};
        int yOffset = (!client.isResized() ? 338 : Client.canvasHeight - 165);

        if (client.hideChatArea) {
            client.channelButtons.drawSprite(0, Client.canvasHeight - 23);
        }

        switch (client.channelButtonClickPosition) {
            case 0: client.chatButtons[1].drawSprite(5, 142 + yOffset); break;
            case 1: client.chatButtons[1].drawSprite(71, 142 + yOffset); break;
            case 2: client.chatButtons[1].drawSprite(137, 142 + yOffset); break;
            case 3: client.chatButtons[1].drawSprite(203, 142 + yOffset); break;
            case 4: client.chatButtons[1].drawSprite(269, 142 + yOffset); break;
            case 5: client.chatButtons[1].drawSprite(335, 142 + yOffset); break;
        }
        if (client.channelButtonHoverPosition == client.channelButtonClickPosition) {
            switch (client.channelButtonHoverPosition) {
                case 0: client.chatButtons[2].drawSprite(5, 142 + yOffset); break;
                case 1: client.chatButtons[2].drawSprite(71, 142 + yOffset); break;
                case 2: client.chatButtons[2].drawSprite(137, 142 + yOffset); break;
                case 3: client.chatButtons[2].drawSprite(203, 142 + yOffset); break;
                case 4: client.chatButtons[2].drawSprite(269, 142 + yOffset); break;
                case 5: client.chatButtons[2].drawSprite(335, 142 + yOffset); break;
                case 6: client.chatButtons[3].drawSprite(404, 142 + yOffset); break;
            }
        } else {
            switch (client.channelButtonHoverPosition) {
                case 0: client.chatButtons[0].drawSprite(5, 142 + yOffset); break;
                case 1: client.chatButtons[0].drawSprite(71, 142 + yOffset); break;
                case 2: client.chatButtons[0].drawSprite(137, 142 + yOffset); break;
                case 3: client.chatButtons[0].drawSprite(203, 142 + yOffset); break;
                case 4: client.chatButtons[0].drawSprite(269, 142 + yOffset); break;
                case 5: client.chatButtons[0].drawSprite(335, 142 + yOffset); break;
                case 6: client.chatButtons[3].drawSprite(404, 142 + yOffset); break;
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        client.newSmallFont.drawCenteredString("" + dateFormat.format(new Date().getTime()), 459, 157 + yOffset, 0xffffff, 0);

        client.smallText.method389(true, 26, 0xffffff, "All", 157 + yOffset);
        client.smallText.method389(true, 86, 0xffffff, "Game", 152 + yOffset);
        client.smallText.method389(true, 150, 0xffffff, "Public", 152 + yOffset);
        client.smallText.method389(true, 212, 0xffffff, "Private", 152 + yOffset);
        client.smallText.method389(true, 286, 0xffffff, "Clan", 152 + yOffset);
        client.smallText.method389(true, 349, 0xffffff, "Trade", 152 + yOffset);
        client.smallText.method382(textColor[client.gameMode], 98, text[client.gameMode], 163 + yOffset, true);
        client.smallText.method382(textColor[client.publicChatMode], 164, text[client.publicChatMode], 163 + yOffset, true);
        client.smallText.method382(textColor[client.privateChatMode], 230, text[client.privateChatMode], 163 + yOffset, true);
        client.smallText.method382(textColor[client.clanChatMode], 296, text[client.clanChatMode], 163 + yOffset, true);
        client.smallText.method382(textColor[client.tradeMode], 362, text[client.tradeMode], 163 + yOffset, true);
    }

    private void drawChatArea() {
        client.drawChatArea();
    }

    private void drawTabArea() {
        client.drawTabArea();
    }

    private void drawMinimap() {
        client.drawMinimap();
    }

    private void updateEntities(int viewportOffsetX, int viewportOffsetY) {
        client.updateEntities(viewportOffsetX, viewportOffsetY);
    }

    private void processExperienceCounter() {
        client.processExperienceCounter();
    }

    private void viewportInterfaceCallback() {
        if (!client.isResized()) {
            client.callbacks.drawInterface(WidgetID.FIXED_VIEWPORT_GROUP_ID, Collections.emptyList());
        } else {
            client.callbacks.drawInterface(WidgetID.RESIZABLE_VIEWPORT_OLD_SCHOOL_BOX_GROUP_ID, Collections.emptyList());
        }
    }
}

