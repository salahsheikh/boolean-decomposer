lexer grammar BoolLexer;

AND        : 'AND' ;
OR         : 'OR' ;
NOT        : 'NOT';
XOR        : 'XOR';
XNOR       : 'XNOR';
NOR        : 'NOR';
NAND       : 'NAND';

TRUE       : 'TRUE' ;
FALSE      : 'FALSE' ;
LPAREN     : '(' ;
RPAREN     : ')' ;

IDENTIFIER : [a-zA-Z_] [a-zA-Z_0-9]* ;

WS         : [ \r\t\n]+ -> skip;