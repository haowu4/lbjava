package edu.illinois.cs.cogcomp.lbjava.learn.vector;

import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessInputStream;
import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessOutputStream;
import gnu.trove.strategy.HashingStrategy;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenCustomHashMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntHash;

import java.util.Arrays;
import java.util.Map;

import static edu.illinois.cs.cogcomp.lbjava.learn.vector.OptimizedVector.isZero;

/**
 * Created by haowu4 on 6/16/17.
 */
public class SVector implements Vector {

    Int2DoubleMap values;
    //    CustomizedTIntDoubleMap values;
    int size;

    public SVector(int size) {
        this(new Int2DoubleOpenCustomHashMap(new IntHash.Strategy() {
            @Override
            public int hashCode(int e) {
                return e;
            }

            @Override
            public boolean equals(int a, int b) {
                return a == b;
            }
        }), size);
    }

    public SVector(Int2DoubleMap values, int size) {
        this.values = values;
        this.size = size;
    }

    /**
     * Construct a dense version of this vector.
     *
     * @return a dense version of this vector.
     */
    public DVector toDense() {
        double[] vs = this.toArray();
        return new DVector(vs);
    }

    @Override
    public double dot(int[] exampleFeatures, double[] exampleValues) {
        double sum = 0;
        for (int i = 0; i < exampleFeatures.length; i++) {
            sum += this.get(exampleFeatures[i]) * exampleValues[i];
        }
        return sum;
    }

    @Override
    public double dot(int[] exampleFeatures, double[] exampleValues, double defaultW) {
        double sum = 0;
        for (int i = 0; i < exampleFeatures.length; i++) {
            sum += this.get(exampleFeatures[i], defaultW) * exampleValues[i];
        }
        return sum;
    }

    @Override
    public boolean isDense() {
        return false;
    }

    @Override
    public double get(int i) {
        return values.get(i);
    }

    @Override
    public double get(int i, double d) {
        return values.get(i);
    }

    @Override
    public double set(int i, double v) {
        throw new RuntimeException("Sparse Vector is immutable.");
//        return values.put(i, v);
    }

    @Override
    public double set(int i, double v, double d) {
        // Probably will not support this.
        throw new RuntimeException("Sparse Vector is immutable.");
    }

    @Override
    public void add(double v) {
        throw new RuntimeException("Sparse Vector is immutable.");
    }

    @Override
    public void addAll(DVector v) {
        throw new RuntimeException("Sparse Vector is immutable.");
    }

    @Override
    public double remove(int i) {
        throw new RuntimeException("Sparse Vector is immutable.");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public double max() {
        double result = -Double.MAX_VALUE;

        for (Int2DoubleMap.Entry it : values.int2DoubleEntrySet()) {
            double v = it.getDoubleValue();
            if (v > result) {
                result = v;
            }
        }
        return result;
    }

    @Override
    public double[] toArray() {
        final double[] ret = new double[this.size];
        for (Int2DoubleMap.Entry it : values.int2DoubleEntrySet()) {
            ret[it.getIntKey()] = it.getDoubleValue();
        }

        return ret;
    }

    @Override
    public void write(final ExceptionlessOutputStream out) {
        out.writeInt(this.size);
        out.writeInt(this.values.size());
        //
        for (Int2DoubleMap.Entry it : values.int2DoubleEntrySet()) {
            out.writeInt(it.getIntKey());
            out.writeDouble(it.getDoubleValue());
        }
    }

    @Override
    public void read(ExceptionlessInputStream in) {
        this.size = in.readInt();
        int nonZeroCount = in.readInt();
        this.values = new Int2DoubleOpenHashMap();
        for (int i = 0; i < nonZeroCount; i++) {
            int k = in.readInt();
            double v = in.readDouble();
            if (!isZero(v)) {
                values.put(k, v);
            }

        }
    }

    @Override
    public Object clone() {
        final Int2DoubleOpenHashMap vs = new Int2DoubleOpenHashMap();
        for (Int2DoubleMap.Entry it : values.int2DoubleEntrySet()) {
            vs.put(it.getIntKey(), it.getDoubleValue());
        }
        return new SVector(vs, this.size);
    }
}
