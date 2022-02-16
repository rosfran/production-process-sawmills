package com.sawmills.app;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 * This is the Result for a given Sawmills scenario. It represents an ordered
 * sequence of tree trunks, a Case Number and a maximum profit value.
 *
 */
public class WoodCutterResult
{

    /* Ordered list of tree chunks, for which will be a maximum profit value.  */
    private Map<Integer, TreeSet<ListOrderedSet>> orderedTrunkSequence = new HashMap<Integer, TreeSet<ListOrderedSet>>();

    /* Number representing this case scenario */
    private int caseNumber;

    /* Maximum profit value */
    private int maximumProfitValue;


    public Map<Integer, TreeSet<ListOrderedSet>> getOrderedTrunkSequence()
    {
        return orderedTrunkSequence;
    }

    public void setOrderedTrunkSequence(Map<Integer, TreeSet<ListOrderedSet>> orderedTrunkSequence)
    {
        this.orderedTrunkSequence = orderedTrunkSequence;
    }

    public int getCaseNumber()
    {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber)
    {
        this.caseNumber = caseNumber;
    }

    public int getMaximumProfitValue()
    {
        return maximumProfitValue;
    }

    public void setMaximumProfitValue(int maximumProfitValue)
    {
        this.maximumProfitValue = maximumProfitValue;
    }

    /* Iterates over all the results, and print the arrays with the tree trunk's sequences. */
    public String printOrderedTrunkSequence()
    {
        String trunks = "";

        for (final Map.Entry<Integer, TreeSet<ListOrderedSet>> entry : orderedTrunkSequence.entrySet())
        {
            trunks += entry.getValue().parallelStream().map(o -> ((ListOrderedSet) o).toString()).collect(
                    Collectors.joining(","));
        }

        return trunks;
    }
}
