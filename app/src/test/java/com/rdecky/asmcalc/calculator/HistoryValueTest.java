package com.rdecky.asmcalc.calculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class HistoryValueTest {

    @Test
    public void isOperator_leftParenthesis_false() {
        HistoryValue value = new HistoryValue("(");
        assertFalse(value.isOperator());
    }

    @Test
    public void isOperator_rightParenthesis_false() {
        HistoryValue value = new HistoryValue(")");
        assertFalse(value.isOperator());
    }

    @Test
    public void isParenthesis_leftParenthesis_true() {
        HistoryValue value = new HistoryValue("(");
        assertTrue(value.isParenthesis());
    }

    @Test
    public void isParenthesis_rightParenthesis_true() {
        HistoryValue value = new HistoryValue(")");
        assertTrue(value.isParenthesis());
    }

    @Test
    public void isNumber_add_false() {
        HistoryValue value = new HistoryValue("+");
        assertFalse(value.isNumber());
    }

    @Test
    public void isNumber_nine_true() {
        HistoryValue value = new HistoryValue(9);
        assertTrue(value.isNumber());
    }
}