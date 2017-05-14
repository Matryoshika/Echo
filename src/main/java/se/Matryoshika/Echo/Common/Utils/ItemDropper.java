package se.Matryoshika.Echo.Common.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ItemDropper {
	
	
	public static List<ItemStack> dropItemHandlerContents(IItemHandler itemHandler, Random random) {
		final List<ItemStack> drops = new ArrayList<>();

		for (int slot = 0; slot < itemHandler.getSlots(); ++slot) {
			if(itemHandler.getStackInSlot(slot) == null)
				continue;
			if (itemHandler.extractItem(slot, itemHandler.getStackInSlot(slot).stackSize, true) != null) {
				final ItemStack itemStack = itemHandler.extractItem(slot, itemHandler.getStackInSlot(slot).stackSize, false);
				drops.add(itemStack);
			}
		}

		return drops;
	}

}
