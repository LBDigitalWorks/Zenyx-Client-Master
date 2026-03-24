package com.client.broadcast;

import com.client.Client;

public class BroadcastManager {

    public static Broadcast[] broadcasts = new Broadcast[1000];

    public static void removeIndex(int broadcastIndex) {
        if (broadcasts[broadcastIndex] != null) {
            broadcasts[broadcastIndex] = null;
        }
    }

    public static Broadcast getCurrentBroadcast() {
        Broadcast b = broadcasts[getHighestIndex()];
        if (b == null || b.message == null)
            return null;
        if (b.time < System.currentTimeMillis())
            return null;
        return b;
    }

    public static void addBoradcast(Broadcast broadcast) {
        broadcasts[broadcast.index] = broadcast;
    }

    public static Broadcast getBroadCast(String message) {
        for (Broadcast b : broadcasts) {
            if (b != null && b.message != null) {
                if (b.message.equalsIgnoreCase(message))
                    return b;
            }
        }
        return null;
    }

    private static int getBroadcastSize() {
        int count = 0;
        for (Broadcast b : broadcasts) {
            if (b != null && b.message != null) {
                count++;
            }
        }
        return count;
    }

    public static int getHighestIndex() {
        int highestIndex = 0;
        for (Broadcast b : broadcasts) {
            if (b != null && b.message != null) {
                if (highestIndex < b.index)
                    highestIndex = b.index;
            }
        }
        return highestIndex;
    }

    public static boolean isDisplayed() {
        Broadcast b = broadcasts[getHighestIndex()];
        if (b == null || b.message == null) {
            return false;
        }
        return b.time > System.currentTimeMillis();
    }

    private static String[] wrapMessage(Client client, String message) {
        int baseX = !Client.instance.isResized() ? 5 : 0;
        int maxLineWidth = client.getChatboxTextWidth();
        int availableWidth = Math.max(20, maxLineWidth - baseX);
        String[] lines = client.newRegularFont.wrap(message, availableWidth);
        return lines.length == 0 ? new String[] { "" } : lines;
    }

    public static String[] getWrappedLines(Client client) {
        Broadcast b = getCurrentBroadcast();
        if (b == null || b.message == null) {
            return new String[0];
        }
        return wrapMessage(client, b.getDecoratedMessage());
    }

    public static int getWrappedLineCount(Client client) {
        return getWrappedLines(client).length;
    }

    public static void display(Client client) {

        Broadcast b = getCurrentBroadcast();
        if (b == null || b.message == null) {
            return;
        }

        int yPosition = (!Client.instance.isResized() ? 330 : client.canvasHeight - 173);
        if (client.isServerUpdating())
            yPosition -= 13;
        int baseX = !Client.instance.isResized() ? 5 : 0;
        String[] wrappedLines = wrapMessage(client, b.getDecoratedMessage());
        int linesDrawn = wrappedLines.length;
        for (int lineIndex = 0; lineIndex < linesDrawn; lineIndex++) {
            int lineOffset = linesDrawn - 1 - lineIndex;
            int lineYPos = yPosition - lineOffset * 13;
            client.newRegularFont.drawBasicString(wrappedLines[lineIndex], baseX, lineYPos, 0xffff00, 0);
        }
    }
}
