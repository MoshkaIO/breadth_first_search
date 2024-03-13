package com.example.dz2_testjson;

import java.util.ArrayList; //возможность использовать ArrayList

public class StationsHunter { //класс, который предназначен для автоматического поиска пересадок
    public static void FindAllNeighbours(City city){ //Главная функция, из которой будут вызываться все остальные
        CheckAllDistances(city.getAllStations()); //вызов функции, которая перебирает пересадки
    }
    public static void CheckAllDistances(ArrayList<Station> AllStations){ //перебираем все пары станций и ищем пересадки
        //int count=0; //счётчик числа пересадок
        for (int i=0; i<AllStations.size(); i++){ //перебираем первую сравниваемую станцию
            for (int j=0; j<AllStations.size(); j++){ //перебираем вторую сравниваемую станцию (итого: все пары станций)
                if (AllStations.get(i).Distance(AllStations.get(j))<1.0){ //всё что на расстоянии 1 км
                    if((!AllStations.get(i).checkNeighbour(j))&&(i!=j)){ // и ещё не является пересадкой
                        AllStations.get(i).getNeighbours().add(j);//сделать пересадкой
                        AllStations.get(j).getNeighbours().add(i); //для обеих станций
                        //count++; //увеличиваем число пересадок
                    }
                }
            }
        }
        // System.out.println("Число найденных пересадок: "+count); //информация в консоль для отладки
        //Здесь надо бы сделать сохранялку в файл(
    }
    //сделать могла быть функция для записи собственного JSON файла с учётом пересадок, но выполнение функции поиска происходит
    //достаточно быстро
}
