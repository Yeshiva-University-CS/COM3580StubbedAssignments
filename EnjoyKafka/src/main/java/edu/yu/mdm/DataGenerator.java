package edu.yu.mdm;

/** Interface for generating data for the EnjoyKafka assignment.
 *
 * A DataGenerator produces string payloads ("records") about products.  Under
 * normal conditions, the payload is a valid JSON string representing a product
 * update. The expected schema contains exactly three fields: productId
 * (String) the unique identifier for the product; timestamp (Long): The epoch
 * time of the update in milliseconds; data (String): the payload of the
 * update.
 *
 * Example: {"productId": "Product-0", "timestamp": 1700000001500, "data": "Update-1"}"
 *
 * All implementations guarantee that a record will contain a structurally
 * valid product identifier. Specifically: "productId": " followed by the
 * string value and a closing quote (e.g., "productId": "Product-1") will
 * always be present.  No other guarantees must be met by implementations of
 * the interface, and in fact, implementations of this interface may choose to
 * supply invalid data ("doesn't match the assignment's happy-path
 * specification").
 *
 * @author Avraham Leff
 */

import java.util.Iterator;

public interface DataGenerator extends Iterator<String> {

  /** Specifies the name of the "dead letter queue" topic for publishing
   * malformed product records ("unhappy path").
   *
   * @fixme? Following the OATS "rule of 3", not putting this constant in its own
   * class.
   */
  public static final String DLQ_TOPIC = "updates-dlq";

  // Inherits hasNext() and next() from Iterator
}
