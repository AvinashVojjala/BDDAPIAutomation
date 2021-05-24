package resources;

public enum APIResources {
	DummyAPI("/todos/1"),
	POSTAPI("/posts");

	private String resource;
	
	APIResources(String resource) {
		this.resource=resource;
	}
	
	public String getResource() {
		return resource;
	}
}
