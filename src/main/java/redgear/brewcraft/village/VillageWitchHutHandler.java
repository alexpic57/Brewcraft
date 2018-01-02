package redgear.brewcraft.village;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class VillageWitchHutHandler implements IVillageCreationHandler {
	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i) {
		return new PieceWeight(ComponentWitchHut.class, 12, 1);
	}

	@Override
	public Class<?> getComponentClass() {
		return ComponentWitchHut.class;
	}

	@Override
	public StructureVillagePieces.Village buildComponent(PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int p1, int p2, int p3, EnumFacing facing, int p5) {
		return ComponentWitchHut.buildComponent(villagePiece, startPiece, pieces, random, p1, p2, p3, facing, p5);
	}
}
