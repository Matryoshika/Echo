package se.Matryoshika.Echo.Common.Content.Recipes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class IRecipePhaseSubstantiator implements IRecipe{

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		for(int i = 0; i < inv.getSizeInventory(); i++){
			if(i == 4)
				if(inv.getStackInSlot(4) == null || inv.getStackInSlot(4).getItem() != Item.getItemFromBlock(ContentRegistry.LANIAITE_BLOCK))
					return false;
				
			if(i != 4)
				if(inv.getStackInSlot(i) == null || inv.getStackInSlot(i).getItem() != Item.getItemFromBlock(ContentRegistry.COMPRESSED_BLOCK))
					return false;
				
		}
		
		int[] diamonds = new int[]{1, 3, 5, 7};
		ItemStack diam = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		if (!diam.hasTagCompound())
			diam.setTagCompound(new NBTTagCompound());
		diam.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.DIAMOND_BLOCK.getDefaultState()));
		diam.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		for(int i : diamonds)
			if(!ItemStack.areItemStacksEqual(diam, inv.getStackInSlot(i)))
				return false;
			
		
		int[] obsidian = new int[]{0, 2, 6, 8};
		ItemStack obs = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		if (!obs.hasTagCompound())
			obs.setTagCompound(new NBTTagCompound());
		obs.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.OBSIDIAN.getDefaultState()));
		obs.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		for(int i : obsidian)
			if(!ItemStack.areItemStacksEqual(obs, inv.getStackInSlot(i)))
				return false;
			
		
		
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return getRecipeOutput().copy();
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ContentRegistry.PHASE_SUBSTANTIATOR);
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}

}
