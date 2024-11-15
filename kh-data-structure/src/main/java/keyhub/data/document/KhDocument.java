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

package keyhub.data.document;

import keyhub.data.document.fragment.KhFragment;
import keyhub.data.document.fragment.KhFragmentIdSequenceList;
import keyhub.data.document.scrap.KhScrap;
import keyhub.data.version.KhVersion;

import java.util.List;

public interface KhDocument<ID, FRAGMENT_ID> {
    static <ID, FRAGMENT_ID> KhDocument<ID, FRAGMENT_ID> of(ID id, String title){
        return KhDocumentImplement.of(id, title);
    }
    ID id();
    String title();
    KhVersion version();
    List<KhFragment<FRAGMENT_ID>> fragments();
    List<KhFragment<FRAGMENT_ID>> histories(FRAGMENT_ID key);
    List<FRAGMENT_ID> fragmentSequence();
    String contents();

    void title(String title);
    void version(KhVersion version);

    KhFragment<FRAGMENT_ID> addScrap(KhScrap<FRAGMENT_ID> scrap, KhFragmentIdSequenceList<FRAGMENT_ID> sequence);
    void setFragmentSequence(KhFragmentIdSequenceList<FRAGMENT_ID> sequence);
    boolean equalsFragmentSequence(KhFragmentIdSequenceList<FRAGMENT_ID> sequence);
}
