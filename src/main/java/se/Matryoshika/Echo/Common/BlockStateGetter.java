package se.Matryoshika.Echo.Common;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import scala.actors.threadpool.Arrays;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Utils.BlockStateJSON;

public class BlockStateGetter implements ICommand{
	
	public static final ArrayList<Block> blacklist = new ArrayList<Block>(Arrays.asList(new Block[]{ContentRegistry.VOID, ContentRegistry.COMPRESSED_BLOCK}));

	@Override
	public int compareTo(ICommand o) {
		return 1;
	}

	@Override
	public String getCommandName() {
		return "echo";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/echo <block> [<n>] [<paste>] {<reload>}";
	}

	@Override
	public List<String> getCommandAliases() {
		return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		
		if(sender instanceof EntityPlayer){
			
			if(args.length == 1 && args[0].equals("block")){
				
				IBlockState state = ((EntityPlayer)sender).worldObj.getBlockState(new BlockPos(((EntityPlayer)sender).posX, ((EntityPlayer)sender).posY, ((EntityPlayer)sender).posZ).down());
				
				if(state.isFullBlock() && !blacklist.contains(state.getBlock()))
					((EntityPlayer)sender).addChatMessage(new TextComponentTranslation((NBTUtil.func_190009_a(new NBTTagCompound(), state).toString())));
				else{
					ItemStack orig = new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, state.getBlock().getMetaFromState(state));
					if(orig != null && orig.getItem() != null)
						((EntityPlayer)sender).addChatMessage(new TextComponentTranslation("The provided state for " + orig.getDisplayName() + " is not a full block!"));
					else{
						((EntityPlayer)sender).addChatMessage(new TextComponentTranslation("The provided state for " + state.getBlock().getRegistryName().toString() + " is not a full block!"));
					}
				}
				return;
			}
			else if(args.length == 3 && args[0].equals("block") && args[1].matches("^[1-6].*") && args[2].equals("paste")){
				IBlockState state = ((EntityPlayer)sender).worldObj.getBlockState(new BlockPos(((EntityPlayer)sender).posX, ((EntityPlayer)sender).posY, ((EntityPlayer)sender).posZ).down());
				
				if(state.isFullBlock() && !blacklist.contains(state.getBlock())){
					System.out.println(Byte.parseByte(args[1]));
					BlockStateJSON.addBlockStateToJSON(state, Byte.parseByte(args[1]));
				}
			}
			
			else if(args.length == 4 && args[0].equals("block") && args[1].matches("^[1-6].*") && args[2].equals("paste") && args[3].equals("reload")){
				IBlockState state = ((EntityPlayer)sender).worldObj.getBlockState(new BlockPos(((EntityPlayer)sender).posX, ((EntityPlayer)sender).posY, ((EntityPlayer)sender).posZ).down());
				
				if(state.isFullBlock() && !blacklist.contains(state.getBlock())){
					System.out.println(Byte.parseByte(args[1]));
					BlockStateJSON.addBlockStateToJSON(state, Byte.parseByte(args[1]));
				}
				BlockStateJSON.reload();
			}
			
			else{
				((EntityPlayer)sender).addChatMessage(new TextComponentTranslation("Please provide type. Applicable: 'block' or 'block' 'n' 'paste' ['reload']"));
			}
			
		}
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		if(server.isSinglePlayer())
			return true;
		return (server.canCommandSenderUseCommand(server.getOpPermissionLevel(), "echo"));
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos pos) {
		return new ArrayList<String>(Arrays.asList(new String[]{"block"}));
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
