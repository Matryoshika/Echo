package se.Matryoshika.Echo.Common.Content;

import java.text.NumberFormat;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class ItemBlockMenger extends ItemBlock {

	public ItemBlockMenger() {
		super(ContentRegistry.COMPRESSED_BLOCK);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		final NBTTagCompound nbt = stack.getTagCompound();

		if (nbt != null) {
			final NBTTagCompound state = nbt.getCompoundTag(EchoConstants.NBT_BLOCKSTATE);
			if (state != null) {
				return "Menger Tier " + nbt.getByte(EchoConstants.NBT_TIER);
			}
		}

		return super.getItemStackDisplayName(stack);
	}
	
	@Override
	public void addInformation( final ItemStack stack, final EntityPlayer playerIn, final List<String> tooltip, final boolean advanced ){
		final NBTTagCompound nbt = stack.getTagCompound();

		if (nbt != null)
			tooltip.add(NumberFormat.getIntegerInstance().format((int)Math.pow(20, nbt.getByte(EchoConstants.NBT_TIER))) + "x " + (NBTUtil.func_190008_d(nbt.getCompoundTag(EchoConstants.NBT_BLOCKSTATE)).getBlock().getLocalizedName()));
	}

}
