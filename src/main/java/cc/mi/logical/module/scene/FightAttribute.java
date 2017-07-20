package cc.mi.logical.module.scene;

public class FightAttribute {
	private final int hp;
	private final int atk;
	private final int def;
	
	public FightAttribute(int hp, int atk, int def) {
		this.hp  =  hp;
		this.atk = atk;
		this.def = def;
	}

	public int getHp() {
		return hp;
	}

	public int getAtk() {
		return atk;
	}

	public int getDef() {
		return def;
	}
}