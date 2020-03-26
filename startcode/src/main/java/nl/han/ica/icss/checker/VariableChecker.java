package nl.han.ica.icss.checker;

import com.google.errorprone.annotations.Var;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class VariableChecker implements CheckerInterface {
    private static HashMap<VariableReference, Expression> variables = new HashMap<>();

    public VariableChecker() {
    }

    public VariableChecker(HashMap<VariableReference, Expression> variables) {
        VariableChecker.variables = variables;
    }

    /**
     * Doesn't actually check anything, merely adds the variable to the known variables
     */
    public void check(VariableAssignment var) {
        VariableChecker.variables.put(var.name, var.expression);
    }

    /**
     * Check if the variable is defined
     */

    public void check(VariableReference node) {
        if (!VariableChecker.variables.containsKey(node)) node.setError("Variable " + node.name + " is not defined");
    }


    @Override
    public void check(ASTNode node) {
        if (node instanceof VariableReference) check((VariableReference) node);
        if (node instanceof VariableAssignment) check((VariableAssignment) node);
        return;
    }

    public static ExpressionType getVariableType(VariableReference reference) {
        if (!VariableChecker.variables.containsKey(reference)) {
            reference.setError("Variable " + reference.name + " is not defined");
            return ExpressionType.UNDEFINED;
        }
        else {
            Expression var = VariableChecker.variables.get(reference);
            if (var instanceof VariableReference) return getVariableType((VariableReference) var);
            return Checker.getExpressionType(var);
        }
    }
}
