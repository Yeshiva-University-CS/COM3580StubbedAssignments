package edu.yu.mdm;

/** Implementations of the EnjoyKafka assignment MUST use the factory to
 * acquire a MockDB instance.
 *
 * Students MAY NOT alter this class in any way: this version of the class will
 * be used during testing.
 *
 * @author Avraham Leff
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MockDBFactory {
    
  private static MockDB instance;

  public static synchronized MockDB getInstance() {
    if (instance == null) {
      try {
        // Attempt to dynamically load the test suite's implementation.
        final String className = "edu.yu.mdm.test.TestMockDB";
        logger.info("Trying to instantiate {}", className);
        Class<?> clazz = Class.forName(className);
        instance = (MockDB) clazz.getDeclaredConstructor().newInstance();
        logger.info("Successfully instantiated {}", className);
      }
      catch (ClassNotFoundException e) {
        logger.info("Couldn't instantiate test mode MockDB");
        instance = new LocalMockDB();
        logger.info("Instatiated development mode MockDB");
      }
      catch (Exception e) {
        final String msg = "Couldn't instantiate MockDB implementation";
        logger.error(msg, e);
        throw new RuntimeException(msg, e);
      }
    }
    return instance;
  }

  private final static Logger logger =
    LogManager.getLogger(MockDBFactory.class);
}
