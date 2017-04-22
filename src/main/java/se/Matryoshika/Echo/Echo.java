package se.Matryoshika.Echo;

import java.io.File;
import java.net.URISyntaxException;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import se.Matryoshika.Echo.Common.BlockStateGetter;
import se.Matryoshika.Echo.Common.CommonProxy;
import se.Matryoshika.Echo.Common.EchoTab;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;
import se.Matryoshika.Echo.Common.Packets.EchoPacketHandler;
import se.Matryoshika.Echo.Common.Utils.BlockStateJSON;
import se.Matryoshika.Echo.Common.Utils.MRLSuggestion;

@Mod(
	modid = Echo.MODID, 
	version = Echo.VERSION,
	name = "Echo",
	clientSideOnly = false,
	serverSideOnly = false,
	dependencies = "after:*"
)
public class Echo{
	
    public static final String MODID = "echo";
    public static final String VERSION = "1.0";
    public static final String NAME = "Echo";
    
    @Instance(MODID)
    public static Echo INSTANCE;
    
    @SidedProxy(clientSide = "se.Matryoshika.Echo.Client.ClientProxy", serverSide = "se.Matryoshika.Echo.Common.CommonProxy")
    public static CommonProxy proxy;
    
    public static final EchoTab TAB = new EchoTab("Echo"){
    	
    };
    private static String directory;
    private static File configDir;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	configDir = new File(event.getModConfigurationDirectory(), NAME);
    	configDir.mkdir();
    	
    	GameRegistry.registerTileEntity(TileMenger.class, "echo:tile_menger");
    	EchoPacketHandler.registerEchoPacketHandler();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	FMLLog.bigWarning(NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.DIAMOND_BLOCK.getDefaultState()).toString(), new Object[0]);
    	
    	BlockStateJSON.createJSON();
    	
    	
    	
    }
    
    @EventHandler
    public void postInit(FMLServerStartingEvent event){
    	event.registerServerCommand(new BlockStateGetter());
    }
    
    public static String getDirectory(){
    	String path = "";
    	try {
			path = Echo.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			path = path.replace("Echo.class", "");
			path = path.replace("bin/se/Matryoshika/Echo/", "");
		} catch (URISyntaxException e) {
		}
    	return path;
    }
    
    public static File getConfigFolder(){
    	return configDir;
    }
}
