package tk.dpgames.acme.inc.game;

public enum Values {
	
	// Player
	jumpHeight(200),
	inventorySize(30),
	
	;
	float i;
	private Values(float i) {
		this.i = i;
	}
}
