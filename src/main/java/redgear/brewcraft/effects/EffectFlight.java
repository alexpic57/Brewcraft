package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.core.AchievementPlugin;

public class EffectFlight extends PotionExtension {

	public EffectFlight() {
		super("flight", false, 0xFFFFFF);
		setIconIndex(2, 0);
		setRegistryName(Brewcraft.MOD_ID, "flight");
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		int duration = living.getActivePotionEffect(this).getDuration();

		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			if (duration <= 1) {
				if (!player.capabilities.isCreativeMode) {
					player.capabilities.allowFlying = false;
					player.capabilities.isFlying = false;
				}
			} else
				player.capabilities.allowFlying = true;
			if (!player.world.isRemote)
				player.sendPlayerAbilities();
			if (AchievementPlugin.flight != null)
				player.addStat(AchievementPlugin.flight, 1);
		}
	}
}
