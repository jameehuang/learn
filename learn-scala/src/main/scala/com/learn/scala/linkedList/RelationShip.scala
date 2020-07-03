package com.learn.java.linkedList

object RelationShip extends Enumeration {

  val FRIEND = Value("friend")
  val FAMILY = Value("family")
  val LOVER = Value("lover")
  val COLLEAGUE = Value("colleague")
  val OWNER = Value("myself")
  val NULL = Value("NOTHING")

  def main(args: Array[String]): Unit = {
    println(LOVER.toString)
  }
}
