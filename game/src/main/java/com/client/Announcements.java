package com.client;


import com.client.engine.GameEngine;

/**
 * @author C.T for koranes
 */

public class Announcements {

    public static int announcementFade = 0;
    public static int announcementMovement = GameEngine.canvasWidth - 2;

    private static final String[] announcements = {
            "In the ashes of bitter tragedy, lie the seeds of astonishing rebirth. On your way to greatness, you will fall, but like a Phoenix rising from the ashes, you too shall rise again.",
            "Welcome to Zenyx!  Founder: Sussy     Founder: BananaStreet",
    };

    static int ticks = 0;
    static int maximum = announcements.length;

    public static void displayAnnouncements() {
        announcementMovement--;
        announcementFade++;

        if (announcementMovement < -announcements[ticks].length() - 10) {
            announcementMovement = GameEngine.canvasWidth + 2;
            ticks++;
            if (ticks >= maximum) {
                ticks = 0;
            }
        }

        TextDrawingArea.drawAlphaGradient(0, 0, GameEngine.canvasWidth, 25, 0x0000, 0x7c5ec7, 205 - (int) (50 * Math.sin(announcementFade / 20.0)));
        Client.instance.smallText.method389(true, announcementMovement, 0xffffff, announcements[ticks], 17);
    }


}

