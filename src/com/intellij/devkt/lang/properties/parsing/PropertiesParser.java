/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.devkt.lang.properties.parsing;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiParser;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/**
 * @author max
 */
public class PropertiesParser implements PsiParser {
	@NotNull
	public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
		doParse(root, builder);
		return builder.getTreeBuilt();
	}

	public void doParse(IElementType root, PsiBuilder builder) {
		final PsiBuilder.Marker rootMarker = builder.mark();
		final PsiBuilder.Marker propertiesList = builder.mark();
		while (!builder.eof()) {
			Parsing.parseProperty(builder);
		}
		propertiesList.done(PropertiesElementTypes.PROPERTIES_LIST);
		rootMarker.done(root);
	}
}
