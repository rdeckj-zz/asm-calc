package com.rdecky.asmcalc.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Calculator {

    public static long evaluate(List<HistoryValue> historyValues) {
        ArrayList<HistoryValue> RPNHistory = convertInfixToRPN(historyValues);

        return evaluate(RPNHistory);
    }

    private static long evaluate(ArrayList<HistoryValue> input) {
        ArrayList<Long> numberList = new ArrayList<>(2);
        for (HistoryValue o : input) {
            // if this is a number
            if (!o.isOperator()) {
                numberList.add(o.getValue());
            }
            // must be an operator
            else {
                if (numberList.size() >= 2) {
                    long val2 = numberList.remove(numberList.size() - 1);
                    long val1 = numberList.remove(numberList.size() - 1);
                    numberList.add(evaluateOperator(val1, val2, o.getOperator()));
                }
            }
        }
        return numberList.get(0);
    }

    /**
     * Implement Djikstra's shunting-yard algorithm
     */
    private static ArrayList convertInfixToRPN(List<HistoryValue> historyValues) {
        // clear the lists we are going to be using
        ArrayList<HistoryValue> outputList = new ArrayList();
        ArrayList<HistoryValue> operatorList = new ArrayList();
        operatorList.clear();
        for (HistoryValue token : historyValues) {
            if (token.isNumber()) {
                outputList.add(token);
            } else if (token.getOperator().equals("(")) {
                operatorList.add(token);
                // if ')', pop off operator stack and onto output list until we hit a '('
            } else if (token.getOperator().equals(")")) {
                for(int i = operatorList.size() -  1; i >= 0; i--){
                    if(!operatorList.get(i).getOperator().equals("(")){
                        outputList.add(operatorList.remove(i));
                    } else {
                        operatorList.remove(i);
                        break;
                    }
                }
                // if normal operator
            } else if(token.isOperator()){
                if(operatorList.isEmpty()) {
                    operatorList.add(token);
                }
                else{
                    for(int i = operatorList.size() -  1; i >= 0; i--){
                                   if(getPrecedence(operatorList.get(i).getOperator()) > getPrecedence(token.getOperator()) ||
                                           (token.getOperator().charAt(0) != '^' && getPrecedence(operatorList.get(i).getOperator()) == getPrecedence(token.getOperator())
                                                   && !operatorList.get(i).getOperator().equals("("))) {
                                       outputList.add(operatorList.remove(i));
                                   }
                                   break;
                        }
                    operatorList.add(token);
                }
            }
        }
        while(!operatorList.isEmpty()){
            outputList.add(operatorList.remove(operatorList.size()-1));
        }
        return outputList;
    }

    private static int getPrecedence(String operator) {
        int result = 0;
        switch (operator.toLowerCase()) {
            case "pow":
                result = 4;
                break;
            case "*":
            case "/":
                result = 3;
                break;
            case "+":
            case "-":
                result = 2;
                break;
        }
        return result;
    }

    private static long evaluateOperator(long val1, long val2, String operator) {
        long result = 0;
        switch (operator.toLowerCase()) {
            case "or":
                result = val1 | val2;
                break;
            case "xor":
                result = val1 ^ val2;
                break;
            case "and":
                result = val1 & val2;
                break;
            case "lsh":
                result = val1 << val2;
                break;
            //we want an unsigned shift
            case "rsh":
                result = val1 >>> val2;
                break;
            case "mod":
                result = val1 % val2;
                break;
            case "pow":
                result = (long) Math.pow(val1, val2);
                break;
            case "*":
                result = val1 * val2;
                break;
            case "/":
                result = val1 / val2;
                break;
            case "+":
                result = val1 + val2;
                break;
            case "-":
                result = val1 - val2;
                break;
        }
        return result;
    }
}
