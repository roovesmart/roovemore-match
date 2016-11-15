package com.appspot.roovemore.match;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ATagMatch extends AbstractHtmlInputStreamReader{

	private final Log logger = LogFactory.getLog(getClass());

//	protected String responseContentType = null;
//
//	public void setResponseContentType(String responseContentType) {
//		this.responseContentType = responseContentType;
//	}

//	protected String responseCharset = null;
//
//	public void setResponseCharset(String responseCharset) {
//		this.responseCharset = responseCharset;
//	}

	/**
	 * 絞り込みパラメータオプション(任意の値)
	 * 抽出結果をこのパラメータで絞りんだ状態で返却する。
	 */
	protected String hrefFilterOption = null;

	public void setHrefFilterOption(String hrefFilterOption) {
		this.hrefFilterOption = hrefFilterOption;
	}

	/**
	 * 絞り込みパラメータオプション(hrefにhttpがあるか)
	 * 抽出結果のhrefにhttpがあるもののみを返却する。
	 */
	protected boolean httpFilterOption = true;

	public void setHttpFilterOption(boolean httpFilterOption) {
		this.httpFilterOption = httpFilterOption;
	}

	/**
	 * Aタグ開始抽出用正規表現
	 */
	private static final String A_TAG_START_REGEX = "<a.+?href=\"(.+?)\".*?>";
	private static final Pattern A_TAG_START_PATTERN = Pattern
			.compile(A_TAG_START_REGEX);

	/**
	 * Aタグ終了抽出用正規表現
	 */
	private static final String A_TAG_END_REGEX = "(.*?)</a>?";
	private static final Pattern A_TAG_END_PATTERN = Pattern
		.compile(A_TAG_END_REGEX);


	List<AtagResult> retList = new ArrayList<AtagResult>();
	AtagResult ret = new AtagResult();
	boolean isAStart = false;
	StringBuilder innerATag = new StringBuilder();
	StringBuilder fullATag = new StringBuilder();

	String line = null;
	int lineCounter = 0;

	@Override
	public boolean readLineProcess(String line){

		lineCounter++;

		while (true) {

			if(logger.isDebugEnabled()){
				logger.debug("[ while ]" + "isAStart = " + isAStart +  ":" + lineCounter );
			}

			if (StringUtil.isEmpty(line)) {
				// 処理終了
				return true;
			}

			// =============================================================
			// A開始タグがあるかをチェックする。
			// =============================================================

			// A開始タグ処理中じゃない場合、
			if (!isAStart) {

				// TODO Aとhrefが改行されていたら取得できない・・・

				// A開始タグがあるか、事前にindexOfで事前確認
				if (line.indexOf("<a ") != -1) {

					// A開始タグがあるかをチェックする。
					Matcher aStartMatcher =
						A_TAG_START_PATTERN.matcher(line);

					if (aStartMatcher.find()) {

						String link = aStartMatcher.group(1);

						isAStart = true;

						if(logger.isDebugEnabled()){
							logger.debug("===== exist a tag start ====");
							logger.debug("aStartMatcher.group() = " + aStartMatcher.group());
							logger.debug("aStartMatcher.group(1) = " + aStartMatcher.group(1));
							logger.debug("aStartMatcher.start() = " + String.valueOf( aStartMatcher.start()));
							logger.debug("aStartMatcher.end() = " +  String.valueOf( aStartMatcher.end() ));
						}

						// hrefの内容を保存する。
						ret = new AtagResult();
						ret.link = link;

						// hrefにhttpがあるかをチェックする。
						if (httpFilterOption) {
							if (link.indexOf("http") == -1) {
								ret.hrefHttpFlg = false;
							}
						}

						// 絞り込みパラメータオプションがあった場合は、絞り込み文字列が含まれているかをチェックする。
						if (!StringUtil.isEmpty(hrefFilterOption)
							&& link.indexOf(hrefFilterOption) == -1) {

							ret.hrefFilterFlg = false;
						}

						fullATag.append(aStartMatcher.group().trim());

						// A開始タグ箇所を除却する。
						line = line.substring(aStartMatcher.end(), line.length());

						continue;

					}
				}

			}

			// =============================================================
			// A終了タグがあるかをチェックする
			// =============================================================

			// A開始タグ処理中の場合、
			if (isAStart) {

				// A終了タグがあるか、事前にindexOfで事前確認
				if (line.indexOf("</a>") != -1) {

					// A終了タグがあるかをチェックする。
					Matcher aEndMatcher =
						A_TAG_END_PATTERN.matcher(line);
					if (aEndMatcher.find()) {
						// A終了タグがある場合

						// このAタグについては要素がすべて出そろったため
						// 結果オブジェクトに格納し、次のAタグチェックを実施する。

						if(logger.isDebugEnabled()){
							logger.debug("===== exist a tag end ====");
							logger.info("aEndMatcher.group() = " + aEndMatcher.group());
							logger.info("aEndMatcher.group(1) = " + aEndMatcher.group(1));
							logger.info("aEndMatcher.start() = " + String.valueOf( aEndMatcher.start()) );
							logger.info("aEndMatcher.end() = " +  String.valueOf( aEndMatcher.end() ));
						}

						// Aタグ全体を保存する。
						fullATag.append(aEndMatcher.group().trim());
						ret.fullATag = fullATag.toString();

						// Aタグ内の内容を保存する。
						innerATag.append(aEndMatcher.group(1));
						ret.innerATag = innerATag.toString();
						// ret.innerATag = aEndMatcher.group(1);

						// オプションが指定されていた場合
						// 絞り込み文字列に合致かつ、http絞り込みに合致する場合のみ、addする。
						if (ret.isExtract()) {
							retList.add(ret);
						}

						// このAタグについての処理が終わったためリセットする。
						ret = new AtagResult();
						isAStart = false;
						innerATag.setLength(0);
						fullATag.setLength(0);

						// A終了タグ箇所を除却する。
						line = line.substring(aEndMatcher.end(), line.length());

						continue;

					}
				} else {

					// A終了タグがない場合

					// 処理中の行の内容を取ためる
					// tempBeforeLine = line.trim();
					// tempBeforeLine.append(line.trim());
					fullATag.append(line.trim());

					innerATag.append(line.trim());

					// 次の行の処理に進む
					return false;

				}

			} else {

				// 次の行の処理に進む
				return false;

			}

		} // end while

	}

}
