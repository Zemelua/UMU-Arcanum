package io.github.zemelua.umu_arcanum.item;

import io.github.zemelua.umu_arcanum.fluid.ModFluids;
import io.github.zemelua.umu_arcanum.util.IDrinkableItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.PotionEvent;

public class ManaBucketItem extends BucketItem implements IDrinkableItem {
	public ManaBucketItem(Properties properties) {
		super(ModFluids.MANA, properties);
	}

	@Override
	public Item getSelf() {
		return this;
	}

	@Override
	public void onDrunk(ItemStack itemStack, Level level, LivingEntity living) {
		living.curePotionEffects(itemStack);
		living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 300));
		living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		InteractionResultHolder<ItemStack> resultHolder = super.use(level, player, hand);

		if (resultHolder.getResult() == InteractionResult.PASS || player.isUsingItem()) {
			return IDrinkableItem.super.use(level, player, hand);
		}

		return resultHolder;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity living) {
		return IDrinkableItem.super.finishUsingItem(itemStack, level, living, new ItemStack(Items.BUCKET));
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return IDrinkableItem.super.getUseDuration(itemStack);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return IDrinkableItem.super.getUseAnimation(itemStack);
	}

	protected static void onPotionAdded(PotionEvent.PotionAddedEvent event) {
		MobEffectInstance effectInstance = event.getPotionEffect();

		if (effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
			effectInstance.addCurativeItem(new ItemStack(ModItems.MANA_BUCKET.get()));
		}
	}
}
