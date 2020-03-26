package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MixinChecker implements CheckerInterface {
    private static HashMap<MixinReference, ArrayList<ASTNode>> mixins = new HashMap<>();

    @Override
    public void check(ASTNode node) {
        if (node instanceof MixinAssignment) check((MixinAssignment) node);
        if (node instanceof MixinReference) check((MixinReference) node);
        return;
    }

    public void check(MixinAssignment assignment) {
        mixins.put(assignment.name, assignment.body);
    }

    public void check(MixinReference node) {
        if (!MixinChecker.mixins.containsKey(node)) node.setError("Mixin " + node.name + " is not defined");
    }
}
