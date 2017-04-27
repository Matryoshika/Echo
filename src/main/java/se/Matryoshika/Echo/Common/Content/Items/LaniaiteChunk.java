package se.Matryoshika.Echo.Common.Content.Items;

import net.minecraft.item.Item;
import se.Matryoshika.Echo.Echo;

public class LaniaiteChunk extends Item{
	
	public LaniaiteChunk(){
		setRegistryName(Echo.MODID, "laniaite_chunk");
		setUnlocalizedName(getRegistryName().toString());
	}

}
