package cc.mi.logical.handler.owner;

public abstract class AbstractHandler implements Handler {
	private final AsyncThreadGroupCategory category;
	
	protected AbstractHandler(AsyncThreadGroupCategory category) {
		this.category = category;
	}

	public AsyncThreadGroupCategory getCategory() {
		return category;
	}
}