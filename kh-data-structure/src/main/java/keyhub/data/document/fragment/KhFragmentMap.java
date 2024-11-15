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

package keyhub.data.document.fragment;

import keyhub.data.document.scrap.KhScrap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public interface KhFragmentMap<FRAGMENT_ID> {
    static <FRAGMENT_ID> KhFragmentMap<FRAGMENT_ID> empty(){
        return  KhFragmentMapImplement.empty();
    }

    List<FRAGMENT_ID> getSequence();

    String getContents();

    List<KhFragment<FRAGMENT_ID>> getList();

    List<KhFragment<FRAGMENT_ID>> getHistories(FRAGMENT_ID key);

    void setSequence(KhFragmentIdSequenceList<FRAGMENT_ID> sequence);

    KhFragment<FRAGMENT_ID> put(KhScrap<FRAGMENT_ID> scrap, KhFragmentIdSequenceList<FRAGMENT_ID> sequence);

    int size();

    boolean isEmpty();

    boolean containsKey(FRAGMENT_ID key);

    boolean containsValue(KhFragment<FRAGMENT_ID> value);

    KhFragment<FRAGMENT_ID> get(FRAGMENT_ID key);

    KhFragment<FRAGMENT_ID> remove(FRAGMENT_ID key);

    void putAll(Map<? extends FRAGMENT_ID, ? extends KhFragment<FRAGMENT_ID>> map);

    void clear();

    Set<FRAGMENT_ID> keySet();

    Collection<KhFragment<FRAGMENT_ID>> values();

    Set<Entry<FRAGMENT_ID, KhFragment<FRAGMENT_ID>>> entrySet();
}
