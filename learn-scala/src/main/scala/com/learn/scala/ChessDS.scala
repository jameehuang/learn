package com.learn.scala

import java.util.Scanner

import scala.collection.mutable.ArrayBuffer


object ChessDS {

  def initChess(rows: Int, cols: Int): Array[Array[Int]] = {

    //初始化棋盘的二维数组
    val chessMap: Array[Array[Int]] = Array.ofDim[Int](rows, cols)
    println("请输入3个不小于0的整数，并以逗号分隔")
    val scanner: Scanner = new Scanner(System.in)
    while (!scanner.hasNext("wocao")) {
      val words: Array[String] = scanner.nextLine().split(" ")
      if (words.length != 3) {
        println("请输入3个不小于0的整数，并以逗号分隔")
      } else {
        chessMap(words(0))(words(1)) = words(2)
      }
    }

    println("打印棋盘模型：")
    printChess(chessMap)

    chessMap
  }

  implicit def StringToInt(string: String): Int = {
    Integer.parseInt(string)
  }

  def compressChess(rows: Int, cols: Int, chessMap: Array[Array[Int]]): ArrayBuffer[ChessNode] = {

    val compressChessArray: ArrayBuffer[ChessNode] = ArrayBuffer[ChessNode]()
    for (i <- 0 until chessMap.length) {
      for (j <- 0 until chessMap(i).length) {
        val value: Int = chessMap(i)(j)
        if ( value != 0) {
          compressChessArray.append(ChessNode(i, j, value))
        }
      }
    }

    println("压缩后的棋盘模型：")
    compressChessArray.foreach(x => {
      println(x.row, x.col, x.color)
    })

    compressChessArray

  }

  def restoreChess(rows: Int, cols: Int, compressChessArray: ArrayBuffer[ChessNode]): Unit = {

    val chessMap: Array[Array[Int]] = Array.ofDim[Int](rows, cols)
    compressChessArray.foreach(x => {
      chessMap(x.row)(x.col) = x.color
    })

    println("打印恢复后的棋盘模型：")
    printChess(chessMap)
  }

  def printChess(chessMap: Array[Array[Int]]): Unit = {
    chessMap.foreach(x => {
      x.foreach(printf("%d ", _))
      println
    })
  }

  def main(args: Array[String]): Unit = {
    val rows = 10
    val cols = 10
    val initChessMap: Array[Array[Int]] = initChess(rows, cols)
    val compressChessArray: ArrayBuffer[ChessNode] = compressChess(rows, cols, initChessMap)
    restoreChess(rows, cols, compressChessArray)
  }
  case class ChessNode(row: Int, col: Int, color: Int)
}

