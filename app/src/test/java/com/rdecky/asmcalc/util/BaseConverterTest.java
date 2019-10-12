package com.rdecky.asmcalc.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseConverterTest {
    @Test
    public void convertBinaryString() {
        Long result = BaseConverter.convertBinString("0001110001010001010101");

        assertThat(result, is(463957L));
    }

    @Test
    public void convertBinaryString_handlesMinValue() {
        Long result = BaseConverter.convertBinString("1000000000000000000000000000000000000000000000000000000000000000");

        assertThat(result, is(Long.MIN_VALUE));
    }

    @Test
    public void convertBinaryString_handlesMaxValue() {
        Long result = BaseConverter.convertBinString("1111111111111111111111111111111111111111111111111111111111111111");

        assertThat(result, is(-1L));
    }

    @Test
    public void convertHexString() {
        Long result = BaseConverter.convertHexString("FFFF9F9F");

        assertThat(result, is(4294942623L));
    }

    @Test
    public void setCurrentValue_inputFormatHex_handlesMaxValue() {
        Long result = BaseConverter.convertHexString("FFFFFFFFFFFFFFFF");

        assertThat(result, is(-1L));
    }

    @Test
    public void setCurrentValue_inputFormatHex_handlesSecondLargestValue() {
        Long result = BaseConverter.convertHexString("EFFFFFFFFFFFFFFF");

        assertThat(result, is(-1152921504606846977L));
    }
}