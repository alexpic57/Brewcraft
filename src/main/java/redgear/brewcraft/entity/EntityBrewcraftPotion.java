package redgear.brewcraft.entity;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import redgear.brewcraft.packet.ParticleHandler;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;

public class EntityBrewcraftPotion extends EntityPotion {

	private final int stackIndex = 10;

	public EntityBrewcraftPotion(World par1World) {
		super(par1World);
	}

	public EntityBrewcraftPotion(World world, double x, double y, double z, ItemStack potion) {
		super(world, x, y, z, potion);
		setPotion(potion);
	}

	public EntityBrewcraftPotion(World world, EntityLivingBase thrower, ItemStack potion) {
		super(world, thrower, potion);
		setPotion(potion);
	}

	public void setPotion(ItemStack potion) {
		getDataWatcher().updateObject(stackIndex, potion);
		getDataWatcher().setObjectWatched(stackIndex);
	}

	@Override
	protected void entityInit() {
		getDataWatcher().addObjectByDataType(10, 5);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		SubItemPotion potion = getPotionItem();
		
		if (!worldObj.isRemote) {

			AxisAlignedBB range = boundingBox.expand(4.0D, 2.0D, 4.0D);
			List<EntityLivingBase> entiesList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, range);

			for (EntityLivingBase entity : entiesList) { //Use a for each whenever possible
				double distance = getDistanceSqToEntity(entity);

				if (distance < 16.0D)
					potion.effect(entity);

			}
			ParticleHandler.send(worldObj, posX, posY, posZ, potion.getEffect(), ParticleHandler.POTION);
			worldObj.playSoundEffect(posX, posY, posZ, "game.potion.smash", 1.0F,
					worldObj.rand.nextFloat() * 0.1F + 0.9F);
			setDead();
		}
	}

	public ItemStack getPotionStack() {
		return getDataWatcher().getWatchableObjectItemStack(stackIndex);
	}

	private SubItemPotion getPotionItem() {
		ItemStack stack = getPotionStack();
		if (stack == null)
			return null;

		Item item = stack.getItem();
		if (item instanceof MetaItemPotion) {
			MetaItemPotion metaItem = (MetaItemPotion) item;
			return metaItem.getMetaItem(stack.getItemDamage());
		} else
			return null; //Should never happen. 
	}
}
