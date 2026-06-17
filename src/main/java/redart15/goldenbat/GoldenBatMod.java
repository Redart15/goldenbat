package redart15.goldenbat;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.material.ToolMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redart15.goldenbat.items.ItemGoldenBat;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.Properties;

import static net.minecraft.core.item.tag.ItemTags.PREVENT_CREATIVE_MINING;


public class GoldenBatMod implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
    public static final String MOD_ID = HalpLibe.registerMod("goldenbat", true);
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Item GOLDEN_BAT;

    static {
		final Properties properties = new Properties();
		properties.setProperty("starting_item_id","23232");
		final ConfigHandler config = new ConfigHandler(MOD_ID,properties);
		config.updateConfig();
		GOLDEN_BAT = new ItemBuilder(MOD_ID)
			.addTags(PREVENT_CREATIVE_MINING)
			.build(new ItemGoldenBat("tool.bat.golden", MOD_ID + ":item/tool_bat_golden", config.getInt("starting_item_id"), ToolMaterial.gold, 4.6f));
	}

    @Override public void onInitialize() 	{LOGGER.info("GoldenBat initialized, now ready to smack!");}
	@Override public void beforeGameStart() {/* no need */}
	@Override public void afterGameStart() 	{/* no need */}
	@Override public void onRecipesReady() 	{/* no need */}
	@Override public void initNamespaces() 	{/* no need */}
}
