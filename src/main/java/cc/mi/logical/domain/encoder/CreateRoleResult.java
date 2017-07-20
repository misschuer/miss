package cc.mi.logical.domain.encoder;

import cc.mi.logical.coder.AbstractCoder;
import cc.mi.logical.domain.UTFString;
import cc.mi.system.OutboundOpcode;
import io.netty.buffer.ByteBuf;

public class CreateRoleResult extends AbstractCoder {
	private int level;
	private String name;
	
	public CreateRoleResult() {
		super(OutboundOpcode.CREATE_ROLE_RESULT);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(level);
		UTFString.writeString(buffer, name);
	}
	
	@Override
	public void decode(ByteBuf buffer) {
		this.level = buffer.readInt();
		this.name  = UTFString.readString(buffer);
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}
}
