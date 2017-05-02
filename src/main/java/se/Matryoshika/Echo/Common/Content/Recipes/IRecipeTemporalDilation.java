package se.Matryoshika.Echo.Common.Content.Recipes;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class IRecipeTemporalDilation implements IRecipe{
	
	public IRecipeTemporalDilation(){
		RecipeSorter.register(Echo.MODID + ":temporal_dilator", getClass(), RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		ItemStack eme = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		eme.setTagCompound(new NBTTagCompound());
		eme.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.EMERALD_BLOCK.getDefaultState()));
		eme.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		
		ItemStack blo = new ItemStack(ContentRegistry.LANIAITE_BLOCK);
		ItemStack chu = new ItemStack(ContentRegistry.LANIAITE_CHUNK);
		
		int[] corners = new int[]{0,2,6,8};
		int[] middles = new int[]{1,3,5,7};
		
		for(int i : corners)
			if(!ItemStack.areItemStacksEqual(inv.getStackInSlot(i), blo))
				return false;
		for(int i : middles)
			if(!ItemStack.areItemStacksEqual(inv.getStackInSlot(i), chu))
				return false;
		
		if(!ItemStack.areItemStacksEqual(inv.getStackInSlot(4), eme))
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
		return new ItemStack(ContentRegistry.TEMPORAL_DILATOR);
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}

}
