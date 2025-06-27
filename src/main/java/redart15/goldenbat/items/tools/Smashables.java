package redart15.goldenbat.items.tools;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Items;
import redart15.goldenbat.helper.BlockSmashResult;

import static redart15.goldenbat.helper.BlockSmashResult.BlockSmashResultBuilder;

// Helper Class for ItemGoldenBat
// require to be loaded after Items creation.

public class Smashables {
	private static BlockSmashResult[] SMASHABLES;

	public static void initializeSmashables(){
		SMASHABLES = new BlockSmashResult[]{
			new BlockSmashResultBuilder().setBlockID(Blocks.GLASS.id()).build(),
			new BlockSmashResultBuilder().setBlockID(Blocks.TRAPDOOR_GLASS.id()).build(),
			new BlockSmashResultBuilder().setBlockID(Blocks.DOOR_GLASS_BOTTOM.id()).build(),
			new BlockSmashResultBuilder().setBlockID(Blocks.DOOR_GLASS_TOP.id()).build(),
			new BlockSmashResultBuilder().setBlockID(Blocks.PUMPKIN_PIE.id()).build(),
			new BlockSmashResultBuilder().setBlockID((Blocks.CACTUS.id())).build(),
			new BlockSmashResultBuilder().setBlockID(Blocks.SUGARCANE.id()).build(),
			new BlockSmashResultBuilder().setBlockID(Blocks.PUMPKIN.id()).setnamespaceID(Items.SEEDS_PUMPKIN.namespaceID).setAmount(1).build(),
			new BlockSmashResultBuilder().setBlockID(Blocks.PUMPKIN_PIE.id()).setnamespaceID(Items.SEEDS_PUMPKIN.namespaceID).setAmount(1).build(),
			new BlockSmashResultBuilder().setBlockID(Blocks.PUMPKIN_CARVED_IDLE.id()).setnamespaceID(Items.SEEDS_PUMPKIN.namespaceID).setAmount(1).build(),
			new BlockSmashResultBuilder().setMetadata(12).setBlockID(Blocks.FLOWER_LIGHT_BLUE.id()).setnamespaceID(Items.DYE.namespaceID).setAmount(1).build(),
			new BlockSmashResultBuilder().setMetadata(14).setBlockID(Blocks.FLOWER_ORANGE.id()).setnamespaceID(Items.DYE.namespaceID).setAmount(1).build(),
			new BlockSmashResultBuilder().setMetadata(9).setBlockID(Blocks.FLOWER_PINK.id()).setnamespaceID(Items.DYE.namespaceID).setAmount(1).build(),
			new BlockSmashResultBuilder().setMetadata(5).setBlockID(Blocks.FLOWER_PURPLE.id()).setnamespaceID(Items.DYE.namespaceID).setAmount(1).build(),
			new BlockSmashResultBuilder().setMetadata(1).setBlockID(Blocks.FLOWER_RED.id()).setnamespaceID(Items.DYE.namespaceID).setAmount(1).build(),
			new BlockSmashResultBuilder().setMetadata(11).setBlockID(Blocks.FLOWER_YELLOW.id()).setnamespaceID(Items.DYE.namespaceID).setAmount(1).build(),
		};
	}

	public static boolean isSmashable(int id) {
		for (BlockSmashResult Smashable : SMASHABLES) {
			int smashable = Smashable.getBlockID();
			if (smashable == id){
				return true;
			}
		}
		return false;
	}

	public static BlockSmashResult[] getSMASHABLES() {
		return SMASHABLES;
	}
}
