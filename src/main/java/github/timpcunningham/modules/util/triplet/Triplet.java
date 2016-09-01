package github.timpcunningham.modules.util.triplet;

import java.util.*;
import java.util.stream.Collectors;

public class Triplet <K, S, V> implements SymbolTable<K, S, V> {
    private List<TripletContainer> triplets;

    public Triplet() {
        triplets = new ArrayList<>();
    }

    @Override
    public void put(K key, S secondary, V value) {
        TripletContainer container = getTriplet(key, secondary);

        if(container == null) {
            container = new TripletContainer(key, secondary, value);
        } else {
            triplets.remove(container);
            container.value = value;
        }

        triplets.add(container);
    }

    @Override
    public void remove(K key) {
        triplets.removeAll(getTriplets(key));
    }

    @Override
    public void remove(K key, S secondary) {
        TripletContainer container = getTriplet(key, secondary);

        if(container != null) {
            triplets.remove(container);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return getTriplets(key).size() > 0;
    }

    @Override
    public boolean contains(K key, S secondary) {
        return getTriplet(key, secondary) != null;
    }

    @Override
    public Map<S, V> get(K key) {
        Map<S, V> result = new HashMap<>();

        getTriplets(key).stream().forEach(triplet -> result.put(triplet.secondary, triplet.value));

        return result;
    }

    @Override
    public V get(K key, S seconday) {
        if(contains(key, seconday)) {
            return getTriplet(key, seconday).value;
        }
        return null;
    }

    @Override
    public Map<K, List<S>> uniqueKeySet() {
        Map<K, List<S>> result = new HashMap<>();

        triplets.stream().forEach(triplet -> {
           if(!result.containsKey(triplet.key)) {
               result.put(triplet.key, new ArrayList<>());
           }

            List<S> temp = result.get(triplet.key);
            temp.add(triplet.secondary);
            result.put(triplet.key, temp);
        });

        return result;
    }

    @Override
    public Set<K> keySet() {
        Set<K> result = new HashSet<>();

        triplets.stream().forEach(triplet -> {
            if(!result.contains(triplet.key)) {
                result.add(triplet.key);
            }
        });

        return result;
    }

    private TripletContainer getTriplet(K key, S secondary) {
       return triplets.stream()
                .filter(triplet -> triplet.key.equals(key) && triplet.secondary.equals(secondary))
                .findFirst().orElse(null);
    }

    private List<TripletContainer> getTriplets(K key) {
        return triplets.stream().filter(triplet -> triplet.key.equals(key)).collect(Collectors.toList());
    }

    private class TripletContainer {
        K key;
        S secondary;
        V value;

        public TripletContainer(K key,S secondary, V value) {
            this.key = key;
            this.secondary = secondary;
            this.value = value;
        }
    }
}
