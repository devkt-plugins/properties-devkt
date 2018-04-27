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

package com.intellij.devkt.lang.properties.psi;

import com.intellij.devkt.lang.properties.IProperty;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

import java.util.List;
import java.util.Map;

/**
 * An interface representing a properties file.
 */
public interface PropertiesFile {

	@NotNull PsiFile getContainingFile();

	/**
	 * @return All properties found in this file.
	 */
	@NotNull List<IProperty> getProperties();

	String getName();

	VirtualFile getVirtualFile();

	/**
	 * @return containing directory for the corresponding properties file
	 */
	PsiDirectory getParent();

	Project getProject();

	String getText();

}
