package com.example.myapplication_ia;

import java.util.ArrayList;

public class Nodo {
    int [][]estado;
    ArrayList<Nodo> hijos;
    Nodo padre;
    int f,g,h;
    boolean visto;
    public Nodo(int[][]estado){
        this.estado=estado;
        this.padre=null;
        this.hijos=new ArrayList<Nodo>();
        f=g=h=0;
        this.visto=false;
    }
    public int[][] getEstado() {
        return estado;
    }
    public ArrayList<Nodo> getHijos() {
        return hijos;
    }
    public Nodo getPadre() {
        return padre;
    }
}
