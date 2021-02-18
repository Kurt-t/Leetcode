/**
 * Leetcode 146
 * a version that uses LinkedHashMap.
 */
import java.util.LinkedHashMap;
import java.util.Map;

class LRUCache {
    private final int capacity;
    Map<Integer, Integer> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new LinkedHashMap<>(capacity);
    }
    
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        // make it most recent
        int val = cache.remove(key);
        cache.put(key, val);
        return val;
    }
    
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            cache.remove(key);
            cache.put(key, value);
            return;
        }
        if (cache.size() >= capacity) {
            int oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        cache.put(key, value);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */