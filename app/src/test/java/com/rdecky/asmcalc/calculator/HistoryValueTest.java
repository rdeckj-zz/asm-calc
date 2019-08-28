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
    public void isLeftParenthesis_leftParenthesis_true() {
        HistoryValue value = new HistoryValue("(");
        assertTrue(value.isLeftParenthesis());
    }

    @Test
    public void isRightParenthesis_rightParenthesis_true() {
        HistoryValue value = new HistoryValue(")");
        assertTrue(value.isRightParenthesis());
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

    @Test
    public void isLeftAssociative_pow_false() {
        HistoryValue value = new HistoryValue("pow");
        assertFalse(value.isLeftAssociative());
    }

    @Test
    public void isLeftAssociative_multiply_true() {
        HistoryValue value = new HistoryValue("x");
        assertTrue(value.isLeftAssociative());
    }

    @Test
    public void isLeftAssociative_divide_true() {
        HistoryValue value = new HistoryValue("/");
        assertTrue(value.isLeftAssociative());
    }
}