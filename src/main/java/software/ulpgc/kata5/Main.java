package software.ulpgc.kata5;

import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import software.ulpgc.kata5.adapters.WikipediaEventAdapter;
import software.ulpgc.kata5.models.WikipediaEvent;
import software.ulpgc.kata5.pojos.RandomWikiEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        int randomDay = randomFromInterval(1, 30);
        int randomMonth = randomFromInterval(1, 12);

        String rawJson = fetchJSON(randomDay, randomMonth);
        RandomWikiEvent wikiEvents = new Gson().fromJson(rawJson, RandomWikiEvent.class);

        List<RandomWikiEvent.EventFromYear> events = Arrays.asList(wikiEvents.events());
        WikipediaEventAdapter adapter = new WikipediaEventAdapter(wikiEvents);
        List<WikipediaEvent> adaptedEvents = events.stream().map(adapter::adapt).toList();
        for (WikipediaEvent event : adaptedEvents) {
            System.out.println(event.date());
            System.out.println(event.title());
            System.out.println("---");
        }
    }

    private static String fetchJSON(int day, int month) throws IOException {
        Connection.Response response = Jsoup.connect(
                "https://byabbe.se/on-this-day/" + month + "/" + day + "/events.json"
        ).ignoreContentType(true).method(Connection.Method.GET).execute();

        return response.body();
    }

    private static int randomFromInterval(int min, int max) {
        Random random =  new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}