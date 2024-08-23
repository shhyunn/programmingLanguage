public class AstCall {

	public AstCall() {}

	public void Call(AstNodes node, int level) {
		//System.out.print("hello");

		if (!((node instanceof VarListNode) || (node instanceof ExprListNode))) {
			for (int i = 0; i < level; i++) {
				System.out.print("\t");
			}
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
			//System.out.print("varnode in...\n");
			VarNode varNode = (VarNode) node;
			System.out.printf("%s\n", varNode.getSymbol());
		
		} else if ( node instanceof NumNode) {
			NumNode numNode = (NumNode) node;
			System.out.printf("%.1f\n",numNode.getValue());

		} else if (node instanceof DeclNode) {
			DeclNode declNode = (DeclNode) node;
			level++;
			System.out.print("DECL\n");
			//System.out.printf("name level : %d\n", level);
			Call(declNode.getName(), level);

			if (declNode.getParams() != null) {
				//System.out.printf("param level : %d\n", level);
				Call(declNode.getParams(), level);
			
			}

			Call(declNode.getBody(), level);

		} else if (node instanceof FuncNode) {
			FuncNode funcNode = (FuncNode) node;
			System.out.printf("%s\n", funcNode.getName());

		} else if (node instanceof VarListNode) {
			//System.out.print("varlist in...\n");
			//System.out.printf("left level : %d\n", level);
			VarListNode varlistNode = (VarListNode) node;
			Call(varlistNode.getLeft(), level);

			if (varlistNode.getRight() != null) {
				//System.out.print("var call...\n");
				//System.out.printf("right level : %d\n", level);
				Call(varlistNode.getRight(), level);
			}

		} else if (node instanceof LetInNode) {
			LetInNode letinNode = (LetInNode) node;
			level++;
			System.out.print("LETIN\n");
			Call(letinNode.getName(), level);
			Call(letinNode.getEx1(), level);
			Call(letinNode.getEx2(), level);

		} else if (node instanceof CallNode) {
			CallNode callNode = (CallNode) node;
			level++;
			System.out.print("CALL\n");
			Call(callNode.getName(), level);
			if (callNode.getArg() != null)
				Call(callNode.getArg(), level);

		} else if (node instanceof ExprListNode) {
			ExprListNode exprNode = (ExprListNode) node;
			Call(exprNode.getLeft(), level);

			if (exprNode.getRight() != null)
			       Call(exprNode.getRight(), level);
				
		} else if (node instanceof DeclListNode) {
			DeclListNode declNode = (DeclListNode) node;
			Call(declNode.getLeft(), level);

			if (declNode.getRight() != null)
				Call(declNode.getRight(), level);

		} else if (node instanceof NegateNode) {
			NegateNode negateNode = (NegateNode) node;
			level++;
			System.out.print("NEGATE\n");
			//System.out.printf("form : %s\n", negateNode.getInnerNode());
			Call(negateNode.getInnerNode(), level);
		}
	}
}
