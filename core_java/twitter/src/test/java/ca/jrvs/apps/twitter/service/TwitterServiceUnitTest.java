package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
    @Mock
    CrdDao mockDao;

    @InjectMocks
    TwitterService service;

    @Spy
    @InjectMocks
    Tweet tweet;

    @Before
    public void setUp() throws Exception {
        String text = "test" + System.currentTimeMillis();
        tweet = TweetUtils.buildTweet(text, 0.0, 0.0);
    }


    @Test
    public void postTweetTest() {
        //test normal tweet
        when(mockDao.create(any())).thenReturn(new Tweet());
        service.postTweet(TweetUtils.buildTweet("test", 50.0, 0.0));
    }
    @Test
    public void postLongTweetTest() {
        String longText = "He that is thy friend indeed,\n" +
                "He will help thee in thy need:\n" +
                "If thou sorrow, he will weep;\n" +
                "If thou wake, he cannot sleep:\n" +
                "Thus of every grief in heart\n" +
                "He with thee doth bear a part.\n" +
                "These are certain signs to know\n" +
                "Faithful friend from flattering foe.\n";

        Tweet longTweet = TweetUtils.buildTweet(longText, 50.0, 0.0);
        try {
            service.postTweet(longTweet);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void invalidCoordTweetTest(){
        //test with invalid coordinates
        Tweet badCoordTweet = TweetUtils.buildTweet("Tweet with bad coordinates",-95.0,-185.0);
        try {
            service.postTweet(badCoordTweet);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    @Test
    public void showTweet() {

        when(mockDao.findById(any())).thenReturn(tweet);
        service.showTweet(tweet.getId_str(), null);
    }

    @Test
    public void showInvalidIdTest() {
        String id = "abc";
        tweet.setId_str(id);
        try {
            service.showTweet(tweet.getId_str(), null);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    @Test
    public void deleteTweet() {
        when(mockDao.deleteById(any())).thenReturn(new Tweet());
        String[] ids = {"123456","78901234"};
        service.deleteTweets(ids);
        verify(mockDao,times(ids.length)).deleteById(any());
    }

    @Test
    public void deleteTweetWhenIdsAreInvalid() {
        String[] ids = new String[]{"1", "abc", "e#f", "-1", "0"};
        try {
            service.deleteTweets(ids);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        verify(mockDao, times(0)).deleteById(any());
    }

}
