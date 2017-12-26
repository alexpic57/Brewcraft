package redgear.brewcraft.potions;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.core.Brewcraft;
import redgear.core.asm.RedGearCore;
import redgear.core.util.SimpleItem;

public class FluidPotion extends Fluid {

	public final int color;
	public final String localName;
	public final int elongation;
	public final int amplifier;
	public final ItemStack item;

	public FluidPotion(String fluidName, SimpleItem bottle, int duration, int potency) {
		this(fluidName, bottle.getStack(), duration, potency);
	}

	public FluidPotion(String fluidName, ItemStack bottle, int duration, int potency) {
		super(fluidName);

		if (Brewcraft.INSTANCE.isClient()) {
			String s = bottle.getDisplayName();

			if (potency > 0)
				s = s + " " + StatCollector.translateToLocal("potion.potency." + potency);
			if (duration > 1)
				s = s + " (" + Potion.getDurationString(new PotionEffect(Potion.confusion.id, duration, 0)) + ")";

			localName = s;
		} else
			localName = "";

		if (RedGearCore.inst.isClient())
			color = bottle.getItem().getColorFromItemStack(bottle, 0);
		else
			color = 0xFFFFFF;

		elongation = duration;
		amplifier = potency;
		item = bottle;

	}

	@Override
	public int getColor() {
		return color;
	}

	public int getDuration() {
		return elongation;
	}

	public int getStrength() {
		return amplifier;
	}

	/**
	 * Returns the localized name of this fluid.
	 */
	@Override
	public String getLocalizedName(FluidStack stack) {
		return getLocalizedName();
	}

	/**
	 * Returns the localized name of this fluid.
	 */
	@Override
	@Deprecated
	public String getLocalizedName() {
		return localName;
	}
}
