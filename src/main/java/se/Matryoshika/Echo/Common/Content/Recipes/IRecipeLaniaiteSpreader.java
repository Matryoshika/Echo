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

public class IRecipeLaniaiteSpreader implements IRecipe{
	
	public IRecipeLaniaiteSpreader(){
		RecipeSorter.register(Echo.MODID + ":laniaite_spreader", getClass(), RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		ItemStack head = new ItemStack(vazkii.botania.common.item.ModItems.gaiaHead);
		ItemStack frag = new ItemStack(ContentRegistry.LANIAITE_CHUNK);
		ItemStack terr = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		terr.setTagCompound(new NBTTagCompound());
		terr.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), vazkii.botania.common.block.ModBlocks.storage.getDefaultState().withProperty(vazkii.botania.api.state.BotaniaStateProps.STORAGE_VARIANT, vazkii.botania.api.state.enums.StorageVariant.TERRASTEEL)));
		terr.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		ItemStack reds = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		reds.setTagCompound(new NBTTagCompound());
		reds.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.REDSTONE_BLOCK.getDefaultState()));
		reds.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 3);
		ItemStack livi = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		livi.setTagCompound(new NBTTagCompound());
		livi.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), vazkii.botania.common.block.ModBlocks.livingwood.getDefaultState()));
		livi.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 2);
		
		
		if(ItemStack.areItemStacksEqual(livi, inv.getStackInSlot(0)) &&
				ItemStack.areItemStacksEqual(livi, inv.getStackInSlot(2)) &&
				ItemStack.areItemStacksEqual(livi, inv.getStackInSlot(6)) &&
				ItemStack.areItemStacksEqual(livi, inv.getStackInSlot(8)))
			if(ItemStack.areItemStacksEqual(frag, inv.getStackInSlot(1)) &&
					ItemStack.areItemStacksEqual(frag, inv.getStackInSlot(7)))	
				if(ItemStack.areItemStacksEqual(terr, inv.getStackInSlot(3)))
					if(ItemStack.areItemStacksEqual(reds, inv.getStackInSlot(4)))
						if(ItemStack.areItemStacksEqual(head, inv.getStackInSlot(5)))
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
		return new ItemStack(BotaniaCompat.LANIAITE_SPREADER);
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}

}
