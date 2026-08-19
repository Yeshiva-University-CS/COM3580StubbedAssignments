package edu.yu.mdm;

/** Implementations of the EnjoyKafka assignment MUST use the factory to
 * acquire a DataGenerator instance.
 *
 * Students MAY NOT alter this class in any way: this version of the factory
 * will be used during testing.
 *
 * @author Avraham Leff
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;  

public class DataGeneratorFactory {

  /** For the EnjoyKafka assignment, you MUST pass the TOTAL_MESSAGES constant
   * when you invoke {@code DataGeneratorFactory.getGenerator()}
   */
  public final static int TOTAL_MESSAGES = 5_000;


  /** Returns a DataGenerator instance, specifying the number of messages to be
   * generated.
   *
   * @param totalMessages the number of messages to be generated.
   * @return an implementation of the DataGenerator interface.
   */
  public static DataGenerator getGenerator(int totalMessages) {
    try {
      // Attempt to dynamically load the test suite's implementation
      final String className = "edu.yu.mdm.test.TestDataGenerator";
      Class<?> clazz = Class.forName(className);
      logger.info("Test mode: returning instance of {}", className);
      return (DataGenerator) clazz.getDeclaredConstructor(int.class).newInstance(totalMessages);
    }
    catch (ClassNotFoundException e) {
      // Test suite version not found on classpath, fallback to local dev mode
      logger.info("Development mode: returning instance of LocalDataGenerator");
      return new LocalDataGenerator(totalMessages);
            
    }
    catch (Exception e) {
      final String msg = "Couldn't construct DataGenerator implementation";
      logger.error(msg, e);
      throw new RuntimeException(msg, e);
    }
  }

  private final static Logger logger =
    LogManager.getLogger(DataGeneratorFactory.class);  
}
