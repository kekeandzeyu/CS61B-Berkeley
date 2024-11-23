// import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private BSTNode root;
    int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key  the key
     * @param value  the value
     */
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode node, K key, V value) {
        if (node == null) {
            size++;
            return new BSTNode(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value; // Update value if key already exists
        }
        return node;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key  the key
     */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key  the key
     */
    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    /**
     * Helper method for containsKey. Direct use of get() method will fail the test.
     * Instead of looking for key-value pair, we are only interested in the key.
     *
     * @param node  the node
     * @param key  the key
     */
    private boolean containsKey(BSTNode node, K key) {
        if (node == null) {
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return containsKey(node.left, key);
        } else if (cmp > 0) {
            return containsKey(node.right, key);
        } else {
            return true; // Key found
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        TreeSet<K> keys = new TreeSet<>();
        addKeys(root, keys);
        return keys;
    }

    private void addKeys(BSTNode node, Set<K> keys) {
        if (node == null) {
            return;
        }
        addKeys(node.left, keys);
        keys.add(node.key);
        addKeys(node.right, keys);
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key  the key
     */
    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        V value = get(key);
        root = remove(root, key);
        size--;
        return value;
    }

    // Hibbard Deletion
    private BSTNode remove(BSTNode node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else { // Key found
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else { // Node has two children
                BSTNode successor = min(node.right); // Find successor (smallest in right subtree)
                node.key = successor.key;           // Replace node's key and value with successor's
                node.value = successor.value;
                node.right = remove(node.right, successor.key); // Remove successor from right subtree
            }
        }
        return node;
    }


    private BSTNode min(BSTNode node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    // @NotNull // if you want to suppress the warning of Not annotated method overrides method annotated with @NotNull
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    // For debugging purposes
    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.print(node.key + " ");
        printInOrder(node.right);
    }
}
