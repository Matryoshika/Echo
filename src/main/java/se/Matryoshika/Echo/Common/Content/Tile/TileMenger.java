package se.Matryoshika.Echo.Common.Content.Tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class TileMenger extends TileEntity {

	private byte tier;

	public TileMenger(IBlockState state, byte tier){
		writeBlockState(state);
		this.tier = tier;
	}
	
	public void writeBlockState(IBlockState state){
		NBTTagCompound nbt = this.getTileData();
		nbt.setTag(EchoConstants.NBT_BLOCKSTATE, NBTUtil.func_190009_a(new NBTTagCompound(), state));
		nbt.setByte(EchoConstants.NBT_TIER, tier);
		writeToNBT(nbt);
	}

}
