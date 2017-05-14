package se.Matryoshika.Echo.Common.Content.Tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class TilePhaseSubstantiator extends TileEntity implements ITickable{
	
	public ItemStackHandler inputSlots;
	public ItemStackHandler outputSlots;

	public TilePhaseSubstantiator(){
		inputSlots = new ItemStackHandler(20);
		outputSlots = new ItemStackHandler(6);
	}
	
	@Override
	public void update() {
		
		for(int i = 0; i < 5; i++){
			if(outputSlots.getStackInSlot(i) != null && outputSlots.getStackInSlot(i).stackSize >= 20){
				IBlockState state = NBTUtil.func_190008_d(outputSlots.getStackInSlot(i).getTagCompound().getCompoundTag(EchoConstants.NBT_BLOCKSTATE));
				if(outputSlots.getStackInSlot(i).getTagCompound().getByte(EchoConstants.NBT_TIER) >= 6)
					continue;
				
				ItemStack stack = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setTag(EchoConstants.NBT_BLOCKSTATE, outputSlots.getStackInSlot(i).getTagCompound().getTag(EchoConstants.NBT_BLOCKSTATE));
				nbt.setByte(EchoConstants.NBT_TIER, (byte) (outputSlots.getStackInSlot(i).getTagCompound().getByte(EchoConstants.NBT_TIER)+1));
				stack.setTagCompound(nbt);
				if(outputSlots.insertItem(i+1, stack, true) == null){
					outputSlots.insertItem(i+1, stack, false);
					outputSlots.extractItem(i, 20, false);
				}
			}
		}
		
		
		//Iterate over all of the inputs first, to check validity
		for(int i = 0; i < inputSlots.getSlots(); i++){
			if(i == inputSlots.getSlots()-1)
				continue;
			if(inputSlots.getStackInSlot(i) == null || inputSlots.getStackInSlot(i+1) == null)
				return;
			
			if(!ItemStack.areItemsEqual(inputSlots.getStackInSlot(i), inputSlots.getStackInSlot(i+1)))
				return;
			
			if(Block.getBlockFromItem(inputSlots.getStackInSlot(i).getItem()) == null || Block.getBlockFromItem(inputSlots.getStackInSlot(i).getItem()).getBlockLayer() != BlockRenderLayer.SOLID)
				return;
			
			if(!Block.getBlockFromItem(inputSlots.getStackInSlot(i).getItem()).isFullBlock(Block.getBlockFromItem(inputSlots.getStackInSlot(i).getItem()).getStateFromMeta(inputSlots.getStackInSlot(i).getMetadata())))
				return;
		}
		
		//If the code gets this far, then all slots are full, and all stacks are equal
		compress(Block.getBlockFromItem(inputSlots.getStackInSlot(0).getItem()).getStateFromMeta(inputSlots.getStackInSlot(0).getMetadata()));
		
	}
	
	public void compress(IBlockState state){
		//All slots are full, as it has already been checked
		
		ItemStack stack = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		NBTTagCompound nbt = new NBTTagCompound();
    	nbt.setTag(EchoConstants.NBT_BLOCKSTATE, NBTUtil.func_190009_a(new NBTTagCompound(), state));
    	nbt.setByte(EchoConstants.NBT_TIER, (byte)1);
    	stack.setTagCompound(nbt);
    	if(outputSlots.insertItem(0, stack, true) == null){
    		outputSlots.insertItem(0, stack, false);
    		for(int i = 0; i < inputSlots.getSlots(); i++)
    			inputSlots.extractItem(i, 1, false);
    	}
	}
	
	public void openGUI(World world, EntityPlayer player) {
		if (!world.isRemote) {
			player.openGui(Echo.MODID, EchoConstants.GUI_PHASE_SUBSTANTIATOR, world, pos.getX(), pos.getY(), pos.getZ());
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return this.getCapability(capability, facing) != null;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			this.markDirty();
			return (T) new CombinedInvWrapper(inputSlots, outputSlots);
		}
		return super.getCapability(capability, facing);
	}

	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag(EchoConstants.NBT_PHASE_INPUT, inputSlots.serializeNBT());
		compound.setTag(EchoConstants.NBT_PHASE_OUTPUT, outputSlots.serializeNBT());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(inputSlots == null) {
			inputSlots = new ItemStackHandler(20);
			outputSlots = new ItemStackHandler(6);
		}
		if(compound.hasKey(EchoConstants.NBT_PHASE_INPUT)) {
			inputSlots.deserializeNBT((NBTTagCompound) compound.getTag(EchoConstants.NBT_PHASE_INPUT));
			outputSlots.deserializeNBT((NBTTagCompound) compound.getTag(EchoConstants.NBT_PHASE_OUTPUT));
		}
	}

}
