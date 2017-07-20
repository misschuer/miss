package cc.mi.logical.module.scene;

import cc.mi.logical.module.scene.utils.Category;

public class Player extends AbstractElement {
	private int userId;
	
	public Player(Category category, int sceneId, int npcId, int x, int y, int width, int height, FightAttribute attribute) {
		super(category, sceneId, npcId, x, y, width, height, attribute);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
