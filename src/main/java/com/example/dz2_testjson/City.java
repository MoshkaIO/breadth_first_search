package com.example.dz2_testjson;

import org.json.simple.JSONObject; //библиотека для использования JSON-объекта
import java.util.ArrayList;     //для использование ArrayList-а

public class City {
    private int Id; //id города
    private String Name; //название города
    private ArrayList<LineOfMetro> MetroLine = new ArrayList<>(); //список линий метро
    private ArrayList<Station> AllStations = new ArrayList<>(); //список всех станций
    // (им удобнее пользоваться, чем каждый раз перебирать все линии в поисках нужной станции)
    public City(JSONObject newCity){ //конструктор города по JSON объекту
        Id = Integer.parseInt((String) newCity.get("id")); //устанавливаем новый id
        Name = (String) newCity.get("name"); //устанавливаем новое имя
    }
    public City(){}

    public ArrayList<LineOfMetro> getMetroLine() {
        return MetroLine;
    } //возвращает список линий метро
    public ArrayList<Station> getAllStations() {
        return AllStations;
    } //возвращает список всех станций
    public void ConnectLines(){ //соединяем линии
        for (int i=0; i<MetroLine.size();i++){ //перебираем линии
            MetroLine.get(i).setStationsNeighbours(); //вызываем функцию соединения соседних станций линии
        }
    }
}
