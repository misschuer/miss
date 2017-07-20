package cc.mi.logical.source;

public enum DecreaseSource {
	USEITEM(1001, "消耗道具");
	
	private final int id;
	private final String desc;
	
	private DecreaseSource(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}
}
