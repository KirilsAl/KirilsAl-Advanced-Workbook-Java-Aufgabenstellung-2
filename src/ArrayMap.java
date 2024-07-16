public class ArrayMap<K, V> {

    private class Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            Entry<K,V> temp = this;
            StringBuilder sb = new StringBuilder();
            while (temp != null) {
                sb.append(temp.key + " -> " + temp.value + ",");
                temp = temp.next;
            }
            return sb.toString();
        }

    }

    private final int INITIAL_SIZE = 5;
    private Entry<K, V>[] table;
    private int size;

    public ArrayMap() {
        table = new Entry[INITIAL_SIZE];
        size = 0;
    }

    public void put(K key, V value) {
        int hash = Math.abs(key.hashCode() % table.length);
        Entry<K, V> newEntry = new Entry<>(key, value);

        if (table[hash] == null) {
            table[hash] = newEntry;
        } else {
            Entry<K, V> current = table[hash];
            while (current.next != null) {
                if (current.getKey().equals(key)) {
                    current.setValue(value);
                    return;
                }
                current = current.next;
            }
            if (current.getKey().equals(key)) {
                current.setValue(value);
            } else {
                current.next = newEntry;
            }
        }
        size++;
        if (size > table.length * 0.75) {
            resize();
        }
    }

    public V get(K key) {
        int hash = Math.abs(key.hashCode() % table.length);
        Entry<K, V> current = table[hash];

        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.next;
        }
        return null;
    }

    public Entry<K, V> remove(K key) {
        int hash = Math.abs(key.hashCode() % table.length);
        Entry<K, V> current = table[hash];
        Entry<K, V> prev = null;

        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev == null) {
                    table[hash] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    private void resize() {
        int newCapacity = table.length * 2;
        Entry<K, V>[] newTable = new Entry[newCapacity];

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                Entry<K, V> next = entry.next;
                int hash = Math.abs(entry.getKey().hashCode() % newCapacity);
                entry.next = newTable[hash];
                newTable[hash] = entry;
                entry = next;
            }
        }
        table = newTable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            sb.append(i + " " + (table[i] != null ? table[i] : "null") + "\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ArrayMap<Integer, String> myHashMap = new ArrayMap<>();
        myHashMap.put(134, "Kirils");
        myHashMap.put(124, "Alexander");
        myHashMap.put(001, "Sophie");
        myHashMap.put(346, "Jan");
        myHashMap.put(234,  "Manuel");
        myHashMap.put(233, "Roberto");
        myHashMap.put(456, "Max");
        myHashMap.put(635, "Valeria");
        myHashMap.put(886,  "Katja");

        myHashMap.remove(886);
        System.out.println(myHashMap);
    }
}
