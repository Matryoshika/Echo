package se.Matryoshika.Echo.Client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;

@Mod.EventBusSubscriber
public class ModelRegistry {
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event){
		OBJLoader.INSTANCE.addDomain(Echo.MODID);
		
		//StateMapperBase ignorer = new StateMapperBase(){
		//	@Override
		//	protected ModelResourceLocation getModelResourceLocation(IBlockState state){
		//		return BakedMenger.VARIANTTAG;
		//	}
		//};
		//ModelLoader.setCustomStateMapper(ContentRegistry.COMPRESSED_BLOCK, ignorer);
		
		for(Block block : ContentRegistry.blockList)
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileMenger.class, new FastMenger());
		
	}

}
