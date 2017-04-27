package se.Matryoshika.Echo.Client.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandler;
import se.Matryoshika.Echo.Common.Content.Tile.TilePhaseSubstantiator;
import se.Matryoshika.Echo.Common.Content.Tile.Inventory.ContainerPhaseSubstantiator;

public class GUIPhaseSubstantiator extends GuiContainer{
	
	TilePhaseSubstantiator tile;
	Container container;
	private static ResourceLocation TEXTURE;
	
	public GUIPhaseSubstantiator(Container cont, TilePhaseSubstantiator tile){
		super(cont);
		this.tile = tile;
		TEXTURE = new ResourceLocation("echo:textures/gui/phase_substantiator.png");
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		//this.fontRendererObj.drawString(new TextComponentTranslation(tile.getName()).getUnformattedText(), 8, 6, 4210752);
		this.fontRendererObj.drawString(new TextComponentTranslation("container.inventory").getUnformattedText(), 8, this.ySize - 96 + 5, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		this.mc.getTextureManager().bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
