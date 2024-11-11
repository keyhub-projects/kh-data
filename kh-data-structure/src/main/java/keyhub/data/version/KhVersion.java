package keyhub.data.version;

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
