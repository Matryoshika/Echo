package se.Matryoshika.Echo.Client.Models;

import java.util.Collection;

import com.google.common.base.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class MengerModel implements IModel{

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return null;
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return null;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModelState getDefaultState() {
		return ModelRotation.X0_Y0;
	}

}
