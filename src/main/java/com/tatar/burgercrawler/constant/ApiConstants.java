package com.tatar.burgercrawler.constant;

public class ApiConstants {

    private static final String BASE_URL = "https://api.foursquare.com/v2/venues/explore?";

    private static final String CLIENT_ID = ""; // TODO need a more secure way to store this
    private static final String CLIENT_SECRET = ""; // TODO need a more secure way to store this
    private static final String VERSION = "20180323";
    private static final String NEAR = "Tartu";
    private static final String CATEGORY_ID = "4d4b7105d754a06374d81259";
    private static final String OFFSET = "60";
    private static final String LIMIT = "30";

    public static final String REQUEST_URL = BASE_URL +
            "client_id=" + CLIENT_ID +
            "&client_secret=" + CLIENT_SECRET +
            "&v=" + VERSION +
            "&near=" + NEAR +
            "&categoryId=" + CATEGORY_ID;
}
