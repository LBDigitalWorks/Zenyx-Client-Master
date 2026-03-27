package com.client.draw.login

import com.client.Announcements
import com.client.Client
import com.client.Rasterizer2D
import com.client.StringUtils
import com.client.draw.ImageCache
import com.client.draw.login.flames.FlameManager
import com.client.draw.login.worlds.WorldManager
import com.client.draw.login.worlds.WorldManager.openWorldSectionScreen
import com.client.draw.login.worlds.WorldManager.worldList
import com.client.engine.GameEngine
import com.client.engine.impl.MouseHandler
import com.client.audio.StaticSound
import com.client.graphics.interfaces.impl.SettingsTabWidget
import com.client.settings.ClientSettingsSync
import com.client.settings.SettingManager
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.value.ObservableValue
import org.apache.commons.lang3.time.StopWatch
import kotlin.math.ceil
import kotlin.system.exitProcess


class LoginScreen(val client : Client) {

    var loginState = LoginState.LOADING
    var loginScreenCursorPos = 0
    private val flameManager = FlameManager()


    private var backgroundStopWatch: StopWatch? = null
    var opacity = 0
    private var backgroundSprite = LoginBackground.ZENYX_LOGIN.spriteID

    private val fadeToBlack: DoubleProperty = SimpleDoubleProperty(256.0)

    private val eulaText = listOf(
        "Before using this app, please read and accept our",
        "@gol@terms of use@whi@, @gol@privacy policy@whi@, and @gol@end user licence",
        "@gol@agreement (EULA)@whi@.",
        "By accepting, you agree to these documents."
    )

    val BACKGROUND_ACTIVE_TIME_SECONDS = 10


    fun setup() {
        fadeToBlack.addListener { observable: ObservableValue<out Number>?, oldVal: Number?, newVal: Number ->
            if (newVal.toDouble() >= 256) {
                backgroundSprite = backgroundSprite()
                backgroundStopWatch!!.reset()
                backgroundStopWatch!!.start()
            }
            if (newVal.toDouble() <= 0) {
                fadeToBlack.set(256.0)
            }

        }
    }

    private fun backgroundSprite() : Int {
        return when(client.settingManager.loginBackground) {
            LoginBackground.FADING_BACKGROUNDS -> LoginBackground.values().filter {
                it.spriteID != -1
            }.toList().shuffled().first().spriteID
            else -> client.settingManager.loginBackground.spriteID
        }
    }

    private fun handleBackgrounds() {
        when(client.settingManager.loginBackground) {
            LoginBackground.FADING_BACKGROUNDS -> {
                if(backgroundStopWatch == null) {
                    backgroundStopWatch = StopWatch()
                    backgroundStopWatch!!.start()
                }

                val end: Long = backgroundStopWatch!!.startTime + 1000L * BACKGROUND_ACTIVE_TIME_SECONDS
                val increment: Long = (end - backgroundStopWatch!!.startTime) / 100
                if (increment > 0) {
                    fadeToBlack.set(fadeToBlack.get() - 0.9)
                    opacity = ceil(fadeToBlack.get()).toInt()
                }
            }
            else -> backgroundSprite = backgroundSprite()
        }
    }

    fun draw() {
        when (loginState) {
            LoginState.LOADING -> drawLoadingScreen()
            else -> drawLoginScreen()
        }
    }

    private fun drawLoadingScreen() {
        val centerX = GameEngine.canvasWidth / 2
        val centerY = GameEngine.canvasHeight / 2

        LoginBackground.backGroundSprite.get(LoginBackground.ZENYX.spriteID)!!.drawAdvancedSprite(0, 0)
       // Client.LOGO.drawAdvancedSprite(centerX - (444 / 2),centerY - (GameEngine.canvasHeight / 2) + 17)

        val barWidth = 540

        val x: Int = 765 / 2 - 543 / 2
        val y = 475 - 20 + 8;
        val height = 32
        val offset = 5.43
        Announcements.displayAnnouncements()
        Rasterizer2D.drawBox(x, y, barWidth, 34, 0x8C1111)
        Rasterizer2D.drawBox(x + 1, y + 1, barWidth-2, 32, 0x000000)
        Rasterizer2D.drawBox(x + 2,y + 2, barWidth - (Math.round(client.loadingPercent * offset).toInt() / 2),30,0x8C1111);

        Client.instance.newBoldFont.drawCenteredString((client.loadingText + " - " + client.loadingPercent) + "%", x + (barWidth / 2), y + 21, 0xFFFFFF,0)
        Client.instance.newBoldFont.drawCenteredString("Zenyx" + " is loading - please wait...", x + (barWidth / 2), y - 14, 0xFFFFFF,0)
    }

