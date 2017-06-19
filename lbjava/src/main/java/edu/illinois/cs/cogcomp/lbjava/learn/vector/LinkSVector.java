package edu.illinois.cs.cogcomp.lbjava.learn.vector;

import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessInputStream;
import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessOutputStream;
import gnu.trove.iterator.TIntDoubleIterator;
import it.unimi.dsi.fastutil.ints.Int2DoubleAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenCustomHashMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;

import java.util.Arrays;

import static edu.illinois.cs.cogcomp.lbjava.learn.vector.OptimizedVector.isZero;

/**
 * Created by haowu4 on 6/18/17.
 */

public class LinkSVector implements Vector {

    int[] indices;
    double[] values;

    int size;


    public LinkSVector(int size) {
        this(new Int2DoubleOpenHashMap(), size);
    }

    public LinkSVector(Int2DoubleMap values, int size) {
        constructFromDict(values, size);
    }

    private void constructFromDict(Int2DoubleMap values, int size) {
        indices = values.keySet().toIntArray();
        Arrays.sort(indices);
        this.values = new double[indices.length];
        for (int i = 0; i < indices.length; i++) {
            this.values[i] = values.get(indices[i]);
        }
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
        Int2DoubleAVLTreeMap vs = new Int2DoubleAVLTreeMap();
        for (int i = 0; i < exampleFeatures.length; i++) {
            vs.put(exampleFeatures[i], exampleValues[i]);
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
        throw new RuntimeException("Linked Sparse Vector is immutable.");
    }

    @Override
    public double get(int i, double d) {
        throw new RuntimeException("Linked Sparse Vector is immutable.");
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
        for (int i = 0; i < indices.length; i++) {
            double v = values[i];
            if (v > result) {
                result = v;
            }
        }
        return result;
    }

    @Override
    public double[] toArray() {
        double[] ret = new double[this.size];
        for (int i = 0; i < indices.length; i++) {
            ret[indices[i]] = values[i];
        }
        return ret;
    }

    @Override
    public void write(ExceptionlessOutputStream out) {
        out.writeInt(this.size);
        out.writeInt(this.values.length);
        for (int i = 0; i < indices.length; i++) {
            out.writeInt(indices[i]);
            out.writeDouble(values[i]);
        }
    }

    @Override
    public void read(ExceptionlessInputStream in) {
        this.size = in.readInt();
        int nonZeroCount = in.readInt();
        CustomizedTIntDoubleMap values = new CustomizedTIntDoubleMap();
        for (int i = 0; i < nonZeroCount; i++) {
            int k = in.readInt();
            double v = in.readDouble();
            if (!isZero(v)) {
                values.put(k, v);
            }
        }
        constructFromDict(values, this.size);
    }

    @Override
    public Object clone() {
        CustomizedTIntDoubleMap vs = new CustomizedTIntDoubleMap();
        for (int i = 0; i < indices.length; i++) {
            vs.put(indices[i], values[i]);
        }

        return new SVector(vs, this.size);
    }
}
