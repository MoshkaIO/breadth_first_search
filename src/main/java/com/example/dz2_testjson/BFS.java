package com.example.dz2_testjson;

import java.util.ArrayList; //подключаемые библиотеки для различных целей
import java.util.LinkedList;
import java.util.List;
import static java.util.Collections.swap;

public class BFS {
    private static Graph graph; //рассматриваемый граф
    private static ArrayList<PrevNext> SectionOfWay = new ArrayList<>(); //список пар предок-наследник
    private static  int ISVISITED=-1; //значение "просмотрен" для вершины графа
    public static ArrayList<Integer> MatrixSearch(int startId, int finishId, Graph newGraph,City city){ //создание и работа с очередью
       graph=newGraph; //записываем переданный граф в переменную
        LinkedList<Integer> Queue = new LinkedList<>(); //создаём очередь
        Queue.add(startId); //добавляем в неё начальный узел
        boolean rezult = false;
        while (Queue.size()>0){
            int check=Queue.get(0); //первый проверяемый из очереди
            //System.out.println("Проверяем станцию:   "+check+" "+city.getAllStations().get(check).getName()); //для отладки
            if (check==finishId){  //если он искомый
                //System.out.println("Искомая станция найдена!"); //для отладки
                rezult=true; //отмечаем, что станция найдена
                break; //выходим из бесконечного цикла
            }
            SetVisited(check); //теперь для всех он посещён
            AddChild(Queue,Queue.get(0),city);
            Queue.remove(0);
        }
        if (rezult==true){ //если станция найдена
            return FindWay(startId,finishId);
        }
        else { //если станция не была найдена
            System.out.println("Путь до станции не был найден!"); //уведомление в консоль для отладки
            return null; //возвращаем нулевое значение
        }

    }
    private static void AddChild(List<Integer> q,int index,City city){ //добавлеяем в очередь всех соседей index-а
        //System.out.print("Его непроверенные соседи: "); //для отладки
        for (int i=0; i<graph.getGraphMatrix().length; i++){
            if (graph.getGraphMatrix()[index][i]>0){ //есть путь
                //System.out.print(i+" "+city.getAllStations().get(i).getName()+"   ,"); //для отладки
                q.add(i); //добавить в очередь
                SetVisited(i); //посещён
                SectionOfWay.add(new PrevNext(index,i)); //добавляем рассмотренный кусок маршрута
                //graph.getGraphMatrix()[index][i]=ISVISITED;
            }
        }
        System.out.println("   ;");
    }
    private static void SetVisited(int index){ //отмечаем вершину графа как посещённую
        for (int i=0; i<graph.getGraphMatrix().length; i++){ //для этого перебираем весь столбик матрицы достижимости
            graph.getGraphMatrix()[i][index]=ISVISITED; //устанавливаем значение ПОСЕЩЁН (-1) для всего столбика
        }
    }
    public static ArrayList<Integer> FindWay(int start,int finish){ //генерируем полноценный маршрут
       ArrayList<Integer> Way = new ArrayList<>(); //массив int-ов индексов.
       Way.add(finish); //добавляем в маршурт конечную вершину
       int target=finish; //
       while (target!=start){ //до тех пор пока последней найденной вершиной не будет "стартовая"
           for (int i=0; i<SectionOfWay.size();i++){ //ищем откуда мы пришли в последнюю точнку маршрута
               if (SectionOfWay.get(i).getNext()==target){ //если найден предок последней найденной вершины
                   target=SectionOfWay.get(i).getPrevious(); // делаем его последней найненной вершиной
                   Way.add(target); //добавляем найденный "кусочек" маршрута в маршрут
                   break; //выходим из цикла for
               }
           }
       }
       for (int i=0; i<Way.size()/2;i++){ //отзеркаливаем
           swap(Way,i,Way.size()-1-i);
       }
       return Way; //возвращаем найденный маршрут
    }
}
