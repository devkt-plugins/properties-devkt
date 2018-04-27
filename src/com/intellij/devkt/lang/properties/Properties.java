package com.intellij.devkt.lang.properties;

import com.intellij.devkt.lang.properties.parsing.PropertiesParserDefinition;
import com.intellij.devkt.lang.properties.psi.PropertiesTokenTypes;
import org.ice1000.devkt.ASTToken;
import org.ice1000.devkt.openapi.ColorScheme;
import org.ice1000.devkt.openapi.ExtendedDevKtLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

import javax.swing.*;

public class Properties<T> extends ExtendedDevKtLanguage<T> {
	public Properties() {
		super(PropertiesLanguage.INSTANCE, new PropertiesParserDefinition());
	}

	@Override
	public @NotNull String getLineCommentStart() {
		return "#";
	}

	@Override
	public @NotNull Icon getIcon() {
		return PropretiesIcons.FILE;
	}

	@Override
	public boolean invokeAutoPopup(@NotNull ASTToken currentElement, @NotNull String inputString) {
		return inputString.equals("=") || inputString.equals(":");
	}

	@Override
	public boolean satisfies(@NotNull String fileName) {
		return fileName.endsWith(PropertiesFileType.DOT_DEFAULT_EXTENSION);
	}

	@Nullable
	@Override
	public T attributesOf(@NotNull IElementType type, @NotNull ColorScheme<? extends T> colorScheme) {
		if (type == PropertiesTokenTypes.BAD_CHARACTER) return super.attributesOf(TokenType.BAD_CHARACTER, colorScheme);
		else if (type == PropertiesTokenTypes.END_OF_LINE_COMMENT) return colorScheme.getLineComments();
		else if (type == PropertiesTokenTypes.KEY_CHARACTERS) return colorScheme.getKeywords();
		else if (type == PropertiesTokenTypes.VALUE_CHARACTERS) return colorScheme.getIdentifiers();
		else if (type == PropertiesTokenTypes.KEY_VALUE_SEPARATOR) return colorScheme.getOperators();
		else return super.attributesOf(type, colorScheme);
	}
}
