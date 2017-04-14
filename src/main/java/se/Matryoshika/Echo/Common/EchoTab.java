package se.Matryoshika.Echo.Common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

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

}
