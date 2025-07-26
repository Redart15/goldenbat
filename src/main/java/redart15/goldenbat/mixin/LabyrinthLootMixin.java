package redart15.goldenbat.mixin;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureLabyrinth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import redart15.goldenbat.items.GoldenBatItems;

import java.util.Random;
@Mixin(value = WorldFeatureLabyrinth.class, remap = false)
public class LabyrinthLootMixin {
	@Shadow
	public WeightedRandomBag<WeightedRandomLootObject> chestLoot;

	@Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/WeightedRandomBag;addEntry(Ljava/lang/Object;D)V", ordinal = 0))
	private void addLoot(final World world, final Random random, final int x, final int y, final int z, final CallbackInfoReturnable<Boolean> cir){
		this.chestLoot.addEntry(new WeightedRandomLootObject(GoldenBatItems.GOLDEN_BAT.getDefaultStack(), 1), 8.0F);
	}
}
