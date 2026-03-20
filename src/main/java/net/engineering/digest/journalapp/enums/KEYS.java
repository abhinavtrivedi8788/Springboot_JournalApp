package net.engineering.digest.journalapp.enums;

public enum KEYS{
    WEATHER_APP("weather-app");

    KEYS(String value) {
        this.value = value;
    }

    private final String value;


    public String getValue() {
        return value;
    }

}