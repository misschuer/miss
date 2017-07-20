package cc.mi.logical.module.scene;

import cc.mi.logical.module.scene.utils.Category;

public class Monster extends AbstractElement {
	public Monster(Category category, int sceneId, int npcId, int x, int y, int width, int height, FightAttribute attribute) {
		super(category, sceneId, npcId, x, y, width, height, attribute);
	}

	private int modelId;

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
}
