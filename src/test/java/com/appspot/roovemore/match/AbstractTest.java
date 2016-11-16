package com.appspot.roovemore.match;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstractTest extends TestCase {

	protected final Log log = LogFactory.getLog(getClass());

	protected InputStream getInputStreamWithoutClose(String url){

		HttpURLConnection con = null;
		try {
			URL _url = new URL(url);
			con = (HttpURLConnection) _url.openConnection();
			// con.setRequestProperty("Content-type", "text/xml");
			con.setRequestMethod("GET");

			int status = con.getResponseCode();
			if(status != 200) {
				return null;
			}
			return con.getInputStream();

		} catch (Exception e) {
			return null;

		} finally {
			if (con != null)
				con.disconnect();
		}
	}

}
