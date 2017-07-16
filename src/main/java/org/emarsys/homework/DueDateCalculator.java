package org.emarsys.homework;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DueDateCalculator {

  private static final int NO_OF_WEEKEND_DAYS = 2;
  private static final int NO_OF_WORKDAYS = 5;
  private static final int END_WORKING_HOURS = 17;
  private static final int BEGIN_WORKING_HOURS = 9;
  private static final int WORKING_HOURS = 8;

  /**
   * Calculates the due date for a bug's ticket.
   * 
   * @param submitDate
   *          Date time of the ticket's submission.
   * @param turnAroundTime
   *          The estimated time for the ticket in working hours.
   * @return The due date of the ticket.
   */
  public Date calculateDueDate(Date submitDate, int turnAroundTime) {
    validateSubmissionDate(submitDate);
    validateTurnAroundTime(turnAroundTime);
    Calendar resultDate = Calendar.getInstance(Locale.US);
    resultDate.setTime(submitDate);
    int hourOfDay = resultDate.get(Calendar.HOUR_OF_DAY);
    int dayOfWeek = resultDate.get(Calendar.DAY_OF_WEEK) - 1;
    int daysShouldAdded = calculateDayOffset(turnAroundTime, hourOfDay, dayOfWeek);
    resultDate.add(Calendar.DAY_OF_MONTH, daysShouldAdded);
    resultDate.add(Calendar.HOUR_OF_DAY, calculateHourOffset(turnAroundTime, hourOfDay));
    return resultDate.getTime();
  }

  private int calculateHourOffset(int turnAroundTime, int hourOfDay) {
    return hourOfDay + turnAroundTime % WORKING_HOURS >= END_WORKING_HOURS
        ? BEGIN_WORKING_HOURS + turnAroundTime % WORKING_HOURS - END_WORKING_HOURS
        : turnAroundTime % WORKING_HOURS;
  }

  private int calculateDayOffset(int turnAroundTime, int hourOfDay, int dayOfWeek) {
    int workDaysShouldAdd = turnAroundTime / WORKING_HOURS
        + (hourOfDay + turnAroundTime % WORKING_HOURS >= END_WORKING_HOURS ? 1 : 0);
    int weekendDays = NO_OF_WEEKEND_DAYS * (workDaysShouldAdd / NO_OF_WORKDAYS)
        + (dayOfWeek + workDaysShouldAdd % NO_OF_WORKDAYS > NO_OF_WORKDAYS ? NO_OF_WEEKEND_DAYS : 0);
    int daysShouldAdded = workDaysShouldAdd + weekendDays;
    return daysShouldAdded;
  }

  private void validateTurnAroundTime(int turnAroundTime) {
    if (turnAroundTime < 1) {
      throw new IllegalArgumentException("Turn around time must be a positive number!");
    }
  }

  private void validateSubmissionDate(Date submitDate) {
    if (submitDate == null) {
      throw new IllegalArgumentException("Submission Date cannot be null!");
    }
    final Calendar calendar = Calendar.getInstance();
    calendar.setTime(submitDate);
    if (calendar.get(Calendar.DAY_OF_WEEK) - 1 > NO_OF_WORKDAYS || calendar.get(Calendar.DAY_OF_WEEK) - 1 < 1
        || calendar.get(Calendar.HOUR_OF_DAY) < BEGIN_WORKING_HOURS
        || calendar.get(Calendar.HOUR_OF_DAY) >= END_WORKING_HOURS) {
      throw new IllegalArgumentException("Submission date is not in valid range!");
    }

  }

}
