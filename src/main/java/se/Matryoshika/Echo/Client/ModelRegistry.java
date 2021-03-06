package se.Matryoshika.Echo.Client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Client.Models.EchoModelLoader;
import se.Matryoshika.Echo.Client.Models.TESRTemporalDilation;
import se.Matryoshika.Echo.Client.Models.MengerTiers.BakedMengerOneModel;
import se.Matryoshika.Echo.Common.Compat.Botania.BotaniaCompat;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Items.ItemUpgrade;
import se.Matryoshika.Echo.Common.Content.Tile.TileTemporalDilation;

@Mod.EventBusSubscriber
public class ModelRegistry {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event){
			
		OBJLoader.INSTANCE.addDomain(Echo.MODID);
		
		
		
		
		StateMapperBase ignorer = new StateMapperBase(){
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state){
				return BakedMengerOneModel.TAG;
			}
		};
		ModelLoader.setCustomStateMapper(ContentRegistry.COMPRESSED_BLOCK, ignorer);
		
		for(Block block : ContentRegistry.blockList)
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
		
		for(Item item : ContentRegistry.itemList){
			if(item != ContentRegistry.UPGRADE)
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			else{
				for(int i = 0; i < ItemUpgrade.Upgrade.values().length; i++)
					ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), String.valueOf(i)));
			}
		}
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileTemporalDilation.class, new TESRTemporalDilation());
		
		if(BotaniaCompat.LANIAITE_POOL != null)
			Echo.proxy.registerCompatTESR("manapool");
		
		if(BotaniaCompat.LANIAITE_SPREADER != null)
			Echo.proxy.registerCompatTESR("spreader");
		
	}

}
