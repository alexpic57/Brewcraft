package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.core.AchievementPlugin;
import redgear.core.util.SimpleItem;
import redgear.core.world.WorldLocation;

public class EffectEternalFlame extends PotionExtension {

	public EffectEternalFlame() {
		super("flame", true, 0xFF9933);
		setIconIndex(7, 0);
		setRegistryName(Brewcraft.MOD_ID, "flame");
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		living.setFire(2);

		//Maybe have the health bar catch on fire or turn 'molten' in the future?
		//Places a 3x3x1 square of fire around the entity.
		if (living.world.rand.nextInt(100) == 0) {
			BlockPos startPos = new BlockPos((int)living.posX - 1, (int)living.posY, (int)living.posZ - 1);

			for(int x = 0; x < 3; x++) {
				for(int z = 0; z < 3; z++) {
					BlockPos currentPos = startPos.add(x, 0, z);
					if(living.world.isAirBlock(currentPos)) {
						living.world.setBlockState(currentPos, Blocks.FIRE.getDefaultState());
					}
				}
			}
		}

		if (living instanceof EntityPlayer && AchievementPlugin.flame != null)
			((EntityPlayer) living).addStat(AchievementPlugin.flame, 1);
	}

}
