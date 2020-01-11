package com.jagex.runescape.media.renderable;

import com.jagex.runescape.cache.def.ItemDefinition;

public class Item extends Renderable {
	public int itemId;
	public int itemCount;

	@Override
	public Model getRotatedModel() {
		ItemDefinition itemDefinition = ItemDefinition.lookup(itemId);
		return itemDefinition.asGroundStack(itemCount);
	}

}
