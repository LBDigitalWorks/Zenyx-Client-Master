package com.client;

import com.client.definitions.NpcDefinition;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
import com.client.definitions.anim.SequenceDefinition;
import com.client.entity.model.Model;
import com.client.features.settings.Preferences;
import com.client.util.headicon.class515;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldArea;
import net.runelite.rs.api.*;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import static com.client.Client.*;

public final class Npc extends Entity implements RSNPC {

	class515 field1329;

	@Override
	public boolean isVisible() {
		return desc != null;
	}

	Npc() {
	}

	public Model getRotatedModel() {
		if (!instance.isInterpolateNpcAnimations()
				|| this.getAnimation() == AnimationID.HELLHOUND_DEFENCE
				|| this.getAnimation() == 8270
				|| this.getAnimation() == 8271
				|| this.getPoseAnimation() == 5583
				|| this.getId() == NpcID.WYRM && this.getAnimation() == AnimationID.IDLE
				|| this.getId() == NpcID.TREE_SPIRIT && this.getAnimation() == AnimationID.IDLE
				|| this.getId() == NpcID.TREE_SPIRIT_6380 && this.getAnimation() == AnimationID.IDLE
				|| this.getId() == NpcID.TREE_SPIRIT_HARD && this.getAnimation() == AnimationID.IDLE
		)
		{
			return getModelVanilla();
		}
		int actionFrame = getActionFrame();
		int poseFrame = getPoseFrame();
		int spotAnimFrame = getSpotAnimFrame();
		try
		{
			// combine the frames with the frame cycle so we can access this information in the sequence methods
			// without having to change method calls
			setActionFrame(Integer.MIN_VALUE | getActionFrameCycle() << 16 | actionFrame);
			setPoseFrame(Integer.MIN_VALUE | getPoseFrameCycle() << 16 | poseFrame);
			setSpotAnimFrame(Integer.MIN_VALUE | getSpotAnimationFrameCycle() << 16 | spotAnimFrame);
			Iterator iter = getSpotAnims().iterator();
			while (iter.hasNext())
			{
				net.runelite.api.ActorSpotAnim actorSpotAnim = (net.runelite.api.ActorSpotAnim) iter.next();
				int frame = actorSpotAnim.getFrame();
				if (frame != -1)
				{
					actorSpotAnim.setFrame(Integer.MIN_VALUE | actorSpotAnim.getCycle() << 16 | frame);
				}
			}
			return getModelVanilla();
		}
		finally
		{
			// reset frames
			setActionFrame(actionFrame);
			setPoseFrame(poseFrame);
			setSpotAnimFrame(spotAnimFrame);
			Iterator iter = getSpotAnims().iterator();
			while (iter.hasNext())
			{
				net.runelite.api.ActorSpotAnim actorSpotAnim = (net.runelite.api.ActorSpotAnim) iter.next();
				int frame = actorSpotAnim.getFrame();
				if (frame != -1)
				{
					actorSpotAnim.setFrame(frame & '\uFFFF');
				}
			}
		}
	}

	public boolean isHintArrowPointingAtNpc(int index) {
		return Client.instance.hintArrowType == 1 && Client.instance.hintArrowNpcIndex == Client.instance.npcIndices[index - Client.instance.playerCount] && loopCycle % 20 < 10;
	}

	public int[] getSpriteIndices() {
		return this.field1329 != null ? this.field1329.method9299() : this.desc.getHeadIconArchiveIds();
	}

	public short[] getSpriteIds() {
		return this.field1329 != null ? this.field1329.method9300() : this.desc.headIconIndex();
	}


