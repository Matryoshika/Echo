package se.Matryoshika.Echo.Common.Compat.JEI;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.FluidStack;
import se.Matryoshika.Echo.Common.Compat.Botania.BotaniaCompat;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeUpgrades;

public class UpgradeWrapper implements IShapedCraftingRecipeWrapper{
	
	private List<List<ItemStack>> input;
	private ItemStack output;
	private ItemStack main;
	
	public UpgradeWrapper(IRecipeUpgrades recipe){
		IStackHelper helper = JEICompat.helper.getStackHelper();
		main = recipe.input.copy();
		input = helper.expandRecipeItemStackInputs(getInputs());
		output = recipe.output;
	}

	@Override
	public List getInputs() {
		ItemStack iron = new ItemStack(Items.IRON_INGOT);
		
		return Arrays.asList(new ItemStack[]{
				iron, iron, iron,
				iron, main, iron,
				iron, iron, iron
		});
	}

	@Override
	public List<ItemStack> getOutputs() {
		return Arrays.asList(new ItemStack[]{output});
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
