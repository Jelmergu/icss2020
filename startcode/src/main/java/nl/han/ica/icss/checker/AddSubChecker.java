package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.Stack;

public class AddSubChecker extends OperationChecker {

    public AddSubChecker(Stack<ExpressionType> types) {
        super(types);
    }

    public AddSubChecker() {
        super();
    }


    public void check(Operation node) {
        if (super.getExpressionType(node) == ExpressionType.UNDEFINED) return;
    }

    public static ExpressionType getExpressionType(Stack<ExpressionType> types, ASTNode node) {
        ExpressionType type = null;

        for (ExpressionType expressionType : types) {
            if (type == null) {
                type = expressionType;
                continue;
            }
            if (type == expressionType) continue;

            node.setError("Expected " + type + ", got " + expressionType);
            return ExpressionType.UNDEFINED;
        }

        return type;
    }


}
