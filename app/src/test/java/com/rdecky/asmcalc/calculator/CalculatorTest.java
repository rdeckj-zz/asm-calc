package com.rdecky.asmcalc.calculator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {
    private ArrayList<HistoryValue> historyValues;

    private HistoryValue add = new HistoryValue("+");
    private HistoryValue subtract = new HistoryValue("-");
    private HistoryValue multiply = new HistoryValue("*");
    private HistoryValue divide = new HistoryValue("/");
    private HistoryValue power = new HistoryValue("pow");
    private HistoryValue openParenthesis = new HistoryValue("(");
    private HistoryValue closeParenthesis = new HistoryValue(")");

    private HistoryValue one = new HistoryValue(1L);
    private HistoryValue two = new HistoryValue(2L);
    private HistoryValue three = new HistoryValue(3L);
    private HistoryValue four = new HistoryValue(4L);
    private HistoryValue five = new HistoryValue(5L);
    private HistoryValue six = new HistoryValue(6L);
    private HistoryValue seven = new HistoryValue(7L);
    private HistoryValue eight = new HistoryValue(8L);
    private HistoryValue ten = new HistoryValue(10L);
    private HistoryValue thirty = new HistoryValue(30L);

    @Before
    public void setUp() {
        historyValues = new ArrayList<>();
    }

    @Test
    public void calculate_1plus1() {
        historyValues.add(one);
        historyValues.add(add);
        historyValues.add(one);

        long result = Calculator.evaluate(historyValues);

        Long expected = (long) 1 + 1;
        assertThat(result, is(2L));
    }

    @Test
    public void calculate_10minus8() {
        historyValues.add(ten);
        historyValues.add(subtract);
        historyValues.add(eight);

        long result = Calculator.evaluate(historyValues);

        assertThat(result, is(2L));
    }

    @Test
    public void calculate_7times7() {
        historyValues.add(seven);
        historyValues.add(multiply);
        historyValues.add(seven);

        long result = Calculator.evaluate(historyValues);

        assertThat(result, is(49L));
    }

    @Test
    public void calculate_30divide3() {
        historyValues.add(thirty);
        historyValues.add(divide);
        historyValues.add(three);

        long result = Calculator.evaluate(historyValues);

        assertThat(result, is(10L));
    }

    @Test
    public void calculate_2toThePowerOf6() {
        historyValues.add(two);
        historyValues.add(power);
        historyValues.add(six);

        long result = Calculator.evaluate(historyValues);

        assertThat(result, is(64L));
    }

    @Test
    public void calculate_2toThePowerOf4Plus8() {
        historyValues.add(two);
        historyValues.add(power);
        historyValues.add(four);
        historyValues.add(add);
        historyValues.add(eight);

        long result = Calculator.evaluate(historyValues);

        assertThat(result, is(24L));
    }

    @Test
    public void calculate_withParenthesis() {
        historyValues.add(openParenthesis);
        historyValues.add(four);
        historyValues.add(add);
        historyValues.add(two);
        historyValues.add(closeParenthesis);
        historyValues.add(multiply);
        historyValues.add(three);

        long result = Calculator.evaluate(historyValues);

        assertThat(result, is(18L));
    }

    @Test
    public void calculate_withDoubleParenthesis() {
        //(30 * 4) * 2 / (3 - 2^4)
        historyValues.add(openParenthesis);
        historyValues.add(thirty);
        historyValues.add(multiply);
        historyValues.add(four);
        historyValues.add(closeParenthesis);
        historyValues.add(multiply);
        historyValues.add(two);
        historyValues.add(divide);
        historyValues.add(openParenthesis);
        historyValues.add(three);
        historyValues.add(subtract);
        historyValues.add(two);
        historyValues.add(power);
        historyValues.add(four);
        historyValues.add(closeParenthesis);

        long result = Calculator.evaluate(historyValues);

        assertThat(result, is(-18L));
    }

    @Test
    public void calculate_withMultipleExponents() {
        //3 + 4 × 2 ÷ ( 1 − 5 ) ^ 2 ^ 3
        historyValues.add(three);
        historyValues.add(add);
        historyValues.add(four);
        historyValues.add(multiply);
        historyValues.add(two);
        historyValues.add(divide);
        historyValues.add(openParenthesis);
        historyValues.add(one);
        historyValues.add(subtract);
        historyValues.add(five);
        historyValues.add(closeParenthesis);
        historyValues.add(power);
        historyValues.add(two);
        historyValues.add(power);
        historyValues.add(three);

        long result = Calculator.evaluate(historyValues);

        assertThat(result, is(3L));
    }
}