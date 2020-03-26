package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;

import javax.swing.text.html.StyleSheet;
import java.util.ArrayList;
import java.util.HashMap;

public class TransformMixin implements Transform {

    private HashMap<String, ArrayList<ASTNode>> mixinValues;

    public TransformMixin(HashMap<String, ArrayList<ASTNode>> mixinValues) {
        this.mixinValues = mixinValues;
    }

    public TransformMixin() {
        this(new HashMap<>());
    }

    @Override
    public void apply(AST ast) {
        resolveStylesheet(ast.root);
    }

    private void resolveStylesheet(Stylesheet node) {
        node.getChildren().forEach(child -> {
            if (child instanceof MixinAssignment) {
                resolveAssignment((MixinAssignment) child);
            } else if (child instanceof Stylerule) {
                resolveStyleRule((Stylerule) child);
            }
        });
    }

    private void resolveStyleRule(Stylerule rule) {
        ArrayList<ASTNode> newBody = new ArrayList<>();
        ArrayList<ASTNode> body = rule.body;

        for (ASTNode child : body) {
            if (child instanceof MixinReference) resolveReference((MixinReference) child).forEach(newBody::add);
            else {
                newBody.add(child);
            }
        }
        rule.body = newBody;
    }

    private void resolveAssignment(MixinAssignment child) {
        String name = child.name.name;
        ArrayList<ASTNode> body = child.body;
        mixinValues.put(name, body);
    }

    private ArrayList<ASTNode> resolveReference(MixinReference node) {
        return mixinValues.get(node.name);

    }
}
