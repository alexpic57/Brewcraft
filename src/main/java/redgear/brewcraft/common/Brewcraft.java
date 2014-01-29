package redgear.brewcraft.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.brewcraft.blocks.RenderItemBrewery;
import redgear.brewcraft.blocks.TileEntityBrewery;
import redgear.brewcraft.blocks.TileRendererBrewery;
import redgear.brewcraft.potions.CustomPotionEffects;
import redgear.brewcraft.potions.EntityBrewcraftPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.RenderBrewcraftPotion;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.brewcraft.recipes.RecipeRegistry;
import redgear.core.block.MetaTile;
import redgear.core.block.MetaTileSpecialRenderer;
import redgear.core.block.SubTileMachine;
import redgear.core.compat.ModConfigHelper;
import redgear.core.compat.Mods;
import redgear.core.fluids.FluidUtil;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.mod.ModUtils;
import redgear.core.mod.RedGear;
import redgear.core.network.CoreGuiHandler;
import redgear.core.util.CoreDungeonLoot;
import redgear.core.util.CoreDungeonLoot.LootRarity;
import redgear.core.util.CoreTradeHandler;
import redgear.core.util.ItemStackUtil;
import redgear.core.util.SimpleItem;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "RedGear|Brewcraft", name = "Brewcraft", version = "@ModVersion@", dependencies = "required-after:RedGear|Core;")
public class Brewcraft extends ModUtils {

	public Brewcraft() {
		super(3578, 11972);
	}

	@Instance("RedGear|Brewcraft")
	public static ModUtils inst;

	public static RecipeRegistry registry = new RecipeRegistry();

	public static MetaItem ingredients;
	public static SimpleItem holydust;
	public static SimpleItem goldenfeather;
	public static SimpleItem charredbone;
	public static SimpleItem splashBottle;

	public static SimpleItem emptyBottle = new SimpleItem(Item.glassBottle);

	public static MetaItemPotion potions;

	public static MetaTile brewing;
	public static SimpleItem brewery;

	private final String breweryTexture = "brewery";

	public static ItemStack soul;
	public static ItemStack dust;
	public static ItemStack crystal;
	public static ItemStack bone;
	public static ItemStack sulfur;
	public static ItemStack brain;
	public static ItemStack goo;
	public static ItemStack tendril;

	public static Fluid fluidHolyWater;
	public static Fluid fluidHolyWaterII;
	public static Fluid fluidHolyWaterLong;
	public static Fluid fluidFlying;
	public static Fluid fluidFlyingLong;
	public static Fluid fluidWither;
	public static Fluid fluidWitherLong;
	public static Fluid fluidWitherII;
	public static Fluid fluidAntidote;
	public static Fluid fluidAntidoteII;
	public static Fluid fluidAntidoteLong;
	public static Fluid fluidBoom;
	public static Fluid fluidBoomLong;
	public static Fluid fluidBoomII;
	public static Fluid fluidAwkward;
	public static Fluid fluidVision;
	public static Fluid fluidVisionLong;
	public static Fluid fluidInvisible;
	public static Fluid fluidInvisibleLong;
	public static Fluid fluidRegen;
	public static Fluid fluidRegenLong;
	public static Fluid fluidFast;
	public static Fluid fluidFastLong;
	public static Fluid fluidFastII;
	public static Fluid fluidWeakness;
	public static Fluid fluidStrength;
	public static Fluid fluidStrengthLong;
	public static Fluid fluidStrengthII;
	public static Fluid fluidFireResist;
	public static Fluid fluidFireResistLong;
	public static Fluid fluidSlowness;
	public static Fluid fluidSlownessLong;
	public static Fluid fluidPoison;
	public static Fluid fluidPoisonII;
	public static Fluid fluidPoisonLong;
	public static Fluid fluidHarm;
	public static Fluid fluidHarmII;
	public static Fluid fluidHealing;
	public static Fluid fluidHealingII;

	public final int DEFAULT_TIME = 7;
	public final int ITEM_CONSUMPTION_BASE = 30;

