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
    public static ProfitAndSequence calculateProfit(List<Integer> allTreeChunks, HashMap<Integer, TreeSet<ListOrderedSet>> profitToTrunkSequences) {

        int profit = 0;
        int actualChunkSize = 0;

        for (int i : allTreeChunks) {

            /* If it sums up to a size above 3, than break down the tree chunk
            *  The size of 3 is speficied as the sawmill capacity for processing trunks.
            */
            if (actualChunkSize + i > 3) {
                actualChunkSize = actualChunkSize + i - 3;
                profit += calculateProfitFromBlock(i - actualChunkSize);
                profit += calculateProfitFromBlock(actualChunkSize);
            } else {
                profit += calculateProfitFromBlock(i);
                actualChunkSize += i;

            }
            /* In any case, if the actual chunk size being processed is above 3,
             * the actualChunkSize will be empty.
             * This is necessary because here the machine will probably start processing
             * another chunk.
             */
            if (actualChunkSize > 3)
            {
                actualChunkSize = 0;
            }
        }

        profitToTrunkSequences.putIfAbsent(profit, new TreeSet<ListOrderedSet>());

        ArrayList<Integer> fullList = new ArrayList<Integer>(allTreeChunks);

        return new ProfitAndSequence(profit, fullList);
    }


}
