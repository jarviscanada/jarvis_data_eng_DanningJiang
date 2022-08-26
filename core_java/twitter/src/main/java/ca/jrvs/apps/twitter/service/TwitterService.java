package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class TwitterService implements Service {
    private CrdDao dao;

    @Autowired
    public TwitterService(CrdDao dao) { this.dao = dao;}

    /**
     * Validate and post a user input Tweet
     *
     * @param tweet tweet to be created
     * @return created tweet
     * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long out of range
     */
    @Override
    public Tweet postTweet(Tweet tweet) {
        //Business logic
        //check text length, lat/lon range, id format...
        validatePostTweet(tweet);

        //create tweet via dao
        return (Tweet) dao.create(tweet);
    }

    /**
     * Search a tweet by ID
     *
     * @param id     tweet id
     * @param fields set fields not in the list to null
     * @return Tweet object which is returned by the Twitter API
     * @throws IllegalArgumentException if id or fields param is invalid
     */
    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateShowTweet(id,fields);
        return (Tweet) dao.findById(id);
    }

    /**
     * Delete Tweet(s) by id(s).
     *
     * @param ids tweet IDs which will be deleted
     * @return A list of Tweets
     * @throws IllegalArgumentException if one of the IDs is invalid.
     */
    @Override
    public List<Tweet> deleteTweets(String[] ids) {

        List<Tweet> deletedTweets = new ArrayList<>();

        for (String id : ids) {
            if(!id.matches("^[0-9]*$") || Long.parseLong(id) < 0){
                throw new IllegalArgumentException("Error:Given id is invalid");
            }
            deletedTweets.add((Tweet) dao.deleteById(id));
        }
        return deletedTweets;
    }

    private void validatePostTweet(Tweet tweet) {
        if (tweet == null) {
            throw new IllegalArgumentException("Input tweet object is null.");
        }
        if (tweet.getText().length() == 0 || tweet.getText().length() > 140) {
            throw new IllegalArgumentException("tweet text length cannot exceed 140 characters.");
        }
        List<Double> coordinates = tweet.getCoordinates().getCoordinates();
        if (coordinates != null) {
            Double longitude = coordinates.get(0);
            Double latitude = coordinates.get(1);

            if (longitude < -180 || longitude > 180 || latitude < -90 || latitude > 90) {
                throw new IllegalArgumentException("Error:coordinates out of range.");
            }
        }

    }

    private void validateShowTweet(String id, String[] fields) {
        if (!id.matches("^[0-9]*$") || Long.parseLong(id) < 0) {
            throw new IllegalArgumentException("Error:Given id is invalid");
        }
        if (fields == null) return;
        for (String f : fields) {
            try {
                Field field = Tweet.class.getDeclaredField(f);
            } catch (NoSuchFieldException e) {
                throw  new IllegalArgumentException( "Error:" + f + " field doesn't exist.");
            }
        }

    }
}
