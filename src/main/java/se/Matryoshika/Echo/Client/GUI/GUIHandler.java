package se.Matryoshika.Echo.Client.GUI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import se.Matryoshika.Echo.Common.Content.Tile.TilePhaseSubstantiator;
import se.Matryoshika.Echo.Common.Content.Tile.Inventory.ContainerPhaseSubstantiator;

import static se.Matryoshika.Echo.Common.Utils.EchoConstants.*;

public class GUIHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		final TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		
		switch(ID){
			
		case GUI_PHASE_SUBSTANTIATOR:
			if(tile != null)
				return ((TilePhaseSubstantiator)tile).createContainer(player);
			
			default:
				return null;
		}
	}
		
		

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
final TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		
		switch(ID){
			
		case GUI_PHASE_SUBSTANTIATOR:
			if(tile != null)
				return new GUIPhaseSubstantiator((ContainerPhaseSubstantiator) ((TilePhaseSubstantiator)tile).createContainer(player));
			
			default:
				return null;
		}
	}

}
