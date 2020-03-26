package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.Objects;

public class MixinAssignment extends ASTNode {

    public MixinReference name;
    public ArrayList<ASTNode> body = new ArrayList<>();

   @Override
    public String getNodeLabel() {
        return "MixinAssignment (" + name.name + ")";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        if(name != null) {
            children.add(name);
        }
        children.addAll(body);

        return children;
    }

    @Override
    public ASTNode removeChild(ASTNode child) {
        body.remove(child);
        return super.removeChild(child);
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(name == null) {
            name = (MixinReference) child;
        } else {
            body.add(child);
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MixinAssignment mixinAssignment = (MixinAssignment) o;
        return Objects.equals(name, mixinAssignment.name) &&
                Objects.equals(body, mixinAssignment.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, body);
    }
}
