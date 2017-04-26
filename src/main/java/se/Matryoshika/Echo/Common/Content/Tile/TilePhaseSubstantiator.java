package se.Matryoshika.Echo.Common.Content.Tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.Tile.Inventory.ContainerPhaseSubstantiator;
import se.Matryoshika.Echo.Common.Content.Tile.Inventory.IContainerCallbacks;
import se.Matryoshika.Echo.Common.Content.Tile.Inventory.IWorldContainer;
import se.Matryoshika.Echo.Common.Content.Tile.Inventory.ItemHandlerItem;
import se.Matryoshika.Echo.Common.Content.Tile.Inventory.ItemHandlerNameable;
import se.Matryoshika.Echo.Common.Content.Tile.Inventory.NameableCombinedInvWrapper;
import se.Matryoshika.Echo.Common.Content.Tile.Inventory.TEInventoryHandler;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class TilePhaseSubstantiator extends TEInventoryHandler implements IContainerCallbacks, ITickable{
	
	private static final int INVENTORY_SIZE = 27;
	private static final ITextComponent DEFAULT_NAME = new TextComponentTranslation("container."+Echo.MODID+":phase_substantiator");

	@Override
	protected IItemHandler createInventory() {
		return new ItemHandlerItem(INVENTORY_SIZE, DEFAULT_NAME);
	}

	@Override
	public Container createContainer(EntityPlayer player) {
		final IItemHandlerModifiable playerInventory = (IItemHandlerModifiable) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		final IItemHandler playerInventoryWrapper = new NameableCombinedInvWrapper(player.inventory, playerInventory);

		return new ContainerPhaseSubstantiator(playerInventoryWrapper, createInventory(), player, this);
	}
	
	public void openGUI(World world, EntityPlayer player) {
		if (!world.isRemote) {
			player.openGui(Echo.MODID, getGuiId(), world, pos.getX(), pos.getY(), pos.getZ());
		}
	}
	
	protected int getGuiId() {
		return EchoConstants.GUI_PHASE_SUBSTANTIATOR;
	}

	@Override
	public void onContainerOpened(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
	}
	
	public void setDisplayName(String displayName){
		((ItemHandlerNameable) inventory).setDisplayName(new TextComponentString(displayName));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
