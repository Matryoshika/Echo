package se.Matryoshika.Echo.Client;

import net.minecraft.util.ResourceLocation;
import se.Matryoshika.Echo.Common.CommonProxy;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void createModels(String name, int tier, ResourceLocation tex){
		ModelCreator create = new ModelCreator(name, tier, tex);
	}

}
