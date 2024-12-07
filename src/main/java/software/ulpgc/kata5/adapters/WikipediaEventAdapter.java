package software.ulpgc.kata5.adapters;

import software.ulpgc.kata5.models.WikipediaEvent;
import software.ulpgc.kata5.pojos.RandomWikiEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class WikipediaEventAdapter {
    private final RandomWikiEvent wikiEvent;

    public WikipediaEventAdapter(RandomWikiEvent wikiEvent) {
        this.wikiEvent = wikiEvent;
    }

    public WikipediaEvent adapt(RandomWikiEvent.EventFromYear randomEvent) {
        return new WikipediaEvent(
                formatDate(wikiEvent.date(), randomEvent.year()),
                randomEvent.description()
        );
    }

    private LocalDate formatDate(String date, String year) {
        if (year.contains("AD")) {
            year = year.replace("AD", "").trim();
        } else if (year.contains("BC")) {
            year = "-" + year.replace("BC", "").trim();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.ENGLISH);
        return LocalDate.parse(date + " " + 1999, formatter).withYear(Integer.parseInt(year));
    }
}