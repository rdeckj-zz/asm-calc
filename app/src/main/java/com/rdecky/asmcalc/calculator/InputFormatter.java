package com.rdecky.asmcalc.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class InputFormatter {

    static String stripFormatting(String input) {
        return input.replace(" ", "").replace(",", "");
    }

    static String formatDec(Long number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }

    static String formatHex(Long number) {
        return formatHexString(Long.toHexString(number));
    }

    static String formatHexString(String hexString) {
        StringBuilder sb = new StringBuilder(hexString);
        String temp;

        temp = sb.reverse().toString();
        sb.delete(0, sb.length());

        for (int i = 1; i < temp.length() + 1; i++) {
            sb.append(temp.charAt(i - 1));
            if (i % 4 == 0 && i != temp.length()) {
                sb.append(" ");
            }
        }
        return sb.reverse().toString().toUpperCase();
    }

    static List<String> formatBinString(String binString) {
        ArrayList<String> returnList = new ArrayList<>();
        int binStringLength = binString.length();

        if (binStringLength > 32) {
            int lengthToGet = binString.length() - 32;
            String secondHalf = binString.substring(0, lengthToGet);
            String firstHalf = binString.substring(lengthToGet, lengthToGet + 32);
            returnList.add(formatEntireBinString(secondHalf));
            returnList.add(formatEntireBinString(firstHalf));
        } else {
            returnList.add(formatEntireBinString(""));
            returnList.add(formatEntireBinString(binString));
        }

        return returnList;
    }

    private static String formatEntireBinString(String binString) {
        StringBuilder sb = new StringBuilder(binString);
        StringBuilder reversedSb = new StringBuilder(sb.reverse().toString());

        sb.delete(0, sb.length());
        padByteWithZeros(reversedSb, calculateByteZeros(reversedSb));
        padHalfwordWithZeros(reversedSb);
        spacePaddedHalfword(sb, reversedSb);
        return sb.toString();
    }

    private static void spacePaddedHalfword(StringBuilder sb, StringBuilder reversedSb) {
        int spaceCnt = 0;
        for (int i = reversedSb.length(); i >= 1; i--) {
            spaceCnt++;
            sb.append(reversedSb.charAt(i - 1));
            if (spaceCnt % 4 == 0 && i != 0 && i != 1) {
                sb.append(" ");
            }
        }
    }

    private static void padHalfwordWithZeros(StringBuilder reversedSb) {
        for (int i = 32 - reversedSb.length(); i > 0; i--) {
            reversedSb.append("0");
        }
    }

    private static void padByteWithZeros(StringBuilder stringBuilder, int fillZeroes) {
        while (fillZeroes != 0 && fillZeroes != 4) {
            stringBuilder.append("0");
            fillZeroes--;
        }
    }

    private static int calculateByteZeros(StringBuilder stringBuilder) {
        int fillZeroes;
        if (stringBuilder.length() >= 4) {
            fillZeroes = 4 - stringBuilder.length() % 4;
        } else {
            fillZeroes = 4 - stringBuilder.length();
        }
        return fillZeroes;
    }
}
