package se.Matryoshika.Echo.Common.Content.Blocks;

import org.apache.commons.lang3.reflect.FieldUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import se.Matryoshika.Echo.Echo;

public class CompressedBlock extends Block{
	
	public final IBlockState original;

	public CompressedBlock(IBlockState block, int tier) throws IllegalArgumentException, IllegalAccessException {
		super(block.getMaterial());
		original = block;
		this.setRegistryName(Echo.MODID, block.getBlock().getRegistryName().getResourcePath());
		this.setUnlocalizedName(getRegistryName().toString());
		this.setCreativeTab(Echo.TAB);
	}

}
