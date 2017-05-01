package se.Matryoshika.Echo.Common.Content.Items;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.items.ItemHandlerHelper;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.LootTables;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;
import se.Matryoshika.Echo.Common.Utils.BlockStateJSON;
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
