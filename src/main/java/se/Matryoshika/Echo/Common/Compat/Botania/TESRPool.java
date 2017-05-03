package se.Matryoshika.Echo.Common.Compat.Botania;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import vazkii.botania.api.mana.IPoolOverlayProvider;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.PoolVariant;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.handler.MiscellaneousIcons;
import vazkii.botania.client.core.handler.MultiblockRenderHandler;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.client.core.proxy.ClientProxy;
import vazkii.botania.common.block.tile.mana.TilePool;

public class TESRPool extends TileEntitySpecialRenderer<TileLaniaitePool> {

	// Overrides for when we call this TESR without an actual pool
	public static PoolVariant forceVariant = PoolVariant.DEFAULT;
	public static int forceManaNumber = -1;

	@Override
	public void renderTileEntityAt(TileLaniaitePool pool, double d0, double d1, double d2, float f, int digProgress) {
		if (pool != null && (!pool.getWorld().isBlockLoaded(pool.getPos(), false)
				|| pool.getWorld().getBlockState(pool.getPos()).getBlock() != BotaniaCompat.LANIAITE_POOL))
			return;
		

		//

		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableRescaleNormal();
		float a = MultiblockRenderHandler.rendering ? 0.6F : 1F;

		GlStateManager.color(1F, 1F, 1F, a);
		if (pool == null) { // A null pool means we are calling the TESR without
							// a pool (on a minecart). Adjust accordingly
			GlStateManager.translate(0, 0, -1);
		} else {
			GlStateManager.translate(d0, d1, d2);
		}

		boolean fab = false;

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		int color = 0xFF1111;

		GlStateManager.translate(0.5F, 1.5F, 0.5F);
		GlStateManager.color(1f, 0.5f, 0.5f, a);
		GlStateManager.enableRescaleNormal();

		int mana = pool.getCurrentMana();
		int cap = pool.manaCap;
		if (cap == -1)
			cap = TilePool.MAX_MANA;

		float waterLevel = (float) mana / (float) cap * 0.42F;

		float s = 1F / 16F;
		float v = 1F / 8F;
		float w = -v * 3.5F;

		if (pool != null) {
			Block below = pool.getWorld().getBlockState(pool.getPos().down()).getBlock();
			if (below instanceof IPoolOverlayProvider) {
				TextureAtlasSprite overlay = ((IPoolOverlayProvider) below).getIcon(pool.getWorld(), pool.getPos());
				if (overlay != null) {
					GlStateManager.pushMatrix();
					GlStateManager.enableBlend();
					GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GlStateManager.disableAlpha();
					GlStateManager.color(1F, 0F, 0F, a * (float) ((Math.sin((ClientTickHandler.ticksInGame + f) / 20.0) + 1) * 0.3 + 0.2));
					GlStateManager.translate(-0.5F, -1F - 0.43F, -0.5F);
					GlStateManager.rotate(90F, 1F, 0F, 0F);
					GlStateManager.scale(s, s, s);

					renderIcon(0, 0, overlay, 16, 16, 240);

					GlStateManager.enableAlpha();
					GlStateManager.disableBlend();
					GlStateManager.popMatrix();
				}
			}
		}

		if (waterLevel > 0) {
			s = 1F / 256F * 14F;
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.disableAlpha();
			GlStateManager.color(1F, 0F, 0F, a);
			GlStateManager.translate(w, -1F - (0.43F - waterLevel), w);
			GlStateManager.rotate(90F, 1F, 0F, 0F);
			GlStateManager.scale(s, s, s);

			ShaderHelper.useShader(ShaderHelper.manaPool);
			renderIcon(0, 0, MiscellaneousIcons.INSTANCE.manaWater, 16, 16, 240);
			ShaderHelper.releaseShader();

			GlStateManager.enableAlpha();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();

		forceVariant = PoolVariant.DEFAULT;
		forceManaNumber = -1;
	}

	public void renderIcon(int par1, int par2, TextureAtlasSprite par3Icon, int par4, int par5, int brightness) {
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		// tessellator.getBuffer().setBrightness(brightness);
		tessellator.getBuffer().pos(par1 + 0, par2 + par5, 0).tex(par3Icon.getMinU(), par3Icon.getMaxV()).endVertex();
		tessellator.getBuffer().pos(par1 + par4, par2 + par5, 0).tex(par3Icon.getMaxU(), par3Icon.getMaxV())
				.endVertex();
		tessellator.getBuffer().pos(par1 + par4, par2 + 0, 0).tex(par3Icon.getMaxU(), par3Icon.getMinV()).endVertex();
		tessellator.getBuffer().pos(par1 + 0, par2 + 0, 0).tex(par3Icon.getMinU(), par3Icon.getMinV()).endVertex();
		tessellator.draw();
	}

}
