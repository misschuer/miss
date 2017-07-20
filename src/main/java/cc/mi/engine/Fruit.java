package cc.mi.engine;

import java.io.IOException;

import cc.mi.engine.db.check.TableChecker;

public class Fruit {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		TableChecker.showSql();
	}
}