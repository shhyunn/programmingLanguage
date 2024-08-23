### Expr.g4
- ASSIGN : 다른 operation을 필요로 하는 Infix Expression과 동일한 형태를 갖도록 expr op= ‘=’ expr로 grammar를 정의하였습니다. 변수 선언을 위해 _, a-z, A-Z 로 VAR를 정의하였고, expr은 var이 될 수 있도록 rule을 추가하였습니다.
- UNARY EXPRESSION : num으로 분류되는 token들만 –가 있어도 되고 없어도되도록 ? 연산자를 이용하여 정의하였습니다. leaf node인 num에만 unary operation으로 분리될 수 있도록 하였기 때문에, op인 ‘-’와 혼동될 수 있는 경우를 방지하였습니다.
  
### AstNodes.java
- Visitor 클래스를 이용하기 위해서는 타입을 통일시켜야 효과적으로 사용할 수 있기 때문에, 추상 클래스인 AstNodes를 정의해두었습니다.
- 이를 상속받은 InfixNode, NumNode, VarNode가 있고, 다시 InfixNode를 상속받은 연산자 (+, -, *, / =) 노드를 정의하였습니다. InfixNode는 op를 기준으로 left노드와 right노드를 추가하여 연결할 필요가 있기 때문에 관련 메서드를 정의한 노드
입니다.
- 추가적으로, ctrl+D가 들어오기 전까지의 모든 Expression의 tree의 루트 노드를 저장할 수 있는 ExprTree 클래스 역시 AstNode를 상속받아 정의하였습니다. 이는 VisitProg의 return type이 AstNodes이기 때문에 이를 충족하기 위함입니다.
- 모든 ast의 변수를 한꺼번에 관리할 수 있도록 tree 클래스에 symbol과 value값을 함께 저장할 수 있는 hash map 자료구조를 추가하였습니다.
  
### BuildAstVisitor.java
- visitProg() : exprtree를 정의한 후, 처음 prog의 child 중에서 ‘;’와 ‘\n’을 제외하고 children 리스트에 추가합니다.
- visitNumber|Var() : 노드에 값을 저장한 후 return 합니다. 만약 var일 경우,tree의 var리스트에 있다면 그 값을 가져와 저장합니다.
- visitInfix() : op를 기준으로 노드를 만들어 노드의 left와 right를 설정합니다. 만약 op가 ‘=’일 경우에는 각 leftnode와 rightnode를 가져와 tree의 var리스트에 저장합니다.
  
### AstCall.java
- tree의 깊이 별 line을 다르게 하기 위해 opnode가 호출될 때마다 line을 뜻하는 level의 수를 1만큼 증가시켜 다음 재귀호출에 전달합니다.
- OP(+|-|*|/|=) : op node인 경우에는 각 op를 뜻하는 문자열을 출력 후, left node와 right node를 재귀호출합니다.
- VAR | NUM : 각 노드가 가지고 있는 symbol 혹은 value 값을 출력합니다.
### Evaluate.java
- OP(+|-|*|/) : ‘=’을 제외한 op node에서는 node의 left node와 right node를 재귀 호출하여 값을 더하여 result를 반환합니다.
- VAR | NUM : 각 노드의 value값을 가져와 result로 반환합니다.
