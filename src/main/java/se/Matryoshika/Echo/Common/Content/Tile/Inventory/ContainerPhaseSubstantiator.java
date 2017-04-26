package se.Matryoshika.Echo.Common.Content.Tile.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerPhaseSubstantiator extends Container {
	
	private static final int SLOTS_PER_ROW = 9;

	private final IContainerCallbacks callbacks;

	private final IItemHandler playerInventory;


	private final IItemHandler chestInventory;


	private final int numRows;

	public ContainerPhaseSubstantiator(IItemHandler playerInventory, IItemHandler chestInventory, EntityPlayer player, IContainerCallbacks containerCallbacks) {
		this.playerInventory = playerInventory;
		this.chestInventory = chestInventory;

		callbacks = containerCallbacks;
		callbacks.onContainerOpened(player);

		numRows = (chestInventory.getSlots() / SLOTS_PER_ROW);

		final int chestOffset = (numRows - 4) * 18;

		for (int row = 0; row < this.numRows; ++row) {
			for (int col = 0; col < SLOTS_PER_ROW; ++col) {
				addSlotToContainer(new SlotItemHandler(chestInventory, col + row * SLOTS_PER_ROW, 8 + col * 18, 18 + row * 18));
			}
		}

		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < SLOTS_PER_ROW; ++col) {
				addSlotToContainer(new SlotItemHandler(playerInventory, col + row * SLOTS_PER_ROW + SLOTS_PER_ROW, 8 + col * 18, 103 + row * 18 + chestOffset));
			}
		}

		for (int col = 0; col < SLOTS_PER_ROW; ++col) {
			addSlotToContainer(new SlotItemHandler(playerInventory, col, 8 + col * 18, 161 + chestOffset));
		}

	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		final Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getStack() != null) {
			final ItemStack stack = slot.getStack();
			final ItemStack originalStack = stack.copy();

			if (index < this.numRows * SLOTS_PER_ROW) {
				if (!this.mergeItemStack(stack, this.numRows * SLOTS_PER_ROW, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(stack, 0, this.numRows * SLOTS_PER_ROW, false)) {
				return null;
			}

			if (stack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			return originalStack;
		}

		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return callbacks.isUsableByPlayer(playerIn);
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);

		callbacks.onContainerClosed(playerIn);
	}

	/**
	 * Get the player inventory.
	 *
	 * @return The player inventory
	 */
	public IItemHandler getPlayerInventory() {
		return playerInventory;
	}

	/**
	 * Get the chest inventory.
	 *
	 * @return The chest inventory
	 */
	public IItemHandler getChestInventory() {
		return chestInventory;
	}

	/**
	 * Get the number of rows in the chest inventory.
	 *
	 * @return The number of rows in the chest inventory
	 */
	public int getNumRows() {
		return numRows;
}

}
