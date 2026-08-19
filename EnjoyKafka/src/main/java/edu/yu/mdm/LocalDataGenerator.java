package edu.yu.mdm;

import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** {@code DataGeneratorFactory.getGenerator} returns this implementation of
 * the DataGenerator interface by default.  I'm providing this class as a
 * convenience for students, but note that the implementation only produces
 * perfectly formatted "happy path" data.  In the real-world(tm), such as
 * simulated by a test suite, a DataGenerator may supply invalid data ("doesn't
 * match the assignment's happy-path specification").
 * 
 * @author Avraham Leff
 */
public class LocalDataGenerator implements DataGenerator {
  
  private static final Logger logger = LogManager.getLogger(LocalDataGenerator.class);

  private final int totalMessages;
  private int currentMessageCount = 0;
  
  private final long[] productTimestamps = {
      1700000000000L, 1700000000000L, 1700000000000L, 1700000000000L, 1700000000000L
  };

  public LocalDataGenerator(int totalMessages) {
    this.totalMessages = totalMessages; 
    logger.debug("Initialized LocalDataGenerator with {} total messages", totalMessages);
  }

  @Override
  public boolean hasNext() { 
    return currentMessageCount < totalMessages; 
  }

  @Override
  public String next() {
    if (!hasNext()) {
      logger.error("next() called but no more messages remain in the generator.");
      throw new NoSuchElementException(); 
    }
    
    currentMessageCount++; 
        
    // Generate an index between 0 and 4 to utilize all 5 consumer threads uniformly
    int productIndex = currentMessageCount % 5;
    String productId = "Product-" + productIndex;
    long currentTimestamp = (productTimestamps[productIndex] += 1500);

    return String.format("{\"productId\": \"%s\", \"timestamp\": %d, \"data\": \"Update-%d\"}", 
                         productId, currentTimestamp, currentMessageCount);
  }
}
