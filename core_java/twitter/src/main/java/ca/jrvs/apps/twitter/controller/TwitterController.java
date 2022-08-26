package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtils;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller{
    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";
    private Service service;

    @Autowired
    public TwitterController (Service service) { this.service = service; }

    /**
     * Parse user argument and post a tweet by calling service classes
     *
     * @param args
     * @return a posted tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet postTweet(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE : TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }
        String tweet_text = args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);
        if (coordArray.length != 2 || tweet_text.length() == 0) {
            throw new IllegalArgumentException(
                    "Invalid location format\nUSAGE : TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }
        Double lat = null;
        Double lon = null;
        try {
            lat = Double.parseDouble(coordArray[0]);
            lon = Double.parseDouble(coordArray[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid location format\nUSAGE : witterCLIApp post \"tweet_text\" \"latitude:longitude\"",e);
        }
        Tweet postTweet = TweetUtils.buildTweet(tweet_text,lon,lat);
        return service.postTweet(postTweet);
    }

    /**
     * Parse user argument and search a tweet by calling service classes
     *
     * @param args
     * @return a tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet showTweet(String[] args) {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("USAGE : TwitterCLIApp show tweet_id [field1,field2]");
        }
        String id_str = args[1];
        if (id_str == null){
            throw new IllegalArgumentException("Invalid id\nUSAGE : TwitterCLIApp show tweet_id [field1,field2]");
        }
        String[] fields;
        if (args.length == 3){
            fields = args[2].split(COMMA);
        } else {
            fields = new String[0];
        }
        return service.showTweet(id_str,fields);

    }

    /**
     * Parse user argument and delete tweets by calling service classes
     *
     * @param args
     * @return a list of deleted tweets
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("USAGE : TwitterCLIApp delete tweet_id1,tweet_id2,...");
        }

        if (args[1].length() == 0) {
            throw new IllegalArgumentException("Invalid arguments,must provide at least one tweet id.");
        }
        String[] ids;
        if (args[1].contains(COMMA)){
            ids = args[1].split(COMMA);
        } else {
            ids = new String[] {args[1]};
        }
        return service.deleteTweets(ids);
    }
}
