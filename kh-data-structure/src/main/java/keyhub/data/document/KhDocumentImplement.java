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
import keyhub.data.document.fragment.KhFragmentMap;
import keyhub.data.document.fragment.KhFragmentIdSequenceList;
import keyhub.data.document.scrap.KhScrap;
import keyhub.data.version.KhVersion;

import java.util.List;

public class KhDocumentImplement<ID, FRAGMENT_ID> implements KhDocument<ID, FRAGMENT_ID>{
    private final ID id;
    private KhVersion version;
    private String title;
    private final KhFragmentMap<FRAGMENT_ID> fragmentMap;

    private KhDocumentImplement(ID id, String title, KhVersion version, KhFragmentMap<FRAGMENT_ID> fragmentMap) {
        this.id = id;
        this.title = title;
        this.version = version;
        this.fragmentMap = fragmentMap;
    }
    public static <ID, FRAGMENT_ID> KhDocument<ID, FRAGMENT_ID> of(ID id, String title){
        @SuppressWarnings("unchecked")
        var result = (KhDocument<ID, FRAGMENT_ID>) KhDocumentImplement.builder()
                .id(id)
                .title(title)
                .version(KhVersion.of(0L,0L,1L))
                .build();
        return result;
    }
    public static <ID, FRAGMENT_ID> KhDocumentBuilder<ID, FRAGMENT_ID> builder() {
        return new KhDocumentBuilder<>();
    }
    public static class KhDocumentBuilder<ID, FRAGMENT_ID>{
        private ID id;
        private String title;
        private KhVersion version = KhVersion.of(0L,0L,1L);
        @SuppressWarnings("unchecked")
        private final KhFragmentMap<FRAGMENT_ID> fragmentMap = (KhFragmentMap<FRAGMENT_ID>) KhFragmentMap.empty();

        public KhDocumentBuilder<ID, FRAGMENT_ID> id(ID id) {
            this.id = id;
            return this;
        }
        public KhDocumentBuilder<ID, FRAGMENT_ID> title(String title) {
            this.title = title;
            return this;
        }
        public KhDocumentBuilder<ID, FRAGMENT_ID> version(KhVersion version){
            this.version = version;
            return this;
        }
        public KhDocument<ID, FRAGMENT_ID> build() {
            return new KhDocumentImplement<ID, FRAGMENT_ID>(this.id, this.title, this.version, this.fragmentMap);
        }
    }

    @Override
    public ID id() {
        return id;
    }
    @Override
    public String title(){
        return this.title;
    }
    @Override
    public KhVersion version(){
        return this.version;
    }
    @Override
    public List<KhFragment<FRAGMENT_ID>> fragments() {
        return fragmentMap.getList();
    }
    @Override
    public List<KhFragment<FRAGMENT_ID>> histories(FRAGMENT_ID key) {
        return fragmentMap.getHistories(key);
    }
    @Override
    public List<FRAGMENT_ID> fragmentSequence(){
        return fragmentMap.getSequence();
    }
    @Override
    public String contents(){
        return fragmentMap.getContents();
    }
    @Override
    public void title(String title){
        this.version = KhVersion.patchUp(version);
        this.title = title;
    }
    @Override
    public void version(KhVersion version){
        if(this.version.compareTo(version) > 0){
            throw new IllegalArgumentException("Version is not allowed to be downgraded.");
        }
        this.version = version;
    }

    @Override
    public KhFragment<FRAGMENT_ID> addScrap(KhScrap<FRAGMENT_ID> scrap, KhFragmentIdSequenceList<FRAGMENT_ID> sequence) {
        version = KhVersion.patchUp(version);
        return fragmentMap.put(scrap, sequence);
    }
    @Override
    public void setFragmentSequence(KhFragmentIdSequenceList<FRAGMENT_ID> sequence){
        if(equalsFragmentSequence(sequence)){
            return;
        }
        version = KhVersion.patchUp(version);
        fragmentMap.setSequence(sequence);
    }
    @Override
    public boolean equalsFragmentSequence(KhFragmentIdSequenceList<FRAGMENT_ID> sequence){
        if(fragmentMap.size() != sequence.size()){
            return false;
        }
        return fragmentMap.getSequence().equals(sequence);
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
        KhDocumentImplement<ID, FRAGMENT_ID> that = (KhDocumentImplement<ID, FRAGMENT_ID>) obj;
        return id.equals(that.id) && version.equals(that.version);
    }
    @Override
    public int hashCode() {
        return id.hashCode() + version.hashCode();
    }
    @Override
    public String toString() {
        return "KhDocument{" +
                "id=" + id +
                ", version=" + version +
                ", title=" + title +
                ", contents=" + contents() +
                "}";
    }
}
