package redart15.goldenbat.items;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Time;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class ItemGoldenBat extends ItemBat {

	public ItemGoldenBat(String name, String namespaceId, int id, ToolMaterial material, double maxValue) {
		super(name, namespaceId, id, material, (float) maxValue);
		this.setMaxDamage(0); // makes the item unbreakable
	}

	@Override
	public boolean isSilkTouch() {
		return false;
	}

	@Override
	public int getDamageVsEntity(@NotNull ItemStack is, @NotNull Entity entity) {
		return 0;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, @NotNull World world, @NotNull Entity entity, int slotId, boolean flag) {
		CompoundTag tag = itemstack.getData();
		if (!tag.containsKey("time")) {
			tag.putLong("time", System.currentTimeMillis());
		}
		if (!tag.containsKey("spam")) {
			tag.putLong("spam", System.currentTimeMillis());
		}
	}

	@Override
	public ItemStack onUse(ItemStack itemstack, @NotNull World world, @NotNull Player player) {
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
			long spamTimer = tag.getLong("spam");
			if (currentTime - spamTimer >= 1250) {
				player.sendMessageTranslated("item.goldenbat.tool.bat.golden.notready");
				tag.putLong("spam", currentTime);
			}
			return itemstack;
		}
	}

}
