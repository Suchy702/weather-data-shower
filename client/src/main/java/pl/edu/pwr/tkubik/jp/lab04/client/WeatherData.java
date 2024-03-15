package pl.edu.pwr.tkubik.jp.lab04.client;

public class WeatherData {
    private final int id;
    private final String name;
    private final String date;
    private final String hour;
    private final double temperature;
    private final int windSpeed;
    private final int windDir;
    private final double humidity;
    private final double rainfallSum;
    private final double pressure;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public int getWindDir() {
        return windDir;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getRainfallSum() {
        return rainfallSum;
    }

    public double getPressure() {
        return pressure;
    }

    public WeatherData(int id, String name, String date, String hour, double temperature, int windSpeed, int windDir, double humidity, double rainfallSum, double pressure) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDir = windDir;
        this.humidity = humidity;
        this.rainfallSum = rainfallSum;
        this.pressure = pressure;
    }

}
