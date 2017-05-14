package se.Matryoshika.Echo.Common.Content.Tile.Inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class SlotOnlyMenger extends SlotItemHandler {
	
	public byte tier;
	
	public SlotOnlyMenger(IItemHandler itemHandler, int index, int xPosition, int yPosition, byte tier) {
		super(itemHandler, index, xPosition, yPosition);
		this.tier = tier;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return super.isItemValid(stack) && stack.getItem() == Item.getItemFromBlock(ContentRegistry.COMPRESSED_BLOCK) && stack.getTagCompound().getByte(EchoConstants.NBT_TIER) == tier;
	}
}
