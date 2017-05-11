package se.Matryoshika.Echo.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import se.Matryoshika.Echo.Client.Models.EchoModelCache;
import se.Matryoshika.Echo.Client.Models.EchoModelLoader;
import se.Matryoshika.Echo.Common.CommonProxy;
import se.Matryoshika.Echo.Common.Compat.Botania.TESRPool;
import se.Matryoshika.Echo.Common.Compat.Botania.TESRSpreader;
import se.Matryoshika.Echo.Common.Compat.Botania.TileLaniaitePool;
import se.Matryoshika.Echo.Common.Compat.Botania.TileLaniaiteSpreader;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.BlockStatesToTabs;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void preInit(FMLPreInitializationEvent event){
		((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new EchoModelCache());
		ModelLoaderRegistry.registerLoader(new EchoModelLoader());
	}
	
	@Override
	public void createModels(String name, int tier, ResourceLocation tex){
		//ModelCreator create = new ModelCreator(name, tier, tex);
	}
	
	@Override
	public void init(FMLInitializationEvent event){
		ItemColourHandler.init();
		BlockStatesToTabs.init();
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
