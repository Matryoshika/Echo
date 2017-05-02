package se.Matryoshika.Echo.Common.Compat.JEI;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.crafting.IRecipe;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaiteFabricator;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaitePool;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaiteSpreader;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipePhaseSubstantiator;

public class EchoHandler implements IRecipeHandler<IRecipe>{

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
		
		if(recipe instanceof IRecipePhaseSubstantiator)
			return new PhaseSubstantiatorWrapper(recipe);
		
		if(recipe instanceof IRecipeLaniaiteFabricator)
			return new LaniaiteFabricatorWrapper(recipe);
		
		if(recipe instanceof IRecipeLaniaitePool)
			return new LaniaitePoolWrapper(recipe);
		
		if(recipe instanceof IRecipeLaniaiteSpreader)
			return new LaniaiteSpreaderWrapper(recipe);
		
		return null;
	}

	@Override
	public boolean isRecipeValid(IRecipe recipe) {
		if(recipe instanceof IRecipePhaseSubstantiator)
			return true;
		
		if(recipe instanceof IRecipeLaniaiteFabricator)
			return true;
		
		if(recipe instanceof IRecipeLaniaitePool)
			return true;
		
		if(recipe instanceof IRecipeLaniaiteSpreader)
			return true;
		
		return false;
	}

}
