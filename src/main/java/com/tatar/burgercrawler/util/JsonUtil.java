package com.tatar.burgercrawler.util;

import com.tatar.burgercrawler.constant.JsonConstants;
import com.tatar.burgercrawler.model.Venue;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtil {

    private JsonUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Transforms JSON to list of Venue objects and returns it
     *
     * @param rawJson String representation of Foursquare API response.
     * @return list of Venue objects
     */
    public static List<Venue> getVenueList(String rawJson) {

        List<Venue> venueList = new ArrayList<>();

        if (rawJson != null) {
            // TODO find a more efficient way to parse JSON (Gson or Jackson)
            JSONObject fourSquareResponse = new JSONObject(rawJson); // Transform raw JSON String to JSONObject for easy processing
            JSONObject response = fourSquareResponse.getJSONObject(JsonConstants.KEY_RESPONSE); // Extract response JSONObject from fourSquareResponse
            JSONArray groupsJsonArray = response.getJSONArray(JsonConstants.KEY_GROUPS); // Extract groups JSONArray from response

            /*  Get recommendedPlaces from groups JSONArray.
                When I tested the API with different parameters the groups array always had one element which is recommended places
                So I just get these places for further data processing
            */
            JSONObject recommendedPlacesJson = groupsJsonArray.getJSONObject(0);
            JSONArray itemsJsonArray = recommendedPlacesJson.getJSONArray(JsonConstants.KEY_ITEMS); // extract items array from recommendedPlaces JSONObject

            // iterate through items JSONArray array and get item ids and item names
            // transform json to list of Venue objects and return it
            for (int i = 0; i < itemsJsonArray.length(); i++) {

                JSONObject itemJson = itemsJsonArray.getJSONObject(i);
                JSONObject venueJson = itemJson.getJSONObject(JsonConstants.KEY_VENUE);

                String venueId = venueJson.getString(JsonConstants.KEY_ID);
                String venueName = venueJson.getString(JsonConstants.KEY_NAME);

                Venue venue = new Venue(venueId, venueName);

                venueList.add(venue);
            }
        }

        return venueList;
    }
}
