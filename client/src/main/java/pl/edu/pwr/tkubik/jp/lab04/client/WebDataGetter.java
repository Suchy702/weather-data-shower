package pl.edu.pwr.tkubik.jp.lab04.client;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WebDataGetter {
    public String getRawJSONFromAPI(){
        try {
            URL url = new URL("https://danepubliczne.imgw.pl/api/data/synop/format/json");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }

                //Close the scanner
                scanner.close();

                return inline.toString();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}