package se.Matryoshika.Echo.Common.Content;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import se.Matryoshika.Echo.Echo;

public class ItemBlockGuillotine extends ItemBlock{

	public ItemBlockGuillotine() {
		super(ContentRegistry.GUILLOTINE);
		
	}
	
	@Override
	public void addInformation( final ItemStack stack, final EntityPlayer playerIn, final List<String> tooltip, final boolean advanced ){
		String txt = I18n.format(Echo.MODID+":lore.block.guillotine");
		tooltip.add(TextFormatting.GRAY + txt);
	}

}
