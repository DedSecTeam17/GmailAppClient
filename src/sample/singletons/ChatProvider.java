package sample.singletons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sample.models.ChatMessage;
import sample.models.SustGroup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ChatProvider {


    private static ChatProvider ourInstance = new ChatProvider();

    public static ChatProvider getInstance() {
        return ourInstance;
    }

    private ChatProvider() {
    }


    public List<ChatMessage> getAllMessages(String senderPk, String recevirePk) throws Exception {
        URL url = new URL("https://sustkeys.herokuapp.com/chat/get_messages");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

//        con.setRequestProperty("Content-Type",
//                "application/x-www-form-urlencoded");


        con.setDoOutput(true);

        String jsonInputString = "{\"sender_pk\":\"" + senderPk + "\",\"receiver_pk\":\"" + recevirePk + "\"} ";


        System.out.println("Loading..");
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Done..");


            Object obj = new JSONParser().parse(response.toString());

            JSONArray ja = (JSONArray) obj;


            List<ChatMessage> messages = new ArrayList<>();


            for (Object object : ja) {
                JSONObject groupObject = (JSONObject) object;
                messages.add(new ChatMessage((String) groupObject.get("sender_group_public_key"), (String) groupObject.get("receiver_group_public_key"), (String) groupObject.get("encrypted_message")));
            }
            return messages;
        }

    }

    public boolean sendMessage(String senderPk, String recevirePk, String encryptedMessage) {

        try {
            URL url = new URL("https://sustkeys.herokuapp.com/chat");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");

//        con.setRequestProperty("Content-Type",
//                "application/x-www-form-urlencoded");


            con.setDoOutput(true);


            String jsonInputString = "{\"sender_pk\":\"" + senderPk + "\",\"receiver_pk\":\"" + recevirePk + "\",\"encrypted_message\":\"" + encryptedMessage + "\"} ";

//        String jsonInputString = "{\"sender_pk\":\"" + senderPk + "\",\"receiver_pk\":\"" + recevirePk + "\"} ";

            System.out.println("Loading..");
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response + "Done..");

                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        }


    }


}
