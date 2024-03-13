package com.example.dz2_testjson;

import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class MetroMap {//создаёт графику для карты метро
    private static double HorPos=0.5; //доля прокрутки карты (середина по горизонтали)
    private static double VerPor=1; //доля прокрутки карты (конец по вертикали)
    private static double  k = 1800;
    private static double Xcor=55; //константа для корриектировки x
    private static double Ycor=37; //константа для корриектировки x
    //private static double WinCorX=1900;
    private static ArrayList<Color> LineColor= new ArrayList();
    //private static City city= new City();

    private static Scanner scanner = new Scanner(System.in);

     private static ScrollPane scroller = new ScrollPane();
    private static SimpleStringProperty textProperty = new SimpleStringProperty();
    private static Stage stage;
    public static void GenerateGraphic(ArrayList<Node> allNodes, City city){
        CreateLinesColors(city);
        GenerateLines(allNodes, city);
        GenerateFinalWayLine(allNodes);

    }
     private static void GenerateFinalWayLine (ArrayList<Node> allNodes){
        LineOfMetro FinalWay = new LineOfMetro("МАРШРУТ по BFS",100,MetroModel.getWay());
        GenerateOneLine(FinalWay,Color.BLACK,allNodes,5);
    }
    public static void Start(Stage stage1){ //основной запуск, выполняется единожды
        stage=stage1; //сохраняем stage
        MetroModel.MetroLoad(); //загрузка метро города, которая потом запустит построение сцены
    }
    public static void StageSceneGenerator(){ //функция построения сцены, запускается при каждом обновлении

        ArrayList<Node> AllNodes = new ArrayList(); //создаём список базовых объектов Node

        Pane metr= new Pane(); //карта
        FlowPane answer = new FlowPane(); //текстовая панель
        //answer.setOrientation(Orientation.VERTICAL);
        FlowPane ButtonsPane = new FlowPane(); //панель с кнопками

        City city = MetroModel.getCity(); //запрашиваем объект-город
        MetroModel.FindWay(city,answer); //запуск поиска пути

        GenerateButtons(ButtonsPane); //создаём панель с кнопками
        GenerateGraphic(AllNodes,city); //создаём графику для карты метрополитена города


       // test(answer); //передаём его в функцию, которая заполнит его
        metr.getChildren().addAll(AllNodes); //добавляем его к Pane
        scroller = new ScrollPane(metr); //создаём новую панель с прокруткой
        scroller.setPrefViewportHeight(600); //задаём высоту
        scroller.setPrefViewportWidth(700); //задаём ширину
        scroller.setHvalue(HorPos); //задаём положение ползунков для прокрутки по ширине
        scroller.setVvalue(VerPor); //задаём положение ползунков для прокрутки по высоте

        FlowPane root = new FlowPane(Orientation.VERTICAL,20,20,scroller,answer,ButtonsPane); //размещаем панели вертикально в FlowPane
        Scene scene1 = new Scene(root, 800, 800); //создаём "сцену", берём за основу объект Pane
        stage.setTitle("METROOOO"); //название окна
        stage.setScene(scene1); //устанавливаем сцену
        stage.show(); //запускаем
    }
    public static void CreateLinesColors(City city){ //заполняем список цветов линий
        for (int i=0; i<city.getMetroLine().size();i++){
            LineColor.add(Color.web(city.getMetroLine().get(i).getHexColour())); //вносим в список новый цвет
            //(их номера в списке цветов совпадают с id соответствующих линий
        }
    }
    public static void GenerateStationCircle(double x,double y,ArrayList<Node> allNodes){
        Circle newCircle = new Circle();//создание круга
        newCircle.setCenterX(x);//задаём координату X и масштабируем с помощью k
        newCircle.setCenterY(y);//задаём координату Y и масштабируем с помощью k
        newCircle.setRadius(3); //задаём радиус круга
        newCircle.setStroke(Color.GRAY); //задаём цвет
        allNodes.add(newCircle);
    }
    private static void GenerateLines(ArrayList<Node> allNodes, City city){ //генерируем линии метро

        for (int i=0; i<city.getMetroLine().size(); i++){ //перебираем линии
            Color c =Color.BLUE; //запасной цвет на случай ошибки
            if (i<LineColor.size()){ //проверяем есть ли цвет для данной линии в списке цветов (на всякий случай)
                c=LineColor.get(i); //если есть то применяем
            }
            GenerateOneLine(city.getMetroLine().get(i),c, allNodes,3); //генерируем линию
            if (city.getMetroLine().get(i).getStationsList().get(0).checkNeighbour(city.getMetroLine().get(i).getStationsList().get(city.getMetroLine().get(i).getStationsList().size()-1).getId())){
                GenerateCoilConnection(city,allNodes,i,c); //если данная линия кольцевая
            }
        }

    }
    public static void GenerateOneLine(LineOfMetro line, Color c, ArrayList<Node> allNodes, int lineWidth){ //генерация одной линии
        for (int j=0; j<line.getStationsList().size()-1; j++){ //перебираем станции одной линии
            double x1=(line.getStationsList().get(j).getLatitude()-Xcor)*k; //вычисляем координаты
            double y1=(line.getStationsList().get(j).getLongitude()-Ycor)*k; //и приводим их к виду для использования
            double x2=(line.getStationsList().get(j+1).getLatitude()-Xcor)*k;//при построении линий метро
            double y2=(line.getStationsList().get(j+1).getLongitude()-Ycor)*k;
            x1=x1+(k-x1)*2; //переворачиваем x (т.к. отсчёт координат ведётся из верхнего левого угла
            x2=x2+(k-x2)*2; //а не нижнего левого как в случае с Землёй
            Line newLine =  new Line(y1, x1,y2,x2); //координаты поменяны местами для ещё одного "зеркала"
            GenerateStationCircle(y1,x1,allNodes);
            GenerateStationCircle(y2,x2,allNodes);
            newLine.setStrokeWidth(lineWidth); //устанавливаем ширину линий в 3 пикселя
            newLine.setStroke(c);//покраска линий в синий цвет
            allNodes.add(newLine); //добавления отрезка в список объектов
        }

    }
    public static void GenerateCoilConnection(City city, ArrayList<Node> allNodes, int i, Color c){ //замыкаем кольцо
        int lastIndex=city.getMetroLine().get(i).getStationsList().size()-1;
        //действуем аналогично фунции  GenerateOneLine:
        double x1=(city.getMetroLine().get(i).getStationsList().get(0).getLatitude()-Xcor)*k;
        double y1=(city.getMetroLine().get(i).getStationsList().get(0).getLongitude()-Ycor)*k;
        double x2=(city.getMetroLine().get(i).getStationsList().get(lastIndex).getLatitude()-Xcor)*k;
        double y2=(city.getMetroLine().get(i).getStationsList().get(lastIndex).getLongitude()-Ycor)*k;
        x1=x1+(k-x1)*2;
        x2=x2+(k-x2)*2;
        Line newLine =  new Line(y1, x1,y2,x2);
        GenerateStationCircle(y1,x1,allNodes);
        GenerateStationCircle(y2,x2,allNodes);
        newLine.setStrokeWidth(3); //устанавливаем ширину линий в 3 пикселя
        newLine.setStroke(c);//покраска линий в синий цвет
        allNodes.add(newLine); //добавления отрезка в список объектов
    }
    public static void GenerateRezultText(City city, ArrayList<Integer> way, Pane answer){ //создание текстовой панели
        System.out.println("Конечный маршрут состоит из: ");
        String RezultWay = " ";
        for (int i=0; i<way.size(); i++){
            RezultWay=RezultWay+city.getAllStations().get(way.get(i)).getFullName()+" - ";
        }
        System.out.println(RezultWay);
        Label MessageLabel = new Label(" /// ");
        SimpleStringProperty textProperty = new SimpleStringProperty();
        MessageLabel.textProperty().bind(textProperty);
        Label StartFinishText = new Label();
        StartFinishText.setText("от "+city.getAllStations().get(MetroModel.getStartStation()).getAllInf()+
                " до "+city.getAllStations().get(MetroModel.getFinishStation()).getAllInf()+"(маршрут обозначен ЧЁРНОЙ линией)    Хопов: "+(way.size()-1));
        Label WayText = new Label (RezultWay);
        ScrollPane TextScroller = new ScrollPane(WayText);
        TextScroller.setPrefViewportHeight(30);
        TextScroller.setPrefViewportWidth(700);
        answer.getChildren().addAll(MessageLabel,StartFinishText,TextScroller);
    }
    public static void GenerateButtons(Pane ButtonPane){ //создание панели с кнопками
        Button CreateWayButton = new Button("Построить маршурт"); //создание кнопки построения маршрута
        HelloController.onCreateWayButtonClick(CreateWayButton); //размещаем в контроллере  реакцию на нажатие
        ButtonPane.getChildren().add(CreateWayButton); //добавляем кнопку на панель

        Button ZoomPlus = new Button("Zoom +"); //создание кнопки для приближения карты
        HelloController.onZoomPlusButtonClick(ZoomPlus);//размещаем в контроллере  реакцию на нажатие
        ButtonPane.getChildren().add(ZoomPlus);  //добавляем кнопку на панель

        Button ZoomMinus = new Button("Zoom -"); //создание кнопки для отдаления карты
        HelloController.onZoomMinusButtonClick(ZoomMinus);//размещаем в контроллере  реакцию на нажатие
        ButtonPane.getChildren().add(ZoomMinus); //добавляем кнопку на панель
    }
    public static void ZoomPlus(){//масштабируем карту (приближаем)
        k=k*1.1;
    }
    public static void ZoomMinus(){ //масштабируем карту (отдаляем)
        k=k/1.1;
    }
    public static void UpdateScrollPosition(){ //обновляем положение ползунков (которое мог изменить пользователь)
        HorPos= scroller.getHvalue(); //обновляем положение горизонтального ползунка
        VerPor=scroller.getVvalue(); //обновляем положение вертикального ползунка
    }
    public static String MessageStartStationToConsole(){ //запрос у пользователя начальной точки маршрута
        System.out.println("Введите станцию отправления: ");
        String rezult = scanner.nextLine(); //считываем строку из консоли
        System.out.println("Ответ принят ");
        return rezult;  //возвращаем результат
    }
    public static String MessageFinishStationToConsole(){ //запрос у пользователя конечной точки маршрута
        System.out.println("Введите станцию прибытия: ");
        String rezult = scanner.nextLine(); //считываем строку из консоли
        System.out.println("Ответ принят ");
        return rezult; //возвращаем результат
    }
    public static String MessageCorrectStationNameToConsole(){ //запрос у пользователя корректной точки маршрута
        System.out.println("Такая станция не обнаружена. Введите название ещё раз: ");
        String rezult = scanner.nextLine(); //считываем строку из консоли
        System.out.println("Ответ принят ");
        return rezult; //возвращаем результат
    }
    public static int MessageLineNumberToConsole(String SearchRezultStr){ //спрашиваем конкретный номер линии
        System.out.println("Станция с таким названием есть на нескольких ветках:");
        System.out.println(SearchRezultStr);
        System.out.println("Введите id требуемой ветки: ");
        return scanner.nextInt(); //считываем число из консоли и возвращаем результат
    }

}
