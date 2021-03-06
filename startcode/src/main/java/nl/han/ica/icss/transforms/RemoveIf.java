package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;

import java.util.stream.Collectors;

public class RemoveIf implements Transform {

    @Override
    public void apply(AST ast) {
        resolveNode(ast.root);
    }

    private void resolveNode(ASTNode node) {
        node.getChildren().stream().forEach(child -> {
            if (child instanceof IfClause) {
                node.removeChild(child);
                if (resolveIf((IfClause) child)) ((IfClause) child).body.forEach(node::addChild);
            }
            else resolveNode(child);
        });
    }

    private boolean resolveIf(IfClause child) {
        resolveNode(child);
        return ((BoolLiteral) child.conditionalExpression).value;

    }
}