	@Override
	protected void PreInit(FMLPreInitializationEvent event) {
		
		if(getBoolean("Potion List Expansion", "Toggle Potion List Expansion", "Disable it you are running another mod that expands the list.", true))
			expandPotionList();
		
		CustomPotionEffects cpe = new CustomPotionEffects(this);
		
		EntityRegistry.registerModEntity(EntityBrewcraftPotion.class, "Potion", EntityRegistry.findGlobalUniqueEntityId(), this, 128, 10, true);
		RenderingRegistry.registerEntityRenderingHandler(EntityBrewcraftPotion.class, new RenderBrewcraftPotion());

		ingredients = new MetaItem(getItemId("ingredients"), "RedGear.Brewcraft.Ingredients");
		holydust = ingredients.addMetaItem(new SubItem("holydust"));
		goldenfeather = ingredients.addMetaItem(new SubItem("goldenfeather"));
		charredbone = ingredients.addMetaItem(new SubItem("charredbone"));
		splashBottle = ingredients.addMetaItem(new SubItem("splashBottle"));

		potions = new MetaItemPotion(getItemId("potions"), "RedGear.Brewcraft.Potions");

		/*
		 * createSpecialPotion("Fire", new SubPotionEffect() {
		 * 
		 * @Override
		 * public void effect(World world, EntityLivingBase player) {
		 * player.setFire(10);
		 * }
		 * });
		 */

		fluidHolyWater = createPotion("HolyWater", "potionGold", cpe.angel, 100, 0);
		fluidHolyWaterII = createPotion("HolyWaterII", "potionGold", cpe.angel, 50, 1);
		fluidHolyWaterLong = createPotion("HolyWaterLong", "potionGold", cpe.angel, 200, 0);

		fluidFlying = createPotion("Flying", "potionWhite", cpe.flight, 300, 0);
		fluidFlyingLong = createPotion("FlyingLong", "potionWhite", cpe.flight, 600, 0);
		fluidWither = createPotion("Wither", "potionBlack", Potion.wither, 400, 0);
		fluidWitherII = createPotion("WitherII", "potionBlack", Potion.wither, 200, 1);
		fluidWitherLong = createPotion("WitherLong", "potionBlack", Potion.wither, 800, 0);

		createSpecialPotion("Ghast", Potion.confusion, 400, 0);

		fluidAntidote = createPotion("Antidote", "potionDarkPurple", cpe.immunity, 600, 0);
		fluidAntidoteII = createPotion("AntidoteII", "potionDarkPurple", cpe.immunity, 300, 1);
		fluidAntidoteLong = createPotion("AntidoteLong", "potionDarkPurple", cpe.immunity, 1200, 0);
		fluidBoom = createPotion("Boom", "potionDarkGreen", cpe.creeper, 160, 0);
		fluidBoomII = createPotion("BoomII", "potionDarkGreen", cpe.creeper, 80, 1);
		fluidBoomLong = createPotion("BoomLong", "potionDarkGreen", cpe.creeper, 320, 0);

		brewing = new MetaTileSpecialRenderer(getBlockId("brewery"), Material.iron, "RedGear.Brewcraft.Brewery",
				new RenderItemBrewery(), new TileRendererBrewery());

		brewing.setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep);

		brewery = brewing.addMetaBlock(new SubTileMachine("Brewery", breweryTexture, TileEntityBrewery.class,
				CoreGuiHandler.addGuiMap("brewery", "Brewery")));

