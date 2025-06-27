package redart15.goldenbat.items.tools;
import net.minecraft.core.item.material.ToolMaterial;

public class ItemGoldenBat extends ItemBat {

	public ItemGoldenBat(String name, String namespaceId, int id, ToolMaterial material) {
		super(name, namespaceId, id, material);
		this.setMaxDamage(0); // makes the item unbreakable
	}
}
