package com.example.dz2_testjson;

public class PrevNext {
   private int Previous;
    private int Next;
    PrevNext(int p,int n){
        Previous=p;
        Next=n;
    }

    public int getPrevious() {
        return Previous;
    }

    public int getNext() {
        return Next;
    }
}
