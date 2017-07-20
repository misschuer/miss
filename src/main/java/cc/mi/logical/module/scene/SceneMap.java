package cc.mi.logical.module.scene;

public class SceneMap {
	private static final int MAP_EMPTY = 0;
	private static final int MAP_BLOCK = 1;
//	private static final int MAP_FLOOR = 2;
	
	private final int height;
	private final int width;
	private final int[][] mapDatas;
	
	public SceneMap(int width, int height) {
		this.height = height;
		this.width = width;
		mapDatas = new int[width][height];
	}
//	
//	public void setMapData1(int x, int y, int w, int h) {
//		for (int i = 0; i < w; ++ i) {
//			for (int j = 0; j < h; ++ j) {
//				int cx = x + i;
//				int cy = y + j;
//			}
//		}
//		if (checkCanMove(x, y)) {
//			
//		}
//	}
	
	/**
	 * 掉落物品
	 * @param x
	 * @param y
	 */
	public void dropItem(int x, int y) {
		
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	private boolean checkBoundary(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return false;
		return true;
	}
	
	private boolean checkCanMovePart(int x, int y, int w, int h, int cx, int cy) {
		if (cx >= x && cx < x + w && cy >= y && cy < y + h) {
			return true;
		}
		return mapDatas[ x ][ y ] != MAP_BLOCK;
	}

	private boolean checkCanMove(int x, int y, int changeX, int changeY) {
		return checkBoundary(changeX, changeY) && checkCanMovePart(x, y, 1, 1, changeX, changeY);
	}
	
	protected boolean checkBossCanMove(int x, int y, int changeX, int changeY) {
		int w = 2;
		int h = 2;
		for (int i = 0; i < w; ++ i) {
			for (int j = 0; j < h; ++ j) {
				int cx = x + i + changeX;
				int cy = y + j + changeY;
				if (!checkBoundary(cx, cy) || !checkCanMovePart(x, y, w, h, cx, cy))
					return false;
			}
		}
		return true;
	}
	
	public boolean moveUp(int x, int y) {
		return moveY(x, y, y+1);
	}
	
	public boolean moveDown(int x, int y) {
		return moveY(x, y, y-1);
	}
	
	public boolean moveRight(int x, int y) {
		return moveX(x, y, x+1);
	}
	
	public boolean moveLeft(int x, int y) {
		return moveX(x, y, x-1);
	}
	
	private boolean moveY(int x, int y, int next) {
		if (checkCanMove(x, y, x, next)) {
			setMap(x, y, MAP_EMPTY);
			setMap(x, next, MAP_BLOCK);
			return true;
		}
		return false;
	}
	
	private boolean moveX(int x, int y, int next) {
		if (checkCanMove(x, y, next, y)) {
			setMap(x, y, MAP_EMPTY);
			setMap(next, y, MAP_BLOCK);
			return true;
		}
		return false;
	}
	
	private void setMap(int x, int y, int type) {
		mapDatas[ x ][ y ] = type;
	}
}
