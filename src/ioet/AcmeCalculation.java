package ioet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AcmeCalculation {

	public static void main(String args[]) {
		ArrayList<String> inputFromText = new ArrayList<>();
		// File Input
		try {
			inputFromText = inputFromFile(inputFromText);
			// Check if there are min 5 sets of data
			if (inputFromText.size() < 5) {
				throw new TooLessDataSetsException("At Least 5 Sets of Data");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TooLessDataSetsException e) {
			e.printStackTrace();
			System.exit(1);
		}
		for (String input : inputFromText) {
			calculationForEachPerson(input);
		}
	}

	private static ArrayList<String> inputFromFile(ArrayList<String> inputFromText)
			throws FileNotFoundException, IOException {
		String empty = "";
		FileReader fr;
		BufferedReader br;
		fr = new FileReader("./input/input");
		br = new BufferedReader(fr);
		while ((empty = br.readLine()) != null) {
			inputFromText.add(empty);
		}
		br.close();
		return inputFromText;
	}

	private static void calculationForEachPerson(String input) {
		int wage = 0;
		ArrayList<DataHelper> daysAndHoursWorked = new ArrayList<>(); // Here will be the days and the worked Hours
																		// saved
		// Formatting the input String to the name and the times
		String[] resultArray = input.split("=");
		String name = resultArray[0]; // The name
		String[] timesWorked = resultArray[1].split(","); // the times worked like MO15:00-16:00

		// Checking the Input File if it fits the Syntax:
		// WeekdayHour:Minute-Hour:Minute,
		try {
			if (!resultArray[1].matches("((MO|TU|WE|TH|FR|SA|SU)\\d{2}:\\d{2}-\\d{2}:\\d{2},){" // Until the last entry
																								// with ,
					+ (timesWorked.length - 1) + "}(MO|TU|WE|TH|FR|SA|SU)\\d{2}:\\d{2}-\\d{2}:\\d{2}")) { // The last
																											// without ,
				throw new WrongInputSyntaxException("Input File doesn't match the correct Syntax");
			}

		} catch (WrongInputSyntaxException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// Adding all days with the worked hours in Data Helper
		daysAndHoursWorked = addAllValuesInDataHelper(daysAndHoursWorked, timesWorked);

		// Payment
		wage = payment(daysAndHoursWorked, wage);
		System.out.printf("The amount to pay %s is: %d USD", name, wage);
		System.out.println();
	}

	private static ArrayList<DataHelper> addAllValuesInDataHelper(ArrayList<DataHelper> daysHours,
			String[] timesWorked) {
		for (int i = 0; i < timesWorked.length; i++) {
			String key = timesWorked[i].substring(0, 2); // MO, SU, TH ....
			String value = timesWorked[i].substring(2); // 12:00-14:00...
			DataHelper tmp = new DataHelper(key, value);
			daysHours.add(tmp);
		}
		return daysHours;
	}

	private static int payment(ArrayList<DataHelper> daysHours, int wage) {
		for (int j = 0; j < daysHours.size(); j++) { // iterate through all days

			String time = daysHours.get(j).getTime(); // time is 12:00-14:00 for example
			String[] hours = time.split("-"); // Split between start and end
			String[] hoursAndMinutesStart = hours[0].split(":"); // [0] = Hours; [1] = Minutes from the start time
			String[] hoursAndMinutesEnd = hours[1].split(":"); // [0] = Hours; [1] = Minutes from the end time

			String startHour = hoursAndMinutesStart[0];
			int startH = 0;

			String endHour = hoursAndMinutesEnd[0];
			int endH = 0;

			// Now the times are be checked if they start with 0
			// When it starts with 0 the 0 will be cut off
			if (startHour.substring(0, 1).equals("0")) {
				startH = Integer.parseInt(startHour.substring(1));
			} else {
				startH = Integer.parseInt(startHour);
			}

			if (endHour.substring(0, 1).equals("0")) {
				endH = Integer.parseInt(endHour.substring(1));
			} else {
				endH = Integer.parseInt(endHour);
			}
			// Now Every Hour from the Duration doesn't start with 0

			wage = calculateWage(daysHours, wage, j, startH, endH);

		}
		return wage;
	}

	private static int calculateWage(ArrayList<DataHelper> daysHours, int wage, int j, int startH, int endH) {
		// Loop while the start hours are unequal to the end hours
		while (startH != endH) {
			// Check wether its on the weekend or during the week
			if (daysHours.get(j).getDay().equals("SA") || daysHours.get(j).getDay().equals("SU")) {
				// checks how much to pay for these hour
				if (startH >= 0 && startH < 9) {
					wage = wage + 30;
					startH++;
				} else if (startH >= 9 && startH < 18) {
					wage = wage + 20;
					startH++;
				} else if (startH >= 18) {
					wage = wage + 25;
					startH++;
				}
			} else {
				// The same but with the different amount of payment
				if (startH >= 0 && startH < 9) {
					wage = wage + 25;
					startH++;
				} else if (startH >= 9 && startH < 18) {
					wage = wage + 15;
					startH++;
				} else if (startH >= 18) {
					wage = wage + 20;
					startH++;
				}
			}
		}
		return wage;
	}

}
