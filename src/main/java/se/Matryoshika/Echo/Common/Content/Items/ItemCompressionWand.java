package se.Matryoshika.Echo.Common.Content.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;
import se.Matryoshika.Echo.Common.Utils.MultiblockFormation;

public class ItemCompressionWand extends Item{
	
	public ItemCompressionWand(){
		this.setRegistryName(Echo.MODID, "compression_wand");
		this.setUnlocalizedName(getRegistryName().toString());
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, 
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		
		if(world.getBlockState(pos).getBlock() == ContentRegistry.COMPRESSED_BLOCK){
			for(BlockPos _pos : BlockPos.getAllInBox(pos.add(-2, -2, -2), pos.add(2, 2, 2)))
				if(world.getTileEntity(_pos) instanceof TileMenger)
					((TileMenger)world.getTileEntity(_pos)).updateMultiblock();
			
			return EnumActionResult.PASS;
		}
			
		
		MultiblockFormation form = new MultiblockFormation();
		for(BlockPos _pos : BlockPos.getAllInBox(pos.add(2, 2, 2), pos.add(-2, -2, -2)))
			form.callForEachIn(world, _pos);
		
        return EnumActionResult.PASS;
    }

}
