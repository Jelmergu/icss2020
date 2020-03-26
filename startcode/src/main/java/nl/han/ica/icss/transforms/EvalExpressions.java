package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.Checker;

import java.util.ArrayList;
import java.util.HashMap;

public class EvalExpressions implements Transform {

    private HashMap<String, Literal> variableValues;

    enum OperationType {
        MULTIPLY,
        SUBTRACT,
        ADD
    }

    public EvalExpressions() {

        variableValues = new HashMap<>();
    }

    @Override
    public void apply(AST ast) {
        resolveStylesheet(ast.root);
    }

    public void resolveStylesheet(Stylesheet node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof VariableAssignment) {
                resolveAssignment((VariableAssignment) child);
            }
            if (child instanceof Stylerule) {
                resolveStyleRule((Stylerule) child);
            }
        }
    }

    private void resolveStyleRule(ASTNode rule) {
        ArrayList<ASTNode> newBody = new ArrayList<>();
        ArrayList<ASTNode> body;
        if (rule instanceof Stylerule) body = ((Stylerule) rule).body;
        else body = ((IfClause) rule).body;

        for (ASTNode child : body) {
            if (child instanceof Declaration) newBody.add(resolveDeclaration((Declaration) child));
            else if (child instanceof IfClause) newBody.add(resolveIf((IfClause) child));
            else {
                newBody.add(child);
            }
        }
        if (rule instanceof Stylerule) ((Stylerule) rule).body = newBody;
        else ((IfClause) rule).body = newBody;
    }

    private IfClause resolveIf(IfClause clause) {
        clause.conditionalExpression = resolveExpression(clause.conditionalExpression);
        resolveStyleRule(clause);

        return clause;
    }

    private Declaration resolveDeclaration(Declaration declaration) {
        declaration.expression = resolveExpression(declaration.expression);
        return declaration;
    }

    public void resolveAssignment(VariableAssignment variable) {
        String name = variable.name.name;
        Literal value = resolveExpression(variable.expression);

        variableValues.put(name, value);
    }

    public Literal resolveExpression(Expression expression) {
        if (expression instanceof Literal) return (Literal) expression;
        if (expression instanceof VariableReference) return variableValues.get(((VariableReference) expression).name);
        if (expression instanceof MultiplyOperation) return resolveOperation((Operation) expression, OperationType.MULTIPLY);
        if (expression instanceof AddOperation) return resolveOperation((Operation) expression, OperationType.ADD);
        if (expression instanceof SubtractOperation) return resolveOperation((Operation) expression, OperationType.SUBTRACT);

        return null;
    }

    private Literal resolveOperation(Operation expression, OperationType type) {
        int lhs = resolveIntLiteral(resolveExpression(expression.lhs));
        int rhs = resolveIntLiteral(resolveExpression(expression.rhs));
        switch (type) {
            case MULTIPLY:
                return newIntLiteral(lhs * rhs, Checker.getExpressionType(expression));
            case SUBTRACT:
                return newIntLiteral(lhs - rhs, Checker.getExpressionType(expression));
            case ADD:
                return newIntLiteral(lhs + rhs, Checker.getExpressionType(expression));
        }
        return null;
    }

    private Literal newIntLiteral(int value, ExpressionType type) {
        switch (type) {
            case PERCENTAGE:
                return new PercentageLiteral(value);
            case PIXEL:
                return new PixelLiteral(value);
            case SCALAR:
                return new ScalarLiteral(value);
            default:
                throw new Error("Arithmetic Error: Unknown ExpressionType");
        }
    }

    private int resolveIntLiteral(Literal literal) {
        switch (literal.getExpressionType()) {
            case PERCENTAGE:
                return ((PercentageLiteral) literal).value;
            case SCALAR:
                return ((ScalarLiteral)literal).value;
            case PIXEL:
                return ((PixelLiteral)literal).value;
            case BOOL:
            case UNDEFINED:
            case COLOR:
            default:
                return -1;
        }
    }

}


