package se.Matryoshika.Echo.Common.Content.Items;

import net.minecraft.item.Item;
import se.Matryoshika.Echo.Echo;

public class LaniaiteFragment extends Item{
	
	public LaniaiteFragment(){
		setRegistryName(Echo.MODID, "laniaite_fragment");
		setUnlocalizedName(getRegistryName().toString());
		setCreativeTab(Echo.TAB);
	}

}
