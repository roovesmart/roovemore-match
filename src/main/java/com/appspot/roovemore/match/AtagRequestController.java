package com.appspot.roovemore.match;

import java.io.InputStream;
import java.util.List;

public class AtagRequestController extends RequestController {

	public List<AtagResult> retList;

	// 除外URL
	public String out;

	// 絞り込みURL
	public String filter;

	// http絞り込みモード
	public boolean http = true;

	// コンテントタイプ
	public String ctype;

	@Override
    protected void afterProcess(
    		int status,
			String responseCharset,
			String responseContentType,
			InputStream inputStream
    		){


		if(status != 200) {
			// TODO: handle exception
			return;
		}


    	ATagMatch a = new ATagMatch();

    	try {
        	a.read(responseCharset, responseContentType, inputStream);

		} catch (Exception e) {
			// TODO: handle exception
		}

    	this.retList = a.retList;

    }

}
