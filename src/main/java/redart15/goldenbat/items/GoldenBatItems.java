package redart15.goldenbat.items;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.material.ToolMaterial;
import redart15.goldenbat.GoldenBat;
import redart15.goldenbat.items.tools.ItemGoldenBat;
import turniplabs.halplibe.helper.ItemBuilder;

import static net.minecraft.core.item.tag.ItemTags.PREVENT_CREATIVE_MINING;
import static redart15.goldenbat.GoldenBat.MOD_ID;

public class GoldenBatItems {
	public static Item GOLDEN_BAT;
	public static void initializeItems(){
		GOLDEN_BAT = new ItemBuilder(MOD_ID)
			.build(new ItemGoldenBat("tool.bat.golden", MOD_ID + ":item/tool_bat_golden", GoldenBat.itemID++,ToolMaterial.gold)).withTags(new Tag[]{PREVENT_CREATIVE_MINING});
	}
}
