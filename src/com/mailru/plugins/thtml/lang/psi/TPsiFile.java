package com.mailru.plugins.thtml.lang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.mailru.plugins.thtml.lang.TFileType;
import org.jetbrains.annotations.NotNull;

/**
 * @author apleshkov
 */
public class TPsiFile extends PsiFileBase {

    public TPsiFile(FileViewProvider viewProvider) {
        super(viewProvider, TFileType.TMPL.getLanguage());
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return TFileType.TMPL;
    }
}
