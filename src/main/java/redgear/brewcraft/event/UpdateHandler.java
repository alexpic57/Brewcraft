package redgear.brewcraft.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import redgear.brewcraft.utils.ModMobEffects;

public class UpdateHandler {

	private static UpdateHandler instance;

	private UpdateHandler() {

	}

	public static UpdateHandler register() {
		if (instance == null) {
			instance = new UpdateHandler();
			MinecraftForge.EVENT_BUS.register(instance);

		}
		return instance;
	}
	
	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (player.getActivePotionEffect(ModMobEffects.IMMUNITY) != null) {
				int strength = player.getActivePotionEffect(ModMobEffects.IMMUNITY).getAmplifier();

				player.removePotionEffect(Potion.poison.id);
				player.removePotionEffect(ModMobEffects.POISON.id);

				if (strength >= 1) {
					player.removePotionEffect(Potion.hunger.id);
					player.removePotionEffect(Potion.weakness.id);
					player.removePotionEffect(Potion.moveSlowdown.id);
					player.removePotionEffect(ModMobEffects.FROZEN.id);
				}

				if (strength >= 2) {
					player.removePotionEffect(Potion.wither.id);
					player.removePotionEffect(ModMobEffects.WITHER.id);
					player.removePotionEffect(Potion.confusion.id);
					player.removePotionEffectClient(Potion.blindness.id);
				}

				Set<Integer> idsToRemove = new HashSet<Integer>();

				if (strength >= 3) {
					Collection<PotionEffect> map = player.getActivePotionEffects();

					for (PotionEffect effect : map) {
						int id = effect.getPotionID();

						if (id < Potion.potionTypes.length && Potion.potionTypes[id].isBadEffect())
							idsToRemove.add(id);
					}

					for(int id : idsToRemove)
						player.removePotionEffect(id);
				}
			}
		}
	}
}
