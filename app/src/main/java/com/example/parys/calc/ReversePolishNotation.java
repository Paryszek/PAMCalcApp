package com.example.parys.calc;

import java.util.Stack;

public class ReversePolishNotation {
    String convertToRPN(String exp) {
        if (exp == null)
            return null;
        String res = "";
        int len = exp.length();
        Stack<Character> operator = new Stack<Character>();
        Stack<String> reversePolish = new Stack<String>();
        operator.push('#');
        for (int i = 0; i < len;) {
            while (i < len && exp.charAt(i) == ' ')
                i++;
            if (i == len)
                break;
            if (isNum(exp.charAt(i), ' ')) {
                String num = "";
                char dot = ".".toCharArray()[0];
                char prev = ' ';
                while (i < len && (isNum(exp.charAt(i), prev) || (exp.charAt(i) == dot))) {
                    num += exp.charAt(i++);
                    prev = exp.charAt(i - 1);
                }
                reversePolish.push(num);
            } else if (isOperator(exp.charAt(i))) {
                char op = exp.charAt(i);
                switch (op) {
                    case '(':
                        operator.push(op);
                        break;
                    case ')':
                        while (operator.peek() != '(')
                            reversePolish.push(Character.toString(operator.pop()));
                        operator.pop();
                        break;
                    case '+':
                    case '-':
                        if (operator.peek() == '(')
                            operator.push(op);
                        else {
                            while (operator.peek() != '#' && operator.peek() != '(')
                                reversePolish.push(Character.toString(operator.pop()));
                            operator.push(op);
                        }
                        break;
                    case '*':
                    case '/':
                        if (operator.peek() == '(')
                            operator.push(op);
                        else {
                            while (operator.peek() != '#' && operator.peek() != '+' &&
                                    operator.peek() != '-' && operator.peek() != '(')
                                reversePolish.push(Character.toString(operator.pop()));
                            operator.push(op);
                        }
                        break;
                }
                i++;
            }
        }
        while (operator.peek() != '#')
            reversePolish.push(Character.toString(operator.pop()));
        while (!reversePolish.isEmpty())
            res = res.length() == 0? reversePolish.pop() + res: reversePolish.pop() + " " + res;
        return res;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }

    private boolean isNum(char c, char p) {
        return (c - '0' >= 0 && c - '0' <= 9) || c == 'E' || (p == 'E' && c == '-');
    }

    String compute(String expr) {
        expr = convertToRPN(expr);
        Stack<Double> stack = new Stack<>();

        for (String token : expr.split("\\s+")) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    Double first = -stack.pop();
                    if (stack.size() != 0) {
                        stack.push(first + stack.pop());
                    } else {
                        stack.push(first);
                    }
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    double divisor = stack.pop();
                    stack.push(stack.pop() / divisor);
                    break;
                case "^":
                    double exponent = stack.pop();
                    stack.push(Math.pow(stack.pop(), exponent));
                    break;
                default:
                    stack.push(Double.parseDouble(token));
                    break;
            }
            System.out.println(stack);
        }
        return stack.pop().toString();
    }

}
