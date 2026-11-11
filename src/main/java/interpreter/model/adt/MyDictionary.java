package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import java.util.Map;
import java.util.HashMap;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> map = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!map.containsKey(key)) {
            throw new ADTException("Key '" + key + "' is not defined.");
        }
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!map.containsKey(key)) {
            throw new ADTException("Key '" + key + "' is not defined to update.");
        }
        map.put(key, value);
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public void remove(K key) throws ADTException {
        if (!this.map.containsKey(key)) {
            throw new ADTException("Cannot remove: key does not exist.");
        }
        this.map.remove(key);
    }

    @Override
    public Map<K, V> toMap() {
        // Returns a read-only copy so the logger can read it without risking accidental modifications
        return Map.copyOf(this.map);
    }
}