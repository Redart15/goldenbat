package redart15.goldenbat.items.tools;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.Side;
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
	public int getDamageVsEntity(Entity entity, ItemStack is){
		return 1;
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
		entityplayer.yd = entityplayer.xd = entityplayer.zd = 2;
		return itemstack;
	}

}
