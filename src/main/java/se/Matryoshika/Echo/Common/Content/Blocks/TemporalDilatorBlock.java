package se.Matryoshika.Echo.Common.Content.Blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Items.ItemUpgrade;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;
import se.Matryoshika.Echo.Common.Content.Tile.TileTemporalDilation;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class TemporalDilatorBlock extends Block{

	public TemporalDilatorBlock() {
		super(Material.IRON);
		setRegistryName(Echo.MODID, "temporal_dilator");
		setUnlocalizedName(getRegistryName().toString());
		this.setHardness(20);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state){
        return false;
    }
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileTemporalDilation();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ){
        
		TileTemporalDilation tile = (TileTemporalDilation) world.getTileEntity(pos);
		
    	if(stack != null){
    		if(stack.getItem() == Items.SPAWN_EGG){
    			Entity entity = EntityList.createEntityByIDFromName(ItemMonsterPlacer.getEntityIdFromItem(stack), world);
    			if(entity instanceof EntityLiving){
    				tile.setEntity((EntityLiving) entity);
    				tile.hasUpdated = true;
    			}
    			
    			
    		}
    		else if(stack.getItem() == ContentRegistry.UPGRADE){
    			
    			//Player kill
    			if(stack.getMetadata() == 0 && !tile.playerKill){
    				tile.playerKill = true;
    				stack.stackSize--;
    				tile.hasUpdated = true;
    			}
    			
    			//Beheading
    			else if(stack.getMetadata() == 7 && !tile.beheading){
    				tile.beheading = true;
    				stack.stackSize--;
    				tile.hasUpdated = true;
    			}
    			
    			//Fortune handling
    			else if(stack.getMetadata() > 0 && stack.getMetadata() < 7){
    				int meta = stack.getMetadata();
    				if(meta == tile.looting+1){
    					tile.looting += 1;
    					stack.stackSize--;
    					tile.hasUpdated = true;
    				}
    			}
    			else if(stack.getMetadata() == 8 && !tile.redstone){
    				tile.redstone = true;
    				stack.stackSize--;
    				tile.hasUpdated = true;
    			}
    		}
    	}
    	
		return true;
    }
	
	@Override
	public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		
		TileTemporalDilation tile = (TileTemporalDilation) world.getTileEntity(pos);
		
		java.util.List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
		if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileTemporalDilation){
			if(tile.playerKill){
				ItemStack stack = new ItemStack(ContentRegistry.UPGRADE, 1, ItemUpgrade.Upgrade.PLAYER_KILL.ordinal());
				ret.add(stack);
			}
			if(tile.beheading){
				ItemStack stack = new ItemStack(ContentRegistry.UPGRADE, 1, ItemUpgrade.Upgrade.BEHEADING.ordinal());
				ret.add(stack);
			}
			if(tile.looting > 0){
				for(int i = 1; i <= tile.looting; i++){
					ItemStack stack = new ItemStack(ContentRegistry.UPGRADE, 1, i);
					ret.add(stack);
				}
			}
			if(tile.redstone){
				ItemStack stack = new ItemStack(ContentRegistry.UPGRADE, 1, ItemUpgrade.Upgrade.REDSTONE.ordinal());
				ret.add(stack);
			}
				
		}
		return ret;
	}
	
	
	@Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest){
        if (willHarvest) return true;
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack tool){
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

}
