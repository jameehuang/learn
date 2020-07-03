package com.learn.zookeeper.test;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperTest {
  private static CountDownLatch CDL = new CountDownLatch(1);
  public static void main(String[] args) {
    try {
      String znode = "/test-sync";
      String asyncZnode = "/test-async";
      ZookeeperTest zookeeperTest = new ZookeeperTest();
      ZooKeeper zooKeeper = new ZooKeeper("jamee1:2181,jamee2:2181", 90000, new ZKWatcher(CDL));
      CDL.await();
      zookeeperTest.syncCall(zooKeeper, znode);
      zookeeperTest.asyncCall(zooKeeper, asyncZnode);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (KeeperException e) {
      e.printStackTrace();
    }
  }

  private void syncCall(ZooKeeper zooKeeper, String znode) throws KeeperException, InterruptedException {

    byte[] data = null;
    try {
      data = zooKeeper.getData(znode, new ZKWatcher(zooKeeper, znode), new Stat());
    } catch (KeeperException e) {
      switch (e.code()) {
        case OK:
          System.out.println("next step...");
          break;
        case NONODE:
          zooKeeper.create(znode, "huangjianming".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
          data = zooKeeper.getData(znode, new ZKWatcher(zooKeeper, znode), new Stat());
          break;
        default:
          e.printStackTrace();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(new String(data));
    zooKeeper.setData(znode, "sync??? what fucking!!!".getBytes(), -1);
    Thread.sleep(10);
  }

  private void asyncCall(ZooKeeper zooKeeper, String znode) throws InterruptedException {

    zooKeeper.create(znode, "huangjianming-async".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new ZKCallBack(zooKeeper), null);
    Thread.sleep(100);
  }
}

class ZKCallBack implements AsyncCallback.StringCallback {

  private ZooKeeper zooKeeper;
  public ZKCallBack(ZooKeeper zk) {
    zooKeeper = zk;
  }
  @Override
  public void processResult(int rc, String path, Object ctx, String name) {

    Code code = Code.get(rc);
    switch (code) {
      case OK:
        System.out.println("create " + path + " success...");
//        System.out.println("rc=" + rc + ", path=" + path + ", ctx=" + ctx + ", name=" + name);
        try {
          zooKeeper.exists(name, new ZKWatcher(zooKeeper, name));
          zooKeeper.setData(name, "async??? what fucking...".getBytes(), -1);
        } catch (KeeperException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        break;
      case NODEEXISTS:
        System.out.println("create " + path + " failed, the znode is existed.");
//        System.out.println("rc=" + rc + ", path=" + path + ", ctx=" + ctx + ", name=" + name);
        try {
          zooKeeper.exists(name, new ZKWatcher(zooKeeper, name));
          zooKeeper.setData(name, "is the znode existed?? what fucking...".getBytes(), -1);
          zooKeeper.delete(path, -1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (KeeperException e) {
          e.printStackTrace();
        }
        break;
      default:
        System.out.println("rc=" + rc + ", path=" + path + ", ctx=" + ctx + ", name=" + name);
        break;
    }
  }
}

class ZKWatcher implements Watcher {

  private CountDownLatch cdl = null;
  private ZooKeeper zk = null;
  private String znode;

  public ZKWatcher(CountDownLatch countDownLatch) {
    cdl = countDownLatch;
  }

  public ZKWatcher(ZooKeeper zookeeper, String znode) {
    zk = zookeeper;
    this.znode = znode;
  }

  @Override
  public void process(WatchedEvent event) {
    switch (event.getType()) {
      case None:
        switch (event.getState()) {
          case Disconnected:
            System.out.println("disconnected..");
            break;
          case Closed:
            System.out.println("closed..");
            break;
          case SyncConnected:
            System.out.println("success...");
            cdl.countDown();
            break;
          default:
            System.out.println("the current envent state is: " + event.getState());
            return;
        }
        break;

      case NodeDataChanged:
        try {
          byte[] data = zk.getData(znode, null, null);
          System.out.println(new String(data));
        } catch (KeeperException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        break;
      default:
        System.out.println("the event type: " + event.getType());
    }
  }
}
