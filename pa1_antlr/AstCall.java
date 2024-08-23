public class AstCall {

	public AstCall() {}

	public void Call(AstNodes node, int level) {
		//System.out.print("hello");

		for (int i = 0; i < level; i++) {
			System.out.print("\t");
		}

		if (node instanceof AddNode) {
			AddNode addNode = (AddNode) node;
			level++;
                	System.out.print("ADD\n");
                	Call(addNode.getLeft(), level);
                	Call(addNode.getRight(), level);

		} else if (node instanceof SubNode) {
			SubNode subNode = (SubNode) node;
			level++;
                	System.out.print("SUB\n");
                	Call(subNode.getLeft(), level);
                	Call(subNode.getRight(), level);
		
		} else if (node instanceof MulNode) {
			MulNode mulNode = (MulNode) node;
			level++;
                	System.out.print("MUL\n");
                	Call(mulNode.getLeft(), level);
                	Call(mulNode.getRight(), level);
		
		} else if ( node instanceof DivNode) {
			DivNode divNode = (DivNode) node;
			level++;
                	System.out.print("DIV\n");
                	Call(divNode.getLeft(), level);
                	Call(divNode.getRight(), level);

		} else if (node instanceof EqeNode) {
			EqeNode eqeNode = (EqeNode) node;
			level++;
			System.out.print("ASSIGN\n");
			Call(eqeNode.getLeft(), level);
			Call(eqeNode.getRight(), level);
		
		} else if (node instanceof VarNode) {
			VarNode varNode = (VarNode) node;
			System.out.printf("%s\n", varNode.getSymbol());
		
		} else if ( node instanceof NumNode) {
			NumNode numNode = (NumNode) node;
			System.out.printf("%.1f\n",numNode.getValue());
		}
	}
}
