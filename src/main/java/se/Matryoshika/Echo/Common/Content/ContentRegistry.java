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
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;

@EventBusSubscriber
public class ContentRegistry {
	
	public static Block TERRA_1_BLOCK;
	
	public static List<Block>  blockList= new ArrayList<Block>();
	
	public static void prepareBlocks(){
		System.out.println(vazkii.botania.common.block.ModBlocks.storage);
		/*
		if(Loader.isModLoaded("Botania")){
			try {
				blockList.add(TERRA_1_BLOCK = new CompressedBlock(vazkii.botania.common.block.ModBlocks.storage.getDefaultState().
						withProperty(vazkii.botania.api.state.BotaniaStateProps.STORAGE_VARIANT, vazkii.botania.api.state.enums.StorageVariant.TERRASTEEL)));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		*/
		
		try {
			blockList.add(TERRA_1_BLOCK = new CompressedBlock(Blocks.EMERALD_BLOCK.getDefaultState(), 2));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

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
			ItemBlock iBlock = new ItemBlock(block);
			iBlock.setRegistryName(block.getRegistryName());
			event.getRegistry().register(iBlock);
		}
	}

}
