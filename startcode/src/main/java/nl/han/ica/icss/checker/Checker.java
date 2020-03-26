package nl.han.ica.icss.checker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.*;

public class Checker {

    private LinkedList<HashMap<String,ExpressionType>> variableTypes;

    private Stack<ASTNode> nodes;

    public Checker() {
        this(new LinkedList<HashMap<String, ExpressionType>>(), new Stack<ASTNode>());
    }

    public Checker(LinkedList<HashMap<String, ExpressionType>> variableTypes, Stack<ASTNode> nodes) {
        this.variableTypes = variableTypes;
        this.nodes = nodes;
    }

    public void check(AST ast) {
        checkNode(ast.root);
    }

    public void checkNode(ASTNode node) {
        nodes.push(node);

        getChecker(node);

        node.getChildren().forEach(this::checkNode);

        nodes.pop();
    }

    private void getChecker(ASTNode node) {
        if (node instanceof VariableAssignment || node instanceof VariableReference) (new VariableChecker()).check(node);
        if (node instanceof Operation) (new OperationChecker()).check(node);
        if (node instanceof Declaration) (new DeclarationChecker()).check(node);
        if (node instanceof IfClause) (new IfChecker()).check(node);
        if (node instanceof MixinReference || node instanceof MixinAssignment) (new MixinChecker()).check(node);

    }

    public static ExpressionType getExpressionType(Expression expression) {
        if (expression instanceof Operation) return  OperationChecker.getExpressionType((Operation) expression);
        if (expression instanceof VariableReference) return VariableChecker.getVariableType((VariableReference) expression);
        if (expression instanceof Literal) return ((Literal) expression).getExpressionType();

        return ExpressionType.UNDEFINED;
    }

}
