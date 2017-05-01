package se.Matryoshika.Echo.Common.Content.Tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.FMLLog;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class TileMenger extends TileEntity implements ITickable{

	private byte tier;
	private IBlockState state;
	
	private boolean hasTickedOther = false;
	private int age;
	private int tickedAtAge;
	
	private List<BlockPos> others = new ArrayList<BlockPos>();

	public TileMenger() {

	}

	public void update(byte tier, IBlockState state) {
		this.tier = tier;
		this.state = state;
	}

	public TileMenger(IBlockState state, byte tier) {
		this.state = state;
		this.tier = tier;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readPacketNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound ret = super.writeToNBT(compound);
		writePacketNBT(ret);
		return ret;
	}

	public void writePacketNBT(NBTTagCompound cmp) {

		cmp.setTag(EchoConstants.NBT_BLOCKSTATE, NBTUtil.func_190009_a(new NBTTagCompound(), state));
		cmp.setByte(EchoConstants.NBT_TIER, tier);
	}

	public void readPacketNBT(NBTTagCompound cmp) {
		tier = cmp.getByte(EchoConstants.NBT_TIER);
		state = NBTUtil.func_190008_d(cmp.getCompoundTag(EchoConstants.NBT_BLOCKSTATE));
	}

	public byte getTier() {
		return tier;
	}

	public IBlockState getOriginalState() {
		return state;
	}
	
	@Override
	public NBTTagCompound getUpdateTag(){
		final NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("x", pos.getX());
		nbt.setInteger("y", pos.getY());
		nbt.setInteger("z", pos.getZ());
		nbt.setTag(EchoConstants.NBT_BLOCKSTATE, NBTUtil.func_190009_a(new NBTTagCompound(), state));
		nbt.setByte(EchoConstants.NBT_TIER, tier);
		
		return nbt;
		
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag){
		setPos(new BlockPos(tag.getInteger("x"),tag.getInteger("y"),tag.getInteger("z")));
		state = NBTUtil.func_190008_d(tag.getCompoundTag(EchoConstants.NBT_BLOCKSTATE));
		tier = tag.getByte(EchoConstants.NBT_TIER);
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag(EchoConstants.NBT_BLOCKSTATE, NBTUtil.func_190009_a(new NBTTagCompound(), state));
		tag.setByte(EchoConstants.NBT_TIER, tier);
		if(tag.hasNoTags())
			return null;
		return new SPacketUpdateTileEntity(pos, -999, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		byte t = tier;
		IBlockState s = state;

		super.onDataPacket(net, pkt);
		readPacketNBT(pkt.getNbtCompound());

		update(pkt.getNbtCompound().getByte(EchoConstants.NBT_TIER),NBTUtil.func_190008_d(pkt.getNbtCompound().getCompoundTag(EchoConstants.NBT_BLOCKSTATE)));

		if (tier != t || state != s)
			worldObj.markBlockRangeForRenderUpdate(pos, pos);

	}
	
	public void updateMultiblock(){
		
		if(tier >= 6)
			return;
		
		BlockPos[] multiblock = new BlockPos[]{
				pos.add(0, 0, 0), pos.add(1, 0, 0), pos.add(2, 0, 0),
				pos.add(0, 0, 1),                   pos.add(2, 0, 1),
				pos.add(0, 0, 2), pos.add(1, 0, 2), pos.add(2, 0, 2),
				
				pos.add(0, 1, 0),                   pos.add(2, 1, 0),
				
				pos.add(0, 1, 2),                   pos.add(2, 1, 2),
				
				pos.add(0, 2, 0), pos.add(1, 2, 0), pos.add(2, 2, 0),
				pos.add(0, 2, 1),                   pos.add(2, 2, 1),
				pos.add(0, 2, 2), pos.add(1, 2, 2), pos.add(2, 2, 2)
		};
		
		for(BlockPos _pos : multiblock){
			if(worldObj.getTileEntity(_pos) == null || !(worldObj.getTileEntity(_pos) instanceof TileMenger)){
				return;
			}
			
			if(worldObj.getBlockState(_pos) != worldObj.getBlockState(pos)){
				return;
			}
		}
		
		if(!worldObj.isAirBlock(pos.add(1, 1, 1)))
			return;
		
		((CompressedBlock)worldObj.getBlockState(pos).getBlock()).spawnNewMenger(worldObj, pos, worldObj.getBlockState(pos));
		worldObj.createExplosion(null, pos.getX()+1.5, pos.getY()+1.5, pos.getZ()+1.5, 0, true);
		
		for(BlockPos _pos : multiblock)
			worldObj.setBlockToAir(_pos);
		
		
		
		
	}


	@Override
	public void update() {
		
	}

}
