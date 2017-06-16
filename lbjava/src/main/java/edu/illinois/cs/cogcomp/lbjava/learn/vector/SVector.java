package edu.illinois.cs.cogcomp.lbjava.learn.vector;

import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessInputStream;
import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessOutputStream;
import gnu.trove.map.hash.TIntDoubleHashMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by haowu4 on 6/16/17.
 */
public class SVector implements Vector {

    TIntDoubleHashMap values;
    int size;

    public SVector() {
        this(new TIntDoubleHashMap());
    }

    public SVector(TIntDoubleHashMap values) {
        this.values = values;
    }

    protected int getMaxIndex() {
        return size - 1;
    }

    @Override
    public boolean isDense() {
        return false;
    }

    @Override
    public Vector optimize() {
        throw new NotImplementedException();
    }

    @Override
    public double get(int i) {
        return values.get(i);
    }

    @Override
    public double get(int i, double d) {
        // TODO(@haowu4): This is not optimized for speed.
        // Because when the key exists, we need to query the hashmap twice. Will fix this later.
        return values.containsKey(i) ? values.get(i) : d;
    }

    @Override
    public double set(int i, double v) {
        return values.put(i, v);
    }

    @Override
    public double set(int i, double v, double d) {
        throw new NotImplementedException();
    }

    @Override
    public void add(double v) {
        throw new NotImplementedException();
    }

    @Override
    public void addAll(DVector v) {
        throw new NotImplementedException();
    }

    @Override
    public double remove(int i) {
        return values.remove(i);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public double max() {
        return 0;
    }

    @Override
    public void sort() {

    }

    @Override
    public int binarySearch(double v) {
        return 0;
    }

    @Override
    public double[] toArray() {
        return new double[0];
    }

    @Override
    public void write(ExceptionlessOutputStream out) {

    }

    @Override
    public void read(ExceptionlessInputStream in) {

    }
}
