package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParse;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterDao implements CrdDao<Tweet,String> {

    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DElETE_PATH = "/1.1/statuses/destroy/";
    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";
    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {this.httpHelper = httpHelper;}

    /**
     * Create an entity(Tweet) to the underlying storage
     *
     * @param tweet entity that to be created
     * @return created entity
     */
    @Override
    public Tweet create(Tweet tweet) {
        URI uri;
        try {
            uri = getPostUri(tweet);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input", e);
        }
        //Execute HTTP Request
        HttpResponse httpResponse = httpHelper.httpPost(uri);
        //Validate response and convert response to Tweet object
        return parseResponseBody(httpResponse, HTTP_OK);
        
    }

    /**
     * Find an entity(Tweet) by its id
     *
     * @param s entity id
     * @return Tweet entity
     */
    @Override
    public Tweet findById(String s) {
        URI uri;
        try {
            uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input", e);
        }
        HttpResponse httpResponse = httpHelper.httpGet(uri);
        return parseResponseBody(httpResponse, HTTP_OK);

    }

    /**
     * Delete an entity(Tweet) by its ID
     *
     * @param id_str of the entity to be deleted
     * @return deleted entity
     */
    @Override
    public Tweet deleteById(String id_str) {
        URI uri;
        try {
            uri = new URI(API_BASE_URI + DElETE_PATH + id_str + ".json");
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input", e);
        }
        HttpResponse httpResponse = httpHelper.httpPost(uri);
        return parseResponseBody(httpResponse, HTTP_OK);
        
    }

    private URI getPostUri(Tweet tweet) throws URISyntaxException {
        Coordinates coordinates = tweet.getCoordinates();
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        URI uri = null;
        if (coordinates != null) {
            double longitude = coordinates.getCoordinates().get(0);
            double latitude = coordinates.getCoordinates().get(1);
            uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM
                    + "status" + EQUAL + percentEscaper.escape(tweet.getText())
                    + AMPERSAND + "long" + EQUAL + longitude
                    + AMPERSAND + "lat" + EQUAL + latitude);
        }else {
            uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + percentEscaper.escape(tweet.getText()));
        }
        return uri;
    }

    /**
     * Check response status code and convert response entity to Tweet
     * @param httpResponse
     * @param expectedStatusCode
     * @return Tweet tweet
     */
    private Tweet parseResponseBody(HttpResponse httpResponse, int expectedStatusCode) {
        //check response status
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status != expectedStatusCode) {
            try {
                System.out.println(EntityUtils.toString(httpResponse.getEntity()));
            } catch (IOException e) {
                System.out.println("Response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP status:" + status);
        }

        if (httpResponse.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }

        //Convert Response Entity to str
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert entity to String", e);
        }

        //Convert JSON string to Tweet object
        Tweet tweet;
        try {
            tweet = JsonParse.toObjectFromJson(jsonStr, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert JSON str to Object", e);
        }

        return tweet;


    }


}
