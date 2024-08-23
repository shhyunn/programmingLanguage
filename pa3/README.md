### Expr.g4
- `decl_list`: 함수를 선언하는 리스트를 정의하였으며, 선언하는 함수가 2개 이상인 경우에는 `decl_list`를 재귀적으로 호출합니다.
- `decl`: 파라미터와 함께 함수 선언이 되는 경우와 파라미터 없이 함수 선언이 되는 경우로 나눠 정의하였습니다.
- `var_list`: 파라미터 선언 시, `decl_list`와 비슷하게 2개 이상일 시 재귀 호출합니다.
- `func`: 함수 이름을 탐지하는 `func`도 새로 정의하였는데, 이는 `var`과 다르게 숫자도 포함될 수 있는 경우를 고려하여 `var`, `func` 모두 가능하게 하였습니다.
- `call`: argument와 함께 호출되는 경우와 argument 없이 호출되는 두 가지 경우로 나눠 정의하였습니다.
- `letIn`: `=`을 기준으로 왼쪽에는 변수명, 오른쪽에는 `expr`로 변수 선언 식을 정의하였으며, `in` 뒤의 `expr`을 추가하였습니다.
- `negate`: `~`과 `expr`의 결합으로 정의하였습니다.
- `expr_list`: argument list를 의미하고, 마찬가지로 2개 이상이면 재귀 호출합니다.

### AstNodes.java
- `FuncNode()`: 함수의 이름을 포함한 노드입니다.
- `NegateNode()`: child는 `~`와 `expr`로 이루어져 있으므로, `expr`만 inner node로 저장합니다.
- `DeclNode()`: 함수 선언 시 사용하는 노드로 함수의 이름은 `FuncNode`, 파라미터는 `VarListNode()`, 함수의 body는 `AstNode`의 형태로 사용 및 저장합니다.
- `LetInNode()`: 변수를 선언할 때 사용하는 노드로 변수의 이름은 `VarNode`, 변수의 value는 `AstNode`, `in` 뒤에 실행할 `expr`도 `AstNode`로 저장합니다.
- `CallNode()`: 함수를 호출할 때 사용하는 노드로 함수의 이름은 `VarNode`, 함수의 argument는 `ExprListNode`, 함수의 이름에 대응되는 함수 노드는 `FuncNode`의 형태로 저장됩니다.
- `VarListNode()`: child는 `var`과 `varlist`가 올 수 있으므로, left 노드와 right 노드를 연결합니다.
- `ExprListNode()`: child는 `expr`과 `exprlist`가 올 수 있으므로, left 노드와 right 노드를 연결합니다.
- `DeclListNode()`: child는 `decl`과 `decllist`가 올 수 있으므로, left 노드와 right 노드를 연결합니다.
- `ExprTree()`: `varArr` 이외에도 함수 처리를 위해 `funcArr`을 추가하였는데, 이는 함수의 파라미터를 저장하여 함수의 body의 변수(variable)가 free identifier인지 여부를 판단할 때 사용합니다. 또한, undefined function을 처리하기 위해 `DeclNode`를 저장한 배열인 `FStore`을 사용하였습니다.

