package redart15.goldenbat.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import redart15.goldenbat.items.tools.ItemBat;
import redart15.goldenbat.items.tools.Smashables;

@Mixin(value = BlockLogic.class,remap = false)
public abstract class BlockLogicMixin {
	@Shadow
	@Final
	@NotNull
	public Block<?> block;

	@Shadow
	public abstract ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity);

	@Inject(
		method = "harvestBlock(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;IIIILnet/minecraft/core/block/entity/TileEntity;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/BlockLogic;dropBlockWithCause(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;IIIILnet/minecraft/core/block/entity/TileEntity;Lnet/minecraft/core/entity/player/Player;)V"),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private void batHarvest(World world, Player player, int x, int y, int z, int meta, TileEntity tileEntity, final CallbackInfo ci, @Local Item heldItem) {
		int id = this.block.id();
		if (!(heldItem instanceof ItemBat) || !Smashables.instance.isSmashable(id)) {
			return;
		}
		ItemStack[] dropItems = this.getBreakResult(world,EnumDropCause.PROPER_TOOL,meta,tileEntity);
		if(dropItems == null) return;
		Pair<NamespaceID, Integer> result = Smashables.instance.getEntry(id);
		if(result.getLeft() == null) {
			ci.cancel();
			return;
		}
		Item toGive = Item.itemsMap.get(result.getLeft());
		int stacksize = 0;
		for(ItemStack dropItem : dropItems){
			stacksize += dropItem.stackSize;
		}
		stacksize = stacksize == 0 ? 1 : stacksize;
		world.dropItem(x,y,z,new ItemStack(toGive, stacksize, result.getRight()));
		ci.cancel();
	}
}
