package keyhub.data.document.fragment;

import keyhub.data.order.KhOrder;
import keyhub.data.order.KhOrderList;

import java.util.*;

public class KhFragmentIdSequenceList<FRAGMENT_ID> implements List<FRAGMENT_ID> {
    private final KhOrderList<FRAGMENT_ID> keyList = new KhOrderList<>();
    private final Map<KhOrder, FRAGMENT_ID> valueMap = new HashMap<>();

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<FRAGMENT_ID> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(FRAGMENT_ID fragmentId) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends FRAGMENT_ID> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends FRAGMENT_ID> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public FRAGMENT_ID get(int index) {
        return null;
    }

    @Override
    public FRAGMENT_ID set(int index, FRAGMENT_ID element) {
        return null;
    }

    @Override
    public void add(int index, FRAGMENT_ID element) {

    }

    @Override
    public FRAGMENT_ID remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<FRAGMENT_ID> listIterator() {
        return null;
    }

    @Override
    public ListIterator<FRAGMENT_ID> listIterator(int index) {
        return null;
    }

    @Override
    public List<FRAGMENT_ID> subList(int fromIndex, int toIndex) {
        return List.of();
    }
}
