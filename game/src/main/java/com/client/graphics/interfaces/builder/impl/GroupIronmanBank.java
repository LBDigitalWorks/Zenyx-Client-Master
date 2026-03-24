package com.client.graphics.interfaces.builder.impl;

import com.client.Sprite;
import com.client.TextDrawingArea;
import com.client.Client;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.Bank;
import com.util.AssetUtils;

public class GroupIronmanBank extends RSInterface {

    public static final int BANK_INTERFACE_ID = 48670;
    public static final int MAIN_CONTAINER = 48675;
    public static final int SCROLLABLE_INTERFACE_ID = 68004;
    public static final int DEPOSIT_WORN_BUTTON = 68005;
    public static final int DEPOSIT_CARRIED_BUTTON = 68006;
    public static final int PLACEHOLDERS_BUTTON = 68007;
    public static final int SEARCH_BUTTON = 68008;
    public static final int QUANTITY_ONE_BUTTON = 68009;
    public static final int QUANTITY_TEN_BUTTON = 68010;
    public static final int QUANTITY_X_BUTTON = 68011;
    public static final int QUANTITY_FIFTY_BUTTON = 68012;
    public static final int QUANTITY_ALL_BUTTON = 68013;
    public static final int INSERT_MODE_BUTTON = 68014;
    public static final int NOTE_MODE_BUTTON = 68015;

    public static final int BACKGROUND = 68001;
    public static final int TITLE_LABEL = 68002;
    public static final int GROUP_NAME_TEXT = 48677;
    public static RSInterface bankScrollable;
    private static boolean insertModeEnabled;
    private static boolean noteModeEnabled;

    public static boolean isBankContainer(RSInterface rsInterface) {
        return rsInterface != null && rsInterface.id == MAIN_CONTAINER;
    }

    public static boolean isSearchingBank() {
        return Client.inputDialogState == 9 && Client.instance.openInterfaceID == BANK_INTERFACE_ID;
    }

    public static void handleButton(int buttonId) {
        if (buttonId == SEARCH_BUTTON) {
            if (isSearchingBank()) {
                closeSearch();
            } else {
                openSearch();
            }
        }
    }

    public static void drawOnBank(RSInterface rsInterface, int x, int y) {
    }

    public static void setupMainTab(RSInterface rsInterface, int x, int y) {
        if (bankScrollable == null || rsInterface.id != MAIN_CONTAINER) {
            return;
        }

        bankScrollable.scrollMax = Math.max(rsInterface.getItemContainerHeight() + 8, bankScrollable.height + 1);
    }

    public static void onConfigChanged(int config, int value) {
        if (config == 222) {
            updateQuantityButtonStates(value);
            return;
        }

        if (config == 304 && value == 0) {
            insertModeEnabled = true;
        } else if (config == 305 && value == 0) {
            insertModeEnabled = false;
        } else if (config == 116 && value == 0) {
            noteModeEnabled = true;
        } else if (config == 117 && value == 0) {
            noteModeEnabled = false;
        }

        updateModeButtonStates();
    }

    private static void openSearch() {
        Client.inputDialogState = 9;
    }

    private static void closeSearch() {
        Client.inputDialogState = 0;
        Bank.searchingBankString = "";
    }

    public static int remapButton(int buttonId) {
        if (buttonId == INSERT_MODE_BUTTON) {
            return insertModeEnabled ? 58002 : 18929;
        }

        if (buttonId == NOTE_MODE_BUTTON) {
            return noteModeEnabled ? 58010 : 18933;
        }

        if (buttonId == QUANTITY_ONE_BUTTON) {
            return 58066;
        }

        if (buttonId == QUANTITY_TEN_BUTTON) {
            return 58070;
        }

        if (buttonId == QUANTITY_X_BUTTON) {
            return 58072;
        }

        if (buttonId == QUANTITY_ALL_BUTTON) {
            return 58074;
        }

        if (buttonId == PLACEHOLDERS_BUTTON) {
            return 58014;
        }

        if (buttonId == DEPOSIT_CARRIED_BUTTON) {
            return 58018;
        }

        if (buttonId == DEPOSIT_WORN_BUTTON) {
            return 58026;
        }

        return buttonId;
    }

