package se.Matryoshika.Echo.Client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.animation.FastTESR;
import se.Matryoshika.Echo.Client.Models.MengerCache;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class FastMenger extends FastTESR{

	@Override
	public void renderTileEntityFast(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, VertexBuffer buffer) {
		
		
		if ( MinecraftForgeClient.getRenderPass() != 0 ){
			return;
		}
		
		IBlockState state = ((TileMenger)te).getOriginalState();
		
		IBakedModel original = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state);
		
		List<BakedQuad> allQuads = MengerCache.getOrLoadModel(original, state, ((TileMenger)te).getTier());
		//System.out.println(allQuads);
		
		for(BakedQuad quad : MengerCache.getOrLoadModel(original, state, ((TileMenger)te).getTier())){
			buffer.addVertexData(quad.getVertexData());
			buffer.putPosition(x, y, z);
			
		}
		
		
	}

}
