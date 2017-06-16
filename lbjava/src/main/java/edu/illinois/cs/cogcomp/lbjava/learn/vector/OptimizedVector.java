package edu.illinois.cs.cogcomp.lbjava.learn.vector;

import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessInputStream;
import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessOutputStream;

/**
 * Created by haowu4 on 6/16/17.
 */
public class OptimizedVector implements Vector {
    Vector realVector;

    @Override
    public boolean isDense() {
        return realVector.isDense();
    }

    @Override
    public Vector optimize() {
        return this;
    }

    @Override
    public double get(int i) {
        return realVector.get(i);
    }

    @Override
    public double get(int i, double d) {
        return 0;
    }

    @Override
    public double set(int i, double v) {
        return 0;
    }

    @Override
    public double set(int i, double v, double d) {
        return 0;
    }

    @Override
    public void add(double v) {

    }

    @Override
    public void addAll(DVector v) {

    }

    @Override
    public double remove(int i) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
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
