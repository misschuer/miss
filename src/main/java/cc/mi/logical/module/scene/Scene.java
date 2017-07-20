package cc.mi.logical.module.scene;

import java.util.HashMap;
import java.util.Map;

import cc.mi.logical.module.scene.utils.Category;

public class Scene {
	private int allocId;
	private Map<Integer, Element> elementHash;
//	private SceneMap currentMap;
	
	public Scene(int width, int height) {
		elementHash = new HashMap<>();
//		currentMap = new SceneMap(width, height);
		allocId = 0;
	}
	
	/**
	 * 创建一个元素
	 */
	public void createElement(Category category, int x, int y, int sceneId, FightAttribute attribute) {
		if (category == Category.PLAYER) {
			createPlayer(x, y, sceneId, attribute);
		}
	}
	
	public void createPlayer(int x, int y, int sceneId, FightAttribute attribute) {
		Element player = new Player(Category.PLAYER, sceneId, ++allocId, x, y, 1, 1, attribute);
		putToHash(allocId, player);
	}
	
	
	
	
	private void putToHash(int id, Element element) {
		elementHash.put(id, element);
	}
	
	/** 
	 * 删除一个元素 
	 * FIXME 未做完
	 **/
	public void deleteElement() {
		
	}
}
