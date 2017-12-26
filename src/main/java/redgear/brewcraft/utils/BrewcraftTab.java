package redgear.brewcraft.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import redgear.brewcraft.core.Brewcraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//ToDo: set the icon item on each tab
public class BrewcraftTab extends CreativeTabs {

	public static final CreativeTabs BREWCRAFT = new BrewcraftTab("misc"); //brewery icon
	public static final CreativeTabs VIALS = new BrewcraftTab("vials"); //vial icon
	public static final CreativeTabs POTIONS = new BrewcraftTab("potions"); //potion icon
	public static final CreativeTabs BIG_POTIONS = new BrewcraftTab("big_potions"); //big potion icon

	private boolean background;
	private ItemStack tabIconItem;

	public BrewcraftTab(String label) {
		super(Brewcraft.MOD_ID + "." + label);
		this.background = Brewcraft.configuration.get("General", "Toggle Unconventional Creative Tab Overlays", true).getBoolean();
		this.tabIconItem = ItemStack.EMPTY;
	}

	@Override
	public ItemStack getTabIconItem() {
		return tabIconItem;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getBackgroundImageName() {
		return background ? "background.png" : "items.png";
	}

	public BrewcraftTab setTabIconItem(ItemStack tabIconItem) {
		this.tabIconItem = tabIconItem;
		return this;
	}
}
