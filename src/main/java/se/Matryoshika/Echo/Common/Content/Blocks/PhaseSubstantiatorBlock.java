package se.Matryoshika.Echo.Common.Content.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.Tile.TilePhaseSubstantiator;

public class PhaseSubstantiatorBlock extends Block{

	public PhaseSubstantiatorBlock() {
		super(Material.IRON);
		setRegistryName(Echo.MODID, "phase_substantiator");
		setUnlocalizedName(getRegistryName().toString());
		setCreativeTab(Echo.TAB);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TilePhaseSubstantiator();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

}
