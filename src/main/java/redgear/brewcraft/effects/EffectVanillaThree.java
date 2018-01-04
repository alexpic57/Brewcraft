package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import redgear.brewcraft.utils.ModMobEffects;

public class EffectVanillaThree extends Potion {

	public EffectVanillaThree(String name, boolean isBadEffect, int liquidColor) {
		super(isBadEffect, liquidColor);
		setPotionName("potion." + name);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		if (this == ModMobEffects.REGENERATION) {
			if (living.getHealth() < living.getMaxHealth())
				living.heal(1.5F);
		} else if (this == ModMobEffects.POISON) {
			if (living.getHealth() > 1.5F)
				living.attackEntityFrom(DamageSource.MAGIC, 1.5F);
		} else if (this == ModMobEffects.WITHER)
			living.attackEntityFrom(DamageSource.WITHER, 1.5F);
	}

	@Override
	public boolean isReady(int par1, int par2) {
		int k;

		if (this == ModMobEffects.REGENERATION) {
			k = 50 >> par2;
			return k > 0 ? par1 % k == 0 : true;
		} else if (this == ModMobEffects.POISON) {
			k = 25 >> par2;
			return k > 0 ? par1 % k == 0 : true;
		} else if (this == ModMobEffects.WITHER) {
			k = 40 >> par2;
			return k > 0 ? par1 % k == 0 : true;
		} else {
			return false;
		}
	}
}
