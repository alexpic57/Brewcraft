package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.DamageSource;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.core.AchievementPlugin;

import java.lang.reflect.Field;

public class EffectCreeper extends PotionExtension {
	
	public EffectCreeper() {
		super("creeper", true, 0x00CC00);
		setIconIndex(0, 0);
		setRegistryName(Brewcraft.MOD_ID, "creeper");
	}

	@Override 
	public void performEffect(EntityLivingBase living, int strength) {
		int duration = living.getActivePotionEffect(this).getDuration();
		boolean flag = living.world.getGameRules().getBoolean("mobGriefing");

		if (living instanceof EntityCreeper && duration == 1) {
			try {
				Field powered = EntityCreeper.class.getDeclaredField("POWERED");
				powered.setAccessible(true);
				living.getDataManager().set((DataParameter<Boolean>) powered.get(living), Boolean.valueOf(true));
			} catch(Exception e) {
				Brewcraft.logger.warn("EffectCreeper need a fix");
			}
		}

		else {

			if (duration == 1) {

				if (living instanceof EntityPlayer && ((EntityPlayer) living).capabilities.isCreativeMode)
					return;//Don't explode if target is player in creative. 

				living.world.createExplosion(null, living.posX, living.posY, living.posZ, strength * 3 + 4, flag);
				living.attackEntityFrom(DamageSource.GENERIC, 100F);
			}
			if (living instanceof EntityPlayer && AchievementPlugin.explode != null)
				((EntityPlayer) living).addStat(AchievementPlugin.explode, 1);
		}
	}
}
