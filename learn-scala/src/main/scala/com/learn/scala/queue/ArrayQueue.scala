package com.learn.java.queue

import scala.io.StdIn

/**
  * 队列无法复用，导致队列满后即使取出一条数据也无法再存入新的数据。
  */
object ArrayQueue {

  def main(args: Array[String]): Unit = {

    showOP()
    val queue: ArrayQueue = new ArrayQueue(4)
    while (true) {
      val key: String = StdIn.readLine()
      key match {
        case "push" =>
          val i: Int = StdIn.readInt()
          printf("数据 %d 放入队列中\n", i)
          queue.push(i)
        case "poll" =>
          println("取出数据：")
          queue.poll()
        case "list" =>
          "当前队列："
          queue.listAll()
        case "quit" =>
          println("查询完毕，退出程序")
          return
        case _ =>
          println(new Exception("未知操作, 请确认后重新输入").getMessage)
      }
    }
  }

  def showOP(): Unit = {
    println(
      """
        |队列遵循"先入先出"的原则
        |可选操作如下：
        |1.输入"list": 查看现有队列内容
        |2.输入"push": 向队列中存入数据
        |3.输入"poll": 从队列中获取一条数据
        |4.输入"quit": 查询完毕，退出程序
      """.stripMargin)
  }

}
class ArrayQueue(arrayMaxSize: Int) {

  val maxSize = arrayMaxSize
  val arr = new Array[Int](maxSize)
  var front = 0 //队首
  var rear = 0 //队尾

  def isFull(): Boolean = {
    rear == maxSize
  }

  def isEmpty(): Boolean = {
    front == rear
  }

  /**
    * 取出数据
    */
  def poll(): Unit = {

    if (isEmpty()) {
      println("队列为空，无法取出数据")
      return
    }
    println(arr(front))
    front += 1
  }

  /**
    * 添加数据
    * @param num
    */
  def push(num: Int): Unit = {
    if (isFull()) {
      println("队列已满，无法存入数据:" + num)
      return
    }
    arr(rear) = num
    rear += 1
  }

  /**
    * 打印整个队列值
    */
  def listAll(): Unit = {
    if (isEmpty()) {
      println("队列为空")
      return
    }
    print("Queue(")
    for (i <- front to rear - 1) {
      printf(" arr(%d) = %d ,", i, arr(i))
    }
    println(")")
  }
}