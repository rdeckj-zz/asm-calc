package com.rdecky.asmcalc.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        Stack<HistoryValue> operatorStack = new Stack<>();
        Stack<HistoryValue> outputStack = new Stack<>();

        for(HistoryValue token: historyValues) {
            if(token.isNumber()) {
                outputStack.push(token);
            }
            if(token.isOperator()) {
                operatorStack.push(token);
                for (HistoryValue operator : operatorStack) {
                    //there is an operator at top of stack with greater precedence
                    if((getPrecedence(operator.getOperator()) > getPrecedence(token.getOperator())
                            //or there is operator at top of the stack with equal prec and is left assoc.
                            || getPrecedence(operator.getOperator()) == getPrecedence(token.getOperator()) && operator.getOperator().equals("^"))
                            //and the top operator is not a left paren
                        && !operator.getOperator().equals("(")) {
                        outputStack.add(operatorStack.pop());
                    }
                }
            }
            if(token.getOperator() != null && token.getOperator().equals("(")) {
                operatorStack.push(token);
            }
            if(token.getOperator() != null && token.getOperator().equals(")")) {
                for(HistoryValue operator: operatorStack) {
                    if(!operator.getOperator().equals("(")) {
                        outputStack.push(operatorStack.pop());
                    }
                    if(operator.getOperator().equals("(")) {
                        operatorStack.pop();
                    }
                }
            }

        }

        while(!operatorStack.empty()) {
            outputStack.push(operatorStack.pop());
        }

        return new ArrayList(outputStack);
    }

    private static int getPrecedence(String operator) {
        int result = 0;
        switch (operator.toLowerCase()) {
            case "pow":
                result = 6;
                break;
            case "mod":
            case "*":
            case "/":
                result = 5;
                break;
            case "+":
            case "-":
                result = 4;
                break;
            case "(":
            case ")":
                result = 3;
                break;
            case "lsh":
            case "rsh":
                result = 2;
                break;
            case "or":
            case "xor":
            case "and":
                result = 1;
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
