import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AstNodes {}

abstract class InfixNode extends AstNodes {
	private AstNodes left;
	private AstNodes right;
	
	public AstNodes getLeft() {
		return left;
	}

	public void setLeft(AstNodes left) {
		this.left = left;
	}

	public AstNodes getRight() {
		return right;
	}

	public void setRight(AstNodes right) {
		this.right = right;
	}
}

class AddNode extends InfixNode {}

class SubNode extends InfixNode {}

class MulNode extends InfixNode {}

class DivNode extends InfixNode {}

class NumNode extends AstNodes {
	private double value;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}

class EqeNode extends InfixNode {}

class VarNode extends AstNodes {
	private String symbol;
	private double value;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
}

class ExprTree extends AstNodes {
        public ArrayList<AstNodes> children;
	private Map<String, Double> varArr;

        ExprTree() {
                children = new ArrayList<AstNodes>();
		varArr = new HashMap<>();
        }

        public void addChild(AstNodes node) {
                children.add(node);
        }

        public ArrayList<AstNodes> getChildren() {
                return children;
        }

	public void addVar(String symbol, Double value) {
		varArr.put(symbol, value);
	}

	public double getVar(String symbol) {
		if (varArr.containsKey(symbol)) {
			return varArr.get(symbol);
		}
		return 0;
	}
}
