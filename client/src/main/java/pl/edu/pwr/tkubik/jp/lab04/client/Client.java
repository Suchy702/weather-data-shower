package pl.edu.pwr.tkubik.jp.lab04.client;

/*
wczytac dane z internetu
sparsowac JSONa od WeatherData
otworzyc baze danych i ewentualnie dopisac do niej nowe dane
pobrac wszystkie dane z bazy danych i sparsowac je do WeatherData
dzialac juz tylko na wygodnych WeatherData
*/

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Client {
    WebDataGetter webDataGetter = new WebDataGetter();
    WeatherDataDAO weatherDataDAO = new WeatherDataDAO();

    ArrayList<WeatherData> clientWeatherData;

    private WeatherData createWeatherDataFromJSONelement(JsonNode e){
        int id = e.get("id_stacji").asInt();
        String name = e.get("stacja").asText();
        String date = e.get("data_pomiaru").asText();
        String hour = e.get("godzina_pomiaru").asText();
        double temperature = e.get("temperatura").asDouble();
        int windSpeed = e.get("predkosc_wiatru").asInt();
        int windDir = e.get("kierunek_wiatru").asInt();
        double humidity = e.get("wilgotnosc_wzgledna").asDouble();
        double rainfallSum = e.get("suma_opadu").asDouble();
        double pressure = e.get("cisnienie").asDouble();
        return new WeatherData(id, name, date, hour, temperature, windSpeed, windDir, humidity, rainfallSum, pressure);
    }

    public ArrayList<WeatherData> getWeatherData(){
        return clientWeatherData;
    }

    private ArrayList<WeatherData> parseJSONtoWeatherData(JsonNode jsonNode){
        ArrayList<WeatherData> data = new ArrayList<>();

        for (JsonNode element : jsonNode)
             data.add(createWeatherDataFromJSONelement(element));

        return data;
    }

    private boolean needToUpdate(ArrayList<WeatherData> todayWeatherData, ArrayList<WeatherData> allWeatherData){
        HashSet<String> previousDates = new HashSet<>();
        for (WeatherData weatherData : allWeatherData)
            previousDates.add(weatherData.getDate());

        return !previousDates.contains(todayWeatherData.get(0).getDate());
    }

    public Client(){
        String rawJSON = webDataGetter.getRawJSONFromAPI();
        ArrayList<WeatherData> todayWeatherData = parseJSONtoWeatherData(Objects.requireNonNull(JSONParser.parseJSONToNode(rawJSON)));
        ArrayList<WeatherData> allWeatherDataData = (ArrayList<WeatherData>) weatherDataDAO.getAllWeatherData();

        if (needToUpdate(todayWeatherData, allWeatherDataData)){
            weatherDataDAO.saveWeatherData(todayWeatherData);
            allWeatherDataData.addAll(todayWeatherData);
        }

        this.clientWeatherData = allWeatherDataData;
    }


    public static void main(String[] args) {
        Client c = new Client();

        for (WeatherData w : c.getWeatherData())
            System.out.println(w.getName());

    }

}
