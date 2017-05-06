package se.Matryoshika.Echo.Common.Content.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Tile.TileTemporalDilation;

public class BlockGuillotine extends Block {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final AxisAlignedBB BOX = new AxisAlignedBB(0.3D, 0.01D, 0.3D, 0.7D, 0.45D, 0.7D);

	public BlockGuillotine() {
		super(Material.WOOD);
		setRegistryName(Echo.MODID, "guillotine");
		setUnlocalizedName(getRegistryName().toString());
		setHardness(2f);

		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOX;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, BlockPos pos, net.minecraft.client.particle.ParticleManager manager) {

		if (world.getBlockState(pos).getBlock() == this) {
			int i = 4;
			net.minecraft.client.particle.ParticleDigging.Factory factory = new net.minecraft.client.particle.ParticleDigging.Factory();
			for (int j = 0; j < i; ++j) {
				for (int k = 0; k < i; ++k) {
					for (int l = 0; l < i; ++l) {
						double d0 = pos.getX() + (j + 0.5D) / i;
						double d1 = pos.getY() + (k + 0.5D) / i;
						double d2 = pos.getZ() + (l + 0.5D) / i;
						manager.addEffect(factory.getEntityFX(-1, world, d0, d1, d2, d0 - pos.getX() - 0.5D, d1 - pos.getY() - 0.5D, d2 - pos.getZ() - 0.5D, Block.getStateId(Blocks.PLANKS.getDefaultState())));
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, ItemStack stack){
        
		if(world.getTileEntity(pos.down()) != null && world.getTileEntity(pos.down()) instanceof TileTemporalDilation)
			((TileTemporalDilation)world.getTileEntity(pos.down())).hasUpdated = true;
		
        return onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

}
