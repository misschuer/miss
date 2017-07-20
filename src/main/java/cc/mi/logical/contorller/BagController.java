package cc.mi.logical.contorller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cc.mi.engine.db.DBOperate;
import cc.mi.engine.db.DBStatedMultiObject;
import cc.mi.engine.db.utils.DBUtils;
import cc.mi.logical.GamePlayer;
import cc.mi.logical.mappin.Item;
import cc.mi.logical.module.Bag.GameBag;

public class BagController extends DBStatedMultiObject<GameBag> implements DBOperate {
	static final Logger logger = Logger.getLogger(BagController.class);
	private GamePlayer player;
	
	public BagController(GamePlayer player) {
		super(200);
		this.player = player;
	}
	
	@Override
	public void load() {
		List<Item> itemList = DBUtils.fecthMany(Item.class, "userId", player.getUserId());
		Map<Integer, GameBag> initHash = new HashMap<>();
		for (Item item : itemList) {
			initHash.put(item.getPosition(), new GameBag(item));
		}
		super.init(initHash);
	}
	
	public void addItem(int itemId, int num) {
		
	}
	
	public void useItem(int position) {
		
	}
}
