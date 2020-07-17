package chat;

import chat.bean.AnswerEntity;
import chat.bean.DialogueEntity;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import utils.CbMongoClient;
import utils.UtilsMini;

import java.io.IOException;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class ImportDialogue {
        private static final String path = "/home/developer/bat/";
//    private static final String path = "E:\\logs\\";
    //    private static int startId = 4994039;
    private static int startId = 5000000;

    /**
     * 插入单条dialogue
     *
     * @throws IOException
     */
    private static void createQid() throws IOException {
        List<String> contents = UtilsMini.readFileToList(path + "data.txt");
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("dialogue");
        for (String content : contents) {
            int qid = 0;
            String[] items = content.split("\t");
            String question = items[0];
            try {
                // 先判断是否存在question
//                BasicDBObject query = new BasicDBObject();
//                query.put("question", question);
//                FindIterable<Document> findIterable = collection.find(query);
//                MongoCursor<Document> mongoCursor = findIterable.iterator();
//                if (mongoCursor.hasNext()) {
//                    JSONObject jsonObject = JSONObject.parseObject(mongoCursor.next().toJson());
//                    qid = jsonObject.getInteger("qid");
//                } else {
                    qid = startId;
                    ++startId;
//                }
                UtilsMini.writeToTxt(path + "data_new.txt", qid + "\t" + content);
            } catch (Exception e1) {
                System.err.println(content);
                e1.printStackTrace();
            }
        }
        mongoClient.close();
    }

    /**
     * 插入单条dialogue
     *
     * @throws IOException
     */
    private static void insertOneDialogue() throws IOException {
        List<String> contents = UtilsMini.readFileToList(path + "dd.txt");
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("dialogue");
        for (String content : contents) {
            String[] items = content.split("\t");
            int qid = Integer.valueOf(items[0]);
            String question = items[1];
            try {
//                if (qid >= startId) {
                    Document doc = Document.parse(JSONObject.toJSONString(new DialogueEntity(qid, question)));
                    collection.insertOne(doc);
//                }
            } catch (Exception e1) {
                System.err.println(content);
                e1.printStackTrace();
            }
        }
        System.out.println("insertOneDialogue success");
        mongoClient.close();
    }

    /**
     * 插入单条dialogue
     *
     * @throws IOException
     */
    private static void insertOneAnswer() throws IOException {
        List<String> contents = UtilsMini.readFileToList(path + "data_new.txt");
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("answer");
        for (String content : contents) {
            String[] items = content.split("\t");
            int qid = Integer.valueOf(items[0]);
            try {
                String answer = items[2];
                Document doc = Document.parse(JSONObject.toJSONString(new AnswerEntity(qid, answer)));
                collection.insertOne(doc);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
//            try {
//                String answer = items[3];
//                Document doc = Document.parse(JSONObject.toJSONString(new AnswerEntity(qid, answer)));
//                collection.insertOne(doc);
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//            try {
//                String answer = items[4];
//                Document doc = Document.parse(JSONObject.toJSONString(new AnswerEntity(qid, answer)));
//                collection.insertOne(doc);
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
        }
        System.out.println("*********************insertOneAnswer success");
        mongoClient.close();
    }


    public static void main(String[] args) throws IOException {
        // 先生成qid
//        createQid();
        // 创建q
        insertOneDialogue();
        // 创建a
//        insertOneAnswer();
//        ChatEs.saveEs(EsAddress.PROD_ES_1, "nlp_chat_19-03-12", path + "data_new.txt");
//        saveEs(EsAddress.PROD_ES_2, "nlp_chat_19-03-12");
//        deleteEs(EsAddress.PROD_ES_2);
    }
}
