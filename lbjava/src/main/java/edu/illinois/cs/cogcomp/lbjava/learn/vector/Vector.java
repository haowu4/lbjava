package edu.illinois.cs.cogcomp.lbjava.learn.vector;

import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessInputStream;
import edu.illinois.cs.cogcomp.core.datastructures.vectors.ExceptionlessOutputStream;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by haowu4 on 6/16/17.
 */
interface Vector extends Cloneable, Serializable {
    /**
     * Check if the implementation of this object is a dense vector.
     *
     * @return true is the implementation of this object is a dense vector.
     */
    boolean isDense();

    /**
     * Return an optimized version of this vector.
     * @return
     */
    Vector optimize();

    /**
     * Retrieves the value stored at the specified index of the vector, or 0 if the vector isn't
     * long enough.
     *
     * @param i The index of the value to retrieve.
     * @return The retrieved value.
     * @throws ArrayIndexOutOfBoundsException When <code>i</code> &lt; 0.
     **/
    double get(int i);

    /**
     * Retrieves the value stored at the specified index of the vector or <code>d</code> if the
     * vector isn't long enough.
     *
     * @param i The index of the value to retrieve.
     * @param d The default value.
     * @return The retrieved value.
     * @throws ArrayIndexOutOfBoundsException When <code>i</code> &lt; 0.
     **/
    double get(int i, double d);


    /**
     * Sets the value at the specified index to the given value.
     *
     * @param i The index of the value to set.
     * @param v The new value at that index.
     * @return The value that used to be at index <code>i</code>.
     * @throws ArrayIndexOutOfBoundsException When <code>i</code> &lt; 0.
     **/
    double set(int i, double v);

    /**
     * Sets the value at the specified index to the given value. If the given index is greater than
     * the vector's current size, the vector will expand to accomodate it.
     *
     * @param i The index of the value to set.
     * @param v The new value at that index.
     * @param d The default value for other new indexes that might get created.
     * @return The value that used to be at index <code>i</code>.
     * @throws ArrayIndexOutOfBoundsException When <code>i</code> &lt; 0.
     **/
    double set(int i, double v, double d);


    /**
     * Adds the specified value on to the end of the vector, expanding its capacity as necessary.
     *
     * @param v The new value to appear last in the vector.
     **/
    void add(double v);


    /**
     * Adds all the values in the given vector to the end of this vector, expanding its capacity as
     * necessary.
     *
     * @param v The new vector of values to appear at the end of this vector.
     **/
    void addAll(DVector v);


    /**
     * Removes the element at the specified index of the vector.
     *
     * @param i The index of the element to remove.
     * @return The removed element.
     **/
    double remove(int i);


    /**
     * Returns the value of {@link #size}.
     */
    int size();


    /**
     * Returns the value of the maximum element in the vector.
     */
    double max();


    /**
     * Sorts this vector in increasing order.
     */
    void sort();


    /**
     * Searches this vector for the specified value using the binary search algorithm. This vector
     * <strong>must</strong> be sorted (as by the {@link #sort()} method) prior to making this call.
     * If it is not sorted, the results are undefined. If this vector contains multiple elements
     * with the specified value, there is no guarantee which one will be found.
     *
     * @param v The value to be searched for.
     * @return The index of <code>v</code>, if it is contained in the vector; otherwise,
     * <code>(-(<i>insertion point</i>) - 1)</code>. The <i>insertion point</i> is defined
     * as the point at which <code>v</code> would be inserted into the vector: the index of
     * the first element greater than <code>v</code>, or the size of the vector if all
     * elements in the list are less than <code>v</code>. Note that this guarantees that the
     * return value will be &gt;= 0 if and only if <code>v</code> is found.
     **/
    int binarySearch(double v);

    /**
     * Returns a new array of <code>double</code>s containing the same data as this vector.
     **/
    double[] toArray();

    /**
     * Writes a binary representation of the vector.
     *
     * @param out The output stream.
     **/
    void write(ExceptionlessOutputStream out);


    /**
     * Reads the binary representation of a vector from the specified stream, overwriting the data
     * in this object.
     *
     * @param in The input stream.
     **/
    void read(ExceptionlessInputStream in);


}
