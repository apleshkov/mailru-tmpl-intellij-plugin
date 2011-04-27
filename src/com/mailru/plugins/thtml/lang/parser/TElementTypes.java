package com.mailru.plugins.thtml.lang.parser;

import com.intellij.psi.tree.IElementType;
import com.mailru.plugins.thtml.lang.psi.TElementType;

/**
 * @author apleshkov
 */
public interface TElementTypes {

    final IElementType SYNTAX_ERROR = new TElementType("SYNTAX_ERROR");
    final IElementType HTML = new TElementType("HTML");

    final IElementType SHORT_STATEMENT = new TElementType("SHORT_STATEMENT");

    final IElementType STRING = new TElementType("STRING");
    final IElementType NUMBER = new TElementType("NUMBER");

    final IElementType EXPRESSION = new TElementType("EXPRESSION");

    final IElementType OPERATOR = new TElementType("OPERATOR");

    final IElementType VARIABLE = new TElementType("VARIABLE");
    final IElementType FUNCTION = new TElementType("FUNCTION");
    final IElementType SETVARS = new TElementType("SETVARS");

    final IElementType FUNCTION_CALL = new TElementType("FUNCTION_CALL");


}
