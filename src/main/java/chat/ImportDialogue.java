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
import es.EsAddress;
import es.EsClient;
import org.bson.Document;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.reindex.BulkByScrollResponse;
//import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
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
//    private static final String path = "/home/developer/bat/";
    private static final String path = "Q:\\logs\\";
    /**
     * 插入单条dialogue
     *
     * @throws IOException
     */
    private static void insertOneDialogue() throws IOException {
        int startId = 4994038;
        List<String> contents = UtilsMini.readFileToList(path + "data.txt");
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("dialogue");
        for (String content : contents) {
            ++startId;
            int qid = 0;
            String[] items = content.split("\t");
//            int qid = Integer.valueOf(items[0]);
            String question = items[0];
            try {
                // 先判断是否存在question
                BasicDBObject query = new BasicDBObject();
                // 检索条件
//                query.put("qid", jsonObject.getInteger("db_q_id"));
//            query.put("state", 1);
//                query.put("type","chat_random_hint");
                query.put("question", question);
                FindIterable<Document> findIterable = collection.find(query);
                MongoCursor<Document> mongoCursor = findIterable.iterator();
                if (mongoCursor.hasNext()) {
                    JSONObject jsonObject = JSONObject.parseObject(mongoCursor.next().toJson());
                    qid = jsonObject.getInteger("qid");
//                    UtilsMini.writeToTxt(path+"delete.txt", qid+"\t"+question);
//                    Bson filter = Filters.eq("question", question);
//                    collection.deleteOne(filter);
                }else{
                    qid = startId;
                }
                UtilsMini.writeToTxt(path+"data_new.txt", qid+"\t"+content);
// else{
//                    UtilsMini.writeToTxt(path+"data_not_exist.txt", content);
//                }
//                Document doc = Document.parse(JSONObject.toJSONString(new DialogueEntity(qid, question)));
//                collection.insertOne(doc);
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

    public static void readFileToList() throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.ALPHA);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("dialogue");
//        int count = 0;
        System.out.println(collection.count());
        BasicDBObject query = new BasicDBObject();
        query.put("qid", 4993529);
        FindIterable<Document> findIterable = collection.find(query);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            System.out.println(document.toJson());
        }
        Document document = Document.parse("{\"_class\" : \"com.turing.chat.entity.DialogueEntity\", \"qid\" : 4993530, \"question\" : \"你在做什么\", \"newcmd\" : \"你在做什么\", \"normalization\" : \"你在做什么\", \"create_at\" : { \"$date\" : 1555405197100 }, \"create_by\" : \"张清\", \"review_by\" : \"张清\", \"batch_num\" : \"201905210101\", \"state\" : 1 }".replace("1555405197100", System.currentTimeMillis() + ""));
        collection.insertOne(document);
        System.out.println(collection.count());
        mongoClient.close();
    }

    private static void saveEs(EsAddress esAddress) throws IOException {
        TransportClient client = EsClient.getClient(esAddress);
        List<String> contents = UtilsMini.readFileToList(path + "data_new.txt");
        for (String content : contents) {
            try{
                String[] items = content.split("\t");
                String question = items[1];
                int qid = Integer.valueOf(items[0]);
                XContentBuilder doc = XContentFactory.jsonBuilder()
                        .startObject()
                        .field("question",question)
                        .field("table_name","chat_question")
                        .field("id",qid)
                        .endObject();
                client.prepareIndex("nlp_chat_19-03-12","doc",null).setSource(doc).execute();
                System.out.println(content);
            }catch (Exception e){
                e.printStackTrace();
                System.err.println(content);
            }
        }
        System.out.println("saveEs success");
        client.close();
    }

    private static void deleteEs(EsAddress esAddress) throws IOException {
        TransportClient client = EsClient.getClient(esAddress);
        List<String> contents = UtilsMini.readFileToList(path + "delete.txt");
        for (String content : contents) {
            try{
                String[] items = content.split("\t");
                int qid = Integer.valueOf(items[0]);
                BulkByScrollResponse response =
                        DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                                .filter(QueryBuilders.matchQuery("id", qid))
                                .source("nlp_chat_19-04-03")
                                .get();

                long deleted = response.getDeleted();
                System.out.println("总共删除时间："+deleted);
            }catch (Exception e){
                e.printStackTrace();
                System.err.println(content);
            }
        }
        System.out.println("saveEs success");
        client.close();
    }

    public static void main(String[] args) throws IOException {
//        readFileToList();
//        insertOneDialogue();
//        insertOneAnswer();
        saveEs(EsAddress.PROD_ES_1);
//        deleteEs(EsAddress.PROD_ES_2);
    }
}
