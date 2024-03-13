package com.example.dz2_testjson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonSimpleParser //парсинг файла
{
    public static City Parse(String FileName){ //проверка корректности файла
        JSONParser parser = new JSONParser(); //создаём парсер
        System.out.println("проверка файла");
        try (FileReader reader = new FileReader(FileName)){ //пробуем создать поток чтения из файла
           JSONObject city = (JSONObject) parser.parse(reader); //получаем из файла JSON-объект: город
          // String name = (String) city.get("name"); //отладка: проверяем название города, в котором расположен описываемый метрополитен
          // System.out.println(name); //выводим название города в консоль
           JSONArray Lines = (JSONArray) city.get("lines"); //получаем JSON-массив: линии метро
            //System.out.println("Проверка наличия линий:"); // отладка: проверяем найденные в файле линии
            /* for (Object it : Lines){ //перебираем массив линий
              JSONObject Line =  (JSONObject) it; //получаем  JSON объект (линию метро)
               System.out.println(Line.get("name")+" "); //выводим информацию в консоль
           } */
        }catch (Exception e) { //если вызвалось исключение
            System.out.println("ошибка парсинга: "+e.toString()); //сообщаем в консоль об ошибке
            return null; //ничего не возвращаем
        }
        return ParseAll(FileName); //если проверка прошла удачно вывываем полноценную функцию парсинга
    }
    public static City ParseAll(String FileName){ //парсинг
        City newCity = null;
        int CountOfStations=0; //счётчик станций (для назначения id)
        try (FileReader reader = new FileReader(FileName)){//пробуем создать поток чтения из файла
            JSONParser parser = new JSONParser(); //создаём парсер
           // System.out.println("запуск парсинга"); // отладка: выводим в консоль уведомление о начале парсинга
            JSONObject city = (JSONObject) parser.parse(reader);  //получаем из файла JSON-объект: город
             newCity = new City(city); //вызываем конструктор класса City
            JSONArray Lines = (JSONArray) city.get("lines"); //получаем JSON-массив: линии метро
            for (Object i : Lines){ //перебираем линии метро
                //System.out.println("Линия: "+ ((JSONObject) i).get("name"));
                LineOfMetro newLine =  new LineOfMetro((JSONObject) i,newCity.getMetroLine().size()); //вызываем конструктор класса Line
                JSONArray Stations = (JSONArray) ((JSONObject) i).get("stations"); //получаем JSON-массив: станции метро конкретной линии
                for (Object j: Stations){ //перебираем станции конкретной линии
                   // System.out.println(CountOfStations+"Станция: "+ ((JSONObject) j).get("name"));
                    Station newStation = new Station((JSONObject) j, CountOfStations,newCity.getMetroLine().size(),(String)((JSONObject) i).get("name"));
                    //создаём новую станцию
                    newLine.getStationsList().add(newStation); //добавляем станцию в список станций линии
                    newCity.getAllStations().add(newStation); //добавляем станцию в список станций города
                    CountOfStations++; //увеличиваем счётчик станций на 1

                }
                newCity.getMetroLine().add(newLine); //добавляем в метрополитен новую линию
            }
        }catch (Exception e) {//если вызвалось исключение (хотя до этого проверка уже проводилась)
            System.out.println("ошибка парсинга: "+e.toString()); //уведомление в консоль
        }
        //System.out.println("Всего станций: "+CountOfStations); //отладка: выводим в консоль подсчитанное число станций
        return newCity; //возвращаем созданный город-объект
    }
}