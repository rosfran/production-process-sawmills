package com.sawmills.app;

import java.util.ArrayList;

/* Class ProfitAndSequence, which stores the profit value
       and a list of tree's trunks sequences. */
class ProfitAndSequence
{
    /* Profit value for a given numerical sequence, representing the trees' chunks value */
    int profit;

    /* List with all trees' chunks sizes */
    ListOrderedSet trunkSequence;

    ProfitAndSequence( int profit, ArrayList<Integer> comb)
    {
        this.profit = profit;
        this.trunkSequence = new ListOrderedSet(comb);
    }

    ProfitAndSequence( int profit, ListOrderedSet comb)
    {
        this.profit = profit;
        this.trunkSequence = new ListOrderedSet(comb);
    }

    public int getProfit(){
        return profit;
    }

    public ListOrderedSet getTrunkSequence()
    {
        return trunkSequence;
    }
}