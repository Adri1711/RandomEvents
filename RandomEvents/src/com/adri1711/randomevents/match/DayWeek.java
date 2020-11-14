package com.adri1711.randomevents.match;

public enum DayWeek {
	SUNDAY("SUNDAY", 1),

	MONDAY("MONDAY", 2),

	TUESDAY("TUESDAY", 3),

	WEDNESDAY("WEDNESDAY", 4),

	THURSDAY("THURSDAY", 5),

	FRIDAY("FRIDAY", 6),

	SATURDAY("SATURDAY", 7),
	
	EVERYDAY("EVERYDAY", 8);

	private String day;

	private Integer position;

	private DayWeek(String day, Integer position) {
		this.day = day;
		this.position = position;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public static DayWeek getByValues(String value) {
		DayWeek dayW = null;
		for (DayWeek d : DayWeek.values()) {
			if (value.equalsIgnoreCase(d.getDay()) || value.equals(d.getPosition().toString())) {
				dayW = d;
			}
		}
		return dayW;
	}
}
