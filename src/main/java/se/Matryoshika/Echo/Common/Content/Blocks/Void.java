package se.Matryoshika.Echo.Common.Content.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import se.Matryoshika.Echo.Echo;

public class Void extends Block{

	public Void() {
		super(Material.DRAGON_EGG);
		setRegistryName(Echo.MODID, "void");
	}

}
