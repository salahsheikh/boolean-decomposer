package com.booleandecomposer;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.*;

public class TruthTable {

    private String expression;

    private String[] variables;

    private BoolLexer lexer;

    private BoolParser parser;

    private boolean[][] rows;

    private boolean[] results;

    public TruthTable(String expression) {
        this.expression = expression;
        this.findVariables();
    }

    private void findVariables() {
        ArrayList vars = new ArrayList<String>();
        BoolParser _parser =  new BoolParser(new CommonTokenStream(new BoolLexer(CharStreams.fromString(Sanitizer.cleanup(expression)))));
        ParseTreeWalker.DEFAULT.walk(new BoolParserBaseListener() {
            @Override
            public void enterIdentifierExpression(BoolParser.IdentifierExpressionContext ctx) {
                vars.add(ctx.children.get(0).getText());
            }
        }, _parser.parse());
        this.variables = (String[]) vars.stream().distinct().toArray(String[]::new);
    }

    public void generateTruthTable() {
        int numOfVars = variables.length;
        int numOfCombinations = (int) Math.pow(2, numOfVars);
        this.results = new boolean[numOfCombinations];

        this.rows = new boolean[numOfCombinations][numOfVars]; //should always be an integer
        byte b = 0b0;
        for(int i = 0; i < numOfCombinations; i++) {
            for(int j = 0; j < numOfVars; j++) {
                rows[i][j] = (b & (1 << j)) != 0;
            }
            reverse(rows[i]);
            b++;
        }

        Map<String, Object> temp_vars = new HashMap<String, Object>();
        for(int i = 0; i < numOfCombinations; i++) {
            for(int j = 0; j < numOfVars; j++) {
                temp_vars.put(variables[j], rows[i][j]);
            }
            lexer = new BoolLexer(CharStreams.fromString(Sanitizer.cleanup(expression)));
            parser = new BoolParser(new CommonTokenStream(lexer));
            Object result = new Evaluator(temp_vars).visit(parser.parse());
            results[i] = (boolean) result;
            temp_vars.clear();
        }
    }

    public void print() {
        int numOfVars = variables.length;
        int numOfCombinations = (int) Math.pow(2, numOfVars);
        for (int i = 0; i < variables.length; i++) {
            System.out.print(variables[i] + "\t");
        }
        System.out.print("OUTPUT\n");

        for(int i = 0; i < numOfCombinations; i++) {
            for(int j = 0; j < numOfVars; j++) {
                System.out.print((rows[i][j] == true ? 1 : 0) + "\t");
            }
            System.out.print(results[i] == true ? 1 : 0);
            System.out.println();
        }
    }

    public List<String> getMinterms() {
        ArrayList<String> minterms = new ArrayList<>();
        for(int i = 0; i < results.length; i++) {
            StringBuilder s = new StringBuilder();
            if (results[i]) {
                for(int j = 0; j < rows[0].length; j++) {
                    if(rows[i][j]) {
                        s.append(variables[j]);
                    } else {
                        s.append(variables[j] + "'");
                    }
                }
                minterms.add(s.toString());
            }
        }
        return minterms;
    }

    public List<String> getMaxterms() {
        ArrayList<String> minterms = new ArrayList<>();
        for(int i = 0; i < results.length; i++) {
            StringBuilder s = new StringBuilder();
            if (!results[i]) {
                for(int j = 0; j < rows[0].length; j++) {
                    if(rows[i][j]) {
                        s.append(variables[j]);
                    } else {
                        s.append(variables[j] + "'");
                    }
                    if(j != rows[0].length - 1)
                        s.append("+");
                }
                minterms.add("(" + s.toString() + ")");
            }
        }
        return minterms;
    }

    public boolean[][] getRows() {
        return rows;
    }

    public boolean[] getResults() {
        return results;
    }

    private boolean[] reverse(boolean[] original) {
        for(int i = 0; i < original.length / 2; i++) {
            boolean temp = original[i];
            original[i] = original[original.length - i - 1];
            original[original.length - i - 1] = temp;
        }
        return original;
    }

}