		fluidAwkward = createVanillaPotion("Awkward", "potionBlue", 16, 0);
		fluidVision = createVanillaPotion("Vision", "potionDarkBlue", 8198, 16390);
		fluidVisionLong = createVanillaPotion("VisionLong", "potionDarkBlue", 8262, 16454);
		fluidInvisible = createVanillaPotion("Invisible", "potionGrey", 8206, 16398);
		fluidInvisibleLong = createVanillaPotion("InvisibleLong", "potionGrey", 8270, 16462);
		fluidRegen = createVanillaPotion("Regen", "potionPink", 8193, 16385);
		fluidRegenLong = createVanillaPotion("RegenLong", "potionPink", 8257, 16449);
		fluidFast = createVanillaPotion("Fast", "potionLightBlue", 8194, 16386);
		fluidFastLong = createVanillaPotion("FastLong", "potionLightBlue", 8290, 16450);
		fluidFastII = createVanillaPotion("FastII", "potionLightBlue", 8258, 16418);
		fluidWeakness = createVanillaPotion("Weakness", "potionPurple", 8200, 16456);
		fluidStrength = createVanillaPotion("Strength", "potionMagenta", 8201, 16393);
		fluidStrengthLong = createVanillaPotion("StrengthLong", "potionMagenta", 8265, 16457);
		fluidStrengthII = createVanillaPotion("StrengthII", "potionMagenta", 8297, 16425);
		fluidFireResist = createVanillaPotion("FireResist", "potionLightPink", 8195, 16387);
		fluidFireResistLong = createVanillaPotion("FireResistLong", "potionLightPink", 8259, 16451);
		fluidSlowness = createVanillaPotion("Slowness", "potionPurple", 8202, 16394);
		fluidSlownessLong = createVanillaPotion("SlownessLong", "potionPurple", 8266, 16458);
		fluidPoison = createVanillaPotion("Poison", "potionGreen", 8196, 16388);
		fluidPoisonII = createVanillaPotion("PoisonII", "potionGreen", 8260, 16420);
		fluidPoisonLong = createVanillaPotion("PoisonLong", "potionGreen", 8228, 16452);
		fluidHarm = createVanillaPotion("Harm", "potionDarkPurple", 8204, 16396);
		fluidHarmII = createVanillaPotion("HarmII", "potionDarkPurple", 8236, 16428);
		fluidHealing = createVanillaPotion("Healing", "potionRed", 8196, 16389);
		fluidHealingII = createVanillaPotion("HealingII", "potionRed", 8229, 16421);

