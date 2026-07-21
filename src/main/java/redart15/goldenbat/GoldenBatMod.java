package redart15.goldenbat;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tag.ItemTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redart15.goldenbat.items.ItemGoldenBat;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.dependency.Key;

import java.util.Properties;


public class GoldenBatMod implements ModInitializer {
	public static final String MOD_ID = HalpLibe.registerMod("goldenbat", true);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static int itemID = 0;
	public static Item GOLDEN_BAT;

	static {
		final Properties properties = new Properties();
		properties.setProperty("starting_item_id", "23232");
		final ConfigHandler config = new ConfigHandler(MOD_ID, properties);
		config.updateConfig();
		itemID = config.getInt("starting_item_id");
	}
	@Override
	public void onInitialize() {
		CommonEvents.AFTER_ITEM_INIT.listen(Key.of(MOD_ID), GoldenBatMod::afterItemInit);
		LOGGER.info("GoldenBat initialized, now ready to smack!");
	}

	private static void afterItemInit() {
		GOLDEN_BAT = new ItemGoldenBat(
			formatTranslationKey("tool.bat.golden"),
			formatName("tool_bat_golden"),
			itemID,
			ToolMaterial.gold,
			4.6f
		).withTags(new Tag[]{ItemTags.PREVENT_CREATIVE_MINING});
//
//
//		GOLDEN_BAT = new ItemBuilder(MOD_ID)
//			.addTags(PREVENT_CREATIVE_MINING)
//			.build(new ItemGoldenBat("tool.bat.golden", MOD_ID + ":item/tool_bat_golden", config.getInt("starting_item_id"), ToolMaterial.gold, 4.6f));
	}


	private static String formatTranslationKey(String key) {
		return String.format("%s.%s", MOD_ID, key);
	}
	private static String formatName(String name) {
		return String.format("%s:item/%s", MOD_ID, name);
	}

}
