package com.client;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public enum PlayerRights {

    PLAYER(0, "000000"),
    VETERAN(1, "000000"),
    BRONZE_BLOOD(2, "1B1ABC"),
    IRON_BLOOD(3, "118120", BRONZE_BLOOD),
    STEEL_BLOOD(4, "6D0000", IRON_BLOOD),
    MITHRIL_BLOOD(5, "005C6D", STEEL_BLOOD),
    ADAMANT_BLOOD(6, "409438", MITHRIL_BLOOD),
    RUNE_BLOOD(7, "0D8CB8", ADAMANT_BLOOD),
    GREEN_BLOOD(8, "0C8200", RUNE_BLOOD),
    BLUE_BLOOD(9, "0042FF", GREEN_BLOOD),
    LAVA_BLOOD(10, "DB1919", BLUE_BLOOD),
    BLACK_BLOOD(11, "000000", LAVA_BLOOD),
    RED_BLOOD(12, "E89400", BLACK_BLOOD),
    YOUTUBER(13, "FE0018"),
    HELPER(14, "004080"),
    MODERATOR(15, "0000FF", HELPER),
    ADMINISTRATOR(16, "F5FF0F", MODERATOR),
    GAME_DEVELOPER(17, "544FBB", ADMINISTRATOR),
    MANAGER(18, "F5FF0F", GAME_DEVELOPER),
    OWNER(19, "F5FF0F", MANAGER),
    HIDDEN_OWNER(20, "000000", OWNER),
    ROGUE(21, "437100"),
    IRONMAN(22, "3A3A3A"),
    ULTIMATE_IRONMAN(23, "717070"),
    HC_IRONMAN(24, "60201F"),
    ROGUE_IRONMAN(25, "60201F"),
    ROGUE_HARDCORE_IRONMAN(26, "60201F"),
    GROUP_IRONMAN(27, "60201F"),
    OSRS(28, "437100"),
    CO_OWNER(29, "437100", OWNER),
    EVENT_MANAGER(30, "437100", MANAGER),
    GFX_ARTIST(31, "437100"),
    BUG_TESTER(32, "004080"),
    MEDIA_MANAGER(33, "437100", HELPER),
    MODELER(34, "437100", MODERATOR),
    MAP_MAKER(35, "437100", MODERATOR),
    LEGENDARY(36, "60201F"),
    IRON_LEGENDARY(37, "60201F"),
    HC_IRON_LEGENDARY(38, "60201F"),
    WILDERNESS_MAN(39, "60201F");

    /**
     * The level of rights that define this
     */
    @Getter
    private final int rightsId;

    /**
     * The rights inherited by this right
     */
    private final List<PlayerRights> inherited;

    /**
     * The color associated with the right
     */
    private final String color;

    /**
     * Creates a new right with a value to differentiate it between the others
     *
     * @param right the right required
     * @param color a color thats used to represent the players name when displayed
     * @param inherited the right or rights inherited with this level of right
     */
    PlayerRights(int right, String color, PlayerRights... inherited) {
        this.rightsId = right;
        this.inherited = Arrays.asList(inherited);
        this.color = color;

    }

    public boolean isStaffPosition() {
        return this == HELPER || this == ADMINISTRATOR || this == MODERATOR || this == OWNER || this == GAME_DEVELOPER || this == MANAGER || this == MEDIA_MANAGER || this == CO_OWNER|| this == EVENT_MANAGER;
    }

    public int spriteId() {
        return rightsId -1;
    }

    public int crownId() {
        return rightsId;
    }

    public boolean hasCrown() {
        return this != PlayerRights.PLAYER && this != PlayerRights.HIDDEN_OWNER;
    }

    public static final EnumSet[] DISPLAY_GROUPS = {
            EnumSet.of(HELPER, MODERATOR, ADMINISTRATOR, MANAGER, GFX_ARTIST, MODELER, MAP_MAKER,
                    MEDIA_MANAGER, EVENT_MANAGER, GAME_DEVELOPER, CO_OWNER, OWNER, BRONZE_BLOOD, IRON_BLOOD, STEEL_BLOOD, MITHRIL_BLOOD,
                    ADAMANT_BLOOD, RUNE_BLOOD, GREEN_BLOOD, BLUE_BLOOD, LAVA_BLOOD, BLACK_BLOOD, RED_BLOOD, YOUTUBER, HIDDEN_OWNER),

            EnumSet.of(BUG_TESTER, IRONMAN, ULTIMATE_IRONMAN, OSRS, HC_IRONMAN, ROGUE, ROGUE_HARDCORE_IRONMAN, ROGUE_IRONMAN, GROUP_IRONMAN, LEGENDARY, IRON_LEGENDARY, HC_IRON_LEGENDARY, WILDERNESS_MAN)
    };

    public static PlayerRights forRightsValue(int rightsValue) {
        Optional<PlayerRights> rights = Arrays.stream(PlayerRights.values()).filter(right -> right.getRightsId() == rightsValue).findFirst();
        if (rights.isPresent()) {
            return rights.get();
        } else {
            System.err.println("No rights for value " + rightsValue);
            return PlayerRights.PLAYER;
        }
    }

    public static List<PlayerRights> getDisplayedRights(PlayerRights[] set) {
        List<PlayerRights> rights = new ArrayList<>();

        for (PlayerRights right : set) {
            if (DISPLAY_GROUPS[0].contains(right)) {
                rights.add(right);
                break; // Only displaying one crown from this group!
            }
        }

        for (PlayerRights right : set) {
            if (DISPLAY_GROUPS[1].contains(right)) {
                if (rights.size() < 2) {
                    rights.add(right);
                }
            }
        }
        return rights;
    }

    public static PlayerRights[] ordinalsToArray(int[] ordinals) {
        PlayerRights[] rights = new PlayerRights[ordinals.length];
        for (int index = 0; index < ordinals.length; index++) {
            rights[index] = PlayerRights.values()[ordinals[index]];
        }
        return rights;
    }

    public static Pair<Integer, PlayerRights[]> readRightsFromPacket(Buffer inStream) {
        int rightsAmount = inStream.readUnsignedByte();
        int[] ordinals = new int[rightsAmount];
        for (int right = 0; right < rightsAmount; right++) {
            ordinals[right] = inStream.readUnsignedByte();
        }
        return Pair.of(rightsAmount, PlayerRights.ordinalsToArray(ordinals));
    }

    public static boolean hasRightsOtherThan(PlayerRights[] rights, PlayerRights playerRight) {
        return Arrays.stream(rights).anyMatch(right -> right != playerRight);
    }

    public static boolean hasRights(PlayerRights[] rights, PlayerRights playerRights) {
        return Arrays.stream(rights).anyMatch(right -> right == playerRights);
    }

    public static boolean hasRightsLevel(PlayerRights[] rights, int rightsId) {
        return Arrays.stream(rights).anyMatch(right -> right.getRightsId() >= rightsId);
    }

    public static boolean hasRightsBetween(PlayerRights[] rights, int low, int high) {
        return Arrays.stream(rights).anyMatch(right -> right.getRightsId() > low && right.getRightsId() < high);
    }

    public static String buildCrownString(List<PlayerRights> rights) {
        return buildCrownString(rights.toArray(new PlayerRights[0]));
    }

    public static String buildCrownString(PlayerRights[] rights) {
        StringBuilder builder = new StringBuilder();
        for (PlayerRights right : rights) {
            if (right.hasCrown()) {
                builder.append("@cr" + right.crownId() + "@");
            }
        }
        return builder.toString();
    }

}
