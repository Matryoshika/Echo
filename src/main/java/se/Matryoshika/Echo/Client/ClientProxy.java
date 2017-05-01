package se.Matryoshika.Echo.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import se.Matryoshika.Echo.Client.Models.EchoModelCache;
import se.Matryoshika.Echo.Common.CommonProxy;
import se.Matryoshika.Echo.Common.Compat.Botania.TESRPool;
import se.Matryoshika.Echo.Common.Compat.Botania.TESRSpreader;
import se.Matryoshika.Echo.Common.Compat.Botania.TileLaniaitePool;
import se.Matryoshika.Echo.Common.Compat.Botania.TileLaniaiteSpreader;

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
	
	@Override
	public void registerCompatTESR(String type){
		if(type.equals("manapool"))
			ClientRegistry.bindTileEntitySpecialRenderer(TileLaniaitePool.class, new TESRPool());
		if(type.equals("spreader"))
			ClientRegistry.bindTileEntitySpecialRenderer(TileLaniaiteSpreader.class, new TESRSpreader());
	}

}
