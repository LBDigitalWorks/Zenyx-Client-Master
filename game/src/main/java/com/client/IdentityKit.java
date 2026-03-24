package com.client;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.client.collection.EvictingDualNodeHashTable;
import com.client.collection.node.DualNode;
import com.client.entity.model.ModelData;
import com.client.js5.Js5List;
import com.client.js5.util.Js5ConfigType;

import java.util.Arrays;

public final class IdentityKit extends DualNode {

	private static final String[] BODY_PART_NAMES = {
		"Head/Hair (M)", "Jaw/Beard (M)", "Torso (M)", "Arms (M)", "Hands (M)", "Legs (M)", "Feet (M)",
		"Head/Hair (F)", "Jaw (F)", "Torso (F)", "Arms (F)", "Hands (F)", "Legs (F)", "Feet (F)"
	};

	public static EvictingDualNodeHashTable cached = new EvictingDualNodeHashTable(64);

	private static boolean dumpedFemaleKits = false;

	public static IdentityKit lookup(int id) {
		IdentityKit hasCached = (IdentityKit)IdentityKit.cached.get(id);
		if (hasCached == null) {
			byte[] data = Js5List.configs.takeFile(Js5ConfigType.IDENTKIT, id);
			hasCached = new IdentityKit();
			hasCached.kitId = id; // Store the kit ID
			if (data != null) {
				hasCached.decode(new Buffer(data));
				hasCached.originalColors[0] = (short) 55232;
				hasCached.replacementColors[0] = 6798;

				// Debug: Log female kits with issues when first loaded
				if (hasCached.bodyPartId >= 7 && hasCached.bodyPartId <= 13) {
					String partName = BODY_PART_NAMES[hasCached.bodyPartId];
					if (hasCached.bodyModels == null) {
						System.out.println("[IDK LOAD] Female kit " + id + " (" + partName + ") has NO BODY MODELS!");
					} else if (containsInvalidModel(hasCached.bodyModels)) {
						System.out.println("[IDK LOAD] Female kit " + id + " (" + partName + ") has -1 in models: " +
							Arrays.toString(hasCached.bodyModels));
					}
				}
			} else {
				System.out.println("[IDK LOAD] Kit " + id + " has NULL cache data!");
			}

			cached.put(hasCached, id);
		}
		return hasCached;
	}

	/**
	 * Call this once after cache is loaded to dump all female identity kits.
	 * Add a call to this from Client initialization if needed.
	 */
	public static void dumpFemaleKitsOnce() {
		if (!dumpedFemaleKits) {
			dumpedFemaleKits = true;
			dumpIdentityKits(true);
		}
	}

	/**
	 * Debug method: Dumps all identity kits for a specific gender to console.
	 * @param female true for female kits (bodyPartId 7-13), false for male (0-6)
	 */
	public static void dumpIdentityKits(boolean female) {
		int minBodyPart = female ? 7 : 0;
		int maxBodyPart = female ? 13 : 6;
		String gender = female ? "FEMALE" : "MALE";

		System.out.println("========== " + gender + " IDENTITY KITS DUMP ==========");

		int kitCount = Js5List.configs.getGroupFileCount(Js5ConfigType.IDENTKIT);
		System.out.println("Total identity kits in cache: " + kitCount);

		for (int i = 0; i < kitCount; i++) {
			IdentityKit kit = lookup(i);
			if (kit != null && kit.bodyPartId >= minBodyPart && kit.bodyPartId <= maxBodyPart) {
				String partName = (kit.bodyPartId >= 0 && kit.bodyPartId < BODY_PART_NAMES.length)
					? BODY_PART_NAMES[kit.bodyPartId] : "Unknown";
				String modelsStr = kit.bodyModels != null ? Arrays.toString(kit.bodyModels) : "NULL";
				String status = kit.bodyModels == null ? " [NO MODELS!]" :
					(containsInvalidModel(kit.bodyModels) ? " [HAS -1 MODEL!]" : "");

				System.out.println("[IDK " + i + "] bodyPartId=" + kit.bodyPartId +
					" (" + partName + ") validStyle=" + kit.validStyle +
					" bodyModels=" + modelsStr + status);
			}
		}

		System.out.println("========== END " + gender + " IDENTITY KITS DUMP ==========");
	}

	private static boolean containsInvalidModel(int[] models) {
		for (int model : models) {
			if (model == -1) return true;
		}
		return false;
	}

	/**
	 * Debug method: Dumps detailed info for a single identity kit.
	 */
	public static void dumpSingleKit(int id) {
		IdentityKit kit = lookup(id);
		if (kit == null) {
			System.out.println("[IDK " + id + "] NOT FOUND");
			return;
		}
		String partName = (kit.bodyPartId >= 0 && kit.bodyPartId < BODY_PART_NAMES.length)
			? BODY_PART_NAMES[kit.bodyPartId] : "Unknown";
		System.out.println("========== IDENTITY KIT " + id + " ==========");
		System.out.println("  bodyPartId: " + kit.bodyPartId + " (" + partName + ")");
		System.out.println("  validStyle: " + kit.validStyle);
		System.out.println("  bodyModels: " + (kit.bodyModels != null ? Arrays.toString(kit.bodyModels) : "NULL"));
		System.out.println("  headModels: " + Arrays.toString(kit.headModels));
		System.out.println("  originalColors: " + Arrays.toString(kit.originalColors));
		System.out.println("  replacementColors: " + Arrays.toString(kit.replacementColors));
		System.out.println("==========================================");
	}

