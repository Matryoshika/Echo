package se.Matryoshika.Echo.Client.Models.MengerTiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;
import static se.Matryoshika.Echo.Client.Models.EchoModelCache.*;

import se.Matryoshika.Echo.Client.Models.TSRSBakedMenger;
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class BakedMengerOneModel implements IPerspectiveAwareModel {

	public final static ModelResourceLocation TAG = new ModelResourceLocation("echo:compressed_block", "normal");

	@Override
	public List<BakedQuad> getQuads(IBlockState someState, EnumFacing side, long rand) {

		if (!(someState instanceof IExtendedBlockState))
			return placeHolder(someState, side, rand);

		IBakedModel wanted = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.SNOW.getDefaultState());
		IExtendedBlockState state = (IExtendedBlockState) someState;
		IBlockState copy = state.getValue(CompressedBlock.IBS);
		byte tier = state.getValue(CompressedBlock.BIT);
		
		
		
		if(cached.get(tier) != null)
			if(cached.get(tier).get(copy) != null)
				if(cached.get(tier).get(copy).get(side) != null)
					return cached.get(tier).get(copy).get(side);
		

		Minecraft mc = Minecraft.getMinecraft();
		BlockRendererDispatcher blockRendererDispatcher = mc.getBlockRendererDispatcher();
		BlockModelShapes blockModelShapes = blockRendererDispatcher.getBlockModelShapes();
		IBakedModel copiedBlockModel = blockModelShapes.getModelForState(copy);
		wanted = copiedBlockModel;

		
		if(cached.get(tier) == null)
			cached.put(tier,new HashMap<IBlockState,Map<EnumFacing,ArrayList<BakedQuad>>>());
		if(cached.get(tier).get(copy) == null)
			cached.get(tier).put(copy, new HashMap<EnumFacing,ArrayList<BakedQuad>>());
		
		cached.get(tier).get(copy).put(side, (ArrayList<BakedQuad>) new ItemBakedMenger(copy, tier).getQuads(copy, side, rand));
		return cached.get(tier).get(copy).get(side);
		/*
		ArrayList<BakedQuad> allQuads = new ArrayList<BakedQuad>();

		for (float dx = -1; dx < 2; dx++) {
			for (float dy = -1; dy < 2; dy++) {
				for (float dz = -1; dz < 2; dz++) {

					if ((dx == 0 && dz == 0) || (dx == 0 && dy == 0) || (dy == 0 && dz == 0))
						continue;

					TSRSBakedMenger model = new TSRSBakedMenger(wanted, dx * (1 / 3f), dy * (1 / 3f), dz * (1 / 3f), 0, 0, 0,1 / 3f);
					allQuads.addAll(model.getQuads(state, side, rand));
				}
			}
		}
		//EchoModelCache.putBlockModel(state.getValue(CompressedBlock.BIT), state.getValue(CompressedBlock.IBS), allQuads);
		
		return allQuads;
		*/
	}

	public List<BakedQuad> placeHolder(IBlockState someState, EnumFacing side, long rand) {
		return Minecraft.getMinecraft().getBlockRendererDispatcher()
				.getModelForState(Blocks.COBBLESTONE.getDefaultState()).getQuads(someState, side, rand);
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
		return itemHandler;
	}

	private final ItemOverrideList itemHandler = new ItemOverrideList(ImmutableList.of()) {
		@Nonnull
		@Override
		public IBakedModel handleItemState(@Nonnull IBakedModel model, ItemStack stack, World world,
				EntityLivingBase entity) {

			if (!stack.hasTagCompound())
				return Minecraft.getMinecraft().getBlockRendererDispatcher()
						.getModelForState(Blocks.COBBLESTONE.getDefaultState());

			IBlockState state = NBTUtil
					.func_190008_d(stack.getTagCompound().getCompoundTag(EchoConstants.NBT_BLOCKSTATE));
			
			//System.out.println(stack.getTagCompound().getByte(EchoConstants.NBT_TIER));
			byte tier = stack.getTagCompound().getByte(EchoConstants.NBT_TIER);
			
			return Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state);
		}
	};
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType transformType) {
		return Pair.of(this, new TRSRTransformation(TRSRTransformation.identity().toItemTransform()).getMatrix());
	}

}
