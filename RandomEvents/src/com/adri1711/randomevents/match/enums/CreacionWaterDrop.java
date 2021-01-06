package com.adri1711.randomevents.match.enums;

import com.adri1711.randomevents.match.Match;

public enum CreacionWaterDrop {

	NAME(0, "§6§lChoose Name"),

	SPAWN(1, "§6§lPut 'Done' to select the spawn of the players"),

	GOAL_LOCATION1(2, "§6§lPut 'Done' to select the point 1 of the goal"),

	GOAL_LOCATION2(3, "§6§lPut 'Done' to select the point 2 of the goal"),

	SAVE(997, "§6§lYou are about to save a Water Drop, put 'Y' to confirm or 'N' to continue creating"),

	DELETE(998, "§6§lChoose the field you want to remove"),

	CANCEL(999, "§6§lYou are about to cancel a Water drop, put 'Y' to confirm or 'N' to continue creating");

	private Integer position;
	private String message;


	private CreacionWaterDrop(Integer position, String message) {
		this.position = position;
		this.message = message;
	}

	public static CreacionWaterDrop getByPosition(Integer position) {
		CreacionWaterDrop creation = null;
		if (position != null) {
			for (CreacionWaterDrop w : CreacionWaterDrop.values()) {

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
