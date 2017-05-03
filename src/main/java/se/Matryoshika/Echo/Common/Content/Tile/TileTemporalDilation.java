package se.Matryoshika.Echo.Common.Content.Tile;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaDoubleChestItemHandler;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Blocks.LaniaiteFabricator;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;
import net.minecraft.inventory.InventoryHelper;

public class TileTemporalDilation extends TileEntity implements ITickable{

	
	private int tickTime;
	public static boolean playerKill = false;
	public Class entityClass;
	public LootGenerator loot;
	public boolean hasUpdated = false;
	
	public void setEntity(EntityLiving entity){
		entityClass = entity.getClass();
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readPacketNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound ret = super.writeToNBT(compound);
		writePacketNBT(ret);
		return ret;
	}

	public void writePacketNBT(NBTTagCompound cmp) {

		cmp.setString(EchoConstants.NBT_TD_CLASS, entityClass.getName());
		cmp.setBoolean(EchoConstants.NBT_TD_KILL, playerKill);
	}

	public void readPacketNBT(NBTTagCompound cmp) {
		try {
			entityClass = Class.forName(cmp.getString(EchoConstants.NBT_TD_CLASS));
		} catch (ClassNotFoundException e) {
			System.out.println("Exception finding the class: " + cmp.getString(EchoConstants.NBT_TD_CLASS));
		}
		playerKill = cmp.getBoolean(EchoConstants.NBT_TD_KILL);
	}
	
	@Override
	public NBTTagCompound getUpdateTag(){
		final NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("x", pos.getX());
		nbt.setInteger("y", pos.getY());
		nbt.setInteger("z", pos.getZ());
		nbt.setString(EchoConstants.NBT_TD_CLASS, entityClass.getName());
		nbt.setBoolean(EchoConstants.NBT_TD_KILL, playerKill);
		
		return nbt;
		
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag){
		setPos(new BlockPos(tag.getInteger("x"),tag.getInteger("y"),tag.getInteger("z")));
		try {
			entityClass = Class.forName(tag.getString(EchoConstants.NBT_TD_CLASS));
		} catch (ClassNotFoundException e) {}
		playerKill = tag.getBoolean(EchoConstants.NBT_TD_KILL);
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		if(entityClass != null){
			tag.setString(EchoConstants.NBT_TD_CLASS, entityClass.getName());
			tag.setBoolean(EchoConstants.NBT_TD_KILL, playerKill);
		}
		if(tag.hasNoTags())
			return null;
		return new SPacketUpdateTileEntity(pos, -999, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readPacketNBT(pkt.getNbtCompound());
		
		//worldObj.markBlockRangeForRenderUpdate(pos, pos);

	}
	

	@Override
	public void update() {
		tickTime++;
		
		if(tickTime >= 20*2){
			tickTime = 0;
			
			if(entityClass == null)
				return;
			
			EntityLiving entity = getEntity(worldObj);
			
			if(worldObj.isRemote)
				return;
			
			playerKill = true;
			
			DamageSource dmg = null;
			if(!playerKill)
				dmg = DamageSource.generic;
			else
				dmg = DamageSource.causePlayerDamage(FakePlayerFactory.getMinecraft((WorldServer) worldObj));
			
			if(loot == null || hasUpdated){
				loot = new LootGenerator(worldObj, entity, dmg);
				hasUpdated = false;
			}
			
			IItemHandler inv = getItemHandler();
			if(inv != null){
				Stack:
				for(ItemStack stack : loot.getLoot()){
					for(int i = 0; i < inv.getSlots(); i++){
						if(inv.getStackInSlot(i) == null || (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).stackSize + stack.stackSize <= stack.getMaxStackSize() && ItemStack.areItemsEqual(inv.getStackInSlot(i), stack))){
							inv.insertItem(i, stack, false);
							continue Stack;
						}
					}
				}
			}
			
		}
	}
	
	public EntityLiving getEntity(World world){
		EntityLiving entity = null;
		for (Constructor<?> cons : entityClass.getConstructors()) {
            if(cons.getParameterTypes().length == 1 && cons.getParameterTypes()[0] == World.class){
            	try {
					entity = (EntityLiving) cons.newInstance(world);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
            }
        }
		return entity;
	}
	
	private IItemHandler getItemHandler(){
		
		for(EnumFacing side : EnumFacing.values()){
			TileEntity te = worldObj.getTileEntity(pos.offset(side));
			if(te == null)
				continue;
			
			if(te instanceof TileEntityChest){
				
				IItemHandler doubleChest = VanillaDoubleChestItemHandler.get((TileEntityChest) te);
				if(doubleChest != VanillaDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE){
					return doubleChest;
				}
			}
			
			IItemHandler ret = te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) ? te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) : null;
		
			if(ret == null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
				ret = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				
			
			return ret;
		}
		
		return null;
	}
	
	public static class LootGenerator{
		
		ResourceLocation deathLootTable = null;
		boolean wasRecentlyHit = false;
		EntityPlayer attackingPlayer = null;
		World world;
		EntityLiving entity;
		DamageSource source;
		
		public LootGenerator(World world, EntityLiving entity, DamageSource source){
			this.world = world;
			this.entity = entity;
			this.source = source;
		}
		
		public List<ItemStack> getLoot(){
			
			
			if(playerKill)
				attackingPlayer = FakePlayerFactory.getMinecraft((WorldServer) world);
			
			List<ItemStack> loots = new ArrayList<ItemStack>();
			
			if(deathLootTable == null){
				//System.out.println("table doest exist");
				String name = "func_184276_b";
				if((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))
					name = "getLootTable";
				
				Method method = null;
				try {
					method = entity.getClass().getDeclaredMethod(name);
				} catch (NoSuchMethodException | SecurityException e) {
				}
				method.setAccessible(true);
				try {
					deathLootTable = (ResourceLocation) method.invoke(entity);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				}
				
			}
			if (deathLootTable != null){
	            LootTable loottable = world.getLootTableManager().getLootTableFromLocation(deathLootTable);
	            LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer)world)).withLootedEntity(entity).withDamageSource(source);

	            if (playerKill)
	                lootcontext$builder = lootcontext$builder.withPlayer(FakePlayerFactory.getMinecraft((WorldServer) world)).withLuck(FakePlayerFactory.getMinecraft((WorldServer) world).getLuck());
	            

	            for (ItemStack itemstack : loottable.generateLootForPools(new Random(), lootcontext$builder.build()))
	                loots.add(itemstack);
	            
	        }
			
			return loots;
			
		}
	}

}
