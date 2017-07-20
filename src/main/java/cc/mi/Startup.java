package cc.mi;

import cc.mi.engine.Engine;
import cc.mi.logical.net.Server;

public class Startup {

	public static void main(String[] args) {
		try {
			Engine.init();
			Server.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
