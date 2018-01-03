package redgear.brewcraft.plugins.compat;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import redgear.brewcraft.plugins.item.ItemPlugin;

public class CustomTableLoot {
	@SubscribeEvent
	public void OnLootTableLoad(LootTableLoadEvent event) {
		if(event.getName().equals(new ResourceLocation("minecraft", "chests/simple_dungeon"))) {
			event.getTable().getPool("pool1").addEntry(new LootEntryItem(ItemPlugin.goldenFeather, 10, 0, new LootFunction[0], new LootCondition[0], ItemPlugin.goldenFeather.getUnlocalizedName()));
		}
	}
}
