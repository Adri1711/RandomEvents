package com.adri1711.randomevents.match;

public class Schedule {

	private Integer day;

	private Integer hour;

	private Integer minute;

	private String matchName;

	public Schedule(Integer day, Integer hour, Integer minute, String matchName) {
		super();
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.matchName = matchName;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

}
