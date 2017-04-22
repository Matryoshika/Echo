package se.Matryoshika.Echo.Common.Content.Tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.FMLLog;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class TileMenger extends TileEntity {

	private byte tier;
	private IBlockState state;

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

		System.out.println("State is: " + state);

		cmp.setTag(EchoConstants.NBT_BLOCKSTATE, NBTUtil.func_190009_a(new NBTTagCompound(), state));
		cmp.setByte(EchoConstants.NBT_TIER, tier);
	}

	public void readPacketNBT(NBTTagCompound cmp) {
		tier = cmp.getByte(EchoConstants.NBT_TIER);
		state = NBTUtil.func_190008_d(cmp.getCompoundTag(EchoConstants.NBT_BLOCKSTATE));
		System.out.println("State is: " + state);
	}

	public byte getTier() {
		return tier;
	}

	public IBlockState getOriginalState() {
		return state;
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writePacketNBT(tag);
		return new SPacketUpdateTileEntity(pos, -999, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		byte t = tier;
		IBlockState s = state;

		super.onDataPacket(net, pkt);
		readPacketNBT(pkt.getNbtCompound());

		FMLLog.bigWarning("Packet sent state: "+ NBTUtil.func_190008_d(pkt.getNbtCompound().getCompoundTag(EchoConstants.NBT_BLOCKSTATE)),new Object[0]);

		update(pkt.getNbtCompound().getByte(EchoConstants.NBT_TIER),NBTUtil.func_190008_d(pkt.getNbtCompound().getCompoundTag(EchoConstants.NBT_BLOCKSTATE)));

		if (tier != t || state != s)
			worldObj.markBlockRangeForRenderUpdate(pos, pos);

	}

}
