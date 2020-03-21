package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import org.antlr.v4.runtime.tree.ErrorNodeImpl;

public class OperationParser {
    public Operation parseOperation(ICSSParser.OperationContext ctx) {
        switch (ctx.getChild(1).getText()) {
            case "+":
                return new AddOperation();
            case "*":
                return new MultiplyOperation();
            case "-":
                return new SubtractOperation();
            default:
                ctx.addErrorNode(new ErrorNodeImpl(ctx.getStart()));
                return null;
        }

    }
}
