/*
 * %W% %E%
 *
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package pl.mn.edu.emory.mathcs.backport.java.util;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * This class consists exclusively of static methods that operate on or return
 * collections.  It contains polymorphic algorithms that operate on
 * collections, "wrappers", which return a new collection backed by a
 * specified collection, and a few other odds and ends.
 *
 * <p>The methods of this class all throw a <tt>NullPointerException</tt>
 * if the collections or class objects provided to them are null.
 *
 * <p>The documentation for the polymorphic algorithms contained in this class
 * generally includes a brief description of the <i>implementation</i>.  Such
 * descriptions should be regarded as <i>implementation notes</i>, rather than
 * parts of the <i>specification</i>.  Implementors should feel free to
 * substitute other algorithms, so long as the specification itself is adhered
 * to.  (For example, the algorithm used by <tt>sort</tt> does not have to be
 * a mergesort, but it does have to be <i>stable</i>.)
 *
 * <p>The "destructive" algorithms contained in this class, that is, the
 * algorithms that modify the collection on which they operate, are specified
 * to throw <tt>UnsupportedOperationException</tt> if the collection does not
 * support the appropriate mutation primitive(s), such as the <tt>set</tt>
 * method.  These algorithms may, but are not required to, throw this
 * exception if an invocation would have no effect on the collection.  For
 * example, invoking the <tt>sort</tt> method on an unmodifiable list that is
 * already sorted may or may not throw <tt>UnsupportedOperationException</tt>.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../guide/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @version %I%, %G%
 * @see	    Collection
 * @see	    Set
 * @see	    List
 * @see	    Map
 * @since   1.2
 */

public class Collections {
    // Suppresses default constructor, ensuring non-instantiability.
    private Collections() {
    }

    // Algorithms

    /*
     * Tuning parameters for algorithms - Many of the List algorithms have
     * two implementations, one of which is appropriate for RandomAccess
     * lists, the other for "sequential."  Often, the random access variant
     * yields better performance on small sequential access lists.  The
     * tuning parameters below determine the cutoff point for what constitutes
     * a "small" sequential access list for each algorithm.  The values below
     * were empirically determined to work well for LinkedList. Hopefully
     * they should be reasonable for other sequential access List
     * implementations.  Those doing performance work on this code would
     * do well to validate the values of these parameters from time to time.
     * (The first word of each tuning parameter name is the algorithm to which
     * it applies.)
     */
    private static final int BINARYSEARCH_THRESHOLD   = 5000;
    private static final int REVERSE_THRESHOLD        =   18;
    private static final int SHUFFLE_THRESHOLD        =    5;
    private static final int FILL_THRESHOLD           =   25;
    private static final int ROTATE_THRESHOLD         =  100;
    private static final int COPY_THRESHOLD           =   10;
    private static final int REPLACEALL_THRESHOLD     =   11;
    private static final int INDEXOFSUBLIST_THRESHOLD =   35;

    /**
     * Sorts the specified list into ascending order, according to the
     * <i>natural ordering</i> of its elements.  All elements in the list must
     * implement the <tt>Comparable</tt> interface.  Furthermore, all elements
     * in the list must be <i>mutually comparable</i> (that is,
     * <tt>e1.compareTo(e2)</tt> must not throw a <tt>ClassCastException</tt>
     * for any elements <tt>e1</tt> and <tt>e2</tt> in the list).<p>
     *
     * This sort is guaranteed to be <i>stable</i>:  equal elements will
     * not be reordered as a result of the sort.<p>
     *
     * The specified list must be modifiable, but need not be resizable.<p>
     *
     * The sorting algorithm is a modified mergesort (in which the merge is
     * omitted if the highest element in the low sublist is less than the
     * lowest element in the high sublist).  This algorithm offers guaranteed
     * n log(n) performance.
     *
     * This implementation dumps the specified list into an array, sorts
     * the array, and iterates over the list resetting each element
     * from the corresponding position in the array.  This avoids the
     * n<sup>2</sup> log(n) performance that would result from attempting
     * to sort a linked list in place.
     *
     * @param  list the list to be sorted.
     * @throws ClassCastException if the list contains elements that are not
     *	      mutually comparable</i> (for example, strings and integers).
     * @throws UnsupportedOperationException if the specified list's
     *	       list-iterator does not support theset</tt> operation.
     * @see Comparable
     */
    public static  void sort(List list) {
        Object[] a = list.toArray();
        Arrays.sort(a);
        ListIterator i = list.listIterator();
        for (int j=0; j<a.length; j++) {
            i.next();
            i.set(a[j]);
        }
    }

    /**
     * Sorts the specified list according to the order induced by the
     * specified comparator.  All elements in the list must be <i>mutually
     * comparable</i> using the specified comparator (that is,
     * <tt>c.compare(e1, e2)</tt> must not throw a <tt>ClassCastException</tt>
     * for any elements <tt>e1</tt> and <tt>e2</tt> in the list).<p>
     *
     * This sort is guaranteed to be <i>stable</i>:  equal elements will
     * not be reordered as a result of the sort.<p>
     *
     * The sorting algorithm is a modified mergesort (in which the merge is
     * omitted if the highest element in the low sublist is less than the
     * lowest element in the high sublist).  This algorithm offers guaranteed
     * n log(n) performance.
     *
     * The specified list must be modifiable, but need not be resizable.
     * This implementation dumps the specified list into an array, sorts
     * the array, and iterates over the list resetting each element
     * from the corresponding position in the array.  This avoids the
     * n<sup>2</sup> log(n) performance that would result from attempting
     * to sort a linked list in place.
     *
     * @param  list the list to be sorted.
     * @param  c the comparator to determine the order of the list.  A
     *        <tt>null</tt> value indicates that the elements' <i>natural
     *        ordering</i> should be used.
     * @throws ClassCastException if the list contains elements that are not
     *	      mutually comparable</i> using the specified comparator.
     * @throws UnsupportedOperationException if the specified list's
     *	       list-iterator does not support theset</tt> operation.
     * @see Comparator
     */
    public static void sort(List list, Comparator c) {
        Object[] a = list.toArray();
        Arrays.sort(a, (Comparator)c);
        ListIterator i = list.listIterator();
        for (int j=0; j<a.length; j++) {
            i.next();
            i.set(a[j]);
        }
    }


    /**
     * Searches the specified list for the specified object using the binary
     * search algorithm.  The list must be sorted into ascending order
     * according to the <i>natural ordering</i> of its elements (as by the
     * <tt>sort(List)</tt> method, above) prior to making this call.  If it is
     * not sorted, the results are undefined.  If the list contains multiple
     * elements equal to the specified object, there is no guarantee which one
     * will be found.<p>
     *
     * This method runs in log(n) time for a "random access" list (which
     * provides near-constant-time positional access).  If the specified list
     * does not implement the {@link RandomAccess} interface and is large,
     * this method will do an iterator-based binary search that performs
     * O(n) link traversals and O(log n) element comparisons.
     *
     * @param  list the list to be searched.
     * @param  key the key to be searched for.
     * @return the index of the search key, if it is contained in the list;
     *	       otherwise,(-(insertion point</i>) - 1)</tt>.  The
     *	      insertion point</i> is defined as the point at which the
     *	       key would be inserted into the list: the index of the first
     *	       element greater than the key, orlist.size()</tt>, if all
     *	       elements in the list are less than the specified key.  Note
     *	       that this guarantees that the return value will be &gt;= 0 if
     *	       and only if the key is found.
     * @throws ClassCastException if the list contains elements that are not
     *	      mutually comparable</i> (for example, strings and
     *	       integers), or the search key in not mutually comparable
     *	       with the elements of the list.
     * @see    Comparable
     * @see #sort(List)
     */
    public static
    int binarySearch(List list, Object key) {
        if (list instanceof RandomAccess || list.size()<BINARYSEARCH_THRESHOLD)
            return Collections.indexedBinarySearch(list, key);
        else
            return Collections.iteratorBinarySearch(list, key);
    }

