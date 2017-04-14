package se.Matryoshika.Echo.Common.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class MRLSuggestion {
	
	public static String get(Block block){
		return block.getRegistryName()+"#inventory";
	}
	
	public static String get(Item item){
		return item.getRegistryName()+"#inventory";
	}
	
	public static String get(Block block, String variant){
		return block.getRegistryName()+"#"+variant;
	}
	
	public static String get(Item item, String variant){
		return item.getRegistryName()+"#"+variant;
	}
	
	

}
