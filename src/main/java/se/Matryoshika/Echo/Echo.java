package se.Matryoshika.Echo;

import java.io.File;
import java.net.URISyntaxException;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import se.Matryoshika.Echo.Client.GUI.GUIHandler;
import se.Matryoshika.Echo.Common.BlockStateGetter;
import se.Matryoshika.Echo.Common.CommonProxy;
import se.Matryoshika.Echo.Common.EchoTab;
import se.Matryoshika.Echo.Common.LootTables;
import se.Matryoshika.Echo.Common.Content.Recipes.RecipeHandler;
import se.Matryoshika.Echo.Common.Content.Tile.TileLaniaiteFabricator;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;
import se.Matryoshika.Echo.Common.Content.Tile.TilePhaseSubstantiator;
import se.Matryoshika.Echo.Common.Content.Tile.TileTemporalDilation;

@Mod(modid = Echo.MODID, version = Echo.VERSION, name = "Echo", clientSideOnly = false, serverSideOnly = false, dependencies = "after:*")
public class Echo {

	public static final String MODID = "echo";
	public static final String VERSION = "1.0";
	public static final String NAME = "Echo";

	@Instance(MODID)
	public static Echo INSTANCE;

	@SidedProxy(clientSide = "se.Matryoshika.Echo.Client.ClientProxy", serverSide = "se.Matryoshika.Echo.Common.CommonProxy")
	public static CommonProxy proxy;

	public static final EchoTab TAB = new EchoTab("Echo") {

	};
	private static String directory;
	private static File configDir;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configDir = new File(event.getModConfigurationDirectory(), NAME);
		configDir.mkdir();

		GameRegistry.registerTileEntity(TileMenger.class, "echo:tile_menger");
		GameRegistry.registerTileEntity(TilePhaseSubstantiator.class, "echo:phase_substantiator");
		GameRegistry.registerTileEntity(TileTemporalDilation.class, "echo:temporal_dilator");
		GameRegistry.registerTileEntity(TileLaniaiteFabricator.class, "echo:laniaite_fabricator");


		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());

		proxy.init(event);

		RecipeHandler.init();

		MinecraftForge.EVENT_BUS.register(new LootTables());
		MinecraftForge.EVENT_BUS.register(new se.Matryoshika.Echo.Common.Events.EventHandler());

	}

	@EventHandler
    public void post(FMLPostInitializationEvent event){
    }

	@EventHandler
	public void postInit(FMLServerStartingEvent event) {
		event.registerServerCommand(new BlockStateGetter());

	}

	public static String getDirectory() {
		String path = "";
		try {
			path = Echo.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			path = path.replace("Echo.class", "");
			path = path.replace("bin/se/Matryoshika/Echo/", "");
		} catch (URISyntaxException e) {
		}
		return path;
	}

	public static File getConfigFolder() {
		return configDir;
	}
}