    private fun drawLoginScreen() {
        if (WorldManager.selectedWorld == null) {
            openWorldSectionScreen()
            WorldManager.selectedWorld = worldList.first()
            WorldManager.selectedWorld = worldList.first()
        }

        val centerX = GameEngine.canvasWidth / 2
        val centerY = GameEngine.canvasHeight / 2
        val alpha = if (Client.instance.settingManager.loginBackground != LoginBackground.FADING_BACKGROUNDS) 226 else opacity
        handleBackgrounds()
        Rasterizer2D.drawBox(0,0,GameEngine.canvasWidth,GameEngine.canvasHeight,0x000000)
        if (backgroundSprite != -1) {
            LoginBackground.backGroundSprite.get(LoginBackground.ZENYX_LOGIN.spriteID)!!.drawAdvancedSprite(centerX - (766 / 2),centerY - (503 / 2),alpha)
        }
        client.loginAsset5.drawSprite(274, 213)
        client.loginAsset6.drawSprite(319, 265)
        client.loginAsset6.drawSprite(319, 320)
        client.loginAsset0.drawSprite(460, 350)
        Announcements.displayAnnouncements()
        client.aTextDrawingArea_1271.method385(
            0xffff00,
            "Mouse: [" + MouseHandler.saveClickX + ", " + MouseHandler.saveClickY + "] ", 139, 5
        )

        client.loginBoxHover = client.newmouseInRegion(317, 265, ImageCache.get(2295));
        client.passwordBoxHover = client.newmouseInRegion(317, 330, ImageCache.get(2295));
        client.playNowHover = client.newmouseInRegion(317, 410, ImageCache.get(2292));
        client.rememberMeHover = client.newmouseInRegion(460, 350, ImageCache.get(2294))


            if (!client.rememberMeHover) {
                client.loginAsset0.drawSprite(460, 350)
            } else {
                client.loginAsset1.drawSprite(460, 350)
            }

        if (!client.loginBoxHover) {
            client.loginAsset6.drawSprite(317, 265);
            } else {
            client.loginAsset7.drawSprite(317, 265);
            }

            if (!client.passwordBoxHover) {
                client.loginAsset8.drawSprite(317, 320)
            } else {
                client.loginAsset9.drawSprite(317, 320)
            }
        if (!client.playNowHover) {
            client.loginAsset10.drawSprite(317, 395)
        } else {
            client.loginAsset11.drawSprite(317, 395)
        }
        val loginBoxX = centerX - (360 / 2)
        val loginBoxY = centerY - (200 / 2) + 21

        ImageCache.getLazy(if(!client.settingManager.muteAll) 25 else 26).drawAdvancedSprite(GameEngine.canvasWidth - 38 - 5,GameEngine.canvasHeight - 45 + 7)
        if(WorldManager.loadedWorlds) {
            ImageCache.getLazy(3).drawAdvancedSprite(591,465)
            client.newBoldFont.drawCenteredString("World: ${WorldManager.selectedWorld?.name}", 640,480,0xFFFFFF,1)
            client.newSmallFont.drawCenteredString(WorldManager.worldStatusText, 640,494,0xFFFFFF,1)

        }

        when(loginState) {
            LoginState.EULA -> {
                eulaText.forEachIndexed { index, line ->
                    client.newRegularFont.drawCenteredString(line,loginBoxX + (360 / 2), loginBoxY + 43 + (20 * index),0xFFFFFF,1)
                }
                listOf("Accept","Decline").forEachIndexed { index, buttonText ->
                    val buttonX = loginBoxX + 28 + if(index == 1) 160 else 0
                  //  ImageCache.getLazy(2).drawSprite(buttonX,loginBoxY + 121)
                    //client.newBoldFont.drawCenteredString(buttonText,buttonX + (147 / 2) - 1, loginBoxY + 146,0xFFFFFF,1)
                }
            }
            LoginState.LOGIN -> {

                client.newBoldFont.drawCenteredString(client.firstLoginMessage, 400, 377, 0xFFFF00, 1)
                client.newBoldFont.drawBasicString(
                    client.myUsername + flash(0),
                    325, 282,
                    0xFFFFFF, 1
                )

                client.newBoldFont.drawBasicString(
                    StringUtils.passwordAsterisks(client.myPassword) + flash(1),
                    325, 335,
                    0xFFFFFF, 1
                )

                ImageCache.get(if(!Client.instance.settingManager.rememberUsername) 2293 else 2294).drawHoverSprite(460, 350,ImageCache.get(if(!Client.instance.settingManager.rememberUsername) 2293 else 2294))
            }
            LoginState.WORLD_SELECT -> WorldManager.renderWorldSelect()
            else -> {}
        }
    }

