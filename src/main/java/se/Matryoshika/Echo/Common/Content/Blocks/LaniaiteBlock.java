package se.Matryoshika.Echo.Common.Content.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import se.Matryoshika.Echo.Echo;

public class LaniaiteBlock extends Block{

	public LaniaiteBlock() {
		super(Material.IRON);
		setRegistryName(Echo.MODID, "laniaite_block");
		setUnlocalizedName(getRegistryName().toString());
	}

}
