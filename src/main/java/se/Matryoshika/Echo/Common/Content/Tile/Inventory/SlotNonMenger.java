package se.Matryoshika.Echo.Common.Content.Tile.Inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;

public class SlotNonMenger extends SlotItemHandler {
	
	public SlotNonMenger(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return super.isItemValid(stack) && stack.getItem() != Item.getItemFromBlock(ContentRegistry.COMPRESSED_BLOCK);
	}
}
