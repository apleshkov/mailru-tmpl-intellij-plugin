package com.mailru.plugins.thtml.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%unicode
%class TFlexLexer
%implements FlexLexer
%public

%function advance
%type IElementType

%{

    private int previousState = YYINITIAL;

    private void saveState(int state) {
        previousState = state;
    }

%}

%eof{

    return;

%eof}

NEWLINE = (\r|\n|\r\n)
WHITESPACE = [ \t]+

GTZ_INTEGER = [1-9][0-9]*
INTEGER = {GTZ_INTEGER} | 0
FLOAT = [0-9]*"."[0-9]+
NUM = ({INTEGER} | {FLOAT})

LABEL = [a-zA-Z_][a-zA-Z_0-9]*

ANY_CHAR = (.|[\n])

%x IN_SHORT_STATEMENT
%x IN_STATEMENT
%x IN_SETVARS
%x IN_FUNCTION_ARGUMENTS
%x IN_NESTED_FUNCTION_CALL

%%

<IN_SHORT_STATEMENT, IN_STATEMENT, IN_SETVARS, IN_FUNCTION_ARGUMENTS> {
    {NUM}{LABEL}                { return TTokenTypes.UNKNOWN_SYMBOL; }
}

<IN_SHORT_STATEMENT, IN_STATEMENT> {
    "("                         { return TTokenTypes.LPAREN; }
    ")"                         { return TTokenTypes.RPAREN; }
}

<IN_FUNCTION_ARGUMENTS, IN_STATEMENT, IN_SETVARS, IN_NESTED_FUNCTION_CALL> {
    {NUM}                       { return TTokenTypes.NUMBER; }
}

<IN_FUNCTION_ARGUMENTS, IN_SETVARS, IN_NESTED_FUNCTION_CALL> {
    {LABEL}                     { return TTokenTypes.VAR_NAME; }
}

<IN_SETVARS> {
    "("                         { return TTokenTypes.LPAREN; }
    ")"                         { yybegin(IN_SHORT_STATEMENT); return TTokenTypes.RPAREN; }
    "="                         { return TTokenTypes.OP_ASSIGN; }
}

<IN_FUNCTION_ARGUMENTS, IN_NESTED_FUNCTION_CALL> {
    "+"                         { return TTokenTypes.OP_PLUS; }
    "-"                         { return TTokenTypes.OP_MINUS; }
    ","                         { return TTokenTypes.OP_COMMA; }
}

<IN_FUNCTION_ARGUMENTS> {
    "("                         { return TTokenTypes.LPAREN; }
    ")"                         { yybegin(previousState); return TTokenTypes.RPAREN; }
}

<IN_FUNCTION_ARGUMENTS, IN_SETVARS, IN_NESTED_FUNCTION_CALL> {
    "##"{LABEL}"##"             { return TTokenTypes.VAR_VALUE; }
}

<IN_SHORT_STATEMENT> {
    "SetVars("                  { yybegin(IN_SETVARS); yypushback(1); return TTokenTypes.SETVARS; }

    {LABEL}"_"{GTZ_INTEGER}"_field"{GTZ_INTEGER} |
    ".field"{GTZ_INTEGER}       { return TTokenTypes.ARRAY_FIELD; }
}

<IN_STATEMENT> {
    "IF"                        { return TTokenTypes.C_IF; }
    "IFNOT"                     { return TTokenTypes.C_IFNOT; }
    "IFDEF"                     { return TTokenTypes.C_IFDEF; }
    "IFNOTDEF"                  { return TTokenTypes.C_IFNOTDEF; }

    "/IF"                       { return TTokenTypes.C_ENDIF; }
    "/IFNOT"                    { return TTokenTypes.C_ENDIFNOT; }
    "/IFDEF"                    { return TTokenTypes.C_ENDIFDEF; }
    "/IFNOTDEF"                 { return TTokenTypes.C_ENDIFNOTDEF; }

    "ELSE"                      { return TTokenTypes.C_ELSE; }
    "ELSEIF"                    { return TTokenTypes.C_ELSEIF; }
    "ELSEIFDEF"                 { return TTokenTypes.C_ELSEIFDEF; }
    "ELSEIFNOT"                 { return TTokenTypes.C_ELSEIFNOT; }
    "ELSEIFNOTDEF"              { return TTokenTypes.C_ELSEIFNOTDEF; }

    "&&"                        { return TTokenTypes.OP_AND; }
    "||"                        { return TTokenTypes.OP_OR; }
    "!"                         { return TTokenTypes.OP_NOT; }

    "FOR"                       { return TTokenTypes.L_FOR; }
    "/FOR"                      { return TTokenTypes.L_ENDFOR; }
    "CONTINUE"                  { return TTokenTypes.L_CONTINUE; }
    "BREAK"                     { return TTokenTypes.L_BREAK; }
}

<IN_SHORT_STATEMENT, IN_STATEMENT> {
    {LABEL}                     { return TTokenTypes.VAR_NAME; }

    {LABEL}"(" |
    {LABEL}"/"{LABEL}"("        { yypushback(1); saveState(yystate()); yybegin(IN_FUNCTION_ARGUMENTS); return TTokenTypes.FUNC_NAME; }
}

<IN_NESTED_FUNCTION_CALL> {
    {LABEL}"\\(" |
    {LABEL}"/"{LABEL}"\\("      { yypushback(2); return TTokenTypes.NESTED_FUNC_NAME; }

    "\\("                       { return TTokenTypes.QLPAREN; }
    "\\)"                       { return TTokenTypes.QRPAREN; }

    "##"                        { yybegin(previousState); return TTokenTypes.DSHARP; }
}

<IN_SETVARS, IN_FUNCTION_ARGUMENTS> {
    "##"                        { saveState(yystate()); yybegin(IN_NESTED_FUNCTION_CALL); return TTokenTypes.DSHARP; }
}

<YYINITIAL> (([^<#]|"<"[^!]){1,200}) | "<" | "<!" | "<!-" | "<!---" | "#" | "###" {
    return TTokenTypes.HTML;
}

<IN_SHORT_STATEMENT> "##" {
    yybegin(previousState);
    return TTokenTypes.DSHARP;
}

<YYINITIAL, IN_STATEMENT> "##" {
    saveState(yystate());
    yybegin(IN_SHORT_STATEMENT);
    return TTokenTypes.DSHARP;
}

<YYINITIAL> "<!--" {
    yybegin(IN_STATEMENT);
    return TTokenTypes.STATEMENT_OPENING_TAG;
}

<IN_SHORT_STATEMENT, IN_STATEMENT> {
    {NEWLINE}                   { yybegin(YYINITIAL); return TTokenTypes.HTML; }
}

<IN_STATEMENT, IN_FUNCTION_ARGUMENTS, IN_SETVARS, IN_NESTED_FUNCTION_CALL> {WHITESPACE} {
    return TTokenTypes.WHITESPACE;
}

<IN_STATEMENT> ("--->"|"-->"){NEWLINE}? {
    yybegin(YYINITIAL);
    return TTokenTypes.STATEMENT_CLOSING_TAG;
}

<IN_SHORT_STATEMENT, IN_STATEMENT, IN_SETVARS, IN_FUNCTION_ARGUMENTS, IN_NESTED_FUNCTION_CALL> {ANY_CHAR} {
    return TTokenTypes.UNKNOWN_SYMBOL;
}