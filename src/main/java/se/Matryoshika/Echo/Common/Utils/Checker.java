package se.Matryoshika.Echo.Common.Utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class Checker {
	
	public static boolean areEqual(BlockPos a, BlockPos b){
		return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
	}
	
	public static boolean areEqual(ChunkPos a, ChunkPos b){
		return a.chunkXPos == b.chunkXPos && a.chunkZPos == b.chunkZPos;
	}

}
