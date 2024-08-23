import java.util.HashMap;
import java.util.Map;

public class Evaluate {
	private Map<String, Double> varArr;
	private Map<String, Double> funcArr;
	private Map<String, Double> currArr;

	public Evaluate(Map<String, Double> varArr) {
		this.varArr = varArr;
		this.funcArr = new HashMap<>();
		currArr = this.varArr;
	}

	public double evaluate(AstNodes node) {
		double result = 0.0;

		if (node instanceof AddNode) {
			//System.out.print("i'm add..\n");
			AddNode addNode = (AddNode) node;
			result = evaluate(addNode.getLeft()) + evaluate(addNode.getRight());

		} else if (node instanceof SubNode) {
			SubNode subNode = (SubNode) node;
			result = evaluate(subNode.getLeft()) - evaluate(subNode.getRight());

		} else if (node instanceof MulNode) {
			MulNode mulNode = (MulNode) node;
			result = evaluate(mulNode.getLeft()) * evaluate(mulNode.getRight());

		} else if (node instanceof DivNode) {
			DivNode divNode = (DivNode) node;
			result = evaluate(divNode.getLeft()) / evaluate(divNode.getRight());

		} else if (node instanceof EqeNode) {
			EqeNode eqeNode = (EqeNode) node;

			//VarNode varNode = eqeNode.left;
			//NumNode numNode = eqeNode.right;

			//varNode.setValue(numNode.getValue());

		} else if (node instanceof NumNode) {
			NumNode numNode = (NumNode) node;
			result = numNode.getValue();


		} else if (node instanceof VarNode) {
		//varnode를 전체 트리에서 저장하고 getSymbol해서 같으면 getValue 하는 걸로...
			//System.out.print("I'm varnode..\n");
			VarNode varNode = (VarNode) node;
			String symbol = varNode.getSymbol();
			Double value = this.currArr.get(symbol);

			result = value;

		} else if (node instanceof CallNode) {
			//System.out.print("1\n");
			CallNode callNode = (CallNode) node;
			//System.out.print("2\n");
			DeclNode declNode = (DeclNode) callNode.getFunc();
			//System.out.print("3\n");

			VarListNode varlistNode;
			if (declNode.getParams() != null)
				varlistNode = (VarListNode) declNode.getParams();

			else
				varlistNode = null;

			//System.out.print("4\n");

			ExprListNode exprlistNode;
			if (callNode.getArg() != null)
				exprlistNode = (ExprListNode) callNode.getArg();
			else
				exprlistNode = null;

			while (varlistNode != null) {

				if (varlistNode.getLeft() != null) {
					VarNode varNode = (VarNode) varlistNode.getLeft();
					//this.varArr.put(varNode.getSymbol(), evaluate(exprlistNode.getLeft()));
					this.funcArr.put(varNode.getSymbol(), evaluate(exprlistNode.getLeft()));
				}

				varlistNode = (VarListNode) varlistNode.getRight();
				exprlistNode = (ExprListNode) exprlistNode.getRight();
			}
			this.currArr = this.funcArr;
			result = evaluate(declNode.getBody());
			this.funcArr.clear();
			this.currArr = this.varArr;

		} else if (node instanceof LetInNode) {
			//System.out.print("I'm letin...\n");
			LetInNode letinNode = (LetInNode) node;
			VarNode nameNode = (VarNode) letinNode.getName();
			//System.out.printf("ex1 is %s\n", letinNode.getEx1());
			double value = evaluate(letinNode.getEx1());
			this.currArr.put(nameNode.getSymbol(), value);
			result = evaluate(letinNode.getEx2());
			//System.out.printf("let in node is : %f\n", result);

		} else if (node instanceof DeclListNode) {
			DeclListNode declNode = (DeclListNode) node;
			//System.out.print("evaluate...\n");
			result = evaluate(declNode.getLeft());

			if (declNode.getRight() != null) {
			        String formatt = String.format("%.1f", evaluate(declNode.getRight()));
				System.out.println(formatt);
			}	       

		} else if (node instanceof DeclNode) {

			DeclNode declNode = (DeclNode) node;
			//System.out.print("i'm decl..\n");
		} else if (node instanceof NegateNode) {
			NegateNode negateNode = (NegateNode) node;

			result = -evaluate(negateNode.getInnerNode());
		} 

		return result;	
	}
}
