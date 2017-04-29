package se.Matryoshika.Echo.Common.Compat.Botania;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.ILensEffect;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.entity.EntityManaBurst;

public class TileLaniaiteSpreader extends TileSpreader{
	
	
	@Override
	public int getMaxMana() {
		//Gaia is Normal pool x 6.4
		//Laniaite is Gaia x 6.4
		return 40960; 
	}
	
	@Override
	public EntityManaBurst getBurst(boolean fake) {
		EntityManaBurst burst = new EntityManaBurst(this, fake);
		//Gaia|Normal 4x
		//Laniaite|Gaia, 6x
		int maxMana = 3840;
		int color = isRedstone() ? 0xFF2020 : 0xFF1111;
		//60-80-120-200 
		int ticksBeforeManaLoss = 200;
		//Gaia|Normal 4x
		float manaLossPerTick = 100F;
		//Increments between tiers = 1x, 1.25x, 1.6x, 
		//Increments increment by 1, 1.25, 1.28 ~> 1.34
		//2F*2.144 ~ 4.3
		float motionModifier = 4.3F;
		float gravity = 0F;
		BurstProperties props = new BurstProperties(maxMana, ticksBeforeManaLoss, manaLossPerTick, gravity, motionModifier, color);

		ItemStack lens = itemHandler.getStackInSlot(0);
		if(lens != null && lens.getItem() instanceof ILensEffect)
			((ILensEffect) lens.getItem()).apply(lens, props);

		burst.setSourceLens(lens);
		if(getCurrentMana() >= props.maxMana || fake) {
			burst.setColor(props.color);
			burst.setMana(props.maxMana);
			burst.setStartingMana(props.maxMana);
			burst.setMinManaLoss(props.ticksBeforeManaLoss);
			burst.setManaLossPerTick(props.manaLossPerTick);
			burst.setGravity(props.gravity);
			burst.setMotion(burst.motionX * props.motionModifier, burst.motionY * props.motionModifier, burst.motionZ * props.motionModifier);

			return burst;
		}
		return null;
	}

}
