package utils.flink

import org.apache.flink.api.common.functions.AggregateFunction
import utils.Parser

/**
  * The accumulator is used to keep a running sum and a count.
  * One counter is initialized for each hour of the day
  */
class CountAggregation extends AggregateFunction[Long, Array[Int], Array[Int]] {

  override def createAccumulator(): Array[Int] = {
    val array = new Array[Int](24)
    array
  }

  override def add(value: Long, accumulator: Array[Int]): Array[Int] = {
    val hour: Int = Parser.convertToDateTime(value).hourOfDay.get()
    accumulator(hour) += 1
    accumulator
  }

  override def getResult(accumulator: Array[Int]): Array[Int] = accumulator

  override def merge(a: Array[Int], b: Array[Int]): Array[Int] = {
    a.zip(b).map { case (x, y) => x + y }
  }

}