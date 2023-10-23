package com.example.cbleecher;

        import okhttp3.*;
        import java.io.IOException;

public class RequestSender {
private static String Return;
    public static void main(String userInput) {
        OkHttpClient client = new OkHttpClient();

        MediaType jsonType = MediaType.get("application/json; charset=utf-8");

        String jsonBody = "{" +
                "\"properties\": {" +
                "\"androidClientInfo\": {" +
                "\"adId\": \"\"," +
                "\"adOptOut\": false," +
                "\"androidId\": \"\"," +
                "\"availableSpace\": 210390933504," +
                "\"cpu\": \"armeabi-v7a,armeabi\"," +
                "\"device\": \"\"," +
                "\"deviceType\": 0," +
                "\"dpi\": 410," +
                "\"hardware\": \"\"," +
                "\"height\": 2186," +
                "\"locale\": \"fa\"," +
                "\"manufacturer\": \"samsung\"," +
                "\"mcc\": 432," +
                "\"mnc\": 35," +
                "\"mobileServiceType\": 1," +
                "\"model\": \"K40\"," +
                "\"osBuild\": \"\"," +
                "\"product\": \"Galaxy\"," +
                "\"sdkVersion\": 29," +
                "\"width\": 1080" +
                "}," +
                "\"appThemeState\": 0," +
                "\"clientID\": \"\"," +
                "\"clientVersion\": \"\"," +
                "\"clientVersionCode\": 0," +
                "\"isKidsEnabled\": false," +
                "\"language\": 2" +
                "}," +
                "\"singleRequest\": {" +
                "\"appDownloadInfoRequest\": {" +
                "\"downloadStatus\": 1," +
                "\"packageName\": \"" + userInput + "\"" +
                "}" +
                "}" +
                "}";

        Request request = new Request.Builder()
                .url("https://api.cafebazaar.ir/rest-v1/process/AppDownloadInfoRequest")
                .post(RequestBody.create(jsonType, jsonBody))
                .header("Host", "api.cafebazaar.ir")
                .header("accept-language", "fa")
                .header("is-kid", "false")
                .header("content-length", String.valueOf(jsonBody.length()))
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {

                ResponseBody responseBody = response.body();
                String responseBodyString = responseBody.string();

                JsonFormater jsonFormater = new JsonFormater(responseBodyString);
                String formattedJson = jsonFormater.run();
                RequestSender Object = new RequestSender();
Object.Return = formattedJson;


                System.out.println(formattedJson);

            } else {

                System.out.println("Error: " + response.code() + " " + response.message());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getReturn() {
        return Return;
    }
}
