package com.mailru.plugins.thtml.lang.parser.tools;

import com.intellij.psi.tree.IElementType;

/**
 * @author mushroom
 */

public class UnsupportedTokenException extends Exception {

    private final IElementType token;

    public UnsupportedTokenException(IElementType token) {
        super();
        this.token = token;
    }

    public IElementType getToken() {
        return token;
    }

}
