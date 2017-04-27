package se.Matryoshika.Echo.Common.Content.Tile.Inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import se.Matryoshika.Echo.Common.Content.Tile.TilePhaseSubstantiator;

public class ContainerPhaseSubstantiator extends EchoContainer{
	
	TilePhaseSubstantiator tile;

	public ContainerPhaseSubstantiator(InventoryPlayer playInv, TilePhaseSubstantiator tile) {
		super(26);
		this.tile = tile;
		
		IItemHandler inventory = this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		//addSlotToContainer(new SlotNonMenger(inventory, 0, 8, 7));
		int slotCount = 0;
		for(int x = 0; x <= 2; x++)
			for(int y = 0; y <= 2; y++){
				if(y == 1 && x == 1)
					continue;
				addSlotToContainer(new SlotNonMenger(inventory, slotCount++, (x*18) + 8, (y*18) + 7));
			}
		
		for(int x = 0; x <= 2; x++)
			for(int y = 0; y <= 2; y++){
				if(y == 1 && x == 1)
					continue;
				addSlotToContainer(new SlotNonMenger(inventory, slotCount++, (x*18) + 116, (y*18) + 7));
			}
		
		addSlotToContainer(new SlotNonMenger(inventory, slotCount++, 67, 12));
		addSlotToContainer(new SlotNonMenger(inventory, slotCount++, 67, 38));
		addSlotToContainer(new SlotNonMenger(inventory, slotCount++, 92, 12));
		addSlotToContainer(new SlotNonMenger(inventory, slotCount++, 92, 38));
		
		for(int x = 0; x < 6; x++)
			addSlotToContainer(new SlotOnlyMenger(inventory, slotCount++, (x*18) + 62, 63));
		
		
		bindPlayerInventory(playInv);
	}

}
