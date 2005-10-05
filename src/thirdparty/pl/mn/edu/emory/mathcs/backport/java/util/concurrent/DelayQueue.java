/*
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/licenses/publicdomain
 */


package pl.mn.edu.emory.mathcs.backport.java.util.concurrent;

import java.util.Collection;
import java.util.Iterator;

import pl.mn.edu.emory.mathcs.backport.java.util.AbstractQueue;
import pl.mn.edu.emory.mathcs.backport.java.util.PriorityQueue;
import pl.mn.edu.emory.mathcs.backport.java.util.concurrent.helpers.Utils;

/**
 * An unbounded {@linkplain BlockingQueue blocking queue} of
 * <tt>Delayed</tt> elements, in which an element can only be taken
 * when its delay has expired.  The <em>head</em> of the queue is that
 * <tt>Delayed</tt> element whose delay expired furthest in the
 * past.  If no delay has expired there is no head and <tt>poll</tt>
 * will return <tt>null</tt>. Expiration occurs when an element's
 * <tt>getDelay(TimeUnit.NANOSECONDS)</tt> method returns a value less
 * than or equal to zero.  Even though unexpired elements cannot be
 * removed using <tt>take</tt> or <tt>poll</tt>, they are otherwise
 * treated as normal elements. For example, the <tt>size</tt> method
 * returns the count of both expired and unexpired elements.
 * This queue does not permit <tt>null</tt>
 * elements.
 *
 * <p>This class and its iterator implement all of the
 * <em>optional</em> methods of the {@link Collection} and {@link
 * Iterator} interfaces.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../guide/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @since 1.5
 * @author Doug Lea
 */

