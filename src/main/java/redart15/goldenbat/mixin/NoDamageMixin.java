//package redart15.goldenbat.mixin;
//
//import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//import net.minecraft.core.achievement.stat.Stat;
//import net.minecraft.core.entity.Entity;
//import net.minecraft.core.entity.player.Player;
//import net.minecraft.core.item.ItemStack;
//import net.minecraft.core.util.helper.DamageType;
//import net.minecraft.core.util.helper.MathHelper;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//import redart15.goldenbat.Helper;
//import redart15.goldenbat.items.tools.ItemGoldenBat;
//
//@Mixin(value = Player.class, remap = false)
//abstract public class NoDamageMixin {

//	private final Helper helper = new Helper();

//	@WrapOperation(
//		method = "attackTargetEntityWithCurrentItem",
//		at = @At(
//			value = "INVOKE",
//			target = "Lnet/minecraft/core/entity/Entity;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"
//		)
//	)
//	private boolean attackTargetWithBat(Entity instance, Entity attacker, int baseDamage, DamageType type, Operation<Boolean> original) {
//		ItemStack itemStack = ((Player) attacker).getCurrentEquippedItem();
//		if (itemStack != null && itemStack.getItem() instanceof ItemGoldenBat) {
////			helper.mySiliyKnockback(attacker, instance);
////			float knockBackStrength = 0.4F, lift = 0.4F;
////			if (attacker.isSneaking()) {
////				lift = 1.9F;                    // 4 times
////				knockBackStrength = 0.8F;        // 2 times
////			} else {
////				knockBackStrength = 2.6F;        // 6 times
////				lift = 1.0F;                    // 2 times
////			}
////
////			double diff_x = attacker.x - instance.x;
////			double diff_z;
////			for (diff_z = attacker.z - instance.z; diff_x * diff_x + diff_z * diff_z < 1.0E-4; diff_z = (Math.random() - Math.random()) * 0.01) {
////				diff_x = (Math.random() - Math.random()) * 0.01;
////			}
////			float horizonalDistance = MathHelper.sqrt(diff_x * diff_x + diff_z * diff_z);
////			instance.fling(
////				-instance.xd / 2.0F - (diff_x / (double) horizonalDistance * (double) knockBackStrength),
////				-instance.yd / 2.0F + lift + 0.2F,
////				-instance.xd / 2.0F - (diff_z / (double) horizonalDistance * (double) knockBackStrength),
////				1
////			);
////
////
////			instance.xd /= 2.0F; // velocity x
////			instance.yd /= 2.0F; // velocity y
////			instance.zd /= 2.0F; // velocity z
////			instance.xd = instance.xd - (diff_x / (double) horizonalDistance * (double) knockBackStrength);
////			instance.yd = instance.yd + lift;
////			instance.zd = instance.zd - (diff_z / (double) horizonalDistance * (double) knockBackStrength);
//			return original.call(instance, attacker, 0, type);
////			return true;
//		}
//		return original.call(instance, attacker, baseDamage, type);
//	}
//
////	@Unique
////	public void mySiliyKnockback(Entity attacker, Entity instance) {
////		helper.mySiliyKnockback(attacker, instance);
////	}
//
//
//	@Inject(
//		method = "attackTargetEntityWithCurrentItem",
//		at = @At(
//			value = "INVOKE",
//			target = "Lnet/minecraft/core/entity/Entity;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z",
//			shift = At.Shift.AFTER
//		)
//	)
//	public void knockback(Entity thiis, CallbackInfo ci) {
//		Player attacker = (Player) (Object) this;
//
//		float knockBackStrength = 0.4F, lift = 0.4F;
//		if (attacker.isSneaking()) {
//			lift = 1.9F;                    // 4 times
//			knockBackStrength = 0.8F;        // 2 times
//		} else {
//			knockBackStrength = 2.6F;        // 6 times
//			lift = 1.0F;                    // 2 times
//		}
//
//		double diff_x = attacker.x - thiis.x;
//		double diff_z;
//		for (diff_z = attacker.z - thiis.z; diff_x * diff_x + diff_z * diff_z < 1.0E-4; diff_z = (Math.random() - Math.random()) * 0.01) {
//			diff_x = (Math.random() - Math.random()) * 0.01;
//		}
//		float horizonalDistance = MathHelper.sqrt(diff_x * diff_x + diff_z * diff_z);
////			thiis.fling(
////				-thiis.xd / 2.0F - (diff_x / (double) horizonalDistance * (double) knockBackStrength),
////				-thiis.yd / 2.0F + lift + 0.2F,
////				-thiis.xd / 2.0F - (diff_z / (double) horizonalDistance * (double) knockBackStrength),
////				1
////			);
//
//
//		thiis.xd /= 2.0F; // velocity x
//		thiis.yd /= 2.0F; // velocity y
//		thiis.zd /= 2.0F; // velocity z
//		thiis.xd = thiis.xd - (diff_x / (double) horizonalDistance * (double) knockBackStrength);
//		thiis.yd = thiis.yd + lift;
//		thiis.zd = thiis.zd - (diff_z / (double) horizonalDistance * (double) knockBackStrength);
//	}
//
//
//	@WrapOperation(
//		method = "attackTargetEntityWithCurrentItem",
//		at = @At(
//			value = "INVOKE",
//			target = "Lnet/minecraft/core/entity/player/Player;addStat(Lnet/minecraft/core/achievement/stat/Stat;I)V",
//			ordinal = 0
//		)
//	)
//	private void addZeroStats(Player instance, Stat statbase, int baseDamage, Operation<Void> original) {
//		ItemStack itemStack = ((Player) instance).getCurrentEquippedItem();
//		if (itemStack != null && itemStack.getItem() instanceof ItemGoldenBat) {
//			original.call(instance, statbase, 0);
//			return;
//		}
//		original.call(instance, statbase, baseDamage);
//	}
//}
