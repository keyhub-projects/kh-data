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

package keyhub.data.structure.version;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public record KhVersion(
        Long major,
        Long minor,
        Long patch,
        Instant instant
) implements Comparable<KhVersion> {
    public static KhVersion of(Long major, Long minor, Long patch){
        return KhVersion.of(major, minor, patch, Instant.now());
    }
    public static KhVersion of(Long major, Long minor, Long patch, Instant instant){
        return new KhVersion(major, minor, patch, instant);
    }
    public static KhVersion majorUp(KhVersion previous){
        Long next = 1 + previous.major;
        return KhVersion.of(next, 0L, 0L);
    }
    public static KhVersion majorUp(KhVersion previous, Instant instant){
        Long next = 1 + previous.major;
        return KhVersion.of(next, 0L, 0L, instant);
    }
    public static KhVersion minorUp(KhVersion previous){
        Long next = 1 + previous.minor;
        return KhVersion.of(previous.major, next, 0L);
    }
    public static KhVersion minorUp(KhVersion previous, Instant instant){
        Long next = 1 + previous.minor;
        return KhVersion.of(previous.major, next, 0L, instant);
    }
    public static KhVersion patchUp(KhVersion previous){
        Long next = 1 + previous.patch;
        return KhVersion.of(previous.major, previous.minor, next);
    }
    public static KhVersion patchUp(KhVersion previous, Instant instant){
        Long next = 1 + previous.patch;
        return KhVersion.of(previous.major, previous.minor, next, instant);
    }
    private static Instant nowInstant(){
        return Instant.now();
    }
    public LocalDateTime localDateTime(ZoneId zoneId){
        return LocalDateTime.ofInstant(instant, zoneId);
    }
    public LocalDateTime koreanDateTime(){
        return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));
    }

    @Override
    public int compareTo(KhVersion o) {
        if(equals(o)){
            return 0;
        }
        if(this.major.compareTo(o.major) == 0){
            if(this.minor.compareTo(o.minor) == 0){
                return this.patch.compareTo(o.patch);
            }
            return this.minor.compareTo(o.minor);
        }
        return this.major.compareTo(o.major);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        KhVersion khVersion = (KhVersion) obj;
        return major.equals(khVersion.major)
                && minor.equals(khVersion.minor)
                && patch.equals(khVersion.patch);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(major) + Long.hashCode(minor) + Long.hashCode(patch);
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }
}
