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

import com.intellij.devkt.lang.properties.PropertiesLanguage;
import com.intellij.devkt.lang.properties.psi.PropertiesElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;

/**
 * @author max
 */
public interface PropertiesElementTypes {
	IFileElementType FILE = new IFileElementType(PropertiesLanguage.INSTANCE);
	IElementType PROPERTY = new PropertiesElementType("PROPERTY");
	IElementType PROPERTIES_LIST = new PropertiesElementType("PROPERTY_LIST");
}
