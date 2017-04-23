package se.Matryoshika.Echo.Client.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public class MengerCache implements IResourceManagerReloadListener {

	private static final Map<Byte,Map<IBlockState, List<BakedQuad>>> bakedCache = new HashMap<>();

	public static List<BakedQuad> getOrLoadModel(IBakedModel origin, IBlockState state, byte tier){
		
		if(bakedCache.get(state) != null)
			return bakedCache.get(tier).get(state);
		
		List<BakedQuad> quads = new ArrayList<BakedQuad>();
		IBakedModel model = new BakedMengerOneModel(origin, state);
		
		quads.addAll(model.getQuads(state, null, 0L));
		for(EnumFacing facing : EnumFacing.values())
			quads.addAll(model.getQuads(state, facing, 0L));
		
		putModel(tier, state, quads);
		
		return bakedCache.get(tier).get(state);
		
	}
	
	public static void putModel(byte tier, IBlockState state, List<BakedQuad> quads){
		if(bakedCache.get(tier) == null)
			bakedCache.put(tier, new HashMap<IBlockState, List<BakedQuad>>());
		bakedCache.get(tier).put(state, quads);
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		bakedCache.clear();
	}

}
