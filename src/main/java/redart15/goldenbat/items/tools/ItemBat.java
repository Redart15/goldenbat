package redart15.goldenbat.items.tools;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
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
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Time;
import net.minecraft.core.world.World;
import org.apache.commons.lang3.tuple.Pair;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.Random;

public class ItemBat extends ItemToolSword {
	public float highStrength = 0.8f;
	public float longStrength = 2.6f;

	public float highLift = 1.9f;
	public float longlift = 1.0f;


	public ItemBat(String name, String namespaceId, int id, ToolMaterial material) {
		super(name, namespaceId, id, material);
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
		if (block == null) {
			return 1.5F;
		}
		return Smashables.instance.isSmashable(block.id()) ? 30.0F : 1.5F;
	}

	@Override
	public int getDamageVsEntity(Entity entity, ItemStack is) {
		return 0;
	}


	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {
		CompoundTag tag = itemstack.getData();
		long time = tag.getLong("time");
		long currentTime = Time.now();
		if (
			currentTime - time >= 5000
				&& !player.isInWater()
				&& !player.isPassenger()
				&& !player.isInLava()
		) {
			tag.putLong("time", currentTime);
			double yRot = player.yRot;
			player.xd = Math.sin(MathHelper.toRadians((float) -yRot));
			player.yd = 0.65f;
			player.zd = Math.cos(MathHelper.toRadians((float) -yRot));
			player.xo = player.x;
			player.yo = player.y;
			player.zo = player.z;
			return itemstack;
		}else {
			long spam_timer = tag.getLong("spam");
			if (currentTime - spam_timer >= 1250 && EnvironmentHelper.isClientWorld()) {
				player.sendTranslatedChatMessage("item.goldenbat.tool.bat.golden.notready");
				tag.putLong("spam", currentTime);
			}
			return itemstack;
		}
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
