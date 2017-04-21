package se.Matryoshika.Echo.Common.Content.Blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;

public class CompressedBlock extends Block {
	

	public CompressedBlock(){
		super(Material.ROCK);
		this.setRegistryName(Echo.MODID, "compressed_block");
		this.setUnlocalizedName(getRegistryName().toString());
		this.setCreativeTab(Echo.TAB);
	}
	
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
		List<ItemStack> drops = new ArrayList<ItemStack>();
		
		ItemStack stack = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		
		if(world.getBlockState(pos) instanceof TileMenger){
			TileMenger menger = (TileMenger) world.getBlockState(pos);
			NBTTagCompound nbt = menger.getTileData();
			stack.setTagCompound(nbt);
			drops.add(stack);
		}
		
		return drops;
	}

	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	//TileEntity is added by Compression wand & the ItemBlock respectively, to supply the proper IBlockState required for rendering etc
	public TileEntity createTileEntity(World world, IBlockState state) {
		return null;
	}

	public CompressedBlock setTexture(IBlockState state) {
		//Echo.proxy.createModels(state.getBlock().getRegistryName().getResourcePath(), tier, new ResourceLocation("minecraft", "blocks/emerald_block"));
		return this;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.INVISIBLE;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player){
		ItemStack stack = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		if(world.getTileEntity(pos) instanceof TileMenger){
			TileMenger menger = (TileMenger) world.getTileEntity(pos);
			System.out.println(menger.getTileData());
			if(menger.getTileData() != null)
				stack.setTagCompound(menger.getTileData());
		}
		
        return stack;
    }

}
