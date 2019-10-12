package com.rdecky.asmcalc.util;

public class BaseConverter {
    public static final int DEC_RADIX = 10;
    public static final int HEX_RADIX = 16;
    public static final int BIN_RADIX = 2;

    private static final int HEX_MIDPOINT = 8;
    private static final int BIN_MIDPOINT = 32;

    public static long convertBinString(String binary) throws NumberFormatException {
        try {
            return Long.parseLong(binary, BIN_RADIX);
        } catch (NumberFormatException e) {
            return convertLargeNumber(binary, BIN_MIDPOINT, BIN_RADIX);
        }
    }

    public static long convertHexString(String newText) throws NumberFormatException {
        try {
            return Long.parseLong(newText, HEX_RADIX);
        } catch (NumberFormatException e) {
            return convertLargeNumber(newText, HEX_MIDPOINT, HEX_RADIX);
        }
    }

    public static long convertDecString(String decimal) throws NumberFormatException {
        return Long.parseLong(decimal);
    }

    private static long convertLargeNumber(String binary, int midPoint, int radix) {
        String left = binary.substring(0, midPoint);
        String right = binary.substring(midPoint);

        long leftValue = Long.parseLong(left, radix);
        long rightValue = Long.parseLong(right, radix);

        return leftValue << 32 | rightValue;
    }
}
