package se.Matryoshika.Echo.Common.Compat.JEI;

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
import scala.actors.threadpool.Arrays;
import se.Matryoshika.Echo.Common.Compat.Botania.BotaniaCompat;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.StorageVariant;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

public class LaniaiteSpreaderWrapper implements IShapedCraftingRecipeWrapper{
	
	private List<List<ItemStack>> input;
	private ItemStack output;
	
	public LaniaiteSpreaderWrapper(IRecipe recipe){
		IStackHelper helper = JEICompat.helper.getStackHelper();
		input = helper.expandRecipeItemStackInputs(getInputs());
		output = new ItemStack(BotaniaCompat.LANIAITE_SPREADER);
	}

	@Override
	public List getInputs() {
		ItemStack head = new ItemStack(ModItems.gaiaHead);
		ItemStack frag = new ItemStack(ContentRegistry.LANIAITE_CHUNK);
		ItemStack terr = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		terr.setTagCompound(new NBTTagCompound());
		terr.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), ModBlocks.storage.getDefaultState().withProperty(BotaniaStateProps.STORAGE_VARIANT, StorageVariant.TERRASTEEL)));
		terr.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		ItemStack reds = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		reds.setTagCompound(new NBTTagCompound());
		reds.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.REDSTONE_BLOCK.getDefaultState()));
		reds.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 3);
		ItemStack livi = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		livi.setTagCompound(new NBTTagCompound());
		livi.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), ModBlocks.livingwood.getDefaultState()));
		livi.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		
		
		return Arrays.asList(new ItemStack[]{
				livi, frag, livi,
				terr, reds, head,
				livi, frag, livi
		});
	}

	@Override
	public List<ItemStack> getOutputs() {
		return Arrays.asList(new ItemStack[]{new ItemStack(BotaniaCompat.LANIAITE_SPREADER)});
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
