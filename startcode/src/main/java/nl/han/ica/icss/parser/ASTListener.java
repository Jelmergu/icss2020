package nl.han.ica.icss.parser;

import java.util.Stack;

import javafx.css.Style;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

    //Accumulator attributes:
    private AST ast;

    //Use this to keep track of the parent nodes when recursively traversing the ast
    private Stack<ASTNode> currentContainer;

    public ASTListener() {
        ast = new AST();
        currentContainer = new Stack<>();
    }

    public AST getAST() {
        return ast;
    }

    @Override
    public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
        this.ast.root = new Stylesheet();
        currentContainer.push(this.ast.root);
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        currentContainer.pop();
    }

    private void addChild(ASTNode node) {
        currentContainer.peek().addChild(node);
        currentContainer.push(node);
    }

    @Override
    public void enterStylerule(ICSSParser.StyleruleContext ctx) {
        addChild(new Stylerule());
    }

    @Override
    public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        addChild(new VariableAssignment());
    }

    @Override
    public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
        addChild(new VariableReference(ctx.getText()));
    }

    @Override
    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        currentContainer.pop();
    }

    @Override
    public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
        currentContainer.pop();
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        currentContainer.pop();
    }

    @Override
    public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
        addChild(new Declaration(ctx.getStart().getText()));
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        currentContainer.pop();
    }

    @Override
    public void enterLiteral(ICSSParser.LiteralContext ctx) {
        addChild(new LiteralParser().parseLiteral(ctx));
    }

    @Override
    public void exitLiteral(ICSSParser.LiteralContext ctx) {
        currentContainer.pop();
    }

    @Override
    public void exitSelector(ICSSParser.SelectorContext ctx) {
        currentContainer.pop();
    }

    @Override
    public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
        addChild(new TagSelector(ctx.getText()));
    }

    @Override
    public void enterClassSelector(ICSSParser.ClassSelectorContext ctx) {
        addChild(new ClassSelector(ctx.getText()));
    }

    @Override
    public void enterIdSelector(ICSSParser.IdSelectorContext ctx) {
        addChild(new IdSelector(ctx.getText()));
    }

    @Override
    public void enterOperation(ICSSParser.OperationContext ctx) {
        addChild(new OperationParser().parseOperation(ctx));
    }

    @Override
    public void enterIfClause(ICSSParser.IfClauseContext ctx) {
        addChild(new IfClause());
    }

    @Override
    public void exitIfClause(ICSSParser.IfClauseContext ctx) {
        currentContainer.pop();
    }

    @Override
    public void exitOperation(ICSSParser.OperationContext ctx) {
        currentContainer.pop();
    }
}
