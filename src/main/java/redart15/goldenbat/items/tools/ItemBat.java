package redart15.goldenbat.items.tools;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;

import static redart15.goldenbat.items.tools.Smashables.isSmashable;

public class ItemBat extends Item {
	private final int weaponDamage;

	public ItemBat(String name, String namespaceId, int id, ToolMaterial material) {
		super(name, namespaceId, id);
		this.maxStackSize = 1;
		this.weaponDamage = 2 + material.getDamage() * 2;
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
		if(block == null){
			return 1.5F;
		}
		return isSmashable(block.id()) ? 30.0F : 1.5F;
	}

	@Override
	public int getDamageVsEntity(Entity entity, ItemStack is) {
		return this.weaponDamage;
	}
}
