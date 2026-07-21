package redart15.goldenbat;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.util.dependency.Key;

import static net.minecraft.client.render.item.model.ItemModelDispatcher.*;
import static redart15.goldenbat.GoldenBatMod.GOLDEN_BAT;
import static redart15.goldenbat.GoldenBatMod.MOD_ID;

public class GoldenBatClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientEvents.ITEM_MODEL_RELOAD.listen(Key.of(MOD_ID), GoldenBatClient::initItemModels);
	}

	public static void initItemModels(ItemModelDispatcher itemModelDispatcher) {
		itemModelDispatcher.addDispatch(new ItemModelStandard(GOLDEN_BAT, true)
			.setDisplayPos("firstperson_righthand", HANDHELD_FIRST_PERSON_RIGHT_HAND)
			.setDisplayPos("firstperson_lefthand", HANDHELD_FIRST_PERSON_LEFT_HAND)
			.setDisplayPos("thirdperson_righthand", HANDHELD_THIRD_PERSON_RIGHT_HAND)
			.setDisplayPos("thirdperson_lefthand", HANDHELD_THIRD_PERSON_LEFT_HAND));
	}
}
