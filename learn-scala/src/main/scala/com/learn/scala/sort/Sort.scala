package com.learn.java.sort

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

object Sort {

  /**
    * 冒泡排序
    * @param data
    * @return
    */
  def bubbleSort(data: Array[Int]): Array[Int] = {
    for (x <- 0 until data.length - 1) {
      for (i <- 0 until(data.length - 1 -x)) {
        if (data(i) > data(i + 1)) {
          data(i) = data(i) ^ data(i + 1)
          data(i + 1) = data(i) ^ data(i + 1)
          data(i) = data(i) ^ data(i + 1)
        }
//        println(data.toList)
      }
    }
    data
  }

  /**
    * 二分查找
     * @param data 传入的有序集合
    * @param left 集合的最小值下标
    * @param right 集合的最大值下标
    * @param findId 需要查找的值
    * @return 集合中所查找值的所有下标
    */
  def binarySearch(data: Array[Int], left: Int, right: Int, findId: Int): ArrayBuffer[Int] = {

    if (left > right) {
      println(s"ERROR：left=$left > right=${right}")
    }
    val midIndex = (left + right) / 2
    if (findId < data(midIndex)) {
      binarySearch(data, left, midIndex, findId)
    } else if (findId > data(midIndex)) {
      binarySearch(data, midIndex, right, findId)
    } else {
      val arrays: ArrayBuffer[Int] = mutable.ArrayBuffer[Int]()
      var temp = midIndex
      //从左向右递归
      breakable {
        while (true) {
          if (data(temp) == findId) {
            arrays.append(temp)
          } else {
            break()
          }
          if (temp == 0) {
            break()
          }
          temp -= 1
        }
      }
      //从右向左递归
      var temp1 = midIndex
      breakable {
        while (true) {
          temp1 += 1
          if (temp1 == data.size) {
            break()
          }
          if (data(temp1) == findId) {
            arrays.append(temp1)
          } else {
            break()
          }
        }
      }
      arrays
    }
  }
  def main(args: Array[String]): Unit = {

    val data: Array[Int] = Array(1, 1, 3, 4, 2, 4, 4)

    //排序后的数据
    val sortedData: Array[Int] = bubbleSort(data)
    val lists: ArrayBuffer[Int] = binarySearch(data, 0, data.size, 4)
    print(s"元素个数：${lists.size}, 下标分别是：")
    bubbleSort(lists.toArray)
      .toList
      .foreach(printf(" %s ", _))

  }
}
