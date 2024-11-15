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

import keyhub.data.order.KhOrder;
import keyhub.data.version.KhVersion;

public class KhFragmentImplement<FRAGMENT_ID> implements KhFragment<FRAGMENT_ID>{
    private final KhFragmentId<FRAGMENT_ID> id;
    private final KhOrder order;
    private final String content;
    private final Boolean deleted;

    private KhFragmentImplement(KhFragmentId<FRAGMENT_ID> id, KhOrder order, String content, Boolean deleted) {
        this.id = id;
        this.order = order;
        this.content = content;
        this.deleted = deleted;
    }
    public static <FRAGMENT_ID> KhFragment<FRAGMENT_ID> of(KhFragmentId<FRAGMENT_ID> id, KhOrder order, String content, Boolean deleted){
        return new KhFragmentImplement<>(id, order, content, deleted);
    }

    @Override
    public KhFragmentId<FRAGMENT_ID> fragmentId() {
        return id;
    }
    @Override
    public FRAGMENT_ID id() {
        return id.id();
    }
    @Override
    public KhVersion version() {
        return id.version();
    }
    @Override
    public KhOrder order() {
        return order;
    }
    @Override
    public String content() {
        return content;
    }
    @Override
    public Boolean deleted() {
        return deleted;
    }

    @Override
    public int compareTo(KhFragment<FRAGMENT_ID> o) {
        int comparedByOrder = order().compareTo(o.order());
        int comparedByVersion = version().compareTo(o.version());
        if(comparedByOrder == 0){
            return comparedByVersion;
        }
        return comparedByOrder;
    }

    @Override
    public int compareByLatest(KhFragment<FRAGMENT_ID> o) {
        if(!id().equals(o.id())){
            throw new IllegalArgumentException("KhFragment id is not same");
        }
        return version().compareTo(o.version());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        @SuppressWarnings("unchecked")
        KhFragmentImplement<FRAGMENT_ID> that = (KhFragmentImplement<FRAGMENT_ID>) obj;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "KhFragment{" +
                "id=" + id +
                ", order=" + order +
                ", content='" + content +
                "'}";
    }
}
