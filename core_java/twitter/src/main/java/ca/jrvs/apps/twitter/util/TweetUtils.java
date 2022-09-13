package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TweetUtils {
    public static Tweet buildTweet(String text, Double lon, Double lat) {
        Tweet tweet = new Tweet();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        tweet.setCreated_at(formatter.format(date));

        tweet.setText(text);

        Coordinates coordinates = new Coordinates();
        coordinates.setType("Point");
        List<Double> coords = new ArrayList<>();
        coords.add(lon);
        coords.add(lat);
        coordinates.setCoordinates(coords);
        tweet.setCoordinates(coordinates);

        return tweet;
    }
}