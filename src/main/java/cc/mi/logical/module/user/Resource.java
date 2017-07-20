package cc.mi.logical.module.user;

import cc.mi.logical.source.IncreaseSource;

public class Resource {
	
	public ChangeResourceResult incrGold(IncreaseSource source, int value) {
		//FIXME 返回一个操作结果
		return null;
	}
	
	public static class ChangeResourceResult {
		private final boolean success;
		private final int changed;
		
		public ChangeResourceResult(boolean success, int changed) {
			this.success = success;
			this.changed = changed;
		}

		public boolean isSuccess() {
			return success;
		}

		public int getChanged() {
			return changed;
		}
	}
}
