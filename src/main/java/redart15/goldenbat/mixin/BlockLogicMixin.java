package redart15.goldenbat.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import redart15.goldenbat.helper.BlockSmashResult;
import redart15.goldenbat.items.tools.ItemBat;
import redart15.goldenbat.items.tools.Smashables;
import redart15.goldenbat.items.tools.ItemGoldenBat;

@Mixin(value = BlockLogic.class,remap = false)
public abstract class BlockLogicMixin {

	@Shadow
	@Final
	@NotNull
	public Block<?> block;

	@Shadow
	public abstract void dropBlockWithCause(World world, EnumDropCause cause, int x, int y, int z, int meta, TileEntity tileEntity, Player player);

	@Shadow
	public abstract ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity);

	@Inject(
		method = "harvestBlock(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;IIIILnet/minecraft/core/block/entity/TileEntity;)V",
		at = @At("HEAD"),
		cancellable = true
	)
	private void batHarvest(World world, Player player, int x, int y, int z, int meta, TileEntity tileEntity, final CallbackInfo ci) {
		player.addStat(this.block.getStat("stat_mined"), 1);
		ItemStack heldItemStack = player.inventory.getCurrentItem();
		Item heldItem = heldItemStack != null ? Item.itemsList[heldItemStack.itemID] : null;
		if (heldItem == null || !(heldItem instanceof ItemBat)) {
			return;
		}
		int id = this.block.id();
		if(!Smashables.isSmashable(id)){
			return;
		}
		BlockSmashResult[] SmashResults = Smashables.getSMASHABLES();
		if(SmashResults == null) return;
		for(BlockSmashResult result : SmashResults){
			if (result == null || result.getBlockID() != id) continue;

			NamespaceID ItemNamespaceID = result.getNamespaceID();
			if(ItemNamespaceID == null) 	break;

			// logic to catch how many dye to give
			Item toGive = Item.itemsMap.get(ItemNamespaceID);
			ItemStack[] dropItems = getBreakResult(world,EnumDropCause.PROPER_TOOL,meta,tileEntity);
			int metadata = result.getMetadata(), stacksize = 0;
			if(dropItems == null) break;
			for(ItemStack dropItem : dropItems){
				stacksize += dropItem.stackSize;
			}
			stacksize = stacksize == 0 ? 1 : stacksize;

			world.dropItem(x,y,z,new ItemStack(toGive, stacksize, metadata));
		}
		ci.cancel();
	}
}
