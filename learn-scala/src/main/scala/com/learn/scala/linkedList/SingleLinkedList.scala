package com.learn.scala.linkedList

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

object SingleLinkedList {

  def main(args: Array[String]): Unit = {

    val p1: People = People(1, "huangjianming", RelationShip.OWNER)
    val p2: People = People(3, "huang XXX", RelationShip.FAMILY)
    val p3: People = People(6, "huang XY", RelationShip.FRIEND)
    val p4: People = People(2, "huo xy", RelationShip.FRIEND)
    val p5: People = People(4, "bi bi", RelationShip.COLLEAGUE)

    val peoples: SingleLinkedList = new SingleLinkedList()
    peoples.add(p1)
    peoples.add(p2)
    peoples.add(p3)
    peoples.add(p4)
    peoples.add(p5)
    peoples.scanAll

    peoples.scanAll(2)

    peoples.update(People(2, "huobaoxia", RelationShip.LOVER))
    peoples.scanAll

    peoples.delete(4)
    peoples.scanAll

    peoples.addBySort(people = p1)
    peoples.addBySort(people = p2)
    peoples.addBySort(people = p3)
    peoples.addBySort(people = p4)
    peoples.addBySort(people = p5)
    peoples.scanAll

    peoples.addBySort(false, p1)
    peoples.addBySort(false, p2)
    peoples.addBySort(false, p3)
    peoples.addBySort(false, p4)
    peoples.addBySort(false, p5)
    peoples.scanAll

  }
}

class SingleLinkedList extends LinkedList {
  // 头节点，仅用于标记
  var head = People(-1, "", RelationShip.NULL)

  def isEmpty(): Boolean = {
    head.next == null
  }

  override def add(people: People): Unit = {
    var current = head
    breakable {
      while (true) {
        if (current.next == null) {
          break()
        }
        //后移遍历
        current = current.next
      }
    }
    current.next = people
  }

  override def delete(id: Int): Unit = {
    var current = head
    if (isEmpty()) {
      println("当前队列为空，无法删除")
      return
    }
    var flag = false
    breakable {
      while (true) {
        if (current.next == null) {
          break()
        }
        if (current.next.id == id) {
          flag = true
          break()
        }
        current = current.next
      }
    }
    if (flag) {
      current.next = current.next.next
    } else {
      println(s"当前队列中无id=${id}的人，请确认")
    }
  }

  override def update(people: People): Unit = {
    var current = head
    if (isEmpty()) {
      println("当前队列为空，无法更新")
      return
    }
    var flag = false
    breakable {
      while (true) {
        if (current.next == null) {
          break()
        }
        if (current.next.id == people.id) {
          flag = true
          break()
        }
        current = current.next
      }
    }

    if(flag) {
      current.next.name = people.name
      current.next.relationship = people.relationship
    } else {
      println(s"查无此人:${people}，无法更新")
    }
  }

  override def get(id: Int): Unit = {
    var current = head
    if (isEmpty()) {
      println("当前队列为空，无需查找")
      return
    }
    var flag: Boolean = false
    breakable {
      while (true) {
        if (current.next == null) {
          break()
        }
        if (current.next.id == id) {
          flag = true
          break()
        }
        current = current.next
      }
    }

    if (flag) {
      println(s"查询信息为：${current.next}")
    } else {
      println(s"查无id=${id}的人，请确认")
    }
  }

   def show(id: Int): ArrayBuffer[People] = {

   var peoples: ArrayBuffer[People] = new ArrayBuffer[People]()
    var current = head

  /*   if (peoples != null)
       peoples.clear()*/

    if (isEmpty()) {
      println("当前队列为空，无需遍历队列")
      return null
    }
    breakable {
      while (true) {

        if (current.id == id) {
          peoples.append(current)
          break()
        }

        if (current.next == null) {
          break()
        }
        if (id < -1) {
          peoples.append(current.next)
        }

        current = current.next
      }
    }
    peoples
  }

  override def scanAll(implicit id: Int = -2): Unit = {
    val peoples: ArrayBuffer[People] = show(id)
    if (peoples != null) {
      print("当前队列里共有：")
      peoples.foreach(print(_))
    }
    println
    println
  }

  def isExists(people: People): Boolean = {
    var peoples = show(people.id)
    if (peoples == null) {
      return false
    }
    val isExist: Boolean = peoples.exists(p => {
      p.id == people.id
    })

    isExist
  }

  override def addBySort(implicit order: Boolean = true, people: People): Unit = {
    var current = head
    var flag = false

    breakable {
      while (true) {
        if (isExists(people)) {
          break()
        }
        if (current.next == null) {
          flag = true
          break()
        }
        if (order) {
          if (current.next.id > people.id) {
            flag = true
            break()
          }
        } else {
          if (current.next.id < people.id) {
            flag = true
            break()
          }
        }
        current = current.next
      }
    }

    if (flag) {
      people.next = current.next
      current.next = people
    } else {
      println(s"队列中已存在${people}，不必添加")
    }
  }
}
