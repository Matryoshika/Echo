package se.Matryoshika.Echo.Common.Content;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Enums;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import se.Matryoshika.Echo.Common.Content.Blocks.*;
import se.Matryoshika.Echo.Common.Content.Items.*;

@EventBusSubscriber
public class ContentRegistry {
	
	public static Block COMPRESSED_BLOCK;
	public static Block PHASE_SUBSTANTIATOR;
	public static Block TEMPORAL_DILATOR;
	public static Block LANIAITE_FABRICATOR;
	public static Block LANIAITE_BLOCK;
	
	public static Item COMPRESSION_WAND;
	public static Item LANIAITE_CHUNK;
	public static Item LANIAITE_FRAGMENT;
	
	public static List<Block> blockList = new ArrayList<Block>();
	public static List<Item> itemList = new ArrayList<Item>();
	
	public static void prepareBlocks(){
		blockList.add(COMPRESSED_BLOCK = new CompressedBlock());
		blockList.add(PHASE_SUBSTANTIATOR = new PhaseSubstantiatorBlock());
		blockList.add(TEMPORAL_DILATOR = new TemporalDilatorBlock());
		blockList.add(LANIAITE_FABRICATOR = new LaniaiteFabricator());
		blockList.add(LANIAITE_BLOCK = new LaniaiteBlock());
	}
	
	public static void prepareItems(){
		itemList.add(COMPRESSION_WAND = new ItemCompressionWand());
		itemList.add(LANIAITE_CHUNK = new LaniaiteChunk());
		itemList.add(LANIAITE_FRAGMENT = new LaniaiteFragment());
	}
	
	@SubscribeEvent
	public static void registerBlocks(Register<Block> event){
		prepareBlocks();
		
		for(Block block : blockList)
			event.getRegistry().register(block);
	}
	
	@SubscribeEvent
	public static void registerItems(Register<Item> event){
		for(Block block : blockList){
			ItemBlock iBlock = null;
			if(block == COMPRESSED_BLOCK)
				iBlock = new ItemBlockMenger();
			else
				iBlock = new ItemBlock(block);
			iBlock.setRegistryName(block.getRegistryName());
			event.getRegistry().register(iBlock);
		}
		
		prepareItems();
		for(Item item : itemList)
			event.getRegistry().register(item);
	}

}
