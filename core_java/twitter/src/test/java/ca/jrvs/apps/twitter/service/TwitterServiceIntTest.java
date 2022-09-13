package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {

    private TwitterService service;
    private TwitterDao dao;
    private Tweet tweet;
    private Double longitude;
    private Double latitude;

    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        this.dao = new TwitterDao(httpHelper);
        this.service = new TwitterService(dao);

        //create Tweet entity
        longitude = -89.246109;
        latitude = 48.382221;
        tweet = new Tweet();
        tweet.setText("Test my tweet api");
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new ArrayList<Double>(){{
            add(longitude);
            add(latitude);
        }});
        tweet.setCoordinates(coordinates);


    }

    @Test
    public void postAndShowTest() {
        //Test posting
        Tweet postedTweet = service.postTweet(tweet);
        assertNotNull(postedTweet);
        assertEquals("Test my tweet api",postedTweet.getText());
        assertEquals(longitude,postedTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(latitude,postedTweet.getCoordinates().getCoordinates().get(1));

        //Test showing
        String id = postedTweet.getId_str();
        String[] fields = {"id", "text", "coordinates"};
        Tweet showedTweet = service.showTweet(id,fields);
        assertNotNull(showedTweet);
        assertEquals("Test my tweet api",showedTweet.getText());
        assertEquals(longitude,showedTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(latitude,showedTweet.getCoordinates().getCoordinates().get(1));

    }


    @Test
    public void deleteTweets() {
        // creat a list of tweets to test deleting
        Tweet[] tweets = new Tweet[3];
        String[] ids = new String[tweets.length];
        for(int i = 0; i < tweets.length; i++) {
            String text = "test" + i + " " + System.currentTimeMillis();
            tweets[i] = dao.create(TweetUtils.buildTweet(text, longitude, latitude));
            ids[i] = tweets[i].getId_str();
        }

        List<Tweet> deletedTweets = service.deleteTweets(ids);
        assertNotNull(deletedTweets);
        for(int i = 0; i < tweets.length; i++) {
            assertEquals(ids[i],deletedTweets.get(i).getId_str());
            assertEquals(tweets[i].getText(),deletedTweets.get(i).getText());
        }

    }


}