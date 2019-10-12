package com.rdecky.asmcalc.util;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class NumberFormatterTest {

    private NumberFormatter numberFormatter;

    @Before
    public void setUp() {
        numberFormatter = new NumberFormatter();
    }

    @Test
    public void formatHexString_oneCharacter() {
        assertEquals("F", numberFormatter.formatHexString("F"));
    }

    @Test
    public void formatHexString_twoCharacters() {
        assertEquals("FA", numberFormatter.formatHexString("FA"));
    }

    @Test
    public void formatHexString_eightCharacters() {
        assertEquals("123F ABCD", numberFormatter.formatHexString("123FABCD"));
    }

    @Test
    public void formatHexString_sixteenCharacters() {
        assertEquals("FFFF FFFF FFFF FFFF", numberFormatter.formatHexString("FFFFFFFFFFFFFFFF"));
    }

    @Test
    public void formatHexString_mixedCase_returnsAllCapitals() {
        assertEquals("AS D123", numberFormatter.formatHexString("asd123"));
    }

    @Test
    public void formatBinaryString_zero() {
        List<String> result = numberFormatter.formatBinString("0");
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0000", result.get(0));
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0000", result.get(1));
    }

    @Test
    public void formatBinaryString_one() {
        List<String> result = numberFormatter.formatBinString("1");
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0000", result.get(0));
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0001", result.get(1));
    }

    @Test
    public void formatBinaryString_singleDigit_withLeadingZero() {
        List<String> result = numberFormatter.formatBinString("01");
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0000", result.get(0));
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0001", result.get(1));
    }

    @Test
    public void formatBinaryString_twentyFourDigits() {
        List<String> result = numberFormatter.formatBinString("111111111111000000000000");
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0000", result.get(0));
        assertEquals("0000 0000 1111 1111 1111 0000 0000 0000", result.get(1));
    }

    @Test
    public void formatBinaryString_thirtyFourDigits() {
        List<String> result = numberFormatter.formatBinString("1100000000111111111111000000000000");
        assertEquals("0000 0000 0000 0000 0000 0000 0000 0011", result.get(0));
        assertEquals("0000 0000 1111 1111 1111 0000 0000 0000", result.get(1));
    }
}