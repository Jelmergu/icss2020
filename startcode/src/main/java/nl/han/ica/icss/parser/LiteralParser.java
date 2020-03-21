package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.literals.*;
import org.antlr.v4.runtime.tree.ErrorNodeImpl;

public class LiteralParser {

    public Literal parseLiteral(ICSSParser.LiteralContext ctx) {
        switch (ctx.getStart().getType()) {
            case ICSSParser.COLOR:
                return new ColorLiteral(ctx.getText());
            case ICSSParser.FALSE:
            case ICSSParser.TRUE:
                return new BoolLiteral(ctx.getText().toUpperCase());
            case ICSSParser.PERCENTAGE:
                return new PercentageLiteral(ctx.getText());
            case ICSSParser.SCALAR:
                return new ScalarLiteral(ctx.getText());
            case ICSSParser.PIXELSIZE:
                return new PixelLiteral(ctx.getText());
            default:
                ctx.addErrorNode(new ErrorNodeImpl(ctx.getStart()));
                return null;
        }
    }
}
