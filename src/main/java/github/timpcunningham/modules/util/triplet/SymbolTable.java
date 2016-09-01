package github.timpcunningham.modules.util.triplet;

import java.util.List;
import java.util.Map;
import java.util.Set;

interface SymbolTable<K, S, V> {

    void put(K key, S secondary, V value);
    void remove(K key);
    void remove(K key, S secondary);

    boolean containsKey(K key);
    boolean contains(K key, S secondary);

    Map<S, V> get(K key);
    V get(K key, S seconday);
    Map<K, List<S>> uniqueKeySet();

    Set<K> keySet();
}
