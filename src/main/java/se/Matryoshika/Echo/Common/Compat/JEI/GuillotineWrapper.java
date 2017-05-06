package se.Matryoshika.Echo.Common.Compat.JEI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;

public class GuillotineWrapper implements IShapedCraftingRecipeWrapper{
	
	private List<List<ItemStack>> input;
	private ItemStack output;
	
	public GuillotineWrapper(IRecipe recipe){
		IStackHelper helper = JEICompat.helper.getStackHelper();
		input = helper.expandRecipeItemStackInputs(getInputs());
		List<ItemStack> logs = OreDictionary.getOres("logWood");
		int[] sides = new int[]{0,2,3,5,6,8};
		for(int i : sides)
			input.get(i).addAll(logs);
		output = new ItemStack(ContentRegistry.GUILLOTINE);
	}

	@Override
	public List getInputs() {
		return Arrays.asList(new ItemStack[]{
				null, new ItemStack(Items.DIAMOND_SWORD), null,
				null, null, null,
				null, CompressedBlock.get(Blocks.IRON_BLOCK.getDefaultState(), (byte) 3), null
		});
	}

	@Override
	public List<ItemStack> getOutputs() {
		return Arrays.asList(new ItemStack[]{new ItemStack(ContentRegistry.GUILLOTINE)});
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
	}

	@Override
	public List<FluidStack> getFluidInputs() {
		return ImmutableList.of();
	}

	@Override
	public List<FluidStack> getFluidOutputs() {
		return ImmutableList.of();
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
	}

	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
		
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return ImmutableList.of();
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}

	@Override
	public int getWidth() {
		return 3;
	}

	@Override
	public int getHeight() {
		return 3;
	}

}
