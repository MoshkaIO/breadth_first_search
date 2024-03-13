package com.example.dz2_testjson;

import org.json.simple.JSONObject;      //библиотека для использования JSON-объекта
import java.util.ArrayList;             //для использование ArrayList-а

public class LineOfMetro {
    private int Id; //id линии
    private String HexColour; //цвет линии
    private String Name; //название линии
    ArrayList<Station> StationsList = new ArrayList<>();
    LineOfMetro(JSONObject newLine, int newId){ //конструктор по JSON объекту
        //Id= (Integer) newLine.get("id");
        //Id=Integer.parseInt((String) newLine.get("id")); //id в json почему-то указан как строка
        Id=newId;
        HexColour = (String) newLine.get("hex_color");
        Name = (String) newLine.get("name");
    }
    LineOfMetro(String newName, int newId, ArrayList<Integer> stations){ //конструктор по списку id станций
        Name=newName;
        Id=newId;
        for (int i=0; i<stations.size(); i++){ //устанавлием список станций по id
            int st = stations.get(i);
            StationsList.add(MetroModel.getCity().getAllStations().get(st));
        }
    }
    public void setStationsNeighbours(){ //формируем часть графа по отдельным линиям метро
        for (int i=0; i<StationsList.size()-1; i++){
            StationsList.get(i).getNeighbours().add(StationsList.get(i+1).getId()); //делаем друг друга соседями
            StationsList.get(i+1).getNeighbours().add(StationsList.get(i).getId());
            //StationsList.get(i).ShowN(); //выводит информацию о соседях
        }
        ConnectCoilsCheck(); //проверяем, является ли линия кольцевой
    }
    public void ConnectCoilsCheck(){ //проверка закольцованности линии
        if (StationsList.get(0).getName().equals(StationsList.get(StationsList.size()-1).getName())){ //если линия начинается и заканчивается одной станцией
            StationsList.remove(StationsList.size()-1); //это станция-дублёр. Она говорит нам о том, что линия кольцевая.
            //Её нужно убрать и замкнуть концы кольцевой
            StationsList.get(0).getNeighbours().add(StationsList.get(StationsList.size()-1).getId()); //замыкаем

            StationsList.get(StationsList.size()-1).getNeighbours().add(StationsList.get(0).getId()); //замыкаем

        }
    }
    public ArrayList<Station> getStationsList() {
        return StationsList;
    } //возвращает список станций
    public String getHexColour() {
        return HexColour;
    } //возвращает цвет линии
}
