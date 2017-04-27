package se.Matryoshika.Echo.Common.Content.Tile;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Maps;
import com.google.gson.Gson;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaDoubleChestItemHandler;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Blocks.LaniaiteFabricator;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class TileLaniaiteFabricator extends TileEntity implements ITickable{
	
	
	private int tick;

	@Override
	public void update() {
		tick++;
		
		if(tick >= 20*60){
			tick = 0;
			
			
			IItemHandler inv = getItemHandler();
			if(inv != null)
				for(int i = 0; i < inv.getSlots(); i++){
					if(inv.getStackInSlot(i) == null || (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() == ContentRegistry.LANIAITE_FRAGMENT && inv.getStackInSlot(i).stackSize < 64)){
						inv.insertItem(i, new ItemStack(ContentRegistry.LANIAITE_FRAGMENT), false);
						break;
					}
				}
		}
		
		
	}
	
	
	private IItemHandler getItemHandler(){
		
		for(EnumFacing side : EnumFacing.values()){
			TileEntity te = worldObj.getTileEntity(pos.offset(side));
			if(te == null)
				continue;
			
			if(te instanceof TileEntityChest){
				
				IItemHandler doubleChest = VanillaDoubleChestItemHandler.get((TileEntityChest) te);
				if(doubleChest != VanillaDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE){
					IBlockState state = ContentRegistry.LANIAITE_FABRICATOR.getDefaultState().withProperty(LaniaiteFabricator.FACING, side.getOpposite());
					worldObj.setBlockState(getPos(), state);
					//System.out.println(state);
					return doubleChest;
				}
			}
			
			IItemHandler ret = te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) ? te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) : null;
		
			if(ret == null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
				ret = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				
			
			
			IBlockState state = ContentRegistry.LANIAITE_FABRICATOR.getDefaultState().withProperty(LaniaiteFabricator.FACING, side.getOpposite());
			worldObj.setBlockState(getPos(), state);
			
			return ret;
		}
		
		return null;
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate){
        return oldState.getBlock() != newSate.getBlock();
    }

}
