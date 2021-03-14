package com.adri1711.randomevents.match.enums;

public enum CreacionKit {

	NAME(0, "§6§lChoose Name"),
	
	ITEM_DESCRIPTIVE(1, "§6§Put the item on your hand and say 'Done' (It can have custom name or lore, if not it will take the default name and lore from messages.yml)"),

	PERMISSION_OPTIONAL(2, "§6§lWrite the permission needed to use this kit"),

	INVENTORY(3, "§6§lTake the inventory for the players and say 'Done' "),

	SAVE(997, "§6§lYou are about to save a Kit, put 'Y' to confirm or 'N' to continue creating"),

	DELETE(998, "§6§lChoose the field you want to remove"),

	CANCEL(999, "§6§lYou are about to cancel a Kit, put 'Y' to confirm or 'N' to continue creating");

	private Integer position;
	private String message;


	private CreacionKit(Integer position, String message) {
		this.position = position;
		this.message = message;
	}

	public static CreacionKit getByPosition(Integer position) {
		CreacionKit creation = null;
		if (position != null) {
			for (CreacionKit w : CreacionKit.values()) {

				if (w.getPosition().equals(position)) {
					creation = w;
				}
			}
		}
		return creation;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}

	

}
