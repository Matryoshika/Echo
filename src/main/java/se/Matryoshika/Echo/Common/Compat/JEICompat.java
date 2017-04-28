package se.Matryoshika.Echo.Common.Compat;

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
import se.Matryoshika.Echo.Common.Compat.JEI.LaniaiteFabricator.LaniaiteFabricatorHandler;
import se.Matryoshika.Echo.Common.Compat.JEI.PhaseSubstantiator.EchoHandler;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipeLaniaiteFabricator;
import se.Matryoshika.Echo.Common.Content.Recipes.IRecipePhaseSubstantiator;
import se.Matryoshika.Echo.Common.Utils.BlockStateJSON;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

@JEIPlugin
public class JEICompat implements IModPlugin{
	
	public static IJeiHelpers helper = null;

	@Override
	public void registerItemSubtypes(ISubtypeRegistry registry) {
		
		registry.useNbtForSubtypes(new Item[]{Item.getItemFromBlock(ContentRegistry.COMPRESSED_BLOCK)});
		
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
		
	}

	@Override
	public void register(IModRegistry registry) {
		
		helper = registry.getJeiHelpers();
		
		registry.addRecipeHandlers(new EchoHandler());
		registry.addRecipes(ImmutableList.of(new IRecipe[]{new IRecipeLaniaiteFabricator(), new IRecipePhaseSubstantiator()}));
		
		
		helper.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ContentRegistry.VOID));
		
		ItemStack basic = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
		basic.setTagCompound(new NBTTagCompound());
		helper.getIngredientBlacklist().addIngredientToBlacklist(basic);
		
		List<ItemStack> unhiders = new ArrayList<ItemStack>();
		for(IBlockState state : BlockStateJSON.getAllowedStates()){
        	
        	for(byte i = 1; i <= BlockStateJSON.getTiers(state); i++){
        		ItemStack stack = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
            	NBTTagCompound nbt = new NBTTagCompound();
            	nbt.setTag(EchoConstants.NBT_BLOCKSTATE, NBTUtil.func_190009_a(new NBTTagCompound(), state));
            	nbt.setByte(EchoConstants.NBT_TIER, i);
            	stack.setTagCompound(nbt);
            	unhiders.add(stack);
        	}
        	
        }
		
		for(ItemStack stack : unhiders)
			helper.getIngredientBlacklist().removeIngredientFromBlacklist(stack);
	
		
		
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		
	}

}
