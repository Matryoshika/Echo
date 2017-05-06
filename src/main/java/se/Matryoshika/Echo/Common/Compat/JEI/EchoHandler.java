package se.Matryoshika.Echo.Common.Compat.JEI;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.crafting.IRecipe;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeGuillotine;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaiteFabricator;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaitePool;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaiteSpreader;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipePhaseSubstantiator;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeTemporalDilation;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeUpgrades;

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
		
		if(recipe instanceof IRecipeTemporalDilation)
			return new TemporalDilationWrapper(recipe);
		
		if(recipe instanceof IRecipeLaniaitePool)
			return new LaniaitePoolWrapper(recipe);
		
		if(recipe instanceof IRecipeLaniaiteSpreader)
			return new LaniaiteSpreaderWrapper(recipe);
		
		if(recipe instanceof IRecipeUpgrades)
			return new UpgradeWrapper((IRecipeUpgrades) recipe);
		
		if(recipe instanceof IRecipeGuillotine)
			return new GuillotineWrapper(recipe);
		
		return null;
	}

	@Override
	public boolean isRecipeValid(IRecipe recipe) {
		if(recipe instanceof IRecipePhaseSubstantiator)
			return true;
		
		if(recipe instanceof IRecipeLaniaiteFabricator)
			return true;
		
		if(recipe instanceof IRecipeTemporalDilation)
			return true;
		
		if(recipe instanceof IRecipeLaniaitePool)
			return true;
		
		if(recipe instanceof IRecipeLaniaiteSpreader)
			return true;
		
		if(recipe instanceof IRecipeUpgrades)
			return true;
		
		if(recipe instanceof IRecipeGuillotine)
			return true;
		
		return false;
	}

}
