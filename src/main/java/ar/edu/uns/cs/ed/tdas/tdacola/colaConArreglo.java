package ar.edu.uns.cs.ed.tdas.tdacola;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;
public class colaConArreglo<E> implements Queue<E> {

    private E[] arreglo;
    private int primero;
    private int ultimo;

    @SuppressWarnings("unchecked")
    public colaConArreglo() {
        arreglo = (E[]) new Object[10];
        primero = 0;
        ultimo = 0;
    }

    public int size() {
        return ultimo - primero;
    }

    public boolean isEmpty() {
        return primero == ultimo;
    }

    public E front() {
        if (isEmpty()) {
            throw new EmptyQueueException("La cola esta vacia");
        }

        return arreglo[primero];
    }

    public void enqueue(E elemento) {

        if (ultimo == arreglo.length) {
            if (primero > 0) {
                compactar();
            } else {
                ampliarArreglo();
            }
        }

        arreglo[ultimo] = elemento;
        ultimo++;
    }

    public E dequeue() {

        if (isEmpty()) {
            throw new EmptyQueueException("La cola esta vacia");
        }

        E elemento = arreglo[primero];
        arreglo[primero] = null;
        primero++;

        return elemento;
    }

    private void compactar() {

        int cantidad = size();

        for (int i = 0; i < cantidad; i++) {
            arreglo[i] = arreglo[primero + i];
        }

        for (int i = cantidad; i < ultimo; i++) {
            arreglo[i] = null;
        }

        primero = 0;
        ultimo = cantidad;
    }
    @SuppressWarnings("unchecked")
    private void ampliarArreglo() {
        E[] nuevo = (E[]) new Object[arreglo.length * 2];

        int cantidad = size();

        for (int i = 0; i < cantidad; i++) {
            nuevo[i] = arreglo[primero + i];
        }
        arreglo = nuevo;
        primero = 0;
        ultimo = cantidad;
    }
}