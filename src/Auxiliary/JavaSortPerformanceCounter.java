package Auxiliary;

import java.util.*;

public class JavaSortPerformanceCounter implements ISortPerformanceCounter {
    final private Map<String, Integer> toSort;
    private long executionTime;

    public JavaSortPerformanceCounter(final Map<String, Integer> toSort){
        this.toSort = toSort;
        executionTime = 0;
    }

    @Override
    public Map<String, Integer> sort(){
        List list = new LinkedList(toSort.entrySet());

        long startTime = System.currentTimeMillis();
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable)((Map.Entry)(o1)).getValue()).
                        compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        executionTime = System.currentTimeMillis() - startTime;

        HashMap sortedHashMap = new LinkedHashMap();
        for(Iterator it = list.iterator(); it.hasNext();){
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    @Override
    public long getExecutionTime(){
        return executionTime;
    }

}
