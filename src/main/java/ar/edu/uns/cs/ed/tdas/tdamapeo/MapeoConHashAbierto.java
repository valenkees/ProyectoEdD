package ar.edu.uns.cs.ed.tdas.tdamapeo;
import ar.edu.uns.cs.ed.tdas.*;
import ar.edu.uns.cs.ed.tdas.excepciones.*;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdalista.LDE;
public class MapeoConHashAbierto<K,V> implements Map<K,V> {

    protected PositionList<Entrada<K,V>>[] buckets;
    protected int size;
    protected int N;
    @SuppressWarnings("unchecked")
    public MapeoConHashAbierto() {
        N = 13;
        size = 0;
        buckets = (PositionList<Entrada<K,V>>[]) new PositionList[N];
        for (int i = 0; i < N; i++)
            buckets[i] = new LDE<Entrada<K,V>>();
    }

    private int hash(K key) {
        return (key.hashCode() % N);
    }

    public int size() { 
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

        public V get(K key) throws InvalidKeyException {
            if (key == null) throw new InvalidKeyException("Clave nula");
            int i=hash(key);
            PositionList<Entrada<K,V>> l = buckets[i];
            V toRet=null;
            for (Entrada<K,V> e : l) {
                if (e.getKey().equals(key)) {
                    toRet = e.getValue();
                    break;
                }
            }
            return toRet;
    }

        public V put(K key, V value) throws InvalidKeyException {
        if (key == null) throw new InvalidKeyException("Clave nula");

        PositionList<Entrada<K,V>> cubeta = buckets[hash(key)];

        for (Entrada<K,V> e : cubeta) {
            if (e.getKey().equals(key)) {
                V viejo = e.getValue();
                e.setValue(value);
                return viejo;
            }
        }
        cubeta.addLast(new Entrada<K,V>(key, value));
        size++;
        return null;
    }

        public V remove(K key) throws InvalidKeyException {
        if (key == null) throw new InvalidKeyException("Clave nula");

        PositionList<Entrada<K,V>> cubeta = buckets[hash(key)];

        for (Position<Entrada<K,V>> p : cubeta.positions()) {
            if (p.element().getKey().equals(key)) {
                V valor = p.element().getValue();
                cubeta.remove(p);
                size--;
                return valor;
            }
        }
        return null;
    }

        public Iterable<K> keys() {
        PositionList<K> claves = new LDE<K>();
        for (int i = 0; i < N; i++)
            for (Entrada<K,V> e : buckets[i])
                claves.addLast(e.getKey());
        return claves;
    }

    public Iterable<V> values() {
        PositionList<V> valores = new LDE<V>();
        for (int i = 0; i < N; i++)
            for (Entrada<K,V> e : buckets[i])
                valores.addLast(e.getValue());
        return valores;
    }

    public Iterable<Entry<K,V>> entries() {
        PositionList<Entry<K,V>> entradas = new LDE<Entry<K,V>>();
        for (int i = 0; i < N; i++)
            for (Entrada<K,V> e : buckets[i])
                entradas.addLast(e);
        return entradas;
    }
}