package com.sawmills.app;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


public class WoodCutterTests
{

    private static Logger logger = Logger.getLogger(WoodCutterTests.class.getName());

    static final String INPUT1 =
            "1\n"+
            "3 2 3 1\n" +
            "3\n" +
            "3 1 2 1\n" +
            "2 1 2\n"+
            "2 1 4\n"+
            "0\n";

    static final String INPUT2 =
            "2\n"+
            "5 2 3 1 4 5\n" +
            "5 9 6 3 3 2\n" +
            "4\n" +
            "1 2\n" +
            "3 8 2 1\n" +
            "6 9 10 56 4 1 5\n"+
            "2 9 14\n"+
            "2\n" +
            "3 2 1 6\n" +
            "3 18 22 11\n" +
            "1\n" +
            "8 1 1 5 4 1 5 4 90\n"+
            "0\n";

    static final String INPUT3 =
            "1\n"+
            "2 4 6\n" +
            "0\n";

    static final String INPUT4 =
            "3\n"+
            "5 1 4 6 3 5\n" +
            "2 4 6\n" +
            "0\n";

    static final boolean PRINT_DEBUG = true;

    WoodCutter woodCutter;

    @BeforeEach
    void setUp() {
        woodCutter = new WoodCutter(PRINT_DEBUG);
    }

    @Test
    @DisplayName("Process the base case - composed by 2 scenarios")
    void testBaseCase2Scenarios() {

        List<WoodCutterResult> resultList2Cases = null;
        try
        {
            resultList2Cases = woodCutter.processSawmills(INPUT1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        assertTrue( resultList2Cases.size() == 2 ,
                "There are 2 scenarios for this given input problem." );

        assertTrue( resultList2Cases.get(0).getMaximumProfitValue() == 4,
                "The first case scenario has a maximum profit value of 4." );

        assertTrue( resultList2Cases.get(1).getMaximumProfitValue() == 8,
                "The second case scenario has a maximum profit value of 8." );

    }


    @RepeatedTest(3)
    @DisplayName("Process 3 times the same base case with 2 scenarios.")
    void testRepeatingBaseCase3Times() throws Exception
    {

        List<WoodCutterResult> resultList2Cases = woodCutter.processSawmills(INPUT1);

        assertTrue( resultList2Cases.size() == 2 ,
                "There are 2 scenarios for this given input problem." );

        assertTrue( resultList2Cases.get(0).getMaximumProfitValue() == 4,
                "The first case scenario has a maximum profit value of 4." );

        assertTrue( resultList2Cases.get(1).getMaximumProfitValue() == 8,
                "The second case scenario has a maximum profit value of 8." );

    }

    @Test
    @DisplayName("Process an input case with 4 scenarios.")
    void test4Scenarios() {

        List<WoodCutterResult> resultList4Cases = null;
        try
        {
            resultList4Cases = woodCutter.processSawmills(INPUT2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        assertTrue( resultList4Cases.size() == 4 ,
                "There are 4 scenarios for this given input problem." );

        assertTrue( resultList4Cases.get(0).getMaximumProfitValue() == 19,
                "The first case scenario has a maximum profit value of 19." );

        assertTrue( resultList4Cases.get(3).getMaximumProfitValue() == 14,
                "The fourth case scenario has a maximum profit value of 14." );


    }

    @Test
    @DisplayName("Process the a case composed by 1 scenario")
    void testCase1Scenario() {

        List<WoodCutterResult> resultList1Cases = null;
        try
        {
            resultList1Cases = woodCutter.processSawmills(INPUT3);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        assertTrue( resultList1Cases.size() == 1 ,
                "There are 1 scenario for this given input problem." );

        assertTrue( resultList1Cases.get(0).getMaximumProfitValue() == 3,
                "The unique case scenario has a maximum profit value of 3." );


    }

    @Test
    @DisplayName("Process the a case composed by 1 mal-formed scenario")
    void testCase1ScenarioMalFormed() {

        assertThrows( Exception.class, () -> {
            woodCutter.processSawmills(INPUT4);
        }, "Throwed Exception, because INPUT4 is mal-formed." );

    }

}
