package se.Matryoshika.Echo.Common.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;

public class BlockStatesToTabs {
	
	public static List<IBlockState> states = new ArrayList<IBlockState>();
	public static final ArrayList<Block> blacklist = new ArrayList<Block>(Arrays.asList(new Block[]{ContentRegistry.VOID, ContentRegistry.COMPRESSED_BLOCK}));
	
	
	public static void init(){
		BlockFlag:
			for(Block block : ForgeRegistries.BLOCKS){
				/*ArrayList<IBlockState> sstates = new ArrayList<IBlockState>(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getBlockStateMapper().getVariants(block).keySet());
				for(IBlockState state : sstates){
					if(!state.isFullBlock())
						continue;
						*/
				for(Block nope : blacklist)
					if(block.getRegistryName().toString().equals(nope.getRegistryName().toString()))
						continue BlockFlag;
				
				if(block.getDefaultState().isFullBlock() && block.getBlockLayer() == BlockRenderLayer.SOLID)
					states.add(block.getDefaultState());
				
			}
	}
}
