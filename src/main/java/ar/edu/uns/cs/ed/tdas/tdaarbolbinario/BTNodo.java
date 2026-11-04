package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;
import ar.edu.uns.cs.ed.tdas.Position;

public class BTNodo<E> implements Position<E>{
    private E element;
    private BTNodo<E> left,right,parent;
    public BTNodo(E elem,BTNodo<E>left,BTNodo<E>right,BTNodo<E>parent){
        this.element=elem;
        this.right=right;
        this.left=left;
        this.parent=parent;
    }
    public BTNodo(E element) {
		this(element,null,null,null);
	}
    public E element(){
        return this.element;
    }
    public void setLeft(BTNodo<E> left){
        this.left=left;
    }
    public void setRight(BTNodo<E> right){
        this.right=right;
    }
    public void setParent(BTNodo<E> parent){
        this.parent=parent;
    }
    public void setElement(E element){
        this.element=element;
    }
    public BTNodo<E> getLeft(){
        return this.left;
    }
    public BTNodo<E> getRight(){
        return this.right;
    }
    public BTNodo<E> getParent(){
        return this.parent;
    }
    public E getElement(){
        return this.element;
    }
}