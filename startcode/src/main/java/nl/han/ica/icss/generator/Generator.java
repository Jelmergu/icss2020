package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;


public class Generator {

	public String generate(AST ast) {
		return generateStylesheet(ast.root);
	}

	public String generateStylesheet(Stylesheet sheet) {
		StringBuilder output = new StringBuilder();
		for (ASTNode child : sheet.getChildren()) {
			output.append(typeResolver(child));
		}
		return output.toString();
	}

	public String typeResolver(ASTNode node) {
		if (node instanceof Declaration) return generateDeclaration((Declaration) node);
		if (node instanceof Literal) return generateLiteral((Literal) node);
		if (node instanceof Stylerule) return generateStylerule((Stylerule) node);

		return "";
	}

	private String generateStylerule(Stylerule node) {
		StringBuilder output = new StringBuilder();
		node.selectors.forEach(selector -> output.append(selector+" "));
		output.append("{ \n");
		for (ASTNode child : node.getChildren()) {
			output.append(typeResolver(child));
		}
		return output +"}\n";
	}

	private String generateLiteral(Literal node) {
		switch (node.getExpressionType()) {
			case PIXEL:
				return ((PixelLiteral) node).value+"px";
			case PERCENTAGE:
				return ((PercentageLiteral) node).value+"%";
			case COLOR:
				return ((ColorLiteral) node).value;
		}
		return "";
	}

	private String generateDeclaration(Declaration node) {
		return "  "+node.property.name +": "+typeResolver(node.expression)+";\n";
	}
}
