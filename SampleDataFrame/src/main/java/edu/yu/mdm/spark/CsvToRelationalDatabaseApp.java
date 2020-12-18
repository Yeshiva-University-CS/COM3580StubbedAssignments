package edu.yu.mdm.spark;

import static org.apache.spark.sql.functions.concat;
import static org.apache.spark.sql.functions.lit;

import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

/**
 * CSV to a relational database.
 * 
 * @author jgp
 */
public class CsvToRelationalDatabaseApp {

  /**
   * main() is your entry point to the application.
   * 
   * @param args
   */
  public static void main(String[] args) {
    CsvToRelationalDatabaseApp app = new CsvToRelationalDatabaseApp();
    app.start();
  }

  /**
   * The processing code.
   */
  private void start() {
    // Creates a session on a local master
    SparkSession spark = SparkSession.builder()
      .appName("CSV to DB")
      .master("local")
      .getOrCreate();

    // Step 1: Ingestion
    // ---------

    // Reads a CSV file with header, called authors.csv, stores it in a
    // dataframe
    Dataset<Row> df = spark.read()
      .format("csv")
      .option("header", "true")
      .load("data/authors.csv");

    // Step 2: Transform
    // ---------

    // Creates a new column called "name" as the concatenation of lname, a
    // virtual column containing ", " and the fname column
    df = df.withColumn(
                       "name",
                       concat(df.col("lname"), lit(", "), df.col("fname")));

    // Step 3: Save
    // ---------

    // Properties to connect to the database, the JDBC driver is part of our
    // pom.xml
    Properties connectionProperties = new Properties();
    connectionProperties.put("user", username);
    connectionProperties.put("password", password);
    connectionProperties.put("driver", "org.postgresql.Driver");

    // Write in a table named with our class name
    final String className = getClass().getName();
    final String tableName =
      className.substring(className.lastIndexOf(".") + 1);
    df.write()
      .mode(SaveMode.Overwrite)
      .jdbc(dbConnectionUrl, tableName, connectionProperties);

    System.out.println("Process complete");

  }

  private final static String username = "avraham";
  private final static  String password = "";
  // The connection URL, assuming your PostgreSQL instance runs locally on the
  // default port, and the database we use is "mdmspark"
  String dbConnectionUrl = "jdbc:postgresql://localhost/mdmspark";
}
