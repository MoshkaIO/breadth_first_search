package com.example.dz2_testjson;

import org.json.simple.JSONObject;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class Station {
   private int Id; //id станции
    private String Name; //название станции
    private double Longitude; //долгота
    private double Latitude; //широта
    private String LineName; //название линии метро
    private int LineId; //id линии метро
    ArrayList<Integer> Neighbours = new ArrayList<>(); //список станций-соседей (нужно для графа)
    Station(JSONObject newStation, int newId, int newLineId, String newLineName){ //конструктор станции на основании JSON объекта
        //присваиваем полям значения
        Id= newId;
        Name = (String) newStation.get("name"); //преобразуем в строку
        Longitude= (Double) newStation.get("lng"); //преобразуем в дробь
        Latitude = (Double) newStation.get("lat"); //преобразуем в дробь
        LineId=newLineId;
        LineName=newLineName;

    }
    Station(){}

    public int getId() {
        return Id;
    }

    public ArrayList<Integer> getNeighbours() {return Neighbours;} //возвращаем список соседей
    public void ShowN(){ //выводит информацию о соседях
        System.out.print("соседи "+Name+" ");
        for(int i=0;i<Neighbours.size();i++){ //перебирает соседей
            System.out.print(Neighbours.get(i) +" "); //выводит
        }
        System.out.println(" ");
    }

    public String getName() {
        return Name;
    }
    public String getFullName(){
        return Name+" ("+LineName+") "; //возвращаем полное название станции
    }

    public double Distance(Station station){ //расчитываем расстояние между двумя станциями (преобразуем в километры)
        return pow(pow((this.Latitude-station.Latitude)*111,2)+pow((this.Longitude-station.Longitude)*90.2,2),0.5);
    }
    public boolean checkNeighbour(int id){ //проверяем, является ли id соседом
        for (int i=0; i<Neighbours.size(); i++){
            if (Neighbours.get(i)==id){ //если такой сосед найден
                return true; //возвращаем значение истина
            }
        }
        return false; //если не найден то возвращаем значение ложь
    }
    public String getLineInf(){  //возвращает информацию о линии, на которой расположена станция
        return LineName+" линия (id:"+LineId+") ";
    }
    public String getAllInf(){ //возвращает всю информацию о станции
        return Name+" ["+getLineInf()+"]";
    }
    public int getLineId() {
        return LineId;
    } //возвращает id
    public double getLongitude() {return Longitude;} //возвращает долготу
    public double getLatitude() {return Latitude;} //возвращает широту
}
