package cc.mi.engine.multithread;
import java.util.concurrent.atomic.AtomicLong;

public class PaddedAtomicLong extends AtomicLong {
	private static final long serialVersionUID = 1L;
	private long p0, p1, p2, p3, p4, p5, p6;
	
	public long getP0() {
		return p0;
	}
	public void setP0(long p0) {
		this.p0 = p0;
	}
	public long getP1() {
		return p1;
	}
	public void setP1(long p1) {
		this.p1 = p1;
	}
	public long getP2() {
		return p2;
	}
	public void setP2(long p2) {
		this.p2 = p2;
	}
	public long getP3() {
		return p3;
	}
	public void setP3(long p3) {
		this.p3 = p3;
	}
	public long getP4() {
		return p4;
	}
	public void setP4(long p4) {
		this.p4 = p4;
	}
	public long getP5() {
		return p5;
	}
	public void setP5(long p5) {
		this.p5 = p5;
	}
	public long getP6() {
		return p6;
	}
	public void setP6(long p6) {
		this.p6 = p6;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
