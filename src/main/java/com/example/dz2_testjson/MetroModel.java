package com.example.dz2_testjson;

import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class MetroModel { //
    private static City city = new City(); //здесь хранится город с метрополитеном (по заданию это Москва
    private static int StartStation=0; // id станции отправления по умолчанию
    private static int FinishStation=287; //id станции прибытия по умолчанию
    private static ArrayList<Integer> Way = new ArrayList<>(); //Здесь хранится последовательность id станций найденного маршрута
    public static void FindWay(City city, Pane answer){ //функция, контролирующая поиск пути
        Graph graph = new Graph(city); //создаём граф на основании метрополитена (по заданию г. Москва)
        Way = BFS.MatrixSearch(StartStation,FinishStation,graph,city); //получаем маршрут
        MetroMap.GenerateRezultText(city,Way,answer); //запуск графической генерации результата
    }
    public static void MetroLoad(){ //восстанавливаем структуру метро
        city = JsonSimpleParser.Parse("metro.txt"); //парсим файл со станциями
        city.ConnectLines(); //соединяем линии
        Dialog(); //переговоры по поводу построения нового маршрута
        StationsHunter.FindAllNeighbours(city); //соединяем станции пересадками
        MetroMap.StageSceneGenerator(); //запуск функции построения сцены

    }

    public static City getCity() { //функция, возвращающая объект-город
        return city;
    }
    public static ArrayList<Station> SearchForStation(String stationName){ //поиск станций с конкретным именем
        ArrayList<Station> StationWithThisName = new ArrayList<>(); //список станций с таким именем
        for (int i=0; i<city.getAllStations().size(); i++){ //перебор всех станций
            if (city.getAllStations().get(i).getName().equals(stationName)){ //
                StationWithThisName.add(city.getAllStations().get(i));
            }
        }
        return StationWithThisName;
    }
    public static void Dialog() {
            String station = MetroMap.MessageStartStationToConsole();
            StartStation = CorrectStation(station);
            System.out.println("принял");
            station = MetroMap.MessageFinishStationToConsole();
            FinishStation = CorrectStation(station);
            System.out.println("принял");
    }
    public static int CorrectStation(String station){ //запрос корректного названия станции
        while (true) { //цикл не завершится, пока пользователь не введёт корректное название станции
            ArrayList<Station> SearhRezult = SearchForStation(station);
            if (SearhRezult.size() > 0) { //если найдена станция с таким названием
                if (SearhRezult.size() > 1) { //если найдено несколько станций с таким названием
                   int LineId=DialogLineOfStation(SearhRezult); //уточняем id её линии (на одной линии  не бывает станций с одинаковым названием)
                   for (int i=0; i<SearhRezult.size();i++){ //ищем единственную станцию на линии с LideId
                       if (SearhRezult.get(i).getLineId()==LineId){ //когда нашли
                           return SearhRezult.get(i).getId(); //возвращаем id станции
                       }
                   }
                }
                else return SearchForStation(station).get(0).getId(); //если найдена всего 1 станция

            }
            else {
                station = MetroMap.MessageCorrectStationNameToConsole(); //сообщение о некорректно введённом названии и запрос нового
            }
        }
    }
    public static int DialogLineOfStation(ArrayList<Station> SearhRezult){ //запрашиваем конкретный номер линии из нескольких возможных
        int lineNumber = 0;
        while(true){ //цикл не завершится, пока пользователь не введёт корректный номер линии из предложенных
            String SearchRezultStr=""; //создаём строку с линиями для выбора пользователем
            for (int i=0; i<SearhRezult.size();i++){ //заполняем строку информацией о линиях, на которых есть искомая станция
                SearchRezultStr=SearchRezultStr+SearhRezult.get(i).getLineInf()+" ";
            }
             lineNumber= MetroMap.MessageLineNumberToConsole(SearchRezultStr); //показываем пользователю возможные линии с искомой станцией и просим выбрать
            for (int i =0; i<SearhRezult.size(); i++){ //проверяем, корректен ли выбор пользователя
                if (SearhRezult.get(i).getLineId()==lineNumber){ //если да (такая линия существует)
                    return lineNumber; //то возвращаем её id
                }
            }

        }
    }
    public static int getStartStation() {
        return StartStation;
    }

    public static int getFinishStation() {
        return FinishStation;
    }

    public static ArrayList<Integer> getWay() {
        return Way;
    }
}