public class DelayQueue extends AbstractQueue
    implements BlockingQueue {

    private transient final Object lock = new Object();
    private final PriorityQueue q = new PriorityQueue();

    /**
     * Creates a new <tt>DelayQueue</tt> that is initially empty.
     */
    public DelayQueue() {}

    /**
     * Creates a <tt>DelayQueue</tt> initially containing the elements of the
     * given collection of {@link Delayed} instances.
     *
     * @param c the collection of elements to initially contain
     * @throws NullPointerException if the specified collection or any
     *         of its elements are null
     */
    public DelayQueue(Collection c) {
        this.addAll(c);
    }

    /**
     * Inserts the specified element into this delay queue.
     *
     * @param e the element to add
     * @return <tt>true</tt> (as per the spec for {@link Collection#add})
     * @throws NullPointerException if the specified element is null
     */
    public boolean add(Object e) {
        return offer(e);
    }

    /**
     * Inserts the specified element into this delay queue.
     *
     * @param e the element to add
     * @return <tt>true</tt>
     * @throws NullPointerException if the specified element is null
     */
    public boolean offer(Object e) {
        synchronized (lock) {
            Object first = q.peek();
            q.offer(e);
            if (first == null || ((Delayed)e).compareTo(first) < 0)
                lock.notifyAll();
            return true;
        }
    }

    /**
     * Inserts the specified element into this delay queue. As the queue is
     * unbounded this method will never block.
     *
     * @param e the element to add
     * @throws NullPointerException {@inheritDoc}
     */
    public void put(Object e) {
        offer(e);
    }

    /**
     * Inserts the specified element into this delay queue. As the queue is
     * unbounded this method will never block.
     *
     * @param e the element to add
     * @param timeout This parameter is ignored as the method never blocks
     * @param unit This parameter is ignored as the method never blocks
     * @return <tt>true</tt>
     * @throws NullPointerException {@inheritDoc}
     */
    public boolean offer(Object e, long timeout, TimeUnit unit) {
        return offer(e);
    }

    /**
     * Retrieves and removes the head of this queue, or returns <tt>null</tt>
     * if this queue has no elements with an expired delay.
     *
     * @return the head of this queue, or <tt>null</tt> if this
     *         queue has no elements with an expired delay
     */
    public Object poll() {
        synchronized (lock) {
            Object first = q.peek();
            if (first == null || ((Delayed)first).getDelay(TimeUnit.NANOSECONDS) > 0)
                return null;
            else {
                Object x = q.poll();
                //assert x != null;
                if (q.size() != 0)
                    lock.notifyAll();
                return x;
            }
        }
    }

    /**
     * Retrieves and removes the head of this queue, waiting if necessary
     * until an element with an expired delay is available on this queue.
     *
     * @return the head of this queue
     * @throws InterruptedException {@inheritDoc}
     */
    public Object take() throws InterruptedException {
        synchronized (lock) {
            for (;;) {
                Object first = q.peek();
                if (first == null) {
                    lock.wait();
                } else {
                    long delay =  ((Delayed)first).getDelay(TimeUnit.NANOSECONDS);
                    if (delay > 0) {
                        TimeUnit.NANOSECONDS.timedWait(lock, delay);
                    } else {
                        Object x = q.poll();
                        //assert x != null;
                        if (q.size() != 0)
                            lock.notifyAll(); // wake up other takers
                        return x;

                    }
                }
            }
        }
    }

    /**
     * Retrieves and removes the head of this queue, waiting if necessary
     * until an element with an expired delay is available on this queue,
     * or the specified wait time expires.
     *
     * @return the head of this queue, or <tt>null</tt> if the
     *         specified waiting time elapses before an element with
     *         an expired delay becomes available
     * @throws InterruptedException {@inheritDoc}
     */
    public Object poll(long timeout, TimeUnit unit) throws InterruptedException {
        synchronized (lock) {
            long nanos = unit.toNanos(timeout);
            long deadline = Utils.nanoTime() + nanos;
            for (;;) {
                Object first = q.peek();
                if (first == null) {
                    if (nanos <= 0)
                        return null;
                    else {
                        TimeUnit.NANOSECONDS.timedWait(lock, nanos);
                        nanos = deadline - Utils.nanoTime();
                    }
                } else {
                    long delay = ((Delayed)first).getDelay(TimeUnit.NANOSECONDS);
                    if (delay > 0) {
                        if (delay > nanos)
                            delay = nanos;
                        TimeUnit.NANOSECONDS.timedWait(lock, delay);
                        nanos = deadline - Utils.nanoTime();
                    } else {
                        Object x = q.poll();
                        //assert x != null;
                        if (q.size() != 0)
                            lock.notifyAll();
                        return x;
                    }
                }
            }
        }
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or
     * returns <tt>null</tt> if this queue is empty.  Unlike
     * <tt>poll</tt>, if no expired elements are available in the queue,
     * this method returns the element that will expire next,
     * if one exists.
     *
     * @return the head of this queue, or <tt>null</tt> if this
     *         queue is empty.
     */
    public Object peek() {
        synchronized (lock) {
            return q.peek();
        }
    }

    public int size() {
        synchronized (lock) {
            return q.size();
        }
    }

    /**
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     */
    public int drainTo(Collection c) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        synchronized (lock) {
            int n = 0;
            for (;;) {
                Object first = q.peek();
                if (first == null || ((Delayed)first).getDelay(TimeUnit.NANOSECONDS) > 0)
                    break;
                c.add(q.poll());
                ++n;
            }
            if (n > 0)
                lock.notifyAll();
            return n;
        }
    }

    /**
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     */
    public int drainTo(Collection c, int maxElements) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        if (maxElements <= 0)
            return 0;
        synchronized (lock) {
            int n = 0;
            while (n < maxElements) {
                Object first = q.peek();
                if (first == null || ((Delayed)first).getDelay(TimeUnit.NANOSECONDS) > 0)
                    break;
                c.add(q.poll());
                ++n;
            }
            if (n > 0)
                lock.notifyAll();
            return n;
        }
    }

    /**
     * Atomically removes all of the elements from this delay queue.
     * The queue will be empty after this call returns.
     * Elements with an unexpired delay are not waited for; they are
     * simply discarded from the queue.
     */
    public void clear() {
        synchronized (lock) {
            q.clear();
        }
    }

    /**
     * Always returns <tt>Integer.MAX_VALUE</tt> because
     * a <tt>DelayQueue</tt> is not capacity constrained.
     *
     * @return <tt>Integer.MAX_VALUE</tt>
     */
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    /**
     * Returns an array containing all of the elements in this queue.
     * The returned array elements are in no particular order.
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this queue.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this queue
     */
    public Object[] toArray() {
        synchronized (lock) {
            return q.toArray();
        }
    }

    /**
     * Returns an array containing all of the elements in this queue; the
     * runtime type of the returned array is that of the specified array.
     * The returned array elements are in no particular order.
     * If the queue fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this queue.
     *
     * <p>If this queue fits in the specified array with room to spare
     * (i.e., the array has more elements than this queue), the element in
     * the array immediately following the end of the queue is set to
     * <tt>null</tt>.
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose <tt>x</tt> is a queue known to contain only strings.
     * The following code can be used to dump the queue into a newly
     * allocated array of <tt>String</tt>:
     *
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     *
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * @param a the array into which the elements of the queue are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose
     * @return an array containing all of the elements in this queue
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this queue
     * @throws NullPointerException if the specified array is null
     */
    public Object[] toArray(Object[] a) {
        synchronized (lock) {
            return q.toArray(a);
        }
    }

    /**
     * Removes a single instance of the specified element from this
     * queue, if it is present, whether or not it has expired.
     */
    public boolean remove(Object o) {
        synchronized (lock) {
            return q.remove(o);
        }
    }

    /**
     * Returns an iterator over all the elements (both expired and
     * unexpired) in this queue. The iterator does not
     * return the elements in any particular order. The returned
     * iterator is a thread-safe "fast-fail" iterator that will throw
     * {@link java.util.ConcurrentModificationException} upon detected
     * interference.
     *
     * @return an iterator over the elements in this queue
     */
    public Iterator iterator() {
        synchronized (lock) {
            return new Itr(q.iterator());
        }
    }

    private class Itr implements Iterator {
        private final Iterator iter;
        Itr(Iterator i) {
            iter = i;
        }

        public boolean hasNext() {
            return iter.hasNext();
        }

        public Object next() {
            synchronized (lock) {
                return iter.next();
            }
        }

        public void remove() {
            synchronized (lock) {
                iter.remove();
            }
        }
    }

}
