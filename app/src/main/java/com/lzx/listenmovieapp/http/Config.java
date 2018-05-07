package com.lzx.listenmovieapp.http;

/**
 * Author：Road
 * Date  ：2018/5/4
 */

public class Config {
    /**
     * 成功
     */
    private static final int SUCCESS = 200;

    //  road
    private static final String BASE_URL = "http://192.168.10.175:8080";

    //  li
//    private static final String BASE_URL = "http://192.168.0.107:8080";

    //  road
    public static final String RESOURCE_URL = "http://192.168.10.175:8080";

    //  li
//    public static final String RESOURCE_URL = "http://192.168.0.107:8080";

    public static final String MOVIE_LIST_URL = BASE_URL + "/json/movies.json";

    public static final String EVENTS_URL = BASE_URL + "/json/movies.json";
}
