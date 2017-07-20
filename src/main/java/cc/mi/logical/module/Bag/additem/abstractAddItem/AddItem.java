package cc.mi.logical.module.Bag.additem.abstractAddItem;

import cc.mi.logical.GamePlayer;

public interface AddItem {
	public void add(GamePlayer player, int num);
	
	public int getItemId();

	public String getName();
}
