package cc.mi.logical.module.Bag.additem.abstractAddItem;

public abstract class AbstractAddItem implements AddItem {
	private final int itemId;
	private final String name;
	
	public AbstractAddItem(int itemId) {
		this.itemId = itemId;
		this.name = ""; //FIXME 通过读表
	}

	public int getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}
}
