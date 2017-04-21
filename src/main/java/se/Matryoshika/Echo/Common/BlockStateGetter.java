package se.Matryoshika.Echo.Common;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import scala.actors.threadpool.Arrays;

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
		return "Returns a string representing the wanted object. 'block' returns the blockstate of the block below you, 'item' returns the nbt-compound of the item in your hand.";
	}

	@Override
	public List<String> getCommandAliases() {
		return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		
		if(sender instanceof EntityPlayer){
			
			if(args.length > 0 && args[0].equals("block")){
				Vec3d vec3 = ((EntityPlayer)sender).getPositionEyes(1.0F);
				Vec3d lookVec = ((EntityPlayer)sender).getLook(1.0F);
				RayTraceResult res = ((EntityPlayer)sender).worldObj.rayTraceBlocks(vec3, lookVec, true, true, true);
				
				
				IBlockState state = ((EntityPlayer)sender).worldObj.getBlockState(new BlockPos(((EntityPlayer)sender).posX, ((EntityPlayer)sender).posY, ((EntityPlayer)sender).posZ).down());
				
				((EntityPlayer)sender).addChatMessage(new TextComponentTranslation(state.toString()));
				return;
			}
			else{
				((EntityPlayer)sender).addChatMessage(new TextComponentTranslation("Please provide type. Applicable: 'block', 'item'"));
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
		return new ArrayList<String>(Arrays.asList(new String[]{"block","item"}));
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
