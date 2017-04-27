package se.Matryoshika.Echo.Common.Content.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.Tile.TileLaniaiteFabricator;

public class LaniaiteFabricator extends Block{

	public LaniaiteFabricator() {
		super(Material.IRON);
		setRegistryName(Echo.MODID, "laniaite_fabricator");
		setUnlocalizedName(getRegistryName().toString());
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileLaniaiteFabricator();
	}

}
