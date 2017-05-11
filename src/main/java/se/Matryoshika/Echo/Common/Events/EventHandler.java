package se.Matryoshika.Echo.Common.Events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Tile.TileTemporalDilation;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

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
			tile.setEntity((EntityLiving) event.getEntityLiving());
			tile.hasUpdated = true;
		}
	}
	
	
	@SubscribeEvent
	public void onUse(RightClickBlock event) {
		
		//Fix for JEI etc adding a Menger block without NBT

		if (event.getItemStack() != null && event.getItemStack().getItem() == Item.getItemFromBlock(ContentRegistry.COMPRESSED_BLOCK)) {
			if(!event.getItemStack().hasTagCompound() || !event.getItemStack().getTagCompound().hasKey(EchoConstants.NBT_BLOCKSTATE))
				event.setCanceled(true);
		}
	}

}
