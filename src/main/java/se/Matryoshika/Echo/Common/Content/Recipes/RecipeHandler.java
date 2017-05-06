package se.Matryoshika.Echo.Common.Content.Recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import se.Matryoshika.Echo.Common.Compat.Botania.BotaniaCompat;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;
import se.Matryoshika.Echo.Common.Content.Items.ItemUpgrade;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class RecipeHandler {
	
	public static void init(){
		
		//##################################################################
		//Items
		
		GameRegistry.addShapedRecipe(new ItemStack(ContentRegistry.LANIAITE_CHUNK), 
				"FFF",
				"FFF",
				"FFF",
				'F', ContentRegistry.LANIAITE_FRAGMENT
				);
		
		GameRegistry.addShapelessRecipe(new ItemStack(ContentRegistry.LANIAITE_FRAGMENT, 9),
				new ItemStack(ContentRegistry.LANIAITE_CHUNK)
				);
		
		GameRegistry.addShapelessRecipe(new ItemStack(ContentRegistry.LANIAITE_CHUNK, 9),  
				new ItemStack(ContentRegistry.LANIAITE_BLOCK)
				);
		
		GameRegistry.addShapedRecipe(new ItemStack(ContentRegistry.COMPRESSION_WAND), 
				" F ",
				"FSF",
				"TF ",
				'F', ContentRegistry.LANIAITE_FRAGMENT,
				'S', Items.NETHER_STAR,
				'T', Items.STICK
				);
		
		
		//##################################################################
		//Blocks
		
		GameRegistry.addShapedRecipe(new ItemStack(ContentRegistry.LANIAITE_BLOCK), 
				"CCC",
				"CCC",
				"CCC",
				'C', ContentRegistry.LANIAITE_CHUNK
				);
		
		GameRegistry.addRecipe(new IRecipePhaseSubstantiator());
		GameRegistry.addRecipe(new IRecipeLaniaiteFabricator());
		GameRegistry.addRecipe(new IRecipeTemporalDilation());
		GameRegistry.addRecipe(new IRecipeUpgrades(CompressedBlock.get(Blocks.REDSTONE_BLOCK.getDefaultState(), (byte) 2), 
				new ItemStack(ContentRegistry.UPGRADE, 1, ItemUpgrade.Upgrade.REDSTONE.ordinal())));
		GameRegistry.addRecipe(new IRecipeUpgrades(CompressedBlock.get(Blocks.EMERALD_BLOCK.getDefaultState(), (byte) 2), 
				new ItemStack(ContentRegistry.UPGRADE, 1, ItemUpgrade.Upgrade.PLAYER_KILL.ordinal())));
		GameRegistry.addRecipe(new IRecipeUpgrades(new ItemStack(ContentRegistry.GUILLOTINE), 
				new ItemStack(ContentRegistry.UPGRADE, 1, ItemUpgrade.Upgrade.BEHEADING.ordinal())));
		GameRegistry.addRecipe(new IRecipeGuillotine());
		
		for(int i = 1; i <= 6; i++)
			GameRegistry.addRecipe(new IRecipeUpgrades(CompressedBlock.get(Blocks.GOLD_BLOCK.getDefaultState(), (byte) i), 
					new ItemStack(ContentRegistry.UPGRADE, 1, i)));
		
		
		if(BotaniaCompat.LANIAITE_POOL != null)
			GameRegistry.addRecipe(new IRecipeLaniaitePool());
		
		if(BotaniaCompat.LANIAITE_SPREADER != null)
			GameRegistry.addRecipe(new IRecipeLaniaiteSpreader());
		
		
	}

}
