package se.Matryoshika.Echo.Common.Content.Blocks;

import java.util.List;

import com.google.gson.Gson;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
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
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;
import se.Matryoshika.Echo.Common.Utils.BlockStateJSON;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class CompressedBlock extends Block {

	public CompressedBlock() {
		super(Material.ROCK);
		this.setRegistryName(Echo.MODID, "compressed_block");
		this.setUnlocalizedName(getRegistryName().toString());
		this.setHardness(3F);
		
	}
	
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list){
		
		for (IBlockState state : BlockStateJSON.getAllowedStates()) {

			for (byte i = 1; i <= BlockStateJSON.getTiers(state); i++) {
				ItemStack stack = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setTag(EchoConstants.NBT_BLOCKSTATE, NBTUtil.func_190009_a(new NBTTagCompound(), state));
				nbt.setByte(EchoConstants.NBT_TIER, i);
				stack.setTagCompound(nbt);
				list.add(stack);
			}

		}
    }

	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {

		((TileMenger) world.getTileEntity(pos)).update(stack.getTagCompound().getByte(EchoConstants.NBT_TIER),
				NBTUtil.func_190008_d(stack.getTagCompound().getCompoundTag(EchoConstants.NBT_BLOCKSTATE)));
		((TileMenger) world.getTileEntity(pos)).markDirty();
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
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return getPickBlock(super.getPickBlock(state, target, world, pos, player),
				(TileMenger) world.getTileEntity(pos), world, pos, state);
	}

	public ItemStack getPickBlock(ItemStack stack, TileMenger te, World world, BlockPos pos, IBlockState state) {
		if (te != null && te.getOriginalState() != null) {
			if (!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			IExtendedBlockState menger = (IExtendedBlockState) getExtendedState(state, world, pos);

			IBlockState copy = menger.getValue(IBS);
			stack.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), copy));
			stack.getTagCompound().setByte(EchoConstants.NBT_TIER, te.getTier());
		}
		
		return stack;
	}
	
	@Override
	public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		java.util.List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
		TileMenger te = world.getTileEntity(pos) instanceof TileMenger ? (TileMenger) world.getTileEntity(pos) : null;
		if (te != null && te.getOriginalState() != null) {
			for (ItemStack stack : ret) {
				if (stack != null && stack.getItem() == Item.getItemFromBlock(ContentRegistry.COMPRESSED_BLOCK)) {
					if (!stack.hasTagCompound())
						stack.setTagCompound(new NBTTagCompound());
					IExtendedBlockState menger = (IExtendedBlockState) getExtendedState(state, world, pos);

					IBlockState copy = menger.getValue(IBS);
					stack.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), copy));
					stack.getTagCompound().setByte(EchoConstants.NBT_TIER, te.getTier());
				}
			}
		}
		return ret;
	}
	
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest){
        if (willHarvest) return true;
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack tool){
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    
    public void spawnNewMenger(World world, BlockPos pos, IBlockState state){
    	IExtendedBlockState ext = (IExtendedBlockState) getExtendedState(state, world, pos);
		IBlockState copy = ext.getValue(CompressedBlock.IBS);
		byte tier = ext.getValue(CompressedBlock.BIT);
		
		world.createExplosion(null, pos.getX()+1.5, pos.getY()+1.5, pos.getZ()+1.5, 0, true);
		world.setBlockState(pos.add(1, 1, 1), ContentRegistry.COMPRESSED_BLOCK.getDefaultState());
		world.setTileEntity(pos.add(1, 1, 1), new TileMenger(copy, (byte) (tier+1)));
    }
	

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state instanceof IExtendedBlockState && world.getTileEntity(pos) instanceof TileMenger) {
			return ((IExtendedBlockState) state)
					.withProperty(IBS, ((TileMenger) world.getTileEntity(pos)).getOriginalState())
					.withProperty(BIT, ((TileMenger) world.getTileEntity(pos)).getTier());
		}
		return state;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { IBS, BIT });
	}

	public static final IUnlistedProperty<IBlockState> IBS = new IUnlistedProperty<IBlockState>() {
		@Override
		public String getName() {return "echo:blockstate_property";}

		@Override
		public boolean isValid(IBlockState value) {return true;}

		@Override
		public Class<IBlockState> getType() {return IBlockState.class;}

		@Override
		public String valueToString(IBlockState value) {return new Gson().toJson(NBTUtil.func_190009_a(new NBTTagCompound(), value));}
	};

	public static final IUnlistedProperty<Byte> BIT = new IUnlistedProperty<Byte>() {
		@Override
		public String getName() {return "echo:blockstate_byte";}

		@Override
		public boolean isValid(Byte value) {return true;}

		@Override
		public Class<Byte> getType() {return Byte.class;}

		@Override
		public String valueToString(Byte value) {return value.toString();}
	};

}
