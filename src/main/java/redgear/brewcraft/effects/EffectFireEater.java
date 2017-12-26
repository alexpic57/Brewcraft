package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.plugins.core.AchievementPlugin;
import redgear.brewcraft.utils.ModDamageSource;

public class EffectFireEater extends PotionExtension {

	public EffectFireEater(int id) {
		super("fireEater", id, false, 0x660000);
		setIconIndex(6, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		//Maybe have health bar turn 'molten' while this is active?
		if (living.isBurning())
			living.heal(2 * strength + 1);
		else
			living.attackEntityFrom(ModDamageSource.FIRE_EATER, 2 * strength + 1);

		if (living instanceof EntityPlayer && AchievementPlugin.fireEater != null)
			((EntityPlayer) living).addStat(AchievementPlugin.fireEater, 1);
	}

	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		int k = 50 >> amplifier;
		return k > 0 ? duration % k == 0 : true;
	}

}
