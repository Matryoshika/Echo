package se.Matryoshika.Echo.Common.Content.Recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class IRecipeLaniaiteFabricator implements IRecipe{

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		for(int i = 0; i < inv.getSizeInventory(); i++){
			if(i == 4)
				if(inv.getStackInSlot(4) == null || inv.getStackInSlot(4).getItem() != Item.getItemFromBlock(ContentRegistry.COMPRESSED_BLOCK))
					return false;
				
			if(i != 4 && ((i == 0) || (i == 2) ||(i == 6) ||(i == 8)))
				if(inv.getStackInSlot(i) == null || inv.getStackInSlot(i).getItem() != Items.NETHER_STAR)
					return false;
			
			if(i != 4 && ((i == 1) || (i == 3) || (i == 5) || (i == 7)))
				if(inv.getStackInSlot(i) == null || inv.getStackInSlot(i).getItem() != ContentRegistry.LANIAITE_FRAGMENT)
					return false;
				
		}
		
		ItemStack obsidian = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		obsidian.setTagCompound(new NBTTagCompound());
		obsidian.getTagCompound().setTag(EchoConstants.NBT_BLOCKSTATE,NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.OBSIDIAN.getDefaultState()));
		obsidian.getTagCompound().setByte(EchoConstants.NBT_TIER, (byte) 3);
		
		if(!ItemStack.areItemStacksEqual(obsidian, inv.getStackInSlot(4)))
			return false;
		
		
		return true;
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
		return new ItemStack(ContentRegistry.LANIAITE_FABRICATOR);
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}

}
