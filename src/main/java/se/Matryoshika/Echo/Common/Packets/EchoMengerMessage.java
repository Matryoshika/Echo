package se.Matryoshika.Echo.Common.Packets;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;

public class EchoMengerMessage implements IMessage{
	
	protected static TileMenger te;
	
	public EchoMengerMessage(){}
	
	public EchoMengerMessage(TileMenger te){
		this.te = te;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		byte[] data = buf.array();
		
		te.update((byte) ByteBufUtils.readVarInt(buf, 1), NBTUtil.func_190008_d(ByteBufUtils.readTag(buf)));
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		Holder hold = new Holder(te);
		ByteBufUtils.writeVarInt(buf, te.getTier(), 1);
		ByteBufUtils.writeTag(buf, hold.state);

	}

	public static class Holder implements Serializable{
		public NBTTagCompound state;
		public byte tier;
		public Holder(TileMenger te){
			System.out.println(te.getOriginalState());
			state = NBTUtil.func_190009_a(new NBTTagCompound(), te.getOriginalState());
			tier = te.getTier();
		}
	}
	
	public static class Handler implements IMessageHandler<EchoMengerMessage, IMessage>{
		@Override
		public IMessage onMessage(EchoMengerMessage msg, MessageContext ctx){
			final Minecraft mc = Minecraft.getMinecraft();
			
			mc.addScheduledTask(() ->{
				System.out.println(msg.te.getTier());
				System.out.println(msg.te.getOriginalState());
				((TileMenger)mc.thePlayer.worldObj.getTileEntity(msg.te.getPos())).update(msg.te.getTier(), msg.te.getOriginalState());
			});
			return null;
		}
	}
}
