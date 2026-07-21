package redart15.goldenbat.items;

import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityActivator;
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
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

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
	public float getStrVsBlock(@NotNull ItemStack itemstack, @NotNull Block<?> block) {
		return Smashables.instance.isSmashable(block.id()) ? 30.0F : 1.5F;
	}

	@Override
	public void onUseByActivator(
		@NotNull ItemStack itemStack,
		@NotNull World world,
		@NotNull TileEntityActivator activator,
		@NotNull Random random,
		@NotNull TilePosc blockPos,
		@NotNull Direction direction,
		double offX, double offY, double offZ

	) {
		TilePos pos = blockPos.add(direction, new TilePos());
		Block<?> block = world.getBlockType(pos);

		if (Smashables.instance.isSmashable(block.id())) {
			int metadata = world.getBlockData(pos);
			world.notifyBlockChange(pos, Blocks.AIR);
			world.playBlockSoundEffect(null, pos.x(), pos.y(), pos.z(), block, EnumBlockSoundEffectType.MINE);
			ItemStack[] dropItems = block.getBreakResult(world, EnumDropCause.PROPER_TOOL, metadata, null);
			if (dropItems == null) return;
			ObjectIntPair<NamespaceID> result = Smashables.instance.getEntry(block.id());
			if (result.left() == null) return;
			Item toGive = Item.itemsMap.get(result.left());
			int stacksize = 0;
			for (ItemStack dropItem : dropItems) {
				stacksize += dropItem.stackSize;
			}
			stacksize = stacksize == 0 ? 1 : stacksize;
			world.dropItem(pos, new ItemStack(toGive, stacksize, result.rightInt()));
			itemStack.damageItem(1, null);
		}
	}

	@Override
	public boolean hitEntity(@NotNull ItemStack itemstack, @NotNull Mob target, Mob attacker) {
		if (attacker.isSneaking() && attacker instanceof Player) {
			MobUtil.knockback(target, attacker, highStrength, highLift);
		} else {
			MobUtil.knockback(target, attacker, longStrength, longlift);
		}
		return super.hitEntity(itemstack, target, attacker);
	}
}
