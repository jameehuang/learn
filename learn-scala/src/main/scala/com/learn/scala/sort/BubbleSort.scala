package com.learn.scala.sort

object BubbleSort {

  def main(args: Array[String]): Unit = {
    val data: Array[Int] = Array(10, 1, 1, 3, 4, -2, 4, 4)
    println(s"初始值： ${data.toList}")
    for (i <- 0 until data.length-1) {
      for (j <- 0 until data.length-1-i) {
        if (data(j) > data(j+1)) {
          data(j) = data(j) ^ data(j+1)
          data(j+1) = data(j) ^ data(j+1)
          data(j) = data(j) ^ data(j+1)
        }
        println("打印中间交换过程：" + data.toList)
      }
    }

    println("最终结果值：" + data.toList)
  }
}
