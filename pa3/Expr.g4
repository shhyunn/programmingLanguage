grammar Expr;

// parser rules
prog : decl_list expr NEWLINE? ';' NEWLINE?
     | expr NEWLINE? ';' NEWLINE?
     ;

decl_list : decl decl_list
	  | decl
	  ;

decl : 'def' func var_list op='=' expr 'endef'	# decl1
     | 'def' func op='=' expr 'endef'	# decl2
     ;

var_list : var var_list
	 | var			
	 ;

func : VAR
     | FUNC
     ;

expr : expr op=('*'|'/') expr  # infixExpr
     | expr op=('+'|'-') expr  # infixExpr
     | expr op='=' expr       # infixExpr 
     | num                  # numberExpr
     | '(' expr ')'         # parensExpr
     | var		    #varExpr
     | func'()'			#call2Expr
     | func'(' expr_list ')'	#call1Expr
     | 'let' var '=' expr 'in' expr	#letInExpr
     | '~' expr				# negateExpr
     ;

expr_list : expr ',' expr_list
	  | expr
	  ;
 
num  : '-'?INT
     | '-'?REAL
     ;

var : VAR ;

     
// lexer rules                    
NEWLINE: [\r\n]+ ;
INT: [0-9]+ ;          // should handle negatives
REAL: [0-9]+'.'[0-9]* ; // shoul:d handle signs(+/-)
WS: [ \t\r\n]+ -> skip ;
VAR: [a-zA-Z_-]+ ;
FUNC: [a-zA-Z0-9]+ ;
OP: [+-/*] ;
