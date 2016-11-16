package com.appspot.roovemore.match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleMatch extends AbstractHtmlInputStreamReader {

	public String matchString;

	/**
	 * find処理で最初にindexOfチェックに引っ掛ける文字列
	 */
	private String initCheckString = "data-tsukurepo-count";
	public void setInitCheckString(String initCheckString) {
		this.initCheckString = initCheckString;
	}

	/**
	 * find処理において、詳細にマッチングをかける際に使用するregex
	 */
	private String regex = ".+data-tsukurepo-count='(.+?)'";
	public void setRegex(String regex) {
		this.regex = regex;
	}
	/**
	 * regexのpattern
	 */
	private Pattern pattern = Pattern
			.compile(regex);

	private int groupNo = 1;
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	//TODO 未テスト
	@Override
	public boolean readLineProcess(String line){

		if (line.indexOf(initCheckString) != -1) {
			Matcher matcher =
				pattern.matcher(line);
			if (matcher.find()) {
				matchString = matcher.group(groupNo);
				return true;
			}
		}
		return true;
	}

}
