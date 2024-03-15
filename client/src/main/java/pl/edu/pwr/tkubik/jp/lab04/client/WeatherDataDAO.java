package pl.edu.pwr.tkubik.jp.lab04.client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataDAO {

    private static final String INSERT_WEATHER_DATA = "INSERT INTO weather_data (id, name, date, hour, temperature, windSpeed, windDir, humidity, rainfallSum, pressure) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_WEATHER_DATA = "SELECT * FROM weather_data";

    private static final String url = "jdbc:mysql://localhost:3306/jbdcdemo";
    private static final String user = "root";
    private static final String password = "";

    public List<WeatherData> getAllWeatherData() {
        List<WeatherData> weatherDataList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WEATHER_DATA)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Odczytywanie danych z wyników zapytania
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String date = resultSet.getString("date");
                        String hour = resultSet.getString("hour");
                        double temperature = resultSet.getDouble("temperature");
                        int windSpeed = resultSet.getInt("windSpeed");
                        int windDir = resultSet.getInt("windDir");
                        double humidity = resultSet.getDouble("humidity");
                        double rainfallSum = resultSet.getDouble("rainfallSum");
                        double pressure = resultSet.getDouble("pressure");

                        // Tworzenie obiektu WeatherData i dodawanie do listy
                        WeatherData weatherData = new WeatherData(id, name, date, hour, temperature, windSpeed, windDir, humidity, rainfallSum, pressure);
                        weatherDataList.add(weatherData);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weatherDataList;
    }

    public void saveWeatherData(ArrayList<WeatherData> weatherDataList) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WEATHER_DATA)) {
                for (WeatherData weatherData : weatherDataList){
                    preparedStatement.setInt(1, weatherData.getId());
                    preparedStatement.setString(2, weatherData.getName());
                    preparedStatement.setString(3, weatherData.getDate());
                    preparedStatement.setString(4, weatherData.getHour());
                    preparedStatement.setDouble(5, weatherData.getTemperature());
                    preparedStatement.setInt(6, weatherData.getWindSpeed());
                    preparedStatement.setInt(7, weatherData.getWindDir());
                    preparedStatement.setDouble(8, weatherData.getHumidity());
                    preparedStatement.setDouble(9, weatherData.getRainfallSum());
                    preparedStatement.setDouble(10, weatherData.getPressure());

                    preparedStatement.addBatch();
                }

                // Wykonaj wsadowe zapytanie SQL
                preparedStatement.executeBatch();

                System.out.println("Rekordy WeatherData zapisane do bazy danych.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
