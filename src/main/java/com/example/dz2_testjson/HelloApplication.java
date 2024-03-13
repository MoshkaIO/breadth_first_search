package com.example.dz2_testjson;

import javafx.application.Application;//подключаемые библиотеки для различных целей
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application { //класс, который используется по умолчанию и его лучше не трогать
    @Override
    public void start(Stage stage) throws IOException { //запуск программы
        MetroMap.Start(stage); //вызываем функцию запуска

    }
    public static void main(String[] args) {
        launch();
    } //загрузка
}