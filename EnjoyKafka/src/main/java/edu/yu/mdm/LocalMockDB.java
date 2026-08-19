package edu.yu.mdm;

/** A minimal implementation of the MockDB interface returned by {@code
 * MockDBFactory.getInstance} in development mode.  
 *
 * @author Avraham Leff
 */

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocalMockDB implements MockDB {
  private final Map<String, String> localData = new HashMap<>();
  
  @Override
  public void upsertProduct(String productId, long timestamp, String payload, int partition, long offset) {
    localData.put(productId, payload);
    logger.info("[Local DB] Upserted: product {} at partition {}, offset={}",
                productId, partition, offset);
  }

  @Override
  public void reset() { localData.clear(); }

 private final static Logger logger = LogManager.getLogger(LocalMockDB.class);
}
