package edu.yu.mdm;

/** Interface to be used by EnjoyKafka Consumer implementations to post data
 * received from the Kafka broker.
 *
 * Design note: a more realistic assignment would use a production quality
 * database such as Postgres or MongoDB.  However, we have not yet discussed
 * these databases at this point of the course, so we'll "mock it up" for this
 * assignment.
 *
 * @author Avraham Leff
 */

public interface MockDB {

  /** Consumers receiving a valid EnjoyKafka record (per the requirements doc)
   * from the Kafka broker, must extract the appropriate state, and invoke this
   * method to update the database.
   *
   * @param productId identifies the product
   * @param timestamp the time that the record was generator
   * @param payload record-specific information
   * @param partition the Kafka partition from which the consumer read the
   * record
   * @param offset the sequential id number assigned by the Kafka broker to
   * each message inside a partition
   */
  void upsertProduct(String productId, long timestamp, String payload, int partition, long offset);

  /** Clears any state currently maintained by the "database".
   */
  void reset();
} // interface
