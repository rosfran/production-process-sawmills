package com.sawmills.app;

import java.util.ArrayList;
import java.util.TreeSet;

/*
        Generates all permutations for a given List of Integers.
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