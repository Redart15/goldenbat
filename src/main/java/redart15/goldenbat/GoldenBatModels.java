package redart15.goldenbat;

import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import turniplabs.halplibe.util.ModelEntrypoint;

import static net.minecraft.client.render.item.model.ItemModelDispatcher.*;
import static redart15.goldenbat.GoldenBatMod.GOLDEN_BAT;

public class GoldenBatModels implements ModelEntrypoint {
	@Override
	public void initItemModels(ItemModelDispatcher itemModelDispatcher) {
		itemModelDispatcher.addDispatch(new ItemModelStandard(GOLDEN_BAT, true)
			.setDisplayPos("firstperson_righthand", HANDHELD_FIRST_PERSON_RIGHT_HAND)
			.setDisplayPos("firstperson_lefthand", HANDHELD_FIRST_PERSON_LEFT_HAND)
			.setDisplayPos("thirdperson_righthand", HANDHELD_THIRD_PERSON_RIGHT_HAND)
			.setDisplayPos("thirdperson_lefthand", HANDHELD_THIRD_PERSON_LEFT_HAND));
	}

	@Override public void initBlockModels(BlockModelDispatcher blockModelDispatcher) 					{/* no need */}
	@Override public void initEntityModels(EntityRendererDispatcher entityRenderDispatcher) 			{/* no need */}
	@Override public void initTileEntityModels(TileEntityRenderDispatcher tileEntityRenderDispatcher) 	{/* no need */}
	@Override public void initBlockColors(BlockColorDispatcher blockColorDispatcher) 					{/* no need */}
}
