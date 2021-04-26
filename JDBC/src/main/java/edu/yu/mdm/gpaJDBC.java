package edu.yu.mdm;

/** A skeleton class for implementating the "gpa_JDBC" requirements doc.
 *
 * @author Avraham Leff
 */

import java.util.*;

public class gpaJDBC {

  // A value class used by the studentGPAs() method
  public static class GPARecord implements Comparable<GPARecord> {
    /** Constructor: you may not change the signature or semantics of this
     * method.
     *
     * @param String studentId
     * @param double gpa
     */
    public GPARecord(final String studentId, final double gpa) {
      assert studentId != null : "studentId can't be null";
      assert studentId.length() > 0 : "studentId can't be empty: "+studentId;
      assert gpa >= 0.0 : "gpa must be non-negative: "+gpa;

      this.studentId = studentId;
      this.gpa = gpa;
    }

    // add methods as necessary

    @Override
    public int compareTo(final GPARecord b) {
      return -1;
    }

    // safe to expose because immutable
    public final String studentId;
    public final double gpa;
  }


  /** Constructor specifying the JDBC "connection parameters" to use when
   * subsequentally invoking operations that require connecting to the
   * database.
   *
   * @param databaseURL the base JDBC url, does NOT contain either the database
   * name, the user name, or the password.  Example:
   * "jdbc:postgresql://localhost/"
   * @param dbName specifies the database to connect to
   * @param userName the user name credentials to use when connecting
   * @param password the password to use when connecting
   *
   * @see https://jdbc.postgresql.org/documentation/head/connect.html
   */
  public gpaJDBC(final String databaseURL, final String dbName,
                 final String userName, final String password)
  {
    // fill me in
  }

  /** Return a List of GPARecord, ordered in lexicographical order, by
   * ascending student id for all students who have taken a course.  GPAs
   * should be reported with a precision of 2 decimal points.  If a student
   * doesn't have a gpa because she hasn't taken courses or because the grade
   * earned on a course doesn't meet the criteria, the student should not be
   * included in the returned result.
   *
   * @return List of GPARecord per the above semantics.
   * @see GPARecord
  */
  public List<GPARecord> studentGPAs() {
    return null;
    // fill me in
  } // allStudentsGPAReport

  /** Returns the total number of credits points earned by that student across
   * all courses taken by the student
   *
   * @param ID the student's id
   * @return total grade points (not GPA) earned by that student across all
   * courses taken by that student.  If the student didn't take any courses,
   * return 0.  A grade that is less than a "D" doesn't contribute to the total
   * grade points.
   * @throws IllegalArgumentException if no student with that ID exists.
   */
  public int totalCredits(final String studentId)
  {
    return -1;
    // fill me in
  } // totalCredits

  /** Returns the student's GPA.
   *
   * @param ID the student's id
   * @return The grade point average earned by that student across all courses
   * taken by that student.  GPAs must be calculated with a precision of 2
   * decimal points.  If the student didn't take any courses, return 0.0
   * @throws IllegalArgumentException if no student with that ID exists.
   */
  public double gpa (final String studentId) {
    return 0.0;
    // fill me in
  } // gpa
} 

