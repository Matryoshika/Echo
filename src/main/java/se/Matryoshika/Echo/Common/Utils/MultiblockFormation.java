package se.Matryoshika.Echo.Common.Utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Tile.TileMenger;

public class MultiblockFormation {
	
	public void callForEachIn(World world, BlockPos pos){
		BlockPos[] multiblock = new BlockPos[]{
				pos.add(0, 0, 0), pos.add(1, 0, 0), pos.add(2, 0, 0),
				pos.add(0, 0, 1),                   pos.add(2, 0, 1),
				pos.add(0, 0, 2), pos.add(1, 0, 2), pos.add(2, 0, 2),
				
				pos.add(0, 1, 0),                   pos.add(2, 1, 0),
				
				pos.add(0, 1, 2),                   pos.add(2, 1, 2),
				
				pos.add(0, 2, 0), pos.add(1, 2, 0), pos.add(2, 2, 0),
				pos.add(0, 2, 1),                   pos.add(2, 2, 1),
				pos.add(0, 2, 2), pos.add(1, 2, 2), pos.add(2, 2, 2)
		};
		
		for(BlockPos _pos : multiblock){
			if(world.getBlockState(_pos) != world.getBlockState(pos)){
				return;
			}
		}
		
		if(!world.isAirBlock(pos.add(1, 1, 1)))
			return;
		
		world.setBlockState(pos.add(1, 1, 1), ContentRegistry.COMPRESSED_BLOCK.getDefaultState());
		world.setTileEntity(pos.add(1, 1, 1), new TileMenger(world.getBlockState(pos), (byte) 1));
		world.createExplosion(null, pos.getX()+1.5, pos.getY()+1.5, pos.getZ()+1.5, 0, true);
		
		for(BlockPos _pos : multiblock)
			world.setBlockToAir(_pos);
		
	}

}
