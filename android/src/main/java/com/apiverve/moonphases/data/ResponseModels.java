// Converter.java

// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import com.apiverve.data.Converter;
//
// Then you can deserialize a JSON string with
//
//     MoonPhasesData data = Converter.fromJsonString(jsonString);

package com.apiverve.moonphases.data;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static MoonPhasesData fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(MoonPhasesData obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(MoonPhasesData.class);
        writer = mapper.writerFor(MoonPhasesData.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}

// MoonPhasesData.java

package com.apiverve.moonphases.data;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

public class MoonPhasesData {
    private String phase;
    private String phaseEmoji;
    private boolean waxing;
    private boolean waning;
    private double lunarAge;
    private double lunarAgePercent;
    private long lunationNumber;
    private double lunarDistance;
    private OffsetDateTime nextFullMoon;
    private OffsetDateTime lastFullMoon;

    @JsonProperty("phase")
    public String getPhase() { return phase; }
    @JsonProperty("phase")
    public void setPhase(String value) { this.phase = value; }

    @JsonProperty("phaseEmoji")
    public String getPhaseEmoji() { return phaseEmoji; }
    @JsonProperty("phaseEmoji")
    public void setPhaseEmoji(String value) { this.phaseEmoji = value; }

    @JsonProperty("waxing")
    public boolean getWaxing() { return waxing; }
    @JsonProperty("waxing")
    public void setWaxing(boolean value) { this.waxing = value; }

    @JsonProperty("waning")
    public boolean getWaning() { return waning; }
    @JsonProperty("waning")
    public void setWaning(boolean value) { this.waning = value; }

    @JsonProperty("lunarAge")
    public double getLunarAge() { return lunarAge; }
    @JsonProperty("lunarAge")
    public void setLunarAge(double value) { this.lunarAge = value; }

    @JsonProperty("lunarAgePercent")
    public double getLunarAgePercent() { return lunarAgePercent; }
    @JsonProperty("lunarAgePercent")
    public void setLunarAgePercent(double value) { this.lunarAgePercent = value; }

    @JsonProperty("lunationNumber")
    public long getLunationNumber() { return lunationNumber; }
    @JsonProperty("lunationNumber")
    public void setLunationNumber(long value) { this.lunationNumber = value; }

    @JsonProperty("lunarDistance")
    public double getLunarDistance() { return lunarDistance; }
    @JsonProperty("lunarDistance")
    public void setLunarDistance(double value) { this.lunarDistance = value; }

    @JsonProperty("nextFullMoon")
    public OffsetDateTime getNextFullMoon() { return nextFullMoon; }
    @JsonProperty("nextFullMoon")
    public void setNextFullMoon(OffsetDateTime value) { this.nextFullMoon = value; }

    @JsonProperty("lastFullMoon")
    public OffsetDateTime getLastFullMoon() { return lastFullMoon; }
    @JsonProperty("lastFullMoon")
    public void setLastFullMoon(OffsetDateTime value) { this.lastFullMoon = value; }
}