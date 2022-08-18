package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {

    private TwitterDao dao;

    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void createTest() {
        Tweet postTweet = new Tweet();
        long currentTime = System.currentTimeMillis();
        postTweet.setText("new tweet post at " + currentTime);
        Coordinates coordinates = new Coordinates();
        List<Double> coordList = new ArrayList<>();
        Double longitude =  -89.246109;
        Double latitude = 48.382221;
        coordList.add(0,longitude);
        coordList.add(1,latitude);
        coordinates.setCoordinates(coordList);
        coordinates.setType("Point");
        postTweet.setCoordinates(coordinates);

        Tweet returnedTweet = dao.create(postTweet);

        assertEquals(postTweet.getText(),returnedTweet.getText());
        assertNotNull(returnedTweet.getText());
        assertEquals(longitude,returnedTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(latitude,returnedTweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void findById() {
        String id = "xxx";
        Double longitude =  -89.246109;
        Double latitude = 48.382221;

        Tweet returnedTweet = dao.findById(id);
        assertEquals(longitude,returnedTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(latitude,returnedTweet.getCoordinates().getCoordinates().get(1));
        assertTrue(returnedTweet.getText().contains("tweet post"));
    }

    @Test
    public void deleteById() {
        String id = "xxx";
        assertNotNull(dao.findById(id));
        Tweet deletedTwitter = dao.deleteById(id);
        assertEquals(id,deletedTwitter.getId_str());
        assertTrue(deletedTwitter.getText().contains("tweet post"));
    }
}