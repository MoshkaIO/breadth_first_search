package com.example.dz2_testjson;

import javafx.event.Event; //подключаемые библиотеки для различных целей
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class HelloController {
    public static void onCreateWayButtonClick(Button button){ //установка кнопки создания маршрута
        button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) { //что нужно сделать при нажании кнопки
                MetroMap.UpdateScrollPosition();  //обновить позицию ползунка
                MetroMap.StageSceneGenerator();  //перегенерировать блок с картой
            }
        });
    }
    public static void onZoomPlusButtonClick(Button button){ //установка кнопки увеличения карты
        button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) { //что нужно сделать при нажании кнопки
                MetroMap.UpdateScrollPosition(); //обновить позицию ползунка
                MetroMap.ZoomPlus(); //увеличить карту
                MetroMap.StageSceneGenerator(); //перегенерировать блок с картой
            }
        });
    }
    public static void onZoomMinusButtonClick(Button button){ //установка кнопки уменьшения карты
        button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) { //что нужно сделать при нажании кнопки
                MetroMap.UpdateScrollPosition(); //обновить позицию ползунка
                MetroMap.ZoomMinus();  //уменьшить карту
                MetroMap.StageSceneGenerator(); //перегенерировать блок с картой
            }
        });
    }
}