package com.appspot.roovemore.match;

public class AtagRequestControllerTest extends AbstractTest {

	public void test_0() throws Exception{

		AtagRequestController r = new AtagRequestController();
		r.requestUrl = "http://www.yahoo.co.jp/";
		r.request();

		for(AtagResult a : r.retList){
		log.info(
				"\n" +
				a.fullATag +
				"\n" +
				a.innerATag +
				"\n" +
				a.link
				);
		}

	}


}
