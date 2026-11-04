package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;
import	java.util.Iterator;

import  ar.edu.uns.cs.ed.tdas.tdalista.*;
import  ar.edu.uns.cs.ed.tdas.excepciones.*;
import 	ar.edu.uns.cs.ed.tdas.Position;

public class arbolBinario<E> implements BinaryTree<E> {
	protected BTNodo<E> root;
	protected int cantElems;
	
	public arbolBinario() {
		root = null;
		cantElems = 0;
	}
	
	public int size() {
		return cantElems;
	}

	public boolean isEmpty() {
		return cantElems == 0;
	}

	public Iterator<E> iterator() {
		PositionList<E> toRet = new LDE<>();
		if (!isEmpty()) {
			preOrder(root,toRet);
		}
		return toRet.iterator();
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> toRet = new LDE<>();
		if (!isEmpty()) {
			preOrderPos(root,toRet);
		}
		return toRet;
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTNodo<E> node = checkPosition(v);
		E toRet = node.element();
		node.setElement(e);
		return toRet;
	}

	public Position<E> root() throws EmptyTreeException {
		if (isEmpty()) {
			throw new EmptyTreeException("Arbol vacio!");
		}
		return root;
	}

	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNodo<E> node = checkPosition(v);
		if (node == root) {
			throw new BoundaryViolationException("La raiz no tiene padre!");
		}
		return node.getParent();
	}

	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		PositionList<Position<E>> toRet = new LDE<>();
		BTNodo<E> node = checkPosition(v);
		if (node.getLeft() != null) {
			toRet.addLast(node.getLeft());
		}
		if (node.getRight() != null) {
			toRet.addLast(node.getRight());
		}
		return toRet;
	}

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		BTNodo<E> node = checkPosition(v);
		return (node.getLeft() != null || node.getRight() != null);
	}

	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		BTNodo<E> node = checkPosition(v);
		return (node.getLeft() == null && node.getRight() == null);
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTNodo<E> node = checkPosition(v);
		return node.getParent() == null;
	}

	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNodo<E> node = checkPosition(v);
		if (node.getLeft() == null) {
			throw new BoundaryViolationException("EL nodo no tiene hijo izquierdo");
		}
		return node.getLeft();
	}

	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNodo<E> node = checkPosition(v);
		if (node.getRight() == null) {
			throw new BoundaryViolationException("EL nodo no tiene hijo derecho");
		}
		return node.getRight();
	}

	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTNodo<E> node = checkPosition(v);
		return node.getLeft() != null;
	}

	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTNodo<E> node = checkPosition(v);
		return node.getRight() != null;
	}

	public void createRoot(E r) throws InvalidOperationException {
		if (root != null) {
			throw new InvalidOperationException("El arbol ya tiene raiz");
		}
		root= new BTNodo<>(r);
		cantElems++;
	}

	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTNodo<E> node = checkPosition(v);
		if (node.getLeft() != null) {
			throw new InvalidOperationException("El nodo ya tiene un hizo izquierdo");
		}
		BTNodo<E> newNode = new BTNodo<>(r,null,null,node);
		node.setLeft(newNode);
		cantElems++;
		return newNode;
	}

	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTNodo<E> node = checkPosition(v);
		if (node.getRight() != null) {
			throw new InvalidOperationException("El nodo ya tiene un hizo derecho");
		}
		BTNodo<E> newNode = new BTNodo<>(r,null,null,node);
		node.setRight(newNode);
		cantElems++;
		return newNode;
	}

	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		BTNodo<E> node = checkPosition(v);
		if (node.getLeft() != null && node.getRight() != null) {
			throw new InvalidOperationException("El nodo a eliminar tiene dos hijos");
		}
		E toRet = node.element();
		if (node != root) {
			BTNodo<E> father = node.getParent();
			if (isExternal(node)) {
				if (father.getLeft() == node) {
					father.setLeft(null);
				} else {
					father.setRight(null);
				}
			}
			if (node.getLeft() != null) {
				if (father.getLeft() == node) {
					father.setLeft(node.getLeft());
				} else {
					father.setRight(node.getLeft());
				}
				node.getLeft().setParent(father);
				node.setLeft(null);
			}
			if (node.getRight() != null) {
				if (father.getLeft() == node) {
					father.setLeft(node.getRight());
				} else {
					father.setRight(node.getRight());
				}
				node.getRight().setParent(father);
				node.setRight(null);
			}
		} else {
			if (isExternal(node)) {
				root = null;
			}
			if (node.getLeft() != null) {
				root = node.getLeft();
				node.setLeft(null);
			}
			if (node.getRight() != null) {
				root = node.getRight();
				node.setRight(null);
			}
		}
		node.setElement(null);
		cantElems--;
		return toRet;
	}

	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		BTNodo<E> node = checkPosition(r);
		if (isInternal(node)) {
			throw new InvalidPositionException("El nodo no es hoja");
		}
		if (T1 == null || T2 == null) {
			throw new InvalidPositionException("Los árboles a adjuntar no pueden ser null");
		}
		BTNodo<E> leftCopy = T1.isEmpty() ? null : cloneSubtree(T1, T1.root(), node);
		BTNodo<E> rightCopy = T2.isEmpty() ? null : cloneSubtree(T2, T2.root(), node);
		node.setLeft(leftCopy);
		node.setRight(rightCopy);
		cantElems += countNodes(leftCopy) + countNodes(rightCopy);
	}

	private BTNodo<E> cloneSubtree(BinaryTree<E> sourceTree, Position<E> source, BTNodo<E> parent) {
		BTNodo<E> copy = new BTNodo<>(source.element(), null, null, parent);
		if (sourceTree.hasLeft(source)) {
			copy.setLeft(cloneSubtree(sourceTree, sourceTree.left(source), copy));
		}
		if (sourceTree.hasRight(source)) {
			copy.setRight(cloneSubtree(sourceTree, sourceTree.right(source), copy));
		}
		return copy;
	}

	private int countNodes(BTNodo<E> node) {
		if (node == null) {
			return 0;
		}
		return 1 + countNodes(node.getLeft()) + countNodes(node.getRight());
	}
	
	private BTNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		if (p == null || isEmpty()) {
			throw new InvalidPositionException("Posicion invalida");
		}
		BTNodo<E> node = null;
		try {
			node = (BTNodo<E>) p;
		} catch (ClassCastException e) {e.printStackTrace();}
		return node;
	}
	
	private void preOrder(BTNodo<E> node, PositionList<E> list) {
		list.addLast(node.element());
		try {
			if (hasLeft(node)) {
				preOrder(node.getLeft(),list);
			}
			if (hasRight(node)) {
				preOrder(node.getRight(),list);
			}
		} catch (InvalidPositionException e) {e.printStackTrace();}
	}
	
	private void preOrderPos(BTNodo<E> node, PositionList<Position<E>> list) {
		list.addLast(node);
		try {
			if (hasLeft(node)) {
				preOrderPos(node.getLeft(),list);
			}
			if (hasRight(node)) {
				preOrderPos(node.getRight(),list);
			}
		} catch (InvalidPositionException e) {e.printStackTrace();}
	}

	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		BTNodo<E> node = checkPosition(p);
		if (node.getLeft() != null || node.getRight() != null) {
			throw new InvalidPositionException("El nodo no es externo");
		}
		if (node == root) {
			root = null;
		} else {
			BTNodo<E> father = node.getParent();
			if (father.getLeft() == node) {
				father.setLeft(null);
			} else {
				father.setRight(null);
			}
		}
		cantElems--;
	}
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		BTNodo<E> node = checkPosition(p);
		BTNodo<E> left = node.getLeft();
		BTNodo<E> right = node.getRight();
		if (left == null && right == null) {
			throw new InvalidPositionException("El nodo no es interno");
		}
		if (node == root) {
			// La raiz solo se puede eliminar si tiene un unico hijo
			if (left != null && right != null) {
				throw new InvalidPositionException("La raiz tiene dos hijos");
			}
			BTNodo<E> child = (left != null) ? left : right;
			child.setParent(null);
			root = child;
		} else {
			BTNodo<E> father = node.getParent();
			if (left != null && right != null) {
				// Los dos hijos ocupan el lugar del nodo: el padre no puede tener otro hijo
				if (father.getLeft() != null && father.getRight() != null) {
					throw new InvalidPositionException("El padre no tiene lugar para los hijos del nodo");
				}
				father.setLeft(left);
				father.setRight(right);
				left.setParent(father);
				right.setParent(father);
			} else {
				// Un solo hijo: ocupa el mismo lugar que el nodo
				BTNodo<E> child = (left != null) ? left : right;
				if (father.getLeft() == node) {
					father.setLeft(child);
				} else {
					father.setRight(child);
				}
				child.setParent(father);
			}
		}
		node.setLeft(null);
		node.setRight(null);
		cantElems--;
	}
		public void removeNode(Position<E> p) throws InvalidPositionException {
		BTNodo<E> node = checkPosition(p);
		if (node.getLeft() == null && node.getRight() == null) {
			removeExternalNode(node);
		} else {
			removeInternalNode(node);
		}
	}
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		BTNodo<E> father = checkPosition(p);
		BTNodo<E> brother = checkPosition(lb);
		if (brother.getParent() != father) {
			throw new InvalidPositionException("lb no es hijo de p");
		}
		// Si hay lugar, lb es el unico hijo: el nuevo nodo pasa a ser el ultimo
		return addLastChild(father, e);
	}
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		BTNodo<E> father = checkPosition(p);
		if (father.getLeft() != null && father.getRight() != null) {
			throw new InvalidPositionException("El nodo ya tiene dos hijos");
		}
		BTNodo<E> newNode = new BTNodo<>(e, null, null, father);
		if (father.getLeft() != null) {
			// El hijo existente pasa a ser el segundo
			father.setRight(father.getLeft());
		}
		father.setLeft(newNode);
		cantElems++;
		return newNode;
	}
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		BTNodo<E> father = checkPosition(p);
		if (father.getLeft() != null && father.getRight() != null) {
			throw new InvalidPositionException("El nodo ya tiene dos hijos");
		}
		BTNodo<E> newNode = new BTNodo<>(e, null, null, father);
		if (father.getRight() != null) {
			// El hijo existente pasa a ser el primero
			father.setLeft(father.getRight());
			father.setRight(newNode);
		} else if (father.getLeft() != null) {
			father.setRight(newNode);
		} else {
			father.setLeft(newNode);
		}
		cantElems++;
		return newNode;
	}
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		BTNodo<E> father = checkPosition(p);
		BTNodo<E> brother = checkPosition(rb);
		if (brother.getParent() != father) {
			throw new InvalidPositionException("rb no es hijo de p");
		}
		// Si hay lugar, rb es el unico hijo: el nuevo nodo pasa a ser el primero
		return addFirstChild(father, e);
	}

}
