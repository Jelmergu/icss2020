package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.Objects;

public class MixinReference extends ASTNode {

    public String name;

    public MixinReference(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getNodeLabel() {
        return "MixinReference  (" + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MixinReference that = (MixinReference) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
