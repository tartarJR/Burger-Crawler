package com.tatar.burgercrawler.util;

import com.tatar.burgercrawler.constant.JsonConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    // TODO find a more efficient way to parse JSON (Gson or Jackson)
    public static List<String> getVenueIdList(String rawJson) {

        List<String> venueIdList = new ArrayList<>();

        if (rawJson != null) {
            JSONObject fourSquareResponse = new JSONObject(rawJson); // Transform raw JSON String to JSONObject for easy processing
            JSONObject response = fourSquareResponse.getJSONObject(JsonConstants.KEY_RESPONSE); // Extract response JSONObject from fourSquareResponse
            JSONArray groups = response.getJSONArray(JsonConstants.KEY_GROUPS); // Extract groups JSONArray from response

            /*  Get recommendedPlaces from groups JSONArray.
                When I tested the API with different parameters the groups array always had one element which is recommended places
                So I just get these places for further data processing
            */
            JSONObject recommendedPlaces = groups.getJSONObject(0);
            JSONArray items = recommendedPlaces.getJSONArray(JsonConstants.KEY_ITEMS); // extract items array from recommendedPlaces JSONObject

            // 'iterate through items array and get item ids
            for (int i = 0; i < items.length(); i++) {

                JSONObject item = items.getJSONObject(i);
                JSONObject venue = item.getJSONObject(JsonConstants.KEY_VENUE);

                venueIdList.add(venue.getString(JsonConstants.KEY_ID));
            }
        }

        return venueIdList;
    }
}
