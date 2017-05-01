package se.Matryoshika.Echo.Common.Events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import se.Matryoshika.Echo.Common.LootTables;
import se.Matryoshika.Echo.Common.Content.Tile.TileTemporalDilation;

public class EventHandler {
	
	@SubscribeEvent
	public void addEntityToTD(LivingDeathEvent event){
		if(!(event.getEntityLiving() instanceof EntityLiving))
			return;
		
		if(!event.getEntityLiving().isNonBoss())
			return;
		
		World world = event.getEntityLiving().worldObj;
		BlockPos pos = event.getEntityLiving().getPosition();
		
		for(BlockPos _pos : BlockPos.getAllInBox(pos.add(1, 1, 1), pos.add(-1, -1, -1))){
			if(!(world.getTileEntity(_pos) != null && world.getTileEntity(_pos) instanceof TileTemporalDilation))
				continue;
			
			TileTemporalDilation tile = (TileTemporalDilation) world.getTileEntity(_pos);
			tile.entityClass = event.getEntityLiving().getClass();
		}
		
		
	}

}
