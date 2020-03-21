grammar Expressions;

expression : sum (SUM sum)*;
sum : (multiplication) (SUM multiplication)* | multiplication;
multiplication : NUM(MUL NUM)*;

MUL: '*';
SUM: '+';
INT : [0-9];
NUM : [1-9] INT+ | INT;
WS: [ \t\r\n]+ -> skip;