    fun handleInput() {

        val centerX = GameEngine.canvasWidth / 2
        val centerY = GameEngine.canvasHeight / 2
        val loginBoxX = centerX - (360 / 2)
        val loginBoxY = centerY - (200 / 2) + 21

        if(client.newclickInRegion(591,466,ImageCache.getLazy(3))) {
            openWorldSectionScreen(true)
        }

        if(client.newclickInRegion(GameEngine.canvasWidth - 38 - 5,GameEngine.canvasHeight - 45 + 7,ImageCache.getLazy(25))) {
            client.settingManager.muteAll = !client.settingManager.muteAll
            if (client.settingManager.muteAll) {
                StaticSound.updateMusicVolume(0)
                StaticSound.updateSoundEffectVolume(0)
                StaticSound.updateAreaVolume(0)
                SettingsTabWidget.musicVolumeSlider?.setValue(255.0)
                SettingsTabWidget.soundVolumeSlider?.setValue(127.0)
                SettingsTabWidget.areaSoundVolumeSlider?.setValue(127.0)
            } else {
                val music = ClientSettingsSync.getMusicVolume()
                val sound = ClientSettingsSync.getSoundEffectVolume()
                val area = ClientSettingsSync.getAreaSoundEffectVolume()
                StaticSound.updateMusicVolume(music)
                StaticSound.updateSoundEffectVolume(sound)
                StaticSound.updateAreaVolume(area)
                SettingsTabWidget.musicVolumeSlider?.setValue((255 - music).toDouble())
                SettingsTabWidget.soundVolumeSlider?.setValue((127 - sound).toDouble())
                SettingsTabWidget.areaSoundVolumeSlider?.setValue((127 - area).toDouble())
            }
            SettingManager.save(client.settingManager)
        }

        when(loginState) {
            LoginState.EULA -> {
                repeat(2) {
                    val buttonX = loginBoxX + 28 + if(it == 1) 160 else 0
                    if(client.newclickInRegion(buttonX + (147 / 2) - 1, loginBoxY + 146,ImageCache.getLazy(2))) {
                        when(it) {
                            0 -> {
                                loginState = LoginState.LOGIN
                                client.settingManager.eulaAccepted = true
                                SettingManager.save(client.settingManager)
                            }
                            1 -> exitProcess(0)
                        }
                    }
                }
            }
            LoginState.WELCOME -> {
                repeat(2) {
                    val buttonX = loginBoxX + 28 + if(it == 1) 160 else 0
                    if(client.newclickInRegion(buttonX - 1,loginBoxY + 100,ImageCache.getLazy(2))) {
                        when(it) {
                            0 -> client.launchURL("https://www.google.com/")
                            1 -> loginState = LoginState.LOGIN
                        }
                    }
                }
            }
            LoginState.LOGIN -> {
                if (client.newclickInRegion(317, 408, 480, 425)) {
                    client.login(client.myUsername, client.myPassword, false)
                }

                if (client.newclickInRegion(323, 269, 476, 279)) {
                    loginScreenCursorPos = 0
                }

                if (client.newclickInRegion(323, 322, 476, 341)) {
                    loginScreenCursorPos = 1
                }

                if (client.newclickInRegion(460, 350, ImageCache.get(2293))) {
                    client.settingManager.rememberUsername = !client.settingManager.rememberUsername
                }

                if (client.newclickInRegion(loginBoxX + 204, loginBoxY + 107, ImageCache.getLazy(21))) {
                    client.settingManager.hiddenUsername = !client.settingManager.hiddenUsername
                }
                while (Client.keyManager.hasNextKey()) {
                    val l1 = Client.keyManager.lastTypedCharacter.code
                    val keyCode = Client.keyManager.lastProcessedKeyCode
                    if (l1 == -1 || l1 == 96) continue

                    // Handle Ctrl+V paste (character code 22 is Ctrl+V, or key code 67 is 'V' when mapped)
                    // Also check for 'v' or 'V' character (118/86) with Ctrl held as a fallback
                    val isCtrlV = l1 == 22 || (Client.controlIsDown && (l1 == 118 || l1 == 86 || keyCode == 67))
                    if (isCtrlV) {
                        val clipboardText = Client.getClipboardContents()
                        if (clipboardText.isNotEmpty()) {
                            // Filter clipboard text to only include valid characters
                            val filteredText = clipboardText.filter { validUserPassChars.contains(it) }
                            if (loginScreenCursorPos == 0) {
                                client.myUsername = (client.myUsername + filteredText).take(12)
                            } else if (loginScreenCursorPos == 1) {
                                client.myPassword = (client.myPassword + filteredText).take(15)
                            }
                        }
                        continue
                    }

                    var flag1 = false
                    for (element in validUserPassChars) {
                        if (l1 != element.code) continue
                        flag1 = true
                        break
                    }

                    if (loginScreenCursorPos == 0) {
                        client.myUsername = processLoginInput(client.myUsername, l1, flag1, 12, { loginScreenCursorPos = 1 }, { loginScreenCursorPos = 1 })
                    } else if (loginScreenCursorPos == 1) {
                        client.myPassword = processLoginInput(client.myPassword, l1, flag1, 15, { loginScreenCursorPos = 0 }, { client.login(client.myUsername, client.password, false) })
                    }
                }
            }
            else -> {}
        }
    }


