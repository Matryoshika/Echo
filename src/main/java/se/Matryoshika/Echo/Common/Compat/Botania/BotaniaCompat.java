package se.Matryoshika.Echo.Common.Compat.Botania;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import vazkii.botania.api.BotaniaAPI;

public class BotaniaCompat {
	
	public static Block LANIAITE_POOL;
	public static Block LANIAITE_SPREADER;
	
	public static List<Block> init(){
		
		BotaniaAPI.looniumBlacklist.add(ContentRegistry.LANIAITE_FRAGMENT);
		
		GameRegistry.registerTileEntity(TileLaniaitePool.class, "echo:laniaite_pool");
		GameRegistry.registerTileEntity(TileLaniaiteSpreader.class, "echo:laniaite_spreader");
		
		
		return Arrays.asList(new Block[]{LANIAITE_POOL = new LaniaiteManaPool(), LANIAITE_SPREADER = new LaniaiteSpreader()});
	}
	

}
