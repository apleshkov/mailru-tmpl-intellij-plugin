package com.mailru.plugins.thtml.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author apleshkov
 */
public class TFileType extends LanguageFileType {

    public static final TFileType TMPL = new TFileType();

    public static final String DEFAULT_EXTENSION = "tmpl";

    protected TFileType() {
        super(new TLanguage());
    }

    @NotNull
    @Override
    public String getName() {
        return getLanguage().getID();
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Mail.ru TMPL";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/icons/tmpl_16x16.png");
    }

}
