package se.Matryoshika.Echo.Common.Content.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.Tile.TileLaniaiteFabricator;

public class LaniaiteFabricator extends Block{
	
	
	public static final PropertyDirection FACING = BlockDirectional.FACING;

	public LaniaiteFabricator() {
		super(Material.IRON);
		setRegistryName(Echo.MODID, "laniaite_fabricator");
		setUnlocalizedName(getRegistryName().toString());
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));
		this.setHardness(20);
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
		return this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }
	
	@Override
	public int getMetaFromState(IBlockState state){
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
	
	@Override
	protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileLaniaiteFabricator();
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
