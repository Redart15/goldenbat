package redart15.goldenbat.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import redart15.goldenbat.items.tools.ItemBat;
import redart15.goldenbat.items.tools.ItemGoldenBat;

@Mixin(value = Mob.class, remap = false)
abstract public  class KnockBackMixin extends Entity  {
	public KnockBackMixin(@Nullable World world) {
		super(world);
	}

	@Inject(method = "knockBack(Lnet/minecraft/core/entity/Entity;IDD)V", at = @At("TAIL"))
	private void additionalKnockback(Entity attacker, int damage, double diff_x, double diff_z, CallbackInfo ci) {
		if (attacker instanceof Player) {
			final ItemStack heldStack = ((Player) attacker).getHeldItem();
			if (heldStack == null) return;

			final Item bat = heldStack.getItem();
			if(!(bat instanceof ItemBat)) return;

			float knockBackStrength = 0.4F, lift = 0.4F;
			if(bat instanceof ItemGoldenBat){
				// 2 * lift + knockBackStrength <= 4.6F

				if(attacker.isSneaking()){
					lift = 1.9F; 					// 4 times
					knockBackStrength = 0.8F; 		// 2 times
				}else{
					knockBackStrength =  2.6F; 		// 6 times
					lift = 1.0F;		 		 	// 2 times
				}
			}

			float horizonalDistance = MathHelper.sqrt(diff_x * diff_x + diff_z * diff_z);

			// half momentum
			this.xd /= 2.0F; // velocity x
			this.yd /= 2.0F; // velocity y
			this.zd /= 2.0F; // velocity z
			this.xd = this.xd - (diff_x / (double) horizonalDistance * (double) knockBackStrength);
			this.yd = this.yd + lift;
			this.zd = this.zd - (diff_z / (double) horizonalDistance * (double) knockBackStrength);
		}
	}
}
