package se.Matryoshika.Echo.Client.Models;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BakedMengerOneModel implements IBakedModel{
	
	protected final IBakedModel origin;
	protected final IBlockState state;
	
	public BakedMengerOneModel(IBakedModel origin, IBlockState state){
		this.origin = origin;
		this.state = state;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		
		List<BakedQuad> allQuads = new ArrayList<BakedQuad>();
		
		for(float dx = -1; dx < 2; dx++){
        	for(float dy = -1; dy < 2; dy++){
        		for(float dz = -1; dz < 2; dz++){
        			
        			if((dx == 0 && dz == 0) || (dx == 0 && dy == 0) || (dy == 0 && dz == 0))
        				continue;
        			
        			TSRSBakedMenger model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(this.state), dx*(1/3f), dy*(1/3f), dz*(1/3f), 1/3f);
        			allQuads.addAll(model.getQuads(this.state, side, rand));
        		}
        	}
		}
		
		return allQuads;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return origin.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return origin.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return origin.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return origin.getParticleTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return origin.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return null;
	}

}
