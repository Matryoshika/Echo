package se.Matryoshika.Echo.Common.Content.Tile;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaDoubleChestItemHandler;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;

public class TileLaniaiteFabricator extends TileEntity implements ITickable{
	
	private int tick;

	@Override
	public void update() {
		tick++;
		
		if(tick >= 20*60){
			tick = 0;
			
			IItemHandler inv = getItemHandler();
			if(inv != null)
				for(int i = 0; i < inv.getSlots(); i++){
					if(inv.getStackInSlot(i) == null || (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() == ContentRegistry.LANIAITE_FRAGMENT && inv.getStackInSlot(i).stackSize < 64)){
						inv.insertItem(i, new ItemStack(ContentRegistry.LANIAITE_FRAGMENT), false);
						break;
					}
				}
		}
		
		
	}
	
	
	private IItemHandler getItemHandler(){
		
		for(EnumFacing side : EnumFacing.values()){
			TileEntity te = worldObj.getTileEntity(pos.offset(side));
			if(te == null)
				continue;
			
			if(te instanceof TileEntityChest){
				IItemHandler doubleChest = VanillaDoubleChestItemHandler.get((TileEntityChest) te);
				if(doubleChest != VanillaDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE)
					return doubleChest;
			}
			
			IItemHandler ret = te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) ? te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) : null;
		
			if(ret == null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
				ret = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			
			return ret;
		}
		
		return null;
	}

}
