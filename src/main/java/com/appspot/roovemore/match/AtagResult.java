package com.appspot.roovemore.match;

public class AtagResult extends Result{

		public String innerATag;

		public String getInnerATag() {
			return innerATag;
		}

		public String fullATag;

		public String getFullATag() {
			return fullATag;
		}

		public String link;

		public String getLink() {
			return link;
		}

		/**
		 * <pre>
		 * 文字列絞り込みオプションが指定された場合、
		 * 該当hrefが絞り込み文字列に部分一致するか
		 * </pre>
		 */
		public boolean hrefFilterFlg = true;

		/**
		 * <pre>
		 * http絞り込みオプションが指定された場合、
		 * 該当hrefにhttpが含まれるか
		 * </pre>
		 */
		public boolean hrefHttpFlg = true;

		public boolean isExtract() {
			return hrefFilterFlg && hrefHttpFlg;
		}

}
