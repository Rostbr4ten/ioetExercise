package ioet;

public class DataHelper {

	private String day;
	private String time;

	public DataHelper() {

	}

	public DataHelper(String day, String time) {
		super();
		this.day = day;
		this.time = time;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
