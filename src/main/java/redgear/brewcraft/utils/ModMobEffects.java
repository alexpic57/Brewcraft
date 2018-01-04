package redgear.brewcraft.utils;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.effects.*;

public class ModMobEffects {
	public static final Potion ANGEL = new EffectAngel(Brewcraft.configuration.get("Potion Effect IDs", "'Angelic' Effect ID", 40, "Must be over 20. Must also be lowered if you have disabled the potion list expansion.").getInt());
	public static final Potion FLIGHT = new EffectFlight(Brewcraft.configuration.get("Potion Effect IDs", "'Flight' Effect ID", 41).getInt());
	public static final Potion CREEPER = new EffectCreeper(Brewcraft.configuration.get("Potion Effect IDs", "'Combustion' Effect ID", 42).getInt());
	public static final Potion IMMUNITY = new EffectImmunity(Brewcraft.configuration.get("Potion Effect IDs", "'Immunity' Effect ID", 43).getInt());
	public static final Potion FROZEN = new EffectFrozen(Brewcraft.configuration.get("Potion Effect IDs", "'Frozen' Effect ID", 44).getInt()).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160891", -0.95000000596046448D, 2);
	public static final Potion FIREPROOF = new EffectFireproof(Brewcraft.configuration.get("Potion Effect IDs", "'Fireproof' Effect ID", 45).getInt());
	public static final Potion FLAME = new EffectEternalFlame(Brewcraft.configuration.get("Potion Effect IDs", "'Eternal Flame' Effect ID", 46).getInt());
	public static final Potion FIRE_EATER = new EffectFireEater(Brewcraft.configuration.get("Potion Effect IDs", "'Fire Eater' Effect ID", 47).getInt());
	public static final Potion POISON = new EffectVanillaThree("POISON", Brewcraft.configuration.get("Potion Effect IDs", "'Poison' Effect ID", 48).getInt(), true, 5149489);
	public static final Potion WITHER = new EffectVanillaThree("WITHER", Brewcraft.configuration.get("Potion Effect IDs", "'Wither' Effect ID", 49).getInt(), true, 3484199);
	public static final Potion REGENERATION = new EffectVanillaThree("REGENERATION", Brewcraft.configuration.get("Potion Effect IDs", "'Regeneration' Effect ID", 50).getInt(), false, 13458603);

	static {
		ForgeRegistries.POTIONS.registerAll(ANGEL, FLIGHT, CREEPER, IMMUNITY, FROZEN, FIREPROOF, FLAME, FIRE_EATER, POISON, WITHER, REGENERATION);
	}
}
