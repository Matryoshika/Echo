package se.Matryoshika.Echo;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import se.Matryoshika.Echo.Common.CommonProxy;
import se.Matryoshika.Echo.Common.EchoTab;
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
    
    @Instance(MODID)
    public static Echo INSTANCE;
    
    @SidedProxy(clientSide = "se.Matryoshika.Echo.Client.ClientProxy", serverSide = "se.Matryoshika.Echo.Common.CommonProxy")
    public static CommonProxy proxy;
    
    public static final EchoTab TAB = new EchoTab("Echo"){
    	
    };
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    }
}
