package com.booleandecomposer;

import java.util.Map;

class Evaluator extends BoolParserBaseVisitor<Object> {

    private final Map<String, Object> variables;

    public Evaluator(Map<String, Object> variables) {
        this.variables = variables;
    }

    @Override
    public Object visitParse(BoolParser.ParseContext ctx) {
        return super.visit(ctx.expression());
    }

    @Override
    public Object visitIdentifierExpression(BoolParser.IdentifierExpressionContext ctx) {
        return variables.get(ctx.IDENTIFIER().getText());
    }

    @Override
    public Object visitNotExpression(BoolParser.NotExpressionContext ctx) {
        return !((Boolean)this.visit(ctx.expression()));
    }

    @Override
    public Object visitParenExpression(BoolParser.ParenExpressionContext ctx) {
        return super.visit(ctx.expression());
    }

    @Override
    public Object visitBinaryExpression(BoolParser.BinaryExpressionContext ctx) {
        if (ctx.op.AND() != null)
        {
            return asBoolean(ctx.left) && asBoolean(ctx.right);
        }
        else if (ctx.op.OR() != null)
        {
            return asBoolean(ctx.left) || asBoolean(ctx.right);
        }
        else if (ctx.op.XOR() != null)
        {
            return asBoolean(ctx.left) ^ asBoolean(ctx.right);
        }
        else if (ctx.op.XNOR() != null)
        {
            return asBoolean(ctx.left) == asBoolean(ctx.right);
        }
        else if (ctx.op.NAND() != null)
        {
            return !(asBoolean(ctx.left) && asBoolean(ctx.right));
        }
        else if (ctx.op.NOR() != null)
        {
            return !(asBoolean(ctx.left) || asBoolean(ctx.right));
        }
        throw new RuntimeException("Unimplemented logical function: " + ctx.op.getText());
    }

    @Override
    public Object visitPosExpression(BoolParser.PosExpressionContext ctx) {
        return (Boolean) visit(ctx.left) && (Boolean) visit(ctx.right);
    }

    @Override
    public Object visitBoolExpression(BoolParser.BoolExpressionContext ctx) {
        return Boolean.valueOf(ctx.getText());
    }

    private boolean asBoolean(BoolParser.ExpressionContext ctx) {
        return (boolean)visit(ctx);
    }

}