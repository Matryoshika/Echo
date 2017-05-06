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
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.SkeletonType;
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

public class TileTemporalDilation extends TileEntity implements ITickable {

	private int tickTime;
	public boolean playerKill = false;
	public Class entityClass;
	public int meta = 0;
	public LootGenerator loot;
	public boolean hasUpdated = false;
	public float looting = 0;
	public boolean beheading = false;
	public boolean redstone = false;

	public void setEntity(EntityLiving entity) {
		entityClass = entity.getClass();
		meta = 0;

		if (entity instanceof EntitySkeleton && ((EntitySkeleton) entity).func_189771_df() == SkeletonType.WITHER)
			meta = 1;
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

	public void writePacketNBT(NBTTagCompound tag) {

		if (entityClass != null) {
			tag.setString(EchoConstants.NBT_TD_CLASS, entityClass.getName());
			tag.setInteger(EchoConstants.NBT_TD_META, meta);
		}
		tag.setBoolean(EchoConstants.NBT_TD_KILL, playerKill);
		tag.setBoolean(EchoConstants.NBT_TD_REDSTONE, redstone);
		tag.setBoolean(EchoConstants.NBT_TD_BEHEADING, beheading);
		tag.setFloat(EchoConstants.NBT_TD_LOOTING, looting);
	}

	public void readPacketNBT(NBTTagCompound tag) {
		if (tag.getString(EchoConstants.NBT_TD_CLASS) != null
				&& !tag.getString(EchoConstants.NBT_TD_CLASS).isEmpty())
			try {
				entityClass = Class.forName(tag.getString(EchoConstants.NBT_TD_CLASS));
				meta = tag.getInteger(EchoConstants.NBT_TD_META);
			} catch (ClassNotFoundException e) {
				System.out.println("Exception finding the class: " + tag.getString(EchoConstants.NBT_TD_CLASS));
			}
		playerKill = tag.getBoolean(EchoConstants.NBT_TD_KILL);
		redstone = tag.getBoolean(EchoConstants.NBT_TD_REDSTONE);
		beheading = tag.getBoolean(EchoConstants.NBT_TD_BEHEADING);
		looting = tag.getFloat(EchoConstants.NBT_TD_LOOTING);
		
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());

		if (entityClass != null) {
			tag.setString(EchoConstants.NBT_TD_CLASS, entityClass.getName());
			tag.setInteger(EchoConstants.NBT_TD_META, meta);
		}
		tag.setBoolean(EchoConstants.NBT_TD_KILL, playerKill);
		tag.setBoolean(EchoConstants.NBT_TD_REDSTONE, redstone);
		tag.setBoolean(EchoConstants.NBT_TD_BEHEADING, beheading);
		tag.setFloat(EchoConstants.NBT_TD_LOOTING, looting);

		return tag;

	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		setPos(new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z")));
		try {
			entityClass = Class.forName(tag.getString(EchoConstants.NBT_TD_CLASS));
			meta = tag.getInteger(EchoConstants.NBT_TD_META);
		} catch (ClassNotFoundException e) {
		}
		playerKill = tag.getBoolean(EchoConstants.NBT_TD_KILL);
		redstone = tag.getBoolean(EchoConstants.NBT_TD_REDSTONE);
		beheading = tag.getBoolean(EchoConstants.NBT_TD_BEHEADING);
		looting = tag.getFloat(EchoConstants.NBT_TD_LOOTING);
		
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		if (entityClass != null) {
			tag.setString(EchoConstants.NBT_TD_CLASS, entityClass.getName());
			tag.setInteger(EchoConstants.NBT_TD_META, meta);
		}
		tag.setBoolean(EchoConstants.NBT_TD_KILL, playerKill);
		tag.setBoolean(EchoConstants.NBT_TD_REDSTONE, redstone);
		tag.setBoolean(EchoConstants.NBT_TD_BEHEADING, beheading);
		tag.setFloat(EchoConstants.NBT_TD_LOOTING, looting);
		
		return new SPacketUpdateTileEntity(pos, -999, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readPacketNBT(pkt.getNbtCompound());

	}
	
	public boolean hasRedstone(){
		for (EnumFacing enumfacing : EnumFacing.values()){
            if (worldObj.isSidePowered(pos.offset(enumfacing), enumfacing)){
                return true;
            }
        }
		return false;
	}

	@Override
	public void update() {
		if(redstone && !hasRedstone())
			return;
		
		
		tickTime++;

		if (tickTime >= 20 * 2) {
			tickTime = 0;
			

			if (entityClass == null)
				return;

			EntityLiving entity = getEntity(worldObj);

			if (worldObj.isRemote)
				return;

			DamageSource dmg = null;
			if (!playerKill)
				dmg = DamageSource.generic;
			else
				dmg = DamageSource.causePlayerDamage(FakePlayerFactory.getMinecraft((WorldServer) worldObj));

			if (loot == null || hasUpdated) {
				loot = new LootGenerator(worldObj, entity, dmg, playerKill, looting);
				hasUpdated = false;
			}

			IItemHandler inv = getItemHandler();
			if (inv != null) {
				Stack: for (ItemStack stack : loot.getLoot()) {
					for (int i = 0; i < inv.getSlots(); i++) {
						if ((inv.getStackInSlot(i) == null) || ((inv.getStackInSlot(i) != null
								&& inv.getStackInSlot(i).stackSize + stack.stackSize <= stack.getMaxStackSize()
								&& ItemStack.areItemsEqual(inv.getStackInSlot(i), stack)))) {
							inv.insertItem(i, stack, false);
							continue Stack;
						}
					}
				}
			}

		}
	}

	public EntityLiving getEntity(World world) {
		EntityLiving entity = null;
		for (Constructor<?> cons : entityClass.getConstructors()) {
			if (cons.getParameterTypes().length == 1 && cons.getParameterTypes()[0] == World.class) {
				try {
					entity = (EntityLiving) cons.newInstance(world);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		if (entity instanceof EntitySkeleton && meta == 1)
			((EntitySkeleton) entity).func_189768_a(SkeletonType.WITHER);

		return entity;
	}

	private IItemHandler getItemHandler() {

		for (EnumFacing side : EnumFacing.values()) {
			TileEntity te = worldObj.getTileEntity(pos.offset(side));
			if (te == null)
				continue;

			if (te instanceof TileEntityChest) {

				IItemHandler doubleChest = VanillaDoubleChestItemHandler.get((TileEntityChest) te);
				if (doubleChest != VanillaDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE) {
					return doubleChest;
				}
			}

			IItemHandler ret = te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)
					? te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) : null;

			if (ret == null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
				ret = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

			return ret;
		}

		return null;
	}

	public class LootGenerator {

		ResourceLocation deathLootTable = null;
		boolean wasRecentlyHit = false;
		EntityPlayer attackingPlayer = null;
		World world;
		EntityLiving entity;
		DamageSource source;
		boolean playerKill = false;
		float looting;

		public LootGenerator(World world, EntityLiving entity, DamageSource source, boolean playerKill, float looting) {
			this.world = world;
			this.entity = entity;
			this.source = source;
			this.playerKill = playerKill;
			this.looting = looting;
		}

		public List<ItemStack> getLoot() {

			if (playerKill)
				attackingPlayer = FakePlayerFactory.getMinecraft((WorldServer) world);

			List<ItemStack> loots = new ArrayList<ItemStack>();

			if (deathLootTable == null) {
				// System.out.println("table doest exist");
				String name = "func_184276_b";
				if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))
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
			if (deathLootTable != null) {
				LootTable loottable = world.getLootTableManager().getLootTableFromLocation(deathLootTable);
				LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer) world))
						.withLootedEntity(entity).withDamageSource(source);

				if (playerKill)
					lootcontext$builder = lootcontext$builder
							.withPlayer(FakePlayerFactory.getMinecraft((WorldServer) world))
							.withLuck(FakePlayerFactory.getMinecraft((WorldServer) world).getLuck());

				for (ItemStack itemstack : loottable.generateLootForPools(new Random(), lootcontext$builder.build())) {
					for (int i = 0; i <= (looting) * 2; i++) {
						loots.add(itemstack);
					}
				}
				
				if(beheading){
					ItemStack head = null;
					if(entity instanceof EntitySkeleton){
						if(((EntitySkeleton) entity).func_189771_df() == SkeletonType.WITHER)
							head = new ItemStack(Items.SKULL, 1, 1);
						else
							head = new ItemStack(Items.SKULL, 1, 0);
					}
					else if(entity instanceof EntityCreeper)
						head = new ItemStack(Items.SKULL, 1, 4);
					
					else if(entity instanceof EntityZombie)
						head = new ItemStack(Items.SKULL, 1, 2);
					
					if(head != null)
						for (int i = 0; i <= (looting) * 2; i++){
							if(world.rand.nextInt(100) <= 20)
								loots.add(head);
						}
					
				}

			}

			return loots;

		}
	}

}
