package Auxiliary;

import java.util.Map;

public interface ISortPerformanceCounter {
    Map<String, Integer> sort();
    long getExecutionTime();
}
