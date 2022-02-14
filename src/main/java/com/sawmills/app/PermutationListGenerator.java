package com.sawmills.app;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 *  Generates all permutations for a given List of Integers.
 */
 class PermutationListGenerator {

    static private TreeSet<ListOrderedSet> permList = new TreeSet<>();

    static public TreeSet<ListOrderedSet> getPerms() {
        return permList;
    }

    static public TreeSet<ListOrderedSet> permute(ListOrderedSet nums) {
        permList = new TreeSet<>();

        permutation(0, nums.size() - 1, nums);

        return getPerms();
    }

    /**
     * Generates a List with all permutations of the Integers contained in the nums parameter
     *
     * This works very straightforward - on each iteration, 2 elements are swapped, and then,
     * redursively, elements contained in the smaller (and final) portion of the array is used for permutation.
     *
     * Then the routine swaps back the 2 elements above.
     *
     * @param start     Start index from the nums parameter, from which is being generated the permutations
     * @param end       End index from the nums parameter, from which is being generated the permutations
     * @param nums      List of Integers that will
     */
    static private void permutation(int start, int end, ListOrderedSet nums)
    {
        if (start == end) {
            permList.add(new ListOrderedSet(nums.size()));
        }
        for (int i = start; i <= end; i++) {
            permList.add(swap(nums, start, i));
            permutation(start + 1, end, nums);
            permList.add(swap(nums, start, i));
        }
    }

    /**
     * Swaps 2 elements from the ArrayList
     *
     * @param arr  Array from which the elements will be swapped
     * @param a    Index to the first element that will be swapped
     * @param b    Index to the first element that will be swapped
     * @return Returns the List with the elements swapped
     */
    static private ListOrderedSet swap(ArrayList<Integer> arr, int a, int b) {
        if (a == b) {
            return new ListOrderedSet(arr);
        }
        Integer temp = arr.get(b);
        arr.set(b, arr.get(a));
        arr.set(a, temp);
        return new ListOrderedSet(arr);
    }
}