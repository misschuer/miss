package cc.mi.logical.domain.decoder;

import cc.mi.logical.coder.AbstractCoder;
import cc.mi.logical.domain.UTFString;
import io.netty.buffer.ByteBuf;

public class CreateRoleArgument extends AbstractCoder {
	private int jobId;
	private String name;
	
	public CreateRoleArgument() {
		super(-1);
	}

	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(jobId);
		UTFString.writeString(buffer, name);
	}
	
	@Override
	public void decode(ByteBuf buffer) {
		this.jobId = buffer.readInt();
		this.name = UTFString.readString(buffer);
	}

	public int getJobId() {
		return jobId;
	}

	public String getName() {
		return name;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public void setName(String name) {
		this.name = name;
	}
}