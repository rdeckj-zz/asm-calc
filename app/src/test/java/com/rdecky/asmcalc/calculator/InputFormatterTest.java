package com.rdecky.asmcalc.calculator;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InputFormatterTest {

    private InputFormatter inputFormatter;

    @Before
    public void setUp() {
        inputFormatter = new InputFormatter();
    }

    @Test
    public void formatHexString_oneCharacter() {
        assertEquals("F", inputFormatter.formatHexString("F"));
    }

    @Test
    public void formatHexString_twoCharacters() {
        assertEquals("FA", inputFormatter.formatHexString("FA"));
    }

    @Test
    public void formatHexString_eightCharacters() {
        assertEquals("123F ABCD", inputFormatter.formatHexString("123FABCD"));
    }

    @Test
    public void formatHexString_sixteenCharacters() {
        assertEquals("FFFF FFFF FFFF FFFF", inputFormatter.formatHexString("FFFFFFFFFFFFFFFF"));
    }

    @Test
    public void formatHexString_mixedCase_returnsAllCapitals() {
        assertEquals("AS D123", inputFormatter.formatHexString("asd123"));
    }

    @Test
    public void formatBinaryString_zero() {
        List<String> result = inputFormatter.formatBinString("0");
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0000", result.get(0));
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0000", result.get(1));
    }

    @Test
    public void formatBinaryString_one() {
        List<String> result = inputFormatter.formatBinString("1");
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0000", result.get(0));
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0001", result.get(1));
    }

    @Test
    public void formatBinaryString_singleDigit_withLeadingZero() {
        List<String> result = inputFormatter.formatBinString("01");
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0000", result.get(0));
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0001", result.get(1));
    }

    @Test
    public void formatBinaryString_twentyFourDigits() {
        List<String> result = inputFormatter.formatBinString("111111111111000000000000");
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0000", result.get(0));
        assertEquals("0000 0000 1111 1111 1111 0000 0000 0000", result.get(1));
    }

    @Test
    public void formatBinaryString_thirtyFourDigits() {
        List<String> result = inputFormatter.formatBinString("1100000000111111111111000000000000");
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0011", result.get(0));
        assertEquals("0000 0000 1111 1111 1111 0000 0000 0000", result.get(1));
    }
}