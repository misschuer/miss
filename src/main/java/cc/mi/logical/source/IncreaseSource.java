package cc.mi.logical.source;

public enum IncreaseSource {
	ADDITEM(1, "增加道具");
	
	private final int id;
	private final String desc;
	
	private IncreaseSource(int id, String desc) {
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
