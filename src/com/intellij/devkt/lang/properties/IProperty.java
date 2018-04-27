/*
 * Copyright 2000-2017 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package com.intellij.devkt.lang.properties;

import com.intellij.devkt.lang.properties.psi.PropertiesFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Iconable;
import org.jetbrains.kotlin.com.intellij.pom.Navigatable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;

/**
 * An interface representing a generic property. It can be a property inside xml file or a property inside .properties-file
 */
public interface IProperty extends Navigatable, Iconable {
	@Nullable String getKey();

	@Nullable String getValue();

	/**
	 * Returns the key with \n, \r, \t, \f and Unicode escape characters converted to their
	 * character equivalents.
	 *
	 * @return unescaped key, or null if no key is specified for this property.
	 */
	@Nullable String getUnescapedKey();

	/**
	 * @return text of comment preceding this property. Comment-start characters ('#' and '!') are stripped from the text.
	 */
	@Nullable String getDocCommentText();

	/**
	 * @return underlying psi element of property
	 */
	@NotNull PsiElement getPsiElement();
}
