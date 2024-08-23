grammar Expr;

// parser rules
prog : (expr ';' NEWLINE?)*; 

expr : expr op=('*'|'/') expr  # infixExpr
     | expr op=('+'|'-') expr  # infixExpr
     | expr op='=' expr       # infixExpr 
     | num                  # numberExpr
     | '(' expr ')'         # parensExpr
     | var		    #varExpr
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
VAR: [a-zA-Z_]+ ;
//OP: [+-/*] ;
