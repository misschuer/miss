package cc.mi.logical.module.scene;

import cc.mi.logical.module.scene.utils.Category;
import cc.mi.logical.module.scene.utils.State;

/**
 * 场景中的元素
 * @author misschuer
 */
class AbstractElement implements Element {
	/** 元素类型 */
	private final Category category;
	/** 当前场景 */
	private final int sceneId;
	/** 场景中的id */
	private final int npcId;
	/** 坐标 */
	private final Rect location;
	/** 属性 */
	private final FightAttribute attribute;
	/** 状态 */
	private State state;
	
	public AbstractElement(Category category, int sceneId, int npcId, int x, int y, int width, int height, FightAttribute attribute) {
		this.category = category;
		this.sceneId = sceneId;
		this.npcId = npcId;
		this.location = new Rect(x, y, width, height);
		this.attribute = attribute;
		this.state = State.REST;
	}
	
	/** 能否进入其他场景 */
	public boolean checkEnterScene() {
		// 需要和sceneId比较
		return false;
	}
	
	/** 进入场景 */
	public void enterScene() {
		if (this.category != Category.PLAYER)
			return;
	}
	
	/** 元素死亡 */
	public void die() {
		this.state = State.DIE;
	}
	
	/** 改变状态 */
	public void changeState() {
		if (this.state == State.DIE)
			return;
	}
	
	public void reward() {
		if (this.category != Category.PLAYER)
			return;
		
	}
	
	/** 自动走到target点 */
	public void antoWalk(Position target) {
		
	}
	
	/** 退出场景 */
	public void existScene() {
		if (this.category != Category.PLAYER)
			return;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Category getCategory() {
		return category;
	}

	public int getSceneId() {
		return sceneId;
	}

	public int getNpcId() {
		return npcId;
	}

	public Rect getLocation() {
		return location;
	}

	public FightAttribute getAttribute() {
		return attribute;
	}
}