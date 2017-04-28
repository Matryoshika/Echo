package se.Matryoshika.Echo.Common.Compat.JEI.LaniaiteFabricator;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.crafting.IRecipe;
import se.Matryoshika.Echo.Common.Compat.JEI.PhaseSubstantiator.PhaseSubstantiatorWrapper;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaiteFabricator;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipePhaseSubstantiator;

public class LaniaiteFabricatorHandler implements IRecipeHandler<IRecipe>{

	@Override
	public Class<IRecipe> getRecipeClass() {
		return IRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return VanillaRecipeCategoryUid.CRAFTING;
	}

	@Override
	public String getRecipeCategoryUid(IRecipe recipe) {
		return getRecipeCategoryUid();
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(IRecipe recipe) {
		
		if(recipe instanceof IRecipeLaniaiteFabricator)
			return new LaniaiteFabricatorWrapper(recipe);
		
		return null;
	}

	@Override
	public boolean isRecipeValid(IRecipe recipe) {
		if(recipe instanceof IRecipeLaniaiteFabricator)
			return true;
		
		return false;
	}

}
