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

class FuncNode extends AstNodes {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class VarListNode extends AstNodes {
	private AstNodes LeftNode;
	private AstNodes RightNode;

	public AstNodes getLeft() {
		return LeftNode;
	}

	public void setLeft(AstNodes left) {
		this.LeftNode = left;
	}

	public AstNodes getRight() {
		return RightNode;
	}

	public void setRight(AstNodes right) {
		this.RightNode = right;
	}
}

class ExprListNode extends AstNodes {
	private AstNodes LeftNode;
	private AstNodes RightNode;

	public AstNodes getLeft() {
		return LeftNode;
	}

	public void setLeft(AstNodes left) {
		this.LeftNode = left;
	}

	public AstNodes getRight() {
		return RightNode;
	}

	public void setRight(AstNodes right) {
		this.RightNode = right;
	}

}

class DeclListNode extends AstNodes {
	private AstNodes LeftNode;
	private AstNodes RightNode;

	public AstNodes getLeft() {
                return LeftNode;
        }

        public void setLeft(AstNodes left) {
                this.LeftNode = left;
        }

        public AstNodes getRight() {
                return RightNode;
        }

        public void setRight(AstNodes right) {
                this.RightNode = right;
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

class NegateNode extends AstNodes {
	private AstNodes innerNode;

	public AstNodes getInnerNode() {
		return this.innerNode;
	}

	public void setInnerNode(AstNodes innerNode) {
		this.innerNode = innerNode;
	}
}

class DeclNode extends AstNodes {
	private AstNodes nameNode;
	private AstNodes paramsNode;
	private AstNodes bodyNode;

	public AstNodes getName() {
		return this.nameNode;
	}

	public void setName(AstNodes node) {
		this.nameNode = node;
	}

	public AstNodes getParams() {
		return this.paramsNode;
	}

	public void setParams(AstNodes param) {
		this.paramsNode = param;
	}

	public AstNodes getBody() {
		return this.bodyNode;
	}

	public void setBody(AstNodes body) {
		this.bodyNode = body;
	}

}

class LetInNode extends AstNodes {
	private AstNodes nameNode;
	private AstNodes ex1Node;
	private AstNodes ex2Node;
	
	public void setName(AstNodes name) {
		this.nameNode = name;
	}

	public AstNodes getName() {
		return this.nameNode;
	}

	public void setEx1(AstNodes ex1) {
		this.ex1Node = ex1;
	} 

	public AstNodes getEx1() {
		return ex1Node;
	}

	public void setEx2(AstNodes ex2) {
		this.ex2Node = ex2;
	}

	public AstNodes getEx2() {
		return ex2Node;
	}
}

class CallNode extends AstNodes {
	private AstNodes nameNode;
	private AstNodes argNode;
	private AstNodes funcNode;

	public AstNodes getName() {
		return this.nameNode;
	}

	public void setName(AstNodes name) {
		this.nameNode = name;
	}

	public AstNodes getArg() {
		return this.argNode;
	}

	public void setArg(AstNodes exprList) {
		this.argNode = exprList;
	}

	public AstNodes getFunc() {
		return this.funcNode;
	}

	public void setFunc(AstNodes func) {
		this.funcNode = func;
	}
}
/*
public class Function {
	private ArrayList<String> params;
	private AstNodes body;

	public Function() {
		this.params = new ArrayList<String>();
	}

	public void setParams(ArrayList<String> params) {
		this.params = params;
	}

	public void addParams(String param) {
		params.add(param);
	}

	public ArrayList<String> getParams() {
		return params;
	}

	public AstNodes getBody() {
		return body;
	}

	public void setBody(AstNodes body) {
		this.body = body;
	}
}
*/
class ExprTree extends AstNodes {
        public ArrayList<AstNodes> children;
	private Map<String, Double> varArr;
	private Map<String, Double> currArr;
	private Map<String, Double> funcArr;
	private ArrayList<DeclNode> FStore;
	//private Map<String, Function> funcs;

        ExprTree() {
                children = new ArrayList<AstNodes>();
		varArr = new HashMap<>();
		funcArr = new HashMap<>();
		currArr = varArr;
		FStore = new ArrayList<DeclNode>();
        }

        public void addChild(AstNodes node) {
                children.add(node);
        }

        public ArrayList<AstNodes> getChildren() {
                return children;
        }

	public void addFStore(DeclNode node) {
		FStore.add(node);
	}

	public ArrayList<DeclNode> getFStore() {
		return FStore;
	}

	public void addVar(String symbol, Double value) {
		currArr.put(symbol, value);
	}

	public int findVar(String symbol) {
		if (currArr.containsKey(symbol))
			return 1;
		else
			return 0;
	}

	public double getVar(String symbol) {
		
		return currArr.get(symbol);
		
	}

	public Map<String, Double> getTotalVar() {
		return this.currArr;
	}

	public void changeArr() {
		this.currArr = this.funcArr;
	}

	public void restoreArr() {
		this.funcArr.clear();
		this.currArr = this.varArr;
	}
	/*
	public void addFunc(String name, Function func) {
		funcs.put(name, func);
	}

	public Function getFunc(String name) {
		if (funcs.containsKey(name)) {
			return funcs.get(name);
		}
		return 0;
	}
	*/
}
