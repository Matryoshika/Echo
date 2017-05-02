package se.Matryoshika.Echo.Common.Compat.JEI;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.fluids.FluidStack;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class TemporalDilationWrapper implements IShapedCraftingRecipeWrapper{
	
	private List<List<ItemStack>> input;
	private ItemStack output;
	
	public TemporalDilationWrapper(IRecipe recipe){
		IStackHelper helper = JEICompat.helper.getStackHelper();
		input = helper.expandRecipeItemStackInputs(getInputs());
		output = new ItemStack(ContentRegistry.TEMPORAL_DILATOR);
	}

	@Override
	public List getInputs() {
		ItemStack eme = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		eme.setTagCompound(new NBTTagCompound());
		eme.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.EMERALD_BLOCK.getDefaultState()));
		eme.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		
		ItemStack blo = new ItemStack(ContentRegistry.LANIAITE_BLOCK);
		ItemStack chu = new ItemStack(ContentRegistry.LANIAITE_CHUNK);
		
		return Arrays.asList(new ItemStack[]{
				blo, chu, blo,
				chu, eme, chu,
				blo, chu, blo
		});
	}

	@Override
	public List<ItemStack> getOutputs() {
		return Arrays.asList(new ItemStack[]{new ItemStack(ContentRegistry.TEMPORAL_DILATOR)});
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		
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
