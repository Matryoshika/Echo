package se.Matryoshika.Echo.Common.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;

public class BlockStatesToTabs {
	
	public static List<IBlockState> states = new ArrayList<IBlockState>();
	public static final ArrayList<Block> blacklist = new ArrayList<Block>(Arrays.asList(new Block[]{ContentRegistry.VOID, ContentRegistry.COMPRESSED_BLOCK}));
	
	public static List<IBlockState> ores = new ArrayList<IBlockState>();
	
	public static void init(){
		BlockFlag:
			for(Block block : ForgeRegistries.BLOCKS){
				List<IBlockState> used = new ArrayList<>();
				for(Block nope : blacklist)
					if(block.getRegistryName().toString().equals(nope.getRegistryName().toString()))
						continue BlockFlag;
				
				for(IBlockState state : block.getBlockState().getValidStates()){
					if(state.isFullBlock() && block.getBlockLayer() == BlockRenderLayer.SOLID && !used.contains(state)){
						states.add(state);
						used.add(state);
						if(block instanceof BlockOre)
							ores.add(state);
					}
				}
			}
	}
	
	
	public static List<IBlockState> getStates(){
		
		if(!states.isEmpty())
			return states;
		
		init();
		return states;
	}
}
