package redgear.brewcraft.plugins.world;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.item.ItemPlugin;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.village.ComponentWitchHut;
import redgear.brewcraft.village.VillageWitchHutHandler;

import java.util.Random;

//ToDO: check if trades levels are correct
public class ModVillageRegistry {
	public static void init() {
		Random random = new Random();
		boolean powderTrade = Brewcraft.configuration.get("General", "Blessed Powder Priest Trade", true).getBoolean();

		VillagerRegistry.VillagerProfession villagerPriest = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:priest"));

		if (powderTrade) {
			villagerPriest.getCareer(0).addTrade(
					1,
					new ItemForItem(new ItemStack(ItemPlugin.holyDust, random.nextInt(1) + 2), new ItemStack(Items.EMERALD, random.nextInt(4) + 1)),
					new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(5) + 3), new ItemStack(ItemPlugin.holyDust, random.nextInt(3) + 1))
			);
		}

		villagerPriest.getCareer(0).addTrade(
				1,
				new TimeConditionalItemForItem(new ItemStack(Items.EMERALD, random.nextInt(5) + 4), new ItemStack(ItemPlugin.pureTear, random.nextInt(1) + 1), 13000)
		);

		ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer")).getCareer(0).addTrade(
				1,
				new ItemForItem(new ItemStack(ItemPlugin.heartSmall, random.nextInt(3) + 1), new ItemStack(Items.EMERALD, random.nextInt(5) + 3)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(5) + 1), new ItemStack(ItemPlugin.heartSmall, random.nextInt(3) + 1))
		);

		VillagerRegistry.VillagerProfession witch = new VillagerRegistry.VillagerProfession(Brewcraft.MOD_ID + ":witch", Brewcraft.MOD_ID + ":textures/entity/villager_witch.png", "minecraft:textures/entity/zombie_villager/zombie_priest.png");
		new VillagerRegistry.VillagerCareer(witch, "witch").addTrade(
				1,
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 1), new EntityVillager.PriceInfo(3, 7)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 10), new EntityVillager.PriceInfo(3, 7)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 20), new EntityVillager.PriceInfo(5, 9)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 30), new EntityVillager.PriceInfo(6, 10)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 36), new EntityVillager.PriceInfo(4, 8)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 46), new EntityVillager.PriceInfo(6, 10)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 52), new EntityVillager.PriceInfo(6, 10)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 62), new EntityVillager.PriceInfo(8, 12)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 72), new EntityVillager.PriceInfo(8, 12)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 82), new EntityVillager.PriceInfo(5, 9)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 92), new EntityVillager.PriceInfo(9, 13)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 102), new EntityVillager.PriceInfo(9, 13)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 112), new EntityVillager.PriceInfo(8, 12)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 118), new EntityVillager.PriceInfo(6, 10)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 130), new EntityVillager.PriceInfo(7, 11)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 140), new EntityVillager.PriceInfo(5, 9)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 146), new EntityVillager.PriceInfo(7, 11)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 152), new EntityVillager.PriceInfo(7, 11)),

				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8193), new EntityVillager.PriceInfo(5, 9)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16385), new EntityVillager.PriceInfo(5, 9)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8194), new EntityVillager.PriceInfo(4, 8)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16386), new EntityVillager.PriceInfo(4, 8)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8227), new EntityVillager.PriceInfo(3, 7)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16419), new EntityVillager.PriceInfo(3, 7)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8196), new EntityVillager.PriceInfo(5, 9)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16388), new EntityVillager.PriceInfo(5, 9)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8261), new EntityVillager.PriceInfo(4, 8)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16453), new EntityVillager.PriceInfo(4, 8)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8230), new EntityVillager.PriceInfo(6, 10)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16422), new EntityVillager.PriceInfo(6, 10)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8232), new EntityVillager.PriceInfo(2, 6)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16424), new EntityVillager.PriceInfo(4, 8)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8201), new EntityVillager.PriceInfo(5, 9)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16393), new EntityVillager.PriceInfo(5, 9)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8234), new EntityVillager.PriceInfo(2, 6)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16426), new EntityVillager.PriceInfo(4, 8)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8268), new EntityVillager.PriceInfo(2, 6)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16460), new EntityVillager.PriceInfo(5, 9)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8237), new EntityVillager.PriceInfo(4, 8)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16429), new EntityVillager.PriceInfo(4, 8)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 8238), new EntityVillager.PriceInfo(4, 8)),
				new EntityVillager.ListItemForEmeralds(new ItemStack(Items.POTIONITEM, 1, 16430), new EntityVillager.PriceInfo(4, 8))
		);

		VillagerRegistry.VillagerProfession warlock = new VillagerRegistry.VillagerProfession(Brewcraft.MOD_ID + ":warlock", Brewcraft.MOD_ID + ":textures/entity/villager_warlock.png", "minecraft:textures/entity/zombie_villager/zombie_priest.png");
		new VillagerRegistry.VillagerCareer(warlock, "warlock").addTrade(
				1,
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), new ItemStack(Items.SPIDER_EYE, random.nextInt(2) + 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(3) + 2), new ItemStack(Items.MAGMA_CREAM, 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 3), new ItemStack(Items.SPECKLED_MELON, 2)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 4), new ItemStack(Items.BLAZE_POWDER, random.nextInt(2) + 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(3) + 1), new ItemStack(Items.SUGAR, random.nextInt(2) + 3)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(3) + 4), new ItemStack(Items.GHAST_TEAR, random.nextInt(2) + 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 3), new ItemStack(Items.FERMENTED_SPIDER_EYE, random.nextInt(2) + 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 4), new ItemStack(Items.GOLDEN_CARROT, random.nextInt(2) + 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 3), new ItemStack(Items.REDSTONE, random.nextInt(3) + 4)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 3), new ItemStack(Items.GLOWSTONE_DUST, random.nextInt(3) + 4)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(5) + 3), new ItemStack(Items.NETHER_WART, random.nextInt(2) + 2)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 2), new ItemStack(ItemPlugin.goldenFeather, random.nextInt(2) + 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(3) + 1), new ItemStack(ItemPlugin.charredBone, random.nextInt(4) + 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 2), new ItemStack(ItemPlugin.spiderFang, 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 4), new ItemStack(ItemPlugin.tiredSpores, random.nextInt(2) + 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 3), new ItemStack(ItemPlugin.remedySalve, random.nextInt(2) + 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 2), new ItemStack(ItemPlugin.splashBottle, 3)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), new ItemStack(ItemPlugin.emptyVial, 6)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 1), new ItemStack(ItemPlugin.splashVial, 6)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 6), new ItemStack(ItemPlugin.heartGold, 1)),
				new ItemForItem(new ItemStack(Items.EMERALD, random.nextInt(4) + 4), new ItemStack(ItemPlugin.steelScales, random.nextInt(2) + 3))
		);

		if (Brewcraft.configuration.get("General", "Witch Hut Village Generation", true).getBoolean()) {
			VillagerRegistry.instance().registerVillageCreationHandler(new VillageWitchHutHandler());
			MapGenStructureIO.registerStructureComponent(ComponentWitchHut.class, Brewcraft.MOD_ID + ":VillagerWitchHutStructure");
		}
	}

	private static class ItemForItem implements EntityVillager.ITradeList {
		public ItemStack buyerItem;
		public ItemStack sellerItem;

		public ItemForItem(ItemStack buyerItem, ItemStack sellerItem) {
			this.buyerItem = buyerItem;
			this.sellerItem = sellerItem;
		}

		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			recipeList.add(new MerchantRecipe(buyerItem, sellerItem));
		}
	}

	private static class TimeConditionalItemForItem extends ItemForItem {
		public long time;

		public TimeConditionalItemForItem(ItemStack buyerItem, ItemStack sellerItem, long time) {
			super(buyerItem, sellerItem);
			this.time = time;
		}

		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			if (merchant.getWorld().getWorldTime() > time) {
				recipeList.add(new MerchantRecipe(buyerItem, sellerItem));
			}
		}
	}
}
