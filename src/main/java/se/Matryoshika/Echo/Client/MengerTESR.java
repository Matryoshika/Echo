package se.Matryoshika.Echo.Client;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Client.Models.BakedMengerOneModel;
import se.Matryoshika.Echo.Client.Models.MengerCache;
import se.Matryoshika.Echo.Client.Models.TSRSBakedMenger;
import se.Matryoshika.Echo.Client.Models.TextureGetter;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;

public class MengerTESR extends TileEntitySpecialRenderer<TileMenger>{
	
	private IModel model;
	private IBakedModel bakedModel;
	private BakedMengerOneModel scaled;
	
	private IBakedModel getBakedModel(IBlockState state) {
		 
        if (bakedModel == null) {
            try {
                model = ModelLoaderRegistry.getModel(new ResourceLocation(Echo.MODID, "block/default_block_1.obj"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.BLOCK,
                    location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
        }
        return bakedModel;
	}
	
	public void renderTileEntityAt(TileMenger te, double x, double y, double z, float partialTicks, int destroyStage){

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		renderOriginalState(te, x, y, z, partialTicks, destroyStage);
    	
    	GlStateManager.translate(-x, -y, -z);
        GlStateManager.popMatrix();
	}
	
	private void renderOriginalState(TileMenger te, double x, double y, double z, float partialTicks, int destroyStage){
			scaled = new BakedMengerOneModel(Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(te.getOriginalState()), te.getOriginalState());
		
		
		RenderHelper.disableStandardItemLighting();
    	World world = getWorld();
        
    	GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
                world,
                scaled,
                world.getBlockState(te.getPos()),
                te.getPos(),
                Tessellator.getInstance().getBuffer(), false);
        tessellator.draw();
        GlStateManager.translate(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
        GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        
	}

}