	public void decode(Buffer buffer) {
		while (true) {
			final int opcode = buffer.readUnsignedByte();
			if (opcode == 0) {
				break;
			}
			if (opcode == 1) {
				bodyPartId = buffer.readUnsignedByte();
			} else if (opcode == 2) {
				int length = buffer.readUnsignedByte();
				bodyModels = new int[length];
				for (int index = 0; index < length; ++index) {
					bodyModels[index] = buffer.readUShort();
					if (bodyModels[index] == 65535) {
						bodyModels[index] = -1;
					}
				}
			} else if (opcode == 3) {
				validStyle = true;
			} else if (opcode == 40) {
				int length = buffer.readUnsignedByte();
				originalColors = new short[length];
				replacementColors = new short[length];

				for (int index = 0; index < length; ++index) {
					originalColors[index] = (short) buffer.readShort();
					replacementColors[index] = (short) buffer.readShort();
				}
			} else if (opcode == 41) {
				int length = buffer.readUnsignedByte();
				retextureToFind = new short[length];
				retextureToReplace = new short[length];

				for (int index = 0; index < length; ++index) {
					retextureToFind[index] = (short) buffer.readShort();
					retextureToReplace[index] = (short) buffer.readShort();
				}
			} else if (opcode >= 60 && opcode < 70) {
				headModels[opcode - 60] = buffer.readUShort();
			} else {
				System.out.println("Error unrecognised config code: " + opcode);
			}
		}
	}

	public boolean bodyCached() {
		if (bodyModels == null)
			return true;
		boolean flag = true;
        for (int bodyModel : bodyModels)
            if (!Js5List.models.tryLoadFile(bodyModel))
                flag = false;

		return flag;
	}

	public ModelData getBody() {
		if (bodyModels == null) {
			String partName = (bodyPartId >= 0 && bodyPartId < BODY_PART_NAMES.length)
				? BODY_PART_NAMES[bodyPartId] : "Unknown";
			System.out.println("[IDK DEBUG] Kit has NULL bodyModels! bodyPartId=" + bodyPartId + " (" + partName + ")");
			return null;
		}

		// Log model IDs when player appearance debug is active
		if (Player.DEBUG_APPEARANCE) {
			String partName = (bodyPartId >= 0 && bodyPartId < BODY_PART_NAMES.length) ? BODY_PART_NAMES[bodyPartId] : "Unknown";
			System.out.println("  -> KIT " + kitId + " bodyPartId=" + bodyPartId + " (" + partName + ") models=" + Arrays.toString(bodyModels));
		}

		ModelData[] models = new ModelData[bodyModels.length];

		for (int part = 0; part < bodyModels.length; part++) {
			models[part] = ModelData.getModel(bodyModels[part]);
			if (models[part] == null) {
				String partName = (bodyPartId >= 0 && bodyPartId < BODY_PART_NAMES.length)
					? BODY_PART_NAMES[bodyPartId] : "Unknown";
				System.out.println("[IDK DEBUG] Failed to load model! bodyPartId=" + bodyPartId +
					" (" + partName + ") modelId=" + bodyModels[part]);
			}
		}

		ModelData model;
		if (models.length == 1) {
			model = models[0];
		} else {
			model = new ModelData(models,models.length);
		}

		if (model != null) {
			if (originalColors != null) {
				for (int index = 0; index < originalColors.length; index++) {
					model.recolor(originalColors[index], replacementColors[index]);
				}
				if (retextureToFind != null) {
					for (int index = 0; index < retextureToFind.length; index++) {
						model.retexture(retextureToFind[index], retextureToReplace[index]);
					}
				}
			}
		}

		return model;
	}
	public boolean headLoaded() {
		boolean flag1 = true;
		for (int i = 0; i < 5; i++)
			if (headModels[i] != -1 && !Js5List.models.tryLoadFile(headModels[i]))
				flag1 = false;

		return flag1;
	}

	public ModelData headModel() {
		ModelData[] headModels = new ModelData[5];
		int modelIndex = 0;

		for(int index = 0; index < 5; ++index) {
			if (this.headModels[index] != -1) {
				headModels[modelIndex++] = ModelData.getModel(this.headModels[index]);
			}
		}

		ModelData headModeel = new ModelData(headModels,modelIndex);
		if (this.originalColors != null) {
			for(int index = 0; index < this.originalColors.length; ++index) {
				headModeel.recolor(this.originalColors[index], this.replacementColors[index]);
			}
		}

		if (this.retextureToFind != null) {
			for(int index = 0; index < this.retextureToFind.length; ++index) {
				headModeel.retexture(this.retextureToFind[index], this.retextureToReplace[index]);
			}
		}

		return headModeel;
	}

	public int bodyPartId = -1;
	public int kitId = -1; // Track which kit ID this is
	private int[] bodyModels;
	private short[] originalColors = new short[6];
	private short[] replacementColors = new short[6];
	public short[] retextureToFind;
	public short[] retextureToReplace;
	private final int[] headModels = { -1, -1, -1, -1, -1 };
	public boolean validStyle = false;
}
