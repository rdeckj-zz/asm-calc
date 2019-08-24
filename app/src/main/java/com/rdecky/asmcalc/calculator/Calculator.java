package com.rdecky.asmcalc.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    public static long evaluate(List<HistoryValue> historyValues) {
        return evaluate(convertInfixToRPN(historyValues));
    }

    /**
     *
     * @param input
     * @return
     */
    private static long evaluate(ArrayList<HistoryValue> input){
        ArrayList<Long> numberList = new ArrayList<Long>(2);
        for(HistoryValue o : input){
            // if this is a number
            if(!o.isOperator()){
                numberList.add(o.getValue());
            }
            // must be an operator
            else{
                if(numberList.size() >= 2) {
                    long val2 = numberList.remove(numberList.size() - 1);
                    long val1 = numberList.remove(numberList.size() - 1);
                    numberList.add(evaluateOperator(val1, val2, o.getOperator()));
                }
            }
        }
        return numberList.get(0);
    }

    /**
     * Implement Djikstra's shunting-yard algorithm on the historyArrayList ArrayList.
     */
    private static ArrayList convertInfixToRPN(List<HistoryValue> historyValues) {
        // clear the lists we are going to be using
        ArrayList<HistoryValue> RPNList = new ArrayList(10);
        ArrayList<HistoryValue> operatorList = new ArrayList();
        operatorList.clear();
        for (HistoryValue o : historyValues) {
            // if number, push to output
            if (!o.isOperator()) {
                RPNList.add(o);
                // if '(', just push to the operator stack
            } else if (o.getOperator().charAt(0) == '(') {
                operatorList.add(o);
                // if ')', pop off operator stack and onto output list until we hit a '('
            } else if (o.getOperator().charAt(0) == ')') {
                for(int i = operatorList.size() -  1; i >= 0; i--){
                    if(operatorList.get(i).getOperator().charAt(0) != '('){
                        RPNList.add(operatorList.remove(i));
                    } else {
                        operatorList.remove(i);
                        break;
                    }
                }
                // if normal operator
            } else {
                // if the operator stack is empty, just add our current operator to it
                if(operatorList.isEmpty()){
                    operatorList.add(o);
                }
                // else if current operator is left-associative and its precedence <= precedence of top of stack
                else if(o.getOperator().charAt(0) != '^' &&
                        getPrecedence(o.getOperator()) <= getPrecedence(operatorList.get(operatorList.size()-1).getOperator())){
                    // remove from the top of the operator stack and put it on the output list
                    RPNList.add(operatorList.remove(operatorList.size()-1));
                    operatorList.add(o);
                }
                // else if current operator is right-associative and its precedence < precedence top of stack
                else if(o.getOperator().charAt(0) == '^' &&
                        getPrecedence(o.getOperator()) < getPrecedence(operatorList.get(operatorList.size()-1).getOperator())){
                    // remove from the top of the operator stack and put it on the output list
                    RPNList.add(operatorList.remove(operatorList.size()-1));
                    operatorList.add(o);
                }
            }
        }
        while(!operatorList.isEmpty()){
            RPNList.add(operatorList.remove(operatorList.size()-1));
        }
        return RPNList;
    }

    private static int getPrecedence(String operator){
        int result = 0;
        switch (operator.toLowerCase()){
            case "pow":    result = 6;
                break;
            case "mod":
            case "*":
            case "/":   result = 5;
                break;
            case "+":
            case "-": result = 4;
                break;
            case "(":
            case ")":    result = 3;
                break;
            case "lsh":
            case "rsh":      result = 2;
                break;
            case "or":
            case "xor":
            case "and":      result = 1;
                break;
        }
        return result;
    }

    private static long evaluateOperator(long val1, long val2, String operator){
        long result = 0;
        switch (operator.toLowerCase()) {
            case "or":       result = val1 | val2;
                break;
            case "xor":      result = val1 ^ val2;
                break;
            case "and":      result = val1 & val2;
                break;
            case "lsh":      result = val1 << val2;
                break;
            //we want an unsigned shift
            case "rsh":      result = val1 >>> val2;
                break;
            case "mod":   result = val1 % val2;
                break;
            case "pow":    double d1 = val1;
                double d2 = val2;
                result = (long) Math.pow(d1, d2);
                break;
            case "*": result = val1 * val2;
                break;
            case "/":   result = val1 / val2;
                break;
            case "+":      result = val1 + val2;
                break;
            case "-": result = val1 - val2;
                break;
        }
        return result;
    }
}
