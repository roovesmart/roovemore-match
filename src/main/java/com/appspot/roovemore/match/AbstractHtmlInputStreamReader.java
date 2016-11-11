package com.appspot.roovemore.match;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractHtmlInputStreamReader {

	/** carriage return(0x0D(13)). */
	protected static final char CR = '\r';

	/** line feed code(0x0A(10)). */
	protected static final char LF = '\n';

	protected static boolean isCrLf(int c) {
		return (c == CR || c == LF);
	}

	protected static final String CHARSET_REGEX =
			"<meta.+?charset=(\"|'|)(.+?)(\"|').*?>";
	protected static final Pattern CHARSET_PATTERN = Pattern
		.compile(CHARSET_REGEX);

	@Deprecated
	public static class Logger{
		public static void info(Object o){
			System.out.println(o);
		}
	}
	@Deprecated
	public static Logger logger;

	@Deprecated
	public String matchString;

	protected String charsetName;

	/**
	 * あらかじめCharsetがわかっている場合は指定すること
	 * @param charsetName
	 */
	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public void read(InputStream inputStream) throws Exception {

		CharByte charByte = new CharByte();
		boolean isLineSeparator = false;

		for (;;) {

			int readChar = inputStream.read();
//			logger.info(String.format("%s => %#x\n", readChar, readChar));
//			logger.info("%s => %c\n", readChar, readChar);
//            logger.info("%s => %#X%n", readChar, readChar);

			// Streamの終わりに達して読み込むデータがない場合
			if (readChar < 0) {
				notStreamProcess();
				return;
			}

			// 1つ前の文字の読み込みが、改行の場合
			if (isLineSeparator) {

				// 続けて改行コードがあるかを調べる
				if (isCrLf(readChar)) {
					// 続けて改行コードがある
					// CR＋LFの場合

					String s = getLineStringAndSetCharset(charByte);
					boolean isReturnWhenMatchString = readLineProcess(s);
					if(isReturnWhenMatchString){
						return;
					}

					// 初期化
					charByte.reset();

					// 改行コードは、appendしない

				} else {
					// 続けて改行コードがない
					// CR or LFの場合

					String s = getLineStringAndSetCharset(charByte);
					boolean isReturnWhenMatchString = readLineProcess(s);
					if(isReturnWhenMatchString){
						return;
					}

					// 初期化
					charByte.reset();

					charByte.setChar(readChar);

				}
				isLineSeparator = false;

			} else if (isCrLf(readChar)) {
				// 改行コードがある場合
				isLineSeparator = true;
				// 改行コードは、appendしない
			} else {
				// 改行コードなしの文字の場合、改行コードが出るまで文字列を取ためる
				charByte.setChar(readChar);

			}
		}
	}

	protected static class CharByte{
		public int byteCnt = 0;
		public  byte[] b = new byte[1024 * 1024];
		public void reset(){
	        Arrays.fill(b, (byte)0);
			byteCnt=0;
		}
		public void setChar(int readChar){
			b[byteCnt] = (byte)readChar;
			byteCnt++;
		}
		public byte[] getBytes(){
			return Arrays.copyOf(b, b.length);
		}
	}

	protected String getCharset(String line) {
		String s = line.toLowerCase();
		if (s.indexOf("meta") != -1 && s.indexOf("charset") != -1) {
			Matcher m = CHARSET_PATTERN.matcher(line);
			if (m.find()) {
				return m.group(2);
			}
		}
		return null;
	}

	protected String getLineStringAndSetCharset(CharByte charByte)
			throws UnsupportedEncodingException{
		if(charsetName==null){
			String s = new String( charByte.getBytes(), "JISAutoDetect");
			String charset = getCharset(s);
			if(charset!=null){
				charsetName = charset;
			}
			return s;
		}
		return new String( charByte.getBytes(), charsetName);
	}

	protected abstract boolean readLineProcess(String line);

	protected void notStreamProcess(){ return; }

}
