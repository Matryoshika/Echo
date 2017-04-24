package se.Matryoshika.Echo.Client;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import se.Matryoshika.Echo.Common.CommonProxy;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void preInit(FMLPreInitializationEvent event){
		//MinecraftForge.EVENT_BUS.register(new ModelBakeEventHandler());
	}
	
	@Override
	public void createModels(String name, int tier, ResourceLocation tex){
		//ModelCreator create = new ModelCreator(name, tier, tex);
	}
	
	@Override
	public void postInit(){
		
	}

}
