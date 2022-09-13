package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {

    private TwitterDao dao;
    private TwitterService service;
    private TwitterController controller;
    private String id;

    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        TwitterHttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
        this.dao = new TwitterDao(httpHelper);
        this.service = new TwitterService(dao);
        this.controller = new TwitterController(service);

    }

    @Test
    public void postAndShowTweetTest() {
        String[] args = new String[]{"post","Test 123","43.0:79.0"};
        Tweet postedTweet = controller.postTweet(args);
        assertNotNull(postedTweet);
        assertNotNull(postedTweet.getId());
        assertEquals("Test 123",postedTweet.getText());
        assertEquals(43.0,postedTweet.getCoordinates().getCoordinates().get(1),0.001);
        assertEquals(79.0,postedTweet.getCoordinates().getCoordinates().get(0),0.001);

        id = postedTweet.getId_str();
        String created_at = postedTweet.getCreated_at();
        args = new String[]{"show", id};
        Tweet showedTweet =  controller.showTweet(args);
        assertNotNull(showedTweet);
        assertEquals(created_at,showedTweet.getCreated_at());
        assertEquals(id,showedTweet.getId_str());
        assertEquals("Test 123",showedTweet.getText());

    }


    @Test
    public void deleteTweet() {
        String[] args = new String[]{"delete",id};
        List<Tweet> deletedTweets = controller.deleteTweet(args);
        for (Tweet deletedTweet : deletedTweets) {
            assertNotNull(deletedTweet);
            assertEquals(id, deletedTweet.getId_str());
            assertEquals("Test 123", deletedTweet.getText());
        }
    }
}