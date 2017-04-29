package se.Matryoshika.Echo.Common.Compat.Botania;

import java.awt.Color;
import java.util.Random;

import net.minecraft.block.BlockCarpet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import se.Matryoshika.Echo.Echo;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.proxy.ClientProxy;
import vazkii.botania.client.model.ModelSpreader;

public class TESRSpreader extends TileEntitySpecialRenderer<TileLaniaiteSpreader>{
	
	private static final ResourceLocation texture = new ResourceLocation(Echo.MODID, "textures/blocks/botania/spreader.png");
	
	private static final EchoSpreader model = new EchoSpreader();
	
	@Override
	public void renderTileEntityAt(TileLaniaiteSpreader spreader, double d0, double d1, double d2, float ticks, int digProgress) {
		if(!spreader.getWorld().isBlockLoaded(spreader.getPos(), false)
				|| spreader.getWorld().getBlockState(spreader.getPos()).getBlock() != BotaniaCompat.LANIAITE_SPREADER)
			return;

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(d0, d1, d2);

		GlStateManager.translate(0.5F, 1.5F, 0.5F);
		GlStateManager.rotate(spreader.rotationX + 90F, 0F, 1F, 0F);
		GlStateManager.translate(0F, -1F, 0F);
		GlStateManager.rotate(spreader.rotationY, 1F, 0F, 0F);
		GlStateManager.translate(0F, 1F, 0F);

		ResourceLocation r = texture;

		Minecraft.getMinecraft().renderEngine.bindTexture(r);
		GlStateManager.scale(1F, -1F, -1F);

		double time = ClientTickHandler.ticksInGame + ticks;
		
		model.render();
		GlStateManager.color(1F, 1F, 1F);

		GlStateManager.pushMatrix();
		double worldTicks = spreader.getWorld() == null ? 0 : time;
		GlStateManager.rotate((float) worldTicks % 360, 0F, 1F, 0F);
		GlStateManager.translate(0F, (float) Math.sin(worldTicks / 20.0) * 0.05F, 0F);
		model.renderCube();
		GlStateManager.popMatrix();
		GlStateManager.scale(1F, -1F, -1F);
		ItemStack stack = spreader.getItemHandler().getStackInSlot(0);

		if(stack != null) {
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			stack.getItem();
			GlStateManager.pushMatrix();
			GlStateManager.translate(-0.0F, -1F, -0.4375F);
			GlStateManager.scale(0.8F, 0.8F, 0.8F);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
			GlStateManager.popMatrix();
		}

		if(spreader.paddingColor != -1) {
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			IBlockState carpet = Blocks.CARPET.getDefaultState().withProperty(BlockCarpet.COLOR, EnumDyeColor.byMetadata(spreader.paddingColor));

			GlStateManager.translate(-0.5F, -0.5F, 0.5F);
			float f = 1 / 16F;

			GlStateManager.translate(0, -f - 0.001, 0);
			Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(carpet, 1.0F);
			GlStateManager.translate(0, f + 0.001, 0);
			GlStateManager.rotate(-90, 0, 1, 0);

			GlStateManager.translate(-0.001, 0, 0);
			GlStateManager.rotate(270, 0, 0, 1);
			Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(carpet, 1.0F);
			GlStateManager.translate(0, 0.001, 0);
			GlStateManager.rotate(-90, 0, 1, 0);

			GlStateManager.translate(0, 15 * f + 0.001, -0.001);
			Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(carpet, 1.0F);
			GlStateManager.translate(0, -0.001, 0.001);
			GlStateManager.rotate(-90, 0, 1, 0);

			GlStateManager.translate(15 * f + 0.001, f, 0.001);
			GlStateManager.rotate(270, 0, 0, 1);
			Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(carpet, 1.0F);
			GlStateManager.translate(-0.001, 0, -0.001);
			GlStateManager.rotate(-90, 0, 1, 0);

			GlStateManager.translate(-0.001, -1 + f + 0.001, -f + 0.001);
			GlStateManager.rotate(90, 1, 0, 0);
			Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(carpet, 1.0F);
		}

		GlStateManager.enableRescaleNormal();
		GlStateManager.popMatrix();
	}

}
