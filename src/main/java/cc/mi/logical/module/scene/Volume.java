package cc.mi.logical.module.scene;

public class Volume {
	private final int width;
	private final int height;
	private final int zsize;
	
	public Volume(int width, int height, int zsize) {
		this.width = width;
		this.height = height;
		this.zsize = zsize;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getZsize() {
		return zsize;
	}
}