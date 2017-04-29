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
import se.Matryoshika.Echo.Common.Compat.Botania.BotaniaCompat;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.StorageVariant;
import vazkii.botania.common.block.ModBlocks;

public class IRecipeLaniaitePool implements IRecipe{
	
	public IRecipeLaniaitePool(){
		RecipeSorter.register(Echo.MODID + ":laniaite_pool", getClass(), RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		ItemStack lan = new ItemStack(ContentRegistry.LANIAITE_BLOCK);
		ItemStack pool = new ItemStack(ModBlocks.pool);
		ItemStack terr = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		terr.setTagCompound(new NBTTagCompound());
		terr.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), ModBlocks.storage.getDefaultState().withProperty(BotaniaStateProps.STORAGE_VARIANT, StorageVariant.TERRASTEEL)));
		terr.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		ItemStack liv = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		liv.setTagCompound(new NBTTagCompound());
		liv.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), ModBlocks.livingrock.getDefaultState()));
		liv.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 3);
		
		//Top recipe first
		if(
				ItemStack.areItemStacksEqual(inv.getStackInSlot(0), lan) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(2), lan) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(1), pool) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(3), liv) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(4), terr) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(5), liv) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(6), null) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(7), null) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(8), null)
		){return true;}
		else if(
				ItemStack.areItemStacksEqual(inv.getStackInSlot(0), null) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(1), null) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(2), null) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(3), lan) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(5), lan) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(4), pool) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(6), liv) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(7), terr) &&
				ItemStack.areItemStacksEqual(inv.getStackInSlot(8), liv) 
		){return true;}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return getRecipeOutput().copy();
	}

	@Override
	public int getRecipeSize() {
		return 6;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(BotaniaCompat.LANIAITE_POOL);
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}

}