		brewery.getBlock().setCreativeTab(CreativeTabs.tabBrewing);
		ingredients.setCreativeTab(CreativeTabs.tabBrewing);

	}

	@Override
	protected void Init(FMLInitializationEvent event) {
		if (getBoolean("Global", "Mod Compatibility", "Toggle Mod Compatibility", true))
			compatibility();
		recipes();

		if (getBoolean("Dungeon Loot", "Golden Feather Dungeon Loot", "Toggle Golden Feather as Dungeon Loot", true))
			CoreDungeonLoot.addLootToDungeons(goldenfeather.getStack(), LootRarity.RARE);
		if (getBoolean("Global", "Golden Feather Villager Trades", "Toggle Golden Feather Villager Trades", true))
			CoreTradeHandler.addTradeRecipe(2, new ItemStack(Item.emerald, 7, 0), goldenfeather.getStack());
		if (getBoolean("Global", "Blessed Powder Villager Trades", "Toggle Blessed Powder Villager Trades", true))
			CoreTradeHandler.addTradeRecipe(2, new ItemStack(Item.emerald, 11, 0), holydust.getStack());

	}

	@Override
	protected void PostInit(FMLPostInitializationEvent event) {

	}

	private void compatibility() {

		soul = ModConfigHelper.get("miscItems", 10);
		dust = ModConfigHelper.get("miscItems", 11);
		crystal = ModConfigHelper.get("materials", 7);
		bone = ModConfigHelper.get("materials", 8);
		sulfur = ModConfigHelper.get("plantItem", 4);
		brain = ModConfigHelper.get("ItemResource", 5);
		goo = ModConfigHelper.get("ItemResource", 11);
		tendril = ModConfigHelper.get("ItemResource", 12);

		if (Mods.BiomesOPlenty.isIn()
				&& getBoolean("Mod Compatibility", "Biomes o' Plenty Compatibility",
						"Toggle Biomes o' Plenty Compatibility", true)) {

			FMLLog.log(Level.INFO, "[" + RedGear.BrewcraftName
					+ "] has found Biomes o' Plenty loaded, now running compatibility.");

			if (!(soul == null))
				registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), new FluidStack(fluidWither, 100), soul, 1,
						4);
			if (!(dust == null))
				registry.addRecipe(new FluidStack(fluidRegen, 100), new FluidStack(fluidHolyWater, 100), dust, 1, 4);
		}

		if (Mods.Thaum.isIn()
				&& getBoolean("Mod Compatibility", "Thaumcraft 4 Compatibility", "Toggle Thaumcraft 4 Compatibility",
						true)) {

			FMLLog.log(Level.INFO, "[" + RedGear.BrewcraftName
					+ "] has found Thaumcraft 4 loaded, now running compatibility.");

			if (!(brain == null)) {
				registry.addRecipe(new FluidStack(fluidVision, 100), new FluidStack(fluidInvisible, 100),
						ItemApi.getItem("ItemResource", 5), ITEM_CONSUMPTION_BASE, 4);
				registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidWeakness, 100),
						ItemApi.getItem("ItemResource", 5), ITEM_CONSUMPTION_BASE, 4);
				registry.addRecipe(new FluidStack(fluidStrength, 100), new FluidStack(fluidWeakness, 100),
						ItemApi.getItem("ItemResource", 5), ITEM_CONSUMPTION_BASE, 4);
				registry.addRecipe(new FluidStack(fluidFireResist, 100), new FluidStack(fluidSlowness, 100),
						ItemApi.getItem("ItemResource", 5), ITEM_CONSUMPTION_BASE, 4);
			}

			if (!(goo == null))
				registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidPoison, 100), goo,
						ITEM_CONSUMPTION_BASE + 10, 4);
			if (!(tendril == null))
				registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidPoison, 100), tendril,
						ITEM_CONSUMPTION_BASE + 10, 4);

			if (getBoolean("Compatibility", "Thaumcraft 4 Aspects on Items and Blocks",
					"Toggle Aspects from Thaumcraft 4", true)) {
				ThaumcraftApi.registerObjectTag(brewery.id, 0,
						new AspectList().add(Aspect.MECHANISM, 11).add(Aspect.METAL, 7));
				ThaumcraftApi.registerObjectTag(ingredients.itemID, 0,
						new AspectList().add(Aspect.LIFE, 3).add(Aspect.LIGHT, 2).add(Aspect.MAGIC, 2));
				ThaumcraftApi.registerObjectTag(ingredients.itemID, 1,
						new AspectList().add(Aspect.GREED, 3).add(Aspect.FLIGHT, 1));
				ThaumcraftApi.registerObjectTag(ingredients.itemID, 2,
						new AspectList().add(Aspect.DEATH, 2).add(Aspect.BEAST, 1));
				ThaumcraftApi.registerObjectTag(potions.itemID, 0,
						new AspectList().add(Aspect.MAGIC, 3).add(Aspect.FIRE, 2));
				ThaumcraftApi.registerObjectTag(potions.itemID, 1,
						new AspectList().add(Aspect.LIGHT, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(potions.itemID, 2,
						new AspectList().add(Aspect.LIGHT, 13).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(potions.itemID, 3,
						new AspectList().add(Aspect.LIGHT, 8).add(Aspect.MAGIC, 5));
				ThaumcraftApi.registerObjectTag(potions.itemID, 4,
						new AspectList().add(Aspect.FLIGHT, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(potions.itemID, 5,
						new AspectList().add(Aspect.FLIGHT, 8).add(Aspect.MAGIC, 5));
				ThaumcraftApi.registerObjectTag(potions.itemID, 6,
						new AspectList().add(Aspect.DEATH, 8).add(Aspect.DARKNESS, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(potions.itemID, 7,
						new AspectList().add(Aspect.DEATH, 13).add(Aspect.DARKNESS, 13).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(potions.itemID, 8,
						new AspectList().add(Aspect.DEATH, 8).add(Aspect.DARKNESS, 8).add(Aspect.MAGIC, 7));
				ThaumcraftApi.registerObjectTag(potions.itemID, 9,
						new AspectList().add(Aspect.BEAST, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(potions.itemID, 10,
						new AspectList().add(Aspect.LIFE, 8).add(Aspect.HEAL, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(potions.itemID, 11,
						new AspectList().add(Aspect.LIFE, 13).add(Aspect.HEAL, 13).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(potions.itemID, 12,
						new AspectList().add(Aspect.LIFE, 8).add(Aspect.HEAL, 8).add(Aspect.MAGIC, 5));
				ThaumcraftApi.registerObjectTag(potions.itemID, 13,
						new AspectList().add(Aspect.WEAPON, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(potions.itemID, 14,
						new AspectList().add(Aspect.WEAPON, 13).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(potions.itemID, 15,
						new AspectList().add(Aspect.WEAPON, 8).add(Aspect.MAGIC, 5));
			}
		}

		if (Mods.TConstruct.isIn()
				&& getBoolean("Mod Compatibility", "Tinkers' Construct Compatibility",
						"Toggle Tinkers' Construct Compatibility", true)) {

			FMLLog.log(Level.INFO, "[" + RedGear.BrewcraftName
					+ "] has found Tinkers' Construct loaded, now running compatibility.");

			if (!(crystal == null))
				registry.addRecipe(new FluidStack(FluidRegistry.WATER, 100), new FluidStack(FluidRegistry.LAVA, 100),
						crystal, ITEM_CONSUMPTION_BASE + 10, DEFAULT_TIME);

			if (!(bone == null))
				registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), new FluidStack(fluidWither, 100), bone,
						ITEM_CONSUMPTION_BASE + 30, DEFAULT_TIME);
		}

		if (Mods.Natura.isIn()
				&& getBoolean("Mod Compatibility", "Natura Compatibility", "Toggle Natura Compatibility", true)) {

			FMLLog.log(Level.INFO, "[" + RedGear.BrewcraftName
					+ "] has found Natura loaded, now running compatibility.");

			if (!(sulfur == null))
				registry.addRecipe(new FluidStack(fluidWither, 100), new FluidStack(fluidBoom, 100), sulfur,
						ITEM_CONSUMPTION_BASE + 30, DEFAULT_TIME + 5);
		}

	}

	private void recipes() {

		registry.addRecipe(new FluidStack(fluidRegen, 100), new FluidStack(fluidHolyWater, 100), holydust,
				ITEM_CONSUMPTION_BASE + 10, DEFAULT_TIME);
		registry.addRecipe(new FluidStack(fluidHolyWater, 100), new FluidStack(fluidHolyWaterII, 100), new ItemStack(
				Item.glowstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
		registry.addRecipe(new FluidStack(fluidHolyWater, 100), new FluidStack(fluidHolyWaterLong, 100), new ItemStack(
				Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
		registry.addRecipe(new FluidStack(FluidRegistry.WATER, 100), new FluidStack(fluidFlying, 100), goldenfeather,
				ITEM_CONSUMPTION_BASE, DEFAULT_TIME + 1);
		registry.addRecipe(new FluidStack(fluidFlying, 100), new FluidStack(fluidFlyingLong, 100), new ItemStack(
				Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
		registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), new FluidStack(fluidWither, 100), charredbone,
				ITEM_CONSUMPTION_BASE - 10, 4);
		registry.addRecipe(new FluidStack(fluidWither, 100), new FluidStack(fluidWitherII, 100), new ItemStack(
				Item.glowstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
		registry.addRecipe(new FluidStack(fluidWither, 100), new FluidStack(fluidWitherLong, 100), new ItemStack(
				Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
		registry.addRecipe(new FluidStack(fluidHealing, 100), new FluidStack(fluidAntidote, 100), new ItemStack(
				Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME + 2);
		registry.addRecipe(new FluidStack(fluidAntidote, 100), new FluidStack(fluidAntidoteII, 100), new ItemStack(
				Item.glowstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME + 2);
		registry.addRecipe(new FluidStack(fluidWither, 100), new FluidStack(fluidBoom, 100), new ItemStack(
				Item.gunpowder), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
		registry.addRecipe(new FluidStack(fluidBoom, 100), new FluidStack(fluidBoomII, 100), new ItemStack(
				Item.glowstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
		registry.addRecipe(new FluidStack(fluidBoom, 100), new FluidStack(fluidBoomLong, 100), new ItemStack(
				Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);

		if (getBoolean("Recipes", "Vanilla Potions are Brewable", "Toggle Vanilla Potion Brewing Recipes", true)) {
			registry.addRecipe(new FluidStack(FluidRegistry.WATER, 100), new FluidStack(fluidAwkward, 100),
					new ItemStack(Item.netherStalkSeeds), ITEM_CONSUMPTION_BASE, 4);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidVision, 100), new ItemStack(
					Item.goldenCarrot), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidVision, 100), new FluidStack(fluidVisionLong, 100), new ItemStack(
					Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidVision, 100), new FluidStack(fluidInvisible, 100), new ItemStack(
					Item.fermentedSpiderEye), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidInvisible, 100), new FluidStack(fluidInvisibleLong, 100),
					new ItemStack(Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidRegen, 100), new ItemStack(
					Item.ghastTear), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidRegen, 100), new FluidStack(fluidRegenLong, 100), new ItemStack(
					Item.glowstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidFast, 100), new ItemStack(
					Item.sugar), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidFast, 100), new FluidStack(fluidFastLong, 100), new ItemStack(
					Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidFast, 100), new FluidStack(fluidFastII, 100), new ItemStack(
					Item.glowstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidWeakness, 100), new ItemStack(
					Item.fermentedSpiderEye), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidStrength, 100), new FluidStack(fluidWeakness, 100), new ItemStack(
					Item.fermentedSpiderEye), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidStrength, 100), new ItemStack(
					Item.blazePowder), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidStrength, 100), new FluidStack(fluidStrengthLong, 100),
					new ItemStack(Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidStrength, 100), new FluidStack(fluidStrengthII, 100), new ItemStack(
					Item.glowstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidFireResist, 100), new ItemStack(
					Item.magmaCream), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidFireResist, 100), new FluidStack(fluidFireResistLong, 100),
					new ItemStack(Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidFireResist, 100), new FluidStack(fluidSlowness, 100), new ItemStack(
					Item.fermentedSpiderEye), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidSlowness, 100), new FluidStack(fluidSlownessLong, 100),
					new ItemStack(Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidPoison, 100), new ItemStack(
					Item.spiderEye), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidPoison, 100), new FluidStack(fluidPoisonLong, 100), new ItemStack(
					Item.redstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidPoison, 100), new FluidStack(fluidHarm, 100), new ItemStack(
					Item.fermentedSpiderEye), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidHarm, 100), new FluidStack(fluidHarmII, 100), new ItemStack(
					Item.glowstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidHealing, 100), new ItemStack(
					Item.speckledMelon), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidHealing, 100), new FluidStack(fluidHealingII, 100), new ItemStack(
					Item.glowstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
			registry.addRecipe(new FluidStack(fluidPoison, 100), new FluidStack(fluidPoisonII, 100), new ItemStack(
					Item.glowstone), ITEM_CONSUMPTION_BASE, DEFAULT_TIME);
		}

		if (getBoolean("Recipes", "Golden Feather Recipe", "Toggle Golden Feather Recipe", true))
			GameRegistry.addShapedRecipe(goldenfeather.getStack(),
					new Object[] {"!!!", "!@!", "!!!", Character.valueOf('!'), Item.goldNugget, Character.valueOf('@'),
							Item.feather });

		if (getBoolean("Recipes", "Splash Bottle Recipe", "Toggle Splash Bottle Recipe", true))
			GameRegistry.addShapedRecipe(splashBottle.getStack(3),
					new Object[] {" @!", "@ @", " @ ", Character.valueOf('!'), Item.gunpowder, Character.valueOf('@'),
							Block.glass });

		boolean ironOverride = false;

		if (getBoolean("Recipes", "Lead Brewery", "Toggle crafting the Brewery with Lead if available"))
			ironOverride = breweryRecipe("ingotLead");

		if (getBoolean("Recipes", "Brass Brewery", "Toggle crafting the Brewery with Brass if available"))
			ironOverride = ironOverride || breweryRecipe("ingotBrass");

		if (!(ironOverride && !getBoolean("Recipes", "Iron Brewery",
				"Toggle crafting the Brewery with Iron. (Can only be disabled if Lead or Brass is available)")))
			breweryRecipe("ingotIron"); //Dat boolean expression!

		if (getBoolean("Recipes", "Charred Bone Recipe", "Toggle Charred Bone Smelting Recipe", true))
			GameRegistry.addSmelting(Item.bone.itemID, Brewcraft.charredbone.getStack(), 0.1F);

	}

	private boolean breweryRecipe(String ingot) {
		ItemStack metal = ItemStackUtil.getOreWithName(ingot);

		if (metal != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(brewery.getStack(), new Object[] {"! !", "!@!", "#!#",
					Character.valueOf('!'), ingot, Character.valueOf('@'), Item.brewingStand, Character.valueOf('#'),
					Item.cauldron }));
			return true;
		} else
			return false;
	}

	/**
	 * Helper method for creating potions
	 * 
	 * @param name The in-code name for the potion that will have 'bottle',
	 * 'splash' and 'potion' added to it.
	 * @param iconName
	 * @param effect
	 */
	private Fluid createPotion(String name, String iconName, Potion effect, int duration, int strength) {
		SimpleItem bottle = potions.addMetaItem(new SubItemPotion("bottle" + name, false, effect, duration, strength));
		SimpleItem splash = potions.addMetaItem(new SubItemPotion("splash" + name, true, effect, duration, strength));
		Fluid potion = FluidUtil.createFluid("potion" + name, iconName);

		FluidContainerRegistry.registerFluidContainer(potion, bottle.getStack(), emptyBottle.getStack());
		FluidContainerRegistry.registerFluidContainer(potion, splash.getStack(), splashBottle.getStack());

		return potion;
	}

	private void createSpecialPotion(String name, Potion effect, int duration, int strength) {
		potions.addMetaItem(new SubItemPotion("bottle" + name, false, effect, duration, strength));
		potions.addMetaItem(new SubItemPotion("splash" + name, true, effect, duration, strength));
	}

	/**
	 * Helper method for vanilla potions.
	 * 
	 * @param name - The suffix for potion names.
	 * @param iconName
	 * @param metaBottle - The meta of the corresponding vanilla potion.
	 * @param metaSplash - The meta of the corresponding vanilla splash potion.
	 */
	private Fluid createVanillaPotion(String name, String iconName, int metaBottle, int metaSplash) {
		Fluid potion = FluidUtil.createFluid("potion" + name, iconName);
		FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Item.potion, 1, metaBottle),
				emptyBottle.getStack());
		if (!(metaSplash == 0))
			FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Item.potion, 1, metaSplash),
					splashBottle.getStack());

		return potion;
	}
	
	private void expandPotionList()
    {
            Potion[] potionTypes = null;
            
            if (Potion.potionTypes.length != getInt("Potion List Expansion", "Potion List Extension Size",
    				"Will only do something if expanding the potion list is set to true.", 3200))
            {
                    for (Field f : Potion.class.getDeclaredFields())
                    {
                            f.setAccessible(true);
                            try
                            {
                                    if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a"))
                                    {
                                            Field modfield = Field.class.getDeclaredField("modifiers");
                                            modfield.setAccessible(true);
                                            modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                                            
                                            potionTypes = (Potion[]) f.get(null);
                                            final Potion[] newPotionTypes = new Potion[getInt("Potion List Expansion", "Potion List Extension Size",
                                    				"Will only do something if expanding the potion list is set to true.", 3200)];
                                            for (int i = 0; i < newPotionTypes.length; i++)
                                            {
                                                    if (i < Potion.potionTypes.length)
                                                            newPotionTypes[i] = Potion.potionTypes[i];
                                                    else
                                                            newPotionTypes[i] = null;
                                            }
                                            f.set(null, newPotionTypes);
                                    }
                            }
                            catch (Exception e)
                            {
                                    inst.logDebug(e);
                            }
                    }
            }
    }

	@Override
	@Mod.EventHandler
	public void PreInitialization(FMLPreInitializationEvent event) {
		super.PreInitialization(event);
	}

	@Override
	@Mod.EventHandler
	public void Initialization(FMLInitializationEvent event) {
		super.Initialization(event);
	}

	@Override
	@Mod.EventHandler
	public void PostInitialization(FMLPostInitializationEvent event) {
		super.PostInitialization(event);
	}
}
