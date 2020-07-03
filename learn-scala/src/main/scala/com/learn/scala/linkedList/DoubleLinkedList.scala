package com.learn.java.linkedList

import scala.util.control.Breaks._

object DoubleLinkedList {

  def main(args: Array[String]): Unit = {
    val p1: People = People(1, "huangjianming", RelationShip.OWNER)
    val p2: People = People(3, "huang XXX", RelationShip.FAMILY)
    val p3: People = People(6, "huang XY", RelationShip.FRIEND)
    val p4: People = People(2, "huo xy", RelationShip.FRIEND)
    val p5: People = People(4, "bi bi", RelationShip.COLLEAGUE)

    val peoples: DoubleLinkedList = new DoubleLinkedList()
    peoples.add(p1)
    peoples.add(p2)
    peoples.add(p3)
    peoples.add(p4)
    peoples.add(p5)
    peoples.scanAll

    peoples.scanAll(2)

    peoples.update(People(2, "huobaoxia", RelationShip.LOVER))
    peoples.scanAll

    peoples.get(2)

    peoples.delete(4)
    peoples.scanAll
  }
}

class DoubleLinkedList extends LinkedList {

  var head: People = new People(-1, "", RelationShip.NULL)
  var tail: People = null

  def isEmpty(): Boolean = {
    head.next == null
  }
  override def add(people: People): Unit = {

    var current = head
    breakable {
      while (true) {
        if (current.next == null)
          break()
        current = current.next
      }
    }
    current.next = people
    people.pre = current
  }

  override def delete(id: Int): Unit = {
    var current = head.next
    if (isEmpty()) {
      println("当前队列为空，无法删除")
      return
    }
    var flag = false
    breakable {
      while (true) {
        if (id == current.id) {
          flag = true
          break()
        }
        if (current.next == null) {
          break()
        }
        current = current.next
      }
    }
    if (flag) {
      current.pre.next = current.next
      if (current.next != null)
        current.next.pre = current.pre
    } else {
      println(s"查不到id=${id}的人, 无法执行删除操作")
    }
  }

  override def update(people: People): Unit = {
    var current = head.next
    if (isEmpty()) {
      println("当前队列为空，无法更新")
      return
    }
    var flag = false
    breakable {
      while (true) {
        if (current.id == people.id) {
          flag = true
          break()
        }
        if (current.next == null) {
          break()
        }
        current = current.next
      }
    }
    if (flag) {
      current.name = people.name
      current.relationship = people.relationship
    } else {
      println(s"更新的id=${people.id}的人不存在")
    }
  }

  override def get(id: Int): Unit = {
    scanAll(id)
  }

  override def scanAll(implicit id: Int = -2): Unit = {
    var current = head.next
    if (isEmpty()) {
      println("当前队列为空，无需遍历队列")
      return null
    }
    print("当前队列中有：")
    breakable {
      while (true) {
        if (current.id == id) {
          println(s" ${current} ")
          break()
        }
        if (id < -1) {
          print(s" ${current} ")
        }
        if (current.next == null) {
          break()
        }
        current = current.next
      }
    }
    println
    println
  }

  override def addBySort(implicit order: Boolean, people: People): Unit = {
    new Exception("Unsupported")
  }
}
