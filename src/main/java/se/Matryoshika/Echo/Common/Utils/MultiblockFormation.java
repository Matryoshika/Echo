package se.Matryoshika.Echo.Common.Utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MultiblockFormation {
	
	public final byte MIDDLES = 12;
	public final byte CORNERS = 8;
	
	
	public BlockPos getTrueMiddle(World world, BlockPos pos, IBlockState state){
		if(isCorner(world, pos, state)){
			
			BlockPos opposite = getOppositeCorner(world, pos, state);
			if(opposite != null){
				int dx = (opposite.getX() - pos.getX()) / 2;
				int dy = (opposite.getY() - pos.getY()) / 2;
				int dz = (opposite.getZ() - pos.getZ()) / 2;
				if(world.isAirBlock(new BlockPos(pos.getX()+dx,pos.getY()+dy, pos.getZ()+dz))){
					return new BlockPos(pos.getX()+dx,pos.getY()+dy, pos.getZ()+dz);
				}
			}
			
		}
		else if(isMiddle(world, pos, state)){
			for(EnumFacing facing : EnumFacing.values()){
				if(isCorner(world, pos.offset(facing), state)){
					BlockPos opposite = getOppositeCorner(world, pos.offset(facing), state);
					if(opposite != null){
						int dx = (opposite.getX() - pos.getX()) / 2;
						int dy = (opposite.getY() - pos.getY()) / 2;
						int dz = (opposite.getZ() - pos.getZ()) / 2;
						if(world.isAirBlock(new BlockPos(pos.getX()+dx,pos.getY()+dy, pos.getZ()+dz))){
							return new BlockPos(pos.getX()+dx,pos.getY()+dy, pos.getZ()+dz);
						}
					}
				}
			}
		}
		
		
		return null;
	}
	
	public BlockPos getOppositeCorner(World world, BlockPos pos, IBlockState state){
		List<BlockPos> middles = new ArrayList<BlockPos>();
		for(EnumFacing facing : EnumFacing.values())
			if(isMiddle(world, pos.offset(facing), state))
				middles.add(pos.offset(facing));
		
		
		if(middles.size() == 3){
			//Only one of the x, y & z values will equal ±1, the other 2 will equal 0
			int dx = (pos.getX()-middles.get(0).getX())-(pos.getY()-middles.get(0).getY())-(pos.getZ()-middles.get(0).getZ());
			int dy = (pos.getX()-middles.get(1).getX())-(pos.getY()-middles.get(1).getY())-(pos.getZ()-middles.get(1).getZ());
			int dz = (pos.getX()-middles.get(2).getX())-(pos.getY()-middles.get(2).getY())-(pos.getZ()-middles.get(2).getZ());
			
			int x = dx*2; 
			int y = dy*2; 
			int z = dz*2;
			
			//Per case edits, because I cannot bother to calculate the formula that determines all of this
			if(dx == 1 && dy == -1 && dz == 1){
				x = -(dx*2);
				y = -(dy*2);
				z = -(dz*2);
			}
			if(dx == 1 && dy == 1 && dz == 1)
				x = -(dx*2);
			
			if(dx == 1 && dy == 1 && dz == -1)
				z = -(dz*2);
			
			if(dx == 1 && dy == -1 && dz == -1)
				y = -(dy*2);
			
			if(dx == -1 && dy == -1 && dz == -1)
				x = -dx*2;
			
			if(dx == -1 && dy == -1 && dz == 1)
				z = -dz*2;
			
			if(dx == -1 && dy == 1 && dz == 1)
				y = -dy*2;
			
			if(dx == -1 && dy == 1 && dz == -1){
				x = -dx*2;
				y = -dy*2;
				z = -dz*2;
			}			
			
			
			BlockPos corner = pos.add(x, y, z);
			if(isCorner(world, corner, state)){
				return corner;
			}
		}
		return null;
	}
	
	public boolean isCorner(World world, BlockPos pos, IBlockState state){
		byte matches = 0;
		List<EnumFacing> used = new ArrayList<EnumFacing>();
		for(EnumFacing facing : EnumFacing.values()){
			if(world.getBlockState(pos.offset(facing)).equals(state)){
				if(facing == EnumFacing.DOWN && used.contains(EnumFacing.UP))
					continue;
				if(facing == EnumFacing.UP && used.contains(EnumFacing.DOWN))
					continue;
				if(facing == EnumFacing.EAST && used.contains(EnumFacing.WEST))
					continue;
				if(facing == EnumFacing.WEST && used.contains(EnumFacing.EAST))
					continue;
				if(facing == EnumFacing.SOUTH && used.contains(EnumFacing.NORTH))
					continue;
				if(facing == EnumFacing.NORTH && used.contains(EnumFacing.SOUTH))
					continue;
				
				
				matches++;
				used.add(facing);
			}
		}
		//This is only a corner if there are 3 states that equal eachother. Due to the opposite-facing-checking above, no block will have
		//3 states nearby with any being on the same axis.
		if(matches == 3)
			return true;
		
		return false;
	}
	
	public boolean isMiddle(World world, BlockPos pos, IBlockState state){
		byte matches = 0;
		List<EnumFacing> used = new ArrayList<EnumFacing>();
		for(EnumFacing facing : EnumFacing.values()){
			if(world.getBlockState(pos.offset(facing)).equals(state)){
				if(facing == EnumFacing.NORTH && (used.contains(EnumFacing.DOWN) || used.contains(EnumFacing.UP) || 
						used.contains(EnumFacing.EAST) || used.contains(EnumFacing.WEST)))
					continue;
				if(facing == EnumFacing.SOUTH && (used.contains(EnumFacing.DOWN) || used.contains(EnumFacing.UP) || 
						used.contains(EnumFacing.EAST) || used.contains(EnumFacing.WEST)))
					continue;
				if(facing == EnumFacing.DOWN && (used.contains(EnumFacing.NORTH) || used.contains(EnumFacing.SOUTH) || 
						used.contains(EnumFacing.EAST) || used.contains(EnumFacing.WEST)))
					continue;
				if(facing == EnumFacing.UP && (used.contains(EnumFacing.NORTH) || used.contains(EnumFacing.SOUTH) || 
						used.contains(EnumFacing.EAST) || used.contains(EnumFacing.WEST)))
					continue;
				if(facing == EnumFacing.EAST && (used.contains(EnumFacing.NORTH) || used.contains(EnumFacing.SOUTH) || 
						used.contains(EnumFacing.UP) || used.contains(EnumFacing.DOWN)))
					continue;
				if(facing == EnumFacing.WEST && (used.contains(EnumFacing.NORTH) || used.contains(EnumFacing.SOUTH) || 
						used.contains(EnumFacing.UP) || used.contains(EnumFacing.DOWN)))
					continue;
				
				matches++;
				used.add(facing);
			}
		}
		//This is only a middle-block if there are 2 states on the same axis on either side of this block
		if(matches == 2)
			return true;
		
		return false;
	}

}
