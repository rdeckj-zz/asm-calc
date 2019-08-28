package com.rdecky.asmcalc.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    public static long evaluate(List<HistoryValue> historyValues) {
        ArrayList<HistoryValue> RPNHistory = convertToRPN(historyValues);

        return evaluateRPNHistory(RPNHistory);
    }

    private static long evaluateRPNHistory(ArrayList<HistoryValue> input) {
        ArrayList<Long> numberList = new ArrayList<>();
        for (HistoryValue value : input) {
            if (value.isNumber()) {
                numberList.add(value.getValue());
            }
            else {
                if (numberList.size() >= 2) {
                    long val2 = numberList.remove(numberList.size() - 1);
                    long val1 = numberList.remove(numberList.size() - 1);
                    numberList.add(evaluateOperator(val1, val2, value.getOperator()));
                }
            }
        }
        return numberList.get(0);
    }

    /**
     * Uses Djikstra's shunting-yard algorithm
     */
    private static ArrayList convertToRPN(List<HistoryValue> historyValues) {
        ArrayList<HistoryValue> outputList = new ArrayList<>();
        ArrayList<HistoryValue> operatorList = new ArrayList<>();
        operatorList.clear();
        for (HistoryValue token : historyValues) {
            if (token.isNumber()) {
                outputList.add(token);
            } else if (token.isLeftParenthesis()) {
                operatorList.add(token);
                // if ')', pop off operator stack and onto output list until we hit a '('
            } else if (token.isRightParenthesis()) {
                for(int i = operatorList.size() -  1; i >= 0; i--){
                    if(!operatorList.get(i).isLeftParenthesis()){
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
                                   if(operatorList.get(i).getPrecedence() > token.getPrecedence() ||
                                           (token.isLeftAssociative() && operatorList.get(i).getPrecedence() == token.getPrecedence()
                                                   && !operatorList.get(i).isLeftParenthesis())) {
                                       outputList.add(operatorList.remove(i));
                                   } else {
                                       break;
                                   }
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
