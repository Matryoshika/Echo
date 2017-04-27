package se.Matryoshika.Echo.Client.Models.MengerTiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import static se.Matryoshika.Echo.Client.Models.EchoModelCache.*;

import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Client.Models.TSRSBakedMenger;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;

public class ItemBakedMenger implements IPerspectiveAwareModel {
	
	public final IBlockState state;
	public final byte tier;

	public ItemBakedMenger(IBlockState state, byte tier) {
		this.state = state;
		this.tier = tier;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		
		if(cached.get(tier) != null)
			if(cached.get(tier).get(this.state) != null)
				if(cached.get(tier).get(this.state).get(side) != null)
					return cached.get(tier).get(this.state).get(side);
		
		ArrayList<BakedQuad> allQuads = new ArrayList<BakedQuad>();
		if(side == null)
			return allQuads;
		
		switch(side){
		case DOWN:
			for(byte x = -1; x < 2; x++)
				for(byte z = -1; z < 2; z++){
					
					TSRSBakedMenger model = null;
					if(x == 0 && z == 0){
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(ContentRegistry.VOID.getDefaultState()), x * (1 / 3f), -1 * (1 / 3f), z * (1 / 3f), 1 / 3f);
						allQuads.addAll(model.getQuads(ContentRegistry.VOID.getDefaultState(), side, rand));
						continue;
					}
					if(this.tier != 1 && this.tier < (byte)6)
						 model = new TSRSBakedMenger(new ItemBakedMenger(this.state, (byte) (tier-1)),x * (1 / 3f), -1 * (1 / 3f), z * (1 / 3f), 1 / 3f);
					else
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(this.state), x * (1 / 3f), -1 * (1 / 3f), z * (1 / 3f), 1 / 3f);
					allQuads.addAll(model.getQuads(this.state, side, rand));
				}
			
			break;
		case EAST:
			for(byte z = -1; z < 2; z++)
				for(byte y = -1; y < 2; y++){
					TSRSBakedMenger model = null;
					if(z == 0 && y == 0){
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(ContentRegistry.VOID.getDefaultState()), 1 * (1 / 3f), y * (1 / 3f), z * (1 / 3f), 1 / 3f);
						allQuads.addAll(model.getQuads(ContentRegistry.VOID.getDefaultState(), side, rand));
						continue;
					}
					
