package com.rdecky.asmcalc.calculator;

import com.rdecky.asmcalc.calculator.value.HistoryValue;
import com.rdecky.asmcalc.calculator.value.NumberValue;
import com.rdecky.asmcalc.calculator.value.OperatorValue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {
    private Calculator calculator;
    private ArrayList<HistoryValue> historyValues;

    private OperatorValue add = new OperatorValue("+");
    private OperatorValue subtract = new OperatorValue("-");
    private OperatorValue multiply = new OperatorValue("*");
    private OperatorValue divide = new OperatorValue("/");
    private OperatorValue power = new OperatorValue("pow");
    private OperatorValue openParenthesis = new OperatorValue("(");
    private OperatorValue closeParenthesis = new OperatorValue(")");

    private NumberValue one = new NumberValue(1L);
    private NumberValue two = new NumberValue(2L);
    private NumberValue three = new NumberValue(3L);
    private NumberValue four = new NumberValue(4L);
    private NumberValue five = new NumberValue(5L);
    private NumberValue six = new NumberValue(6L);
    private NumberValue seven = new NumberValue(7L);
    private NumberValue eight = new NumberValue(8L);
    private NumberValue ten = new NumberValue(10L);
    private NumberValue thirty = new NumberValue(30L);

    @Before
    public void setUp() {
        historyValues = new ArrayList<>();
        calculator = new Calculator();
    }

    @Test
    public void calculate_1plus1() {
        // 1 + 1
        historyValues.add(one);
        historyValues.add(add);
        historyValues.add(one);

        long result = calculator.evaluate(historyValues);

        assertThat(result, is(2L));
    }

    @Test
    public void calculate_10minus8() {
        // 10 - 8
        historyValues.add(ten);
        historyValues.add(subtract);
        historyValues.add(eight);

        long result = calculator.evaluate(historyValues);

        assertThat(result, is(2L));
    }

    @Test
    public void calculate_7times7() {
        // 7 * 7
        historyValues.add(seven);
        historyValues.add(multiply);
        historyValues.add(seven);

        long result = calculator.evaluate(historyValues);

        assertThat(result, is(49L));
    }

    @Test
    public void calculate_30divide3() {
        // 30 / 3
        historyValues.add(thirty);
        historyValues.add(divide);
        historyValues.add(three);

        long result = calculator.evaluate(historyValues);

        assertThat(result, is(10L));
    }

    @Test
    public void calculate_2toThePowerOf6() {
        // 2 ^ 6
        historyValues.add(two);
        historyValues.add(power);
        historyValues.add(six);

        long result = calculator.evaluate(historyValues);

        assertThat(result, is(64L));
    }

    @Test
    public void calculate_2toThePowerOf4Plus8() {
        // 2 ^ 4 + 8
        historyValues.add(two);
        historyValues.add(power);
        historyValues.add(four);
        historyValues.add(add);
        historyValues.add(eight);

        long result = calculator.evaluate(historyValues);

        assertThat(result, is(24L));
    }

    @Test
    public void calculate_withParenthesis() {
        // ( 4 + 2 ) * 3
        historyValues.add(openParenthesis);
        historyValues.add(four);
        historyValues.add(add);
        historyValues.add(two);
        historyValues.add(closeParenthesis);
        historyValues.add(multiply);
        historyValues.add(three);

        long result = calculator.evaluate(historyValues);

        assertThat(result, is(18L));
    }

    @Test
    public void calculate_withDoubleParenthesis() {
        //( 30 * 4 ) * 2 / ( 3 - 2^4 )
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

        long result = calculator.evaluate(historyValues);

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

        long result = calculator.evaluate(historyValues);

        assertThat(result, is(3L));
    }
}