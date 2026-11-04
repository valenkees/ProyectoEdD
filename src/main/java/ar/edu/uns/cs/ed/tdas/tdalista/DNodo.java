package ar.edu.uns.cs.ed.tdas.tdalista;

import ar.edu.uns.cs.ed.tdas.Position;

public class DNodo<E> implements Position<E>{

    protected E element;
    protected DNodo<E> next;
    protected DNodo<E> before;
    
    public DNodo(E e){
        element=e;
        next=null;
        before=null;    
    }
    public E element(){
        return element;
    }
    public void setElement(E element){
        this.element=element;
    }
    public DNodo<E> getNext(){
        return next;
    }
    public DNodo<E> getBefore(){
        return before;
    }
    public void setNext(DNodo<E> next){
        this.next=next;
    }
    public void setBefore(DNodo<E>before){
        this.before=before;
    }
}
