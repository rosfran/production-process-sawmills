package com.sawmills.app;

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class WoodCutter
{
    public static HashMap<Integer, TreeSet<ListOrderedSet>> profitToTrunkSequences;
    public static int treeChunksSize;

    public static void main(String[] args)
    {
//        String str = "1\n"+
//                "3 2 3 1\n" +
//                "3\n" +
//                "3 1 2 1\n" +
//                "2 1 2\n"+
//                "2 1 4\n"+
//                "0\n";
//        InputStream stream = new ByteArrayInputStream(str.getBytes());
//        Scanner sc = new Scanner(stream);

        Scanner sc = new Scanner(System.in);

        int Z = sc.nextInt();

        int profitValue = 0;

        /* This structure stores the result for a given case. A case is composed by an amount of tree's trunks, and a
           an amount of sawmills.
        */
        Map<Integer, TreeSet<ListOrderedSet>> result = new HashMap<Integer, TreeSet<ListOrderedSet>>();
        int index = 0;


        int caseNumber = 1;

        /* Iterates until find a 0 value for the Z field */
        while (Z != 0)
        {

            /* Structure to maintain all the profit values, during the process of calculating them.
             The HashMap variable has its values updated by the method */
            profitToTrunkSequences = new HashMap<Integer, TreeSet<ListOrderedSet>>();

            /* Number of tree trunks that will be sliced. The value below is called E in the problem description. */
            treeChunksSize = sc.nextInt();

            /* This strucuture is an ArrayList, but that implements the Comparator interface, allowing that we
                could maintains all the arrays sorted inside a TreeSet data strucuture.
            */
            ListOrderedSet list = new ListOrderedSet(0);
            int num = 0;

            /* */
            for (int i = 0; i < treeChunksSize; i++) {
                // Size of the tree trunks (number of trunks).
                num = sc.nextInt();
                list.add(num);
            }

            /* Generates all permutations of trunk sequences on the river, based on trunk's sizes in blocks */
            TreeSet<ListOrderedSet> allTrunksPermutations = PermutationListGenerator.permute(list);

            /* For all trunk's sizes (in block units), calculate the profit.
               There will be one profit value for each sequence of trunks
             */
            for ( final ArrayList<Integer> perm : allTrunksPermutations )
            {
                ProfitAndSequence pr = TreeChunkProcessing.calculateProfit(perm, profitToTrunkSequences);

                /* Each profit value is being stored into a Map (a Key/Value data strucuture), where the Key is the profit value,
                   and the Value is a derived class from ArrayList (ListOrderedSet))  */
                profitToTrunkSequences.get(pr.profit).add(pr.trunkSequence);

            }

            /* Structure to store the maximum profit from the map filled with all found profits. */
            Map.Entry<Integer, TreeSet<ListOrderedSet>> maxProfit = null;

            /* After we had calculated all profit values, for each valid sequences of trunks,
               we can start finding the maximum profit
            */
            for ( final Map.Entry<Integer, TreeSet<ListOrderedSet>> entry : profitToTrunkSequences.entrySet())
            {
                //System.out.println("entry: "+entry);

                /* Search for the max key (by the way, this is to get the maximum profit)  */
                if (maxProfit == null || entry.getKey() > maxProfit.getKey() ) {
                    maxProfit = entry;
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

            Z--;

            /* Found the Z token equals to zero. By the way, this indicates that we reached the end of this test case. */
            if (Z == 0)
            {
                System.out.println("Case Number: " + caseNumber);
                System.out.println("Maximum Profit value: " + profitValue);
                System.out.print("Results: ");

                /* An String message with the String representation got from the List of tree's trunks.  */
                String trunks = "";
                /* Iterates over all the results, and print the arrays with the tree trunk's sequences. */
                for (final Map.Entry<Integer, TreeSet<ListOrderedSet>> entry : result.entrySet())
                {
                    trunks+= entry.getValue().parallelStream().map(o -> ((ListOrderedSet)o).toString()).collect(Collectors.joining(","));
                }

                System.out.println(trunks);

                /* Here we need to clean up the result HashMap - maybe there will be a next case to evaluate. */
                result = new HashMap<Integer, TreeSet<ListOrderedSet>>();
                profitValue = 0;

                /* Go to the next case */
                ++caseNumber;
                Z = sc.nextInt();

            }
            index++;
        } // while  Z != 0

        if ( sc != null )
        {
            sc.close();
        }
    } // method - static void main
}
