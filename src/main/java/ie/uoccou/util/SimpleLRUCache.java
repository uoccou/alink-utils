package ie.uoccou.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleLRUCache<K,V> extends LinkedHashMap<K,V> {
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = -1132552352674116009L;
	private final int capacity;
	  private long accessCount = 0;
	  private long hitCount = 0;

	  public SimpleLRUCache(int capacity)
	  {
	    super(capacity + 1, 1.1f, true);
	    this.capacity = capacity;
	  }

	  public V get(Object key)
	  {
	    accessCount++;
	    if (containsKey(key))
	    {
	      hitCount++;
	    }
	    V value = super.get(key);
	    return value;
	  }

	  protected boolean removeEldestEntry(Map.Entry eldest)
	  {
	    return size() > capacity;
	  }

	  public long getAccessCount()
	  {
	    return accessCount;
	  }

	  public long getHitCount()
	  {
	    return hitCount;
	  }
}
