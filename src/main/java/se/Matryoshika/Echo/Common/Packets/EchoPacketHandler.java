package se.Matryoshika.Echo.Common.Packets;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class EchoPacketHandler {
	
	private static int ID = 0;
	public static SimpleNetworkWrapper NETWORK;
	
	public EchoPacketHandler(){
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public static void registerEchoPacketHandler(){
		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("echo");
		NETWORK.registerMessage(EchoMengerMessage.Handler.class, EchoMengerMessage.class, ID++, Side.CLIENT);
	}

}
