package com.learn.java.linkedList

import scala.collection.mutable.ArrayBuffer

trait LinkedList {

  def add(people: People)
  def delete(id: Int)
  def update(people: People)
  def get(id: Int)
  def scanAll(implicit id: Int)
  def addBySort(implicit order: Boolean = true, people: People)
}


case class People (id: Int, var name: String, var relationship: RelationShip.Value) {
  var next: People = null; // 链表的当前元素的下一个元素
  var pre: People = null; // 链表的当前元素的上一个元素，仅用于双链表中

  override def toString: String = {
    s" People(id=${id}, name=${name}, relationship=${relationship}) "
  }
}