package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.Stack;

public class OperationChecker implements CheckerInterface {

    protected Stack<ExpressionType> types;


    public OperationChecker(Stack<ExpressionType> types) {
        this.types = types;
    }

    public OperationChecker() {
        types = new Stack<>();
    }

    @Override
    public void check(ASTNode node) {
        if (node instanceof AddOperation || node instanceof SubtractOperation) (new AddSubChecker(types)).check((Operation) node);
        else if (node instanceof MultiplyOperation) (new MultiplicationChecker(types)).check((MultiplyOperation) node);
        else {
            node.setError("Unknown operation");
            return;
        }
    }

    public static ExpressionType getExpressionType (Operation node) {
        Stack<ExpressionType> types = new Stack<>();
        for (ASTNode child : node.getChildren()) {
            if (child instanceof Operation) {
                types.push(getExpressionType((Operation) child));
                continue;
            }
            types.push(Checker.getExpressionType((Expression) child));
        }
        return node instanceof MultiplyOperation ? MultiplicationChecker.getExpressionType(types, node) : AddSubChecker.getExpressionType(types, node);
    }


}
