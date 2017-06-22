package edu.illinois.cs.cogcomp.lbjava.learn.vector;

import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessInputStream;
import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessOutputStream;
import it.unimi.dsi.fastutil.ints.*;

/**
 * Created by haowu4 on 6/16/17.
 */
public class OptimizedVector implements Vector {


    static final double DOUBLE_DELTA = 0.0000001;
    static final String OPTIMIZE_AFTER_READ = "LBJava.OptimizeVector";


    static boolean isZero(double d) {
        return Math.abs(d) <= DOUBLE_DELTA;
    }

    private static final int SID = -1268324;

    Vector realVector;

    public OptimizedVector(double[] w) {
        this.realVector = new DVector(w);
    }

    public OptimizedVector() {
        this.realVector = new DVector(DVector.defaultCapacity);
    }

    public OptimizedVector(int w) {
        this.realVector = new DVector(w);
    }

    public OptimizedVector(Vector realVector) {
        this.realVector = realVector;
    }

    @Override
    public double dot(int[] exampleFeatures, double[] exampleValues) {
        return realVector.dot(exampleFeatures, exampleValues);
    }

    @Override
    public double dot(int[] exampleFeatures, double[] exampleValues, double defaultW) {
        return realVector.dot(exampleFeatures, exampleValues, defaultW);
    }

    /**
     * Optimize this vector according to sparsity.
     */
    public void optimize() {
        int nonZeroValue = 0;

        if (this.isDense()) {
            DVector sv = (DVector) this.realVector;

            for (double d : sv.vector) {
                if (!isZero(d)) {
                    nonZeroValue++;
                }
            }
        } else {
            SVector sv = (SVector) this.realVector;
            nonZeroValue = sv.values.size();
        }

        double sparsity = ((double) nonZeroValue) / this.size();
        if (sparsity < 0.05) {
            // This is a sparse vector.
            if (this.isDense()) {
                System.out.println("Transferring dense vector to sparse vector. " + sparsity);
                double[] vs = ((DVector) this.realVector).vector;
                Int2DoubleMap sparseValues = new Int2DoubleOpenCustomHashMap(new IntHash.Strategy() {
                    @Override
                    public int hashCode(int e) {
                        return e;
                    }

                    @Override
                    public boolean equals(int a, int b) {
                        return a == b;
                    }
                });
                for (int i = 0; i < vs.length; i++) {
                    double v = vs[i];
                    if (!isZero(v)) {
                        sparseValues.put(i, v);
                    }
                }
                ((Int2DoubleOpenCustomHashMap) sparseValues).trim();
                this.realVector = new SVector(sparseValues, this.size());
            }
        }
    }

    @Override
    public boolean isDense() {
        return realVector.isDense();
    }

    @Override
    public double get(int i) {
        return realVector.get(i);
    }

    @Override
    public double get(int i, double d) {
        return realVector.get(i, d);
    }

    @Override
    public double set(int i, double v) {
        return realVector.set(i, v);
    }

    @Override
    public double set(int i, double v, double d) {
        return realVector.set(i, v, d);
    }

    @Override
    public void add(double v) {
        realVector.add(v);
    }

    @Override
    public void addAll(DVector v) {
        realVector.addAll(v);
    }

    @Override
    public double remove(int i) {
        return realVector.remove(i);
    }

    @Override
    public int size() {
        return realVector.size();
    }

    @Override
    public double max() {
        return realVector.max();
    }

    @Override
    public double[] toArray() {
        return realVector.toArray();
    }

    @Override
    public void write(ExceptionlessOutputStream out) {
        out.writeInt(SID);
        out.writeBoolean(this.isDense());
        this.realVector.write(out);
    }

    @Override
    public void read(ExceptionlessInputStream in) {
        int sidOrLength = in.readInt();
        if (sidOrLength == SID) {
            // This is new version.
            boolean isDense = in.readBoolean();
            if (isDense) {
                this.realVector = new DVector();
            } else {
                this.realVector = new SVector(0);
            }
            this.realVector.read(in);
        } else {
            // Reading from an old version dump.
            readDenseVector(in, sidOrLength);
        }


        if (shouldOptimize()) {
            this.optimize();
        }

    }

    public static boolean shouldOptimize() {
        String confg = System.getProperty(OPTIMIZE_AFTER_READ);
        return confg != null && Boolean.valueOf(confg);
    }


    protected void readDenseVector(ExceptionlessInputStream in, int size) {
        if (size == 0) {
            this.realVector = new DVector();
        } else {
            double[] vector = new double[size];
            for (int i = 0; i < size; ++i) {
                vector[i] = in.readDouble();
            }
            this.realVector = new DVector(vector);
        }
    }

    @Override
    public Object clone() {
        return new OptimizedVector((Vector) this.realVector.clone());
    }
}
