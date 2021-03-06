package zk;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class IdMaker {

    private ZkClient client = null;

    private final String server;

    private final String root;

    private final String nodeName;

    private volatile boolean running = false;

    private ExecutorService cleanExector = null;

    public enum RemoveMethod {

        NONE, IMMEDIATELY, DELAY

    }

    public IdMaker(String zkServer, String root, String nodeName) {
        this.root = root;
        this.server = zkServer;
        this.nodeName = nodeName;
    }

    /**
     * 启动
     *
     * @throws Exception
     */
    public void start() throws Exception {
        if (running) {
            throw new Exception("server has stated...");
        }
        running = true;
        init();
    }

    /**
     * 停止
     *
     * @throws Exception
     */
    public void stop() throws Exception {
        if (!running) {
            throw new Exception("server has stopped...");
        }
        running = false;
        freeResource();
    }

    private void init() {
        //初始化zk客户端和线程池
        client = new ZkClient(server, 5000, 5000, new BytesPushThroughSerializer());
        cleanExector = Executors.newFixedThreadPool(10);
        try {
            client.createPersistent(root, true);
        } catch (ZkNodeExistsException e) {
            e.printStackTrace();
        }
    }

    private void freeResource() {
        cleanExector.shutdown();
        try {
            cleanExector.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cleanExector = null;
        }
        if (client != null) {
            client.close();
            client = null;
        }
    }

    private void checkRunning() throws Exception {
        if (!running)
            throw new Exception("请先调用start");
    }

    private String ExtractId(String str) {

        int index = str.lastIndexOf(nodeName);

        if (index >= 0) {

            index += nodeName.length();

            return index <= str.length() ? str.substring(index) : "";

        }

        return str;

    }

    public String generateId(RemoveMethod removeMethod) throws Exception {
        checkRunning();
        final String fullNodePath = root.concat("/").concat(nodeName);
        final String ourPath = client.createPersistentSequential(fullNodePath, null);
        if (removeMethod.equals(RemoveMethod.IMMEDIATELY)) {
            client.delete(ourPath);
        } else if (removeMethod.equals(RemoveMethod.DELAY)) {
            cleanExector.execute(new Runnable() {
                public void run() {
                    // TODO Auto-generated method stub
                    client.delete(ourPath);
                    System.out.println("删除: ".concat(ourPath));
                }
            });
        }
        return ExtractId(ourPath);
    }

    public static void main(String[] args) throws Exception {
        IdMaker idMaker = new IdMaker("192.168.10.20:2181", "/NameService/IdGen", "ID");
        idMaker.start();
        try {
            for (int i = 0; i < 10; i++) {
                String id = idMaker.generateId(RemoveMethod.DELAY);
                System.out.println(id);
            }
        } finally {
            idMaker.stop();
        }
    }
}