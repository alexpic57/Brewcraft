package redgear.brewcraft.village;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import redgear.brewcraft.core.Brewcraft;

public class ComponentWitchHut extends StructureVillagePieces.House1 {
	public boolean hasCauldron;
	public boolean hasTable;
	public boolean hasFlowerPot;
	public boolean hasFences;
	public boolean isInDesert;
	public boolean hasGlass;
	public boolean hasWitch;

	public ComponentWitchHut() {
	}

	public ComponentWitchHut(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox p_i45571_4_, EnumFacing facing) {
		super(start, type, rand, p_i45571_4_, facing);
		this.setCoordBaseMode(facing);
		this.boundingBox = p_i45571_4_;
		this.hasCauldron = rand.nextBoolean();
		this.hasTable = rand.nextBoolean();
		this.hasFlowerPot = rand.nextBoolean();
		this.hasFences = rand.nextBoolean();
		this.isInDesert = start.biome == Biomes.DESERT || start.biome == Biomes.DESERT_HILLS; // || start.biome == Biomes.MUTATED_DESERT ?
		this.hasGlass = rand.nextBoolean() && !this.hasFlowerPot;
		this.hasWitch = rand.nextBoolean();
	}

	public static ComponentWitchHut buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int p1, int p2, int p3, EnumFacing facing, int p5) {
		StructureBoundingBox sbb = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 7, 5, 9, facing);
		return canVillageGoDeeper(sbb) && StructureComponent.findIntersecting(pieces, sbb) == null ? new ComponentWitchHut(startPiece, p5, random, sbb, facing) : null;
	}

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {

		if (this.averageGroundLvl < 0) {
			this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

			if (this.averageGroundLvl < 0) {
				return true;
			}

			this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4, 0);
		}

		//The walls, floor, posts that hold it upright.
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 5, 1, 7, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 2, 5, 4, 7, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 4, 1, 0, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 2, 3, 3, 2, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 3, 1, 3, 6, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 3, 5, 3, 6, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 7, 4, 3, 7, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 2, 1, 3, 2, Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 2, 5, 3, 2, Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 7, 1, 3, 7, Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 7, 5, 3, 7, Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState(), false);

		//Rails on front of house and in the windows.
		if (this.hasFences) {
			this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 2, 3, 2, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 3, 3, 7, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 1, 2, 1, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 5, 2, 1, structureBoundingBoxIn);
		}

		if (this.hasFlowerPot)
			this.setBlockState(worldIn, Blocks.FLOWER_POT.getDefaultState().withProperty(BlockFlowerPot.CONTENTS, BlockFlowerPot.EnumFlowerType.MUSHROOM_RED), 1, 3, 5, structureBoundingBoxIn);

		if (this.hasGlass) {
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 3, 2, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 3, 7, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 1, 3, 4, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 1, 3, 5, structureBoundingBoxIn);
		}

		//Creating holes for the windows.
		if (!this.hasGlass) {
			this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 3, 4, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
		}

		//Miscellaneous things that are randomly included/excluded.
		if (this.hasTable)
			this.setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 3, 2, 6, structureBoundingBoxIn);

		if (this.hasCauldron)
			this.setBlockState(worldIn, Blocks.CAULDRON.getDefaultState(), 4, 2, 6, structureBoundingBoxIn);

		if (!this.isInDesert) {
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 6, 4, 1, Blocks.WOODEN_SLAB.getDefaultState(), Blocks.WOODEN_SLAB.getDefaultState(), false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 2, 0, 4, 7, Blocks.WOODEN_SLAB.getDefaultState(), Blocks.WOODEN_SLAB.getDefaultState(), false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 4, 2, 6, 4, 7, Blocks.WOODEN_SLAB.getDefaultState(), Blocks.WOODEN_SLAB.getDefaultState(), false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 8, 6, 4, 8, Blocks.WOODEN_SLAB.getDefaultState(), Blocks.WOODEN_SLAB.getDefaultState(), false);
		} else {
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 6, 4, 1, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 2, 0, 4, 7, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 4, 2, 6, 4, 7, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 8, 6, 4, 8, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), false);
		}

		int i1;
		int j1;

		for (i1 = 2; i1 <= 7; i1 += 5) {
			for (j1 = 1; j1 <= 5; j1 += 4) {
				this.replaceAirAndLiquidDownwards(worldIn, Blocks.LOG.getDefaultState(), j1, -1, i1, structureBoundingBoxIn);
			}
		}

		this.spawnVillagers(worldIn, structureBoundingBoxIn, 3, 1, 3, 1);
		return true;
	}

	@Override
	protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession) {
		return this.hasWitch ? VillagerRegistry.getId(ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(Brewcraft.MOD_ID + ":witch"))) : VillagerRegistry.getId(ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(Brewcraft.MOD_ID + ":warlock")));
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		super.writeStructureToNBT(tagCompound);
		tagCompound.setBoolean("Cauldron", this.hasCauldron);
		tagCompound.setBoolean("Crafting Table", this.hasTable);
		tagCompound.setBoolean("Flower Pots", this.hasFlowerPot);
		tagCompound.setBoolean("Fences", this.hasFences);
		tagCompound.setBoolean("Desert", this.isInDesert);
		tagCompound.setBoolean("Glass", this.hasGlass);
		tagCompound.setBoolean("Witch", this.hasWitch);
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
		super.readStructureFromNBT(tagCompound, p_143011_2_);
		this.hasCauldron = tagCompound.getBoolean("Cauldron");
		this.hasTable = tagCompound.getBoolean("Crafting Table");
		this.hasFlowerPot = tagCompound.getBoolean("Flower Pots");
		this.hasFences = tagCompound.getBoolean("Fences");
		this.isInDesert = tagCompound.getBoolean("Desert");
		this.hasGlass = tagCompound.getBoolean("Glass");
		this.hasWitch = tagCompound.getBoolean("Witch");
	}
}
