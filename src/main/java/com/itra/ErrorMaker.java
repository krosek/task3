package com.itra;

import java.util.HashMap;
import javax.json.*;

public class ErrorMaker {
    private int errorEveryNumberLines;
    private int countErrorsInLine;
    private String[] errorTypes = {"swap","add","remove"};
    private JsonObject config;
    private String lang;

    public ErrorMaker(double errorsCount, JsonObject config, String lang) {
        this.errorEveryNumberLines = (Math.ceil(errorsCount) == errorsCount || errorsCount > 0.5)
                ? 1 : Math.round(10 / Math.round(errorsCount*10));
        this.countErrorsInLine = (Math.ceil(errorsCount) == errorsCount)
                ? (int) errorsCount : 1;
        this.config = config;
        this.lang   = lang;
    }

    public int getErrorEveryNumberLines() {
        return errorEveryNumberLines;
    }

    public String[] makeError(String[] lineArray) {
        String errType;
        for(int i = 1; i <= countErrorsInLine ; i++) {
            errType = getRandomTypeError(errorTypes);
            if ("swap".equals(errType)) {
                lineArray = errorSwapChar(lineArray);
            } else if ("remove".equals(errType)) {
                lineArray = errorRemoveChar(lineArray);
            } else if ("add".equals(errType)) {
                lineArray = errorAddChar(lineArray,this.config, this.lang);
            }

        }
        return lineArray;
    }

    public static int prepareEveryNumberLines(double errorsCount) {
        if (Math.ceil(errorsCount) == errorsCount || errorsCount > 0.5) {
            return 1;
        } else {
            return Math.round(10 / Math.round(errorsCount*10));
        }

    }
    public static String getRandomTypeError(String[] errorTypes) {
        String typeError; int n;
        n = getRandomIndexByArray(errorTypes);
        typeError = errorTypes[n];
        return typeError;
    }

    public static String[] errorSwapChar(String[] lineArray) {
        HashMap<String,Integer> errPrps = new HashMap<String, Integer>();

        errPrps.put("fieldNumber", getRandomIndexByArray(lineArray));
        errPrps.put("firstCharNumber", getRandomIndexCharByString(
                lineArray[errPrps.get("fieldNumber")]
        ));
        errPrps.put("secondCharNumber", errPrps.get("firstCharNumber")+1);
        lineArray[errPrps.get("fieldNumber")] = doSwap(
                        lineArray[errPrps.get("fieldNumber")],
                        errPrps.get("firstCharNumber"),
                        errPrps.get("secondCharNumber")
                );

        return lineArray;
    }
    public static String[] errorRemoveChar(String[] lineArray) {
        int chNumber, field, temp;
        field    = getRandomIndexByArray(lineArray);
        if (lineArray[field].length() > 1) {
            temp     = getRandomIndexCharByString(lineArray[field]);
            chNumber = temp == 0 ? temp + 1 : temp;
            lineArray[field] = lineArray[field].substring(0, chNumber) + lineArray[field].substring(chNumber + 1);
        }
        return lineArray;
    }
    public static String[] errorAddChar(String[] lineArray, JsonObject config, String lang) {
        int posInArray       = getRandomIndexByArray(lineArray);
        String str           = lineArray[posInArray];
        int pos              = getRandomIndexCharByString(str);
        StringBuffer strBuff = new StringBuffer(str);

        strBuff.insert(
                pos,
                config
                    .getString(lang)
                    .toCharArray()[getRandomIndexCharByString(config.getString(lang))]
        );
        lineArray[posInArray] = strBuff.toString();

        return lineArray;
    }
    public static int getRandomIndexByArray(String[] array) {
        int result = (int)Math.floor(Math.random() * array.length);
        return result;
    }
    public static int getRandomIndexCharByString(String array) {
        int result = (int)Math.floor(Math.random() * array.length());
        return result;
    }
    public static String doSwap(String str, int firstCharNumber, int secondCharNumber)
    {
        if (str.length() > 1) {
            secondCharNumber = (secondCharNumber >= str.length()) ? firstCharNumber - 1 : secondCharNumber;
            char ch[] = str.toCharArray();
            char temp = ch[firstCharNumber];
            ch[firstCharNumber] = ch[secondCharNumber];
            ch[secondCharNumber] = temp;
            str = String.valueOf(ch);
        }
        return str;
    }
}
