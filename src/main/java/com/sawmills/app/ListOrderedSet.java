package com.sawmills.app;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 *  This strucuture is an ArrayList, but that implements the Comparator interface, allowing that we
 *  could maintain all the arrays sorted inside a TreeSet data strucuture.
 *
 *  Have changed from using a List<Integer>, just because in order to guarantee that the values (ArrayList)
 *  inserted into a TreeSet are sorted, we need to provide a Comparable interface.
 *
 *  It's not trivial to sort an array of Integers, but the solution is simple. Choice here is to compare
 *  each element from the array, in the sequence (from the index 0 to the last index).
 *
 *  The first difference found (compareTo differs from zero), we return the compareTo result.
 */
class ListOrderedSet extends ArrayList<Integer> implements  Comparable<ArrayList<Integer>>
{

    public ListOrderedSet(ArrayList<Integer> list)
    {
        super(list);
    }

    public ListOrderedSet(int size)
    {
        super(size);
    }

    @Override
    public int compareTo(ArrayList<Integer> o) {
        int minSize = Math.min(this.size(), o.size());

        Iterator<Integer> it = this.iterator();

        int i = 0;
        while (it.hasNext() && i < minSize )
        {
            Integer num = it.next();

                /* we have to guarantee that each element in this array list has a different value from the other being compared
                  If this happens, then we return the result of compareTo.
                  This will give to the Comparable*/
            int c = num.compareTo(o.get(i));
            if (c != 0) {
                return c;
            }
            ++i;

        }
        return Integer.compare(this.size(), o.size());
    }
}