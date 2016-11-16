package com.appspot.roovemore.match;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App
{
	public static Logger logger = Logger.getLogger(App.class.getSimpleName());

    public static void main( String[] args )
    {

    	int[] a = new int[1024*1024*100];

    	logger.info("1");
//    	logger.info(Arrays.toString(a));

    	logger.info("2");

        //初期値として"*"をセットする
        Arrays.fill(a, 9);

//    	logger.info("3");

        //初期値設定後の状態を画面表示する
//        logger.info(Arrays.toString(a));



    	logger.info("1-1");

    	a = new int[1024*1024*100];

//    	logger.info(Arrays.toString(a));

    	logger.info("1-2");

        //初期値として"*"をセットする
    	a = new int[1024*1024*100];

    	logger.info("1-3");

        //初期値設定後の状態を画面表示する
//        logger.info(Arrays.toString(a));


    }
}