    public void bank(TextDrawingArea[] tda) {
        RSInterface rs = addInterface(BANK_INTERFACE_ID);
        rs.alignmentPolicy = AlignPolicy.CENTER;

        addSprite(BACKGROUND, new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/groupbg.png")));
        addText(TITLE_LABEL, "Group Storage:", tda, 2, 0xD3B67A, false, true);
        addText(GROUP_NAME_TEXT, "Unknown", tda, 2, 0xFFFFFF, false, true);
        addInsertModeButton();
        addNoteModeButton();
        addQuantityButton(QUANTITY_ONE_BUTTON, "Default quantity: 1", "interfaces/groupstorage/qty_1.png", "interfaces/groupstorage/qty_1_hover.png");
        addQuantityButton(QUANTITY_TEN_BUTTON, "Default quantity: 10", "interfaces/groupstorage/qty_10.png", "interfaces/groupstorage/qty_10_hover.png");
        addQuantityButton(QUANTITY_X_BUTTON, "Default quantity: X", "interfaces/groupstorage/qty_x.png", "interfaces/groupstorage/qty_x_hover.png");
        addQuantityButton(QUANTITY_FIFTY_BUTTON, "Default quantity: 50", "interfaces/groupstorage/qty_50.png", "interfaces/groupstorage/qty_50_hover.png");
        addQuantityButton(QUANTITY_ALL_BUTTON, "Default quantity: All", "interfaces/groupstorage/qty_all.png", "interfaces/groupstorage/qty_all_hover.png");
        addSearchButton();
        addPlaceholdersButton();
        addDepositCarriedButton();
        addDepositWornButton();

        bankScrollable = addInterface(SCROLLABLE_INTERFACE_ID);
        bankScrollable.height = 240;
        bankScrollable.width = 437;
        bankScrollable.scrollMax = 241;
        setChildren(1, bankScrollable);

        RSInterface container = addInventoryContainer(MAIN_CONTAINER, 10, 100, 12, 4, true);
        container.actions = new String[] {"Withdraw 1", "Withdraw 5", "Withdraw 10", "Withdraw All", "Withdraw X", "Withdraw All but one"};
        container.contentType = 206;
        bankScrollable.child(0, MAIN_CONTAINER, 0, 0);

        setChildren(15, rs);
        setBounds(BACKGROUND, 16, 7, 0, rs);
        setBounds(TITLE_LABEL, 174, 18, 1, rs);
        setBounds(GROUP_NAME_TEXT, 278, 18, 2, rs);
        setBounds(SCROLLABLE_INTERFACE_ID, 41, 44, 3, rs);
        setBounds(INSERT_MODE_BUTTON, 26, 285, 4, rs);
        setBounds(NOTE_MODE_BUTTON, 64, 285, 5, rs);
        setBounds(QUANTITY_ONE_BUTTON, 124, 285, 6, rs);
        setBounds(QUANTITY_TEN_BUTTON, 162, 285, 7, rs);
        setBounds(QUANTITY_X_BUTTON, 200, 285, 8, rs);
        setBounds(QUANTITY_FIFTY_BUTTON, 238, 285, 9, rs);
        setBounds(QUANTITY_ALL_BUTTON, 276, 285, 10, rs);
        setBounds(SEARCH_BUTTON, 340, 285, 11, rs);
        setBounds(PLACEHOLDERS_BUTTON, 378, 285, 12, rs);
        setBounds(DEPOSIT_CARRIED_BUTTON, 416, 285, 13, rs);
        setBounds(DEPOSIT_WORN_BUTTON, 454, 285, 14, rs);

        if (Client.instance != null) {
            insertModeEnabled = Client.instance.variousSettings[305] == 1 || Client.instance.variousSettings[304] == 0;
            updateQuantityButtonStates(Client.instance.variousSettings[222]);
            updateModeButtonStates();
        }
    }

    private static void updateQuantityButtonStates(int value) {
        interfaceCache[QUANTITY_ONE_BUTTON].active = value == 0;
        interfaceCache[QUANTITY_TEN_BUTTON].active = value == 2;
        interfaceCache[QUANTITY_X_BUTTON].active = value == 3;
        interfaceCache[QUANTITY_FIFTY_BUTTON].active = false;
        interfaceCache[QUANTITY_ALL_BUTTON].active = value == 4;
    }

    private static void updateModeButtonStates() {
        interfaceCache[INSERT_MODE_BUTTON].active = insertModeEnabled;
        interfaceCache[NOTE_MODE_BUTTON].active = noteModeEnabled;
    }

    private void addInsertModeButton() {
        RSInterface button = addInterface(INSERT_MODE_BUTTON);
        button.type = 5;
        button.atActionType = 1;
        button.tooltip = "Switch to insert items mode";
        button.sprite1 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/insert.png"));
        button.sprite2 = button.sprite1;
        button.width = button.sprite1.myWidth;
        button.height = button.sprite1.myHeight;
    }

    private void addNoteModeButton() {
        RSInterface button = addInterface(NOTE_MODE_BUTTON);
        button.type = 5;
        button.atActionType = 1;
        button.tooltip = "Switch to note withdrawal mode";
        button.sprite1 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/note.png"));
        button.sprite2 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/note_clicked.png"));
        button.width = button.sprite1.myWidth;
        button.height = button.sprite1.myHeight;
    }

    private void addQuantityButton(int id, String tooltip, String normalPath, String hoverPath) {
        RSInterface button = addInterface(id);
        button.type = 5;
        button.atActionType = 1;
        button.tooltip = tooltip;
        button.sprite1 = new Sprite(AssetUtils.INSTANCE.getResource(normalPath));
        button.sprite2 = new Sprite(AssetUtils.INSTANCE.getResource(hoverPath));
        button.width = button.sprite1.myWidth;
        button.height = button.sprite1.myHeight;
    }

    private void addSearchButton() {
        RSInterface button = addInterface(SEARCH_BUTTON);
        button.type = 5;
        button.atActionType = 1;
        button.tooltip = "Search bank";
        button.sprite1 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/search.png"));
        button.sprite2 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/search_hover.png"));
        button.width = button.sprite1.myWidth;
        button.height = button.sprite1.myHeight;
    }

    private void addPlaceholdersButton() {
        RSInterface button = addInterface(PLACEHOLDERS_BUTTON);
        button.type = 5;
        button.atActionType = 1;
        button.tooltip = "Enable/disable placeholders";
        button.sprite1 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/placeholders.png"));
        button.sprite2 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/placeholders_hover.png"));
        button.width = button.sprite1.myWidth;
        button.height = button.sprite1.myHeight;
    }

    private void addDepositCarriedButton() {
        RSInterface button = addInterface(DEPOSIT_CARRIED_BUTTON);
        button.type = 5;
        button.atActionType = 1;
        button.tooltip = "Deposit carried items";
        button.sprite1 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/deposit_carried.png"));
        button.sprite2 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/deposit_carried_hover.png"));
        button.width = button.sprite1.myWidth;
        button.height = button.sprite1.myHeight;
    }

    private void addDepositWornButton() {
        RSInterface button = addInterface(DEPOSIT_WORN_BUTTON);
        button.type = 5;
        button.atActionType = 1;
        button.tooltip = "Deposit worn items";
        button.sprite1 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/deposit_worn.png"));
        button.sprite2 = new Sprite(AssetUtils.INSTANCE.getResource("interfaces/groupstorage/deposit_worn_hover.png"));
        button.width = button.sprite1.myWidth;
        button.height = button.sprite1.myHeight;
    }
}

