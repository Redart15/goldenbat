package redart15.goldenbat.mixin;

import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.achievement.stat.StatList;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import redart15.goldenbat.items.tools.ItemGoldenBat;

@Mixin(value = Player.class, remap = false)
public abstract class NoDamageMixin extends Mob {

	public NoDamageMixin(World world) {
		super(world);
	}

	@Shadow
	public ContainerInventory inventory;

	@Shadow
	public abstract Gamemode getGamemode();

	@Shadow
	public abstract ItemStack getCurrentEquippedItem();

	@Shadow
	public abstract void destroyCurrentEquippedItem();

	@Shadow
	public abstract void addStat(Stat statbase, int i);

	@Shadow
	protected abstract void alertWolves(Mob attacker, boolean flag);

	@Shadow
	public abstract ItemStack getHeldItem();

	@Inject(
		method = "attackTargetEntityWithCurrentItem(Lnet/minecraft/core/entity/Entity;)V",
		at = @At("HEAD"),
		cancellable = true
	)
	public void attackTargetEntityWithBat(Entity entity, CallbackInfo ci) {
		if (this.getGamemode().canInteract()) {
			final ItemStack heldStack = this.getHeldItem();
			if (heldStack == null) {
				return;
			}
			Item held = heldStack.getItem();
			if (!(heldStack.getItem() instanceof ItemGoldenBat)) {
				return;
			}

			entity.hurt(this, 0, DamageType.COMBAT);
			ItemStack itemstack = this.getCurrentEquippedItem();
			if (itemstack != null && entity instanceof Mob) {
				itemstack.hitEntity((Mob) entity, (Player) (Object) this);
				if (itemstack.stackSize <= 0) {
					this.destroyCurrentEquippedItem();
				}
			}

			if (entity instanceof Mob) {
				if (entity.isAlive()) {
					this.alertWolves((Mob) entity, true);
				}

				this.addStat(StatList.damageDealtStat, 0);
			}
		}

		this.addStat((Stat) StatList.mobEncounterStats.get(EntityDispatcher.idForClass(entity.getClass())), 1);
		ci.cancel();
	}
}
