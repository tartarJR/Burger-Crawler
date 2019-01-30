package com.tatar.burgercrawler.constant;

public class ApiConstants {

    public static final String ML_BASE_URL = "https://pplkdijj76.execute-api.eu-west-1.amazonaws.com/prod/recognize";

    private static final String BASE_URL = "https://api.foursquare.com/v2/venues/explore?";

    private static final String CLIENT_ID = "GRD5JH5OFBGV4RO0N2B24D010CT1WFATH4OSLGGQTL5OLX0Z"; // TODO need a more secure way to store this
    private static final String CLIENT_SECRET = "HKHWX42VDJW5VMPHYEGQLYJZUMVX2T0D5WEYX24QFS4QHU43"; // TODO need a more secure way to store this
    private static final String VERSION = "20180323";
    private static final String NEAR = "Tartu";
    private static final String CATEGORY_ID = "4d4b7105d754a06374d81259";
    private static final String LIMIT = "&limit=5";

    public static final String OFFSET = "&offset=";

    public static final String REQUEST_URL = BASE_URL +
            "client_id=" + CLIENT_ID +
            "&client_secret=" + CLIENT_SECRET +
            "&v=" + VERSION +
            "&near=" + NEAR +
            "&categoryId=" + CATEGORY_ID;
}
