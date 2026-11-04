package ar.edu.uns.cs.ed.tdas.tdadiccionario;
import ar.edu.uns.cs.ed.tdas.*;
import ar.edu.uns.cs.ed.tdas.excepciones.*;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdalista.LDE;

public class diccionarioConHashAbierto<K,V> implements Dictionary<K,V> {

    protected PositionList<Entrada<K,V>>[] buckets;
    protected int size;
    protected int N;
    @SuppressWarnings ("unchecked")
    public diccionarioConHashAbierto() {
        N = 13;
        size = 0;
        buckets = (PositionList<Entrada<K,V>>[]) new PositionList[N];
        for (int i = 0; i < N; i++)
            buckets[i] = new LDE<Entrada<K,V>>();
    }

    private int hash(K key) {
    return (key.hashCode()) % N;
}

    public int size() { 
        return size; 
    }
    public boolean isEmpty() {
        return size == 0;
    }

   public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
    if (key == null) throw new InvalidKeyException("Clave nula");

    Entrada<K,V> nueva = new Entrada<K,V>(key, value);
    buckets[hash(key)].addLast(nueva);
    size++;
    return nueva;
}

public Entry<K,V> find(K key) throws InvalidKeyException {
    if (key == null) throw new InvalidKeyException("Clave nula");

    for (Entrada<K,V> e : buckets[hash(key)]) {
        if (e.getKey().equals(key))
            return e;
    }
    return null;
}


public Iterable<Entrada<K,V>>eliminarTodas(K c,V v)throws InvalidKeyException{
    PositionList<Entrada<K,V>> eliminados = new LDE<Entrada<K,V>>();
    if (c == null) throw new InvalidKeyException("Clave nula");
    PositionList<Entrada<K,V>> cubeta = buckets[hash(c)];
    for(Entrada<K,V> e : cubeta){
        if (e.getKey().equals(c) && e.getValue().equals(v)){
            eliminados.addLast(e);
        }
    }
    return eliminados;
}

public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {
    if (key == null) throw new InvalidKeyException("Clave nula");

    PositionList<Entry<K,V>> res = new LDE<Entry<K,V>>();
    for (Entrada<K,V> e : buckets[hash(key)]) {
        if (e.getKey().equals(key))
            res.addLast(e);
    }
    return res;
}
    public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {
        if (e==null||e.getKey()==null){
            throw new InvalidEntryException("Entrada nula");
        }
       
        PositionList<Entrada<K,V>> cubeta = buckets[hash(e.getKey())];

        for (Position<Entrada<K,V>> p : cubeta.positions()) {
            if (p.element() == e) {
                cubeta.remove(p);
                size--;
                return e;
            }
        }
        throw new InvalidEntryException("La entrada no está en el diccionario");
    }

    public Iterable<Entry<K,V>> entries() {
        PositionList<Entry<K,V>> res = new LDE<Entry<K,V>>();
        for (int i = 0; i < N; i++)
            for (Entrada<K,V> e : buckets[i])
                res.addLast(e);
        return res;
    }
}
