package com.example.dz2_testjson;

public class Graph { //создаём граф при помощи двумерного массива
    private int [][] MatrixOfNeigh; //матрица смежности
    Graph(City city){ //конструктор графа, берёт информацию о метро города
        int newSize=city.getAllStations().size();
        MatrixOfNeigh = new int[newSize][newSize];
        for (int i=0; i<newSize; i++){ //перебираем все строки
            for (int j=0; j<newSize; j++){ //перебираем все столбцы
                MatrixOfNeigh[i][j]=-1; //изначально обозначаем все вершины графа недостижимыми
            }
        }
        CreateGraph(city); //создание матрицы смежности на основе города city

    }

    public int[][] getGraphMatrix() {
        return MatrixOfNeigh;
    }
    public void CreateGraph (City city){ //наполняем матрицу смежности
        for (int i=0; i<MatrixOfNeigh.length; i++){ //перебираем строки
            for (int j=0; j<MatrixOfNeigh.length; j++){ //перебираем столбцы
                if (city.getAllStations().get(i).getNeighbours().contains(j)){
                    MatrixOfNeigh[i][j]=1; //указываем, что путь есть
                }
            }
        }
    }
}
