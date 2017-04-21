package se.Matryoshika.Echo.Common;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.BlockStateJSON;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class EchoTab extends CreativeTabs{
	
	public EchoTab(String label){
		super(label);
	}
	
	@Override
	public Item getTabIconItem(){
		return Item.getItemFromBlock(Blocks.BEDROCK);
	}
	
	@Override
	public String getTranslatedTabLabel(){
		return getTabLabel();
	}
	
	@SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(List<ItemStack> items){
        super.displayAllRelevantItems(items);
        
        for(IBlockState state : BlockStateJSON.getAllowedStates()){
        	
        	for(byte i = 1; i <= BlockStateJSON.getTiers(state); i++){
        		ItemStack stack = new ItemStack(ContentRegistry.COMPRESSED_BLOCK);
            	NBTTagCompound nbt = new NBTTagCompound();
            	nbt.setTag(EchoConstants.NBT_BLOCKSTATE, NBTUtil.func_190009_a(new NBTTagCompound(), state));
            	nbt.setByte(EchoConstants.NBT_TIER, i);
            	stack.setTagCompound(nbt);
            	items.add(stack);
        	}
        	
        }
    }

}
