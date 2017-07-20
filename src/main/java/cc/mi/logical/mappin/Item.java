package cc.mi.logical.mappin;

import cc.mi.engine.db.annotation.AutoIncrement;
import cc.mi.engine.db.annotation.Column;
import cc.mi.engine.db.annotation.Table;

@Table(name="item", pks={"id"}, comment = "道具表")
public class Item {
	@AutoIncrement
	@Column(name="id", nullable = false, comment = "道具的序号")
	private long id;
	
	@Column(name="userId", nullable = false, defaultValue = "-1", comment = "用户id")
	private int userId;
	
	@Column(name="position", nullable = false, defaultValue = "-1", comment = "背包位置")
	private int position;
	
	@Column(name="itemId", nullable = false, defaultValue = "-1", comment = "道具id")
	private int itemId;
	
	@Column(name="num", nullable = false, defaultValue = "0", comment = "道具数量")
	private int num;
	
	@Column(name="uniqueId", nullable = false, defaultValue = "-1", comment = "道具的惟一id")
	private long uniqueId;
	
	public Item() {}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public String toString() {
		return String.format("Item[position:%d, itemId=%d, num=%d]", position, id, num);
	}
}