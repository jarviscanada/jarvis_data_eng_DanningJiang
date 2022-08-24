package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    TwitterService service;

    @InjectMocks
    TwitterController controller;

    @Test
    public void postTweet() {
        //test with valid arguments
        when(service.postTweet(any())).thenReturn(new Tweet());
        String[] args = {"post","Test 456","1.0:2.0"};
        Tweet tweet = controller.postTweet(args);
        assertNotNull(tweet);
        verify(service,times(1)).postTweet(any());

        //test with illegal arguments
        try {
            controller.postTweet(new String[] {"test case 1", "50:50"});
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
        try {
            controller.postTweet(new String[] {"post", "test case 2", "200:50"});
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
        try {
            controller.postTweet(new String[] {"post", "test case 3", "50,50"});
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
        try {
            controller.postTweet(new String[] {"post", "", "50:50"});
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

    }

    @Test
    public void showTweet() {
        //test that service is invoked correctly
        when(service.showTweet(any(),any())).thenReturn(new Tweet());
        Tweet tweet1 = controller.showTweet(new String[] {"show","123456"});
        Tweet tweet2 = controller.showTweet(new String[] {"show","123456","id,created_at,text"});
        assertNotNull(tweet1);
        assertNotNull(tweet2);
        verify(service, times(2)).showTweet(any(), any());

        //test with illegal arguments
        try {
            controller.showTweet(new String[] {"show"});
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

        try {
            controller.showTweet(new String[] {"show", ""});
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

        try {
            controller.showTweet(new String[] {"show", "1ab"});
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

    }

    @Test
    public void deleteTweet() {
        //test that service is invoked correctly
        when(service.deleteTweets(any())).thenReturn(new ArrayList<Tweet>());
        List<Tweet> listTweets1 = controller.deleteTweet(new String[]{"delete", "123456"});
        List<Tweet> listTweets2 = controller.deleteTweet(new String[]{"delete", "11111,22222,33333"});
        assertNotNull(listTweets1);
        assertNotNull(listTweets2);
        verify(service, times(2)).deleteTweets(any());

        //test with illegal arguments
        try {
            controller.deleteTweet(new String[] {"delete"});
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

        try {
            controller.deleteTweet(new String[] {"delete", "1#23"});
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }
}