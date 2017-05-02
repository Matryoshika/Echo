package se.Matryoshika.Echo.Common.Compat.JEI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
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

public class PhaseSubstantiatorWrapper implements IShapedCraftingRecipeWrapper{
	
	private List<List<ItemStack>> input;
	private ItemStack output;
	
	public PhaseSubstantiatorWrapper(IRecipe recipe){
		IStackHelper helper = JEICompat.helper.getStackHelper();
		input = helper.expandRecipeItemStackInputs(getInputs());
		output = new ItemStack(ContentRegistry.PHASE_SUBSTANTIATOR);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
		
	}

	@Override
	public List getInputs() {
		ItemStack diamond = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		ItemStack obsidian = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		diamond.setTagCompound(new NBTTagCompound());
		obsidian.setTagCompound(new NBTTagCompound());
		diamond.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.DIAMOND_BLOCK.getDefaultState()));
		diamond.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		obsidian.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.OBSIDIAN.getDefaultState()));
		obsidian.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		ItemStack laniaite = new ItemStack(ContentRegistry.LANIAITE_BLOCK);
		
		return Arrays.asList(new ItemStack[]{
				obsidian, diamond, obsidian,
				diamond, laniaite, diamond,
				obsidian, diamond, obsidian
		});
	}

	@Override
	public List getOutputs() {
		return Arrays.asList(new ItemStack[]{new ItemStack(ContentRegistry.PHASE_SUBSTANTIATOR)});
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
		// TODO Auto-generated method stub
		
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
