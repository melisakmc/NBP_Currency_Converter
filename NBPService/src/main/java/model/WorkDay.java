package model;

import java.time.LocalDate;

public class WorkDay {
	
	public static LocalDate getDate(LocalDate end, int numOfDays) {
		LocalDate start = end.minusDays(numOfDays);
		while (true) {
		    int dayCount = getWorkDays(start, end);
			if (dayCount == numOfDays) return start; 	
			start = start.minusDays(numOfDays-dayCount);	//if desired number of work days haven't established, loop continues
		}
	}
	
	public static int getWorkDays(LocalDate start, LocalDate end) {
		int workDays = 0; 
		LocalDate comp = end; 
		while (!start.isAfter(comp)) {			//if the day is not sunday or saturday 
			if (!(comp.getDayOfWeek().getValue() % 7 == 0 || comp.getDayOfWeek().getValue() % 7 == 6)) workDays++;
			comp = comp.minusDays(1);	
		}
		return workDays;
	}

}
