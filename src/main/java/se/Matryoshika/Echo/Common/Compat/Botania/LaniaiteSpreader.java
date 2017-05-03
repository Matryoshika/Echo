package se.Matryoshika.Echo.Common.Compat.Botania;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import se.Matryoshika.Echo.Echo;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.mana.ILens;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.SpreaderVariant;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.api.wand.IWireframeAABBProvider;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.core.helper.InventoryHelper;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lexicon.CompatLexiconEntry;
import vazkii.botania.common.lexicon.LexiconData;
import vazkii.botania.common.lexicon.page.PageText;

public class LaniaiteSpreader extends Block implements IWandable, IWandHUD, ILexiconable, IWireframeAABBProvider {
	
	public static LexiconEntry LANIAITESPREADER;

	public LaniaiteSpreader() {
		super(Material.WOOD);
		setRegistryName(Echo.MODID, "laniaite_spreader");
		setUnlocalizedName(getRegistryName().toString());
		setHardness(20F);
		setSoundType(SoundType.WOOD);

	}

	@Nonnull
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BotaniaStateProps.SPREADER_VARIANT);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BotaniaStateProps.SPREADER_VARIANT).ordinal();
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		if (meta > SpreaderVariant.values().length) {
			meta = 0;
		}
		return getDefaultState().withProperty(BotaniaStateProps.SPREADER_VARIANT, SpreaderVariant.values()[meta]);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase,
			ItemStack par6ItemStack) {
		EnumFacing orientation = BlockPistonBase.getFacingFromEntity(pos, par5EntityLivingBase);
		TileSpreader spreader = (TileSpreader) world.getTileEntity(pos);
		world.setBlockState(pos, getStateFromMeta(par6ItemStack.getItemDamage()), 1 | 2);

		switch (orientation) {
		case DOWN:
			spreader.rotationY = -90F;
			break;
		case UP:
			spreader.rotationY = 90F;
			break;
		case NORTH:
			spreader.rotationX = 270F;
			break;
		case SOUTH:
			spreader.rotationX = 90F;
			break;
		case WEST:
			break;
		default:
			spreader.rotationX = 180F;
			break;
		}
	}

	@Override
	public int damageDropped(IBlockState par1) {
		return getMetaFromState(par1);
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
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			ItemStack heldItem, EnumFacing par6, float par7, float par8, float par9) {
		TileEntity tile = world.getTileEntity(pos);
		if (!(tile instanceof TileSpreader))
			return false;

		TileSpreader spreader = (TileSpreader) tile;
		ItemStack lens = spreader.getItemHandler().getStackInSlot(0);
		boolean isHeldItemLens = heldItem != null && heldItem.getItem() instanceof ILens;
		boolean wool = heldItem != null && heldItem.getItem() == Item.getItemFromBlock(Blocks.WOOL);

		if (heldItem != null)
			if (heldItem.getItem() == ModItems.twigWand)
				return false;

		if (lens == null && isHeldItemLens) {
			if (!player.capabilities.isCreativeMode)
				player.setHeldItem(hand, null);

			spreader.getItemHandler().setStackInSlot(0, heldItem.copy());
			spreader.markDirty();
		} else if (lens != null && !wool) {
			ItemStack add = lens.copy();
			if (!player.inventory.addItemStackToInventory(add))
				player.dropItem(add, false);
			spreader.getItemHandler().setStackInSlot(0, null);
			spreader.markDirty();
		}

		if (wool && spreader.paddingColor == -1) {
			spreader.paddingColor = heldItem.getItemDamage();
			heldItem.stackSize--;
			if (heldItem.stackSize == 0)
				player.setHeldItem(hand, null);
		} else if (heldItem == null && spreader.paddingColor != -1 && lens == null) {
			ItemStack pad = new ItemStack(Blocks.WOOL, 1, spreader.paddingColor);
			if (!player.inventory.addItemStackToInventory(pad))
				player.dropItem(pad, false);
			spreader.paddingColor = -1;
			spreader.markDirty();
		}

		return true;
	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TileEntity tile = world.getTileEntity(pos);
		if (!(tile instanceof TileSpreader))
			return;

		TileSpreader inv = (TileSpreader) tile;

		if (inv.paddingColor != -1) {
			net.minecraft.inventory.InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(),
					new ItemStack(Blocks.WOOL, 1, inv.paddingColor));
		}

		InventoryHelper.dropInventory(inv, world, state, pos);

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing side) {
		((TileSpreader) world.getTileEntity(pos)).onWanded(player, stack);
		return true;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileLaniaiteSpreader();
	}

	@Override
	public void renderHUD(Minecraft mc, ScaledResolution res, World world, BlockPos pos) {
		((TileSpreader) world.getTileEntity(pos)).renderHUD(mc, res);
	}

	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return LexiconData.spreader;
	}

	@Override
	public AxisAlignedBB getWireframeAABB(World world, BlockPos pos) {
		return FULL_BLOCK_AABB.offset(pos).contract(1.0 / 16.0);
	}
	
	public static class SpreaderLexicon{
		
		public static void setLexiconData(){
			LANIAITESPREADER = new CompatLexiconEntry("echo:laniaite_spreader", BotaniaAPI.categoryMana, "Echo");
			LANIAITESPREADER.setLexiconPages(new PageText("0")).setIcon(new ItemStack(BotaniaCompat.LANIAITE_SPREADER));
		}
	}

}
