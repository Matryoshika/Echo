package se.Matryoshika.Echo.Client.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import se.Matryoshika.Echo.Common.Content.Tile.Inventory.ContainerPhaseSubstantiator;

public class GUIPhaseSubstantiator extends GuiContainer{
	
	private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("minecraft:textures/gui/container/generic_54.png");


	private static final int TEXT_COLOUR = 0x404040;


	private final IItemHandler playerInventory;


	private final IItemHandler chestInventory;


	private final int numRows;

	public GUIPhaseSubstantiator(ContainerPhaseSubstantiator container) {
		super(container);
		playerInventory = container.getPlayerInventory();
		chestInventory = container.getChestInventory();

		allowUserInput = false;
		numRows = container.getNumRows();
		ySize = 114 + numRows * 18;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 *
	 * @param mouseX Mouse x coordinate
	 * @param mouseY Mouse y coordinate
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		//this.fontRenderer.drawString(this.chestInventory.getDisplayName().getUnformattedText(), 8, 6, TEXT_COLOUR);
		//this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, TEXT_COLOUR);
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 *
	 * @param partialTicks How far into the current tick the game is, with 0.0 being the start of the tick and 1.0 being the end.
	 * @param mouseX       Mouse x coordinate
	 * @param mouseY       Mouse y coordinate
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);

		final int x = (this.width - this.xSize) / 2;
		final int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.numRows * 18 + 17);
		this.drawTexturedModalRect(x, y + this.numRows * 18 + 17, 0, 126, this.xSize, 96);
}

}
