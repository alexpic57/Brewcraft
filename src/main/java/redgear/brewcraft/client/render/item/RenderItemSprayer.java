package redgear.brewcraft.client.render.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import redgear.brewcraft.blocks.sprayer.TileEntitySprayer;
import redgear.core.render.SimpleBlockRenderingHandler;

public class RenderItemSprayer extends SimpleBlockRenderingHandler{
	
	public RenderItemSprayer(int renderId) {
		super(renderId);
	}

	TileEntitySprayer blank = new TileEntitySprayer();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		blank.setDirection(ForgeDirection.UP);
		TileEntityRendererDispatcher.instance.renderTileEntityAt(blank, 0.0D, 0.0D, 0.0D, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int i) {
		return true;
	}

}
