package ar.edu.uns.cs.ed.tdas.tdapila;

import ar.edu.uns.cs.ed.tdas.excepciones.*;

public class pilaConArreglo<E> implements Stack<E>{
    private int tope;
    private E []a;
    //Constructor
    @SuppressWarnings("unchecked")
    public pilaConArreglo(){
        tope=0;
        a=(E[])new Object[10];
    }
    public int size(){
        return tope;
    }
    public boolean isEmpty(){
        return tope==0;
    }
    public E top(){
        if(tope==0){
            throw new EmptyStackException("La pila esta llena");
        }
        return a[tope-1];
    }
    public void push(E elem){
        if(tope!=a.length){
            a[tope]=elem;
            tope++;
        }
        else{
            @SuppressWarnings("unchecked")
            E[]nuevo=(E[])new Object[a.length*2];
            for (int i=0;i<a.length;i++){
                nuevo[i]=a[i];
            }
            a=nuevo;
            a[tope]=elem;
            tope++;
        }
    }
    public E pop(){
        if(tope==0){
            throw new EmptyStackException("La pila esta llena");
        }
        E toRet=a[tope-1];
        a[tope-1]=null;
        tope--;
        return toRet;
    }
}