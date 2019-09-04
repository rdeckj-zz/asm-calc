package com.rdecky.asmcalc.calculator;

import com.rdecky.asmcalc.calculator.value.HistoryValue;
import com.rdecky.asmcalc.calculator.value.NumberValue;
import com.rdecky.asmcalc.calculator.value.OperatorValue;

import java.util.ArrayList;
import java.util.List;

class Calculator {

    long evaluate(List<HistoryValue> historyValues) {
        ArrayList<HistoryValue> RPNHistory = convertToRPN(historyValues);

        return evaluateRPNHistory(RPNHistory);
    }

    private long evaluateRPNHistory(ArrayList<HistoryValue> input) {
        ArrayList<Long> numberList = new ArrayList<>();
        for (HistoryValue value : input) {
            if (value instanceof NumberValue) {
                NumberValue numberValue = (NumberValue) value;
                numberList.add(numberValue.getValue());
            } else {
                OperatorValue operatorValue = (OperatorValue) value;
                if (numberList.size() >= 2) {
                    long val2 = numberList.remove(numberList.size() - 1);
                    long val1 = numberList.remove(numberList.size() - 1);
                    numberList.add(evaluateOperator(val1, val2, operatorValue.getText()));
                }
            }
        }
        return numberList.get(0);
    }

    /**
     * Uses Djikstra's shunting-yard algorithm
     */
    private ArrayList<HistoryValue> convertToRPN(List<HistoryValue> historyValues) {
        ArrayList<HistoryValue> outputList = new ArrayList<>();
        ArrayList<OperatorValue> operatorList = new ArrayList<>();
        for (HistoryValue token : historyValues) {
            handleNumber(outputList, token);
            handleLeftParenthesis(operatorList, token);
            handleRightParenthesis(outputList, operatorList, token);
            handleOperator(outputList, operatorList, token);
        }
        putOperatorsOntoOutput(outputList, operatorList);
        return outputList;
    }

    private void putOperatorsOntoOutput(ArrayList<HistoryValue> outputList, ArrayList<OperatorValue> operatorList) {
        while (!operatorList.isEmpty()) {
            outputList.add(operatorList.remove(operatorList.size() - 1));
        }
    }

    private void handleOperator(ArrayList<HistoryValue> outputList, ArrayList<OperatorValue> operatorList, HistoryValue token) {
        if (token instanceof OperatorValue) {
            OperatorValue operatorValue = (OperatorValue) token;
            if (!operatorValue.isParenthesis()) {
                if (operatorList.isEmpty()) {
                    operatorList.add(operatorValue);
                } else {
                    for (int i = operatorList.size() - 1; i >= 0; i--) {
                        if (operatorList.get(i).getPrecedence() > operatorValue.getPrecedence() ||
                                (operatorValue.isLeftAssociative() && operatorList.get(i).getPrecedence() == operatorValue.getPrecedence()
                                        && !operatorList.get(i).isLeftParenthesis())) {
                            outputList.add(operatorList.remove(i));
                        } else {
                            break;
                        }
                    }
                    operatorList.add(operatorValue);
                }
            }
        }
    }

    private void handleRightParenthesis(ArrayList<HistoryValue> outputList, ArrayList<OperatorValue> operatorList, HistoryValue token) {
        if(token instanceof OperatorValue) {
            OperatorValue operatorValue = (OperatorValue) token;
            if (operatorValue.isRightParenthesis()) {
                for (int i = operatorList.size() - 1; i >= 0; i--) {
                    if (!operatorList.get(i).isLeftParenthesis()) {
                        outputList.add(operatorList.remove(i));
                    } else {
                        operatorList.remove(i);
                        break;
                    }
                }
            }
        }
    }

    private void handleLeftParenthesis(ArrayList<OperatorValue> operatorList, HistoryValue token) {
        if (token instanceof OperatorValue) {
            OperatorValue operatorValue = (OperatorValue) token;
            if (operatorValue.isLeftParenthesis()) {
                operatorList.add(operatorValue);
            }
        }
    }

    private void handleNumber(ArrayList<HistoryValue> outputList, HistoryValue token) {
        if (token instanceof NumberValue) {
            outputList.add(token);
        }
    }

    private long evaluateOperator(long val1, long val2, String operator) {
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
