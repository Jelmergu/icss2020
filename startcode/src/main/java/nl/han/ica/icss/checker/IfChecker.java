package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.IfClause;
import nl.han.ica.icss.ast.types.ExpressionType;

public class IfChecker implements CheckerInterface {

    @Override
    public void check(ASTNode node) {
        ExpressionType type = Checker.getExpressionType(((IfClause) node).conditionalExpression);
        if (type != ExpressionType.BOOL) {
            node.setError("Expected " + ExpressionType.BOOL + " in if condition, got " + type);
            return;
        }
    }
}
