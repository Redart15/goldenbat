package redart15.goldenbat.items.tools;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Random;

public class ItemBat extends ItemToolSword {
	/// 2 * lift + knockbackStrength <= maxValue </br>
	public final double highStrength;
	public final double highLift;
	public final double longStrength;
	public final double longlift;

	public ItemBat(String name, String namespaceId, int id, ToolMaterial material, double maxValue) {
		super(name, namespaceId, id, material);
		maxValue = Math.abs(maxValue);
		this.highStrength = 1.0 / 5.0f * maxValue;
		this.highLift = 2.0 / 5.0f * maxValue;
		this.longStrength = 3.0 / 5.0f * maxValue;
		this.longlift = 1.0 / 5.0f * maxValue;
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
		if (block == null) {
			return 1.5F;
		}
		return Smashables.instance.isSmashable(block.id()) ? 30.0F : 1.5F;
	}

	@Override
	public void onUseByActivator(ItemStack itemStack, TileEntityActivator activatorBlock, World world, Random random, int blockX, int blockY, int blockZ, double offX, double offY, double offZ, Direction direction) {
		blockX += direction.getOffsetX();
		blockY += direction.getOffsetY();
		blockZ += direction.getOffsetZ();
		Block<?> block = world.getBlock(blockX, blockY, blockZ);
		if (block != null && Smashables.instance.isSmashable(block.id())) {
			int metadata = world.getBlockMetadata(blockX, blockY, blockZ);
			world.setBlockWithNotify(blockX, blockY, blockZ, 0);
			world.playBlockSoundEffect(null, blockX, blockY, blockZ, block, EnumBlockSoundEffectType.MINE);
			ItemStack[] dropItems = block.getBreakResult(world, EnumDropCause.PROPER_TOOL, metadata, null);
			if (dropItems == null) return;
			Pair<NamespaceID, Integer> result = Smashables.instance.getEntry(block.id());
			if (result.getLeft() == null) return;
			Item toGive = Item.itemsMap.get(result.getLeft());
			int stacksize = 0;
			for (ItemStack dropItem : dropItems) {
				stacksize += dropItem.stackSize;
			}
			stacksize = stacksize == 0 ? 1 : stacksize;
			world.dropItem(blockX, blockY, blockZ, new ItemStack(toGive, stacksize, result.getRight()));
			itemStack.damageItem(1, null);
		}
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
		if (attacker.isSneaking() && attacker instanceof Player) {
			MobUtil.knockback(target, attacker, highStrength, highLift);
		} else {
			MobUtil.knockback(target, attacker, longStrength, longlift);
		}
		return super.hitEntity(itemstack, target, attacker);
	}
}
