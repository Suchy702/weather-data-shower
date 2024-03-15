package pl.edu.pwr.tkubik.jp.lab04.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pl.edu.pwr.tkubik.jp.lab04.client.Client;
import pl.edu.pwr.tkubik.jp.lab04.client.WeatherData;


import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    public ComboBox<String> pickStation;
    public CheckBox temperature;
    public CheckBox windSpeed;
    public CheckBox windDir;
    public CheckBox humility;
    public CheckBox rainfallSum;
    public CheckBox pressure;
    public ChoiceBox<String> pickChartParameter = new ChoiceBox<>();
    public Button showData;
    public TableView<WeatherData> table = new TableView<>();
    public LineChart<?, ?> chart;

    private final List<String> checkboxParams = List.of("temperatura", "predkosc wiatru", "kierunek wiatru", "wilgotnosc", "suma opadow", "cisnienie");

    Client client = new Client();

    private Set<String> getParamsToShow(){
        Set<String> paramsToShow = new HashSet<>();
        List<Boolean> checkboxes = List.of(temperature.isSelected(), windSpeed.isSelected(), windDir.isSelected(),
                humility.isSelected(), rainfallSum.isSelected(), pressure.isSelected());
        for (int i = 0; i < checkboxParams.size(); i++)
            if (checkboxes.get(i))
                paramsToShow.add(checkboxParams.get(i));
        return paramsToShow;
    }


    private List<WeatherData> getStationData(String station){
        List<WeatherData> stationData = new ArrayList<>();
        for (WeatherData weatherData : client.getWeatherData())
            if (Objects.equals(weatherData.getName(), station))
                stationData.add(weatherData);
        return stationData;
    }

    private void createTable(Set<String> paramsToShow, ObservableList<WeatherData> stationWeatherData){
        table.getColumns().clear();
        List<String> valNames = new ArrayList<>(List.of("temperature", "windSpeed", "windDir", "humidity", "rainfallSum", "pressure"));
        List<String> tabNames = new ArrayList<>(List.of("temperatura", "predkość wiatru", "kierunek wiatru", "wilgotność", "suma opadów", "ciśnienie"));


        //Date column
        TableColumn<WeatherData, String> dateColumn = new TableColumn<>("Data");
        dateColumn.setMinWidth(60);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.getColumns().add(dateColumn);

        for (int idx = 0; idx < valNames.size(); idx++){
            if (paramsToShow.contains(checkboxParams.get(idx))){
                TableColumn<WeatherData, Double> columnToAdd = new TableColumn<>(tabNames.get(idx));
                columnToAdd.setMinWidth(60);
                columnToAdd.setCellValueFactory(new PropertyValueFactory<>(valNames.get(idx)));
                table.getColumns().add(columnToAdd);
            }
        }

        table.setItems(stationWeatherData);
    }

    private void createChart(String chartPick, List<WeatherData> stationData){
        chart.getData().clear();

        XYChart.Series series = new XYChart.Series();

        switch (chartPick){
            case "temperatura":
                for (WeatherData weatherData : stationData)
                    series.getData().add(new XYChart.Data(weatherData.getDate(), weatherData.getTemperature()));
                break;
            case "prędkośc wiatru":
                for (WeatherData weatherData : stationData)
                    series.getData().add(new XYChart.Data(weatherData.getDate(), weatherData.getWindSpeed()));
                break;
            case "wilgotność":
                for (WeatherData weatherData : stationData)
                    series.getData().add(new XYChart.Data(weatherData.getDate(), weatherData.getHumidity()));
                break;
            case "suma opadów":
                for (WeatherData weatherData : stationData)
                    series.getData().add(new XYChart.Data(weatherData.getDate(), weatherData.getRainfallSum()));
                break;
            case "ciśnienie":
                for (WeatherData weatherData : stationData)
                    series.getData().add(new XYChart.Data(weatherData.getDate(), weatherData.getPressure()));
                break;
        }

        chart.getData().add(series);
    }

    public void showGUIData(){
        String station = pickStation.getValue();
        String chartPick = pickChartParameter.getValue();
        Set<String> paramsToShow = getParamsToShow();
        List<WeatherData> stationData = getStationData(station);
        ObservableList<WeatherData> stationWeatherData = FXCollections.observableArrayList(stationData);
        createTable(paramsToShow, stationWeatherData);
        createChart(chartPick, stationData);

    }

    private void initPickChart(){
        List<String> pickParams = List.of("temperatura", "prędkośc wiatru", "wilgotność", "suma opadów", "ciśnienie");
        pickChartParameter.getItems().addAll(pickParams);
        pickChartParameter.setValue(pickParams.get(0));
    }

    private void initPickStation(){
        List<String> params = List.of("Białystok", "Bielsko Biała", "Chojnice", "Częstochowa", "Elbląg",
                "Gdańsk", "Gorzów", "Hel", "Jelenia Góra", "Kalisz", "Kasprowy Wierch", "Katowice", "Kętrzyn", "Kielce",
                "Kłodzko", "Koło", "Kołobrzeg", "Koszalin", "Kozienice", "Kraków", "Krosno", "Legnica", "Lesko",
                "Leszno", "Lębork", "Lublin", "Łeba", "Łódź", "Mikołajki", "Mława", "Nowy Sącz", "Olsztyn", "Opole",
                "Ostrołęka", "Piła", "Platforma", "Płock", "Poznań", "Przemyśl", "Racibórz", "Resko", "Rzeszów",
                "Sandomierz", "Siedlce", "Słubice", "Sulejów", "Suwałki", "Szczecin", "Szczecinek", "Śnieżka",
                "Świnoujście", "Tarnów", "Terespol", "Toruń", "Ustka", "Warszawa", "Wieluń", "Włodawa", "Wrocław",
                "Zakopane", "Zamość", "Zielona Góra");
        pickStation.getItems().addAll(params);
        pickStation.setValue(params.get(0));
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPickChart();
        initPickStation();

        temperature.setSelected(true);
        ObservableList<WeatherData> stationWeatherData = FXCollections.observableArrayList(getStationData("Białystok"));
        createTable(getParamsToShow(), stationWeatherData);

        createChart("temperatura", getStationData("Białystok"));
    }
}