					if(this.tier != 1 && this.tier < (byte)6)
						 model = new TSRSBakedMenger(new ItemBakedMenger(this.state, (byte) (tier-1)),1 * (1 / 3f), y * (1 / 3f), z * (1 / 3f), 1 / 3f);
					else
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(this.state), 1 * (1 / 3f), y * (1 / 3f), z * (1 / 3f), 1 / 3f);
					allQuads.addAll(model.getQuads(this.state, side, rand));
				}
			
			break;
		case NORTH:
			for(byte x = -1; x < 2; x++)
				for(byte y = -1; y < 2; y++){
					TSRSBakedMenger model = null;
					if(x == 0 && y == 0){
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(ContentRegistry.VOID.getDefaultState()), x * (1 / 3f), y * (1 / 3f), -1 * (1 / 3f), 1 / 3f);
						allQuads.addAll(model.getQuads(ContentRegistry.VOID.getDefaultState(), side, rand));
						continue;
					}
					
					if(this.tier != 1 && this.tier < (byte)6)
						 model = new TSRSBakedMenger(new ItemBakedMenger(this.state, (byte) (tier-1)),x * (1 / 3f), y * (1 / 3f), -1 * (1 / 3f), 1 / 3f);
					else
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(this.state), x * (1 / 3f), y * (1 / 3f), -1 * (1 / 3f), 1 / 3f);
					allQuads.addAll(model.getQuads(this.state, side, rand));
				}
			
			break;
		case SOUTH:
			for(byte x = -1; x < 2; x++)
				for(byte y = -1; y < 2; y++){
					TSRSBakedMenger model = null;
					if(x == 0 && y == 0){
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(ContentRegistry.VOID.getDefaultState()), x * (1 / 3f), y * (1 / 3f), 1 * (1 / 3f), 1 / 3f);
						allQuads.addAll(model.getQuads(ContentRegistry.VOID.getDefaultState(), side, rand));
						continue;
					}
					if(this.tier != 1 && this.tier < (byte)6)
						 model = new TSRSBakedMenger(new ItemBakedMenger(this.state, (byte) (tier-1)),x * (1 / 3f), y * (1 / 3f), 1 * (1 / 3f), 1 / 3f);
					else
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(this.state), x * (1 / 3f), y * (1 / 3f), 1 * (1 / 3f), 1 / 3f);
					allQuads.addAll(model.getQuads(this.state, side, rand));
				}
			
			break;
		case UP:
			for(byte x = -1; x < 2; x++)
				for(byte z = -1; z < 2; z++){
					TSRSBakedMenger model = null;
					if(x == 0 && z == 0){
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(ContentRegistry.VOID.getDefaultState()), x * (1 / 3f), 1 * (1 / 3f), z * (1 / 3f), 1 / 3f);
						allQuads.addAll(model.getQuads(ContentRegistry.VOID.getDefaultState(), side, rand));
						continue;
					}
					if(this.tier != 1 && this.tier < (byte)6)
						 model = new TSRSBakedMenger(new ItemBakedMenger(this.state, (byte) (tier-1)),x * (1 / 3f), 1 * (1 / 3f), z * (1 / 3f), 1 / 3f);
					else
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(this.state), x * (1 / 3f), 1 * (1 / 3f), z * (1 / 3f), 1 / 3f);
					allQuads.addAll(model.getQuads(this.state, side, rand));
				}
			
			break;
		case WEST:
			for(byte z = -1; z < 2; z++)
				for(byte y = -1; y < 2; y++){
					TSRSBakedMenger model = null;
					if(z == 0 && y == 0){
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(ContentRegistry.VOID.getDefaultState()), -1 * (1 / 3f), y * (1 / 3f), z * (1 / 3f), 1 / 3f);
						allQuads.addAll(model.getQuads(ContentRegistry.VOID.getDefaultState(), side, rand));
						continue;
					}
					if(this.tier != 1 && this.tier < (byte)6)
						 model = new TSRSBakedMenger(new ItemBakedMenger(this.state, (byte) (tier-1)), -1 * (1 / 3f), y * (1 / 3f), z * (1 / 3f), 1 / 3f);
					else
						model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(this.state), -1 * (1 / 3f), y * (1 / 3f), z * (1 / 3f), 1 / 3f);
					allQuads.addAll(model.getQuads(this.state, side, rand));
				}
			
			break;
		default:
			break;
		
		}
		/*
			for (float dx = -1; dx < 2; dx++) {
				for (float dy = -1; dy < 2; dy++) {
					for (float dz = -1; dz < 2; dz++) {
	
						if ((dx == 0 && dz == 0) || (dx == 0 && dy == 0) || (dy == 0 && dz == 0))
							continue;
						
						if((dy != 1 && side == EnumFacing.UP) || (dy != -1 && side == EnumFacing.DOWN) || 
							(dx != 1 && side == EnumFacing.EAST) || (dx != -1 && side == EnumFacing.WEST) || 
							(dz != 1 && side == EnumFacing.SOUTH) || (dz != -1 && side == EnumFacing.NORTH))
							continue;
						
						TSRSBakedMenger model = null;
						if(this.tier != 1 && this.tier < (byte)7)
							 model = new TSRSBakedMenger(new ItemBakedMenger(this.state, (byte) (tier-1)),dx * (1 / 3f), dy * (1 / 3f), dz * (1 / 3f), 1 / 3f);
						else
							model = new TSRSBakedMenger(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(this.state), dx * (1 / 3f), dy * (1 / 3f), dz * (1 / 3f), 1 / 3f);
						allQuads.addAll(model.getQuads(this.state, side, rand));
					}
				}
			}
			
			*/
		if(cached.get(tier) == null)
			cached.put(tier,new HashMap<IBlockState,Map<EnumFacing,ArrayList<BakedQuad>>>());
		if(cached.get(tier).get(this.state) == null)
			cached.get(tier).put(this.state, new HashMap<EnumFacing,ArrayList<BakedQuad>>());
		
		cached.get(tier).get(this.state).put(side, allQuads);
		
		return cached.get(tier).get(this.state).get(side);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/trip_wire");
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return null;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		ItemCameraTransforms itemCameraTransforms = ItemCameraTransforms.DEFAULT;
		ItemTransformVec3f itemTransformVec3f = itemCameraTransforms.getTransform(cameraTransformType);
		TRSRTransformation tr = new TRSRTransformation(itemTransformVec3f);
		Matrix4f mat = null;
		if (tr != null) {
			mat = tr.getMatrix();
		}

		return Pair.of(this, mat);
	}

}
