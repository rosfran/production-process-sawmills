package com.sawmills.app;

import java.util.*;

/**
 * This class implements the logic behind generating the profit value, based on a list of chunks of trees.
 */
public class TreeChunkProcessing
{

    /**
     * Based on the tree's chunk size, returns the profit value associated with this chunk size.
     *
     * @param chunkSize
     * @return The profit that represents this tree's chunk with size chunkSize.
     */
    public static int calculateProfitFromBlock(int chunkSize)
    {
        switch (chunkSize)
        {
            /* Sawn wood of length of 1 has a profit of -1. It costs money to get rid of it. */
            case 1: return -1;

            /* Sawn wood of length 2 has a profit of +3. */
            case 2: return 3;

            /* Sawn wood of length 3 can still be sold for +1 as it can be shortened by the carpenters themself. */
            case 3: return 1;
            default:  return 0;
        }
    }

    /**
     *  Method to calculate the profit, given a list of tree's trunks sizes.
     *
     *  @returns An object of ProfitAndSequence, which stores the profit value
     *      and a list of tree's trunks sequences.
     */
    public static ProfitAndSequence calculateProfit(List<Integer> list, HashMap<Integer, TreeSet<ListOrderedSet>> profitToTrunkSequences) {

        int profit = 0;
        int actualChunk = 0;

        for (int i : list) {

            if (actualChunk + i > 3) {
                actualChunk = actualChunk + i - 3;
                profit += calculateProfitFromBlock(i - actualChunk);
                profit += calculateProfitFromBlock(actualChunk);
            } else {
                profit += calculateProfitFromBlock(i);
                actualChunk += i;

            }
            if (actualChunk > 3)
            {
                actualChunk = 0;
            }
        }

        profitToTrunkSequences.putIfAbsent(profit, new TreeSet<ListOrderedSet>());

        ArrayList<Integer> fullList = new ArrayList<Integer>(list);

        return new ProfitAndSequence(profit, fullList);
    }


    /* Old function that calculates the profit  */
    public static void calculateProfit2(ArrayList<Integer> combs, HashMap<Integer, TreeSet<ListOrderedSet>> profitToTrunkSequences)
    {
        int profit = 0;

        if ( combs == null || combs.size() == 0 ) return;

        int actualChunk = combs.get(0);

        for (int i = 1; i < combs.size(); i++ )  {

            if ( ( combs.get(i) + actualChunk ) >= 4 )
            {
                int sum = combs.get(i) + actualChunk;
                profit += calculateProfitFromBlock(3);

                profit += calculateProfitFromBlock(sum - 3);

                actualChunk += (sum - 3);
            } else {
                int sum = combs.get(i) + actualChunk;
                profit += calculateProfitFromBlock(sum );

                actualChunk += sum;


            }

            if  ( actualChunk > 3 )
            {
                actualChunk = 0;
            }

        }

        profitToTrunkSequences.putIfAbsent(profit, new TreeSet<ListOrderedSet>());

        ListOrderedSet fullList = new ListOrderedSet(combs);
        profitToTrunkSequences.get(profit).add(fullList);

    }


}
