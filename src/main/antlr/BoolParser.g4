parser grammar BoolParser;

options
{
tokenVocab=BoolLexer;
}

parse
 : expression EOF
 ;

expression
 : LPAREN expression RPAREN                       #parenExpression
 | NOT expression                                 #notExpression
 | left=expression op=binary right=expression     #binaryExpression
 | LPAREN left=expression RPAREN LPAREN right=expression RPAREN #posExpression
 | bool                                           #boolExpression
 | IDENTIFIER                                     #identifierExpression
 ;

binary
 : AND | OR | XOR | XNOR | NAND | NOR
 ;

bool
 : TRUE | FALSE
 ;