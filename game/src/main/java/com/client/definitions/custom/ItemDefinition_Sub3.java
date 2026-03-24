package com.client.definitions.custom;

import com.client.definitions.ItemDefinition;

public class ItemDefinition_Sub3 {

    public ItemDefinition_Sub3() {
    }

    public static ItemDefinition itemDef(int i, ItemDefinition itemDef) {
        if (i == 758) {
            itemDef.name = "Mini ";//+ Client.localPlayer.displayName;
            itemDef.animateInventory = true;
            itemDef.spriteScale = 2500;
            //itemDef.spriteXRotation = 5;
            //itemDef.spriteYRotation = 150;
            itemDef.spriteZRotation = 1800;
            itemDef.modelYOffset = 0;
            itemDef.groundActions = new String[]{null, null, "Pick Up", null, null};
            itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
        }

        if (i == 26017) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Blue Sigil";
            itemDef.description = "Test Things.";
            itemDef.modelId = 42895;
            itemDef.spriteScale = 900;
            itemDef.spriteYRotation = 117;
            itemDef.spriteZRotation = 105;
            itemDef.spriteXRotation = 0;
            itemDef.primaryMaleModel = 42895;
            itemDef.primaryFemaleModel = 42895;
            itemDef.modelYOffset = -75;
            itemDef.modelXOffset = 20;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 26002) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Red Sigil";
            itemDef.description = "Test Things.";
            itemDef.modelId = 42902;
            itemDef.spriteScale = 900;
            itemDef.spriteYRotation = 117;
            itemDef.spriteZRotation = 105;
            itemDef.spriteXRotation = 0;
            itemDef.primaryMaleModel = 42902;
            itemDef.primaryFemaleModel = 42902;
            itemDef.modelYOffset = -75;
            itemDef.modelXOffset = -20;
            itemDef.originalModelColors = new short[]{10417, 10413, 10407, 31682};
            itemDef.modifiedModelColors = new short[]{10334, 10326, 10316, 962};
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 19480) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63693;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63692;
            itemDef.primaryFemaleModel = 63135;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Kaida Cape";
            itemDef.description = "Kaida Cape made by the gods! ";
        }
        if (i == 29680) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63695;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63694;
            itemDef.primaryFemaleModel = 63065;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Scorched Cape";
            itemDef.description = "Scorched Cape made for Over Power Players ";
        }
        if (i == 29364) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Elder Webweaver Bow"; //Name
            itemDef.description = "A powerful power bow."; //Description
            itemDef.modelId = 58277;
            itemDef.spriteScale = 1200;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63492;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 63493;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29365) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = (short) 39551;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = (short) 39551;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = (short) 39551;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = (short) 39551;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29366) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = (short) 40959;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = (short) 40959;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = (short) 40959;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = (short) 40959;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29367) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = (short) 48895;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = (short) 48895;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = (short) 48895;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = (short) 48895;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29368) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = (short) 54194;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = (short) 54194;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = (short) 54194;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = (short) 54194;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29369) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = (short) 62463;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = (short) 62463;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = (short) 62463;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = (short) 62463;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29370) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = 32716;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = 32716;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = 32716;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = 32716;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29371) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = 11237;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = 11237;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = 11237;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = 11237;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29372) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = 29644;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = 29644;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = 29644;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = 29644;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29373) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = (short) 54169;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = (short) 54169;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = (short) 54169;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = (short) 54169;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29374) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = 50;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = 50;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = 50;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = 50;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29375) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gnome scarf"; //Name
            itemDef.description = "Its an Gnome scarf"; //Description
            itemDef.originalModelColors = new short[4];
            itemDef.modifiedModelColors = new short[4];
            itemDef.originalModelColors[0] = 127;
            itemDef.modifiedModelColors[0] = (short) 43775;
            itemDef.originalModelColors[1] = 111;
            itemDef.modifiedModelColors[1] = (short) 43775;
            itemDef.originalModelColors[2] = 119;
            itemDef.modifiedModelColors[2] = (short) 43775;
            itemDef.originalModelColors[3] = 108;
            itemDef.modifiedModelColors[3] = (short) 43775;
            itemDef.modelId = 17125;
            itemDef.spriteScale = 919;
            itemDef.spriteYRotation = 595;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 17155;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 17157;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29438) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, "Uncharge", "Unload"};
            itemDef.name = "Elvarg blowpipe"; //Name
            itemDef.description = "Elvarg blowpipe"; //Description
            itemDef.modelId = 63069;
            itemDef.spriteScale = 1158;
            itemDef.spriteYRotation = 768;
            itemDef.spriteZRotation = 189;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 4;
            itemDef.primaryMaleModel = 63539;
            itemDef.primaryFemaleModel = 63036;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 120000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 8858) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
            itemDef.name = "Elvarg blowpipe(empty)"; //Name
            itemDef.description = "Its an Masori blowpipe"; //Description
            itemDef.modelId = 63035;
            itemDef.spriteScale = 1158;
            itemDef.spriteYRotation = 768;
            itemDef.spriteZRotation = 189;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -7;
            itemDef.spriteTranslateY = 4;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 120000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 782) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Pernix Cowl"; //Name
            itemDef.description = "Its an Pernix Cowl"; //Description
            itemDef.modelId = 65060;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
        }
        if (i == 33550) //ID
        {//used to convert old to new
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Pernix Cowl"; //Name
            itemDef.description = "Its an Pernix Cowl"; //Description
            itemDef.modelId = 65060;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
        }

        if (i == 783) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Pernix body"; //Name
            itemDef.description = "Its an Pernix body"; //Description
            itemDef.modelId = 43140;
            itemDef.spriteScale = 1420;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 50;
            itemDef.primaryMaleModel = 43140;
            itemDef.primaryFemaleModel = 58184;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 33551) //ID
        {//used to convert old to new
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Pernix body"; //Name
            itemDef.description = "Its an Pernix body"; //Description
            itemDef.modelId = 43140;
            itemDef.spriteScale = 1420;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 50;
        }
        if (i == 784) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Pernix Chaps"; //Name
            itemDef.description = "Its an Pernix Chaps"; //Description
            itemDef.modelId = 43131;
            itemDef.spriteScale = 1789;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 43131;
            itemDef.primaryFemaleModel = 58185;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 33552) //ID
        {//used to convert old to new
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Pernix Chaps"; //Name
            itemDef.description = "Its an Pernix Chaps"; //Description
            itemDef.modelId = 43131;
            itemDef.spriteScale = 1789;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
        }
        if (i == 33553) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Virtus Robe Bottoms"; //Name
            itemDef.description = "Its an Virtus Robe Bottoms"; //Description
            itemDef.modelId = 43239;
            itemDef.spriteScale = 1789;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 43162;
            itemDef.primaryFemaleModel = 43162;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 33555) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Virtus Mask"; //Name
            itemDef.description = "Its an Virtus Mask"; //Description
            itemDef.modelId = 43233;
            itemDef.spriteScale = 1389;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 43113;
            itemDef.primaryFemaleModel = 43113;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 33554) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Virtus Robe Top"; //Name
            itemDef.description = "Its an Virtus Robe Top"; //Description
            itemDef.modelId = 43236;
            itemDef.spriteScale = 1200;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 43143;
            itemDef.primaryFemaleModel = 43143;
            itemDef.secondaryMaleModel = 43137;
            itemDef.secondaryFemaleModel = 43137;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 29622) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos chestplate (B)"; //Name
            itemDef.description = "Its an Bandos chestplate (B)"; //Description
            itemDef.originalModelColors = new short[6];
            itemDef.modifiedModelColors = new short[6];
            itemDef.originalModelColors[0] = 9403;
            itemDef.modifiedModelColors[0] = (short) 43980;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = (short) 43980;
            itemDef.originalModelColors[2] = 8367;
            itemDef.modifiedModelColors[2] = (short) 43980;
            itemDef.originalModelColors[3] = 8384;
            itemDef.modifiedModelColors[3] = (short) 43980;
            itemDef.originalModelColors[4] = 8375;
            itemDef.modifiedModelColors[4] = (short) 43980;
            itemDef.originalModelColors[5] = 8379;
            itemDef.modifiedModelColors[5] = (short) 43980;
            itemDef.modelId = 28042;
            itemDef.spriteScale = 984;
            itemDef.spriteYRotation = 501;
            itemDef.spriteZRotation = 6;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 1;
            itemDef.spriteTranslateY = 4;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryMaleModel = 27636;
            itemDef.primaryFemaleModel = 27644;
            itemDef.secondaryMaleModel = 28826;
            itemDef.secondaryFemaleModel = 28827;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 290010;
        }
        if (i == 29623) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos tassets (B)"; //Name
            itemDef.description = "Its an Bandos tassets (B)"; //Description
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 8390;
            itemDef.modifiedModelColors[0] = (short) 43980;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = (short) 43980;
            itemDef.modelId = 28047;
            itemDef.spriteScale = 854;
            itemDef.spriteYRotation = 540;
            itemDef.spriteZRotation = 2039;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 3;
            itemDef.spriteTranslateY = 3;
            itemDef.primaryMaleModel = 27625;
            itemDef.primaryFemaleModel = 27640;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289910;
        }
        if (i == 29624) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos boots (B)"; //Name
            itemDef.description = "Its an Bandos boots (B)"; //Description
            itemDef.originalModelColors = new short[1];
            itemDef.modifiedModelColors = new short[1];
            itemDef.originalModelColors[0] = 10291;
            itemDef.modifiedModelColors[0] = (short) 43980;
            itemDef.modelId = 28040;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 27637;
            itemDef.primaryFemaleModel = 19951;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
        }
        if (i == 29625) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos chestplate (G)"; //Name
            itemDef.description = "Its an Bandos chestplate (G)"; //Description
            itemDef.originalModelColors = new short[6];
            itemDef.modifiedModelColors = new short[6];
            itemDef.originalModelColors[0] = 9403;
            itemDef.modifiedModelColors[0] = 22501;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = 22501;
            itemDef.originalModelColors[2] = 8367;
            itemDef.modifiedModelColors[2] = 22501;
            itemDef.originalModelColors[3] = 8384;
            itemDef.modifiedModelColors[3] = 22501;
            itemDef.originalModelColors[4] = 8375;
            itemDef.modifiedModelColors[4] = 22501;
            itemDef.originalModelColors[5] = 8379;
            itemDef.modifiedModelColors[5] = 22501;
            itemDef.modelId = 28042;
            itemDef.spriteScale = 984;
            itemDef.spriteYRotation = 501;
            itemDef.spriteZRotation = 6;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.spriteTranslateY = 4;
            itemDef.primaryMaleModel = 27636;
            itemDef.primaryFemaleModel = 27644;
            itemDef.secondaryMaleModel = 28826;
            itemDef.secondaryFemaleModel = 28827;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 290010;
        }
        if (i == 29626) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos tassets (G)"; //Name
            itemDef.description = "Its an Bandos tassets (G)"; //Description
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 8390;
            itemDef.modifiedModelColors[0] = 22501;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = 22501;
            itemDef.modelId = 28047;
            itemDef.spriteScale = 854;
            itemDef.spriteYRotation = 540;
            itemDef.spriteZRotation = 2039;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 3;
            itemDef.spriteTranslateY = 3;
            itemDef.primaryMaleModel = 27625;
            itemDef.primaryFemaleModel = 27640;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289910;
        }
        if (i == 29627) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos boots (G)"; //Name
            itemDef.description = "Its an Bandos boots (G)"; //Description
            itemDef.originalModelColors = new short[1];
            itemDef.modifiedModelColors = new short[1];
            itemDef.originalModelColors[0] = 10291;
            itemDef.modifiedModelColors[0] = 22501;
            itemDef.modelId = 28040;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 27637;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 19951;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
        }
        if (i == 29628) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos chestplate (R)"; //Name
            itemDef.description = "Its an Bandos chestplate (R)"; //Description
            itemDef.originalModelColors = new short[6];
            itemDef.modifiedModelColors = new short[6];
            itemDef.originalModelColors[0] = 9403;
            itemDef.modifiedModelColors[0] = 972;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = 972;
            itemDef.originalModelColors[2] = 8367;
            itemDef.modifiedModelColors[2] = 972;
            itemDef.originalModelColors[3] = 8384;
            itemDef.modifiedModelColors[3] = 972;
            itemDef.originalModelColors[4] = 8375;
            itemDef.modifiedModelColors[4] = 972;
            itemDef.originalModelColors[5] = 8379;
            itemDef.modifiedModelColors[5] = 972;
            itemDef.modelId = 28042;
            itemDef.spriteScale = 984;
            itemDef.spriteYRotation = 501;
            itemDef.spriteZRotation = 6;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 1;
            itemDef.spriteTranslateY = 4;
            itemDef.primaryMaleModel = 27636;
            itemDef.primaryFemaleModel = 27644;
            itemDef.secondaryMaleModel = 28826;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryFemaleModel = 28827;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 290010;
        }
        if (i == 29629) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos tassets (R)"; //Name
            itemDef.description = "Its an Bandos tassets (R)"; //Description
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 8390;
            itemDef.modifiedModelColors[0] = 972;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = 972;
            itemDef.modelId = 28047;
            itemDef.spriteScale = 854;
            itemDef.spriteYRotation = 540;
            itemDef.spriteZRotation = 2039;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 3;
            itemDef.spriteTranslateY = 3;
            itemDef.primaryMaleModel = 27625;
            itemDef.primaryFemaleModel = 27640;
            itemDef.secondaryMaleModel = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289910;
        }
        if (i == 29630) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos boots (R)"; //Name
            itemDef.description = "Its an Bandos boots (R)"; //Description
            itemDef.originalModelColors = new short[1];
            itemDef.modifiedModelColors = new short[1];
            itemDef.originalModelColors[0] = 10291;
            itemDef.modifiedModelColors[0] = 972;
            itemDef.modelId = 28040;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 27637;
            itemDef.primaryFemaleModel = 19951;
            itemDef.secondaryMaleModel = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
        }
        if (i == 29631) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos chestplate (P)"; //Name
            itemDef.description = "Its an Bandos chestplate (P)"; //Description
            itemDef.originalModelColors = new short[6];
            itemDef.modifiedModelColors = new short[6];
            itemDef.originalModelColors[0] = 9403;
            itemDef.modifiedModelColors[0] = (short) 54194;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = (short) 54194;
            itemDef.originalModelColors[2] = 8367;
            itemDef.modifiedModelColors[2] = (short) 54194;
            itemDef.originalModelColors[3] = 8384;
            itemDef.modifiedModelColors[3] = (short) 54194;
            itemDef.originalModelColors[4] = 8375;
            itemDef.modifiedModelColors[4] = (short) 54194;
            itemDef.originalModelColors[5] = 8379;
            itemDef.modifiedModelColors[5] = (short) 54194;
            itemDef.modelId = 28042;
            itemDef.spriteScale = 984;
            itemDef.spriteYRotation = 501;
            itemDef.spriteZRotation = 6;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 1;
            itemDef.spriteTranslateY = 4;
            itemDef.primaryMaleModel = 27636;
            itemDef.primaryFemaleModel = 27644;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = 28826;
            itemDef.secondaryFemaleModel = 28827;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 290010;
        }
        if (i == 29632) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos tassets (P)"; //Name
            itemDef.description = "Its an Bandos tassets (P)"; //Description
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 8390;
            itemDef.modifiedModelColors[0] = (short) 54194;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = (short) 54194;
            itemDef.modelId = 28047;
            itemDef.spriteScale = 854;
            itemDef.spriteYRotation = 540;
            itemDef.spriteZRotation = 2039;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 3;
            itemDef.spriteTranslateY = 3;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryMaleModel = 27625;
            itemDef.primaryFemaleModel = 27640;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289910;
        }
        if (i == 29633) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos boots (P)"; //Name
            itemDef.description = "Its an Bandos boots (P)"; //Description
            itemDef.originalModelColors = new short[1];
            itemDef.modifiedModelColors = new short[1];
            itemDef.originalModelColors[0] = 10291;
            itemDef.modifiedModelColors[0] = (short) 54194;
            itemDef.modelId = 28040;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 27637;
            itemDef.primaryFemaleModel = 19951;
            itemDef.secondaryMaleModel = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
        }
        if (i == 29934) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos chestplate (Pink)"; //Name
            itemDef.description = "Its an Bandos chestplate (Pink)"; //Description
            itemDef.originalModelColors = new short[6];
            itemDef.modifiedModelColors = new short[6];
            itemDef.originalModelColors[0] = 9403;
            itemDef.modifiedModelColors[0] = 767;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = 767;
            itemDef.originalModelColors[2] = 8367;
            itemDef.modifiedModelColors[2] = 767;
            itemDef.originalModelColors[3] = 8384;
            itemDef.modifiedModelColors[3] = 767;
            itemDef.originalModelColors[4] = 8375;
            itemDef.modifiedModelColors[4] = 767;
            itemDef.originalModelColors[5] = 8379;
            itemDef.modifiedModelColors[5] = 767;
            itemDef.modelId = 28042;
            itemDef.spriteScale = 984;
            itemDef.spriteYRotation = 501;
            itemDef.spriteZRotation = 6;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 1;
            itemDef.spriteTranslateY = 4;
            itemDef.primaryMaleModel = 27636;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 27644;
            itemDef.secondaryMaleModel = 28826;
            itemDef.secondaryFemaleModel = 28827;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 290010;
        }
        if (i == 29935) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos tassets (Pink)"; //Name
            itemDef.description = "Its an Bandos tassets (Pink)"; //Description
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 8390;
            itemDef.modifiedModelColors[0] = 767;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = 767;
            itemDef.modelId = 28047;
            itemDef.spriteScale = 854;
            itemDef.spriteYRotation = 540;
            itemDef.spriteZRotation = 2039;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 3;
            itemDef.spriteTranslateY = 3;
            itemDef.primaryMaleModel = 27625;
            itemDef.primaryFemaleModel = 27640;
            itemDef.secondaryMaleModel = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289910;
        }
        if (i == 29936) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos boots (Pink)"; //Name
            itemDef.description = "Its an Bandos boots (Pink)"; //Description
            itemDef.originalModelColors = new short[1];
            itemDef.modifiedModelColors = new short[1];
            itemDef.originalModelColors[0] = 10291;
            itemDef.modifiedModelColors[0] = 767;
            itemDef.modelId = 28040;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 27637;
            itemDef.primaryFemaleModel = 19951;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
        }
        if (i == 29521) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos chestplate (W)"; //Name
            itemDef.description = "Its an Bandos chestplate (W)"; //Description
            itemDef.originalModelColors = new short[6];
            itemDef.modifiedModelColors = new short[6];
            itemDef.originalModelColors[0] = 9403;
            itemDef.modifiedModelColors[0] = 100;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = 100;
            itemDef.originalModelColors[2] = 8367;
            itemDef.modifiedModelColors[2] = 100;
            itemDef.originalModelColors[3] = 8384;
            itemDef.modifiedModelColors[3] = 100;
            itemDef.originalModelColors[4] = 8375;
            itemDef.modifiedModelColors[4] = 100;
            itemDef.originalModelColors[5] = 8379;
            itemDef.modifiedModelColors[5] = 100;
            itemDef.modelId = 28042;
            itemDef.spriteScale = 984;
            itemDef.spriteYRotation = 501;
            itemDef.spriteZRotation = 6;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 1;
            itemDef.spriteTranslateY = 4;
            itemDef.primaryMaleModel = 27636;
            itemDef.primaryFemaleModel = 27644;
            itemDef.secondaryMaleModel = 28826;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryFemaleModel = 28827;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 290010;
        }
        if (i == 29522) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos tassets (W)"; //Name
            itemDef.description = "Its an Bandos tassets (W)"; //Description
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 8390;
            itemDef.modifiedModelColors[0] = 100;
            itemDef.originalModelColors[1] = 9523;
            itemDef.modifiedModelColors[1] = 100;
            itemDef.modelId = 28047;
            itemDef.spriteScale = 854;
            itemDef.spriteYRotation = 540;
            itemDef.spriteZRotation = 2039;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 3;
            itemDef.spriteTranslateY = 3;
            itemDef.primaryMaleModel = 27625;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryFemaleModel = 27640;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289910;
        }
        if (i == 29523) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bandos boots (W)"; //Name
            itemDef.description = "Its an Bandos boots (W)"; //Description
            itemDef.originalModelColors = new short[1];
            itemDef.modifiedModelColors = new short[1];
            itemDef.originalModelColors[0] = 10291;
            itemDef.modifiedModelColors[0] = 100;
            itemDef.modelId = 28040;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 27637;
            itemDef.primaryFemaleModel = 19951;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
        }
        if (i == 29916) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.primaryMaleModel = 58977;
            itemDef.spriteScale = 2200;
            itemDef.spriteYRotation = 572;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 1;
            itemDef.primaryFemaleModel = 58977;
            itemDef.modelId = 63121;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Bob Cape";
            itemDef.description = "Made by Joel";
        }
        if (i == 29855) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Sussy cape"; //Name
            itemDef.description = "Its an Sussy cape"; //Description
            itemDef.modelId = 63579;
            itemDef.spriteScale = 2232;
            itemDef.spriteYRotation = 687;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -5;
            itemDef.primaryMaleModel = 63580;
            itemDef.primaryFemaleModel = 63581;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 99000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 29855) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Sussy cape"; //Name
            itemDef.description = "Its an Sussy cape"; //Description
            itemDef.modelId = 58143;
            itemDef.spriteScale = 2232;
            itemDef.spriteYRotation = 687;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -5;
            itemDef.primaryMaleModel = 63122;
            itemDef.primaryFemaleModel = 63122;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 99000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 29998) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 7114;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 7114;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.stackable = false;
            itemDef.name = "Golden Bunny ears";
            itemDef.description = "Golden Bunny ears";
        }
        if (i == 29999) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = (short) 43968;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = (short) 43968;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Blue Bunny ears";
            itemDef.description = "Its a Blue Bunny ears";
        }
        if (i == 30100) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 950;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 950;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Red Bunny ears";
            itemDef.description = "Its a Red Bunny ears";
        }
        if (i == 30101) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = (short) 51136;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = (short) 51136;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Purple Bunny ears";
            itemDef.description = "Its a Purple Bunny ears";
        }
        if (i == 30102) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 22464;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 22464;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Green Bunny ears";
            itemDef.description = "Its a Green Bunny ears";
        }
        if (i == 30103) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 6073;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 6073;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Orange Bunny ears";
            itemDef.description = "Its a Orange Bunny ears";
        }
        if (i == 30104) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 10394;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 10394;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Barrows Bunny ears";
            itemDef.description = "Its A Barrows Bunny ears";
        }
        if (i == 30105) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 926;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 926;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Dragon Bunny ears";
            itemDef.description = "Its A Dragon Bunny ears";
        }
        if (i == 30106) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 5652;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 5652;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Bronze Bunny ears";
            itemDef.description = "Its A Bronze Bunny ears";
        }
        if (i == 30107) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 33;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 33;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Iron Bunny ears";
            itemDef.description = "Its A Iron Bunny ears";
        }
        if (i == 30108) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = (short) 43072;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = (short) 43072;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Steel Bunny ears";
            itemDef.description = "Its A Steel Bunny ears";
        }
        if (i == 30109) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 8;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 8;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Black Bunny ears";
            itemDef.description = "Its A Black Bunny ears";
        }
        if (i == 30110) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = (short) 43297;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = (short) 43297;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Mith Bunny ears";
            itemDef.description = "Its A Mith Bunny ears";
        }
        if (i == 30111) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = (short) 36133;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = (short) 36133;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Rune Bunny ears";
            itemDef.description = "Its A Rune Bunny ears";
        }
        if (i == 30112) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 21662;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 21662;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Adam Bunny ears";
            itemDef.description = "Its A Adam Bunny ears";
        }
        if (i == 30113) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 6069;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 6069;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Lava Bunny ears";
            itemDef.description = "Its A lava Bunny ears";
        }
        if (i == 30114) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 26706;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 26706;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ivandis Bunny ears";
            itemDef.description = "Its A Ivandis Bunny ears";
        }
        if (i == 30115) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = (short) 62920;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = (short) 62920;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ladies Bunny ears";
            itemDef.description = "Its A Ladies Bunny ears";
        }
        if (i == 30116) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[3];
            itemDef.modifiedModelColors = new short[3];
            itemDef.originalModelColors[0] = (short) 48596;
            itemDef.modifiedModelColors[0] = 10394;
            itemDef.originalModelColors[1] = (short) 37196;
            itemDef.modifiedModelColors[1] = 10394;
            itemDef.originalModelColors[2] = (short) 53167;
            itemDef.modifiedModelColors[2] = 10394;
            itemDef.modelId = 1781;
            itemDef.spriteScale = 840;
            itemDef.spriteYRotation = 612;
            itemDef.spriteZRotation = 816;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 677;
            itemDef.primaryFemaleModel = 677;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Barrows Flowers";
            itemDef.description = "Its A Barrow Flower";
        }
        if (i == 30117) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[3];
            itemDef.modifiedModelColors = new short[3];
            itemDef.originalModelColors[0] = (short) 48596;
            itemDef.modifiedModelColors[0] = 926;
            itemDef.originalModelColors[1] = (short) 37196;
            itemDef.modifiedModelColors[1] = 926;
            itemDef.originalModelColors[2] = (short) 53167;
            itemDef.modifiedModelColors[2] = 926;
            itemDef.modelId = 1781;
            itemDef.spriteScale = 840;
            itemDef.spriteYRotation = 612;
            itemDef.spriteZRotation = 816;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 677;
            itemDef.primaryFemaleModel = 677;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Dragon Flowers";
            itemDef.description = "Its A Dragon Flowers";
        }
        if (i == 30118) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[3];
            itemDef.modifiedModelColors = new short[3];
            itemDef.originalModelColors[0] = (short) 48596;
            itemDef.modifiedModelColors[0] = 5652;
            itemDef.originalModelColors[1] = (short) 37196;
            itemDef.modifiedModelColors[1] = 5652;
            itemDef.originalModelColors[2] = (short) 53167;
            itemDef.modifiedModelColors[2] = 5652;
            itemDef.modelId = 1781;
            itemDef.spriteScale = 840;
            itemDef.spriteYRotation = 612;
            itemDef.spriteZRotation = 816;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 677;
            itemDef.primaryFemaleModel = 677;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Bronze Flowers";
            itemDef.description = "Its A Bronze Flowers";
        }
        if (i == 30119) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[3];
            itemDef.modifiedModelColors = new short[3];
            itemDef.originalModelColors[0] = (short) 48596;
            itemDef.modifiedModelColors[0] = 33;
            itemDef.originalModelColors[1] = (short) 37196;
            itemDef.modifiedModelColors[1] = 33;
            itemDef.originalModelColors[2] = (short) 53167;
            itemDef.modifiedModelColors[2] = 33;
            itemDef.modelId = 1781;
            itemDef.spriteScale = 840;
            itemDef.spriteYRotation = 612;
            itemDef.spriteZRotation = 816;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 677;
            itemDef.primaryFemaleModel = 677;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Iron Flowers";
            itemDef.description = "Its A Iron Flowers";
        }
        if (i == 30120) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[3];
            itemDef.modifiedModelColors = new short[3];
            itemDef.originalModelColors[0] = (short) 48596;
            itemDef.modifiedModelColors[0] = (short) 43072;
            itemDef.originalModelColors[1] = (short) 37196;
            itemDef.modifiedModelColors[1] = (short) 43072;
            itemDef.originalModelColors[2] = (short) 53167;
            itemDef.modifiedModelColors[2] = (short) 43072;
            itemDef.modelId = 1781;
            itemDef.spriteScale = 840;
            itemDef.spriteYRotation = 612;
            itemDef.spriteZRotation = 816;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 677;
            itemDef.primaryFemaleModel = 677;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Steel Flowers";
            itemDef.description = "Its A Steel Flowers";
        }
        if (i == 30121) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[3];
            itemDef.modifiedModelColors = new short[3];
            itemDef.originalModelColors[0] = (short) 48596;
            itemDef.modifiedModelColors[0] = (short) 43297;
            itemDef.originalModelColors[1] = (short) 37196;
            itemDef.modifiedModelColors[1] = (short) 43297;
            itemDef.originalModelColors[2] = (short) 53167;
            itemDef.modifiedModelColors[2] = (short) 43297;
            itemDef.modelId = 1781;
            itemDef.spriteScale = 840;
            itemDef.spriteYRotation = 612;
            itemDef.spriteZRotation = 816;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 677;
            itemDef.primaryFemaleModel = 677;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Mithril Flowers";
            itemDef.description = "Its A Mithril Flowers";
        }
        if (i == 30122) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[3];
            itemDef.modifiedModelColors = new short[3];
            itemDef.originalModelColors[0] = (short) 48596;
            itemDef.modifiedModelColors[0] = 21662;
            itemDef.originalModelColors[1] = (short) 37196;
            itemDef.modifiedModelColors[1] = 21662;
            itemDef.originalModelColors[2] = (short) 53167;
            itemDef.modifiedModelColors[2] = 21662;
            itemDef.modelId = 1781;
            itemDef.spriteScale = 840;
            itemDef.spriteYRotation = 612;
            itemDef.spriteZRotation = 816;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 677;
            itemDef.primaryFemaleModel = 677;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Adam Flowers";
            itemDef.description = "Its A Adam Flowers";
        }
        if (i == 30123) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[3];
            itemDef.modifiedModelColors = new short[3];
            itemDef.originalModelColors[0] = (short) 48596;
            itemDef.modifiedModelColors[0] = (short) 36133;
            itemDef.originalModelColors[1] = (short) 37196;
            itemDef.modifiedModelColors[1] = (short) 36133;
            itemDef.originalModelColors[2] = (short) 53167;
            itemDef.modifiedModelColors[2] = (short) 36133;
            itemDef.modelId = 1781;
            itemDef.spriteScale = 840;
            itemDef.spriteYRotation = 612;
            itemDef.spriteZRotation = 816;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 677;
            itemDef.primaryFemaleModel = 677;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Rune Flowers";
            itemDef.description = "Its A Rune Flowers";
        }
        if (i == 30124) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 32707;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 32707;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Light Blue Bunny ears";
            itemDef.description = "Its A Light Blue Bunny ears";
        }
        if (i == 30125) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = (short) 52685;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = (short) 52685;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Light Purple Bunny ears";
            itemDef.description = "Its A Light Purple Bunny ears";
        }
        if (i == 30126) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 13140;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 13140;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Light Yellow Bunny ears";
            itemDef.description = "Its A Light Yellow Bunny ears";
        }
        if (i == 30127) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = 20245;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = 20245;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Forest Green Bunny ears";
            itemDef.description = "Its A Forest Green Bunny ears";
        }
        if (i == 30128) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[2];
            itemDef.modifiedModelColors = new short[2];
            itemDef.originalModelColors[0] = 933;
            itemDef.modifiedModelColors[0] = (short) 50976;
            itemDef.originalModelColors[1] = 10351;
            itemDef.modifiedModelColors[1] = (short) 50976;
            itemDef.modelId = 2553;
            itemDef.spriteScale = 550;
            itemDef.spriteYRotation = 360;
            itemDef.spriteZRotation = 4;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 3352;
            itemDef.primaryFemaleModel = 3353;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Dark Purple Bunny ears";
            itemDef.description = "Its A Dark Purple Bunny ears";
        }
        if (i == 3110) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 59780;
            itemDef.spriteScale = 400;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 5;
            itemDef.primaryMaleModel = 59781;
            itemDef.primaryFemaleModel = 59781;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Amulet of the Dragons";
            itemDef.description = "A amulet worn by the dragon slayers.";
        }
        if (i == 29721) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 58467;
            itemDef.spriteScale = 2200;
            itemDef.spriteYRotation = 572;
            itemDef.spriteZRotation = 1000;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 1;
            itemDef.primaryMaleModel = 58468;
            itemDef.primaryFemaleModel = 58468;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Modeler Cape";
            itemDef.description = "Modeler Cape ";
        }
        if (i == 28) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63066;
            itemDef.spriteScale = 2000;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 10;
            itemDef.spriteTranslateY = 5;
            itemDef.primaryMaleModel = 63067;
            itemDef.primaryFemaleModel = 63068;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Fang";
            itemDef.description = "Elvarg Fang.";
        }
        if (i == 8382) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63684;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 200;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63685;
            itemDef.primaryFemaleModel = 63686;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Slayer Helmet";
            itemDef.description = "Elvarg Slayer Helmet";
        }
        if (i == 5771) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63043;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63044;
            itemDef.primaryFemaleModel = 63045;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Kaida Torva Helm";
            itemDef.description = "Kaida Torva Helm";
        }
        if (i == 5779) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63046;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63047;
            itemDef.primaryFemaleModel = 63048;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Kaida Torva Platebody";
            itemDef.description = "Kaida Torva Platebody";
        }
        if (i == 5787) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63049;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63050;
            itemDef.primaryFemaleModel = 63051;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Kaida Torva Platelegs";
            itemDef.description = "Kaida Torva Platelegs";
        }
        if (i == 2871) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63674;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63675;
            itemDef.primaryFemaleModel = 63676;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Crystal Helm";
            itemDef.description = "Elvarg Crystal Helm";
        }
        if (i == 2872) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63677;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63678;
            itemDef.primaryFemaleModel = 63679;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Crystal Body";
            itemDef.description = "Elvarg Crystal Body";
        }
        if (i == 2873) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63680;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63681;
            itemDef.primaryFemaleModel = 63682;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Crystal Legs";
            itemDef.description = "Elvarg Crystal Legs";
        }
        
        if (i == 17) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63656;
            itemDef.spriteScale = 840;
            itemDef.spriteYRotation = 280;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -2;
            itemDef.spriteTranslateY = 56;
            itemDef.primaryMaleModel = 63657;
            itemDef.primaryFemaleModel = 63658;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Dragonmander";
            itemDef.description = "Dragonmander";
        }
        if (i == 16) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63653;
            itemDef.spriteScale = 840;
            itemDef.spriteYRotation = 280;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -2;
            itemDef.spriteTranslateY = 56;
            itemDef.primaryMaleModel = 63654;
            itemDef.primaryFemaleModel = 63655;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Blunder bussy";
            itemDef.description = "BLUNDERBUSS";
        }
        if (i == 468) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63659;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63660;
            itemDef.primaryFemaleModel = 63661;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Skilling Hood";
            itemDef.description = "Elvarg Skilling Hood";
        }
        if (i == 470) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63662;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63663;
            itemDef.primaryFemaleModel = 63664;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Skilling Body";
            itemDef.description = "Elvarg Skilling Body";
        }
        if (i == 472) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63665;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63666;
            itemDef.primaryFemaleModel = 63667;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Skilling Legs";
            itemDef.description = "Elvarg Skilling Legs";
        }
        if (i == 474) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Elvarg Skilling Boots"; //Name
            itemDef.description = "Elvarg Skilling Boots"; //Description
            itemDef.modelId = 63668;
            itemDef.spriteScale = 976;
            itemDef.spriteYRotation = 147;
            itemDef.spriteZRotation = 279;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 5;
            itemDef.spriteTranslateY = -5;
            itemDef.primaryMaleModel = 63669;
            itemDef.primaryFemaleModel = 63673;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 75000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = 13236;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = 14025;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 476) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63670;
            itemDef.spriteScale = 2200;
            itemDef.spriteYRotation = 572;
            itemDef.spriteZRotation = 1000;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 1;
            itemDef.primaryMaleModel = 63671;
            itemDef.primaryFemaleModel = 63672;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Skilling Cape";
            itemDef.description = "Elvarg Skilling Cape! ";
        }
        if (i == 3102) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Kaida Pernix Cowl"; //Name
            itemDef.description = "Its an Kaida Pernix Cowl"; //Description
            itemDef.modelId = 63290;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63291;
            itemDef.primaryFemaleModel = 63292;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 3103) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Kaida Pernix body"; //Name
            itemDef.description = "Its an Kaida Pernix body"; //Description
            itemDef.modelId = 63293;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63294;
            itemDef.primaryFemaleModel = 63295;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 3104) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Kaida Pernix Chaps"; //Name
            itemDef.description = "Its an Kaida Pernix Chaps"; //Description
            itemDef.modelId = 63296;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63297;
            itemDef.primaryFemaleModel = 63298;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 611) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Scorched Pernix Cowl"; //Name
            itemDef.description = "Its an Scorched Pernix Cowl"; //Description
            itemDef.modelId = 63299;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63300;
            itemDef.primaryFemaleModel = 63301;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 612) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Scorched Pernix body"; //Name
            itemDef.description = "Its an Scorched Pernix body"; //Description
            itemDef.modelId = 63004;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63302;
            itemDef.primaryFemaleModel = 63303;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 613) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Scorched Pernix Chaps"; //Name
            itemDef.description = "Its an Scorched Pernix Chaps"; //Description
            itemDef.modelId = 63304;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63305;
            itemDef.primaryFemaleModel = 63306;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }


        if (i == 29020) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Kaida Virtus Mask"; //Name
            itemDef.description = "Its an Virtus Mask"; //Description
            itemDef.modelId = 63167;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 63168;
            itemDef.primaryFemaleModel = 63169;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 29021) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Kaida Virtus Robe Top"; //Name
            itemDef.description = "Its an Virtus Robe Top"; //Description
            itemDef.modelId = 63170;
            itemDef.spriteScale = 1200;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 63171;
            itemDef.primaryFemaleModel = 63172;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 29022) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Kaida Virtus Robe Bottoms"; //Name
            itemDef.description = "Its an Virtus Robe Bottoms"; //Description
            itemDef.modelId = 63173;
            itemDef.spriteScale = 1789;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 63174;
            itemDef.primaryFemaleModel = 63175;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 25159) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "TEST SHEILD"; //Name
            itemDef.description = "Its an Kaida Virtus Robe Bottoms"; //Description
            itemDef.modelId = 63307;
            itemDef.spriteScale = 1000;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 63308;
            itemDef.primaryFemaleModel = 63309;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 25160) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bow of Armadyl"; //Name
            itemDef.description = "A powerful power bow."; //Description
            itemDef.modelId = 63310;
            itemDef.spriteScale = 1200;
            itemDef.spriteYRotation = 250;
            itemDef.spriteZRotation = 180;
            itemDef.spriteXRotation = 400;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 10;
            itemDef.primaryMaleModel = 63311;
            itemDef.primaryFemaleModel = 63312;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 25161) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "TEST hammer"; //Name
            itemDef.description = "A powerful power bow."; //Description
            itemDef.modelId = 63313;
            itemDef.spriteScale = 1200;
            itemDef.spriteYRotation = 250;
            itemDef.spriteZRotation = 180;
            itemDef.spriteXRotation = 400;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 10;
            itemDef.primaryMaleModel = 63314;
            itemDef.primaryFemaleModel = 63315;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 29095) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63601;
            itemDef.spriteScale = 1500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 5;
            itemDef.spriteTranslateY = 5;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63602;
            itemDef.primaryFemaleModel = 63603;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Completionist cape ";
            itemDef.description = "Completionist cape! ";
        }
        if (i == 2423) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Equip";
            itemDef.modelId = 63054;
            itemDef.spriteScale = 1800;
            itemDef.spriteYRotation = 180;
            itemDef.spriteZRotation = 1100;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63055;
            itemDef.primaryFemaleModel = 63056;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Starter Staff";
            itemDef.description = "Staff made with the head of the Legendary Elvarg";
        }
        if (i == 587) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Elvarg Starter Scimitar"; //Name
            itemDef.description = "Elvarg Starter Scimitar"; //Description
            itemDef.modelId = 63057;
            itemDef.spriteScale = 1500;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -2;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63058;
            itemDef.primaryFemaleModel = 63059;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 588) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Elvarg Starter Bow"; //Name
            itemDef.description = "Elvarg Starter Bow"; //Description
            itemDef.modelId = 63060;
            itemDef.spriteScale = 1225;
            itemDef.spriteYRotation = 720;
            itemDef.spriteZRotation = 1500;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 1;
            itemDef.primaryMaleModel = 63061;
            itemDef.primaryFemaleModel = 63062;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.value = 3000000;
        }
        if (i == 5) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Kaida gem stone"; //Name
            itemDef.modelId = 63038;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 720;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 1;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.value = 3000000;
        }
        if (i == 33269) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
            itemDef.groundActions = new String[]{"Pick-up", null, "Take", null, null};
            itemDef.name = "Big brother ryuu"; //Name
            itemDef.description = "Big brother ryuu"; //Description
            itemDef.modelId = 63161;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 1;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.value = 3000000;
        }
        if (i == 3) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Scorched gem stone"; //Name
            itemDef.description = "Scorched Gem Stone"; //Description
            itemDef.modelId = 63039;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 720;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 1;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.value = 3000000;
        }
        if (i == 29094) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63011;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63012;
            itemDef.primaryFemaleModel = 63013;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ryuu Helm";
            itemDef.description = "Ryuu Mask";
        }
        if (i == 29090) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63014;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63015;
            itemDef.primaryFemaleModel = 63016;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ryuu Body";
            itemDef.description = "Ryuu Body";
        }
        if (i == 29091) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63017;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63018;
            itemDef.primaryFemaleModel = 63019;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ryuu Legs";
            itemDef.description = "Ryuu Legs";
        }
        if (i == 29093) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Bloody Bandos chestplate"; //Name
            itemDef.modelId = 63494;
            itemDef.spriteScale = 1500;
            itemDef.spriteYRotation = 501;
            itemDef.spriteZRotation = 6;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63495;
            itemDef.primaryFemaleModel = 63496;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 2000000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 29092) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Ryuu boots"; //Name
            itemDef.description = "Its an Slayer boots"; //Description
            itemDef.modelId = 63020;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 63021;
            itemDef.primaryFemaleModel = 63022;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 795) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Ryuu gloves"; //Name
            itemDef.description = "Its an Ryuu gloves"; //Description
            itemDef.modelId = 63023;
            itemDef.spriteScale = 789;
            itemDef.spriteYRotation = 609;
            itemDef.spriteZRotation = 111;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63024;
            itemDef.primaryFemaleModel = 63025;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 100000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = 14369;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 286) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63406;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63407;
            itemDef.primaryFemaleModel = 63408;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Warden's Helm";
            itemDef.description = "Masori Helm";
        }
        if (i == 287) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63409;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63410;
            itemDef.primaryFemaleModel = 63411;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Warden's Platebody";
            itemDef.description = "Masori Platebody";
        }
        if (i == 288) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63412;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63413;
            itemDef.primaryFemaleModel = 63414;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Warden's Platelegs";
            itemDef.description = "Masori Platelegs";
        }
        if (i == 291) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63358;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63359;
            itemDef.primaryFemaleModel = 63485;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ascension  Nemes";
            itemDef.description = "Masori Nemes ";
        }
        if (i == 292) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63360;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63361;
            itemDef.primaryFemaleModel = 63486;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ascension robe top";
            itemDef.description = "Masori robe bottom";
        }
        if (i == 293) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63362;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63363;
            itemDef.primaryFemaleModel = 63487;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ascension robe bottom";
            itemDef.description = "Masori robe bottom";
        }
        if (i == 3114) {
            // itemDef.setDefaults();
            itemDef.modelId = 63136;
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.name = "Kaida Scythe";
            itemDef.description = "A scythe from Kaida Boss.";
            itemDef.spriteScale = 3000;
            itemDef.spriteYRotation = 1750;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 10;
            itemDef.primaryMaleModel = 63137;
            itemDef.primaryFemaleModel = 63138;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 19511) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Equip";
            itemDef.modelId = 63355;
            itemDef.spriteScale = 1800;
            itemDef.spriteYRotation = 180;
            itemDef.spriteZRotation = 1100;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63356;
            itemDef.primaryFemaleModel = 63357;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Kaida Staff";
            itemDef.description = "Staff";
        }
        if (i == 3109) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Equip";
            itemDef.modelId = 63589;
            itemDef.spriteScale = 1800;
            itemDef.spriteYRotation = 180;
            itemDef.spriteZRotation = 1100;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63590;
            itemDef.primaryFemaleModel = 63591;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Staff of Elvarg";
            itemDef.description = "Staff";
        }
        if (i == 2529) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Equip";
            itemDef.modelId = 63316;
            itemDef.spriteScale = 1800;
            itemDef.spriteYRotation = 180;
            itemDef.spriteZRotation = 1100;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63317;
            itemDef.primaryFemaleModel = 63318;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Scorched Staff";
            itemDef.description = "Staff";
        }
        if (i == 2966) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Scorched twisted bow"; //Name
            itemDef.description = "The legendary Bow of Elvarg"; //Description
            itemDef.modelId  = 63319;
            itemDef.spriteScale = 1875;
            itemDef.spriteYRotation = 720;
            itemDef.spriteZRotation = 1500;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 1;
            itemDef.primaryMaleModel  = 63320;
            itemDef.primaryFemaleModel  = 63321;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.value = 3000000;
        }
        if (i == 38384) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{"Spawn-Giveaway", null, null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Youtube Button"; //Name
            itemDef.description = "Youtube Button"; //Description
            itemDef.modelId  = 63280;
            itemDef.spriteScale = 1000;
            itemDef.spriteYRotation = 720;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 1;
            itemDef.primaryMaleModel  = -1;
            itemDef.primaryFemaleModel  = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.value = 3000000;
        }
        if (i == 4259) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63211;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63212;
            itemDef.primaryFemaleModel = 63213;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Dragonkin Battlegear Helm";
            itemDef.description = "Dragonkin Battlegear Helm";
        }
        if (i == 4263) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63214;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63215;
            itemDef.primaryFemaleModel = 63216;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Dragonkin Battlegear Body";
            itemDef.description = "Dragonkin Battlegear Body";
        }
        if (i == 4264) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63217;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63218;
            itemDef.primaryFemaleModel = 63219;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Dragonkin Battlegear Legs";
            itemDef.description = "Dragonkin Battlegear Legs";
        }
        if (i == 83) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Dragonkin Battlegear boots"; //Name
            itemDef.description = "Its an Dragonkin Battlegear boots"; //Description
            itemDef.modelId = 63220;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 63221;
            itemDef.primaryFemaleModel = 63222;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 77) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Dragonkin Battlegear gloves"; //Name
            itemDef.description = "Its an Dragonkin Battlegear gloves"; //Description
            itemDef.modelId = 63223;
            itemDef.spriteScale = 789;
            itemDef.spriteYRotation = 609;
            itemDef.spriteZRotation = 111;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63224;
            itemDef.primaryFemaleModel = 63225;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 100000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = 14369;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 1581) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63006;
            itemDef.spriteScale = 1000;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63476;
            itemDef.primaryFemaleModel = 63007;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Kaida Assembler";
            itemDef.description = "Kaida Assembler made by the gods! ";
        }
        if (i == 1494) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63477;
            itemDef.spriteScale = 1000;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63478;
            itemDef.primaryFemaleModel = 63479;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Scorched Assembler";
            itemDef.description = "Scorched Assembler made by the gods! ";
        }
        if (i == 1579) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63592;
            itemDef.spriteScale = 1000;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63593;
            itemDef.primaryFemaleModel = 63594;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Assembler";
        }
        if (i == 24) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 62043;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 62044;
            itemDef.primaryFemaleModel = 62045;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Imbued Elvarg Cape";
            itemDef.description = "Imbued Kaida Cape made by the gods! ";
        }
        if (i == 33) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63176;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63177;
            itemDef.primaryFemaleModel = 63000;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Imbued Kaida Cape";
            itemDef.description = "Imbued Kaida Cape made by the gods! ";
        }
        if (i == 32) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63178;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63179;
            itemDef.primaryFemaleModel = 49588;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Imbued Scorched Cape";
            itemDef.description = "Imbued Scorched Cape made by the gods! ";
        }
        if (i == 284) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Patrizot Gauntlets"; //Name
            itemDef.description = "Its an Patrizot Gauntlets"; //Description
            itemDef.modelId = 63148;
            itemDef.spriteScale = 789;
            itemDef.spriteYRotation = 609;
            itemDef.spriteZRotation = 111;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63149;
            itemDef.primaryFemaleModel = 63150;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 100000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = 14369;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 431) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Pathian Handwraps"; //Name
            itemDef.description = "Its an Pathian Handwraps"; //Description
            itemDef.modelId = 63144;
            itemDef.spriteScale = 789;
            itemDef.spriteYRotation = 609;
            itemDef.spriteZRotation = 111;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63145;
            itemDef.primaryFemaleModel = 63146;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 100000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = 14369;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 669) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Enfirial Bracers"; //Name
            itemDef.description = "Its an Enfirial Bracers"; //Description
            itemDef.modelId = 63153;
            itemDef.spriteScale = 789;
            itemDef.spriteYRotation = 609;
            itemDef.spriteZRotation = 111;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63154;
            itemDef.primaryFemaleModel = 63155;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 100000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = 14369;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 76) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63199;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63200;
            itemDef.primaryFemaleModel = 63201;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "4th of July Tophat";
            itemDef.description = "4th of July Tophat";
        }
        if (i == 6127) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Warden's Blades"; //Name
            itemDef.description = "Warden's Blades"; //Description
            itemDef.modelId = 63130;
            itemDef.spriteScale = 1500;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -2;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63131;
            itemDef.primaryFemaleModel = 63132;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 945) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Divine Spirit Sheild"; //Name
            itemDef.description = "Divine spirit Shield"; //Description
            itemDef.modelId = 63140;
            itemDef.spriteScale = 1750;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 50;
            itemDef.spriteXRotation = 50;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63141;
            itemDef.primaryFemaleModel = 63142;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 2000000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 2575) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, null, null, null, "Drop"};
            itemDef.name = "Divine sigil"; //Name
            itemDef.description = "Divine sigil"; //Description
            itemDef.modelId = 63143;
            itemDef.spriteScale = 1300;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 650;
            itemDef.spriteXRotation = 50;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 2000000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 689) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Blade of the Ruin King"; //Name
            itemDef.description = "Blade of the Ruin King"; //Description
            itemDef.modelId = 63181;
            itemDef.spriteScale = 1900;
            itemDef.spriteYRotation = 691;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 3;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63182;
            itemDef.primaryFemaleModel = 63183;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 72001;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 4283) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Gilded Ryuu Poleaxe"; //Name
            itemDef.description = "Gilded Ryuu Pole Axe"; //Description
            itemDef.modelId = 63186;
            itemDef.spriteScale = 1900;
            itemDef.spriteYRotation = 691;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 3;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63187;
            itemDef.primaryFemaleModel = 63188;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 72001;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 279) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Ryuu Poleaxe"; //Name
            itemDef.description = "Ryuu Pole Axe"; //Description
            itemDef.modelId = 63162;
            itemDef.spriteScale = 1900;
            itemDef.spriteYRotation = 691;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 3;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63163;
            itemDef.primaryFemaleModel = 63164;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 72001;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 9933) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63391;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63392;
            itemDef.primaryFemaleModel = 63393;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Gilded Ryuu Helm";
            itemDef.description = "Ryuu Mask";
        }
        if (i == 9934) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63394;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63395;
            itemDef.primaryFemaleModel = 63396;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Gilded Ryuu Body";
            itemDef.description = "Ryuu Body";
        }
        if (i == 9935) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63397;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63398;
            itemDef.primaryFemaleModel = 63399;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Gilded Ryuu Legs";
            itemDef.description = "Ryuu Legs";
        }
        if (i == 9936) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Gilded Ryuu boots"; //Name
            itemDef.description = "Its an Slayer boots"; //Description
            itemDef.modelId = 63400;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 63401;
            itemDef.primaryFemaleModel = 63402;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 9937) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Gilded Ryuu gloves"; //Name
            itemDef.description = "Its an Ryuu gloves"; //Description
            itemDef.modelId = 63403;
            itemDef.spriteScale = 789;
            itemDef.spriteYRotation = 609;
            itemDef.spriteZRotation = 111;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63404;
            itemDef.primaryFemaleModel = 63405;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 100000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = 14369;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 15) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{"Open", null, null, "Quick- Open", "Drop"};
            itemDef.name = "Super Elvarg Chest"; //Name
            itemDef.modelId = 63037;
            itemDef.spriteScale = 3000;
            itemDef.spriteYRotation = 609;
            itemDef.spriteZRotation = 111;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 100000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = 14369;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 8292) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63157;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63158;
            itemDef.primaryFemaleModel = 63159;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ex-Staff Cape";
            itemDef.description = "Ex-Staff Cape! ";
        }
        if (i == 419) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63364;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63365;
            itemDef.primaryFemaleModel = 63366;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Cursed Warden's Helm";
            itemDef.description = "Masori Helm";
        }
        if (i == 420) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63367;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63368;
            itemDef.primaryFemaleModel = 63369;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Cursed Warden's Platebody";
            itemDef.description = "Masori Platebody";
        }
        if (i == 421) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63370;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63371;
            itemDef.primaryFemaleModel = 63372;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Cursed Warden's Platelegs";
            itemDef.description = "Masori Platelegs";
        }
        if (i == 602) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63373;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63374;
            itemDef.primaryFemaleModel = 63375;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Divine Warden's Helm";
            itemDef.description = "Masori Helm";
        }
        if (i == 604) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63376;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63377;
            itemDef.primaryFemaleModel = 63378;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Divine Warden's Platebody";
            itemDef.description = "Masori Platebody";
        }
        if (i == 605) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63379;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63380;
            itemDef.primaryFemaleModel = 63381;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Divine Warden's Platelegs";
            itemDef.description = "Masori Platelegs";
        }
        if (i == 717) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63382;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63383;
            itemDef.primaryFemaleModel = 63384;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Anubis Warden's Helm";
            itemDef.description = "Masori Helm";
        }
        if (i == 718) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63385;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63386;
            itemDef.primaryFemaleModel = 63387;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Anubis Warden's Platebody";
            itemDef.description = "Masori Platebody";
        }
        if (i == 719) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63388;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63389;
            itemDef.primaryFemaleModel = 63390;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Anubis Warden's Platelegs";
            itemDef.description = "Masori Platelegs";
        }
        if (i == 2978) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63202;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63203;
            itemDef.primaryFemaleModel = 63204;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Cursed Masori Mask";
            itemDef.description = "Masori Helm";
        }
        if (i == 2979) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63205;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63206;
            itemDef.primaryFemaleModel = 63207;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Cursed Masori Body";
            itemDef.description = "Masori Platebody";
        }
        if (i == 2980) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63208;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63209;
            itemDef.primaryFemaleModel = 63210;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Cursed Masori Chaps";
            itemDef.description = "Masori Platelegs";
        }
        if (i == 2981) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63226;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63227;
            itemDef.primaryFemaleModel = 63228;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Divine Masori Mask";
            itemDef.description = "Masori Helm";
        }
        if (i == 2982) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63234;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63229;
            itemDef.primaryFemaleModel = 63230;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Divine Masori Body";
            itemDef.description = "Masori Platebody";
        }
        if (i == 2983) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63231;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63232;
            itemDef.primaryFemaleModel = 63233;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Divine Masori Chaps";
            itemDef.description = "Masori Platelegs";
        }
        if (i == 2984) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63241;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63242;
            itemDef.primaryFemaleModel = 63243;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Anubis Masori Mask";
            itemDef.description = "Masori Helm";
        }
        if (i == 2985) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63244;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63245;
            itemDef.primaryFemaleModel = 63246;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Anubis Masori Body";
            itemDef.description = "Masori Platebody";
        }
        if (i == 2986) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63247;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63248;
            itemDef.primaryFemaleModel = 63249;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Anubis Masori Chaps";
            itemDef.description = "Masori Platelegs";
        }
        if (i == 2987) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63260;
            itemDef.spriteScale = 875;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63261;
            itemDef.primaryFemaleModel = 63262;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Cursed Ascension Nemes";
            itemDef.description = "Masori Nemes ";
        }
        if (i == 2988) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63263;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63264;
            itemDef.primaryFemaleModel = 63265;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Cursed Ascension robe top";
            itemDef.description = "Masori robe bottom";
        }
        if (i == 2989) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63266;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63267;
            itemDef.primaryFemaleModel = 63268;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Cursed Ascension robe bottom";
            itemDef.description = "Masori robe bottom";
        }
        if (i == 2990) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63322;
            itemDef.spriteScale = 875;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63323;
            itemDef.primaryFemaleModel = 63324;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Divine Ascension Nemes";
            itemDef.description = "Masori Nemes ";
        }
        if (i == 2991) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63325;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63326;
            itemDef.primaryFemaleModel = 63327;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Divine Ascension robe top";
            itemDef.description = "Masori robe bottom";
        }
        if (i == 2992) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63328;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63329;
            itemDef.primaryFemaleModel = 50151;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Divine Ascension robe bottom";
            itemDef.description = "Masori robe bottom";
        }
        if (i == 2993) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63330;
            itemDef.spriteScale = 875;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63331;
            itemDef.primaryFemaleModel = 63332;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Anubis Ascension Nemes";
            itemDef.description = "Masori Nemes ";
        }
        if (i == 2994) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63333;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63334;
            itemDef.primaryFemaleModel = 63335;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Anubis Ascension robe top";
            itemDef.description = "Masori robe bottom";
        }
        if (i == 2995) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63336;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63337;
            itemDef.primaryFemaleModel = 63338;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Anubis Ascension robe bottom";
            itemDef.description = "Masori robe bottom";
        }
        if (i == 90) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63189;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63190;
            itemDef.primaryFemaleModel = 63191;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Tokhaar-kal";
            itemDef.description = "tokhaar-kal";
        }
        if (i == 278) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63235;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63236;
            itemDef.primaryFemaleModel = 63237;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Monkey max cape";
            itemDef.description = "tokhaar-kal";
        }
        if (i == 275) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63238;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63239;
            itemDef.primaryFemaleModel = 63240;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Monkey fire max cape";
            itemDef.description = "tokhaar-kal";
        }
        if (i == 285) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63506;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63507;
            itemDef.primaryFemaleModel = 63508;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Reaper cape";
            itemDef.description = "tokhaar-kal";
        }
        if (i == 785) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63165;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63165;
            itemDef.primaryFemaleModel = 63165;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Monkey max cape";
            itemDef.description = "tokhaar-kal";
        }
        if (i == 2886) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Equip";
            itemDef.modelId = 63250;
            itemDef.spriteScale = 1800;
            itemDef.spriteYRotation = 180;
            itemDef.spriteZRotation = 975;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63251;
            itemDef.primaryFemaleModel = 63001;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elder Accursed Sceptre";
            itemDef.description = "Staff made with the head of the Legendary Elvarg";
        }
        if (i == 418) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Bone Whip"; //Name
            itemDef.description = "A speedy whip."; //Description
            itemDef.modelId = 57993;
            itemDef.spriteScale = 1200;
            itemDef.spriteYRotation = 250;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 57991;
            itemDef.primaryFemaleModel = 57992;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }

        if (i == 466) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Ravaged Dragon Hunter Lance"; //Name
            itemDef.description = "A speedy whip."; //Description
            itemDef.modelId = 63192;
            itemDef.spriteScale = 1500;
            itemDef.spriteYRotation = 1250;
            itemDef.spriteZRotation = 20;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 15;
            itemDef.primaryMaleModel = 63194;
            itemDef.primaryFemaleModel = 63195;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 480) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Ravaged Dragon Hunter Crossbow"; //Name
            itemDef.description = "A speedy whip."; //Description
            itemDef.modelId = 63196;
            itemDef.spriteScale = 875;
            itemDef.spriteYRotation = 250;
            itemDef.spriteZRotation = 250;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63197;
            itemDef.primaryFemaleModel = 63198;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 296) {
            // itemDef.setDefaults();
            itemDef.modelId = 63586;
            itemDef.primaryMaleModel = 63587;
            itemDef.primaryFemaleModel = 63588;
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.name = "Elvarg Scythe";
            itemDef.spriteScale = 3000;
            itemDef.spriteYRotation = 1750;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 10;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 297) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Dragonkin's Bow"; //Name
            itemDef.description = "A powerful power bow."; //Description
            itemDef.modelId = 63147;
            itemDef.spriteScale = 1500;
            itemDef.spriteYRotation = 750;
            itemDef.spriteZRotation = 250;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63151;
            itemDef.primaryFemaleModel = 63151;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 684) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Dragonkin's hammer"; //Name
            itemDef.description = "A powerful power bow."; //Description
            itemDef.modelId = 63152;
            itemDef.spriteScale = 1500;
            itemDef.spriteYRotation = 250;
            itemDef.spriteZRotation = 250;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 10;
            itemDef.primaryMaleModel = 63156;
            itemDef.primaryFemaleModel = 63156;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
        }
        if (i == 20) {
            // itemDef.setDefaults();
            itemDef.modelId = 63252;
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.name = "Noxious Scythe";
            itemDef.description = "A scythe from Elvarg Boss.";
            itemDef.spriteScale = 3000;
            itemDef.spriteYRotation = 1750;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 10;
            itemDef.primaryMaleModel = 63253;
            itemDef.primaryFemaleModel = 63254;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 21) //ID
        {
            //itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Noxious bow"; //Name
            itemDef.description = "A powerful power bow."; //Description
            itemDef.modelId = 63255;
            itemDef.spriteScale = 1250;
            itemDef.spriteYRotation = 250;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63256;
            itemDef.primaryFemaleModel = 63257;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 22) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Equip";
            itemDef.modelId = 63258;
            itemDef.spriteScale = 1800;
            itemDef.spriteYRotation = 180;
            itemDef.spriteZRotation = 1100;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63259;
            itemDef.primaryFemaleModel = 63262;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Noxious Staff";
            itemDef.description = "Staff made with the head of the Legendary Elvarg";
        }

        if (i == 715) //ID
        {
            //itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Dorgan's Bow"; //Name
            itemDef.description = "A powerful power bow."; //Description
            itemDef.modelId = 63339;
            itemDef.spriteScale = 1250;
            itemDef.spriteYRotation = 250;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63340;
            itemDef.primaryFemaleModel = 50167;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 274) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Equip";
            itemDef.modelId = 63281;
            itemDef.spriteScale = 1250;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 280;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63282;
            itemDef.primaryFemaleModel = 50020;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = 33;
            itemDef.primaryFemaleHeadPiece = 91;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Pumpkin Staff";
            itemDef.description = "Staff made by the halloween spirits!";
        }
        if (i == 273) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Pumpkin twisted bow"; //Name
            itemDef.description = "The legendary Bow of Elvarg"; //Description
            itemDef.modelId  = 63283;
            itemDef.spriteScale = 1875;
            itemDef.spriteYRotation = 720;
            itemDef.spriteZRotation = 1500;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 1;
            itemDef.primaryMaleModel  = 63284;
            itemDef.primaryFemaleModel  = 63285;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.value = 3000000;
        }
        if (i == 10542) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Ankou mask (purple)"; //Name
            itemDef.description = "Ankou Mask (purple)"; //Description
            itemDef.modelId = 59520;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 59521;
            itemDef.primaryFemaleModel = 59522;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 10543) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Ankou top (purple)"; //Name
            itemDef.description = "Ankou top (purple)"; //Description
            itemDef.modelId = 59523;
            itemDef.spriteScale = 1200;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 59524;
            itemDef.primaryFemaleModel = 59525;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 10544) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 59526;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 59527;
            itemDef.primaryFemaleModel = 59528;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ankou leggings (purple)";
            itemDef.description = "Ankou legs (purple)";
        }
        if (i == 10545) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Ankou gloves (purple)"; //Name
            itemDef.description = "Ankou gloves (purple)"; //Description
            itemDef.modelId = 59529;
            itemDef.spriteScale = 789;
            itemDef.spriteYRotation = 609;
            itemDef.spriteZRotation = 111;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 59530;
            itemDef.primaryFemaleModel = 59531;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 100000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 10546) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Ankou socks (purple)"; //Name
            itemDef.description = "Ankou boots (purple)"; //Description
            itemDef.modelId = 59532;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 59533;
            itemDef.primaryFemaleModel = 59534;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 38376) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Ankou mask (black)"; //Name
            itemDef.description = "Ankou Mask (black)"; //Description
            itemDef.modelId = 63418;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 51051;
            itemDef.primaryFemaleModel = 63419;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 38377) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Ankou top (black)"; //Name
            itemDef.description = "Ankou top (black)"; //Description
            itemDef.modelId = 63420;
            itemDef.spriteScale = 1200;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 63421;
            itemDef.primaryFemaleModel = 63422;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 38406) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63423;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63424;
            itemDef.primaryFemaleModel = 63425;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Ankou leggings (black)";
            itemDef.description = "Ankou legs (black)";
        }
        if (i == 38378) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Ankou gloves (black)"; //Name
            itemDef.description = "Ankou gloves (black)"; //Description
            itemDef.modelId = 63426;
            itemDef.spriteScale = 789;
            itemDef.spriteYRotation = 609;
            itemDef.spriteZRotation = 111;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63427;
            itemDef.primaryFemaleModel = 63428;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 100000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 38379) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Ankou socks (black)"; //Name
            itemDef.description = "Ankou boots (black)"; //Description
            itemDef.modelId = 63429;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 51063;
            itemDef.primaryFemaleModel = 63430;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 3135) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Elly's cape"; //Name
            itemDef.description = "Its an Elly cape"; //Description
            itemDef.modelId = 63527;
            itemDef.spriteScale = 2232;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -5;
            itemDef.primaryMaleModel = 63528;
            itemDef.primaryFemaleModel = 63529;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 99000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 3136) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Flemo's cape"; //Name
            itemDef.description = "Its an Elly cape"; //Description
            itemDef.modelId = 63530;
            itemDef.spriteScale = 2232;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -5;
            itemDef.primaryMaleModel = 63531;
            itemDef.primaryFemaleModel = 63532;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 99000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 3137) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "SK's cape"; //Name
            itemDef.description = "Its an Elly cape"; //Description
            itemDef.modelId = 63533;
            itemDef.spriteScale = 2232;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -5;
            itemDef.primaryMaleModel = 63534;
            itemDef.primaryFemaleModel = 63535;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 99000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 3161) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "J Beezy's cape"; //Name
            itemDef.modelId = 63536;
            itemDef.spriteScale = 2232;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -5;
            itemDef.primaryMaleModel = 63537;
            itemDef.primaryFemaleModel = 63538;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 99000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 38506) {
            itemDef.setDefaults();
            itemDef.name = "Inquisitor's christmas helm";
            itemDef.description = "Inquisitor's christmas helm";
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63341;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63342;
            itemDef.primaryFemaleModel = 63343;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 38532) {
            itemDef.setDefaults();
            itemDef.name = "Inquisitor's christmas hauberk";
            itemDef.description = "Inquisitor's christmas hauberk";
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63344;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63345;
            itemDef.primaryFemaleModel = 50173;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 38540) {
            itemDef.setDefaults();
            itemDef.name = "Inquisitor's christmas plateskirt";
            itemDef.description = "Inquisitor's christmas plateskirt";
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63346;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63347;
            itemDef.primaryFemaleModel = 63348;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 8542) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Icicle rapier"; //Name
            itemDef.description = "Icicle rapier"; //Description
            itemDef.modelId = 63439;
            itemDef.spriteScale = 1750;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -5;
            itemDef.primaryMaleModel = 63440;
            itemDef.primaryFemaleModel = 63441;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 99000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 27) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Cape of Memories"; //Name
            itemDef.description = "Elvarg reaper cape"; //Description
            itemDef.modelId = 63540;
            itemDef.spriteScale = 2232;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -5;
            itemDef.primaryMaleModel = 63541;
            itemDef.primaryFemaleModel = 63542;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 99000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 2967) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63431;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63432;
            itemDef.primaryFemaleModel = 63433;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Corrupt revenant hood";
            itemDef.description = "Corrupt revenant helm";
        }
        if (i == 2968) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63005;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63434;
            itemDef.primaryFemaleModel = 63435;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Corrupt revenant platebody";
            itemDef.description = "Corrupt revenant Platebody";
        }
        if (i == 2969) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63436;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63437;
            itemDef.primaryFemaleModel = 63438;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Corrupt revenant platelegs";
            itemDef.description = "Corrupt revenant Platelegs";
        }
        if (i == 19) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Inquisitor's christmas mace"; //Name
            itemDef.description = "Inquisitor's christmas mace"; //Description
            itemDef.modelId = 63286;
            itemDef.spriteScale = 1750;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -5;
            itemDef.primaryMaleModel = 50025;
            itemDef.primaryFemaleModel = 63287;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 99000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 26) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Twisted Crossbow"; //Name
            itemDef.description = "Twisted Crossbow."; //Description
            itemDef.modelId = 63543;
            itemDef.spriteScale = 1000;
            itemDef.spriteYRotation = 250;
            itemDef.spriteZRotation = 100;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63544;
            itemDef.primaryFemaleModel = 63545;
        }
        if (i == 609) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63442;
            itemDef.spriteScale = 1000;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63443;
            itemDef.primaryFemaleModel = 63444;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Santa gnome backpack";
            itemDef.description = "Santa gnome backpack";
        }
        if (i == 14) {
            itemDef.setDefaults();
            itemDef.name = "Icicle Staff";
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Equip";
            itemDef.modelId = 63448;
            itemDef.spriteScale = 1800;
            itemDef.spriteYRotation = 180;
            itemDef.spriteZRotation = 1100;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63449;
            itemDef.primaryFemaleModel = 63450;
        }
        if (i == 272) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Crescent Moon Blade"; //Name
            itemDef.modelId = 63445;
            itemDef.spriteScale = 1500;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -2;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63446;
            itemDef.primaryFemaleModel = 63447;
        }
        if (i == 11196) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Kaida Twisted Crossbow"; //Name
            itemDef.description = "Twisted Crossbow."; //Description
            itemDef.modelId = 63546;
            itemDef.primaryMaleModel = 63547;
            itemDef.primaryFemaleModel = 63548;
            itemDef.spriteScale = 1000;
            itemDef.spriteYRotation = 250;
            itemDef.spriteZRotation = 100;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
        }
        if (i == 11202) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Scorched Twisted Crossbow"; //Name
            itemDef.description = "Twisted Crossbow."; //Description
            itemDef.modelId = 63549;
            itemDef.spriteScale = 1000;
            itemDef.spriteYRotation = 250;
            itemDef.spriteZRotation = 100;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63550;
            itemDef.primaryFemaleModel = 63551;
        }
        if (i == 415) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63460;
            itemDef.spriteScale = 789;
            itemDef.spriteYRotation = 66;
            itemDef.spriteZRotation = 372;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63461;
            itemDef.primaryFemaleModel = 63462;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Bloody armadyl helmet";
        }
        if (i == 416) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63463;
            itemDef.spriteScale = 1500;
            itemDef.spriteYRotation = 453;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63464;
            itemDef.primaryFemaleModel = 63465;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Bloody armadyl chestplate";
        }
        if (i == 417) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63466;
            itemDef.spriteScale = 1957;
            itemDef.spriteYRotation = 555;
            itemDef.spriteZRotation = 2036;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63467;
            itemDef.primaryFemaleModel = 63468;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Bloody armadyl chainskirt ";
        }
        if (i == 29728) {
            //itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63598;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63599;
            itemDef.primaryFemaleModel = 63600;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Cape";
            itemDef.description = "Elvarg Cape made by the gods! ";
        }
        if (i == 23800) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Elvarg spirit shield"; //Name
            itemDef.description = "Elvarg spirit shield"; //Description
            itemDef.modelId = 63687;
            itemDef.spriteScale = 1300;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 1000;
            itemDef.spriteXRotation = 50;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63688;
            itemDef.primaryFemaleModel = 63689;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 2000000;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 20641) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63451;
            itemDef.spriteScale = 1750;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 5;
            itemDef.spriteTranslateY = 5;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63452;
            itemDef.primaryFemaleModel = 63453;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Enfirial Completionist cape ";
            itemDef.description = "Completionist cape! ";
        }
        if (i == 20642) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63454;
            itemDef.spriteScale = 1750;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 5;
            itemDef.spriteTranslateY = 5;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63455;
            itemDef.primaryFemaleModel = 63456;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = " Patrizot Completionist cape ";
            itemDef.description = "Completionist cape! ";
        }
        if (i == 20643) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.originalModelColors = new short[5];
            itemDef.modifiedModelColors = new short[5];
            itemDef.modelId = 63457;
            itemDef.spriteScale = 1750;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 5;
            itemDef.spriteTranslateY = 5;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 63458;
            itemDef.primaryFemaleModel = 63459;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Pathian Completionist cape ";
            itemDef.description = "Completionist cape! ";
        }
        if (i == 277) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.name = "Aranea  boots"; //Name
            itemDef.description = "Its an pair Aranea boots"; //Description
            itemDef.modelId = 63556;
            itemDef.spriteScale = 724;
            itemDef.spriteYRotation = 171;
            itemDef.spriteZRotation = 250;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -7;
            itemDef.primaryMaleModel = 56221;
            itemDef.primaryFemaleModel = 63557;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 289010;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 280) {
            itemDef.setDefaults();
            itemDef.name = "Araxxor Abdomen";
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63497;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 85) {
            itemDef.setDefaults();
            itemDef.name = "Araxxor Fang";
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63498;
            itemDef.spriteScale = 1200;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 250;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 283) {
            itemDef.setDefaults();
            itemDef.name = "Araxxor Head";
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63499;
            itemDef.spriteScale = 1550;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 250;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 86) {
            itemDef.setDefaults();
            itemDef.name = "Araxxor Legs (bottom)";
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63500;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 250;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 87) {
            itemDef.setDefaults();
            itemDef.name = "Araxxor Legs (top)";
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63501;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 294) {
            itemDef.setDefaults();
            itemDef.name = "Araxxor webbing";
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63505;
            itemDef.spriteScale = 1000;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = -1;
            itemDef.primaryFemaleModel = -1;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 300) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63502;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 200;
            itemDef.spriteZRotation = 125;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.primaryMaleModel = 63503;
            itemDef.primaryFemaleModel = 63504;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Araxxor Slayer Helmet";
            itemDef.description = "Araxxor Slayer Helmet";
        }
        if (i == 12690) //ID
        {
            //itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wear", null, null, "Drop"};
            itemDef.groundActions = new String[]{null, null, "Take", null, null};
            itemDef.name = "Test Cape"; //Name
            itemDef.description = "A powerful power bow."; //Description
            //itemDef.modelId = 63339;
            itemDef.spriteScale = 2500;
            itemDef.spriteYRotation = 517;
            itemDef.spriteZRotation = 1035;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 0;
            itemDef.spriteXRotation = 14;
            itemDef.primaryMaleModel = 56287;
            itemDef.primaryFemaleModel = 56287;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }
        if (i == 22228) {
            // itemDef.setDefaults();
            itemDef.modelId = 63558;
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.name = "Dorgan's Scythe";
            itemDef.description = "A scythe from Dorgan the Boss.";
            itemDef.spriteScale = 3000;
            itemDef.spriteYRotation = 1750;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -3;
            itemDef.spriteTranslateY = 10;
            itemDef.primaryMaleModel = 63559;
            itemDef.primaryFemaleModel = 63560;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
        }

        if (i == 603) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Elvarg Pernix Cowl"; //Name
            itemDef.description = "Its an Scorched Pernix Cowl"; //Description
            itemDef.modelId = 63613;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 63614;
            itemDef.primaryFemaleModel = 63615;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.value = 51200000;
            itemDef.unnotedId = -1;
            itemDef.notedId = -1;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.stackable = false;
            itemDef.placeholderId = -1;
            itemDef.placeholderTemplateId = -1;
        }
        if (i == 614) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Elvarg Pernix body"; //Name
            itemDef.description = "Its an Scorched Pernix body"; //Description
            itemDef.modelId = 63610;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63611;
            itemDef.primaryFemaleModel = 63612;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;

        }
        if (i == 615) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Elvarg Pernix Chaps"; //Name
            itemDef.modelId = 63607;
            itemDef.primaryMaleModel = 63608;
            itemDef.primaryFemaleModel = 63609;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;

        }
        if (i == 674) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Elvarg Virtus Mask"; //Name
            itemDef.description = "Its an Virtus Mask"; //Description
            itemDef.modelId = 63616;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 63617;
            itemDef.primaryFemaleModel = 63618;
        }
        if (i == 675) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Elvarg Virtus Robe Top"; //Name
            itemDef.description = "Its an Virtus Robe Top"; //Description
            itemDef.modelId = 63619;
            itemDef.spriteScale = 1200;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 63620;
            itemDef.primaryFemaleModel = 63621;
        }
        if (i == 676) //ID
        {
            itemDef.setDefaults();
            itemDef.itemActions = new String[]{null, "Wield", null, null, "Drop"};
            itemDef.name = "Elvarg Virtus Robe Bottoms"; //Name
            itemDef.modelId = 63622;
            itemDef.spriteScale = 1789;
            itemDef.spriteYRotation = 431;
            itemDef.spriteZRotation = 27;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = 11;
            itemDef.primaryMaleModel = 63623;
            itemDef.primaryFemaleModel = 63624;
        }
        if (i == 29378) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 62046;
            itemDef.spriteScale = 750;
            itemDef.spriteYRotation = 0;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 1;
            itemDef.spriteTranslateX = 0;
            itemDef.spriteTranslateY = -2;
            itemDef.primaryMaleModel = 62047;
            itemDef.primaryFemaleModel = 62048;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Torva Helm";
        }
        if (i == 29377) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63625;
            itemDef.spriteScale = 1480;
            itemDef.spriteYRotation = 518;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = -1;
            itemDef.primaryMaleModel = 63626;
            itemDef.primaryFemaleModel = 63627;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Torva Platebody";
        }

        if (i == 29376) {
            itemDef.setDefaults();
            itemDef.itemActions = new String[5];
            itemDef.itemActions[1] = "Wear";
            itemDef.modelId = 63628;
            itemDef.spriteScale = 1780;
            itemDef.spriteYRotation = 500;
            itemDef.spriteZRotation = 0;
            itemDef.spriteXRotation = 0;
            itemDef.spriteTranslateX = -1;
            itemDef.spriteTranslateY = 8;
            itemDef.primaryMaleModel = 63629;
            itemDef.primaryFemaleModel = 63630;
            itemDef.secondaryMaleModel = -1;
            itemDef.secondaryFemaleModel = -1;
            itemDef.primaryMaleHeadPiece = -1;
            itemDef.primaryFemaleHeadPiece = -1;
            itemDef.stackable = false;
            itemDef.certID = -1;
            itemDef.certTemplateID = -1;
            itemDef.unnotedId = -1;
            itemDef.certID = -1;
            itemDef.name = "Elvarg Torva Platelegs";
            itemDef.description = "Kaida Torva Platelegs";
        }
        return itemDef;
    }
}
