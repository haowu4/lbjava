package edu.illinois.cs.cogcomp.lbjava.learn.vector;


import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;

/**
 * Created by haowu4 on 6/17/17.
 */
public class CustomizedTIntDoubleMap extends Int2DoubleOpenHashMap {
    public CustomizedTIntDoubleMap() {
        super(DEFAULT_INITIAL_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public double get(final int k, double defRetValue) {
        if (((k) == (0))) return containsNullKey ? value[n] : defRetValue;
        int curr;
        final int[] key = this.key;
        int pos;
        // The starting point.
        if (((curr = key[pos = (it.unimi.dsi.fastutil.HashCommon.mix((k))) & mask]) == (0))) return defRetValue;
        if (((k) == (curr))) return value[pos];
        // There's always an unused entry.
        while (true) {
            if (((curr = key[pos = (pos + 1) & mask]) == (0))) return defRetValue;
            if (((k) == (curr))) return value[pos];
        }
    }
}
