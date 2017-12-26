package redgear.brewcraft.core;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Brewcraft.MOD_ID, name = "Brewcraft", version = "@ModVersion@")
public class Brewcraft {
	@Instance("redgear_brewcraft")
	public static Brewcraft INSTANCE;

	public static final String MOD_ID = "redgear_brewcraft";

	public static Logger logger;
	public static Configuration configuration;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		configuration = new Configuration(event.getSuggestedConfigurationFile());
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		configuration.save();
	}

	protected void PreInit(FMLPreInitializationEvent event) {
		/*

		addPlugin(new DamageSourcePlugin());
		addPlugin(new EffectPlugin());
		addPlugin(new ItemPlugin());
		addPlugin(new PotionPlugin());
		addPlugin(new MachinePlugin());
		addPlugin(new KegPlugin());
		addPlugin(new AchievementPlugin());
		addPlugin(new CraftingPlugin());
		addPlugin(new VillagePlugin());
		if(isClient())
			addPlugin(new BrewcraftClientProxy());

		addPlugin(new ForestryPlugin());
		addPlugin(new BuildcraftPlugin());
		addPlugin(new VanillaPlugin());

		EntityRegistry.registerModEntity(EntityBrewcraftPotion.class, "Brewcraft:Potion",
				EntityRegistry.findGlobalUniqueEntityId(), RedGearCore.inst, 128, 10, true);
		*/
	}

	protected void Init(FMLInitializationEvent event) {
		/*
		CraftingHandler.register();
		DamageHandler.register();
		DropHandler.register();
		TradeHandler.register();
		ParticleHandler.register();
		SprayerDelayHandler.register();
		TipHandler.register();
		UpdateHandler.register();

		*/
	}
}
