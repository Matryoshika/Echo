package se.Matryoshika.Echo.Client.Models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.FMLLog;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class BakedMengerOneModel implements IBakedModel{
	
	public final static ModelResourceLocation TAG = new ModelResourceLocation("echo:compressed_block", "normal");

	@Override
	public List<BakedQuad> getQuads(IBlockState someState, EnumFacing side, long rand) {
		
		FMLLog.bigWarning("Not an IExtendedState", new Object[0]);
		
		if(!(someState instanceof IExtendedBlockState))
			return placeHolder(someState, side, rand);
		
		FMLLog.bigWarning("Yes it is", new Object[0]);
		
		IBakedModel wanted = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.SNOW.getDefaultState());
		IExtendedBlockState state = (IExtendedBlockState) someState;
		IBlockState copy = state.getValue(CompressedBlock.IBS);
		
		
        Minecraft mc = Minecraft.getMinecraft();
        BlockRendererDispatcher blockRendererDispatcher = mc.getBlockRendererDispatcher();
        BlockModelShapes blockModelShapes = blockRendererDispatcher.getBlockModelShapes();
        IBakedModel copiedBlockModel = blockModelShapes.getModelForState(copy);
        wanted = copiedBlockModel;
		
		
		
		List<BakedQuad> allQuads = new ArrayList<BakedQuad>();
		
		/*
		for(float dx = -1; dx < 2; dx++){
        	for(float dy = -1; dy < 2; dy++){
        		for(float dz = -1; dz < 2; dz++){
        			
        			if((dx == 0 && dz == 0) || (dx == 0 && dy == 0) || (dy == 0 && dz == 0))
        				continue;
        			
        			TSRSBakedMenger model = new TSRSBakedMenger(wanted, dx*(1/3f), dy*(1/3f), dz*(1/3f), 1/3f);
        			allQuads.addAll(model.getQuads(state, side, rand));
        		}
        	}
		}
		*/
		
		allQuads.addAll(wanted.getQuads(copy, side, rand));
		return allQuads;
	}
	
	public List<BakedQuad> placeHolder(IBlockState someState, EnumFacing side, long rand) {
		return Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.COBBLESTONE.getDefaultState()).getQuads(someState, side, rand);
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
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/dirt");
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
		public IBakedModel handleItemState(@Nonnull IBakedModel model, ItemStack stack, World world, EntityLivingBase entity) {
			
			if(!stack.hasTagCompound())
				return Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.COBBLESTONE.getDefaultState());
			
			IBlockState state = NBTUtil.func_190008_d(stack.getTagCompound().getCompoundTag(EchoConstants.NBT_BLOCKSTATE));
			return Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state);
		}
	};

}
