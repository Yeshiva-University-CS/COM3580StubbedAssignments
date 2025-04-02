package edu.yu.mdm;

/** Defines the API for the RedisMarket assignment: see the requirements
 * document for more information.
 *
 * Please note the public constants defined below (and follow the implied
 * semantics).
 *
 * Students MAY NOT add to the constructor IMPLEMENTATION.  Students MAY NOT
 * add additional constructor signatures.
 *
 * @author Avraham Leff
 */

import java.util.*;
import redis.clients.jedis.Jedis;

public abstract class RedisMarketBase {

  /** The index of the Redis database to which ALL client requests must be
   * addressed.
   */
  public static final int DB_INDEX = 15;

  /** The number of MILLI seconds that a buyer or seller will wait for a sale
   * to occur before giving up.
   */
  public int WAIT_DURATION_IN_MILLIS = 3_000;

  /** Represents an item listed in the market.  This is a value class.
   */
  public static class Item {
    public Item(String botId, String itemId, double price) {
      this.botId = botId;
      this.itemId = itemId;
      this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }

      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }

      final Item other = (Item) obj;
      return botId.equals(other.botId) && itemId.equals(other.itemId) &&
        // painful, but see no fundamentally better alternative
        Math.abs(this.price - other.price) < EPSILON;
    }

    @Override
    public int hashCode() {
      // definitely do not want to autobox price
      return (31 * ((int) price)) + botId.hashCode() + itemId.hashCode();
    }

    @Override
    public String toString() {
      return "bot="+botId+",item="+itemId+",price="+price;
    }
      
    // Design note: ok to expose ivars because immutable
    public final String botId;
    public final String itemId;
    public final double price;
    // for price comparison
    private final static double EPSILON = 0.000001d;
  }

  

  /** Constructor is supplied with a Jedis instance, created with the no-arg
   * constructor.  The constructor selects DB number DB_INDEX as its working
   * database.
   */
  public RedisMarketBase(final Jedis conn) {
    this.conn = conn;
    conn.select(DB_INDEX);
  }

  /** Adds a bot to the system.
   *
   * @param botId uniquely identifies the bot, must not currently exist in the
   * system.  Client's responsibility to ensure that not null or empty.
   * @param funds amount of money available to the bot to purchase items in the
   * market.  Client's responsibility to ensure that greater than 0.
   * @throws IllegalArgumentException if bot already exists in the system.
   */
  public abstract void addBot(String botId, double funds);

  /** Returns information about bots in the system.
   *
   * @return a map whose keys are bot ids and whose values are corresponding
   * available funds, empty map if no bots are entered in the system.
   * Ownership remains with server.
   */
  public abstract Map<String, Double> getBots();

  /** Adds specified item to bot's inventory.  
   *
   * @param botId uniquely identifies the bot: client's responsibility to
   * ensure that not null or empty.
   * @param itemId uniquely identifies the item: client's responsibility to
   * ensure that not null or empty.
   * @throws IllegalArgumentException if bot doesn't already exist in the
   * system.
   * @fixme should add notion of price and quantity to inventory, but not
   * today.
   */
  public abstract void addInventory(String botId, String itemId);

  /** Returns information about the system's inventory.
   *
   * @return a map whose keys are bot ids and whose values are corresponding
   * Set of inventory, empty map if no bots are entered in the system, empty
   * Set if bot currently doesn't have inventory.  Ownership remains with
   * server.
   */
  public abstract Map<String, Set<String>> getInventory();

  /** Returns all items currently listed in the market.
   *
   * @return Map, whose keys are bot ids, and whose values are the Set of items
   * that they've listed in the market.  If no items listed in the market,
   * returns an empty map.
   */
  public abstract Map<String, Set<Item>> getMarket();

  /** Attempts to list an item in the marketplace.  The bot specifies what it
   * wants to sell and at what price.  Multiple instances with the same botId
   * may attempt to sell the same item at the same or for a different price.
   * If the seller bot's inventory changes between method entry and successful
   * completion, the operation is transparently retried on the client's behalf,
   * but only for a maximum of WAIT_DURATION_IN_MILLIS.
   *
   * If the list operation is successful, the item is removed from the seller
   * bot's inventory.
   * 
   * @param botId uniquely identifies the bot, must have previously been added
   * via addBot: client's responsibility to ensure that not null or empty.
   * @param itemId uniquely identifies the item, must previously been
   * associated with this botIt via addInventory: client's responsibility to
   * ensure that not null or empty.  If botId and itemId pair already exist in
   * the system, the new price overrides the current price.
   * @param price the price that the item will be listed for: client's
   * responsibility to ensure that value is greater than 0.
   * @return true iff the "list item" operation succeeded.  The operation fails
   * iff the application had to retry the operation (see above), and the max
   * retry period expired without successfully listing the item.
   * @throws IllegalArgumentException if the botId or itemId assumptions are
   * violated.
   */
  public abstract boolean listItem(String botId, String itemId, double price);

  /** Attempts to purchase an item from the marketplace.  The buyerBot
   * specifies what it wants to purchase, and at what price, from the specified
   * sellerBot.  Using the market, the purchase bot determines if the sellerBot
   * is selling the item at the buyer's specified price.  The purchase can
   * succeed only if the buyer has sufficient funds to cover the purchase and
   * if the offered price matches the listed price.  Assuming sufficient funds,
   * the application makes the purchase, transfers the inventory from sellerBot
   * to buyerBot, decreases the buyer's funds, increases the seller's funds,
   * delists the item from the market, and returns "true".  Otherwise, returns
   * false.
   *
   * If the buyer's inventory state, or if the market state, changes between
   * method entry and successful completion, the operation is transparently
   * retried on the client's behalf, but only for a maximum of
   * WAIT_DURATION_IN_MILLIS.  Multiple bots can attempt to purchase the same
   * item at the same or for a different price.
   * 
   * @param buyerBot uniquely identifies the bot that wants to make the
   * purchase, must have previously been added via addBot: client's
   * responsibility to ensure that not null or empty.  The buyerBot's available
   * funds must at least cover the purchase price.
   * @param itemId uniquely identifies the item listed in the market: client's
   * responsibility to ensure that not null or empty.
   * @param sellerBot uniquely identifies the bot from whom the purchase will
   * be made via the market: client's responsibility to ensure that not null or
   * empty.
   * @param price the price that the buyer is willing to spend on the item:
   * client's responsibility to ensure that value is greater than 0.
   * @return true iff the "purchase item" operation succeeded (see above).  The
   * operation fails if the application had to retry the operation (see above)
   * and the max retry period expired without successfully purchasing the item.
   * @throws IllegalArgumentException if the botId, itemId, sellerId, or price
   * assumptions violated.
   */
  public abstract boolean purchaseItem(String buyerBot, String itemId,
                                       String sellerBot, double price);

  // explicit protected access so subclass implementation can use
  protected final Jedis conn;


} // RedisMarketBase
