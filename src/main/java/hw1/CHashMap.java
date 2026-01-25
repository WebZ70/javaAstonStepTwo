package hw1;

import java.util.Objects;

public class CHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Item<K,V>[] buckets;
    private int size;
    public int lenght;

    private static class Item<K,V> {
        K key;
        V value;
        Item<K,V> next;

        public Item(K key, V value, Item<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public CHashMap() {
        buckets = new Item[DEFAULT_CAPACITY];
        size = 0;
    }

    private int indexSearch(K key) {
        return Math.abs(Objects.hashCode(key)) % buckets.length;
    }

    public V put(K key, V value) {
        int index = indexSearch(key);
        Item<K,V> current = buckets[index];

        while (current != null) {
            if (current.key.hashCode() == key.hashCode()) {
                if (current.key.equals(key)) {
                    V oldValue = current.value;
                    current.value = value;
                    return oldValue;
                }
            }
            current = current.next;
        }
        buckets[index] = new Item<>(key, value, buckets[index]);
        if ((float) ++size / buckets.length > DEFAULT_LOAD_FACTOR) {resize();}
        return null;
    }

    public V get(K key) {
        int index = indexSearch(key);
        Item<K,V> current = buckets[index];

        while (current != null) {
            if (current.key.hashCode() == key.hashCode()) {
                if (current.key.equals(key)) {
                    return current.value;
                }
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = indexSearch(key);
        Item<K,V> current = buckets[index];
        Item<K,V> prev = null;

        while (current != null) {
            if (current.key.hashCode() == key.hashCode()) {
                if (current.key.equals(key)) {
                    if (prev == null) {
                        buckets[index] = current.next;
                    }else {
                        prev.next = current.next;
                    }
                    size--;
                    return current.value;
                }
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    private void resize() {
        Item<K, V>[] newBuckets = new Item[buckets.length * 2];

        for (int i = 0; i < buckets.length; i++) {
            Item<K, V> current = buckets[i];
            while (current != null) {
                Item<K, V> next = current.next;

                int hash = Objects.hashCode(current.key); // Безопасно для null
                int index = Math.abs(hash) % newBuckets.length;

                current.next = newBuckets[index];
                newBuckets[index] = current;
                current = next;
            }
        }
        buckets = newBuckets;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < buckets.length; i++) {
            Item<K,V> current = buckets[i];
            while (current != null) {
                str.append(current.key);
                str.append("=");
                str.append(current.value);
                str.append(",");
                current = current.next;
            }
        }
        return str.toString();
    }
}