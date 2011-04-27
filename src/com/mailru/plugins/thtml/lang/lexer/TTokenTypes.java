package com.mailru.plugins.thtml.lang.lexer;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.mailru.plugins.thtml.lang.psi.TElementType;

/**
 * @author apleshkov
 */
public interface TTokenTypes {

    final IElementType UNKNOWN_SYMBOL = new TElementType("UNKNOWN_SYMBOL");

    final IElementType DSHARP = new TElementType("DSHARP");
    final IElementType STATEMENT_OPENING_TAG = new TElementType("STATEMENT_OPENING_TAG"); // <!--
    final IElementType STATEMENT_CLOSING_TAG = new TElementType("STATEMENT_CLOSING_TAG"); // -->

    final IElementType WHITESPACE = new TElementType("WHITESPACE");
    final IElementType HTML = new TElementType("HTML");

    final IElementType C_IF = new TElementType("IF");
    final IElementType C_IFNOT = new TElementType("IFNOT");
    final IElementType C_IFDEF = new TElementType("IFDEF");
    final IElementType C_IFNOTDEF = new TElementType("IFNOTDEF");

    final IElementType C_ENDIF = new TElementType("/IF");
    final IElementType C_ENDIFNOT = new TElementType("/IFNOT");
    final IElementType C_ENDIFDEF = new TElementType("/IFDEF");
    final IElementType C_ENDIFNOTDEF = new TElementType("/IFNOTDEF");

    final IElementType C_ELSE = new TElementType("ELSE");
    final IElementType C_ELSEIF = new TElementType("ELSEIF");
    final IElementType C_ELSEIFDEF = new TElementType("ELSEIFDEF");
    final IElementType C_ELSEIFNOT = new TElementType("ELSEIFNOT");
    final IElementType C_ELSEIFNOTDEF = new TElementType("ELSEIFNOTDEF");

    final IElementType OP_AND = new TElementType("AND"); // &&
    final IElementType OP_OR = new TElementType("OR"); // ||
    final IElementType OP_NOT = new TElementType("NOT"); // !
    final IElementType OP_COMMA = new TElementType("COMMA"); // ,
    final IElementType OP_PLUS = new TElementType("PLUS"); // +
    final IElementType OP_MINUS = new TElementType("MINUS"); // -
    final IElementType OP_ASSIGN = new TElementType("ASSIGN"); // =

    final IElementType L_FOR = new TElementType("FOR");
    final IElementType L_ENDFOR = new TElementType("/FOR");
    final IElementType L_CONTINUE = new TElementType("CONTINUE");
    final IElementType L_BREAK = new TElementType("BREAK");

    final IElementType LPAREN = new TElementType("LPAREN"); // (
    final IElementType RPAREN = new TElementType("RPAREN"); // )

    final IElementType QLPAREN = new TElementType("QLPAREN"); // (
    final IElementType QRPAREN = new TElementType("QRPAREN"); // )

    final IElementType NUMBER = new TElementType("NUMBER");

    final IElementType VAR_NAME = new TElementType("VAR_NAME");
    final IElementType VAR_VALUE = new TElementType("VAR_VALUE"); // ##var##

    final IElementType FUNC_NAME = new TElementType("FUNC_NAME"); // myfunc(...)

    final IElementType NESTED_FUNC_NAME = new TElementType("NESTED_FUNC_NAME"); // a(##b\(...\)##)

    final IElementType SETVARS = new TElementType("SetVars");


    final IElementType ARRAY_FIELD = new TElementType("ARRAY_FIELD"); // ##.field2## or ##ARR_1_field3##

    final TokenSet OPERATORS = TokenSet.create(
            OP_AND, OP_OR, OP_NOT,
            OP_COMMA,
            OP_PLUS, OP_MINUS,
            OP_ASSIGN
    );

    final TokenSet EXPRESSION_OPERATORS = TokenSet.create(
            OP_PLUS, OP_MINUS
    );

    final TokenSet COND_OPERATORS = TokenSet.create(
            OP_AND, OP_OR, OP_NOT
    );

    final TokenSet COND_KEYWORDS = TokenSet.create(
            C_IF, C_ELSE, C_IFNOT, C_IFNOTDEF, C_ELSEIF,
            C_ELSEIFDEF, C_ELSEIFNOT, C_ELSEIFNOTDEF
    );

    final TokenSet LOOP_KEYWORDS = TokenSet.create(
            L_FOR, L_CONTINUE, L_BREAK
    );

}
