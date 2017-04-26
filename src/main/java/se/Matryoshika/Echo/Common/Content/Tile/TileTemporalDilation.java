package se.Matryoshika.Echo.Common.Content.Tile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;

public class TileTemporalDilation extends TileEntity implements ITickable{
	
	public ItemStack entityHolder;
	private int tickTime;
	public boolean playerKill = false;
	
	public void setEntity(ItemStack stack){
		entityHolder = stack;
	}

	@Override
	public void update() {
		tickTime++;
		
		if(tickTime >= 20*30 && entityHolder != null){
			tickTime = 0;
			
			EntityLiving entity = (EntityLiving)EntityList.createEntityByIDFromName(ItemMonsterPlacer.getEntityIdFromItem(entityHolder), worldObj);
			
			if((int)entity.posX != this.pos.getX() || (int)entity.posY != this.pos.getY()+1 || (int)entity.posZ != this.pos.getZ())
				entity.setPosition(this.pos.getX() + 0.5, this.pos.getY() + 1, this.pos.getZ() + 0.5);
			
			
			if(!playerKill)
				entity.attackEntityFrom(DamageSource.generic, entity.getHealth());
			
			entity.setDead();
		}
	}

}
