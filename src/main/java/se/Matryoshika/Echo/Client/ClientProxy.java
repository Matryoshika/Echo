package se.Matryoshika.Echo.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import se.Matryoshika.Echo.Client.Models.EchoModelCache;
import se.Matryoshika.Echo.Common.CommonProxy;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void preInit(FMLPreInitializationEvent event){
		((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new EchoModelCache());
	}
	
	@Override
	public void createModels(String name, int tier, ResourceLocation tex){
		//ModelCreator create = new ModelCreator(name, tier, tex);
	}
	
	@Override
	public void init(FMLInitializationEvent event){
		ItemColourHandler.init();
	}
	
	@Override
	public void postInit(){
		
	}

}
