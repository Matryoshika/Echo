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
		return Item.getItemFromBlock(ContentRegistry.PHASE_SUBSTANTIATOR);
	}
	
	@Override
	public String getTranslatedTabLabel(){
		return getTabLabel();
	}
	
	@SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(List<ItemStack> items){
        super.displayAllRelevantItems(items);
        
        items.add(new ItemStack(ContentRegistry.COMPRESSION_WAND));
        items.add(new ItemStack(ContentRegistry.LANIAITE_FRAGMENT));
        items.add(new ItemStack(ContentRegistry.LANIAITE_CHUNK));
        items.add(new ItemStack(ContentRegistry.LANIAITE_BLOCK));
        
        items.add(new ItemStack(ContentRegistry.LANIAITE_FABRICATOR));
        items.add(new ItemStack(ContentRegistry.PHASE_SUBSTANTIATOR));
        items.add(new ItemStack(ContentRegistry.TEMPORAL_DILATOR));
        
        
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