    private fun processLoginInput(currentInput: String, keyCode: Int, shouldAppendChar: Boolean, maxLength: Int, tabAction: Runnable?, enterAction: Runnable?): String {
        var updatedInput = currentInput

        if (keyCode == 8 && updatedInput.isNotEmpty()) { // Backspace
            updatedInput = updatedInput.substring(0, updatedInput.length - 1)
        } else if (keyCode == 9) { // Tab
            tabAction?.run()
        } else if (keyCode == 10 || keyCode == 13) { // Enter or Return
            enterAction?.run()
            return updatedInput
        }

        if (shouldAppendChar) updatedInput += keyCode.toChar()

        if (updatedInput.length > maxLength) {
            updatedInput = updatedInput.substring(0, maxLength)
        }

        return updatedInput
    }


    private val validUserPassChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\u00a3$%^&*()-_=+[{]};:'@#~,<.>/?\\| "

    private fun flash(state: Int): String = if ((loginScreenCursorPos == state) and (Client.loopCycle % 40 < 20)) "|" else ""

    fun missingUsername(): Boolean {
        if (client.username == null || client.username.isEmpty()) {
            loginScreenCursorPos = 0
            return true
        }
        return false
    }

    fun missingPassword(): Boolean {
        if (client.password == null || client.password.isEmpty()) {
            loginScreenCursorPos = 0
            return true
        }
        return false
    }

}

