package se.Matryoshika.Echo.Common.Content;

import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;
import se.Matryoshika.Echo.Common.Utils.BlockStateJSON;
import se.Matryoshika.Echo.Common.Utils.MultiblockFormation;

public class ItemCompressionWand extends Item{
	
	public ItemCompressionWand(){
		this.setRegistryName(Echo.MODID, "compression_wand");
		this.setUnlocalizedName(getRegistryName().toString());
		this.setMaxStackSize(1);
		this.setCreativeTab(Echo.TAB);
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, 
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		//FMLLog.bigWarning(NBTUtil.func_190009_a(new NBTTagCompound(), Blocks.REDSTONE_BLOCK.getDefaultState()).toString(), new Object[0]);
		
		//System.out.println(NBTUtil.func_190009_a(new NBTTagCompound(), world.getBlockState(pos)).toString());
		/*
		Map<String,Byte> map = BlockStateJSON.getAllowedStates();
		int i = 0;
			for(String key : map.keySet()){
				String raw = key.replaceAll("\\\\", "");
				NBTTagCompound nbt = new NBTTagCompound();
				try {
					nbt = (NBTTagCompound) JsonToNBT.getTagFromJson(raw);
				} catch (NBTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i >= 1){
					world.setBlockState(pos.up(), NBTUtil.func_190008_d(nbt));
					if(world.isRemote)
					world.playEvent(2001, pos, Block.getStateId(NBTUtil.func_190008_d(nbt)));
					break;
					}
				i++;
				
			}
			*/
			
		MultiblockFormation form = new MultiblockFormation();
		for(IBlockState state : BlockStateJSON.getAllowedStates()){
			if(world.getBlockState(pos).equals(state)){
				BlockPos mid = form.getTrueMiddle(world, pos, state);
				if(mid != null){
					for(BlockPos _pos : BlockPos.getAllInBox(mid.add(-1, -1, -1), mid.add(1, 1, 1))){
						if(world.getBlockState(_pos).equals(state)){
							world.setBlockToAir(_pos);
							world.setBlockState(mid, ContentRegistry.COMPRESSED_BLOCK.getDefaultState());
							world.setTileEntity(mid, new TileMenger(state, (byte) 1));
						}
					}
				}
			}
		}
		
		//world.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5, 0.0D, 0.0D, 0.0D, new int[0]);
		//System.out.println(pos);
		
		
        return EnumActionResult.PASS;
    }

}