	public void drawIcons() {
		int[] spriteIndices = getSpriteIndices();
		short[] spriteIds = getSpriteIds();
		if (spriteIds != null && spriteIndices != null) {
			for (int index = 0; index < spriteIds.length; ++index) {
				if (spriteIds[index] >= 0 && spriteIndices[index] >= 0) {
					long cacheKey = (long) spriteIndices[index] << 8 | (long) spriteIds[index];
					Sprite icon = (Sprite) Client.archive5.method7781(cacheKey);
					if (icon == null) {
						Sprite[] sprites = Sprite.getSprites(spriteIndices[index], 0);
						if (sprites != null && spriteIds[index] < sprites.length) {
							icon = sprites[spriteIds[index]];
							Client.archive5.put(cacheKey, icon);
						}
					}

					if (icon != null) {
						Client.instance.npcScreenPos(this, defaultHeight + 15);
						if (Client.instance.viewportTempX > -1) {
							icon.drawSprite(viewportOffsetX + Client.instance.viewportTempX - (icon.myWidth >> 1), Client.instance.viewportTempY + (viewportOffsetY - icon.myHeight) - 4);
						}
					}
				}
			}
		}
	}

	public Model getModelVanilla() {
		if (this.desc == null) {
			return null;
		} else {
			SequenceDefinition sequenceDefinition = super.sequence != -1 && super.sequenceDelay == 0 ? SequenceDefinition.get(super.sequence) : null;
			SequenceDefinition walkSequenceDefinition = super.movementSequence == -1 || super.idleSequence == super.movementSequence && sequenceDefinition != null ? null : SequenceDefinition.get(super.movementSequence);
			Model animatedModel = this.desc.getAnimatedModel(sequenceDefinition, super.sequenceFrame, walkSequenceDefinition, super.movementFrame);
			if (animatedModel == null) {
				return null;
			} else {
				animatedModel.calculateBoundsCylinder();
				super.defaultHeight = animatedModel.model_height;
				int indicesCount = animatedModel.indicesCount;
				animatedModel = createSpotAnimModel(animatedModel);

				if (this.desc.size == 1) {
					animatedModel.isSingleTile = true;
				}



				if (super.recolourAmount != 0 && loopCycle >= super.recolourStartCycle && loopCycle < super.recolourEndCycle) {
					animatedModel.overrideHue = super.recolorHue;
					animatedModel.overrideSaturation = super.recolourSaturation;
					animatedModel.overrideLuminance = super.recolourLuminance;
					animatedModel.overrideAmount = super.recolourAmount;
					animatedModel.field3103 = (short)indicesCount;
				} else {
					animatedModel.overrideAmount = 0;
				}

				return animatedModel;
			}
		}
	}

	public boolean isShowMenuOnHover() {
		return npcPetType == 0 || npcPetType == 2 && !Preferences.getPreferences().hidePetOptions;
	}

	public int npcPetType;
	public NpcDefinition desc;

	@Override
	public int getCombatLevel() {
		return desc != null ? desc.combatLevel : 0;
	}

	@Nullable
	@Override
	public NPCComposition getTransformedComposition() {
		return desc;
	}

	@Override
	public void onDefinitionChanged(NPCComposition composition) {

	}


	@Override
	public int getId() {
		return desc.getId();
	}


	@Nullable
	@Override
	public String getName() {
		return desc != null ? desc.name : null;
	}

	@Override
	public Actor getInteracting() {
		int index = interactingEntity;
		if (index == -1 || index == 65535)
		{
			return null;
		}
		Client client = instance;

		if (index < 32768)
		{
			Npc[] npcs = (Npc[]) client.getCachedNPCs();
			return npcs[index];
		}

		index -= 32768;
		Player[] players = client.players;
		return players[index];
	}


	@Override
	public WorldArea getWorldArea() {
		return super.getWorldArea();
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public int getPoseAnimationFrame() {
		return 0;
	}

	@Override
	public void setPoseAnimationFrame(int frame) {

	}

	@Override
	public RSNPCComposition getComposition() {
		return desc;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public void setIndex(int id) {
		this.index = id;
	}



	@Override
	public int getModelHeight() {
		return model_height;
	}

	@Override
	public void setModelHeight(int modelHeight) {
		model_height = modelHeight;
	}

	@Override
	public RSModel getModel() {
		return getRotatedModel();
	}

	@Override
	public void draw(int orientation, int pitchSin, int pitchCos, int yawSin, int yawCos, int x, int y, int z, long hash) {

	}
}
