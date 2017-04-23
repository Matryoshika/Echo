package se.Matryoshika.Echo.Client;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import se.Matryoshika.Echo.Client.Models.BakedMengerOneModel;
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;

public class ModelBakeEventHandler {
	
	@SubscribeEvent
	public void onModelBaking(ModelBakeEvent event){
		
		//FMLLog.bigWarning("Adding new BakedMengerModel", new Object[0]);
		BakedMengerOneModel custom = new BakedMengerOneModel();
		
		//event.getModelRegistry().putObject(BakedMengerOneModel.TAG, custom);
	}
}
