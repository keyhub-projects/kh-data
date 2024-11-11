package keyhub.data.order;

import java.util.*;
import java.util.stream.Stream;

public class KhOrderList<T> extends AbstractList<T> {
    private static final Long FIRST_KEY = 0L;
    private static final KhOrder FIRST_KHORDER = KhOrder.of(0L, FIRST_KEY, 0L);
    private static final Long MAX_KEY = Long.MAX_VALUE;
    private static final Long MIN_KEY = Long.MIN_VALUE;
    private final Map<KhOrder, T> map = new HashMap<>();

    KhOrder generateFirst(){
        return FIRST_KHORDER;
    }

    KhOrder generateNext(KhOrder order){
        try{
            Optional<KhOrder> nextOption = findNextKhOrder(order);
            if(nextOption.isEmpty()){
                long middle = (order.fixedKey() + MAX_KEY) / 2;
                if(middle <= 0){
                    // todo - variableKey 작업
                    return null;
                }
                return KhOrder.of(order.bucket(), middle, 0L);
            }
            KhOrder next = nextOption.get();
            long middle = (order.fixedKey() + next.fixedKey()) / 2;
            if(middle <= 0){
                // todo - variableKey 작업
                return null;
            }
            return KhOrder.of(order.bucket(), middle, 0L);
        }finally {
            // todo counter 처리 - bucket 밸런싱을 위함
            // todo counter 차면 밸런싱 처리
        }
    }

    KhOrder generatePrevious(KhOrder order){
        try{
            Optional<KhOrder> prevOption = findPreviousKhOrder(order);
            if(prevOption.isEmpty()){
                long middle = (order.fixedKey() + MIN_KEY) / 2;
                if(middle <= 0){
                    // todo - variableKey 작업
                    return null;
                }
                return KhOrder.of(order.bucket(), middle, 0L);
            }
            KhOrder prev = prevOption.get();
            long middle = (order.fixedKey() + prev.fixedKey()) / 2;
            if(middle <= 0){
                // todo - variableKey 작업
                return null;
            }
            return KhOrder.of(order.bucket(), middle, 0L);
        }finally {
            // todo counter 처리 - bucket 밸런싱을 위함
            // todo counter 차면 밸런싱 처리
        }
    }

    public KhOrder getLastKhOrder(){
        return map.keySet().stream().reduce((first, second) -> second)
                .orElse(null);
    }

    public KhOrder getKhOrder(int index){
        return streamOfKhOrder().limit(index+1).toList().get(index);
    }

    Optional<KhOrder> findNextKhOrder(KhOrder order){
        int index = indexOf(order);
        try{
            return Optional.of(getKhOrder(index+1));
        }catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    Optional<KhOrder> findPreviousKhOrder(KhOrder order){
        int index = indexOf(order);
        try{
            return Optional.of(getKhOrder(index-1));
        }catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    int indexOf(KhOrder order){
        return streamOfKhOrder()
                .takeWhile(khOrder -> !khOrder.equals(order))
                .toList()
                .size();
    }

    @Override
    public int size() {
        return map.size();
    }

    public Stream<KhOrder> streamOfKhOrder(){
        return map.keySet().stream()
                .sorted(Comparator.comparing(KhOrder::bucket)
                        .thenComparing(KhOrder::fixedKey)
                        .thenComparing(KhOrder::variableKey)
                );
    }

    @Override
    public Stream<T> stream(){
        @SuppressWarnings("unchecked")
        Stream<T> stream = map.values().stream()
                .sorted((Comparator<? super T>) Comparator.comparing(KhOrder::bucket)
                        .thenComparing(KhOrder::fixedKey)
                        .thenComparing(KhOrder::variableKey)
                );
        return stream;
    }

    @Override
    public T get(int index) {
        return stream().limit(index+1).toList().get(index);
    }

    @Override
    public T set(int index, T element) {
        rangeCheckForAdd(index);
        KhOrder cursor = getKhOrder(index);
        return map.put(cursor, element);
    }

    @Override
    public void add(int index, T element) {
        rangeCheckForAdd(index);
        if(isEmpty()){
            KhOrder first = generateFirst();
            map.put(first, element);
        }
        else if(index == size()){
            KhOrder last = getLastKhOrder();
            KhOrder next = generateNext(last);
            map.put(next, element);
        }
        else{
            KhOrder cursor = getKhOrder(index);
            KhOrder pre = generatePrevious(cursor);
            map.put(pre, element);
        }
    }

    @Override
    public T remove(int index) {
        KhOrder cursor = getKhOrder(index);
        return map.remove(cursor);
    }

    void rangeCheckForAdd(int index) {
        if (index > size() || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: " + size();
    }
}
