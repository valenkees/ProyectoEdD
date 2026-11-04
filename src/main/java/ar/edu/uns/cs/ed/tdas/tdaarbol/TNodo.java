package ar.edu.uns.cs.ed.tdas.tdaarbol;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.LDE;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
public class TNodo<E> implements Position<E>{
    protected TNodo<E> padre;
    protected E elem;
    protected PositionList <TNodo<E>> hijos;
    public TNodo(E elem){
        this.elem=elem;
        this.padre=null;
        hijos= new LDE<TNodo<E>>();
    }
    public TNodo(E elem, TNodo<E> padre){
        this.elem=elem;
        this.padre=padre;
        this.hijos=new LDE<TNodo<E>>();
    }
    public E element(){
        return  elem;
    }
    public TNodo<E> getPadre(){
        return padre;
    }
    public PositionList<TNodo<E>> getHijos(){
        return hijos;
    }
    public void setElemento(E elem){
        this.elem=elem;
    }
    public void setPadre(TNodo<E>padre){
        this.padre=padre;
    }
    public E elem(){
        return elem;
    }
}