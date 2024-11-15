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

package keyhub.data.order;

public record KhOrder(
    Long bucket,
    Long fixedKey,
    Long variableKey
) implements Comparable<KhOrder> {
    public static final KhOrder EMPTY = KhOrder.of(0L, 0L, 0L);
    static KhOrder of(){
        return EMPTY;
    }
    static KhOrder from(String order){
        String[] split = order.split("\\|");
        String[] split1 = split[1].split(":");
        return KhOrder.of(Long.parseLong(split[0]), Long.parseLong(split1[0]), Long.parseLong(split1[1]));
    }
    static KhOrder of(Long bucket, Long fixedKey, Long variableKey){
        return new KhOrder(bucket, fixedKey, variableKey);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        KhOrder that = (KhOrder) obj;
        return bucket.equals(that.bucket)
                && fixedKey.equals(that.fixedKey)
                && variableKey.equals(that.variableKey);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(bucket) + Long.hashCode(fixedKey) + Long.hashCode(variableKey);
    }

    @Override
    public String toString() {
        return bucket + "|" + fixedKey + ":" + variableKey;
    }

    @Override
    public int compareTo(KhOrder o) {
        if(equals(o)){
            return 0;
        }
        if(this.bucket.compareTo(o.bucket()) == 0){
            if(this.fixedKey.compareTo(o.fixedKey()) == 0){
                return this.variableKey.compareTo(o.variableKey());
            }
            return this.fixedKey.compareTo(o.fixedKey());
        }
        return this.bucket.compareTo(o.bucket());
    }
}
