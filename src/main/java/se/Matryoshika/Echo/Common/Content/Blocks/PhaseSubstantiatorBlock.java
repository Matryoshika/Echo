package se.Matryoshika.Echo.Common.Content.Blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.Content.Tile.TilePhaseSubstantiator;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class PhaseSubstantiatorBlock extends Block{

	public PhaseSubstantiatorBlock() {
		super(Material.IRON);
		setRegistryName(Echo.MODID, "phase_substantiator");
		setUnlocalizedName(getRegistryName().toString());
		this.setHardness(20);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TilePhaseSubstantiator();
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
        player.openGui(Echo.INSTANCE, EchoConstants.GUI_PHASE_SUBSTANTIATOR, world, pos.getX(), pos.getY(), pos.getZ());
        	
		
		return true;
    }

}
