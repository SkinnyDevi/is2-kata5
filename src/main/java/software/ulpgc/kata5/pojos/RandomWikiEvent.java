package software.ulpgc.kata5.pojos;

public record RandomWikiEvent(String wikipedia, String date, EventFromYear[] events) {

    public record EventFromYear(String year, String description) {}
}