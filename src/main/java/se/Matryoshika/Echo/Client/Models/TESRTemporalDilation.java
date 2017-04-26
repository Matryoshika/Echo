package se.Matryoshika.Echo.Client.Models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemMonsterPlacer;
import se.Matryoshika.Echo.Common.Content.Tile.TileTemporalDilation;

public class TESRTemporalDilation extends TileEntitySpecialRenderer<TileTemporalDilation>{
	
	public void renderTileEntityAt(TileTemporalDilation te, double x, double y, double z, float partialTicks, int destroyStage){
		if(te.entityHolder == null)
			return;
		
		Entity entity = EntityList.createEntityByIDFromName(ItemMonsterPlacer.getEntityIdFromItem(te.entityHolder), te.getWorld());
		GlStateManager.pushMatrix();
		GlStateManager.translate(x+0.5, y+0.20, z+0.5);
		GlStateManager.scale(0.3, 0.3, 0.3);
		GlStateManager.rotate((System.currentTimeMillis() / 40) % 360, 0.0F, 1.0F, 0.0F);
        entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
        GlStateManager.popMatrix();
	}

}
