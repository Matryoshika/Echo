package se.Matryoshika.Echo.Common.Content.Recipes;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;

public class IRecipeGuillotine implements IRecipe{
	
	public IRecipeGuillotine(){
		RecipeSorter.register(Echo.MODID + ":guillotine", getClass(), RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		int[] sides = new int[]{0,2,3,5,6,8};
		String log = "logWood";
		for(int i : sides)
			if(!match(OreDictionary.getOres(log)))
				return false;
			
		
		if(!ItemStack.areItemStacksEqual(inv.getStackInSlot(1), new ItemStack(Items.DIAMOND_SWORD)))
			return false;
		
		
		if(!ItemStack.areItemStacksEqual(inv.getStackInSlot(7), CompressedBlock.get(Blocks.IRON_BLOCK.getDefaultState(), (byte)3)))
			return false;
		
		
		return true;
	}
	
	public boolean match(List<ItemStack> stacks){
		for(ItemStack stack : stacks)
			if(ArrayUtils.contains(OreDictionary.getOreIDs(stack), OreDictionary.getOreID("logWood")))
				return true;
		
				
		
		return false;
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
		return new ItemStack(ContentRegistry.GUILLOTINE);
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}

}
