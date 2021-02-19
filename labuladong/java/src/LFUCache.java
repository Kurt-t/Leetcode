import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache {
    private final Map<Integer, Integer> keyToVal;
    private final Map<Integer, Integer> keyToFreq;
    private final Map<Integer, LinkedHashSet<Integer>> freqToKeys;
    private int minFreq;
    private final int cap;

    public LFUCache(int capacity) {
        keyToVal = new HashMap<>();
        keyToFreq = new HashMap<>();
        freqToKeys = new HashMap<>();
        this.cap = capacity;
        this.minFreq = 0;
    }
    
    public int get(int key) {
        if (!keyToVal.containsKey(key)) {
            return -1;
        }
        increaseFreq(key);
        return keyToVal.get(key);
    }
    
    public void put(int key, int value) {
        if (this.cap <= 0) {  // cache capacity <= 0
            return;
        }
        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, value);
            increaseFreq(key);
            return;
        }
        // key does not exist
        if (this.cap <= keyToVal.size()) {
            removeMinFreqKey();
        }
        // insert new key, set its freq to 1 (and minFreq to 1)
        keyToVal.put(key, value);
        keyToFreq.put(key, 1);
        freqToKeys.putIfAbsent(1, new LinkedHashSet<>());
        freqToKeys.get(1).add(key);
        this.minFreq = 1;
    }

    private void removeMinFreqKey() {
        LinkedHashSet<Integer> keyList = freqToKeys.get(this.minFreq);
        int deletedKey = keyList.iterator().next();
        // update
        keyList.remove(deletedKey);
        if (keyList.isEmpty()) {
            freqToKeys.remove(this.minFreq);
        }
        keyToFreq.remove(deletedKey);
        keyToVal.remove(deletedKey);
    }

    private void increaseFreq(int key) {
        int freq = keyToFreq.get(key);
        keyToFreq.put(key, freq+1);
        freqToKeys.get(freq).remove(key);
        freqToKeys.putIfAbsent(freq+1, new LinkedHashSet<>());
        freqToKeys.get(freq+1).add(key);
        // if freq list is empty
        if (freqToKeys.get(freq).isEmpty()) {
            freqToKeys.remove(freq);
            // if this freq is minFreq!!!
            if (freq == this.minFreq) {
                this.minFreq++;
            }
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */