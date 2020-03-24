package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

public class DeclarationChecker implements CheckerInterface {

    @Override
    public void check(ASTNode node) {
        ExpressionType type = Checker.getExpressionType(((Declaration) node).expression);
        switch (((Declaration) node).property.name) {
            case "background-color":
            case "color":
                if (type != ExpressionType.COLOR) node.setError("property '"+((Declaration) node).property.name + "' expects " + ExpressionType.COLOR + ", got " + type);
                break;
            case "width":
            case "height":
                if (!(type == ExpressionType.PIXEL || type == ExpressionType.PERCENTAGE)) {
                    node.setError("property '"+((Declaration) node).property.name + "' expects " + ExpressionType.PIXEL + " or " + ExpressionType.PERCENTAGE + ", got " + type);
                }
                break;
        }
    }


}
