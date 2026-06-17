package redart15.goldenbat.mixin;

import net.minecraft.core.achievement.stat.StatList;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import redart15.goldenbat.items.ItemGoldenBat;

@Mixin(value = Player.class, remap = false)
public abstract class NoDamageMixin {

	@Inject(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;getDamageVsEntity(Lnet/minecraft/core/entity/Entity;)I", shift = At.Shift.AFTER))
	private void doNoDamage(Entity entity, CallbackInfo ci) {
		Player asThis = (Player) (Object) this;
		ItemStack itemstack = asThis.getCurrentEquippedItem();
		if (itemstack != null && entity instanceof Mob mob && itemstack.getItem() instanceof ItemGoldenBat) {
			entity.hurtMarked = true;
			itemstack.hitEntity(mob, asThis);
		}
		if (entity instanceof Mob mob && entity.isAlive()) {
			((PlayerAccessor)asThis).invokeAlertWolves(mob, true);
		}
		asThis.addStat(StatList.mobEncounterStats.get(entity.getDispatcherId()), 1);
	}

}
