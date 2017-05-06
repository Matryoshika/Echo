package se.Matryoshika.Echo.Common.Content.Items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import se.Matryoshika.Echo.Echo;

public class ItemUpgrade extends Item{
	
	public static enum Upgrade{
		PLAYER_KILL,
		FORTUNE_1,
		FORTUNE_2,
		FORTUNE_3,
		FORTUNE_4,
		FORTUNE_5,
		FORTUNE_6,
		BEHEADING,
		REDSTONE
	}
	
	public ItemUpgrade(){
		setRegistryName(Echo.MODID, "upgrade");
		hasSubtypes = true;
		setMaxDamage(0);
		
		this.addPropertyOverride(new ResourceLocation("meta"), new IItemPropertyGetter(){
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return stack.getMetadata();
            }
        });
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
        int i = stack.getMetadata();
        return "item.echo:" + Upgrade.values()[i].toString().toLowerCase();
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems){
        for (int i = 0; i < Upgrade.values().length; ++i)
            subItems.add(new ItemStack(item, 1, i));
    }

}
