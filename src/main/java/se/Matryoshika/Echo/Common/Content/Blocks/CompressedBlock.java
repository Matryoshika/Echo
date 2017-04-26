package se.Matryoshika.Echo.Common.Content.Blocks;

import com.google.gson.Gson;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class CompressedBlock extends Block {
	
	public CompressedBlock() {
		super(Material.ROCK);
		this.setRegistryName(Echo.MODID, "compressed_block");
		this.setUnlocalizedName(getRegistryName().toString());
	}

	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,ItemStack stack) {
		
		((TileMenger)world.getTileEntity(pos)).update(stack.getTagCompound().getByte(EchoConstants.NBT_TIER), NBTUtil.func_190008_d(stack.getTagCompound().getCompoundTag(EchoConstants.NBT_BLOCKSTATE)));
		((TileMenger)world.getTileEntity(pos)).markDirty();
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileMenger();
	}

	public CompressedBlock setTexture(IBlockState state) {
		// Echo.proxy.createModels(state.getBlock().getRegistryName().getResourcePath(),
		// tier, new ResourceLocation("minecraft", "blocks/emerald_block"));
		return this;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return true;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,EntityPlayer player) {
		return getPickBlock(super.getPickBlock(state, target, world, pos, player),(TileMenger) world.getTileEntity(pos), world, pos, state);
	}

	public ItemStack getPickBlock(ItemStack stack, TileMenger te, World world, BlockPos pos, IBlockState state) {
		if (te != null && te.getOriginalState() != null) {
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			IExtendedBlockState menger = (IExtendedBlockState) getExtendedState(state, world, pos);
			
			IBlockState copy = menger.getValue(IBS);
			stack.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE, NBTUtil.func_190009_a(new NBTTagCompound(), copy));
			stack.getTagCompound().setByte(EchoConstants.NBT_TIER, te.getTier());
		}
		else
			System.out.println("TE is null");

		return stack;
	}
	
	@Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos){
        if(state instanceof IExtendedBlockState && world.getTileEntity(pos) instanceof TileMenger) {
            return ((IExtendedBlockState)state).withProperty(IBS, ((TileMenger)world.getTileEntity(pos)).getOriginalState()).withProperty(BIT, ((TileMenger)world.getTileEntity(pos)).getTier());
        }
        return state;
    }
	
	@Override
    protected BlockStateContainer createBlockState(){
        return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[]{ IBS, BIT });
    }
	
	public BlockStateContainer getBSC(){
		return createBlockState();
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return true;
    }
	
	
	public static final IUnlistedProperty<IBlockState> IBS = new IUnlistedProperty<IBlockState>() {
        @Override
        public String getName() {
            return "echo:blockstate_property";
        }
        
        @Override
        public boolean isValid(IBlockState value) {
            return true;
        }
        
        @Override
        public Class<IBlockState> getType() {
            return IBlockState.class;
        }
        
        @Override
        public String valueToString(IBlockState value) {
            return new Gson().toJson(NBTUtil.func_190009_a(new NBTTagCompound(), value));
        }
	};
	
	public static final IUnlistedProperty<Byte> BIT = new IUnlistedProperty<Byte>() {
        @Override
        public String getName() {
            return "echo:blockstate_byte";
        }
        
        @Override
        public boolean isValid(Byte value) {
            return true;
        }
        
        @Override
        public Class<Byte> getType() {
            return Byte.class;
        }
        
        @Override
        public String valueToString(Byte value) {
            return value.toString();
        }
	};
	
}
