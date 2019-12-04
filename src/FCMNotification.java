import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.example.subscriptions.Subscription;

public class FCMNotification {

    // Method to send Notifications from server to client end.
    public final static String AUTH_KEY_FCM = "AIzaSyDcDO5OZxW2-C8D-xyCiSSJNVA6ifVWPYY";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public static void pushFCMNotification(Item i) throws Exception {

        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        /*
        JSONObject data = new JSONObject();
        data.put("to",i.username.trim());
        JSONObject info = new JSONObject();
        info.put("title", "SUBSCRIPTIONS"); // Notification title
        info.put("text", "Your payment date for "+i.name+" is close!! You will be alerted for next payment in "+i.nextAlertDate); // Notification body
        info.put("sound","default" );
        data.put("notification", info);
*/
        
        String text="Your payment date for "+i.name+" is close!! Next payment after this: "+i.nextAlertDate;
        String json="{\r\n\"to\":\""+i.username.trim()+"\",\r\n\"notification\":{\r\n  \"title\":\"SUBSCRIPTIONS\",\r\n       \"text\":\""+text+"\",\r\n      \"sound\":\"default\"\r\n}\r\n}";
       System.out.println(json);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
       // wr.write(data.toString());
        wr.write(json);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

    }

    @SuppressWarnings("static-access")
    public static void main2(String[] args) throws Exception {
        //FCMNotification.pushFCMNotification("USER_DEVICE_TOKEN");
    }
}