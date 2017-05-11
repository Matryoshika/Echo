package se.Matryoshika.Echo.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import se.Matryoshika.Echo.Common.Content.ContentRegistry;
import se.Matryoshika.Echo.Common.Content.Blocks.CompressedBlock;
import se.Matryoshika.Echo.Common.Utils.EchoConstants;

public class BlockStateGetter implements ICommand{

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
			
			if(args.length == 2 && args[0].equals("make_menger") && args[1].matches("^[1-6].*")){
				if(((EntityPlayer) sender).capabilities.isCreativeMode){
					ItemStack stack = ((EntityPlayer) sender).getHeldItemMainhand();
					if(stack != null && stack.getItem() != Item.getItemFromBlock(ContentRegistry.COMPRESSED_BLOCK)){
						IBlockState state = Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getMetadata());
						if(!state.isFullBlock())
							return;
						stack = CompressedBlock.get(state, Byte.parseByte(args[1]));
						((EntityPlayer) sender).setHeldItem(EnumHand.MAIN_HAND, stack);
						
					}
				}
			}
			else if(args.length == 2 && args[0].equals("set_tier") && args[1].matches("^[1-6].*")){
				if(((EntityPlayer) sender).capabilities.isCreativeMode){
					ItemStack stack = ((EntityPlayer) sender).getHeldItemMainhand();
					if(stack != null && stack.getItem() == Item.getItemFromBlock(ContentRegistry.COMPRESSED_BLOCK)){
						stack.getTagCompound().setByte(EchoConstants.NBT_TIER, Byte.parseByte(args[1]));
						((EntityPlayer) sender).setHeldItem(EnumHand.MAIN_HAND, stack);
						
					}
				}
			}
			
			else{
				((EntityPlayer)sender).addChatMessage(new TextComponentTranslation("Correct usage: 'make_menger' '1-6'"));
			}
			
		}
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		if(server.isSinglePlayer()){
			return true;
		}
		return false;
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
