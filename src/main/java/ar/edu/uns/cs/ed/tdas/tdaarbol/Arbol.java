
package ar.edu.uns.cs.ed.tdas.tdaarbol;
 
import java.util.Iterator;
 
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.*;
import ar.edu.uns.cs.ed.tdas.tdalista.LDE;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
 
public class Arbol<E> implements Tree<E> {
    protected int size;
    protected TNodo<E> root;
    public Arbol(){
        size=0;root=null;
    }

	 private TNodo<E> checkPosition(Position<E> p) {
        if (p == null||this.isEmpty()) {
            throw new InvalidPositionException("posicion invalida");
        }
        TNodo<E> nodo;
        try {
            nodo = (TNodo<E>) p;
        } catch (ClassCastException e) {
            throw new InvalidPositionException("posicion invalida");
        }
       // if (nodo.getNext() == null || nodo.getBefore() == null) {
         //   throw new InvalidPositionException("posicion invalida");
       // }
        return nodo;
    }


    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size==0;
    }

    /**
	 * Devuelve un iterador de los elementos almacenados en el árbol en preorden.
	 * @return Iterador de los elementos almacenados en el árbol.
	 */
	public Iterator<E> iterator(){
		PositionList<E> resultado=new LDE<E>();
		for(Position<E> pos:this.positions()){
			resultado.addLast(pos.element());
		}
		return resultado.iterator();
	}
	/**
	 * Devuelve una colección iterable de las posiciones de los nodos del árbol.
	 * @return Colección iterable de las posiciones de los nodos del árbol.
	 */
	public Iterable<Position<E>> positions(){
		PositionList<Position<E>> resultado=new LDE<Position<E>>();
		if (!isEmpty()) {
			preOrdenAuxiliar(this.root,resultado);
		}
		return resultado;
	}

		private void preOrdenAuxiliar(TNodo<E> nodo, PositionList<Position<E>> lista){
		//Visitar
		lista.addLast(nodo);
		//for sobre los hijos
		for(TNodo<E>pos:nodo.hijos){
			preOrdenAuxiliar(pos, lista);
		}
	}
	/**
	 * Reemplaza el elemento almacenado en la posición dada por el elemento pasado por parámetro. Devuelve el elemento reemplazado.
	 * @param v Posición de un nodo.
	 * @param e Elemento a reemplazar en la posición pasada por parámetro.
	 * @return Elemento reemplazado.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 */
	public E replace(Position<E> v, E e){
		E resultado=null;
		TNodo<E> nodo=checkPosition(v);
		resultado=nodo.element();
		nodo.setElemento(e);
		return resultado;
	}
	/**
	 * Devuelve la posición de la raíz del árbol.
	 * @return Posición de la raíz del árbol.
	 * @throws EmptyTreeException si el árbol está vacío.
	 */
	public Position<E> root()throws EmptyTreeException{
        if (isEmpty()) {
            throw new EmptyTreeException("Arbol vacio");
        }
        return root;
    }
	
	/**
	 * Devuelve la posición del nodo padre del nodo correspondiente a una posición dada.
	 * @param v Posición de un nodo.
	 * @return Posición del nodo padre del nodo correspondiente a la posición dada.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 * @throws BoundaryViolationException si la posición pasada por parámetro corresponde a la raíz del árbol.
	 */
	public Position<E> parent(Position<E> v)throws EmptyTreeException,BoundaryViolationException{
		TNodo<E> nodo=checkPosition(v);
		if (nodo==root){
			throw new BoundaryViolationException("El nodo es la raiz");
		}
		return nodo.getPadre();
	}
	
	/**
	 * Devuelve una colección iterable de los hijos del nodo correspondiente a una posición dada.
	 * @param v Posición de un nodo.
	 * @return Colección iterable de los hijos del nodo correspondiente a la posición pasada por parámetro.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 */
	public Iterable<Position<E>> children(Position<E> v){
		TNodo<E>nodo=checkPosition(v);
		PositionList<Position<E>>lista=new LDE<Position<E>>();
		for(TNodo<E>n:nodo.getHijos()){
			lista.addLast(n);
		}
		return lista;
	}
	/**
	 * Consulta si una posición corresponde a un nodo interno.
	 * @param v Posición de un nodo.
	 * @return Verdadero si la posición pasada por parámetro corresponde a un nodo interno, falso en caso contrario.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 */
	public boolean isInternal(Position<E> v){
		TNodo<E>nodo=checkPosition(v);
		return !nodo.getHijos().isEmpty();
	}
	/**
	 * Consulta si una posición dada corresponde a un nodo externo.
	 * @param v Posición de un nodo.
	 * @return Verdadero si la posición pasada por parámetro corresponde a un nodo externo, falso en caso contrario.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 */
	public boolean isExternal(Position<E> v){
		TNodo<E> nodo= checkPosition(v);
		return nodo.getHijos().isEmpty();
	}
	/**
	 * Consulta si una posición dada corresponde a la raíz del árbol.
	 * @param v Posición de un nodo.
	 * @return Verdadero, si la posición pasada por parámetro corresponde a la raíz del árbol,falso en caso contrario.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 */
	public boolean isRoot(Position<E> v){
		TNodo<E> root=checkPosition(v);
		return this.root==root;
	}
	/**
	 * Crea un nodo con rótulo e como raíz del árbol.
	 * @param E Rótulo que se asignará a la raíz del árbol.
	 * @throws InvalidOperationException si el árbol ya tiene un nodo raíz.
	 */
	public void createRoot(E e)throws InvalidOperationException{
		if (this.root!=null) {
			throw new InvalidOperationException("");
		}
		root=new TNodo<E>(e);
		size++;
	}
	
	/**
	 * Agrega un nodo con rótulo e como primer hijo de un nodo dado.
	 * @param e Rótulo del nuevo nodo.
	 * @param padre Posición del nodo padre.
	 * @return La posición del nuevo nodo creado.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida o el árbol está vacío.
	 */
	public Position<E> addFirstChild(Position<E> p, E e)throws InvalidPositionException{
		TNodo<E>padre=checkPosition(p);
		TNodo<E>hijo=new TNodo<E>(e,padre);
		padre.getHijos().addFirst(hijo);
		size++;
		return hijo;
	}
	/**
	 * Agrega un nodo con rótulo e como último hijo de un nodo dado.
	 * @param e Rótulo del nuevo nodo.
	 * @param p Posición del nodo padre.
	 * @return La posición del nuevo nodo creado.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida o el árbol está vacío.
	 */
	public Position<E> addLastChild(Position<E> p, E e){
		TNodo<E>padre=checkPosition(p);
		TNodo<E>hijo=new TNodo<E>(e,padre);
		padre.getHijos().addLast(hijo);
		size++;
		return hijo;
	}
	/**
	 * Agrega un nodo con rótulo e como hijo de un nodo padre dado. El nuevo nodo se agregará delante de otro nodo también dado.
	 * @param e Rótulo del nuevo nodo.
	 * @param p Posición del nodo padre.
	 * @param rb Posición del nodo que será el hermano derecho del nuevo nodo.
	 * @return La posición del nuevo nodo creado.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida, o el árbol está vacío, o la posición rb no corresponde a un nodo hijo del nodo referenciado por p.
	 */
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e){
		TNodo<E>padre=checkPosition(p);
		TNodo<E>hermano=checkPosition(rb);
		TNodo<E>hijo=new TNodo<E>(e,padre);
		Iterator<Position<TNodo<E>>>iterador=padre.getHijos().positions().iterator();
		boolean corte=false;
		Position<TNodo<E>>pos=null;
		while (iterador.hasNext()&&!corte) {
			pos=iterador.next();
			if(pos.element()==hermano){
				corte=true;
			}	 
		}
		if (corte) {
			padre.getHijos().addBefore(pos, hijo);
		}else{
			throw new InvalidPositionException("Posición invalida");
		}
		size++;
		return  hijo;
	}
	/**
	 * Agrega un nodo con rótulo e como hijo de un nodo padre dado. El nuevo nodo se agregará a continuación de otro nodo también dado.
	 * @param e Rótulo del nuevo nodo.
	 * @param p Posición del nodo padre.
	 * @param lb Posición del nodo que será el hermano izquierdo del nuevo nodo.
	 * @return La posición del nuevo nodo creado.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida, o el árbol está vacío, o la posición lb no corresponde a un nodo hijo del nodo referenciado por p.
	 */
	public Position<E> addAfter (Position<E> p, Position<E> lb, E e){
		TNodo<E> padre = checkPosition(p);
		TNodo<E> hermano = checkPosition(lb);
		Iterator<Position<TNodo<E>>> it = padre.getHijos().positions().iterator();
		Position<TNodo<E>> posHermano = null;
		boolean corte = false;
		while (it.hasNext() && !corte) {
			Position<TNodo<E>> pos = it.next();
			if (pos.element() == hermano) {
				posHermano = pos;
				corte = true;
			}
		}
		if (!corte) {
			throw new InvalidPositionException("Posicion invalida");
		}
		TNodo<E> hijo = new TNodo<E>(e, padre);
		padre.getHijos().addAfter(posHermano, hijo);
		size++;
		return hijo;
	}
	/**
	 * Elimina el nodo referenciado por una posición dada, si se trata de un nodo externo. 
	 * @param n Posición del nodo a eliminar.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida o no corresponde a un nodo externo, o el árbol está vacío.
	 */
	public void removeExternalNode (Position<E> p)throws InvalidPositionException{
		TNodo<E>nodo=checkPosition(p);
		if (nodo==root){
			if (!isExternal(nodo)) {
				throw new InvalidPositionException("no puedo eliminar la raiz");	
			}else{
				root=null;
				size--;
			}
		}else{
			if(isInternal(nodo)){
				throw new InvalidPositionException("No es hoja la posición");
			}
			else{
				TNodo<E>padre=nodo.getPadre();
				boolean corte=false;
				Iterator<Position<TNodo<E>>>iterator=padre.getHijos().positions().iterator();
				while (iterator.hasNext()&&!corte) {
					Position<TNodo<E>>pos=iterator.next();
					if (pos.element()==nodo) {
						corte=true;
						padre.getHijos().remove(pos);
						size--;
					}
				}
				if (!corte) {
					throw new InvalidPositionException("No es una posición valida");
				}
			}
		}
	}
	/**
	 * Elimina el nodo referenciado por una posición dada, si se trata de un nodo interno. Los hijos del nodo eliminado lo reemplazan en el mismo orden en el que aparecen. 
	 * Si el nodo a eliminar es la raíz del árbol, únicamente podrá ser eliminado si tiene un solo hijo, el cual lo reemplazará.
	 * @param n Posición del nodo a eliminar.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida o no corresponde a un nodo interno o corresponde a la raíz (con más de un hijo), o el árbol está vacío.
	 */
	public void removeInternalNode(Position<E> p) {
	TNodo<E> nodo = checkPosition(p);
	if (nodo.getHijos().isEmpty()) {
		throw new InvalidPositionException("El nodo no es interno");
	}
	if (nodo == root) {
		if (nodo.getHijos().size() != 1) {
			throw new InvalidPositionException("La raiz tiene mas de un hijo");
		}
		TNodo<E> hijo = nodo.getHijos().first().element();
		hijo.padre = null;
		root = hijo;
	} else {
		TNodo<E> padre = nodo.getPadre();
		Iterator<Position<TNodo<E>>> it = padre.getHijos().positions().iterator();
		Position<TNodo<E>> posNodo = null;
		boolean corte = false;
		while (it.hasNext() && !corte) {
			Position<TNodo<E>> pos = it.next();
			if (pos.element() == nodo) {
				posNodo = pos;
				corte = true;
			}
		}
		if (!corte) {
			throw new InvalidPositionException("Posicion invalida");
		}
		// Los hijos del nodo lo reemplazan, en el mismo orden
		for (TNodo<E> hijo : nodo.getHijos()) {
			hijo.padre = padre;
			padre.getHijos().addBefore(posNodo, hijo);
		}
		padre.getHijos().remove(posNodo);
	}
	size--;
}
	
	/**
	 * Elimina el nodo referenciado por una posición dada. Si se trata de un nodo interno, los hijos del nodo eliminado lo reemplazan en el mismo orden en el que aparecen. 
	 * Si el nodo a eliminar es la raíz del árbol, únicamente podrá ser eliminado si tiene un solo hijo, el cual lo reemplazará.
	 * @param n Posición del nodo a eliminar.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida o corresponde a la raíz (con más de un hijo), o el árbol está vacío.
	 */
	public void removeNode (Position<E> p)throws InvalidPositionException{
		TNodo<E> nodo = checkPosition(p);
		if (nodo.getHijos().isEmpty()) {
			removeExternalNode(nodo);
		} else {
			removeInternalNode(nodo);
		}
	}
}