### BuildAstVisitor.java
- `ArrayList<String> SemanticErrors`: semantic error 메시지를 저장하는 리스트입니다.
- `visitProg()`: `ctx`의 순회가 끝난 후, semantic 에러가 하나 이상 발생했을 경우, 에러 메시지를 출력하고 시스템을 강제 종료합니다.
- `visitDecl_list()`: `decl_list`의 0번째 child가 `Decl`이므로, 이때 사용하는 `varArr`을 함수 전용 `funcArr`로 바꾼 후, 저장이 완료되면 다시 `varArr`로 바꿉니다. child가 2개일 경우에만 right 노드를 `decl_list`로 설정하여 방문합니다.
- `visitNegateExpr()`: 방문하여 `~ (0) expr (1)` 이므로 1번째 child를 방문하여 inner 노드로 설정합니다.
- `visitDecl1()`: 파라미터가 있을 경우의 함수 선언으로 각각 저장한 후, `FStore`에 저장합니다.
- `visitDecl2()`: 파라미터가 없을 경우의 함수 선언으로 각각 저장한 후, `FStore`에 저장합니다.
- `visitCall1()`, `visitCall2()`: argument가 있을 경우와 없을 경우에 방문하며, 함수의 이름과 argument 설정 후 해당 함수 또한 `FStore`에서 찾아 함수 노드로 설정합니다.
- `visitVar_list()`: `var_list`의 0번째 child가 `var`이므로, left 노드로 `var`를 설정한 후, child가 2개일 경우에만 right 노드를 `var_list`로 설정하여 방문합니다.
- `visitExpr_list()`: `expr_list`의 0번째 child가 `expr`이므로, left 노드로 `expr`을 설정한 후, child가 2개일 경우에만 right 노드를 `expr_list`로 설정하여 방문합니다.
- `visitFunc()`: 함수의 이름을 설정합니다.
- `visitLetInExpr()`: 변수의 이름과 변수의 값을 방문하여 설정한 후, `var`를 `varArr`에 저장하고, `expr`을 in 뒤의 `expr`을 방문하여 설정합니다.
- **Free Identifier**: `expr`에서 사용된 `var`의 경우에만, `varArr`에서 해당 `var`가 있는지를 확인한 후, 없다면 free identifier 에러를 발생시킵니다.
- **Argument Mismatch**: Call 방문 시, 함수의 파라미터와 호출 시 입력된 argument의 개수를 비교하여 만약 개수가 다르다면 argument mismatch 에러를 발생시킵니다.
- **Undefined Function**: Call 방문 시, `FStore`에 해당 함수 이름과 일치하는 함수가 저장되어 있지 않다면 undefined error를 발생시킵니다.

### AstCall.java
- `VarList` 노드이거나 `ExprList` 노드일 경우에는 `\t`를 출력하지 않습니다.
- `DeclNode`: ‘DECL’ 출력 및 들여쓰기 단계를 1 증가시킵니다. 함수의 이름을 호출하고, 만약 `param` 노드가 있다면 `param` 노드도 호출합니다. 함수의 body도 마지막으로 호출합니다.
- `FuncNode`: 함수의 이름을 호출합니다.
- `VarListNode`: left 노드를 호출하고, right 노드가 null이 아니면 `varlist` 노드도 호출합니다.
- `LetInNode`: ‘LETIN’을 출력 및 들여쓰기 단계를 1 증가시킵니다. `name`과 `value`, `expr`을 출력합니다.
- `CallNode`: ‘CALL’을 출력 및 들여쓰기 단계를 1 증가시킵니다. 함수의 이름과 argument가 존재하면 이를 호출합니다.
- `ExprListNode`: left 노드를 호출하고, right 노드인 `exprlist` 노드가 있다면 이를 호출합니다.
- `DeclListNode`: left 노드를 호출하고, right 노드인 `decl_list` 노드가 있다면 이를 호출합니다.
- `NegateNode`: ‘NEGATE’ 출력 및 들여쓰기 단계를 1 증가시킵니다. inner 노드를 호출합니다.

### Evaluate.java
- `funcArr`: 함수의 파라미터 설정을 위한 리스트입니다.
- `CallNode`: 해당 함수의 `params`와 `call` 노드의 argument들을 대응하여 `funcArr`에 저장합니다. 이후 함수의 body를 평가하기 전에 사용하는 `arr`을 `funcArr`로 바꾸고, 함수 body 평가가 끝난 후에 다시 `varArr`로 바꿉니다.
- `LetInNode`: 변수의 이름과 평가한 값을 `varArr`에 저장합니다.
- `DeclNode`: 선언할 때마다 값을 출력합니다.
- `NegateNode`: inner 노드를 평가한 후, 값의 음수 부호를 추가하여 반전시켜 값을 출력합니다.

### program.java
- 평가를 할 때, 방문자에서 저장한 `varArr`을 추가로 인자로 넘겨주었습니다.
