package com.rdecky.asmcalc.calculator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {
    private HistoryValue add = new HistoryValue("+");
    private HistoryValue subtract = new HistoryValue("-");
    private HistoryValue multiply = new HistoryValue("*");
    private HistoryValue divide = new HistoryValue("/");
    private HistoryValue power = new HistoryValue("pow");
    private HistoryValue openParenthesis = new HistoryValue("(");
    private HistoryValue closeParenthesis = new HistoryValue(")");

    @Test
    public void calculate_1plus1() {
        HistoryValue one = new HistoryValue(1L);
        List<HistoryValue> historyValues = new ArrayList();
        historyValues.add(one);
        historyValues.add(add);
        historyValues.add(one);

        long result = Calculator.evaluate(historyValues);

        Long expected = (long) 1 + 1;
        assertThat(result, is(expected));
    }

    @Test
    public void calculate_10minus8() {
        HistoryValue ten = new HistoryValue(10L);
        HistoryValue eight = new HistoryValue(8L);
        List<HistoryValue> historyValues = new ArrayList();
        historyValues.add(ten);
        historyValues.add(subtract);
        historyValues.add(eight);

        long result = Calculator.evaluate(historyValues);

        Long expected = (long) 10 - 8;
        assertThat(result, is(expected));
    }

    @Test
    public void calculate_7times7() {
        HistoryValue seven = new HistoryValue(7L);
        List<HistoryValue> historyValues = new ArrayList();
        historyValues.add(seven);
        historyValues.add(multiply);
        historyValues.add(seven);

        long result = Calculator.evaluate(historyValues);

        Long expected = (long) 7 * 7;
        assertThat(result, is(expected));
    }

    @Test
    public void calculate_30divide3() {
        HistoryValue thirty = new HistoryValue(30L);
        HistoryValue three = new HistoryValue(3L);
        List<HistoryValue> historyValues = new ArrayList();
        historyValues.add(thirty);
        historyValues.add(divide);
        historyValues.add(three);

        long result = Calculator.evaluate(historyValues);

        Long expected = (long) 30 / 3;
        assertThat(result, is(expected));
    }

    @Test
    public void calculate_2toThePowerOf6() {
        HistoryValue two = new HistoryValue(2L);
        HistoryValue six = new HistoryValue(6L);
        List<HistoryValue> historyValues = new ArrayList();
        historyValues.add(two);
        historyValues.add(power);
        historyValues.add(six);

        long result = Calculator.evaluate(historyValues);

        Long expected = (long) Math.pow(2, 6);
        assertThat(result, is(expected));
    }

    @Test
    public void calculate_withParenthesis() {
        HistoryValue four = new HistoryValue(4);
        HistoryValue three = new HistoryValue(3);
        HistoryValue two = new HistoryValue(2);
        List<HistoryValue> historyValues = new ArrayList();
        historyValues.add(openParenthesis);
        historyValues.add(four);
        historyValues.add(add);
        historyValues.add(two);
        historyValues.add(closeParenthesis);
        historyValues.add(multiply);
        historyValues.add(three);

        long result = Calculator.evaluate(historyValues);

        Long expected = (long) (4 + 2) * 3;
        assertThat(result, is(expected));
    }

    @Test
    public void calculate_withDoubleParenthesis() {
        HistoryValue twenty = new HistoryValue(20);
        HistoryValue four = new HistoryValue(4);
        HistoryValue three = new HistoryValue(3);
        HistoryValue two = new HistoryValue(2);
        List<HistoryValue> historyValues = new ArrayList();
        historyValues.add(openParenthesis);
        historyValues.add(openParenthesis);
        historyValues.add(twenty);
        historyValues.add(multiply);
        historyValues.add(four);
        historyValues.add(closeParenthesis);
        historyValues.add(multiply);
        historyValues.add(two);
        historyValues.add(closeParenthesis);
        historyValues.add(divide);
        historyValues.add(openParenthesis);
        historyValues.add(three);
        historyValues.add(subtract);
        historyValues.add(two);
        historyValues.add(closeParenthesis);

        long result = Calculator.evaluate(historyValues);

        Long expected = (long) ((20 * 4) * 2) / (3 - 2);
        assertThat(result, is(expected));
    }
}