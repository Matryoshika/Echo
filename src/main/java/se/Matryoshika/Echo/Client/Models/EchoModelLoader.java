package se.Matryoshika.Echo.Client.Models;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import se.Matryoshika.Echo.Echo;

public class EchoModelLoader implements ICustomModelLoader{

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		
	}

	@Override
	public boolean accepts(ResourceLocation loc) {
		return loc.getResourceDomain().equals(Echo.MODID) && !loc.getResourcePath().contains("phase");
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return new MengerIModel();
	}

}
