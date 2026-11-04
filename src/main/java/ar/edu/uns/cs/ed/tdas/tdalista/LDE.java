package ar.edu.uns.cs.ed.tdas.tdalista;
import ar.edu.uns.cs.ed.tdas.excepciones.*;
import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;

public class LDE<E> implements PositionList<E> {
    private DNodo<E>head;
    private DNodo<E>tail;
    private int cantElem;
    public LDE(){
        head=new DNodo<E>(null);
        tail=new DNodo<E>(null);
        tail.setBefore(head);
        head.setNext(tail);
        cantElem=0;
    }
    
    public int size() {
        return cantElem;
    }
    
    public boolean isEmpty() {
        return cantElem==0;
    }
    
   private DNodo<E> checkPosition(Position<E> p) {
    if (p == null) {
        throw new InvalidPositionException("posicion invalida");
    }
    DNodo<E> nodo;
    try {
        nodo = (DNodo<E>) p;
    } catch (ClassCastException e) {
        throw new InvalidPositionException("posicion invalida");
    }
    if (nodo.getNext() == null || nodo.getBefore() == null) {
        throw new InvalidPositionException("posicion invalida, elemento ya eliminado");
    }
    return nodo;
}

    public Position<E> first() {
        if (head.getNext()==tail){
            throw new EmptyListException("lista vacia");
        }
        return head.getNext();
    }
    
    public Position<E> last() {
        if (head.getNext()==tail){
            throw new EmptyListException("lista vacia");
        }
        return tail.getBefore();
    }
    
    public Position<E> next(Position<E> p) {
    DNodo<E> nodo = checkPosition(p);
    if (nodo == head || nodo == tail) {
        throw new InvalidPositionException("posicion invalida");
    }
    if (nodo.getNext() == tail) {
        throw new BoundaryViolationException("no hay siguiente");
    }
    return nodo.getNext();
}

    
    public Position<E> prev(Position<E> p) {
        DNodo<E> nodo = checkPosition(p);
        if (nodo == head || nodo == tail) {
            throw new InvalidPositionException("posicion invalida");
        }
        if (nodo.getBefore() == head) {
            throw new BoundaryViolationException("no hay anterior");
        }
        return nodo.getBefore();
    }
    
    public void addFirst(E element) {
        DNodo<E> nuevo = new DNodo<E>(element);
        DNodo<E> primero = head.getNext();

        nuevo.setNext(primero);
        nuevo.setBefore(head);
        primero.setBefore(nuevo);
        head.setNext(nuevo);

        cantElem++;
    }
    
    public void addLast(E element) {
        DNodo<E> nuevo = new DNodo<E>(element);
        DNodo<E> ultimo = tail.getBefore();

        nuevo.setNext(tail);
        nuevo.setBefore(ultimo);
        ultimo.setNext(nuevo);
        tail.setBefore(nuevo);
        cantElem++;
    }
    
    public void addAfter(Position<E> p, E element) {
        DNodo<E> nodo = checkPosition(p);
        if (nodo == head || nodo == tail) {
            throw new InvalidPositionException("posicion invalida");
        }

        DNodo<E> nuevo = new DNodo<E>(element);
        DNodo<E> siguiente = nodo.getNext();

        nuevo.setNext(siguiente);
        nuevo.setBefore(nodo);
        siguiente.setBefore(nuevo);
        nodo.setNext(nuevo);
        cantElem++;
    }
    
    public void addBefore(Position<E> p, E element) {
        DNodo<E>nodo=checkPosition(p);
        if (nodo==head||nodo==tail)
            throw new InvalidPositionException("Posicion invalida");
        DNodo<E> nuevo=new DNodo<E>(element);
        DNodo<E>anterior=nodo.getBefore();
        nuevo.setNext(nodo);
        nuevo.setBefore(anterior);
        anterior.setNext(nuevo);
        nodo.setBefore(nuevo);
        cantElem++;
    }
    
    public E remove(Position<E> p) {
        DNodo<E>nodo=checkPosition(p);
        if (nodo==head||nodo==tail) {
            throw new InvalidPositionException("Posicion invalida");
        }
        DNodo<E>siguiente=nodo.getNext();
        DNodo<E>anterior=nodo.getBefore();
        siguiente.setBefore(anterior);
        anterior.setNext(siguiente);
        nodo.setNext(null);    
        nodo.setBefore(null);
        cantElem--;
        return nodo.element();
    }
    
    public E set(Position<E> p, E element) {
        DNodo<E>nodo=checkPosition(p);
        if (nodo==head||nodo==tail) {
            throw new InvalidPositionException("Posición invalida");
        }
        E actual=nodo.element();
        nodo.setElement(element);
        return actual;
    }
    
    public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}
   

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>>pl=new LDE<Position<E>>();
        if(cantElem!=0){
            DNodo<E>n=head.getNext();
            while(n!=tail){
                pl.addLast(n);
                n=n.getNext();
            }
        }
        return pl;
	}   
    //Ejercicio 2
    public void insertarSegundoYAntepenultimo(E e1, E e2) {
    if (isEmpty()) {//caso vacio
        addFirst(e1);
        addAfter(head.getNext(), e2);
    } else if (cantElem == 1) {//caso de 1 elemento
        addAfter(head.getNext(), e1);
        addAfter(head.getNext().getNext(), e2);
    } else {//general 2o mas
        addAfter(head.getNext(), e1);
        addBefore(tail.getBefore(), e2);
    }
    }
}