    private static
    int indexedBinarySearch(List list, Object key)
    {
        int low = 0;
        int high = list.size()-1;

        while (low <= high) {
            int mid = (low + high) >> 1;
            Comparable midVal = (Comparable)list.get(mid);
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    private static
    int iteratorBinarySearch(List list, Object key)
    {
        int low = 0;
        int high = list.size()-1;
        ListIterator i = list.listIterator();

        while (low <= high) {
            int mid = (low + high) >> 1;
            Comparable midVal = (Comparable)get(i, mid);
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    /**
     * Gets the ith element from the given list by repositioning the specified
     * list listIterator.
     */
    private static Object get(ListIterator i, int index) {
        Object obj = null;
        int pos = i.nextIndex();
        if (pos <= index) {
            do {
                obj = i.next();
            } while (pos++ < index);
        } else {
            do {
                obj = i.previous();
            } while (--pos > index);
        }
        return obj;
    }

    /**
     * Searches the specified list for the specified object using the binary
     * search algorithm.  The list must be sorted into ascending order
     * according to the specified comparator (as by the <tt>Sort(List,
     * Comparator)</tt> method, above), prior to making this call.  If it is
     * not sorted, the results are undefined.  If the list contains multiple
     * elements equal to the specified object, there is no guarantee which one
     * will be found.<p>
     *
     * This method runs in log(n) time for a "random access" list (which
     * provides near-constant-time positional access).  If the specified list
     * does not implement the {@link RandomAccess} interface and is large,
     * this method will do an iterator-based binary search that performs
     * O(n) link traversals and O(log n) element comparisons.
     *
     * @param  list the list to be searched.
     * @param  key the key to be searched for.
     * @param  c the comparator by which the list is ordered.  A
     *        <tt>null</tt> value indicates that the elements' <i>natural
     *        ordering</i> should be used.
     * @return the index of the search key, if it is contained in the list;
     *	       otherwise,(-(insertion point</i>) - 1)</tt>.  The
     *	      insertion point</i> is defined as the point at which the
     *	       key would be inserted into the list: the index of the first
     *	       element greater than the key, orlist.size()</tt>, if all
     *	       elements in the list are less than the specified key.  Note
     *	       that this guarantees that the return value will be &gt;= 0 if
     *	       and only if the key is found.
     * @throws ClassCastException if the list contains elements that are not
     *	      mutually comparable</i> using the specified comparator,
     *	       or the search key in not mutually comparable with the
     *	       elements of the list using this comparator.
     * @see    Comparable
     * @see #sort(List, Comparator)
     */
    public static int binarySearch(List list, Object key, Comparator c) {
        if (c==null)
            return binarySearch((List) list, key);

        if (list instanceof RandomAccess || list.size()<BINARYSEARCH_THRESHOLD)
            return Collections.indexedBinarySearch(list, key, c);
        else
            return Collections.iteratorBinarySearch(list, key, c);
    }

    private static int indexedBinarySearch(List l, Object key, Comparator c) {
        int low = 0;
        int high = l.size()-1;

        while (low <= high) {
            int mid = (low + high) >> 1;
            Object midVal = l.get(mid);
            int cmp = c.compare(midVal, key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    private static int iteratorBinarySearch(List l, Object key, Comparator c) {
        int low = 0;
        int high = l.size()-1;
        ListIterator i = l.listIterator();

        while (low <= high) {
            int mid = (low + high) >> 1;
            Object midVal = get(i, mid);
            int cmp = c.compare(midVal, key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    private interface SelfComparable extends Comparable {}


    /**
     * Reverses the order of the elements in the specified list.<p>
     *
     * This method runs in linear time.
     *
     * @param  list the list whose elements are to be reversed.
     * @throws UnsupportedOperationException if the specified list or
     *         its list-iterator does not support the <tt>set</tt> operation.
     */
    public static void reverse(List list) {
        int size = list.size();
        if (size < REVERSE_THRESHOLD || list instanceof RandomAccess) {
            for (int i=0, mid=size>>1, j=size-1; i<mid; i++, j--)
                swap(list, i, j);
        } else {
            ListIterator fwd = list.listIterator();
            ListIterator rev = list.listIterator(size);
            for (int i=0, mid=list.size()>>1; i<mid; i++) {
                Object tmp = fwd.next();
                fwd.set(rev.previous());
                rev.set(tmp);
            }
        }
    }

    /**
     * Randomly permutes the specified list using a default source of
     * randomness.  All permutations occur with approximately equal
     * likelihood.<p>
     *
     * The hedge "approximately" is used in the foregoing description because
     * default source of randomness is only approximately an unbiased source
     * of independently chosen bits. If it were a perfect source of randomly
     * chosen bits, then the algorithm would choose permutations with perfect
     * uniformity.<p>
     *
     * This implementation traverses the list backwards, from the last element
     * up to the second, repeatedly swapping a randomly selected element into
     * the "current position".  Elements are randomly selected from the
     * portion of the list that runs from the first element to the current
     * position, inclusive.<p>
     *
     * This method runs in linear time.  If the specified list does not
     * implement the {@link RandomAccess} interface and is large, this
     * implementation dumps the specified list into an array before shuffling
     * it, and dumps the shuffled array back into the list.  This avoids the
     * quadratic behavior that would result from shuffling a "sequential
     * access" list in place.
     *
     * @param  list the list to be shuffled.
     * @throws UnsupportedOperationException if the specified list or
     *         its list-iterator does not support the <tt>set</tt> operation.
     */
    public static void shuffle(List list) {
        if (r == null) {
            r = new Random();
        }
        shuffle(list, r);
    }
    private static Random r;

    /**
     * Randomly permute the specified list using the specified source of
     * randomness.  All permutations occur with equal likelihood
     * assuming that the source of randomness is fair.<p>
     *
     * This implementation traverses the list backwards, from the last element
     * up to the second, repeatedly swapping a randomly selected element into
     * the "current position".  Elements are randomly selected from the
     * portion of the list that runs from the first element to the current
     * position, inclusive.<p>
     *
     * This method runs in linear time.  If the specified list does not
     * implement the {@link RandomAccess} interface and is large, this
     * implementation dumps the specified list into an array before shuffling
     * it, and dumps the shuffled array back into the list.  This avoids the
     * quadratic behavior that would result from shuffling a "sequential
     * access" list in place.
     *
     * @param  list the list to be shuffled.
     * @param  rnd the source of randomness to use to shuffle the list.
     * @throws UnsupportedOperationException if the specified list or its
     *         list-iterator does not support the <tt>set</tt> operation.
     */
    public static void shuffle(List list, Random rnd) {
        int size = list.size();
        if (size < SHUFFLE_THRESHOLD || list instanceof RandomAccess) {
            for (int i=size; i>1; i--)
                swap(list, i-1, rnd.nextInt(i));
        } else {
            Object arr[] = list.toArray();

            // Shuffle array
            for (int i=size; i>1; i--)
                swap(arr, i-1, rnd.nextInt(i));

            // Dump array back into list
            ListIterator it = list.listIterator();
            for (int i=0; i<arr.length; i++) {
                it.next();
                it.set(arr[i]);
            }
        }
    }

    /**
     * Swaps the elements at the specified positions in the specified list.
     * (If the specified positions are equal, invoking this method leaves
     * the list unchanged.)
     *
     * @param list The list in which to swap elements.
     * @param i the index of one element to be swapped.
     * @param j the index of the other element to be swapped.
     * @throws IndexOutOfBoundsException if either <tt>i</tt> or <tt>j</tt>
     *         is out of range (i &lt; 0 || i &gt;= list.size()
     *         || j &lt; 0 || j &gt;= list.size()).
     * @since 1.4
     */
    public static void swap(List list, int i, int j) {
        final List l = list;
        l.set(i, l.set(j, l.get(i)));
    }

    /**
     * Swaps the two specified elements in the specified array.
     */
    private static void swap(Object[] arr, int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * Replaces all of the elements of the specified list with the specified
     * element. <p>
     *
     * This method runs in linear time.
     *
     * @param  list the list to be filled with the specified element.
     * @param  obj The element with which to fill the specified list.
     * @throws UnsupportedOperationException if the specified list or its
     *	       list-iterator does not support theset</tt> operation.
     */
    public static void fill(List list, Object obj) {
        int size = list.size();

        if (size < FILL_THRESHOLD || list instanceof RandomAccess) {
            for (int i=0; i<size; i++)
                list.set(i, obj);
        } else {
            ListIterator itr = list.listIterator();
            for (int i=0; i<size; i++) {
                itr.next();
                itr.set(obj);
            }
        }
    }

    /**
     * Copies all of the elements from one list into another.  After the
     * operation, the index of each copied element in the destination list
     * will be identical to its index in the source list.  The destination
     * list must be at least as long as the source list.  If it is longer, the
     * remaining elements in the destination list are unaffected. <p>
     *
     * This method runs in linear time.
     *
     * @param  dest The destination list.
     * @param  src The source list.
     * @throws IndexOutOfBoundsException if the destination list is too small
     *         to contain the entire source List.
     * @throws UnsupportedOperationException if the destination list's
     *         list-iterator does not support the <tt>set</tt> operation.
     */
    public static void copy(List dest, List src) {
        int srcSize = src.size();
        if (srcSize > dest.size())
            throw new IndexOutOfBoundsException("Source does not fit in dest");

        if (srcSize < COPY_THRESHOLD ||
            (src instanceof RandomAccess && dest instanceof RandomAccess)) {
            for (int i=0; i<srcSize; i++)
                dest.set(i, src.get(i));
        } else {
            ListIterator di=dest.listIterator();
            ListIterator si=src.listIterator();
            for (int i=0; i<srcSize; i++) {
                di.next();
                di.set(si.next());
            }
        }
    }

    /**
     * Returns the minimum element of the given collection, according to the
     * <i>natural ordering</i> of its elements.  All elements in the
     * collection must implement the <tt>Comparable</tt> interface.
     * Furthermore, all elements in the collection must be <i>mutually
     * comparable</i> (that is, <tt>e1.compareTo(e2)</tt> must not throw a
     * <tt>ClassCastException</tt> for any elements <tt>e1</tt> and
     * <tt>e2</tt> in the collection).<p>
     *
     * This method iterates over the entire collection, hence it requires
     * time proportional to the size of the collection.
     *
     * @param  coll the collection whose minimum element is to be determined.
     * @return the minimum element of the given collection, according
     *         to the <i>natural ordering</i> of its elements.
     * @throws ClassCastException if the collection contains elements that are
     *	       notmutually comparable</i> (for example, strings and
     *	       integers).
     * @throws NoSuchElementException if the collection is empty.
     * @see Comparable
     */
    public static Object min(Collection coll) {
        Iterator i = coll.iterator();
        Object candidate = i.next();

        while (i.hasNext()) {
            Object next = i.next();
            if (((Comparable)next).compareTo(candidate) < 0)
                candidate = next;
        }
        return candidate;
    }

    /**
     * Returns the minimum element of the given collection, according to the
     * order induced by the specified comparator.  All elements in the
     * collection must be <i>mutually comparable</i> by the specified
     * comparator (that is, <tt>comp.compare(e1, e2)</tt> must not throw a
     * <tt>ClassCastException</tt> for any elements <tt>e1</tt> and
     * <tt>e2</tt> in the collection).<p>
     *
     * This method iterates over the entire collection, hence it requires
     * time proportional to the size of the collection.
     *
     * @param  coll the collection whose minimum element is to be determined.
     * @param  comp the comparator with which to determine the minimum element.
     *         A <tt>null</tt> value indicates that the elements' <i>natural
     *         ordering</i> should be used.
     * @return the minimum element of the given collection, according
     *         to the specified comparator.
     * @throws ClassCastException if the collection contains elements that are
     *	       notmutually comparable</i> using the specified comparator.
     * @throws NoSuchElementException if the collection is empty.
     * @see Comparable
     */
    public static Object min(Collection coll, Comparator comp) {
        if (comp==null)
            return min((Collection) (Collection) coll);

        Iterator i = coll.iterator();
        Object candidate = i.next();

        while (i.hasNext()) {
            Object next = i.next();
            if (comp.compare(next, candidate) < 0)
                candidate = next;
        }
        return candidate;
    }

    /**
     * Returns the maximum element of the given collection, according to the
     * <i>natural ordering</i> of its elements.  All elements in the
     * collection must implement the <tt>Comparable</tt> interface.
     * Furthermore, all elements in the collection must be <i>mutually
     * comparable</i> (that is, <tt>e1.compareTo(e2)</tt> must not throw a
     * <tt>ClassCastException</tt> for any elements <tt>e1</tt> and
     * <tt>e2</tt> in the collection).<p>
     *
     * This method iterates over the entire collection, hence it requires
     * time proportional to the size of the collection.
     *
     * @param  coll the collection whose maximum element is to be determined.
     * @return the maximum element of the given collection, according
     *         to the <i>natural ordering</i> of its elements.
     * @throws ClassCastException if the collection contains elements that are
     *	       notmutually comparable</i> (for example, strings and
     *         integers).
     * @throws NoSuchElementException if the collection is empty.
     * @see Comparable
     */
    public static Object max(Collection coll) {
        Iterator i = coll.iterator();
        Object candidate = i.next();

        while (i.hasNext()) {
            Object next = i.next();
            if (((Comparable)next).compareTo(candidate) > 0)
                candidate = next;
        }
        return candidate;
    }

    /**
     * Returns the maximum element of the given collection, according to the
     * order induced by the specified comparator.  All elements in the
     * collection must be <i>mutually comparable</i> by the specified
     * comparator (that is, <tt>comp.compare(e1, e2)</tt> must not throw a
     * <tt>ClassCastException</tt> for any elements <tt>e1</tt> and
     * <tt>e2</tt> in the collection).<p>
     *
     * This method iterates over the entire collection, hence it requires
     * time proportional to the size of the collection.
     *
     * @param  coll the collection whose maximum element is to be determined.
     * @param  comp the comparator with which to determine the maximum element.
     *         A <tt>null</tt> value indicates that the elements' <i>natural
     *        ordering</i> should be used.
     * @return the maximum element of the given collection, according
     *         to the specified comparator.
     * @throws ClassCastException if the collection contains elements that are
     *	       notmutually comparable</i> using the specified comparator.
     * @throws NoSuchElementException if the collection is empty.
     * @see Comparable
     */
    public static Object max(Collection coll, Comparator comp) {
        if (comp==null)
            return max((Collection) (Collection) coll);

        Iterator i = coll.iterator();
        Object candidate = i.next();

        while (i.hasNext()) {
            Object next = i.next();
            if (comp.compare(next, candidate) > 0)
                candidate = next;
        }
        return candidate;
    }

    /**
     * Rotates the elements in the specified list by the specified distance.
     * After calling this method, the element at index <tt>i</tt> will be
     * the element previously at index <tt>(i - distance)</tt> mod
     * <tt>list.size()</tt>, for all values of <tt>i</tt> between <tt>0</tt>
     * and <tt>list.size()-1</tt>, inclusive.  (This method has no effect on
     * the size of the list.)
     *
     * <p>For example, suppose <tt>list</tt> comprises<tt> [t, a, n, k, s]</tt>.
     * After invoking <tt>Collections.rotate(list, 1)</tt> (or
     * <tt>Collections.rotate(list, -4)</tt>), <tt>list</tt> will comprise
     * <tt>[s, t, a, n, k]</tt>.
     *
     * <p>Note that this method can usefully be applied to sublists to
     * move one or more elements within a list while preserving the
     * order of the remaining elements.  For example, the following idiom
     * moves the element at index <tt>j</tt> forward to position
     * <tt>k</tt> (which must be greater than or equal to <tt>j</tt>):
     * <pre>
     *     Collections.rotate(list.subList(j, k+1), -1);
     * </pre>
     * To make this concrete, suppose <tt>list</tt> comprises
     * <tt>[a, b, c, d, e]</tt>.  To move the element at index <tt>1</tt>
     * (<tt>b</tt>) forward two positions, perform the following invocation:
     * <pre>
     *     Collections.rotate(l.subList(1, 4), -1);
     * </pre>
     * The resulting list is <tt>[a, c, d, b, e]</tt>.
     *
     * <p>To move more than one element forward, increase the absolute value
     * of the rotation distance.  To move elements backward, use a positive
     * shift distance.
     *
     * <p>If the specified list is small or implements the {@link
     * RandomAccess} interface, this implementation exchanges the first
     * element into the location it should go, and then repeatedly exchanges
     * the displaced element into the location it should go until a displaced
     * element is swapped into the first element.  If necessary, the process
     * is repeated on the second and successive elements, until the rotation
     * is complete.  If the specified list is large and doesn't implement the
     * <tt>RandomAccess</tt> interface, this implementation breaks the
     * list into two sublist views around index <tt>-distance mod size</tt>.
     * Then the {@link #reverse(List)} method is invoked on each sublist view,
     * and finally it is invoked on the entire list.  For a more complete
     * description of both algorithms, see Section 2.3 of Jon Bentley's
     * <i>Programming Pearls</i> (Addison-Wesley, 1986).
     *
     * @param list the list to be rotated.
     * @param distance the distance to rotate the list.  There are no
     *        constraints on this value; it may be zero, negative, or
     *        greater than <tt>list.size()</tt>.
     * @throws UnsupportedOperationException if the specified list or
     *         its list-iterator does not support the <tt>set</tt> operation.
     * @since 1.4
     */
    public static void rotate(List list, int distance) {
        if (list instanceof RandomAccess || list.size() < ROTATE_THRESHOLD)
            rotate1((List)list, distance);
        else
            rotate2((List)list, distance);
    }

    private static void rotate1(List list, int distance) {
        int size = list.size();
        if (size == 0)
            return;
        distance = distance % size;
        if (distance < 0)
            distance += size;
        if (distance == 0)
            return;

        for (int cycleStart = 0, nMoved = 0; nMoved != size; cycleStart++) {
            Object displaced = list.get(cycleStart);
            int i = cycleStart;
            do {
                i += distance;
                if (i >= size)
                    i -= size;
                displaced = list.set(i, displaced);
                nMoved ++;
            } while(i != cycleStart);
        }
    }

    private static void rotate2(List list, int distance) {
        int size = list.size();
        if (size == 0)
            return;
        int mid =  -distance % size;
        if (mid < 0)
            mid += size;
        if (mid == 0)
            return;

        reverse(list.subList(0, mid));
        reverse(list.subList(mid, size));
        reverse(list);
    }

    /**
     * Replaces all occurrences of one specified value in a list with another.
     * More formally, replaces with <tt>newVal</tt> each element <tt>e</tt>
     * in <tt>list</tt> such that
     * <tt>(oldVal==null ? e==null : oldVal.equals(e))</tt>.
     * (This method has no effect on the size of the list.)
     *
     * @param list the list in which replacement is to occur.
     * @param oldVal the old value to be replaced.
     * @param newVal the new value with which <tt>oldVal</tt> is to be
     *        replaced.
     * @return <tt>true</tt> if <tt>list</tt> contained one or more elements
     *         <tt>e</tt> such that
     *         <tt>(oldVal==null ?  e==null : oldVal.equals(e))</tt>.
     * @throws UnsupportedOperationException if the specified list or
     *         its list-iterator does not support the <tt>set</tt> operation.
     * @since  1.4
     */
    public static boolean replaceAll(List list, Object oldVal, Object newVal) {
        boolean result = false;
        int size = list.size();
        if (size < REPLACEALL_THRESHOLD || list instanceof RandomAccess) {
            if (oldVal==null) {
                for (int i=0; i<size; i++) {
                    if (list.get(i)==null) {
                        list.set(i, newVal);
                        result = true;
                    }
                }
            } else {
                for (int i=0; i<size; i++) {
                    if (oldVal.equals(list.get(i))) {
                        list.set(i, newVal);
                        result = true;
                    }
                }
            }
        } else {
            ListIterator itr=list.listIterator();
            if (oldVal==null) {
                for (int i=0; i<size; i++) {
                    if (itr.next()==null) {
                        itr.set(newVal);
                        result = true;
                    }
                }
            } else {
                for (int i=0; i<size; i++) {
                    if (oldVal.equals(itr.next())) {
                        itr.set(newVal);
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Returns the starting position of the first occurrence of the specified
     * target list within the specified source list, or -1 if there is no
     * such occurrence.  More formally, returns the lowest index <tt>i</tt>
     * such that <tt>source.subList(i, i+target.size()).equals(target)</tt>,
     * or -1 if there is no such index.  (Returns -1 if
     * <tt>target.size() > source.size()</tt>.)
     *
     * <p>This implementation uses the "brute force" technique of scanning
     * over the source list, looking for a match with the target at each
     * location in turn.
     *
     * @param source the list in which to search for the first occurrence
     *        of <tt>target</tt>.
     * @param target the list to search for as a subList of <tt>source</tt>.
     * @return the starting position of the first occurrence of the specified
     *         target list within the specified source list, or -1 if there
     *         is no such occurrence.
     * @since  1.4
     */
    public static int indexOfSubList(List source, List target) {
        int sourceSize = source.size();
        int targetSize = target.size();
        int maxCandidate = sourceSize - targetSize;

        if (sourceSize < INDEXOFSUBLIST_THRESHOLD ||
            (source instanceof RandomAccess&&target instanceof RandomAccess)) {
        nextCand:
            for (int candidate = 0; candidate <= maxCandidate; candidate++) {
                for (int i=0, j=candidate; i<targetSize; i++, j++)
                    if (!eq(target.get(i), source.get(j)))
                        continue nextCand;  // Element mismatch, try next cand
                return candidate;  // All elements of candidate matched target
            }
        } else {  // Iterator version of above algorithm
            ListIterator si = source.listIterator();
        nextCand:
            for (int candidate = 0; candidate <= maxCandidate; candidate++) {
                ListIterator ti = target.listIterator();
                for (int i=0; i<targetSize; i++) {
                    if (!eq(ti.next(), si.next())) {
                        // Back up source iterator to next candidate
                        for (int j=0; j<i; j++)
                            si.previous();
                        continue nextCand;
                    }
                }
                return candidate;
            }
        }
        return -1;  // No candidate matched the target
    }

    /**
     * Returns the starting position of the last occurrence of the specified
     * target list within the specified source list, or -1 if there is no such
     * occurrence.  More formally, returns the highest index <tt>i</tt>
     * such that <tt>source.subList(i, i+target.size()).equals(target)</tt>,
     * or -1 if there is no such index.  (Returns -1 if
     * <tt>target.size() > source.size()</tt>.)
     *
     * <p>This implementation uses the "brute force" technique of iterating
     * over the source list, looking for a match with the target at each
     * location in turn.
     *
     * @param source the list in which to search for the last occurrence
     *        of <tt>target</tt>.
     * @param target the list to search for as a subList of <tt>source</tt>.
     * @return the starting position of the last occurrence of the specified
     *         target list within the specified source list, or -1 if there
     *         is no such occurrence.
     * @since  1.4
     */
    public static int lastIndexOfSubList(List source, List target) {
        int sourceSize = source.size();
        int targetSize = target.size();
        int maxCandidate = sourceSize - targetSize;

        if (sourceSize < INDEXOFSUBLIST_THRESHOLD ||
            source instanceof RandomAccess) {   // Index access version
        nextCand:
            for (int candidate = maxCandidate; candidate >= 0; candidate--) {
                for (int i=0, j=candidate; i<targetSize; i++, j++)
                    if (!eq(target.get(i), source.get(j)))
                        continue nextCand;  // Element mismatch, try next cand
                return candidate;  // All elements of candidate matched target
            }
        } else {  // Iterator version of above algorithm
            if (maxCandidate < 0)
                return -1;
            ListIterator si = source.listIterator(maxCandidate);
        nextCand:
            for (int candidate = maxCandidate; candidate >= 0; candidate--) {
                ListIterator ti = target.listIterator();
                for (int i=0; i<targetSize; i++) {
                    if (!eq(ti.next(), si.next())) {
                        if (candidate != 0) {
                            // Back up source iterator to next candidate
                            for (int j=0; j<=i+1; j++)
                                si.previous();
                        }
                        continue nextCand;
                    }
                }
                return candidate;
            }
        }
        return -1;  // No candidate matched the target
    }


    // Unmodifiable Wrappers

    /**
     * Returns an unmodifiable view of the specified collection.  This method
     * allows modules to provide users with "read-only" access to internal
     * collections.  Query operations on the returned collection "read through"
     * to the specified collection, and attempts to modify the returned
     * collection, whether direct or via its iterator, result in an
     * <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned collection does <i>not</i> pass the hashCode and equals
     * operations through to the backing collection, but relies on
     * <tt>Object</tt>'s <tt>equals</tt> and <tt>hashCode</tt> methods.  This
     * is necessary to preserve the contracts of these operations in the case
     * that the backing collection is a set or a list.<p>
     *
     * The returned collection will be serializable if the specified collection
     * is serializable.
     *
     * @param  c the collection for which an unmodifiable view is to be
     *	       returned.
     * @return an unmodifiable view of the specified collection.
     */
    public static Collection unmodifiableCollection(Collection c) {
        return new UnmodifiableCollection(c);
    }

    /**
     * @serial include
     */
    static class UnmodifiableCollection implements Collection, Serializable {
        // use serialVersionUID from JDK 1.2.2 for interoperability
        private static final long serialVersionUID = 1820017752578914078L;

        final Collection c;

        UnmodifiableCollection(Collection c) {
            if (c==null)
                throw new NullPointerException();
            this.c = c;
        }

        public int size() 		    {return c.size();}
        public boolean isEmpty() 	    {return c.isEmpty();}
        public boolean contains(Object o)   {return c.contains(o);}
        public Object[] toArray()           {return c.toArray();}
        public Object[] toArray(Object[] a)       {return c.toArray(a);}
        public String toString()            {return c.toString();}

        public Iterator iterator() {
            return new Iterator() {
                Iterator i = c.iterator();

                public boolean hasNext() {return i.hasNext();}
                public Object next() 	 {return i.next();}
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        public boolean add(Object e){
            throw new UnsupportedOperationException();
        }
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        public boolean containsAll(Collection coll) {
            return c.containsAll(coll);
        }
        public boolean addAll(Collection coll) {
            throw new UnsupportedOperationException();
        }
        public boolean removeAll(Collection coll) {
            throw new UnsupportedOperationException();
        }
        public boolean retainAll(Collection coll) {
            throw new UnsupportedOperationException();
        }
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns an unmodifiable view of the specified set.  This method allows
     * modules to provide users with "read-only" access to internal sets.
     * Query operations on the returned set "read through" to the specified
     * set, and attempts to modify the returned set, whether direct or via its
     * iterator, result in an <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned set will be serializable if the specified set
     * is serializable.
     *
     * @param  s the set for which an unmodifiable view is to be returned.
     * @return an unmodifiable view of the specified set.
     */
    public static Set unmodifiableSet(Set s) {
        return new UnmodifiableSet(s);
    }

    /**
     * @serial include
     */
    static class UnmodifiableSet extends UnmodifiableCollection
                                     implements Set, Serializable {
        private static final long serialVersionUID = -9215047833775013803L;

        UnmodifiableSet(Set s)	{super(s);}
        public boolean equals(Object o) {return c.equals(o);}
        public int hashCode() 		{return c.hashCode();}
    }

    /**
     * Returns an unmodifiable view of the specified sorted set.  This method
     * allows modules to provide users with "read-only" access to internal
     * sorted sets.  Query operations on the returned sorted set "read
     * through" to the specified sorted set.  Attempts to modify the returned
     * sorted set, whether direct, via its iterator, or via its
     * <tt>subSet</tt>, <tt>headSet</tt>, or <tt>tailSet</tt> views, result in
     * an <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned sorted set will be serializable if the specified sorted set
     * is serializable.
     *
     * @param s the sorted set for which an unmodifiable view is to be
     *        returned.
     * @return an unmodifiable view of the specified sorted set.
     */
    public static SortedSet unmodifiableSortedSet(SortedSet s) {
        return new UnmodifiableSortedSet(s);
    }

    /**
     * @serial include
     */
    static class UnmodifiableSortedSet
                             extends UnmodifiableSet
                             implements SortedSet, Serializable {
        private static final long serialVersionUID = -4929149591599911165L;
        private final SortedSet ss;

        UnmodifiableSortedSet(SortedSet s) {super(s); ss = s;}

        public Comparator comparator() {return ss.comparator();}

        public SortedSet subSet(Object fromElement, Object toElement) {
            return new UnmodifiableSortedSet(ss.subSet(fromElement,toElement));
        }
        public SortedSet headSet(Object toElement) {
            return new UnmodifiableSortedSet(ss.headSet(toElement));
        }
        public SortedSet tailSet(Object fromElement) {
            return new UnmodifiableSortedSet(ss.tailSet(fromElement));
        }

        public Object first() 	           {return ss.first();}
        public Object last()  	           {return ss.last();}
    }

    /**
     * Returns an unmodifiable view of the specified list.  This method allows
     * modules to provide users with "read-only" access to internal
     * lists.  Query operations on the returned list "read through" to the
     * specified list, and attempts to modify the returned list, whether
     * direct or via its iterator, result in an
     * <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned list will be serializable if the specified list
     * is serializable. Similarly, the returned list will implement
     * {@link RandomAccess} if the specified list does.
     *
     * @param  list the list for which an unmodifiable view is to be returned.
     * @return an unmodifiable view of the specified list.
     */
    public static List unmodifiableList(List list) {
        return (list instanceof RandomAccess ?
                new UnmodifiableRandomAccessList(list) :
                new UnmodifiableList(list));
    }

    /**
     * @serial include
     */
    static class UnmodifiableList extends UnmodifiableCollection
                                      implements List {
        static final long serialVersionUID = -283967356065247728L;
        final List list;

        UnmodifiableList(List list) {
            super(list);
            this.list = list;
        }

        public boolean equals(Object o) {return list.equals(o);}
        public int hashCode() 		{return list.hashCode();}

        public Object get(int index) {return list.get(index);}
        public Object set(int index, Object element) {
            throw new UnsupportedOperationException();
        }
        public void add(int index, Object element) {
            throw new UnsupportedOperationException();
        }
        public Object remove(int index) {
            throw new UnsupportedOperationException();
        }
        public int indexOf(Object o)            {return list.indexOf(o);}
        public int lastIndexOf(Object o)        {return list.lastIndexOf(o);}
        public boolean addAll(int index, Collection c) {
            throw new UnsupportedOperationException();
        }
        public ListIterator listIterator() 	{return listIterator(0);}

        public ListIterator listIterator(final int index) {
            return new ListIterator() {
                ListIterator i = list.listIterator(index);

                public boolean hasNext()     {return i.hasNext();}
                public Object next()		     {return i.next();}
                public boolean hasPrevious() {return i.hasPrevious();}
                public Object previous()	     {return i.previous();}
                public int nextIndex()       {return i.nextIndex();}
                public int previousIndex()   {return i.previousIndex();}

                public void remove() {
                    throw new UnsupportedOperationException();
                }
                public void set(Object e) {
                    throw new UnsupportedOperationException();
                }
                public void add(Object e) {
                    throw new UnsupportedOperationException();
                }
            };
        }

        public List subList(int fromIndex, int toIndex) {
            return new UnmodifiableList(list.subList(fromIndex, toIndex));
        }

        /**
         * UnmodifiableRandomAccessList instances are serialized as
         * UnmodifiableList instances to allow them to be deserialized
         * in pre-1.4 JREs (which do not have UnmodifiableRandomAccessList).
         * This method inverts the transformation.  As a beneficial
         * side-effect, it also grafts the RandomAccess marker onto
         * UnmodifiableList instances that were serialized in pre-1.4 JREs.
         *
         * Note: Unfortunately, UnmodifiableRandomAccessList instances
         * serialized in 1.4.1 and deserialized in 1.4 will become
         * UnmodifiableList instances, as this method was missing in 1.4.
         */
        private Object readResolve() {
            return (list instanceof RandomAccess
                    ? new UnmodifiableRandomAccessList(list)
                    : this);
        }
    }

    /**
     * @serial include
     */
    static class UnmodifiableRandomAccessList extends UnmodifiableList
                                              implements RandomAccess
    {
        UnmodifiableRandomAccessList(List list) {
            super(list);
        }

        public List subList(int fromIndex, int toIndex) {
            return new UnmodifiableRandomAccessList(
                list.subList(fromIndex, toIndex));
        }

        private static final long serialVersionUID = -2542308836966382001L;

        /**
         * Allows instances to be deserialized in pre-1.4 JREs (which do
         * not have UnmodifiableRandomAccessList).  UnmodifiableList has
         * a readResolve method that inverts this transformation upon
         * deserialization.
         */
        private Object writeReplace() {
            return new UnmodifiableList(list);
        }
    }

    /**
     * Returns an unmodifiable view of the specified map.  This method
     * allows modules to provide users with "read-only" access to internal
     * maps.  Query operations on the returned map "read through"
     * to the specified map, and attempts to modify the returned
     * map, whether direct or via its collection views, result in an
     * <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned map will be serializable if the specified map
     * is serializable.
     *
     * @param  m the map for which an unmodifiable view is to be returned.
     * @return an unmodifiable view of the specified map.
     */
    public static Map unmodifiableMap(Map m) {
        return new UnmodifiableMap(m);
    }

    /**
     * @serial include
     */
    private static class UnmodifiableMap implements Map, Serializable {
        // use serialVersionUID from JDK 1.2.2 for interoperability
        private static final long serialVersionUID = -1034234728574286014L;

        private final Map m;

        UnmodifiableMap(Map m) {
            if (m==null)
                throw new NullPointerException();
            this.m = m;
        }

        public int size() 		         {return m.size();}
        public boolean isEmpty() 	         {return m.isEmpty();}
        public boolean containsKey(Object key)   {return m.containsKey(key);}
        public boolean containsValue(Object val) {return m.containsValue(val);}
        public Object get(Object key) 	         {return m.get(key);}

        public Object put(Object key, Object value) {
            throw new UnsupportedOperationException();
        }
        public Object remove(Object key) {
            throw new UnsupportedOperationException();
        }
        public void putAll(Map m) {
            throw new UnsupportedOperationException();
        }
        public void clear() {
            throw new UnsupportedOperationException();
        }

        private transient Set keySet = null;
        private transient Set entrySet = null;
        private transient Collection values = null;

        public Set keySet() {
            if (keySet==null)
                keySet = unmodifiableSet(m.keySet());
            return keySet;
        }

        public Set entrySet() {
            if (entrySet==null)
                entrySet = new UnmodifiableEntrySet(m.entrySet());
            return entrySet;
        }

        public Collection values() {
            if (values==null)
                values = unmodifiableCollection(m.values());
            return values;
        }

        public boolean equals(Object o) {return m.equals(o);}
        public int hashCode()           {return m.hashCode();}
        public String toString()        {return m.toString();}

        /**
         * We need this class in addition to UnmodifiableSet as
         * Map.Entries themselves permit modification of the backing Map
         * via their setValue operation.  This class is subtle: there are
         * many possible attacks that must be thwarted.
         *
         * @serial include
         */
        static class UnmodifiableEntrySet
            extends UnmodifiableSet {
            private static final long serialVersionUID = 7854390611657943733L;

            UnmodifiableEntrySet(Set s) {
                super((Set)s);
            }
            public Iterator iterator() {
                return new Iterator() {
                    Iterator i = c.iterator();

                    public boolean hasNext() {
                        return i.hasNext();
                    }
                    public Object next() {
                        return new UnmodifiableEntry((Map.Entry)i.next());
                    }
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            public Object[] toArray() {
                Object[] a = c.toArray();
                for (int i=0; i<a.length; i++)
                    a[i] = new UnmodifiableEntry((Map.Entry)a[i]);
                return a;
            }

            public Object[] toArray(Object[] a) {
                // We don't pass a to c.toArray, to avoid window of
                // vulnerability wherein an unscrupulous multithreaded client
                // could get his hands on raw (unwrapped) Entries from c.
                Object[] arr =
                    c.toArray(
                        a.length==0 ? a :
                        (Object[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), 0));

                for (int i=0; i<arr.length; i++)
                    arr[i] = new UnmodifiableEntry((Map.Entry)arr[i]);

                if (arr.length > a.length)
                    return (Object[])arr;

                System.arraycopy(arr, 0, a, 0, arr.length);
                if (a.length > arr.length)
                    a[arr.length] = null;
                return a;
            }

            /**
             * This method is overridden to protect the backing set against
             * an object with a nefarious equals function that senses
             * that the equality-candidate is Map.Entry and calls its
             * setValue method.
             */
            public boolean contains(Object o) {
                if (!(o instanceof Map.Entry))
                    return false;
                return c.contains(new UnmodifiableEntry((Map.Entry) o));
            }

            /**
             * The next two methods are overridden to protect against
             * an unscrupulous List whose contains(Object o) method senses
             * when o is a Map.Entry, and calls o.setValue.
             */
            public boolean containsAll(Collection coll) {
                Iterator e = coll.iterator();
                while (e.hasNext())
                    if (!contains(e.next())) // Invokes safe contains() above
                        return false;
                return true;
            }
            public boolean equals(Object o) {
                if (o == this)
                    return true;

                if (!(o instanceof Set))
                    return false;
                Set s = (Set) o;
                if (s.size() != c.size())
                    return false;
                return containsAll(s); // Invokes safe containsAll() above
            }

            /**
             * This "wrapper class" serves two purposes: it prevents
             * the client from modifying the backing Map, by short-circuiting
             * the setValue method, and it protects the backing Map against
             * an ill-behaved Map.Entry that attempts to modify another
             * Map Entry when asked to perform an equality check.
             */
            private static class UnmodifiableEntry implements Map.Entry {
                private Map.Entry e;

                UnmodifiableEntry(Map.Entry e) {this.e = e;}

                public Object getKey()	  {return e.getKey();}
                public Object getValue()  {return e.getValue();}
                public Object setValue(Object value) {
                    throw new UnsupportedOperationException();
                }
                public int hashCode()	  {return e.hashCode();}
                public boolean equals(Object o) {
                    if (!(o instanceof Map.Entry))
                        return false;
                    Map.Entry t = (Map.Entry)o;
                    return eq(e.getKey(),   t.getKey()) &&
                           eq(e.getValue(), t.getValue());
                }
                public String toString()  {return e.toString();}
            }
        }
    }

    /**
     * Returns an unmodifiable view of the specified sorted map.  This method
     * allows modules to provide users with "read-only" access to internal
     * sorted maps.  Query operations on the returned sorted map "read through"
     * to the specified sorted map.  Attempts to modify the returned
     * sorted map, whether direct, via its collection views, or via its
     * <tt>subMap</tt>, <tt>headMap</tt>, or <tt>tailMap</tt> views, result in
     * an <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned sorted map will be serializable if the specified sorted map
     * is serializable.
     *
     * @param m the sorted map for which an unmodifiable view is to be
     *        returned.
     * @return an unmodifiable view of the specified sorted map.
     */
    public static SortedMap unmodifiableSortedMap(SortedMap m) {
        return new UnmodifiableSortedMap(m);
    }

    /**
     * @serial include
     */
    static class UnmodifiableSortedMap
          extends UnmodifiableMap
          implements SortedMap, Serializable {
        private static final long serialVersionUID = -8806743815996713206L;

        private final SortedMap sm;

        UnmodifiableSortedMap(SortedMap m) {super(m); sm = m;}

        public Comparator comparator() {return sm.comparator();}

        public SortedMap subMap(Object fromKey, Object toKey) {
            return new UnmodifiableSortedMap(sm.subMap(fromKey, toKey));
        }
        public SortedMap headMap(Object toKey) {
            return new UnmodifiableSortedMap(sm.headMap(toKey));
        }
        public SortedMap tailMap(Object fromKey) {
            return new UnmodifiableSortedMap(sm.tailMap(fromKey));
        }

        public Object firstKey()           {return sm.firstKey();}
        public Object lastKey()            {return sm.lastKey();}
    }


    // Synch Wrappers

    /**
     * Returns a synchronized (thread-safe) collection backed by the specified
     * collection.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing collection is accomplished
     * through the returned collection.<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * collection when iterating over it:
     * <pre>
     *  Collection c = Collections.synchronizedCollection(myCollection);
     *     ...
     *  synchronized(c) {
     *      Iterator i = c.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *         foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned collection does <i>not</i> pass the <tt>hashCode</tt>
     * and <tt>equals</tt> operations through to the backing collection, but
     * relies on <tt>Object</tt>'s equals and hashCode methods.  This is
     * necessary to preserve the contracts of these operations in the case
     * that the backing collection is a set or a list.<p>
     *
     * The returned collection will be serializable if the specified collection
     * is serializable.
     *
     * @param  c the collection to be "wrapped" in a synchronized collection.
     * @return a synchronized view of the specified collection.
     */
    public static Collection synchronizedCollection(Collection c) {
        return new SynchronizedCollection(c);
    }

    static Collection synchronizedCollection(Collection c, Object mutex) {
        return new SynchronizedCollection(c, mutex);
    }

    /**
     * @serial include
     */
    static class SynchronizedCollection implements Collection, Serializable {
        // use serialVersionUID from JDK 1.2.2 for interoperability
        private static final long serialVersionUID = 3053995032091335093L;

        final Collection c;	   // Backing Collection
        final Object	   mutex;  // Object on which to synchronize

        SynchronizedCollection(Collection c) {
            if (c==null)
                throw new NullPointerException();
            this.c = c;
            mutex = this;
        }
        SynchronizedCollection(Collection c, Object mutex) {
            this.c = c;
            this.mutex = mutex;
        }

        public int size() {
            synchronized(mutex) {return c.size();}
        }
        public boolean isEmpty() {
            synchronized(mutex) {return c.isEmpty();}
        }
        public boolean contains(Object o) {
            synchronized(mutex) {return c.contains(o);}
        }
        public Object[] toArray() {
            synchronized(mutex) {return c.toArray();}
        }
        public Object[] toArray(Object[] a) {
            synchronized(mutex) {return c.toArray(a);}
        }

        public Iterator iterator() {
            return c.iterator(); // Must be manually synched by user!
        }

        public boolean add(Object e) {
            synchronized(mutex) {return c.add(e);}
        }
        public boolean remove(Object o) {
            synchronized(mutex) {return c.remove(o);}
        }

        public boolean containsAll(Collection coll) {
            synchronized(mutex) {return c.containsAll(coll);}
        }
        public boolean addAll(Collection coll) {
            synchronized(mutex) {return c.addAll(coll);}
        }
        public boolean removeAll(Collection coll) {
            synchronized(mutex) {return c.removeAll(coll);}
        }
        public boolean retainAll(Collection coll) {
            synchronized(mutex) {return c.retainAll(coll);}
        }
        public void clear() {
            synchronized(mutex) {c.clear();}
        }
        public String toString() {
            synchronized(mutex) {return c.toString();}
        }
        private void writeObject(ObjectOutputStream s) throws IOException {
            synchronized(mutex) {s.defaultWriteObject();}
        }
    }

    /**
     * Returns a synchronized (thread-safe) set backed by the specified
     * set.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing set is accomplished
     * through the returned set.<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * set when iterating over it:
     * <pre>
     *  Set s = Collections.synchronizedSet(new HashSet());
     *      ...
     *  synchronized(s) {
     *      Iterator i = s.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned set will be serializable if the specified set is
     * serializable.
     *
     * @param  s the set to be "wrapped" in a synchronized set.
     * @return a synchronized view of the specified set.
     */
    public static Set synchronizedSet(Set s) {
        return new SynchronizedSet(s);
    }

    static Set synchronizedSet(Set s, Object mutex) {
        return new SynchronizedSet(s, mutex);
    }

    /**
     * @serial include
     */
    static class SynchronizedSet
          extends SynchronizedCollection
          implements Set {
        private static final long serialVersionUID = 487447009682186044L;

        SynchronizedSet(Set s) {
            super(s);
        }
        SynchronizedSet(Set s, Object mutex) {
            super(s, mutex);
        }

        public boolean equals(Object o) {
            synchronized(mutex) {return c.equals(o);}
        }
        public int hashCode() {
            synchronized(mutex) {return c.hashCode();}
        }
    }

    /**
     * Returns a synchronized (thread-safe) sorted set backed by the specified
     * sorted set.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing sorted set is accomplished
     * through the returned sorted set (or its views).<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * sorted set when iterating over it or any of its <tt>subSet</tt>,
     * <tt>headSet</tt>, or <tt>tailSet</tt> views.
     * <pre>
     *  SortedSet s = Collections.synchronizedSortedSet(new TreeSet());
     *      ...
     *  synchronized(s) {
     *      Iterator i = s.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * or:
     * <pre>
     *  SortedSet s = Collections.synchronizedSortedSet(new TreeSet());
     *  SortedSet s2 = s.headSet(foo);
     *      ...
     *  synchronized(s) {  // Note: s, not s2!!!
     *      Iterator i = s2.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned sorted set will be serializable if the specified
     * sorted set is serializable.
     *
     * @param  s the sorted set to be "wrapped" in a synchronized sorted set.
     * @return a synchronized view of the specified sorted set.
     */
    public static SortedSet synchronizedSortedSet(SortedSet s) {
        return new SynchronizedSortedSet(s);
    }

    /**
     * @serial include
     */
    static class SynchronizedSortedSet
        extends SynchronizedSet
        implements SortedSet
    {
        private static final long serialVersionUID = 8695801310862127406L;

        final private SortedSet ss;

        SynchronizedSortedSet(SortedSet s) {
            super(s);
            ss = s;
        }
        SynchronizedSortedSet(SortedSet s, Object mutex) {
            super(s, mutex);
            ss = s;
        }

        public Comparator comparator() {
            synchronized(mutex) {return ss.comparator();}
        }

        public SortedSet subSet(Object fromElement, Object toElement) {
            synchronized(mutex) {
                return new SynchronizedSortedSet(
                    ss.subSet(fromElement, toElement), mutex);
            }
        }
        public SortedSet headSet(Object toElement) {
            synchronized(mutex) {
                return new SynchronizedSortedSet(ss.headSet(toElement), mutex);
            }
        }
        public SortedSet tailSet(Object fromElement) {
            synchronized(mutex) {
               return new SynchronizedSortedSet(ss.tailSet(fromElement),mutex);
            }
        }

        public Object first() {
            synchronized(mutex) {return ss.first();}
        }
        public Object last() {
            synchronized(mutex) {return ss.last();}
        }
    }

    /**
     * Returns a synchronized (thread-safe) list backed by the specified
     * list.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing list is accomplished
     * through the returned list.<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * list when iterating over it:
     * <pre>
     *  List list = Collections.synchronizedList(new ArrayList());
     *      ...
     *  synchronized(list) {
     *      Iterator i = list.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned list will be serializable if the specified list is
     * serializable.
     *
     * @param  list the list to be "wrapped" in a synchronized list.
     * @return a synchronized view of the specified list.
     */
    public static List synchronizedList(List list) {
        return (list instanceof RandomAccess ?
                new SynchronizedRandomAccessList(list) :
                new SynchronizedList(list));
    }

    static List synchronizedList(List list, Object mutex) {
        return (list instanceof RandomAccess ?
                new SynchronizedRandomAccessList(list, mutex) :
                new SynchronizedList(list, mutex));
    }

    /**
     * @serial include
     */
    static class SynchronizedList
        extends SynchronizedCollection
        implements List {
        static final long serialVersionUID = -7754090372962971524L;

        final List list;

        SynchronizedList(List list) {
            super(list);
            this.list = list;
        }
        SynchronizedList(List list, Object mutex) {
            super(list, mutex);
            this.list = list;
        }

        public boolean equals(Object o) {
            synchronized(mutex) {return list.equals(o);}
        }
        public int hashCode() {
            synchronized(mutex) {return list.hashCode();}
        }

        public Object get(int index) {
            synchronized(mutex) {return list.get(index);}
        }
        public Object set(int index, Object element) {
            synchronized(mutex) {return list.set(index, element);}
        }
        public void add(int index, Object element) {
            synchronized(mutex) {list.add(index, element);}
        }
        public Object remove(int index) {
            synchronized(mutex) {return list.remove(index);}
        }

        public int indexOf(Object o) {
            synchronized(mutex) {return list.indexOf(o);}
        }
        public int lastIndexOf(Object o) {
            synchronized(mutex) {return list.lastIndexOf(o);}
        }

        public boolean addAll(int index, Collection c) {
            synchronized(mutex) {return list.addAll(index, c);}
        }

        public ListIterator listIterator() {
            return list.listIterator(); // Must be manually synched by user
        }

        public ListIterator listIterator(int index) {
            return list.listIterator(index); // Must be manually synched by user
        }

        public List subList(int fromIndex, int toIndex) {
            synchronized(mutex) {
                return new SynchronizedList(list.subList(fromIndex, toIndex),
                                            mutex);
            }
        }

        /**
         * SynchronizedRandomAccessList instances are serialized as
         * SynchronizedList instances to allow them to be deserialized
         * in pre-1.4 JREs (which do not have SynchronizedRandomAccessList).
         * This method inverts the transformation.  As a beneficial
         * side-effect, it also grafts the RandomAccess marker onto
         * SynchronizedList instances that were serialized in pre-1.4 JREs.
         *
         * Note: Unfortunately, SynchronizedRandomAccessList instances
         * serialized in 1.4.1 and deserialized in 1.4 will become
         * SynchronizedList instances, as this method was missing in 1.4.
         */
        private Object readResolve() {
            return (list instanceof RandomAccess
                    ? new SynchronizedRandomAccessList(list)
                    : this);
        }
    }

    /**
     * @serial include
     */
    static class SynchronizedRandomAccessList
        extends SynchronizedList
        implements RandomAccess {

        SynchronizedRandomAccessList(List list) {
            super(list);
        }

        SynchronizedRandomAccessList(List list, Object mutex) {
            super(list, mutex);
        }

        public List subList(int fromIndex, int toIndex) {
            synchronized(mutex) {
                return new SynchronizedRandomAccessList(
                    list.subList(fromIndex, toIndex), mutex);
            }
        }

        static final long serialVersionUID = 1530674583602358482L;

        /**
         * Allows instances to be deserialized in pre-1.4 JREs (which do
         * not have SynchronizedRandomAccessList).  SynchronizedList has
         * a readResolve method that inverts this transformation upon
         * deserialization.
         */
        private Object writeReplace() {
            return new SynchronizedList(list);
        }
    }

    /**
     * Returns a synchronized (thread-safe) map backed by the specified
     * map.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing map is accomplished
     * through the returned map.<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * map when iterating over any of its collection views:
     * <pre>
     *  Map m = Collections.synchronizedMap(new HashMap());
     *      ...
     *  Set s = m.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized(m) {  // Synchronizing on m, not s!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned map will be serializable if the specified map is
     * serializable.
     *
     * @param  m the map to be "wrapped" in a synchronized map.
     * @return a synchronized view of the specified map.
     */
    public static Map synchronizedMap(Map m) {
        return new SynchronizedMap(m);
    }

    /**
     * @serial include
     */
    private static class SynchronizedMap
        implements Map, Serializable {
        // use serialVersionUID from JDK 1.2.2 for interoperability
        private static final long serialVersionUID = 1978198479659022715L;

        private final Map m;     // Backing Map
        final Object      mutex;	// Object on which to synchronize

        SynchronizedMap(Map m) {
            if (m==null)
                throw new NullPointerException();
            this.m = m;
            mutex = this;
        }

        SynchronizedMap(Map m, Object mutex) {
            this.m = m;
            this.mutex = mutex;
        }

        public int size() {
            synchronized(mutex) {return m.size();}
        }
        public boolean isEmpty(){
            synchronized(mutex) {return m.isEmpty();}
        }
        public boolean containsKey(Object key) {
            synchronized(mutex) {return m.containsKey(key);}
        }
        public boolean containsValue(Object value){
            synchronized(mutex) {return m.containsValue(value);}
        }
        public Object get(Object key) {
            synchronized(mutex) {return m.get(key);}
        }

        public Object put(Object key, Object value) {
            synchronized(mutex) {return m.put(key, value);}
        }
        public Object remove(Object key) {
            synchronized(mutex) {return m.remove(key);}
        }
        public void putAll(Map map) {
            synchronized(mutex) {m.putAll(map);}
        }
        public void clear() {
            synchronized(mutex) {m.clear();}
        }

        private transient Set keySet = null;
        private transient Set entrySet = null;
        private transient Collection values = null;

        public Set keySet() {
            synchronized(mutex) {
                if (keySet==null)
                    keySet = new SynchronizedSet(m.keySet(), mutex);
                return keySet;
            }
        }

        public Set entrySet() {
            synchronized(mutex) {
                if (entrySet==null)
                    entrySet = new SynchronizedSet(m.entrySet(), mutex);
                return entrySet;
            }
        }

        public Collection values() {
            synchronized(mutex) {
                if (values==null)
                    values = new SynchronizedCollection(m.values(), mutex);
                return values;
            }
        }

        public boolean equals(Object o) {
            synchronized(mutex) {return m.equals(o);}
        }
        public int hashCode() {
            synchronized(mutex) {return m.hashCode();}
        }
        public String toString() {
            synchronized(mutex) {return m.toString();}
        }
        private void writeObject(ObjectOutputStream s) throws IOException {
            synchronized(mutex) {s.defaultWriteObject();}
        }
    }

    /**
     * Returns a synchronized (thread-safe) sorted map backed by the specified
     * sorted map.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing sorted map is accomplished
     * through the returned sorted map (or its views).<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * sorted map when iterating over any of its collection views, or the
     * collections views of any of its <tt>subMap</tt>, <tt>headMap</tt> or
     * <tt>tailMap</tt> views.
     * <pre>
     *  SortedMap m = Collections.synchronizedSortedMap(new TreeMap());
     *      ...
     *  Set s = m.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized(m) {  // Synchronizing on m, not s!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * or:
     * <pre>
     *  SortedMap m = Collections.synchronizedSortedMap(new TreeMap());
     *  SortedMap m2 = m.subMap(foo, bar);
     *      ...
     *  Set s2 = m2.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized(m) {  // Synchronizing on m, not m2 or s2!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned sorted map will be serializable if the specified
     * sorted map is serializable.
     *
     * @param  m the sorted map to be "wrapped" in a synchronized sorted map.
     * @return a synchronized view of the specified sorted map.
     */
    public static SortedMap synchronizedSortedMap(SortedMap m) {
        return new SynchronizedSortedMap(m);
    }


    /**
     * @serial include
     */
    static class SynchronizedSortedMap
        extends SynchronizedMap
        implements SortedMap
    {
        private static final long serialVersionUID = -8798146769416483793L;

        private final SortedMap sm;

        SynchronizedSortedMap(SortedMap m) {
            super(m);
            sm = m;
        }
        SynchronizedSortedMap(SortedMap m, Object mutex) {
            super(m, mutex);
            sm = m;
        }

        public Comparator comparator() {
            synchronized(mutex) {return sm.comparator();}
        }

        public SortedMap subMap(Object fromKey, Object toKey) {
            synchronized(mutex) {
                return new SynchronizedSortedMap(
                    sm.subMap(fromKey, toKey), mutex);
            }
        }
        public SortedMap headMap(Object toKey) {
            synchronized(mutex) {
                return new SynchronizedSortedMap(sm.headMap(toKey), mutex);
            }
        }
        public SortedMap tailMap(Object fromKey) {
            synchronized(mutex) {
               return new SynchronizedSortedMap(sm.tailMap(fromKey),mutex);
            }
        }

        public Object firstKey() {
            synchronized(mutex) {return sm.firstKey();}
        }
        public Object lastKey() {
            synchronized(mutex) {return sm.lastKey();}
        }
    }

    // Dynamically typesafe collection wrappers

    /**
     * Returns a dynamically typesafe view of the specified collection.  Any
     * attempt to insert an element of the wrong type will result in an
     * immediate <tt>ClassCastException</tt>.  Assuming a collection contains
     * no incorrectly typed elements prior to the time a dynamically typesafe
     * view is generated, and that all subsequent access to the collection
     * takes place through the view, it is <i>guaranteed</i> that the
     * collection cannot contain an incorrectly typed element.
     *
     * <p>The generics mechanism in the language provides compile-time
     * (static) type checking, but it is possible to defeat this mechanism
     * with unchecked casts.  Usually this is not a problem, as the compiler
     * issues warnings on all such unchecked operations.  There are, however,
     * times when static type checking alone is not sufficient.  For example,
     * suppose a collection is passed to a third-party library and it is
     * imperative that the library code not corrupt the collection by
     * inserting an element of the wrong type.
     *
     * <p>Another use of dynamically typesafe views is debugging.  Suppose a
     * program fails with a <tt>ClassCastException</tt>, indicating that an
     * incorrectly typed element was put into a parameterized collection.
     * Unfortunately, the exception can occur at any time after the erroneous
     * element is inserted, so it typically provides little or no information
     * as to the real source of the problem.  If the problem is reproducible,
     * one can quickly determine its source by temporarily modifying the
     * program to wrap the collection with a dynamically typesafe view.
     * For example, this declaration:
     * <pre>
     *     Collection&lt;String&gt; c = new HashSet&lt;String&gt;();
     * </pre>
     * may be replaced temporarily by this one:
     * <pre>
     *     Collection&lt;String&gt; c = Collections.checkedCollection(
     *         new HashSet&lt;String&gt;(), String.class);
     * </pre>
     * Running the program again will cause it to fail at the point where
     * an incorrectly typed element is inserted into the collection, clearly
     * identifying the source of the problem.  Once the problem is fixed, the
     * modified declaration may be reverted back to the original.
     *
     * <p>The returned collection does <i>not</i> pass the hashCode and equals
     * operations through to the backing collection, but relies on
     * <tt>Object</tt>'s <tt>equals</tt> and <tt>hashCode</tt> methods.  This
     * is necessary to preserve the contracts of these operations in the case
     * that the backing collection is a set or a list.
     *
     * <p>The returned collection will be serializable if the specified
     * collection is serializable.
     *
     * @param c the collection for which a dynamically typesafe view is to be
     *             returned
     * @param type the type of element that <tt>c</tt> is permitted to hold
     * @return a dynamically typesafe view of the specified collection
     * @since 1.5
     */
    public static Collection checkedCollection(Collection c,
                                                      Class type) {
        return new CheckedCollection(c, type);
    }

    /**
     * @serial include
     */
    static class CheckedCollection implements Collection, Serializable {
        private static final long serialVersionUID = 1578914078182001775L;

        final Collection c;
        final Class type;

        void typeCheck(Object o) {
            if (!type.isInstance(o))
                throw new ClassCastException("Attempt to insert " +
                   o.getClass() + " element into collection with element type "
                   + type);
        }

        CheckedCollection(Collection c, Class type) {
            if (c==null || type == null)
                throw new NullPointerException();
            this.c = c;
            this.type = type;
        }

        public int size()                   { return c.size(); }
        public boolean isEmpty()            { return c.isEmpty(); }
        public boolean contains(Object o)   { return c.contains(o); }
        public Object[] toArray()           { return c.toArray(); }
        public Object[] toArray(Object[] a)       { return c.toArray(a); }
        public String toString()            { return c.toString(); }
        public Iterator iterator()       { return c.iterator(); }
        public boolean remove(Object o)     { return c.remove(o); }
        public boolean containsAll(Collection coll) {
            return c.containsAll(coll);
        }
        public boolean removeAll(Collection coll) {
            return c.removeAll(coll);
        }
        public boolean retainAll(Collection coll) {
            return c.retainAll(coll);
        }
        public void clear() {
            c.clear();
        }

        public boolean add(Object e){
            typeCheck(e);
            return c.add(e);
        }

        public boolean addAll(Collection coll) {
            /*
             * Dump coll into an array of the required type.  This serves
             * three purposes: it insulates us from concurrent changes in
             * the contents of coll, it type-checks all of the elements in
             * coll, and it provides all-or-nothing semantics (which we
             * wouldn't get if we type-checked each element as we added it).
             */
            Object[] a = null;
            try {
                a = coll.toArray(zeroLengthElementArray());
            } catch (ArrayStoreException e) {
                throw new ClassCastException();
            }

            boolean result = false;
            for (int i=0; i<a.length; i++) {
                Object e = a[i];
                result |= c.add(e);
            }
            return result;
        }

        private Object[] zeroLengthElementArray = null; // Lazily initialized

        /*
         * We don't need locking or volatile, because it's OK if we create
         * several zeroLengthElementArrays, and they're immutable.
         */
        Object[] zeroLengthElementArray() {
            if (zeroLengthElementArray == null)
                zeroLengthElementArray = (Object[]) Array.newInstance(type, 0);
            return zeroLengthElementArray;
        }
    }

    /**
     * Returns a dynamically typesafe view of the specified set.
     * Any attempt to insert an element of the wrong type will result in
     * an immediate <tt>ClassCastException</tt>.  Assuming a set contains
     * no incorrectly typed elements prior to the time a dynamically typesafe
     * view is generated, and that all subsequent access to the set
     * takes place through the view, it is <i>guaranteed</i> that the
     * set cannot contain an incorrectly typed element.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection checkedCollection}
     * method.
     *
     * <p>The returned set will be serializable if the specified set is
     * serializable.
     *
     * @param s the set for which a dynamically typesafe view is to be
     *             returned
     * @param type the type of element that <tt>s</tt> is permitted to hold
     * @return a dynamically typesafe view of the specified set
     * @since 1.5
     */
    public static Set checkedSet(Set s, Class type) {
        return new CheckedSet(s, type);
    }

    /**
     * @serial include
     */
    static class CheckedSet extends CheckedCollection
                                 implements Set, Serializable
    {
        private static final long serialVersionUID = 4694047833775013803L;

        CheckedSet(Set s, Class elementType) { super(s, elementType); }

        public boolean equals(Object o) { return c.equals(o); }
        public int hashCode()           { return c.hashCode(); }
    }

    /**
     * Returns a dynamically typesafe view of the specified sorted set.  Any
     * attempt to insert an element of the wrong type will result in an
     * immediate <tt>ClassCastException</tt>.  Assuming a sorted set contains
     * no incorrectly typed elements prior to the time a dynamically typesafe
     * view is generated, and that all subsequent access to the sorted set
     * takes place through the view, it is <i>guaranteed</i> that the sorted
     * set cannot contain an incorrectly typed element.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection checkedCollection}
     * method.
     *
     * <p>The returned sorted set will be serializable if the specified sorted
     * set is serializable.
     *
     * @param s the sorted set for which a dynamically typesafe view is to be
     *             returned
     * @param type the type of element that <tt>s</tt> is permitted to hold
     * @return a dynamically typesafe view of the specified sorted set
     * @since 1.5
     */
    public static SortedSet checkedSortedSet(SortedSet s,
                                                    Class type) {
        return new CheckedSortedSet(s, type);
    }

    /**
     * @serial include
     */
    static class CheckedSortedSet extends CheckedSet
        implements SortedSet, Serializable
    {
        private static final long serialVersionUID = 1599911165492914959L;
        private final SortedSet ss;

        CheckedSortedSet(SortedSet s, Class type) {
            super(s, type);
            ss = s;
        }

        public Comparator comparator() { return ss.comparator(); }
        public Object first()                   { return ss.first(); }
        public Object last()                    { return ss.last(); }

        public SortedSet subSet(Object fromElement, Object toElement) {
            return new CheckedSortedSet(ss.subSet(fromElement,toElement),
                                           type);
        }
        public SortedSet headSet(Object toElement) {
            return new CheckedSortedSet(ss.headSet(toElement), type);
        }
        public SortedSet tailSet(Object fromElement) {
            return new CheckedSortedSet(ss.tailSet(fromElement), type);
        }
    }

    /**
     * Returns a dynamically typesafe view of the specified list.
     * Any attempt to insert an element of the wrong type will result in
     * an immediate <tt>ClassCastException</tt>.  Assuming a list contains
     * no incorrectly typed elements prior to the time a dynamically typesafe
     * view is generated, and that all subsequent access to the list
     * takes place through the view, it is <i>guaranteed</i> that the
     * list cannot contain an incorrectly typed element.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection checkedCollection}
     * method.
     *
     * <p>The returned list will be serializable if the specified list is
     * serializable.
     *
     * @param list the list for which a dynamically typesafe view is to be
     *             returned
     * @param type the type of element that <tt>list</tt> is permitted to hold
     * @return a dynamically typesafe view of the specified list
     * @since 1.5
     */
    public static List checkedList(List list, Class type) {
        return (list instanceof RandomAccess ?
                new CheckedRandomAccessList(list, type) :
                new CheckedList(list, type));
    }

    /**
     * @serial include
     */
    static class CheckedList extends CheckedCollection
                                implements List
    {
        static final long serialVersionUID = 65247728283967356L;
        final List list;

        CheckedList(List list, Class type) {
            super(list, type);
            this.list = list;
        }

        public boolean equals(Object o)  { return list.equals(o); }
        public int hashCode()            { return list.hashCode(); }
        public Object get(int index)          { return list.get(index); }
        public Object remove(int index)       { return list.remove(index); }
        public int indexOf(Object o)     { return list.indexOf(o); }
        public int lastIndexOf(Object o) { return list.lastIndexOf(o); }

        public Object set(int index, Object element) {
            typeCheck(element);
            return list.set(index, element);
        }

        public void add(int index, Object element) {
            typeCheck(element);
            list.add(index, element);
        }

        public boolean addAll(int index, Collection c) {
            // See CheckCollection.addAll, above, for an explanation
            Object[] a = null;
            try {
                a = c.toArray(zeroLengthElementArray());
            } catch (ArrayStoreException e) {
                throw new ClassCastException();
            }

            return list.addAll(index, Arrays.asList(a));
        }
        public ListIterator listIterator()   { return listIterator(0); }

        public ListIterator listIterator(final int index) {
            return new ListIterator() {
                ListIterator i = list.listIterator(index);

                public boolean hasNext()     { return i.hasNext(); }
                public Object next()              { return i.next(); }
                public boolean hasPrevious() { return i.hasPrevious(); }
                public Object previous()          { return i.previous(); }
                public int nextIndex()       { return i.nextIndex(); }
                public int previousIndex()   { return i.previousIndex(); }
                public void remove()         { i.remove(); }

                public void set(Object e) {
                    typeCheck(e);
                    i.set(e);
                }

                public void add(Object e) {
                    typeCheck(e);
                    i.add(e);
                }
            };
        }

        public List subList(int fromIndex, int toIndex) {
            return new CheckedList(list.subList(fromIndex, toIndex), type);
        }
    }

    /**
     * @serial include
     */
    static class CheckedRandomAccessList extends CheckedList
                                            implements RandomAccess
    {
        private static final long serialVersionUID = 1638200125423088369L;

        CheckedRandomAccessList(List list, Class type) {
            super(list, type);
        }

        public List subList(int fromIndex, int toIndex) {
            return new CheckedRandomAccessList(
                list.subList(fromIndex, toIndex), type);
        }
    }

    /**
     * Returns a dynamically typesafe view of the specified map.  Any attempt
     * to insert a mapping whose key or value have the wrong type will result
     * in an immediate <tt>ClassCastException</tt>.  Similarly, any attempt to
     * modify the value currently associated with a key will result in an
     * immediate <tt>ClassCastException</tt>, whether the modification is
     * attempted directly through the map itself, or through a {@link
     * java.util.Map.Entry} instance obtained from the map's {@link Map#entrySet()
     * entry set} view.
     *
     * <p>Assuming a map contains no incorrectly typed keys or values
     * prior to the time a dynamically typesafe view is generated, and
     * that all subsequent access to the map takes place through the view
     * (or one of its collection views), it is <i>guaranteed</i> that the
     * map cannot contain an incorrectly typed key or value.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection checkedCollection}
     * method.
     *
     * <p>The returned map will be serializable if the specified map is
     * serializable.
     *
     * @param m the map for which a dynamically typesafe view is to be
     *             returned
     * @param keyType the type of key that <tt>m</tt> is permitted to hold
     * @param valueType the type of value that <tt>m</tt> is permitted to hold
     * @return a dynamically typesafe view of the specified map
     * @since 1.5
     */
    public static Map checkedMap(Map m, Class keyType,
                                              Class valueType) {
        return new CheckedMap(m, keyType, valueType);
    }


    /**
     * @serial include
     */
    private static class CheckedMap implements Map,
                                                         Serializable
    {
        private static final long serialVersionUID = 5742860141034234728L;

        private final Map m;
        final Class keyType;
        final Class valueType;

        private void typeCheck(Object key, Object value) {
            if (!keyType.isInstance(key))
                throw new ClassCastException("Attempt to insert " +
                    key.getClass() + " key into collection with key type "
                    + keyType);

            if (!valueType.isInstance(value))
                throw new ClassCastException("Attempt to insert " +
                    value.getClass() +" value into collection with value type "
                    + valueType);
        }

        CheckedMap(Map m, Class keyType, Class valueType) {
            if (m == null || keyType == null || valueType == null)
                throw new NullPointerException();
            this.m = m;
            this.keyType = keyType;
            this.valueType = valueType;
        }

        public int size()                      { return m.size(); }
        public boolean isEmpty()               { return m.isEmpty(); }
        public boolean containsKey(Object key) { return m.containsKey(key); }
        public boolean containsValue(Object v) { return m.containsValue(v); }
        public Object get(Object key)               { return m.get(key); }
        public Object remove(Object key)            { return m.remove(key); }
        public void clear()                    { m.clear(); }
        public Set keySet()                 { return m.keySet(); }
        public Collection values()          { return m.values(); }
        public boolean equals(Object o)        { return m.equals(o);  }
        public int hashCode()                  { return m.hashCode(); }
        public String toString()               { return m.toString(); }

        public Object put(Object key, Object value) {
            typeCheck(key, value);
            return m.put(key, value);
        }

        public void putAll(Map t) {
            // See CheckCollection.addAll, above, for an explanation
            Object[] keys = null;
            try {
                keys = t.keySet().toArray(zeroLengthKeyArray());
            } catch (ArrayStoreException e) {
                throw new ClassCastException();
            }
            Object[] values = null;
            try {
                values = t.values().toArray(zeroLengthValueArray());
            } catch (ArrayStoreException e) {
                throw new ClassCastException();
            }

            if (keys.length != values.length)
                throw new ConcurrentModificationException();

            for (int i = 0; i < keys.length; i++)
                m.put(keys[i], values[i]);
        }

        // Lazily initialized
        private Object[] zeroLengthKeyArray   = null;
        private Object[] zeroLengthValueArray = null;

        /*
         * We don't need locking or volatile, because it's OK if we create
         * several zeroLengthValueArrays, and they're immutable.
         */
        private Object[] zeroLengthKeyArray() {
            if (zeroLengthKeyArray == null)
                zeroLengthKeyArray = (Object[]) Array.newInstance(keyType, 0);
            return zeroLengthKeyArray;
        }
        private Object[] zeroLengthValueArray() {
            if (zeroLengthValueArray == null)
                zeroLengthValueArray = (Object[]) Array.newInstance(valueType, 0);
            return zeroLengthValueArray;
        }

        private transient Set entrySet = null;

        public Set entrySet() {
            if (entrySet==null)
                entrySet = new CheckedEntrySet(m.entrySet(), valueType);
            return entrySet;
        }

        /**
         * We need this class in addition to CheckedSet as Map.Entry permits
         * modification of the backing Map via the setValue operation.  This
         * class is subtle: there are many possible attacks that must be
         * thwarted.
         *
         * @serial exclude
         */
        static class CheckedEntrySet implements Set {
            Set s;
            Class valueType;

            CheckedEntrySet(Set s, Class valueType) {
                this.s = s;
                this.valueType = valueType;
            }

            public int size()                   { return s.size(); }
            public boolean isEmpty()            { return s.isEmpty(); }
            public String toString()            { return s.toString(); }
            public int hashCode()               { return s.hashCode(); }
            public boolean remove(Object o)     { return s.remove(o); }
            public boolean removeAll(Collection coll) {
                return s.removeAll(coll);
            }
            public boolean retainAll(Collection coll) {
                return s.retainAll(coll);
            }
            public void clear() {
                s.clear();
            }

            public boolean add(Object e){
                throw new UnsupportedOperationException();
            }
            public boolean addAll(Collection coll) {
                throw new UnsupportedOperationException();
            }


            public Iterator iterator() {
                return new Iterator() {
                    Iterator i = s.iterator();

                    public boolean hasNext() { return i.hasNext(); }
                    public void remove()     { i.remove(); }

                    public Object next() {
                        return new CheckedEntry((Map.Entry)i.next(), valueType);
                    }
                };
            }

            public Object[] toArray() {
                Object[] source = s.toArray();

                /*
                 * Ensure that we don't get an ArrayStoreException even if
                 * s.toArray returns an array of something other than Object
                 */
                Object[] dest = (CheckedEntry.class.isInstance(
                    source.getClass().getComponentType()) ? source :
                                 new Object[source.length]);

                for (int i = 0; i < source.length; i++)
                    dest[i] = new CheckedEntry((Map.Entry)source[i],
                                                    valueType);
                return dest;
            }

            public Object[] toArray(Object[] a) {
                // We don't pass a to s.toArray, to avoid window of
                // vulnerability wherein an unscrupulous multithreaded client
                // could get his hands on raw (unwrapped) Entries from s.
                Object[] arr = s.toArray(a.length==0 ? a :
                   (Object[])Array.newInstance(a.getClass().getComponentType(), 0));

                for (int i=0; i<arr.length; i++)
                    arr[i] = new CheckedEntry((Map.Entry)arr[i],
                                                   valueType);
                if (arr.length > a.length)
                    return (Object[])arr;

                System.arraycopy(arr, 0, a, 0, arr.length);
                if (a.length > arr.length)
                    a[arr.length] = null;
                return a;
            }

            /**
             * This method is overridden to protect the backing set against
             * an object with a nefarious equals function that senses
             * that the equality-candidate is Map.Entry and calls its
             * setValue method.
             */
            public boolean contains(Object o) {
                if (!(o instanceof Map.Entry))
                    return false;
                return s.contains(
                    new CheckedEntry((Map.Entry) o, valueType));
            }

            /**
             * The next two methods are overridden to protect against
             * an unscrupulous collection whose contains(Object o) method
             * senses when o is a Map.Entry, and calls o.setValue.
             */
            public boolean containsAll(Collection coll) {
                Iterator e = coll.iterator();
                while (e.hasNext())
                    if (!contains(e.next())) // Invokes safe contains() above
                        return false;
                return true;
            }

            public boolean equals(Object o) {
                if (o == this)
                    return true;
                if (!(o instanceof Set))
                    return false;
                Set that = (Set) o;
                if (that.size() != s.size())
                    return false;
                return containsAll(that); // Invokes safe containsAll() above
            }

            /**
             * This "wrapper class" serves two purposes: it prevents
             * the client from modifying the backing Map, by short-circuiting
             * the setValue method, and it protects the backing Map against
             * an ill-behaved Map.Entry that attempts to modify another
             * Map Entry when asked to perform an equality check.
             */
            private static class CheckedEntry implements Map.Entry {
                private Map.Entry e;
                private Class valueType;

                CheckedEntry(Map.Entry e, Class valueType) {
                    this.e = e;
                    this.valueType = valueType;
                }

                public Object getKey()        { return e.getKey(); }
                public Object getValue()      { return e.getValue(); }
                public int hashCode()    { return e.hashCode(); }
                public String toString() { return e.toString(); }


                public Object setValue(Object value) {
                    if (!valueType.isInstance(value))
                        throw new ClassCastException("Attempt to insert " +
                        value.getClass() +
                        " value into collection with value type " + valueType);
                    return e.setValue(value);
                }

                public boolean equals(Object o) {
                    if (!(o instanceof Map.Entry))
                        return false;
                    Map.Entry t = (Map.Entry)o;
                    return eq(e.getKey(),   t.getKey()) &&
                           eq(e.getValue(), t.getValue());
                }
            }
        }
    }

    /**
     * Returns a dynamically typesafe view of the specified sorted map.  Any
     * attempt to insert a mapping whose key or value have the wrong type will
     * result in an immediate <tt>ClassCastException</tt>.  Similarly, any
     * attempt to modify the value currently associated with a key will result
     * in an immediate <tt>ClassCastException</tt>, whether the modification
     * is attempted directly through the map itself, or through a {@link
     * java.util.Map.Entry} instance obtained from the map's {@link Map#entrySet() entry
     * set} view.
     *
     * <p>Assuming a map contains no incorrectly typed keys or values
     * prior to the time a dynamically typesafe view is generated, and
     * that all subsequent access to the map takes place through the view
     * (or one of its collection views), it is <i>guaranteed</i> that the
     * map cannot contain an incorrectly typed key or value.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection checkedCollection}
     * method.
     *
     * <p>The returned map will be serializable if the specified map is
     * serializable.
     *
     * @param m the map for which a dynamically typesafe view is to be
     *             returned
     * @param keyType the type of key that <tt>m</tt> is permitted to hold
     * @param valueType the type of value that <tt>m</tt> is permitted to hold
     * @return a dynamically typesafe view of the specified map
     * @since 1.5
     */
    public static SortedMap checkedSortedMap(SortedMap m,
                                                        Class keyType,
                                                        Class valueType) {
        return new CheckedSortedMap(m, keyType, valueType);
    }

    /**
     * @serial include
     */
    static class CheckedSortedMap extends CheckedMap
        implements SortedMap, Serializable
    {
        private static final long serialVersionUID = 1599671320688067438L;

        private final SortedMap sm;

        CheckedSortedMap(SortedMap m,
                         Class keyType, Class valueType) {
            super(m, keyType, valueType);
            sm = m;
        }

        public Comparator comparator() { return sm.comparator(); }
        public Object firstKey()                       { return sm.firstKey(); }
        public Object lastKey()                        { return sm.lastKey(); }

        public SortedMap subMap(Object fromKey, Object toKey) {
            return new CheckedSortedMap(sm.subMap(fromKey, toKey),
                                             keyType, valueType);
        }

        public SortedMap headMap(Object toKey) {
            return new CheckedSortedMap(sm.headMap(toKey),
                                             keyType, valueType);
        }

        public SortedMap tailMap(Object fromKey) {
            return new CheckedSortedMap(sm.tailMap(fromKey),
                                             keyType, valueType);
        }
    }

    // Miscellaneous

    /**
     * The empty set (immutable).  This set is serializable.
     *
     * @see #emptySet()
     */
    public static final Set EMPTY_SET = new EmptySet();

    /**
     * Returns the empty set (immutable).  This set is serializable.
     * Unlike the like-named field, this method is parameterized.
     *
     * <p>This example illustrates the type-safe way to obtain an empty set:
     * <pre>
     *     Set&lt;String&gt; s = Collections.emptySet();
     * </pre>
     * Implementation note:  Implementations of this method need not
     * create a separate <tt>Set</tt> object for each call.   Using this
     * method is likely to have comparable cost to using the like-named
     * field.  (Unlike this method, the field does not provide type safety.)
     *
     * @see #EMPTY_SET
     * @since 1.5
     */
    public static final Set emptySet() {
        return (Set) EMPTY_SET;
    }

    /**
     * @serial include
     */
    private static class EmptySet extends AbstractSet implements Serializable {
        // use serialVersionUID from JDK 1.2.2 for interoperability
        private static final long serialVersionUID = 1582296315990362920L;

        public Iterator iterator() {
            return new Iterator() {
                public boolean hasNext() {
                    return false;
                }
                public Object next() {
                    throw new NoSuchElementException();
                }
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        public int size() {return 0;}

        public boolean contains(Object obj) {return false;}

        // Preserves singleton property
        private Object readResolve() {
            return EMPTY_SET;
        }
    }

    /**
     * The empty list (immutable).  This list is serializable.
     *
     * @see #emptyList()
     */
    public static final List EMPTY_LIST = new EmptyList();

    /**
     * Returns the empty list (immutable).  This list is serializable.
     *
     * <p>This example illustrates the type-safe way to obtain an empty list:
     * <pre>
     *     List&lt;String&gt; s = Collections.emptyList();
     * </pre>
     * Implementation note:  Implementations of this method need not
     * create a separate <tt>List</tt> object for each call.   Using this
     * method is likely to have comparable cost to using the like-named
     * field.  (Unlike this method, the field does not provide type safety.)
     *
     * @see #EMPTY_LIST
     * @since 1.5
     */
    public static final List emptyList() {
        return (List) EMPTY_LIST;
    }

    /**
     * @serial include
     */
    private static class EmptyList
        extends AbstractList
        implements RandomAccess, Serializable {
        // use serialVersionUID from JDK 1.2.2 for interoperability
        private static final long serialVersionUID = 8842843931221139166L;

        public int size() {return 0;}

        public boolean contains(Object obj) {return false;}

        public Object get(int index) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }

        // Preserves singleton property
        private Object readResolve() {
            return EMPTY_LIST;
        }
    }

    /**
     * The empty map (immutable).  This map is serializable.
     *
     * @see #emptyMap()
     * @since 1.3
     */
    public static final Map EMPTY_MAP = new EmptyMap();

    /**
     * Returns the empty map (immutable).  This map is serializable.
     *
     * <p>This example illustrates the type-safe way to obtain an empty set:
     * <pre>
     *     Map&lt;String, Date&gt; s = Collections.emptyMap();
     * </pre>
     * Implementation note:  Implementations of this method need not
     * create a separate <tt>Map</tt> object for each call.   Using this
     * method is likely to have comparable cost to using the like-named
     * field.  (Unlike this method, the field does not provide type safety.)
     *
     * @see #EMPTY_MAP
     * @since 1.5
     */
    public static final Map emptyMap() {
        return (Map) EMPTY_MAP;
    }

    private static class EmptyMap
        extends AbstractMap
        implements Serializable {

        private static final long serialVersionUID = 6428348081105594320L;

        public int size()                          {return 0;}

        public boolean isEmpty()                   {return true;}

        public boolean containsKey(Object key)     {return false;}

        public boolean containsValue(Object value) {return false;}

        public Object get(Object key)              {return null;}

        public Set keySet()                {return Collections.emptySet();}

        public Collection values()         {return Collections.emptySet();}

        public Set entrySet() {
            return Collections.emptySet();
        }

        public boolean equals(Object o) {
            return (o instanceof Map) && ((Map)o).size()==0;
        }

        public int hashCode()                      {return 0;}

        // Preserves singleton property
        private Object readResolve() {
            return EMPTY_MAP;
        }
    }

    /**
     * Returns an immutable set containing only the specified object.
     * The returned set is serializable.
     *
     * @param o the sole object to be stored in the returned set.
     * @return an immutable set containing only the specified object.
     */
    public static Set singleton(Object o) {
        return new SingletonSet(o);
    }

    /**
     * @serial include
     */
    private static class SingletonSet
        extends AbstractSet
        implements Serializable
    {
        // use serialVersionUID from JDK 1.2.2 for interoperability
        private static final long serialVersionUID = 3193687207550431679L;

        final private Object element;

        SingletonSet(Object e) {element = e;}

        public Iterator iterator() {
            return new Iterator() {
                private boolean hasNext = true;
                public boolean hasNext() {
                    return hasNext;
                }
                public Object next() {
                    if (hasNext) {
                        hasNext = false;
                        return element;
                    }
                    throw new NoSuchElementException();
                }
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        public int size() {return 1;}

        public boolean contains(Object o) {return eq(o, element);}
    }

    /**
     * Returns an immutable list containing only the specified object.
     * The returned list is serializable.
     *
     * @param o the sole object to be stored in the returned list.
     * @return an immutable list containing only the specified object.
     * @since 1.3
     */
    public static List singletonList(Object o) {
        return new SingletonList(o);
    }

    private static class SingletonList
        extends AbstractList
        implements RandomAccess, Serializable {

        static final long serialVersionUID = 3093736618740652951L;

        private final Object element;

        SingletonList(Object obj)                {element = obj;}

        public int size()                   {return 1;}

        public boolean contains(Object obj) {return eq(obj, element);}

        public Object get(int index) {
            if (index != 0)
              throw new IndexOutOfBoundsException("Index: "+index+", Size: 1");
            return element;
        }
    }

    /**
     * Returns an immutable map, mapping only the specified key to the
     * specified value.  The returned map is serializable.
     *
     * @param key the sole key to be stored in the returned map.
     * @param value the value to which the returned map maps <tt>key</tt>.
     * @return an immutable map containing only the specified key-value
     *         mapping.
     * @since 1.3
     */
    public static Map singletonMap(Object key, Object value) {
        return new SingletonMap(key, value);
    }

    private static class SingletonMap
          extends AbstractMap
          implements Serializable {
        private static final long serialVersionUID = -6979724477215052911L;

        private final Object k;
        private final Object v;

        SingletonMap(Object key, Object value) {
            k = key;
            v = value;
        }

        public int size()                          {return 1;}

        public boolean isEmpty()                   {return false;}

        public boolean containsKey(Object key)     {return eq(key, k);}

        public boolean containsValue(Object value) {return eq(value, v);}

        public Object get(Object key)                   {return (eq(key, k) ? v : null);}

        private transient Set keySet = null;
        private transient Set entrySet = null;
        private transient Collection values = null;

        public Set keySet() {
            if (keySet==null)
                keySet = singleton(k);
            return keySet;
        }

        public Set entrySet() {
            if (entrySet==null)
                entrySet = Collections.singleton(
                    new SimpleImmutableEntry(k, v));
            return entrySet;
        }

        public Collection values() {
            if (values==null)
                values = singleton(v);
            return values;
        }

    }

    /**
     * Returns an immutable list consisting of <tt>n</tt> copies of the
     * specified object.  The newly allocated data object is tiny (it contains
     * a single reference to the data object).  This method is useful in
     * combination with the <tt>List.addAll</tt> method to grow lists.
     * The returned list is serializable.
     *
     * @param  n the number of elements in the returned list.
     * @param  o the element to appear repeatedly in the returned list.
     * @return an immutable list consisting of <tt>n</tt> copies of the
     * 	       specified object.
     * @throws IllegalArgumentException if n &lt; 0.
     * @see    List#addAll(Collection)
     * @see    List#addAll(int, Collection)
     */
    public static List nCopies(int n, Object o) {
        return new CopiesList(n, o);
    }

    /**
     * @serial include
     */
    private static class CopiesList
        extends AbstractList
        implements RandomAccess, Serializable
    {
        static final long serialVersionUID = 2739099268398711800L;

        int n;
        Object element;

        CopiesList(int n, Object e) {
            if (n < 0)
                throw new IllegalArgumentException("List length = " + n);
            this.n = n;
            element = e;
        }

        public int size() {
            return n;
        }

        public boolean contains(Object obj) {
            return n != 0 && eq(obj, element);
        }

        public Object get(int index) {
            if (index<0 || index>=n)
                throw new IndexOutOfBoundsException("Index: "+index+
                                                    ", Size: "+n);
            return element;
        }
    }

    /**
     * Returns a comparator that imposes the reverse of the <i>natural
     * ordering</i> on a collection of objects that implement the
     * <tt>Comparable</tt> interface.  (The natural ordering is the ordering
     * imposed by the objects' own <tt>compareTo</tt> method.)  This enables a
     * simple idiom for sorting (or maintaining) collections (or arrays) of
     * objects that implement the <tt>Comparable</tt> interface in
     * reverse-natural-order.  For example, suppose a is an array of
     * strings. Then: <pre>
     * 		Arrays.sort(a, Collections.reverseOrder());
     * </pre> sorts the array in reverse-lexicographic (alphabetical) order.<p>
     *
     * The returned comparator is serializable.
     *
     * @return a comparator that imposes the reverse of the <i>natural
     * 	       ordering</i> on a collection of objects that implement
     *	       theComparable</tt> interface.
     * @see Comparable
     */
    public static Comparator reverseOrder() {
        return (Comparator) REVERSE_ORDER;
    }

    private static final Comparator REVERSE_ORDER = new ReverseComparator();

    /**
     * @serial include
     */
    private static class ReverseComparator
        implements Comparator, Serializable {

        // use serialVersionUID from JDK 1.2.2 for interoperability
        private static final long serialVersionUID = 7207038068494060240L;

        public int compare(Object c1, Object c2) {
            return ((Comparable)c2).compareTo(c1);
        }
    }

    /**
     * Returns a comparator that imposes the reverse ordering of the specified
     * comparator.  If the specified comparator is null, this method is
     * equivalent to {@link #reverseOrder()} (in other words, it returns a
     * comparator that imposes the reverse of the <i>natural ordering</i> on a
     * collection of objects that implement the Comparable interface).
     *
     * <p>The returned comparator is serializable (assuming the specified
     * comparator is also serializable or null).
     *
     * @return a comparator that imposes the reverse ordering of the
     *     specified comparator.
     * @since 1.5
     */
    public static Comparator reverseOrder(Comparator cmp) {
        if (cmp == null)
            return new ReverseComparator();  // Unchecked warning!!

        return new ReverseComparator2(cmp);
    }

    /**
     * @serial include
     */
    private static class ReverseComparator2 implements Comparator,
        Serializable
    {
        private static final long serialVersionUID = 4374092139857L;

        /**
         * The comparator specified in the static factory.  This will never
         * be null, as the static factory returns a ReverseComparator
         * instance if its argument is null.
         *
         * @serial
         */
        private Comparator cmp;

        ReverseComparator2(Comparator cmp) {
            //assert cmp != null;
            this.cmp = cmp;
        }

        public int compare(Object t1, Object t2) {
            return cmp.compare(t2, t1);
        }
    }

    /**
     * Returns an enumeration over the specified collection.  This provides
     * interoperability with legacy APIs that require an enumeration
     * as input.
     *
     * @param c the collection for which an enumeration is to be returned.
     * @return an enumeration over the specified collection.
     * @see Enumeration
     */
    public static Enumeration enumeration(final Collection c) {
        return new Enumeration() {
            Iterator i = c.iterator();

            public boolean hasMoreElements() {
                return i.hasNext();
            }

            public Object nextElement() {
                return i.next();
            }
        };
    }

    /**
     * Returns an array list containing the elements returned by the
     * specified enumeration in the order they are returned by the
     * enumeration.  This method provides interoperability between
     * legacy APIs that return enumerations and new APIs that require
     * collections.
     *
     * @param e enumeration providing elements for the returned
     *          array list
     * @return an array list containing the elements returned
     *         by the specified enumeration.
     * @since 1.4
     * @see Enumeration
     * @see ArrayList
     */
    public static ArrayList list(Enumeration e) {
        ArrayList l = new ArrayList();
        while (e.hasMoreElements())
            l.add(e.nextElement());
        return l;
    }

    /**
     * Returns true if the specified arguments are equal, or both null.
     */
    private static boolean eq(Object o1, Object o2) {
        return (o1==null ? o2==null : o1.equals(o2));
    }

    /**
     * Returns the number of elements in the specified collection equal to the
     * specified object.  More formally, returns the number of elements
     * <tt>e</tt> in the collection such that
     * <tt>(o == null ? e == null : o.equals(e))</tt>.
     *
     * @param c the collection in which to determine the frequency
     *     of <tt>o</tt>
     * @param o the object whose frequency is to be determined
     * @throws NullPointerException if <tt>c</tt> is null
     * @since 1.5
     */
    public static int frequency(Collection c, Object o) {
        int result = 0;
        if (o == null) {
            for (Iterator itr = c.iterator(); itr.hasNext();) {
                Object e = itr.next();
                if (e == null)
                    result++;
            }
        } else {
            for (Iterator itr = c.iterator(); itr.hasNext();) {
                Object e = itr.next();
                if (o.equals(e))
                    result++;
            }
        }
        return result;
    }

    /**
     * Returns <tt>true</tt> if the two specified collections have no
     * elements in common.
     *
     * <p>Care must be exercised if this method is used on collections that
     * do not comply with the general contract for <tt>Collection</tt>.
     * Implementations may elect to iterate over either collection and test
     * for containment in the other collection (or to perform any equivalent
     * computation).  If either collection uses a nonstandard equality test
     * (as does a {@link SortedSet} whose ordering is not <i>compatible with
     * equals</i>, or the key set of an {@link IdentityHashMap}), both
     * collections must use the same nonstandard equality test, or the
     * result of this method is undefined.
     *
     * <p>Note that it is permissible to pass the same collection in both
     * parameters, in which case the method will return true if and only if
     * the collection is empty.
     *
     * @param c1 a collection
     * @param c2 a collection
     * @throws NullPointerException if either collection is null
     * @since 1.5
     */
    public static boolean disjoint(Collection c1, Collection c2) {
        /*
         * We're going to iterate through c1 and test for inclusion in c2.
         * If c1 is a Set and c2 isn't, swap the collections.  Otherwise,
         * place the shorter collection in c1.  Hopefully this heuristic
         * will minimize the cost of the operation.
         */
        if ((c1 instanceof Set) && !(c2 instanceof Set) ||
            (c1.size() > c2.size())) {
            Collection tmp = c1;
            c1 = c2;
            c2 = tmp;
        }

        for (Iterator itr = c1.iterator(); itr.hasNext();) {
            Object e = itr.next();
            if (c2.contains(e))
                return false;
        }
        return true;
    }

    /**
     * Adds all of the specified elements to the specified collection.
     * Elements to be added may be specified individually or as an array.
     * The behavior of this convenience method is identical to that of
     * <tt>c.addAll(Arrays.asList(elements))</tt>, but this method is likely
     * to run significantly faster under most implementations.
     *
     * <p>When elements are specified individually, this method provides a
     * convenient way to add a few elements to an existing collection:
     * <pre>
     *     Collections.addAll(flavors, "Peaches 'n Plutonium", "Rocky Racoon");
     * </pre>
     *
     * @param c the collection into which <tt>elements</tt> are to be inserted
     * @param a the elements to insert into <tt>c</tt>
     * @return <tt>true</tt> if the collection changed as a result of the call
     * @throws UnsupportedOperationException if <tt>c</tt> does not support
     *         the <tt>add</tt> operation.
     * @throws NullPointerException if <tt>elements</tt> contains one or more
     *         null values and <tt>c</tt> does not permit null elements, or
     *         if <tt>c</tt> or <tt>elements</tt> are <tt>null</tt>
     * @throws IllegalArgumentException if some property of a value in
     *         <tt>elements</tt> prevents it from being added to <tt>c</tt>
     * @see Collection#addAll(Collection)
     * @since 1.5
     */
    public static boolean addAll(Collection c, Object[] a) {
        boolean result = false;
        for (int i=0; i<a.length; i++) {
            Object e = a[i];
            result |= c.add(e);
        }
        return result;
    }

    /**
     * Returns a set backed by the specified map.  The resulting set displays
     * the same ordering, concurrency, and performance characteristics as the
     * backing map.  In essence, this factory method provides a {@link Set}
     * implementation corresponding to any {@link Map} implementation.  There
     * is no need to use this method on a {@link Map} implementation that
     * already has a corresponding {@link Set} implementation (such as {@link
     * HashMap} or {@link TreeMap}).
     *
     * <p>Each method invocation on the set returned by this method results in
     * exactly one method invocation on the backing map or its <tt>keySet</tt>
     * view, with one exception.  The <tt>addAll</tt> method is implemented
     * as a sequence of <tt>put</tt> invocations on the backing map.
     *
     * <p>The specified map must be empty at the time this method is invoked,
     * and should not be accessed directly after this method returns.  These
     * conditions are ensured if the map is created empty, passed directly
     * to this method, and no reference to the map is retained, as illustrated
     * in the following code fragment:
     * <pre>
     *    Set&lt;Object&gt; weakHashSet = Collections.asSet(
     *        new WeakHashMap&lt;Object, Boolean&gt;());
     * </pre>
     *
     * @param map the backing map
     * @return the set backed by the map
     * @throws IllegalArgumentException if <tt>map</tt> is not empty
     * @since 1.6
     */
    public static Set asSet(Map map) {
        return new MapAsSet(map);
    }

    private static class MapAsSet extends AbstractSet
        implements Set, Serializable
    {
        private final Map m;  // The backing map
        private transient Set keySet;  // Its keySet

        MapAsSet(Map map) {
            if (!map.isEmpty())
                throw new IllegalArgumentException("Map is non-empty");
            m = map;
            keySet = map.keySet();
        }

        public int size()                 { return m.size(); }
        public boolean isEmpty()          { return m.isEmpty(); }
        public boolean contains(Object o) { return m.containsKey(o); }
        public Iterator iterator()     { return keySet.iterator(); }
        public Object[] toArray()         { return keySet.toArray(); }
        public Object[] toArray(Object[] a)     { return keySet.toArray(a); }
        public boolean add(Object e) {
            return m.put(e, Boolean.TRUE) == null;
        }
        public boolean remove(Object o)   { return m.remove(o) != null; }

        public boolean removeAll(Collection c) {
            return keySet.removeAll(c);
        }
        public boolean retainAll(Collection c) {
            return keySet.retainAll(c);
        }
        public void clear()               { m.clear(); }
        public boolean equals(Object o)   { return keySet.equals(o); }
        public int hashCode()             { return keySet.hashCode(); }

        private static final long serialVersionUID = 2454657854757543876L;

        private void readObject(java.io.ObjectInputStream s)
            throws IOException, ClassNotFoundException
        {
            s.defaultReadObject();
            keySet = m.keySet();
        }
    }

    /**
     * Returns a view of a {@link Deque} as a Last-in-first-out (Lifo)
     * {@link Queue}. Method <tt>add</tt> is mapped to <tt>push</tt>,
     * <tt>remove</tt> is mapped to <tt>pop</tt> and so on. This
     * view can be useful when you would like to use a method
     * requiring a <tt>Queue</tt> but you need Lifo ordering.
     * @param deque the Deque
     * @return the queue
     * @since  1.6
     */
    public static Queue asLifoQueue(Deque deque) {
        return new AsLIFOQueue(deque);
    }

    static class AsLIFOQueue extends AbstractQueue
        implements Queue, Serializable {
        private static final long serialVersionUID = 1802017725587941708L;
        private final Deque q;
        AsLIFOQueue(Deque q)            { this.q = q; }
        public boolean offer(Object e)          { return q.offerFirst(e); }
        public Object poll()                    { return q.pollFirst(); }
        public Object remove()                  { return q.removeFirst(); }
        public Object peek()                    { return q.peekFirst(); }
        public Object element()                 { return q.getFirst(); }
        public int size()                  { return q.size(); }
        public boolean isEmpty()           { return q.isEmpty(); }
        public boolean contains(Object o)  { return q.contains(o); }
        public Iterator iterator()      { return q.iterator(); }
        public Object[] toArray()          { return q.toArray(); }
        public Object[] toArray(Object[] a)      { return q.toArray(a); }
        public boolean add(Object e)            { return q.offerFirst(e); }
        public boolean remove(Object o)    { return q.remove(o); }
        public void clear()                { q.clear(); }
    }
}
