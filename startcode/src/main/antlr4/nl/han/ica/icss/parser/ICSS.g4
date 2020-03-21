grammar ICSS;

//--- LEXER: ---
// IF support:
IF: 'if';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;

//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';

//--- PARSER: ---

/**
 * Level 0
 * Contains parserRules for at the very least css
 */
stylesheet: (variableAssignment | stylerule)* EOF;

//stylerule: selector OPEN_BRACE declaration* CLOSE_BRACE; // override in Level 3
selector: (classSelector | tagSelector | idSelector);
//declaration: LOWER_IDENT COLON literal SEMICOLON; // override in Level 1

literal: bool | pixel | percent | scalar | color;

bool: TRUE | FALSE;
pixel: PIXELSIZE;
percent: PERCENTAGE;
scalar: SCALAR;
color: COLOR;

tagSelector: LOWER_IDENT;
classSelector: CLASS_IDENT;
idSelector: ID_IDENT;

/**
 * level 1
 * Includes level 0 and expands with variables
 */
//declaration: LOWER_IDENT COLON (literal | variableReference) SEMICOLON; // override in Level 2
variableAssignment: variableReference ASSIGNMENT_OPERATOR literal SEMICOLON;
variableReference: CAPITAL_IDENT;

/**
 * level 2
 * Includes level 1 and expands with operations
 */
declaration: LOWER_IDENT COLON (literal | variableReference | operation) SEMICOLON;
operation: (variableReference | literal) ((PLUS | MIN | MUL) (variableReference |literal | operation));

/**
 * Level 3
 * Includes level 2 and expands with if statements
 */
ifClause: IF BOX_BRACKET_OPEN condition BOX_BRACKET_CLOSE OPEN_BRACE (ifClause | declaration)+ CLOSE_BRACE;
condition: bool | variableReference;
stylerule: selector OPEN_BRACE (ifClause | declaration)* CLOSE_BRACE;