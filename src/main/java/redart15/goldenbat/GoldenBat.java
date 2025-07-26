package redart15.goldenbat;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redart15.goldenbat.items.GoldenBatItems;
import redart15.goldenbat.items.tools.Smashables;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.Properties;


public class GoldenBat implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
    public static final String MOD_ID = "goldenbat";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static int itemID;

	static {
		final Properties properties = new Properties();
		properties.setProperty("starting_item_id","23232");
		final ConfigHandler config = new ConfigHandler(MOD_ID,properties);
		itemID = config.getInt("starting_item_id");
		config.updateConfig();
	}

    @Override
    public void onInitialize() {
        LOGGER.info("GoldenBat initialized, now ready to smack!");
		GoldenBatItems.initializeItems();
    }
	@Override public void beforeGameStart() {}
	@Override public void afterGameStart() {}
	@Override public void onRecipesReady() {}
	@Override public void initNamespaces() {}
}
