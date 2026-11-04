package ar.edu.uns.cs.ed.tdas.tdalista;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ar.edu.uns.cs.ed.tdas.Position;

public class ElementIterator<E> implements Iterator<E> {
    protected PositionList<E> list;
    protected Position<E> cursor;

    public ElementIterator(PositionList<E> l) {
        list = l;
        if (list.isEmpty()) {
            cursor = null;
        } else {
            cursor = list.first();
        }
    }

    public boolean hasNext() {
        return cursor != null;
    }

    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException("no hay mas elementos");
        }
        E elemento = cursor.element();
        if (cursor == list.last()) {
            cursor = null;
        } else {
            cursor = list.next(cursor);
        }
        return elemento;
    }
}   