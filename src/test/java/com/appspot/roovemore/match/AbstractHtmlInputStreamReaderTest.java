package com.appspot.roovemore.match;

import java.io.InputStream;
import java.util.regex.Matcher;

public class AbstractHtmlInputStreamReaderTest extends AbstractTest {

	public void test_1() throws Exception{

		String testDataArray[][] =
			{
				{"http://www.yahoo.co.jp/","utf-8"},
			};

		for(String[] testData : testDataArray){

			log.info("================================" );
			log.info("test-data = " + testData[0] );
			log.info("================================" );

			InputStream is = getInputStreamWithoutClose(testData[0]);
			CharsetUtil a = new CharsetUtil();
			TestAbstractHtmlInputStreamReader t = new TestAbstractHtmlInputStreamReader();
			t.read(is);

//			assertEquals(testData[1], a.matchString);

		}

	}

	public void test_11() throws Exception{

		String testDataArray[][] =
			{
				{"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=shift_jis\">", "shift_jis"},
				{"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=shift_jis\">" , "shift_jis"},
				{"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=\"shift_jis\">", "shift_jis"},
				{"<meta charset=\"utf-8\"/>", "utf-8"}
			};

		for(String[] testData : testDataArray){

			log.info("================================" );
			log.info("test-data = " + testData[0] );
			log.info("================================" );

			Matcher m = AbstractHtmlInputStreamReader.CHARSET_PATTERN.matcher(testData[0]);
			if (m.find()) {
				log.info(m.group(1));
				log.info(m.group(2));
				assertEquals(testData[1], m.group(2));

			}else{
				log.info("matching error");
				assertFalse(true);

			}

		}
	}

	public static class TestAbstractHtmlInputStreamReader extends AbstractHtmlInputStreamReader{

		@Override
		protected boolean readLineProcess(String line) {
			return false;
		}

	}




}
