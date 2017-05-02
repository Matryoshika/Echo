package se.Matryoshika.Echo.Common.Compat.JEI;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import se.Matryoshika.Echo.Common.Compat.Botania.BotaniaCompat;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaiteFabricator;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaitePool;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaiteSpreader;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipePhaseSubstantiator;
import se.Matryoshika.Echo.Common.Utils.BlockStateJSON;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

@JEIPlugin
public class JEICompat implements IModPlugin {

	public static IJeiHelpers helper = null;

	@Override
	public void registerItemSubtypes(ISubtypeRegistry registry) {

		registry.useNbtForSubtypes(Item.getItemFromBlock(ContentRegistry.COMPRESSED_BLOCK));

	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {

	}

	@Override
	public void register(IModRegistry registry) {

		helper = registry.getJeiHelpers();
		registry.addRecipeHandlers(new EchoHandler());
		helper.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ContentRegistry.VOID));

	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

	}

}
