package redart15.goldenbat.items;

import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DyeColor;

import java.util.HashMap;
import java.util.Map;

/// Helper Class for ItemGoldenBat
/// require to be loaded after Items creation.

public final class Smashables {
	public static final Smashables instance = new Smashables();
	private final Map<Integer, ObjectIntPair<NamespaceID>> smashables = new HashMap<>();


	private Smashables() {
		this.register();
	}

	private void register() {
		this.addEntry(Blocks.GLASS.id());
		this.addEntry(Blocks.GLASS.id());
		this.addEntry(Blocks.TRAPDOOR_GLASS.id());
		this.addEntry(Blocks.DOOR_GLASS_BOTTOM.id());
		this.addEntry(Blocks.DOOR_GLASS_TOP.id());
		this.addEntry(Blocks.PUMPKIN_PIE.id());
		this.addEntry(Blocks.CACTUS.id());
		this.addEntry(Blocks.SUGARCANE.id());
		this.addEntry(Blocks.SUGARCANE.id());
		this.addEntry(Blocks.PUMPKIN.id(),Items.SEEDS_PUMPKIN.namespaceID);
		this.addEntry(Blocks.PUMPKIN_PIE.id(),Items.SEEDS_PUMPKIN.namespaceID);
		this.addEntry(Blocks.PUMPKIN_CARVED_IDLE.id(),Items.SEEDS_PUMPKIN.namespaceID);
		this.addEntry(Blocks.FLOWER_LIGHT_BLUE.id(),Items.DYE.namespaceID, DyeColor.LIGHT_BLUE.itemMeta);
		this.addEntry(Blocks.FLOWER_ORANGE.id(),Items.DYE.namespaceID, DyeColor.ORANGE.itemMeta);
		this.addEntry(Blocks.FLOWER_PINK.id(),Items.DYE.namespaceID, DyeColor.PINK.itemMeta);
		this.addEntry(Blocks.FLOWER_PURPLE.id(),Items.DYE.namespaceID, DyeColor.PURPLE.itemMeta);
		this.addEntry(Blocks.FLOWER_RED.id(),Items.DYE.namespaceID, DyeColor.RED.itemMeta);
		this.addEntry(Blocks.FLOWER_YELLOW.id(),Items.DYE.namespaceID, DyeColor.YELLOW.itemMeta);
	}

	public void addEntry(int blockID) {
		this.smashables.put(blockID, ObjectIntPair.of(null, 0));
	}

	public void addEntry(int blockID, NamespaceID itemID) {
		this.smashables.put(blockID, ObjectIntPair.of(itemID, 0));
	}

	public void addEntry(int blockID, NamespaceID itemID, int metadata) {
		this.smashables.put(blockID, ObjectIntPair.of(itemID, metadata));
	}

	public ObjectIntPair<NamespaceID> getEntry(int blockID){
		return this.smashables.get(blockID);
	}

	public boolean isSmashable(int blockID){
		return this.smashables.containsKey(blockID);
	}
}
