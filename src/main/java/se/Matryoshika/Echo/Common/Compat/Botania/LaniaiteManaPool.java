package se.Matryoshika.Echo.Common.Compat.Botania;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.Optional;
import se.Matryoshika.Echo.Echo;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.PoolVariant;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.common.achievement.ICraftAchievement;
import vazkii.botania.common.achievement.ModAchievements;
import vazkii.botania.common.block.mana.BlockPool;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.lexicon.LexiconData;

@Optional.InterfaceList({
	@Optional.Interface(modid = "botania", iface = "vazkii.botania.api.wand.IWandHUD", striprefs = true),
	@Optional.Interface(modid = "botania", iface = "vazkii.botania.api.wand.IWandable", striprefs = true),
	@Optional.Interface(modid = "botania", iface = "vazkii.botania.api.lexicon.ILexiconable", striprefs = true),
	@Optional.Interface(modid = "botania", iface = "vazkii.botania.common.achievement.ICraftAchievement", striprefs = true)
})
public class LaniaiteManaPool extends Block implements IWandHUD, IWandable, ILexiconable, ICraftAchievement{

	public LaniaiteManaPool() {
		super(Material.ROCK);
		setRegistryName(Echo.MODID, "laniaite_pool");
		setUnlocalizedName(getRegistryName().toString());
		setHardness(10F);
		setResistance(100F);
		setSoundType(SoundType.STONE);
		BotaniaAPI.blacklistBlockFromMagnet(this, Short.MAX_VALUE);
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileLaniaitePool();
	}
	
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1);

	private boolean lastFragile = false;

	@Nonnull
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BotaniaStateProps.POOL_VARIANT);
	}


	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		if (meta > PoolVariant.values().length) {
			meta = 0;
		}
		return getDefaultState().withProperty(BotaniaStateProps.POOL_VARIANT, PoolVariant.DEFAULT);
	}

	@Nonnull
	@Override
	public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
		return state;
	}

	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getBlock().getMetaFromState(state);
	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TilePool pool = (TilePool) world.getTileEntity(pos);
		lastFragile = pool.fragile;
		super.breakBlock(world, pos, state);
	}

	@Nonnull
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();

		if(!lastFragile)
			drops.add(new ItemStack(this, 1, state.getBlock().getMetaFromState(state)));

		return drops;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity par5Entity) {
		if(par5Entity instanceof EntityItem) {
			TilePool tile = (TilePool) world.getTileEntity(pos);
			if(tile.collideEntityItem((EntityItem) par5Entity))
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, pos);
		}
	}

	private static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0, 0, 0, 1, 1/16.0, 1);
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0, 0, 15/16.0, 1, 0.5, 1);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1/16.0);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0, 0, 0, 1/16.0, 0.5, 1);
	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(15/16.0, 0, 0, 1, 0.5, 1);

	@Override
	public boolean isSideSolid(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
		return side == EnumFacing.DOWN;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Nonnull
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		if (state.getValue(BotaniaStateProps.POOL_VARIANT) == PoolVariant.FABULOUS)
			return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
		else return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TilePool pool = (TilePool) world.getTileEntity(pos);
		int val = (int) ((double) pool.getCurrentMana() / (double) pool.manaCap * 15.0);
		if(pool.getCurrentMana() > 0)
			val = Math.max(val, 1);

		return val;
	}

	@Override
	public void renderHUD(Minecraft mc, ScaledResolution res, World world, BlockPos pos) {
		((TilePool) world.getTileEntity(pos)).renderHUD(mc, res);
	}

	@Override
	public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing side) {
		((TilePool) world.getTileEntity(pos)).onWanded(player, stack);
		return true;
	}

	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return world.getBlockState(pos).getValue(BotaniaStateProps.POOL_VARIANT) == PoolVariant.FABULOUS ? LexiconData.rainbowRod : LexiconData.pool;
	}

	@Override
	public Achievement getAchievementOnCraft(ItemStack stack, EntityPlayer player, IInventory matrix) {
		return ModAchievements.manaPoolPickup;
	}
	
	

}
