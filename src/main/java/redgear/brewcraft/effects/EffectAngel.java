package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.core.AchievementPlugin;

import java.lang.reflect.Method;

public class EffectAngel extends PotionExtension {

	public EffectAngel() {
		super("angel", false, 0xFFFF33);
		setIconIndex(1, 0);
		setRegistryName(Brewcraft.MOD_ID, "angel");
	}

	/**
	 * Perform this potion's normal ever-tick effect.
	 */
	@Override
	public void performEffect(EntityLivingBase living, int strength) {

		//Maybe have health bar turn white/gold while this is active?
		if (living instanceof EntityZombieVillager) {
			convertToVillager((EntityZombieVillager) living);
			//ToDo: test this later (was leaving a ghost entity when tested on entity spawn event)
			/*
			try {
				Method finishConversion = EntityZombieVillager.class.getDeclaredMethod("finishConversion", null);
				finishConversion.setAccessible(true);
				finishConversion.invoke(living);

			} catch(Exception e) {
				Brewcraft.logger.warn("EffectAngel need a fix");
			}
			*/
			return;
		}

		if (living.isEntityUndead())
			living.attackEntityFrom(DamageSource.MAGIC, strength * 2 + 2F);
		else
			living.heal(strength + 1 * 1F);

		if (living instanceof EntityPlayer && AchievementPlugin.holywater != null)
			((EntityPlayer) living).addStat(AchievementPlugin.holywater, 1);
	}

	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		int k = 50 >> amplifier;
		return k > 0 ? duration % k == 0 : true;
	}

	/**
	 * Convert this zombie into a villager.
	 * The method in EntityZombieVillager is protected.
	 */
	protected void convertToVillager(EntityZombieVillager living) {
		EntityVillager entityvillager = new EntityVillager(living.world);
		entityvillager.copyLocationAndAnglesFrom(living);
		entityvillager.setProfession(living.getForgeProfession());
		entityvillager.finalizeMobSpawn(living.world.getDifficultyForLocation(new BlockPos(entityvillager)), null, false);
		entityvillager.setLookingForHome();

		if (living.isChild()) {
			entityvillager.setGrowingAge(-24000);
		}

		living.world.removeEntity(living);
		entityvillager.setNoAI(living.isAIDisabled());

		if (living.hasCustomName()) {
			entityvillager.setCustomNameTag(living.getCustomNameTag());
			entityvillager.setAlwaysRenderNameTag(living.getAlwaysRenderNameTag());
		}

		living.world.spawnEntity(entityvillager);
		entityvillager.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
		living.world.playEvent(null, 1017, new BlockPos((int) living.posX, (int) living.posY, (int) living.posZ), 0);
	}
}
