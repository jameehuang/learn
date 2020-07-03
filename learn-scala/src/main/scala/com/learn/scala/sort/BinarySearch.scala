package com.learn.java.sort

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._
object BinarySearch {

  def main(args: Array[String]): Unit = {

    val data: Array[Int] = Array(10, 1, 1, 3, 4, -2, 4, 4)
    val ints: Array[Int] = Array(-2, 1, 1, 3, 4, 4, 4, 10)
    val lists: ArrayBuffer[Int] = binarySearch(ints, 0, data.size, 110)
    print(s"元素个数：${lists.size},元素下标分别是：")
    lists.foreach(printf(" %s ", _))
  }

  def binarySearch(datas: Array[Int], left: Int, right: Int, findVal: Int): mutable.ArrayBuffer[Int] = {

    if (left > right || findVal < datas(0) || findVal > datas(datas.size-1)) {
      return new mutable.ArrayBuffer[Int]()
    }

    var midIndex = (left + right) / 2
    if (findVal > datas(midIndex)) {
      binarySearch(datas, midIndex, right, findVal)
    } else if (findVal < datas(midIndex)) {
      binarySearch(datas, left, midIndex, findVal)
    } else {
      val arrays: ArrayBuffer[Int] = new mutable.ArrayBuffer[Int]()
      breakable {//从右向左遍历
      var temp = midIndex
        while (true) {
          if (datas(temp) == findVal) {
            arrays.append(temp)
          } else {
            break()
          }
          temp -= 1
          if (temp < 0) {
            break()
          }
        }
      }

      breakable {//从右向左
      var temp = midIndex
        while (true) {
          temp += 1
          if (temp > datas.size-1) {
            break()
          }
          if (datas(temp) == findVal) {
            arrays.append(temp)
          } else {
            break()
          }
        }
      }
      arrays
    }
  }

}
