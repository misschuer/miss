package cc.mi.logical.module.Bag;

import cc.mi.engine.db.DBState;
import cc.mi.logical.mappin.Item;

public class GameBag extends DBState {
	private final Item item;
	
	public GameBag(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	@Override
	public Object getMappinData() {
		return this.item;
	}
}
