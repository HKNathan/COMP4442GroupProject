package com.comp4442.serviceapp.service;

import org.springframework.stereotype.Service;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;

@Service
public class ComputeService {

    public double evaluate(String expression) throws IllegalArgumentException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression is empty");
        }
        try {
            Expression exp = new ExpressionBuilder(expression).build();
            return exp.evaluate();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid expression: " + e.getMessage(), e);
        }
    }
}