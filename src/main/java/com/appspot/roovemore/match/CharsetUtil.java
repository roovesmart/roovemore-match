package com.appspot.roovemore.match;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharsetUtil {

	protected static final String CHARSET_REGEX2 = ".*?charset=(.+?) .*";
	protected static final Pattern CHARSET_PATTERN2 = Pattern
		.compile(CHARSET_REGEX2);

	/**
	 * charsetを取得します。
	 *
	 * @param responseCharset
	 * 				キャラセットがあらかじめわかっている場合は、ここに指定
	 * @param responseContentType
	 * 				レスポンスのコンテントタイプを指定（そこからキャラセットを抽出）
	 * @return charset
	 * 				キャラセット
	 * @throws Exception
	 */
	public static String getCharsetFromHtml (
			String responseCharset,
			String responseContentType
			) {

		String charset = null;

		if(!StringUtil.isEmpty(responseCharset)){
			// charsetが固定で設定されている場合は、その値を使用する。
			return responseCharset;
		}

		// 固定で設定されていない場合、
		// まずは、レスポンスヘッダーのコンテントタイプにcharsetが設定されているかを確認する。
		charset = getCharsetFromContentType(responseContentType);

		return charset;
	}

	public static boolean sameCharset(String targetStr, String charset) {

		String s = null;
		try {
			s = new String(targetStr.getBytes(charset), charset);
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		return targetStr.equals(s);
	}

	public static String getCharsetFromContentType(String contentType) {

		String s = contentType.toLowerCase();

		if (s.indexOf("charset") != -1) {

			Matcher m = CHARSET_PATTERN2.matcher(s);
			if (m.find()) {
				return m.group(1);
			}
		}
		return null;
	}

}
