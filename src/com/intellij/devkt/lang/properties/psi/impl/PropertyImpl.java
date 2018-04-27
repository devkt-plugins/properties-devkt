// Copyright 2000-2017 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.devkt.lang.properties.psi.impl;

import com.intellij.devkt.lang.properties.psi.PropertiesTokenTypes;
import com.intellij.devkt.lang.properties.psi.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/**
 * @author max
 */
public class PropertyImpl extends ASTWrapperPsiElement implements Property {
	public PropertyImpl(@NotNull ASTNode node) {
		super(node);
	}

	public String toString() {
		return "Property{ key = " + getKey() + ", value = " + getValue() + "}";
	}

	@Override
	public String getName() {
		return getUnescapedKey();
	}

	@Override
	public String getKey() {
		final ASTNode node = getKeyNode();
		if (node == null) {
			return null;
		}
		return node.getText();
	}

	@Nullable
	public ASTNode getKeyNode() {
		return getNode().findChildByType(PropertiesTokenTypes.KEY_CHARACTERS);
	}

	@Nullable
	public ASTNode getValueNode() {
		return getNode().findChildByType(PropertiesTokenTypes.VALUE_CHARACTERS);
	}

	@Override
	public String getValue() {
		final ASTNode node = getValueNode();
		if (node == null) {
			return "";
		}
		return node.getText();
	}


	public static String unescape(String s) {
		if (s == null) return null;
		StringBuilder sb = new StringBuilder();
		parseCharacters(s, sb, null);
		return sb.toString();
	}

	public static boolean parseCharacters(String s, StringBuilder outChars, @Nullable int[] sourceOffsets) {
		assert sourceOffsets == null || sourceOffsets.length == s.length() + 1;
		int off = 0;
		int len = s.length();

		boolean result = true;
		final int outOffset = outChars.length();
		while (off < len) {
			char aChar = s.charAt(off++);
			if (sourceOffsets != null) {
				sourceOffsets[outChars.length() - outOffset] = off - 1;
				sourceOffsets[outChars.length() + 1 - outOffset] = off;
			}

			if (aChar == '\\') {
				aChar = s.charAt(off++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					boolean error = false;
					for (int i = 0; i < 4 && off < s.length(); i++) {
						aChar = s.charAt(off++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								outChars.append("\\u");
								int start = off - i - 1;
								int end = start + 4 < s.length() ? start + 4 : s.length();
								outChars.append(s, start, end);
								i = 4;
								error = true;
								off = end;
								break;
							//throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					if (!error) {
						outChars.append((char) value);
					} else {
						result = false;
					}
				} else if (aChar == '\n') {
					// escaped linebreak: skip whitespace in the beginning of next line
					while (off < len && (s.charAt(off) == ' ' || s.charAt(off) == '\t')) {
						off++;
					}
				} else if (aChar == 't') {
					outChars.append('\t');
				} else if (aChar == 'r') {
					outChars.append('\r');
				} else if (aChar == 'n') {
					outChars.append('\n');
				} else if (aChar == 'f') {
					outChars.append('\f');
				} else {
					outChars.append(aChar);
				}
			} else {
				outChars.append(aChar);
			}
			if (sourceOffsets != null) {
				sourceOffsets[outChars.length() - outOffset] = off;
			}
		}
		return result;
	}

	@Override
	@Nullable
	public String getUnescapedKey() {
		return unescape(getKey());
	}

	@Override
	public void delete() throws IncorrectOperationException {
		final ASTNode parentNode = getParent().getNode();
		assert parentNode != null;

		ASTNode node = getNode();
		ASTNode prev = node.getTreePrev();
		ASTNode next = node.getTreeNext();
		parentNode.removeChild(node);
		if ((prev == null || prev.getElementType() == TokenType.WHITE_SPACE) && next != null && next.getElementType() == TokenType.WHITE_SPACE) {
			parentNode.removeChild(next);
		}
	}

	@Override
	public String getDocCommentText() {
		StringBuilder text = new StringBuilder();
		for (PsiElement doc = getPrevSibling(); doc != null; doc = doc.getPrevSibling()) {
			if (doc instanceof PsiWhiteSpace) {
				doc = doc.getPrevSibling();
			}
			if (doc instanceof PsiComment) {
				if (text.length() != 0) text.insert(0, "\n");
				String comment = doc.getText();
				text.insert(0, comment);
			} else {
				break;
			}
		}
		if (text.length() == 0) return null;
		return text.toString();
	}

	@NotNull
	@Override
	public PsiElement getPsiElement() {
		return this;
	}

	public char getKeyValueDelimiter() {
		final PsiElement delimiter = findChildByType(PropertiesTokenTypes.KEY_VALUE_SEPARATOR);
		if (delimiter == null) {
			return ' ';
		}
		final String text = delimiter.getText();
		return text.charAt(0);
	}

}
