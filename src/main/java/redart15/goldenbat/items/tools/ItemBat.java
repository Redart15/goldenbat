package redart15.goldenbat.items.tools;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

public class ItemBat extends ItemToolSword {
	public ItemBat(String name, String namespaceId, int id, ToolMaterial material) {
		super(name, namespaceId, id, material);
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
		if(block == null){
			return 1.5F;
		}
		return Smashables.instance.isSmashable(block.id()) ? 30.0F : 1.5F;
	}
}
