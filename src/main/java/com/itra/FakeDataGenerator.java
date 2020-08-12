package com.itra;

import com.github.javafaker.Faker;
import java.io.InputStream;
import java.util.Locale;
import javax.json.*;

public class FakeDataGenerator {

    private String lang;
    Faker generationLibrary;
    ErrorMaker errorGenerator;
    private double countErrors;
    private int countRows,numberEveryErrorLine;
    JsonObject config;

    public FakeDataGenerator(String lang, int countRows, double countErrors) {
        InputStream jis = getClass().getClassLoader().getResourceAsStream("config.json");
        this.config = getConfig(jis);

        this.lang                 = lang;
        this.countRows            = countRows;
        this.countErrors          = countErrors;
        this.generationLibrary    = new Faker(new Locale(this.lang));
        this.errorGenerator       = new ErrorMaker(countErrors, this.config, this.lang);
        this.numberEveryErrorLine = this.errorGenerator.getErrorEveryNumberLines();

    }
    public static JsonObject getConfig(InputStream jis){
        JsonObject configJson = null;
        JsonReader reader = Json.createReader(jis);
        configJson = reader.readObject();
        reader.close();
        return configJson;
    }
    public void generate() {
        String name, zipcode, country, streetAddress, phoneNumber, city, line;
        String[] lineArray;
        for(int i = 1; i <= countRows ; i++) {
            name          = generationLibrary.name().name();
            zipcode       = generationLibrary.address().zipCode();
            country       = config.getString(lang+"_country");
            city          = generationLibrary.address().cityName();
            streetAddress = generationLibrary.address().streetAddress();
            phoneNumber   = generationLibrary.phoneNumber().phoneNumber();

            lineArray = new String[]{name, zipcode, country, city, streetAddress, phoneNumber};
            if ((i % numberEveryErrorLine) == 0) {
                lineArray = errorGenerator.makeError(lineArray);
            }
            line = lineArray[0] + "; " + lineArray[1] + ", " + lineArray[2]
                    + ", " + lineArray[3] + ", " + lineArray[4] + "; " + lineArray[5];

            System.out.println(line);
        }
    }
}
