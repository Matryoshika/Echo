package se.Matryoshika.Echo.Common.Content.Tile.Inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ItemHandlerItem extends ItemHandlerNameable{

	private final ITextComponent defaultName;

	/**
	 * The custom name of this inventory, if any.
	 */
	private ITextComponent displayName;

	public ItemHandlerItem(ITextComponent defaultName) {
		super(defaultName);
		this.defaultName = defaultName.createCopy();
	}

	public ItemHandlerItem(int size, ITextComponent defaultName) {
		super(size, defaultName);
		this.defaultName = defaultName.createCopy();
	}

	public ItemHandlerItem(ItemStack[] stacks, ITextComponent defaultName) {
		super(stacks, defaultName);
		this.defaultName = defaultName.createCopy();
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return getDisplayName().getUnformattedText();
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return displayName != null;
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's username in chat
	 */
	@Override
	public ITextComponent getDisplayName() {
		return hasCustomName() ? displayName : defaultName;
	}

	/**
	 * Set the display name of this inventory.
	 *
	 * @param displayName The display name
	 */
	public void setDisplayName(ITextComponent displayName) {
		this.displayName = displayName.createCopy();
	}

	@Override
	public NBTTagCompound serializeNBT() {
		final NBTTagCompound tagCompound = super.serializeNBT();

		if (hasCustomName()) {
			tagCompound.setString("DisplayName", ITextComponent.Serializer.componentToJson(getDisplayName()));
		}

		return tagCompound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		super.deserializeNBT(nbt);

		if (nbt.hasKey("DisplayName")) {
			setDisplayName(ITextComponent.Serializer.jsonToComponent(nbt.getString("DisplayName")));
		}
	}
	
}
