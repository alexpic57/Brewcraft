package redgear.brewcraft.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import redgear.brewcraft.core.Brewcraft;

import javax.annotation.Nullable;

public abstract class PotionExtension extends Potion {

	public PotionExtension(String name, boolean isBadEffect, int liquidColor) {
		super(isBadEffect, liquidColor);
		setPotionName("effect." + Brewcraft.MOD_ID + "." + name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("redgear_brewcraft:textures/potion/icons.png"));
		return super.getStatusIconIndex();
	}

	@Override
	/**
	 * Perform this potion's normal every-tick effect. 
	 */
	public abstract void performEffect(EntityLivingBase living, int strength);

	@Override
	public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {

	}

	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
}
