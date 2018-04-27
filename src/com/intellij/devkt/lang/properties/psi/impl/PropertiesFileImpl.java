/*
 * Copyright 2000-2017 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package com.intellij.devkt.lang.properties.psi.impl;

import com.intellij.devkt.lang.properties.IProperty;
import com.intellij.devkt.lang.properties.PropertiesFileType;
import com.intellij.devkt.lang.properties.PropertiesLanguage;
import com.intellij.devkt.lang.properties.psi.PropertiesFile;
import com.intellij.devkt.lang.properties.psi.PropertiesList;
import com.intellij.devkt.lang.properties.psi.Property;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.com.intellij.extapi.psi.PsiFileBase;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

import java.util.Collections;
import java.util.List;

public class PropertiesFileImpl extends PsiFileBase implements PropertiesFile {

	public PropertiesFileImpl(FileViewProvider viewProvider) {
		super(viewProvider, PropertiesLanguage.INSTANCE);
	}

	@Override
	@NotNull
	public FileType getFileType() {
		return PropertiesFileType.INSTANCE;
	}

	@NonNls
	public String toString() {
		return "Properties file:" + getName();
	}

	@Override
	@NotNull
	public List<IProperty> getProperties() {
		final PropertiesList propertiesList = PsiTreeUtil.getStubChildOfType(this, PropertiesList.class);
		if (propertiesList == null) return Collections.emptyList();
		return Collections.unmodifiableList(PsiTreeUtil.getStubChildrenOfTypeAsList(propertiesList, Property.class));
	}

	@Override
	public PsiElement add(@NotNull PsiElement element) throws IncorrectOperationException {
		if (element instanceof Property) {
			throw new IncorrectOperationException("Use addProperty() instead");
		}
		return super.add(element);
	}

}
