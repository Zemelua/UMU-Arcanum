package io.github.zemelua.umu_arcanum.mixin;

import io.github.zemelua.umu_arcanum.fluid.ModFluids;
import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BottleItem.class)
public abstract class BottleItemMixin extends Item {
	@Deprecated
	public BottleItemMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;",
			at = @At(value = "RETURN",
					ordinal = 3),
			cancellable = true)
	@SuppressWarnings("SpellCheckingInspection")
	private void use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> callback) {
		BlockHitResult hitResult = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

		if (hitResult.getType() == HitResult.Type.BLOCK && level.getFluidState(hitResult.getBlockPos()).is(ModFluids.MANA.get())) {
			callback.setReturnValue(InteractionResultHolder.sidedSuccess(ItemUtils.createFilledResult(
					player.getItemInHand(hand), player, new ItemStack(ModItems.MANA_BOTTLE.get())
			), level.isClientSide()));
		}
	}
}
