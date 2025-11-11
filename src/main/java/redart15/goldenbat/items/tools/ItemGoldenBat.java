package redart15.goldenbat.items.tools;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.world.World;

public class ItemGoldenBat extends ItemBat {

	public ItemGoldenBat(String name, String namespaceId, int id, ToolMaterial material) {
		super(name, namespaceId, id, material);
		this.setMaxDamage(0); // makes the item unbreakable
	}

	@Override
	public boolean isSilkTouch() {
		return false;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
		CompoundTag tag = itemstack.getData();
		if (!tag.containsKey("time")) {
			tag.putLong("time", System.currentTimeMillis());
		}
		if (!tag.containsKey("spam")) {
			tag.putLong("spam", System.currentTimeMillis());
		}
	}

}
