package se.Matryoshika.Echo.Common.Content.Tile.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public abstract class TEInventoryHandler<INVENTORY extends IItemHandler & INBTSerializable<NBTTagCompound>> extends TileEntity{


	/*
	 * Massive thanks to Choonster's TestMod3 GitHub!
	 * Capabilities makes my head hurt
	 */
	
	
	public final INVENTORY inventory = createInventory();
	protected abstract INVENTORY createInventory();
	
	public abstract Container createContainer(EntityPlayer player);
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		inventory.deserializeNBT(nbt.getCompoundTag(EchoConstants.NBT_INVENTORY));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setTag(EchoConstants.NBT_INVENTORY, inventory.serializeNBT());
		return nbt;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing){
		
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		
		return super.getCapability(capability, facing);
	}
	
}
