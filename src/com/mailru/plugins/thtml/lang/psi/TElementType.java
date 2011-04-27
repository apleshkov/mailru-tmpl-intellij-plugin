package com.mailru.plugins.thtml.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.mailru.plugins.thtml.lang.TFileType;

/**
 * @author apleshkov
 */
public class TElementType extends IElementType {

    public TElementType(@org.jetbrains.annotations.NotNull String debugName) {
        super(debugName, TFileType.TMPL.getLanguage());
    }

    @Override
    public String toString() {
        return "T:" + super.toString();
    }
}
