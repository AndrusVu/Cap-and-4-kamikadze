package Auxiliary

import java.util

import collection.JavaConversions._
import Array._

class ScalaSortPerformanceCounter(toSort: java.util.Map[String, Integer]) extends ISortPerformanceCounter {
  var executionTime: Long = 0;

  override def sort() : java.util.Map[String, Integer] ={
    val values: Array[Integer] = toSort.values().toArray(new Array[Integer](toSort.size()))
    val keys: Array[String] = toSort.keySet().toArray(new Array[String](toSort.size()))

    val startTime: Long = System.currentTimeMillis();
    sort(values, keys)
    executionTime = System.currentTimeMillis() - startTime

    val sorted: java.util.Map[String, Integer] =
      new java.util.LinkedHashMap[String, Integer]()

    for(i <- 0 to (toSort.size()- 1) ) sorted.put(keys(i), values(i))

    return sorted;
  }

  override def getExecutionTime() : Long ={
    return executionTime
  }

  private def sort(xs: Array[Integer], xsi :Array[String]) ={
    def swap(i: Int, j: Int) {
      val t = xs(i)
      xs(i) = xs(j)
      xs(j) = t
    }
    def swapi(i: Int, j: Int) {
      val t = xsi(i)
      xsi(i) = xsi(j)
      xsi(j) = t
    }
    def sort1(l: Int, r: Int) {
      val pivot = xs((l + r) / 2)
      var i = l
      var j = r
      while (i <= j) {
        while (xs(i) < pivot) i += 1
        while (xs(j) > pivot) j -= 1
        if (i <= j) {
          swap(i, j)
          swapi(i, j)
          i += 1
          j -= 1
        }
      }
      if (l < j) sort1(l, j)
      if (j < r) sort1(i, r)
    }
    sort1(0, xs.length - 1)
  }
}
