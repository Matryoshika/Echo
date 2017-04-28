package se.Matryoshika.Echo.Common.Content.Recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;

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
		
	}

}