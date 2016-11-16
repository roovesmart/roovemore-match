package com.appspot.roovemore.match;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class RequestController {

	/**
	 * requestUrl.
	 * This field must be set.
	 */
	public String requestUrl;

	/**
	 * responseCharset
	 */
	public String responseCharset;

	/**
	 * requestProperty example:"text/xml"
	 */
	public String requestProperty;

	/**
	 * requestMethod
	 */
	public String requestMethod = "GET";

	/**
	 * connectTimeout
	 */
	public int connectTimeout = 60000;

	/**
	 * Do request.
	 * @throws IOException
	 */
    public void request() throws IOException{

		HttpURLConnection con = null;
		InputStream inputStream = null;
		try {

			URL url = new URL(requestUrl);
			con = (HttpURLConnection) url.openConnection();
			if(requestProperty!=null){
				con.setRequestProperty("Content-type", requestProperty);
			}
			con.setRequestMethod(requestMethod);
			con.setConnectTimeout(connectTimeout);

			int status = con.getResponseCode();
//			if(status != 200) {
//				return;
//			}

			inputStream = con.getInputStream();
			afterProcess( status, responseCharset, con.getContentType(), inputStream);

		} catch (IOException e){
			throw new IOException(
					"http request exception e = " + e);

		} finally {

			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				throw new IOException(
						"inputStream.close exception e = " + e);
			}
			if (con != null)
				con.disconnect();
		}

		return;
	}

    protected abstract void afterProcess(
    		int statusCd,
			String responseCharset,
			String responseContentType,
			InputStream inputStream
    		);

}
