package se.Matryoshika.Echo.Common;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import se.Matryoshika.Echo.Common.Compat.Botania.BotaniaCompat;

public class CommonProxy {
	

	public void preInit(FMLPreInitializationEvent event){
		
	}
	
	public void createModels(String name, int tier, ResourceLocation tex){}
	
	public void init(FMLInitializationEvent event){
		if(Loader.isModLoaded("Botania"))
			BotaniaCompat.postInit();
	}
	
	
	public void postInit(){}
	
	public void registerCompatTESR(String type){}

}
