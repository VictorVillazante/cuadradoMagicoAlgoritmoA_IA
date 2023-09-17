package com.example.myapplication_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView primero_1, segundo_1, tercero_1, cuarto_1, quinto_1, sexto_1, septimo_1, octavo_1, noveno_1;
    TextView primero_2, segundo_2, tercero_2, cuarto_2, quinto_2, sexto_2, septimo_2, octavo_2, noveno_2;
    TextView[] cuadros_2;
    Chronometer chronometro;
    public TextView[] getCuadros_2() {
        return cuadros_2;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometro=(Chronometer)findViewById(R.id.cronometro);
        primero_1 = (TextView) findViewById(R.id.primero_1);
        segundo_1 = (TextView) findViewById(R.id.segundo_1);
        tercero_1 = (TextView) findViewById(R.id.tercero_1);
        cuarto_1 = (TextView) findViewById(R.id.cuarto_1);
        quinto_1 = (TextView) findViewById(R.id.quinto_1);
        sexto_1 = (TextView) findViewById(R.id.sexto_1);
        septimo_1 = (TextView) findViewById(R.id.septimo_1);
        octavo_1 = (TextView) findViewById(R.id.octavo_1);
        noveno_1 = (TextView) findViewById(R.id.noveno_1);
        TextView cuadros[] = {primero_1, segundo_1, tercero_1, cuarto_1, quinto_1, sexto_1, septimo_1, octavo_1, noveno_1};
        //final int[][] solucion = sortearElementosPosicion();
        final int [][] solucion ={{6,5,0},{1,8,2},{4,3,7}};
        establecerColores(solucion,cuadros);
        primero_2 = (TextView) findViewById(R.id.primero_2);
        segundo_2 = (TextView) findViewById(R.id.segundo_2);
        tercero_2 = (TextView) findViewById(R.id.tercero_2);
        cuarto_2 = (TextView) findViewById(R.id.cuarto_2);
        quinto_2 = (TextView) findViewById(R.id.quinto_2);
        sexto_2 = (TextView) findViewById(R.id.sexto_2);
        septimo_2 = (TextView) findViewById(R.id.septimo_2);
        octavo_2 = (TextView) findViewById(R.id.octavo_2);
        noveno_2 = (TextView) findViewById(R.id.noveno_2);
        cuadros_2 = new TextView[]{primero_2, segundo_2, tercero_2, cuarto_2, quinto_2, sexto_2, septimo_2, octavo_2, noveno_2};
        //final int[][] inicio=sortearElementosPosicion();
        final int [][] inicio= {{7,0,8},{6,5,3},{2,1,4}};
        establecerColores(inicio,cuadros_2);
        final Nodo n_inicial=new Nodo(inicio);
        final Button btnEjecutar = (Button)findViewById(R.id.buttonResolver);
        btnEjecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarAlgoritmo(n_inicial,solucion);
                btnEjecutar.setVisibility(View.INVISIBLE);
            }
        });
        Button btnRepetir = (Button)findViewById(R.id.buttonRepetir);
        btnRepetir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                reset_cronometro();
                establecerColores(inicio,cuadros_2);
                btnEjecutar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void mostrarSecuencia(Nodo revisar) {
        ArrayList<int[][]>lista_estados=new ArrayList<int[][]>();
        while(revisar!=null) {
            lista_estados.add(revisar.estado);
            revisar=revisar.padre;
        }
        ArrayList<int[][]> revArrayList = new ArrayList<int[][]>();
        for (int i = lista_estados.size() - 1; i >= 0; i--) {
            revArrayList.add(lista_estados.get(i));
        }
        int n=0;
        new Hilo(revArrayList, getCuadros_2()).start();
        start_cronometro();
        //new Hilo().start();
        /*for (int[][] is : revArrayList) {
            //System.out.println("Estado"+n);
            n++;
            try {
                establecerColores(is,getCuadros_2());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //new Hilo(is, getCuadros_2()).start();
            //new Hilo().start();

            //establecerColores(is,getCuadros_2());

            //SystemClock.sleep(1000);
        }*/

    }
    boolean correr=false;
    long detenerse=0;
    private void start_cronometro() {
        if(!correr){
            chronometro.setBase(SystemClock.elapsedRealtime() - detenerse);
            chronometro.start();
            correr=true;
        }

    }
    private void detener_cronometro() {
        if (correr){
            chronometro.stop();
            detenerse = SystemClock.elapsedRealtime() - chronometro.getBase();
            correr=false;
        }
    }
    private void reset_cronometro() {
        chronometro.setBase(SystemClock.elapsedRealtime());
        detenerse=0;
    }
    class Hilo extends Thread{
        ArrayList<int[][]>estados;
        TextView[]cuadros;
        public Hilo(ArrayList<int[][]>estados,TextView[]cuadros){
            this.estados=estados;
            this.cuadros=cuadros;
        }
        public void run() {
            try {
                for (final int[][] is : this.estados) {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            establecerColores(is,getCuadros_2());
                        }
                    });
                }
                detener_cronometro();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public static void imprimir_estado(int[][]estado) {
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                System.out.print("["+estado[i][j]+"]");
            }
            System.out.println();
        }
        System.out.println();
    }
    private void establecerColores(int[][] elementos, TextView[] cuadros) {
        for (int i = 0; i < 3; i++) {
            for(int j=0; j<3;j++) {
                int valor = elementos[i][j];
                cuadros[i*3+j].setText(valor + "");
                switch (valor) {
                    case 1:
                        cuadros[i*3+j].setBackground(getDrawable(R.color.colorUno));
                        break;
                    case 2:
                        cuadros[i*3+j].setBackground(getDrawable(R.color.colorDos));
                        break;
                    case 3:
                        cuadros[i*3+j].setBackground(getDrawable(R.color.colorTres));
                        break;
                    case 4:
                        cuadros[i*3+j].setBackground(getDrawable(R.color.colorCuatro));
                        break;
                    case 5:
                        cuadros[i*3+j].setBackground(getDrawable(R.color.colorCinco));
                        break;
                    case 6:
                        cuadros[i*3+j].setBackground(getDrawable(R.color.colorSeis));
                        break;
                    case 7:
                        cuadros[i*3+j].setBackground(getDrawable(R.color.colorSiete));
                        break;
                    case 8:
                        cuadros[i*3+j].setBackground(getDrawable(R.color.colorOcho));
                        break;
                    case 0:
                        cuadros[i*3+j].setBackground(getDrawable(R.color.colorNueve));
                        break;
                }
            }
        }
    }
    private int[][] sortearElementosPosicion() {
        ArrayList<Integer>elementos=new ArrayList<Integer>();
        Random rnd = new Random();
        for (int i = 0; i < 9; i++) {
            int valor = (int) (rnd.nextDouble() * 9);
            while (elementos.contains(valor)) {
                valor = (int) (rnd.nextDouble() * 9);
            }
            elementos.add(valor);
        }
        int [][]respuesta=new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                respuesta[i][j]=elementos.remove(0);
            }
        }
        return respuesta;
    }
    private void ejecutarAlgoritmo(Nodo n_inicial, int[][] solucion) {
        ArrayList<Nodo> lista_abierta=new ArrayList<Nodo>();
        ArrayList<Nodo> lista_cerrada=new ArrayList<Nodo>();
        lista_abierta.add(n_inicial);
        int c=0;
        while(lista_abierta.size()!=0) {
            Nodo menor=null;
            c++;
            for(int i=0;i<lista_abierta.size();i++) {
                if(menor==null || lista_abierta.get(i).h<menor.h) {
                    menor=lista_abierta.get(i);
                }
            }
            //imprimir_estado(menor.estado);
            if(estados_iguales(menor.estado,solucion)) {
                System.out.println("Solucion encontrada");
                System.out.println("Numero de pasos"+c);
                mostrarSecuencia(menor);
                break;
            }
            int posicion[]=posicionCero(menor.getEstado());
            int movimientos[][]= {{1,0},{-1,0},{0,1},{0,-1}};
            for (int[] movimiento : movimientos) {
                int m_fila=posicion[0]+movimiento[0];
                int m_columna=posicion[1]+movimiento[1];
                if((m_fila>=0 && m_columna>=0 && m_fila<3 && m_columna<3)) {
                    int estado_hijo[][]=obtenerCambioEstado(menor.getEstado(),m_fila,m_columna,posicion[0],posicion[1],menor.getEstado()[m_fila][m_columna]);
                    Nodo hijo=new Nodo(estado_hijo);
                    hijo.f=numeroElementosFueraLugar(estado_hijo,solucion);
                    hijo.g=1;
                    hijo.h=hijo.f+hijo.g;
                    if(!esta_lista_abierta(hijo,lista_abierta) && !esta_lista_cerrada(hijo,lista_cerrada)) {
                        lista_abierta.add(hijo);
                        hijo.padre=menor;
                    }
                }
            }
            lista_cerrada.add(menor);
            lista_abierta.remove(menor);

        }
    }

    private static int numeroElementosFueraLugar(int[][] estado_hijo, int[][] solucion) {
        int n_fuera=0;
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(estado_hijo[i][j]!=solucion[i][j] && estado_hijo[i][j]!=0) {
                    n_fuera++;
                }
            }
        }
        return n_fuera;
    }
    private static boolean esta_lista_cerrada(Nodo hijo, ArrayList<Nodo> lista_cerrada) {
        for (Nodo nodo : lista_cerrada) {
            if(estados_iguales(nodo.estado,hijo.estado)) {
                return true;
            }
        }
        return false;
    }
    private static boolean estados_iguales(int[][] estado, int[][] estado2) {
        boolean acierto=true;
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(estado[i][j]!=estado2[i][j]) {
                    acierto=false;
                }
            }
        }
        return acierto;
    }
    private static boolean esta_lista_abierta(Nodo hijo, ArrayList<Nodo> lista_abierta) {
        for (Nodo nodo : lista_abierta) {
            if(estados_iguales(nodo.estado,hijo.estado) && hijo.h>nodo.h) {
                return true;
            }
        }
        return false;
    }
    private static int[][] obtenerCambioEstado(int[][] estado_padre, int m_fila, int m_columna, int x0, int y0,int valor) {
        int r[][]=new int[3][3];
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(i==x0 && j==y0) {
                    r[i][j]=valor;
                    continue;
                }
                if(i==m_fila && j==m_columna) {
                    r[i][j]=0;
                    continue;
                }
                r[i][j]=estado_padre[i][j];

            }
        }
        return r;
    }
    private static int[] posicionCero(int[][] estado) {
        int respuesta[]=new int[2];
        for (int i=0;i<estado.length;i++) {
            for(int j=0;j<estado.length;j++) {
                if(estado[i][j]==0) {
                    respuesta[0]=i;
                    respuesta[1]=j;
                }
            }
        }
        return respuesta;
    }

}