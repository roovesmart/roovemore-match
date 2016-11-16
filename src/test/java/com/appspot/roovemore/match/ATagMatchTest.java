package com.appspot.roovemore.match;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ATagMatchTest extends AbstractTest {


	public void test_1() throws Exception{

		String testDataArray[][] =
			{
				{"http://www.yahoo.co.jp/","utf-8"},
			};

		for(String[] testData : testDataArray){

			InputStream is = getInputStreamWithoutClose(testData[0]);
			ATagMatch a = new ATagMatch();
			a.read(is);

			log.info("\n" +
					"================================" +
					"\n" +
					"result" +
					"\n" +
					"================================"
					);

			for(AtagResult r : a.retList){
				log.info(
						"\n" +
						r.fullATag +
						"\n" +
						r.innerATag +
						"\n" +
						r.link
						);
			}

//			assertEquals(testData[1], a.matchString);

		}

	}

	public void test_2() throws Exception{

		List<String> list = new ArrayList<String>();
		list.add("<a href=\"http://1111\" class=\"username\">a111</a>ああああ<a href=\"http://2222\" class=\"username\">a222</a>");
		list.add("<a href=\"http://3333\" class=\"username\">a333</a>");
		list.add("<a href=\"http://4444\">");
		list.add("a444");
		list.add("</a>");
		list.add("<a href=\"http://5555\">a555");
		list.add("</a>");
		list.add("<a href=\"http://6666\">a666");
		list.add("</a><a href=\"http://7777\">a777</a>");
		list.add("<div>a href bbbbbbbbbbb</div><a href=\"http://8888\">a888</a>");

		ATagMatch a = new ATagMatch();

		for(String s: list){
			a.readLineProcess(s);
		}

		log.info("\n" +
				"================================" +
				"\n" +
				"result" +
				"\n" +
				"================================"
				);

		for(AtagResult r : a.retList){
			log.info(
					"\n" +
					r.fullATag +
					"\n" +
					r.innerATag +
					"\n" +
					r.link
					);
		}

//			assertEquals(testData[1], a.matchString);

	}


}
