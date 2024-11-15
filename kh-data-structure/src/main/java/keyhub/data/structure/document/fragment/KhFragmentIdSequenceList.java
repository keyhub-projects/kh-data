/*
 * MIT License
 *
 * Copyright (c) 2024 KH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package keyhub.data.structure.document.fragment;

import keyhub.data.structure.order.KhOrder;
import keyhub.data.structure.order.KhOrderList;

import java.util.*;

public class KhFragmentIdSequenceList<FRAGMENT_ID> implements List<FRAGMENT_ID> {
    private final KhOrderList<FRAGMENT_ID> keyList = new KhOrderList<>();
    private final Map<KhOrder, FRAGMENT_ID> valueMap = new HashMap<>();

    // todo

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
