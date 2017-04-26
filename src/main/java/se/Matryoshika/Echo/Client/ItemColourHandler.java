package se.Matryoshika.Echo.Client;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;

public class ItemColourHandler {
	
	public static void init(){
		ItemColors items = Minecraft.getMinecraft().getItemColors();
		
		items.registerItemColorHandler(new RainbowColourHandler(), ContentRegistry.COMPRESSION_WAND);
		
		
	}
	
	public static class RainbowColourHandler implements IItemColor{

		@Override
		public int getColorFromItemstack(ItemStack stack, int tintIndex) {
			return Color.getHSBColor((((System.currentTimeMillis()/20) % 256) / 256f), 0.75f, 1f).getRGB();
		}
		
	}

}
