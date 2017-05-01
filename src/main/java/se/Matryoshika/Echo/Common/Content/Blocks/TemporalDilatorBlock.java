package se.Matryoshika.Echo.Common.Content.Blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.Tile.TileTemporalDilation;

public class TemporalDilatorBlock extends Block{

	public TemporalDilatorBlock() {
		super(Material.IRON);
		setRegistryName(Echo.MODID, "temporal_dilator");
		setUnlocalizedName(getRegistryName().toString());
	}
	
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileTemporalDilation();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ){
        
    	if(stack != null)
    		if(stack.getItem() == Items.SPAWN_EGG){
    			TileTemporalDilation tile = (TileTemporalDilation) world.getTileEntity(pos);
    			Entity entity = EntityList.createEntityByIDFromName(ItemMonsterPlacer.getEntityIdFromItem(stack), world);
    			if(entity instanceof EntityLiving){
    				tile.setEntity((EntityLiving) entity);
    			}
    			
    		}
        
        	
		
		return true;
    }

}
