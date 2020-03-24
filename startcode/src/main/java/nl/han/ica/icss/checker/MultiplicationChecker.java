package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.Stack;

public class MultiplicationChecker extends OperationChecker {

    public MultiplicationChecker(Stack<ExpressionType> types) {
        super(types);
    }

    public MultiplicationChecker() {
        super();
    }

    public void check(MultiplyOperation node) {
        if (Checker.getExpressionType(node) == ExpressionType.UNDEFINED) return;
    }

    public static ExpressionType getExpressionType(Stack<ExpressionType> types, ASTNode node) {
        ExpressionType type = null;

        for (ExpressionType expressionType : types) {
            if (type == null) {
                type = expressionType;
                continue;
            }
            if (type == ExpressionType.SCALAR && expressionType != ExpressionType.SCALAR) {
                type = expressionType;
                continue;
            }
            if (type == expressionType || expressionType == ExpressionType.SCALAR) continue;

            node.setError("Expected " + type + " or " + ExpressionType.SCALAR + ", got " +expressionType);
            return ExpressionType.UNDEFINED;
        }

        return type;
    }
}
