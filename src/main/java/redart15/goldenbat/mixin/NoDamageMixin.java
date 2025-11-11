package redart15.goldenbat.mixin;

import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.achievement.stat.StatList;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import redart15.goldenbat.items.tools.ItemGoldenBat;

@Mixin(value = Player.class, remap = false)
abstract public class NoDamageMixin {

	@Inject(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;getDamageVsEntity(Lnet/minecraft/core/entity/Entity;)I", shift = At.Shift.AFTER))
	private void doNoDamage(Entity entity, CallbackInfo ci) {
		Player asThis = (Player) (Object) this;
		ItemStack itemstack = asThis.getCurrentEquippedItem();
		if (itemstack != null && entity instanceof Mob && itemstack.getItem() instanceof ItemGoldenBat) {
			entity.hurtMarked = true;
			itemstack.hitEntity((Mob) entity, asThis);
		}
		if (entity instanceof Mob && entity.isAlive()) {
			((PlayerAccessor)asThis).invokeAlertWolves((Mob) entity, true);
		}
		asThis.addStat(StatList.mobEncounterStats.get(EntityDispatcher.idForClass(entity.getClass())), 1);
	}

}
