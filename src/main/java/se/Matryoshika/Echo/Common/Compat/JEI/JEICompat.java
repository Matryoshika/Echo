package se.Matryoshika.Echo.Common.Compat.JEI;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;

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
