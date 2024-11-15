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

import keyhub.data.structure.version.KhVersion;

public record KhFragmentId<FRAGMENT_ID>(
        FRAGMENT_ID id,
        KhVersion version
) {
    private static final KhFragmentId<?> EMPTY = new KhFragmentId<>(null, KhVersion.of(0L,0L,1L));
    public static <FRAGMENT_ID> KhFragmentId<FRAGMENT_ID> empty(){
        @SuppressWarnings("unchecked")
        var empty = (KhFragmentId<FRAGMENT_ID>) EMPTY;
        return empty;
    }
    public static <FRAGMENT_ID> KhFragmentId<FRAGMENT_ID> of(FRAGMENT_ID id, KhVersion version){
        return new KhFragmentId<>(id, version);
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
        KhFragmentId<FRAGMENT_ID> that = (KhFragmentId<FRAGMENT_ID>) obj;
        return id.equals(that.id)
            && version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return id.hashCode() + version.hashCode();
    }

    @Override
    public String toString() {
        return id + "." + version;
    }
}
