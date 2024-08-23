import java.util.ArrayList;

public class BuildAstVisitor extends ExprBaseVisitor<AstNodes> {
	
	
	ExprTree tree;
	public ArrayList<String> SemanticErrors = new ArrayList<String>();
	/*처음 시작*/
	@Override
	public AstNodes visitProg(ExprParser.ProgContext ctx) {
		tree = new ExprTree();
		//System.out.printf("count : %d\n", ctx.getChildCount());
		for (int i = 0; i < ctx.getChildCount(); i++) {
			String ex = ctx.getChild(i).getText();
			if (!(ex.equals(";") || ex.equals( "\n"))){
				tree.addChild(visit(ctx.getChild(i)));
			}

			//ParseTree child = ctx.getChild(i);
			//System.out.print("type:" + ctx.getChild(i).getClass());
			//System.out.print("add child\n");
		}
		if (SemanticErrors.size() > 0) {

			for (String strr : SemanticErrors) {
				System.out.println(strr);
			}

			System.exit(0);
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

		if (tree.findVar(symbol) == 0) {
			SemanticErrors.add("Error: Free identifier " + symbol + " detected." );
		} else {
			//함수인 경우에는 어차피 값 다시 해줘야되는데 어떻게 해주지? 
			double value = tree.getVar(symbol);
			node.setValue(value);
		}
		/*
		double value = tree.getVar(symbol);
		node.setValue(value);
	`	*/
		return node;
	}
	
	@Override
	public AstNodes visitParensExpr(ExprParser.ParensExprContext ctx) {
		return visit(ctx.expr());
	}

        

	@Override public AstNodes visitDecl_list(ExprParser.Decl_listContext ctx) { 
		DeclListNode node = new DeclListNode();

		tree.changeArr();	
		node.setLeft(visit(ctx.getChild(0))); //DECL
	        tree.restoreArr();

		if (ctx.getChildCount() == 2)
			node.setRight(visit(ctx.getChild(1)));
			
		return node; 
	}

	@Override public AstNodes visitNegateExpr(ExprParser.NegateExprContext ctx) { 
		
		NegateNode node = new NegateNode();
		node.setInnerNode(visit(ctx.getChild(1)));

		return node;
	}

	@Override
	public AstNodes visitDecl1(ExprParser.Decl1Context ctx) { //파라미터 존재
		//Funtcion funct = new Function();
		DeclNode node = new DeclNode();
		
		node.setName(visit(ctx.getChild(1))); //func name

		node.setParams(visit(ctx.getChild(2))); //param string
		node.setBody(visit(ctx.getChild(4))); //funexpr
		
		tree.addFStore(node);

		//System.out.print("add func1..\n");

		return node; 
	}

	@Override public AstNodes visitDecl2(ExprParser.Decl2Context ctx) { 
		
		//Function funct = new Function();
		DeclNode node = new DeclNode();

		node.setName(visit(ctx.getChild(1)));

		node.setBody(visit(ctx.getChild(3))); //funexpr

		tree.addFStore(node);
		//System.out.print("add func2,,\n");

		return node;
	}

        @Override public AstNodes visitCall1Expr(ExprParser.Call1ExprContext ctx) { 
		
		CallNode node = new CallNode();

		node.setName(visit(ctx.getChild(0)));
		node.setArg(visit(ctx.getChild(2)));
		FuncNode findNode = (FuncNode) node.getName();

		for (DeclNode item : tree.getFStore()) {
			//System.out.printf("item's name : %s\n", item.getName());
			FuncNode funcNode = (FuncNode) item.getName();
			//System.out.printf("func name : %s\n", funcNode.getName());
			//System.out.printf("node name : %s\n", findNode.getName());
			if (funcNode.getName().equals(findNode.getName())) {
				//System.out.print("items equals..\n");
				node.setFunc(item);
				break;
			}
		}

		if (node.getFunc() == null)
			SemanticErrors.add("Error: Undefined function " + findNode.getName() + " detected.");

		DeclNode declNode = (DeclNode) node.getFunc();

		VarListNode varlistNode = (VarListNode) declNode.getParams();
		ExprListNode exprlistNode = (ExprListNode) node.getArg();

		int varCount = 0;
		int exprCount = 0;

		while (varlistNode != null) {
			if (varlistNode.getLeft() != null)
				varCount += 1;
			varlistNode = (VarListNode) varlistNode.getRight();
		}

		while (exprlistNode != null) {
			if (exprlistNode.getLeft() != null)
				exprCount += 1;
			exprlistNode = (ExprListNode) exprlistNode.getRight();
		}
		
		if (varCount != exprCount)
			SemanticErrors.add("Error: The number of arguments of " + findNode.getName() +" mismatched, Required: " + String.valueOf(varCount) + ", Actual: " + String.valueOf(exprCount));

		//findNode의 getParams해서 right? 가 0일 떄까지를 비교...
		//현 node의 getArgs해서 right?가 0일 때까지를 비교...
		
		return node; 
	}

	@Override public AstNodes visitCall2Expr(ExprParser.Call2ExprContext ctx) {
       		
		CallNode node = new CallNode();

		node.setName(visit(ctx.getChild(0)));
		FuncNode findNode = (FuncNode) node.getName();

		for (DeclNode item : tree.getFStore()) {
			FuncNode funcNode = (FuncNode) item.getName();
			if (funcNode.getName().equals(findNode.getName())) {
				node.setFunc(item);
				break;
			}
		}

		if (node.getFunc() == null) {
			SemanticErrors.add("Error: Undefined function " + findNode.getName() + " detected.");

		} else {
			DeclNode declNode = (DeclNode) node.getFunc();
		
			int varCount = 0;
			if (declNode.getParams() != null) {
				VarListNode varlistNode = (VarListNode) declNode.getParams();

				while (varlistNode != null) {

					if (varlistNode.getLeft() != null)
						varCount += 1;
					varlistNode = (VarListNode) varlistNode.getRight();
				}

				SemanticErrors.add("Error: The number of arguments of " + findNode.getName() +" mismatched, Required: " + String.valueOf(varCount) + ", Actual: 0");
			}

		}
		return node; 
	}


	@Override public AstNodes visitVar_list(ExprParser.Var_listContext ctx) { 
		VarListNode node = new VarListNode();

		node.setLeft(visit(ctx.getChild(0)));
		VarNode varNode = (VarNode) node.getLeft();
		tree.addVar(varNode.getSymbol(), 0.0);

		if (ctx.getChildCount() == 2)
			node.setRight(visit(ctx.getChild(1)));
	
		return node; 
	
	}

	@Override
        public AstNodes visitVar(ExprParser.VarContext ctx) {
                String symbol = ctx.getText();
                VarNode node = new VarNode();
                node.setSymbol(symbol);

                //double value = tree.getVar(symbol);
                //node.setValue(value);

                return node;
        }

	@Override public AstNodes visitExpr_list(ExprParser.Expr_listContext ctx) { 
		ExprListNode node = new ExprListNode();

		node.setLeft(visit(ctx.getChild(0)));

		if (ctx.getChildCount() == 3)
			node.setRight(visit(ctx.getChild(2)));

		return node; 
	}	

	@Override public AstNodes visitFunc(ExprParser.FuncContext ctx) { 
		FuncNode node = new FuncNode();
		String name = ctx.getText();
		node.setName(name);

                return node;
	}
        @Override public AstNodes visitLetInExpr(ExprParser.LetInExprContext ctx) { 
		LetInNode node = new LetInNode();
		node.setName(visit(ctx.getChild(1)));
		node.setEx1(visit(ctx.getChild(3)));
		//tree.addVar(node.getName(), 0.0);
		VarNode leftNode = (VarNode)(node.getName());
		//NumNode rightNode = (NumNode)(node.getEx1());

		String symbol = leftNode.getSymbol();
		//double value = rightNode.getValue();
		tree.addVar(symbol, 0.0);

		node.setEx2(visit(ctx.getChild(5)));

		return node;
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


