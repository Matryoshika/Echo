package se.Matryoshika.Echo.Common.Content.Recipes;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import se.Matryoshika.Echo.Echo;

public class IRecipeUpgrades implements IRecipe{
	
	public ItemStack input;
	public ItemStack output;
	
	public IRecipeUpgrades(ItemStack input, ItemStack output){
		this.input = input;
		this.output = output;
		RecipeSorter.register(Echo.MODID + ":upgrade"+output.getUnlocalizedName(), getClass(), RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		int[] donut = new int[]{0,1,2,3,5,6,7,8};
		ItemStack iron = new ItemStack(Items.IRON_INGOT);
		
		for(int i : donut)
			if(!ItemStack.areItemStacksEqual(inv.getStackInSlot(i), iron))
				return false;
		
		if(!ItemStack.areItemStacksEqual(inv.getStackInSlot(4), input))
			return false;
		
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return output.copy();
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}

}
