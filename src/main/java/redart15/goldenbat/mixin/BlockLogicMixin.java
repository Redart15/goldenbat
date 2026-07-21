package redart15.goldenbat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import redart15.goldenbat.items.ItemBat;
import redart15.goldenbat.items.Smashables;

@Mixin(value = BlockLogic.class, remap = false)
public abstract class BlockLogicMixin {
	@Shadow
	@Final
	@NotNull
	public Block<?> block;

	@Shadow
	public abstract ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity);

	@WrapOperation(
		method = "onHarvest",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/BlockLogic;dropWithCause(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;Lnet/minecraft/core/world/pos/TilePosc;ILnet/minecraft/core/block/entity/TileEntity;Lnet/minecraft/core/entity/player/Player;)V")
	)
	private void batHarvest(
		BlockLogic instance, World world,
		EnumDropCause dropCause, TilePosc tilePosc,
		int data, TileEntity tileEntity,
		Player player, Operation<Void> original
	) {
		int id = this.block.id();
		ItemStack itemStack = player.getHeldItem();
		if (itemStack == null || !(itemStack.getItem() instanceof ItemBat) || !Smashables.instance.isSmashable(id)) {
			original.call(instance, world, dropCause, tilePosc, data, tileEntity, player);
			return;
		}
		ItemStack[] dropItems = this.getBreakResult(world, EnumDropCause.PROPER_TOOL, data, tileEntity);
		if (dropItems == null) return;
		ObjectIntPair<NamespaceID> result = Smashables.instance.getEntry(id);
		if (result.left() == null) {
			original.call(instance, world, dropCause, tilePosc, data, tileEntity, player);
			return;
		}
		Item toGive = Item.itemsMap.get(result.left());
		int stacksize = 0;
		for (ItemStack dropItem : dropItems) {
			stacksize += dropItem.stackSize;
		}
		stacksize = stacksize == 0 ? 1 : stacksize;
		world.dropItem(tilePosc, new ItemStack(toGive, stacksize, result.rightInt()));
	}
}
