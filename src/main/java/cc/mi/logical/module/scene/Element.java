package cc.mi.logical.module.scene;

public interface Element {
	/** 是否可进入 */
	public boolean checkEnterScene();
	/** 进入场景 */
	public void enterScene();
	/** 元素死亡 */
	public void die();
	/** 改变状态 */
	public void changeState();
	/** 退出场景 */
	public void existScene();
}
