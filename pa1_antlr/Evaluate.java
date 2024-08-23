public class Evaluate {
	
	public Evaluate() {}

	public double evaluate(AstNodes node) {
		double result = 0.0;

		if (node instanceof AddNode) {
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
			VarNode varNode = (VarNode) node;
			result = varNode.getValue();
		}

		return result;	
	}
}
