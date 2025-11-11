package redart15.goldenbat.mixin;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Player.class, remap = false)
public interface PlayerAccessor {
	@Invoker("alertWolves")
	void invokeAlertWolves(Mob attacker, boolean flag);
}
