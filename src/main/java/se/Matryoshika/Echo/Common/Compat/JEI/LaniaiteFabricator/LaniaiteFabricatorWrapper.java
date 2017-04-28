package se.Matryoshika.Echo.Common.Compat.JEI.LaniaiteFabricator;

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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.fluids.FluidStack;
import scala.actors.threadpool.Arrays;
import se.Matryoshika.Echo.Common.Compat.JEICompat;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class LaniaiteFabricatorWrapper implements IShapedCraftingRecipeWrapper{
	
	private List<List<ItemStack>> input;
	private ItemStack output;
	
	public LaniaiteFabricatorWrapper(IRecipe recipe){
		IStackHelper helper = JEICompat.helper.getStackHelper();
		input = helper.expandRecipeItemStackInputs(getInputs());
		output = new ItemStack(ContentRegistry.LANIAITE_FABRICATOR);
	}

	@Override
	public List getInputs() {
		ItemStack star = new ItemStack(Items.NETHER_STAR);
		ItemStack frag = new ItemStack(ContentRegistry.LANIAITE_FRAGMENT);
		ItemStack obs = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		obs.setTagCompound(new NBTTagCompound());
		obs.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.OBSIDIAN.getDefaultState()));
		obs.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 3);
		
		
		return Arrays.asList(new ItemStack[]{
				star, frag, star,
				frag, obs, frag,
				star, frag, star
		});
	}

	@Override
	public List<ItemStack> getOutputs() {
		return Arrays.asList(new ItemStack[]{new ItemStack(ContentRegistry.LANIAITE_FABRICATOR)});
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
