package com.itra;

import java.util.Arrays;

public class App 
{
    private static int    countRows;
    private static String lang;
    private static double countErrors;

    public static void main( String[] args )
    {

        String[] params = new String[3];
        params[0] = args[0];
        params[1] = args[1];
        params[2] = args.length < 3 ? "0" : args[2];

        validateData(params);
        FakeDataGenerator fakeGenerator = new FakeDataGenerator(lang, countRows, countErrors);
        fakeGenerator.generate();
    }
    public static void validateData(String[] params){
        String[] langResources = {"en_US","ru_RU", "be_BY"};
        if (Arrays.asList(langResources).contains(params[0])) {
            if (params[1].matches("^\\d+$") && params[2].matches("^[0-9]+([,.][0-9]?)?$")) {
                lang = params[0].substring(0,2).equals("be") ? "by" : params[0].substring(0,2);
                countRows = Integer.parseInt(params[1]);
                countErrors = Double.parseDouble(params[2]);
            } else {
                System.err.println("Wrong arguments!"); System.exit(0);
            }
        } else {
            System.err.println("Wrong arguments!"); System.exit(0);
        }
    }
}
