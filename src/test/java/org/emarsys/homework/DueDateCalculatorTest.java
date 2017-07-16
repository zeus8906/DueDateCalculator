package org.emarsys.homework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;


public class DueDateCalculatorTest {

  private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm");
  private static final DueDateCalculator underTest = new DueDateCalculator();

  @Test
  public void testCalculateDueDateInDay() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 09:12");
    final int turnAroundTime = 4;
    final Date expectedResult = DATE_FORMATTER.parse("2017/07/12 13:12");
    // WHEN
    final Date dueDate = underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertEquals(expectedResult, dueDate);
  }


  @Test
  public void testCalculateDueDateExactEndOfDay() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 09:00");
    final int turnAroundTime = 8;
    final Date expectedResult = DATE_FORMATTER.parse("2017/07/13 09:00");
    // WHEN
    final Date dueDate = underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertEquals(expectedResult, dueDate);
  }

  @Test
  public void testCalculateDueDateTomorrow() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 12:01");
    final int turnAroundTime = 5;
    final Date expectedResult = DATE_FORMATTER.parse("2017/07/13 09:01");
    // WHEN
    final Date dueDate = underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertEquals(expectedResult, dueDate);
  }

  @Test
  public void testCalculateDueDateMoreThenOneDay() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 09:01");
    final int turnAroundTime = 18;
    final Date expectedResult = DATE_FORMATTER.parse("2017/07/14 11:01");
    // WHEN
    final Date dueDate = underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertEquals(expectedResult, dueDate);
  }

  @Test
  public void testCalculateDueDateFromFirdayToMonday() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/14 13:01");
    final int turnAroundTime = 5;
    final Date expectedResult = DATE_FORMATTER.parse("2017/07/17 10:01");
    // WHEN
    final Date dueDate = underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertEquals(expectedResult, dueDate);
  }

  @Test
  public void testCalculateDueDateMoreThanOneWeek() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 09:01");
    final int turnAroundTime = 48;
    final Date expectedResult = DATE_FORMATTER.parse("2017/07/20 09:01");
    // WHEN
    final Date dueDate = underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertEquals(expectedResult, dueDate);
  }

  @Test
  public void testCalculateDueDateLessThenOneWeekButWeekend() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 09:01");
    final int turnAroundTime = 28;
    final Date expectedResult = DATE_FORMATTER.parse("2017/07/17 13:01");
    // WHEN
    final Date dueDate = underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertEquals(expectedResult, dueDate);
  }

  @Test
  public void testCalculateDueDateMoreThanOneMonth() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 09:01");
    final int turnAroundTime = 163;
    final Date expectedResult = DATE_FORMATTER.parse("2017/08/09 12:01");
    // WHEN
    final Date dueDate = underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertEquals(expectedResult, dueDate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCalculateDueDateInvalidInputNegativeTurnAroundTime() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 09:01");
    final int turnAroundTime = -1;
    // WHEN
    underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertTrue("Should not reach this point!", false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCalculateDueDateInvalidInputZeroTurnAroundTime() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 09:01");
    final int turnAroundTime = 0;
    // WHEN
    underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertTrue("Should not reach this point!", false);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testCalculateDueDateInvalidInputNullSubmissionDate() throws ParseException {
    // GIVEN
    final Date submissionDate = null;
    final int turnAroundTime = 8;
    // WHEN
    underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertTrue("Should not reach this point!", false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCalculateDueDateInvalidInputSubmissionDateUnderRange() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 08:59");
    final int turnAroundTime = 8;
    // WHEN
    underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertTrue("Should not reach this point!", false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCalculateDueDateInvalidInputSubmissionDateOverRange() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/12 17:01");
    final int turnAroundTime = 8;
    // WHEN
    underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertTrue("Should not reach this point!", false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCalculateDueDateInvalidInputSubmissionDateOnSunday() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/16 10:01");
    final int turnAroundTime = 8;
    // WHEN
    underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertTrue("Should not reach this point!", false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCalculateDueDateInvalidInputSubmissionDateOnSaturday() throws ParseException {
    // GIVEN
    final Date submissionDate = DATE_FORMATTER.parse("2017/07/15 10:01");
    final int turnAroundTime = 8;
    // WHEN
    underTest.calculateDueDate(submissionDate, turnAroundTime);
    // THEN
    assertTrue("Should not reach this point!", false);
  }
}
