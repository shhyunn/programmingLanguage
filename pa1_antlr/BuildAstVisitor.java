public class BuildAstVisitor extends ExprBaseVisitor<AstNodes> {
	
	
	ExprTree tree;
	/*처음 시작*/
	@Override
	public AstNodes visitProg(ExprParser.ProgContext ctx) {
		tree = new ExprTree();

		for (int i = 0; i < ctx.getChildCount(); i++) {
			String ex = ctx.getChild(i).getText();
			if (!(ex.equals(";") || ex.equals( "\n"))){
				tree.addChild(visit(ctx.getChild(i)));
			}

			//ParseTree child = ctx.getChild(i);
			//System.out.print("type:" + ctx.getChild(i).getClass());
			//System.out.print("add child\n");
		}
		//visitChildren(ctx);
		return tree;
	}
	
	@Override
	public AstNodes visitNumberExpr(ExprParser.NumberExprContext ctx) {
		double value = Double.parseDouble(ctx.getText());
		NumNode node = new NumNode();
		node.setValue(value);	
		return node;
	}

	@Override
	public AstNodes visitVarExpr(ExprParser.VarExprContext ctx) {
		String symbol = ctx.getText();
		VarNode node = new VarNode();
		node.setSymbol(symbol);

		double value = tree.getVar(symbol);
		node.setValue(value);

		return node;
	}
	
	@Override
	public AstNodes visitParensExpr(ExprParser.ParensExprContext ctx) {
		return visit(ctx.expr());
	}
 
	@Override
	public AstNodes visitInfixExpr(ExprParser.InfixExprContext ctx) {
		
		InfixNode node;
		String op = ctx.op.getText();
		//System.out.print("i'm infix expr");

		switch (op) {
			case "+":
				node = new AddNode();
				break;

			case "-":
				node = new SubNode();
				break;

			case "*":
				node = new MulNode();
				break;

			case "/":
				node = new DivNode();
				break;

			case "=":
			//	System.out.print("case =");
				node = new EqeNode();
				break;

			default:
				throw new UnsupportedOperationException();

		}

		node.setLeft(visit(ctx.getChild(0)));
		node.setRight(visit(ctx.getChild(2)));

		if (node instanceof EqeNode) {
			//System.out.print("eqenode\n");
			EqeNode eqeNode = (EqeNode) node; 
			VarNode leftNode = (VarNode)(eqeNode.getLeft());
			NumNode rightNode = (NumNode)(eqeNode.getRight());

			String symbol = leftNode.getSymbol();
			double value = rightNode.getValue();
			tree.addVar(symbol, value);
		}	
		return node;
	}
}
