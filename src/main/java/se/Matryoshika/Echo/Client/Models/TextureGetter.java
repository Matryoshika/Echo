package se.Matryoshika.Echo.Client.Models;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class TextureGetter {
	
	public static TextureAtlasSprite getTextureFromBlock(Block block, int meta) {
	    IBlockState state = block.getStateFromMeta(meta);
	    return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
	  }

	  public static TextureAtlasSprite getTextureFromBlockstate(IBlockState state) {
	    return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
	}

}
