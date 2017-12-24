package redgear.brewcraft.plugins.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.block.KegPlugin;
import redgear.brewcraft.plugins.block.MachinePlugin;
import redgear.brewcraft.plugins.item.ItemPlugin;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.ItemStackUtil;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingPlugin implements IPlugin {

	@Override
	public String getName() {
		return "CraftingPlugin";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return true;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	@Override
	public void preInit(ModUtils mod) {

	}

	@Override
	public void Init(ModUtils mod) {
		if (Brewcraft.configuration.get("Recipes", "Golden Feather Crafting Recipe", true).getBoolean())
			GameRegistry.addShapedRecipe(ItemPlugin.goldenFeather.getStack(), new Object[] {"!!!", "!@!", "!!!",
					Character.valueOf('!'), Items.gold_nugget, Character.valueOf('@'), Items.feather });

		if (Brewcraft.configuration.get("Recipes", "Heart Medallion Crafting Recipe", true).getBoolean())
			GameRegistry.addShapedRecipe(
					ItemPlugin.heartGold.getStack(),
					new Object[] {"!!!", "!@!", "!!!", Character.valueOf('!'), Items.gold_nugget,
							Character.valueOf('@'), ItemPlugin.heartSmall.getItem() });

		if (Brewcraft.configuration.get("Recipes", "Tired Spores Crafting Recipe", true).getBoolean())
			GameRegistry.addShapelessRecipe(ItemPlugin.tiredSpores.getStack(3), new Object[] {Blocks.brown_mushroom,
					Blocks.red_mushroom, Items.spider_eye });

		if (Brewcraft.configuration.get("Recipes", "Medicinal Salve Crafting Recipe", true).getBoolean())
			GameRegistry.addShapelessRecipe(ItemPlugin.remedySalve.getStack(3), new Object[] {Items.paper, Items.sugar,
					Items.redstone });

		if (Brewcraft.configuration.get("Recipes", "Splash Bottle Crafting Recipe", true).getBoolean())
			GameRegistry.addShapedRecipe(ItemPlugin.splashBottle.getStack(3), new Object[] {" @!", "@ @", " @ ",
					Character.valueOf('!'), Items.gunpowder, Character.valueOf('@'), Blocks.glass });
		
		if (Brewcraft.configuration.get("Recipes", "Large Bottle Crafting Recipe", true).getBoolean())
			GameRegistry.addShapedRecipe(ItemPlugin.emptyBigBottle.getStack(3), new Object[] {"G G", "G G", "GGG", 'G', Blocks.glass});
		
		if (Brewcraft.configuration.get("Recipes", "Large Splash Bottle Crafting Recipe", true).getBoolean())
			GameRegistry.addShapedRecipe(ItemPlugin.splashBigBottle.getStack(3), new Object[] {"G G", "G!G", "GGG", 'G', Blocks.glass, '!', Items.gunpowder});
		
		if (Brewcraft.configuration.get("Recipes", "Vial Crafting Recipe", true).getBoolean())
			GameRegistry.addShapedRecipe(ItemPlugin.emptyVial.getStack(3), new Object[] {"G", "G", 'G', Blocks.glass});
		
		if (Brewcraft.configuration.get("Recipes", "Splash Vial Crafting Recipe", true).getBoolean())
			GameRegistry.addShapedRecipe(ItemPlugin.splashVial.getStack(3), new Object[] {"!", "G", "G", 'G', Blocks.glass, '!', Items.gunpowder});
		

		boolean ironOverride = false;

		if (Brewcraft.configuration.get("Recipes", "Lead Brewery", "Toggle crafting the Brewery with Lead if available").getBoolean())
			ironOverride = breweryRecipe("ingotLead");

		if (Brewcraft.configuration
				.get("Recipes", "Brass Brewery", "Toggle crafting the Brewery with Brass if available").getBoolean())
			ironOverride = ironOverride || breweryRecipe("ingotBrass");

		if (!(ironOverride && !Brewcraft.configuration.get("Recipes", "Iron Brewery",
				"Toggle crafting the Brewery with Iron. (Can only be disabled if Lead or Brass is available)").getBoolean()))
			breweryRecipe("ingotIron"); //Dat boolean expression!

		if (Brewcraft.configuration.get("Recipes", "Metal Ring Crafting Recipe", true).getBoolean())
			GameRegistry.addShapedRecipe(ItemPlugin.metalRing.getStack(2),
					new Object[] {" @ ", "@ @", " @ ", Character.valueOf('@'), Items.iron_ingot });

		if (Brewcraft.configuration.get("Recipes", "Sprayer Crafting Recipe", true).getBoolean())
			GameRegistry.addShapedRecipe(MachinePlugin.sprayer.getStack(),
					new Object[] {" @ ", "!#!", "!$!", Character.valueOf('@'), Blocks.dispenser,
							Character.valueOf('!'), Blocks.cobblestone, Character.valueOf('#'), KegPlugin.kegs,
							Character.valueOf('$'), Items.redstone });

		if (Brewcraft.configuration.get("Recipes", "Charred Bone Crafting Recipe", true).getBoolean())
			GameRegistry.addSmelting(Items.bone, ItemPlugin.charredBone.getStack(), 0.1F);

		if (Brewcraft.configuration.get("Recipes", "Steel Scales Smelting Recipe", true).getBoolean()) {
			GameRegistry.addSmelting(Items.chainmail_boots, ItemPlugin.steelScales.getStack(4), 0.1F);
			GameRegistry.addSmelting(Items.chainmail_chestplate, ItemPlugin.steelScales.getStack(8), 0.3F);
			GameRegistry.addSmelting(Items.chainmail_helmet, ItemPlugin.steelScales.getStack(5), 0.2F);
			GameRegistry.addSmelting(Items.chainmail_leggings, ItemPlugin.steelScales.getStack(7), 0.3F);
		}

		if (Brewcraft.configuration.get("Recipes", "Sealed Keg Crafting Recipe", true).getBoolean()) {
			GameRegistry.addShapelessRecipe(KegPlugin.kegSealed.getStack(), new Object[] {KegPlugin.kegOak.getStack(),
					Items.slime_ball });
			GameRegistry.addShapelessRecipe(KegPlugin.kegSealed.getStack(), new Object[] {
					KegPlugin.kegBirch.getStack(), Items.slime_ball });
			GameRegistry.addShapelessRecipe(KegPlugin.kegSealed.getStack(),
					new Object[] {KegPlugin.kegJungle.getStack(), Items.slime_ball });
			GameRegistry.addShapelessRecipe(KegPlugin.kegSealed.getStack(),
					new Object[] {KegPlugin.kegSpruce.getStack(), Items.slime_ball });
			GameRegistry.addShapelessRecipe(KegPlugin.kegSealed.getStack(),
					new Object[] {KegPlugin.kegAcacia.getStack(), Items.slime_ball });
			GameRegistry.addShapelessRecipe(KegPlugin.kegSealed.getStack(), new Object[] {KegPlugin.kegDark.getStack(),
					Items.slime_ball });
		}

		if (Brewcraft.configuration.get("Recipes", "Plated Keg Crafting Recipe", true).getBoolean())
			GameRegistry.addShapelessRecipe(KegPlugin.kegPlated.getStack(), new Object[] {KegPlugin.kegIron.getStack(),
					Items.slime_ball });

		addKegRecipe(KegPlugin.kegOak.getStack(), new ItemStack(Blocks.planks, 1, 0));
		addKegRecipe(KegPlugin.kegSpruce.getStack(), new ItemStack(Blocks.planks, 1, 1));
		addKegRecipe(KegPlugin.kegBirch.getStack(), new ItemStack(Blocks.planks, 1, 2));
		addKegRecipe(KegPlugin.kegJungle.getStack(), new ItemStack(Blocks.planks, 1, 3));
		addKegRecipe(KegPlugin.kegAcacia.getStack(), new ItemStack(Blocks.planks, 1, 4));
		addKegRecipe(KegPlugin.kegDark.getStack(), new ItemStack(Blocks.planks, 1, 5));
		addKegRecipe(KegPlugin.kegIron.getStack(), new ItemStack(Blocks.iron_block));
		//addKegRecipe(KegPlugin.kegSealed.getStack(), new ItemStack());
		//addKegRecipe(KegPlugin.kegPlated.getStack(), new ItemStack());
		addOreDictKegRecipe(KegPlugin.kegSteel.getStack(), "blockSteel");
		addOreDictKegRecipe(KegPlugin.kegCopper.getStack(), "blockCopper");
		addOreDictKegRecipe(KegPlugin.kegSilver.getStack(), "blockSilver");
		addOreDictKegRecipe(KegPlugin.kegTungsten.getStack(), "blockTungsten");
		addOreDictKegRecipe(KegPlugin.kegBrass.getStack(), "blockBrass");
		addOreDictKegRecipe(KegPlugin.kegRubber.getStack(), "blockRubber");
	}

	@Override
	public void postInit(ModUtils mod) {

	}

	private boolean breweryRecipe(String ingot) {
		ItemStack metal = ItemStackUtil.getOreWithName(ingot);

		if (metal != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(MachinePlugin.brewery.getStack(), new Object[] {"! !", "!@!",
					"#!#", Character.valueOf('!'), ingot, Character.valueOf('@'), Items.brewing_stand,
					Character.valueOf('#'), Items.cauldron }));
			return true;
		} else
			return false;
	}

	private void addKegRecipe(ItemStack output, ItemStack material) {
		if (Brewcraft.configuration.get("Recipes", output.getDisplayName() + " Recipe", true).getBoolean())
			GameRegistry.addShapedRecipe(output, new Object[] {" ! ", " @ ", " ! ", Character.valueOf('!'),
					ItemPlugin.metalRing.getStack(), Character.valueOf('@'), material });
	}

	private void addOreDictKegRecipe(ItemStack output, String material) {
		if (Brewcraft.configuration.get("Recipes", output.getDisplayName() + " Recipe", true).getBoolean())
			GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[] {" ! ", " @ ", " ! ",
					Character.valueOf('!'), ItemPlugin.metalRing.getStack(), Character.valueOf('@'), material }));
	}
}
