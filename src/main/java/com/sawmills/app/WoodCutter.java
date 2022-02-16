package com.sawmills.app;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class WoodCutter
{
    static HashMap<Integer, TreeSet<ListOrderedSet>> profitToTrunkSequences;

    static int treeChunksQuantity;

    boolean printDebugMessages = true;

    public WoodCutter()
    {
    }

    public WoodCutter( boolean printDebugMessages )
    {
        this.printDebugMessages = printDebugMessages;
    }

    /**
     * Generates a list of results for a given set of wood cutter cases.
     *
     * @param input An String representing a set of cases.
     * @return A list of results.
     */
    public ArrayList<WoodCutterResult> processSawmills(String input) throws Exception
    {

        InputStream stream = new ByteArrayInputStream(input.getBytes());

        return processSawmills(stream);

    }

    /**
     * Generates a list of results for a given set of wood cutter cases.
     *
     * @param stream An IO Stream representing a set of cases.
     * @return A list of results.
     */
    public ArrayList<WoodCutterResult> processSawmills(InputStream stream) throws Exception
    {

        ArrayList<WoodCutterResult> allCases = new ArrayList<WoodCutterResult>();

        Scanner sc = new Scanner(stream);

        /* this variable is the quantity of sawmills for this specific case - it is called variable Z,
         * in the problem description
         */
        int sawmillsCount = sc.nextInt();

        int profitValue = 0;

        /* This structure stores the result for a given case. A case is composed by an amount of tree's trunks, and a
           an amount of sawmills.
        */
        Map<Integer, TreeSet<ListOrderedSet>> result = new HashMap<Integer, TreeSet<ListOrderedSet>>();
        int index = 0;

        int caseNumber = 1;

        /* Iterates until find a 0 value for the sawmillsCount field */
        while (sawmillsCount != 0)
        {

            /* Structure to maintain all the profit values, during the process of calculating them.
             The HashMap variable has its values updated by the method */
            profitToTrunkSequences = new HashMap<Integer, TreeSet<ListOrderedSet>>();

            /* Number of tree trunks that will be sliced. The value below is called E in the problem description. */
            treeChunksQuantity = sc.nextInt();

            /* This strucuture is an ArrayList, but that implements the Comparator interface, allowing that we
                could maintains all the arrays sorted inside a TreeSet data strucuture.
            */
            ListOrderedSet allTreeChunksSizes = new ListOrderedSet(0);
            int num = 0;

            /* This is the number of tree chunks - we fill up the List with the chunks' size */
            for (int i = 0; i < treeChunksQuantity; i++) {
                /* Quantity of tree trunks (number of trunks). */
                num = sc.nextInt();
                allTreeChunksSizes.add(num);
            }

            /* Generates all permutations of trunk sequences on the river, based on trunk's sizes in blocks */
            TreeSet<ListOrderedSet> allTrunksPermutations = PermutationListGenerator.permute(allTreeChunksSizes);

            /* For all trunk's sizes (in block units), calculate the profit.
               There will be one profit value for each sequence of trunks
             */
            for ( final ArrayList<Integer> perm : allTrunksPermutations )
            {
                ProfitAndSequence pr = TreeChunkProcessing.calculateProfit(perm, profitToTrunkSequences);

                /* Each profit value is being stored into a Map (a Key/Value data strucuture), where the Key is the profit value,
                   and the Value is a derived class from ArrayList (ListOrderedSet))  */
                profitToTrunkSequences.get(pr.getProfit()).add(pr.getTrunkSequence());

            }

            /* Structure to store the maximum profit from the map filled with all found profits. */
            Map.Entry<Integer, TreeSet<ListOrderedSet>> maxProfit = null;

            /* After we had calculated all profit values, for each valid sequences of trunks,
               we can start finding the maximum profit
            */
            for ( final Map.Entry<Integer, TreeSet<ListOrderedSet>> profitMapEntry : profitToTrunkSequences.entrySet())
            {
                //System.out.println("entry: "+entry);

                /* Search for the max key (by the way, this is to get the maximum profit)  */
                if (maxProfit == null || profitMapEntry.getKey() > maxProfit.getKey() ) {
                    maxProfit = profitMapEntry;
                }
            }
            /* Sums up the profit, from all maximum profit values available. */
            profitValue += maxProfit.getKey();

            /* Using a TreeSet data structure, which can automatically sort in O(log n), for each add operation.
              See: https://docs.oracle.com/javase/9/docs/api/java/util/TreeSet.html
              Using this strucuture, we would have removed calls to sort functions.
            */
            result.put(index, new TreeSet<ListOrderedSet>());
            Iterator<ListOrderedSet> it = maxProfit.getValue().iterator();
            while ( it.hasNext() )
            {
                ListOrderedSet lists = it.next();
                /* Using this add operation from TreeSet can sort in O(log n). */
                result.get(index).add(lists);
            }

            sawmillsCount--;

            /* Found the sawmillsCount counter equals to zero. By the way, this indicates that we reached the end of this test case. */
            if (sawmillsCount == 0)
            {
                WoodCutterResult woodCutterResult = new WoodCutterResult();

                /* Only print out messages to stdout when the field this.printDebugMessages is true */
                if ( this.printDebugMessages )
                {
                    System.out.println("Case Number: " + caseNumber);
                    System.out.println("Maximum Profit value: " + profitValue);
                    System.out.print("Results: ");
                }

                /* An String message with the String representation got from the List of tree's trunks.  */
                String trunks = "";

                /* Iterates over all the results, and print the arrays with the tree trunk's sequences. */
                for (final Map.Entry<Integer, TreeSet<ListOrderedSet>> entry : result.entrySet())
                {
                    trunks+= entry.getValue().parallelStream().map(o -> ((ListOrderedSet)o).toString()).collect(Collectors.joining(","));
                }

                if ( this.printDebugMessages )
                {
                    System.out.println(trunks);
                }

                /*
                 *  Creates an Object with the data that representes this result - a case number,
                 *  a profit value and a sequence of tree trunks
                 */
                woodCutterResult.setCaseNumber(caseNumber);
                woodCutterResult.setMaximumProfitValue(profitValue);
                woodCutterResult.setOrderedTrunkSequence( result );

                /* Stores this specific result into an array list. This ArrayList will be returned by this method. */
                allCases.add(woodCutterResult);

                /* Here we need to clean up the result HashMap - maybe there will be a next case to evaluate. */
                result = new HashMap<Integer, TreeSet<ListOrderedSet>>();
                profitValue = 0;

                /* Go to the next case */
                ++caseNumber;
                sawmillsCount = sc.nextInt();

            }
            index++;
        } // while  sawmillsCount != 0

        if ( sc != null )
        {
            sc.close();
        }

        return allCases;

    } // method - static void main

    public static void main(String[] args)
    {

        WoodCutter woodCutter = new WoodCutter();

        try
        {
            woodCutter.processSawmills(System.in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    } // method - static void main
}
