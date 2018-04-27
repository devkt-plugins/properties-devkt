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

import com.intellij.devkt.lang.properties.PropertiesFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author cdr
 */
public class PropertiesElementFactory {
	@NotNull
	public static PropertiesFile createPropertiesFile(@NotNull Project project, Properties properties, String fileName) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			properties.store(stream, "");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		@NonNls String filename = fileName + "." + PropertiesFileType.INSTANCE.getDefaultExtension();
		return (PropertiesFile) PsiFileFactory.getInstance(project).createFileFromText(filename,
				PropertiesFileType.INSTANCE,
				stream.toString());
	}

	@Deprecated
	public static String escapeValue(String value) {
		return escapeValue(value, '=');
	}

	public static String escapeValue(String value, char delimiter) {
		return PropertiesResourceBundleUtil.fromValueEditorToPropertyValue(value, delimiter);
	}
}
