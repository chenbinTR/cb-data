package chat;

import chat.bean.AnswerEntity;
import chat.bean.DialogueEntity;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
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
import utils.CbMongoClient;

import java.io.IOException;

/**
 * 张清——零散的增加语料库需求
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class ImportMongoDialogue {
    private static int qid = 4993534;
    private static String question = "不停放屁";
    private static String[] answers = {"哪有有那么多屁呀？","有时候放起来就停不下来。"};
    private static CbMongoClient.MongoServer mongoServer = CbMongoClient.MongoServer.ALPHA;
    private static EsAddress[] esAddresses = {EsAddress.ALPHA_ES};
    /**
     * 插入单条dialogue
     *
     * @throws IOException
     */
    public static void insertOneDialogue() throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(mongoServer);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("dialogue");
        try {
            Document doc = Document.parse(JSONObject.toJSONString(new DialogueEntity(qid, question)));
            collection.insertOne(doc);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        System.out.println("insertOneDialogue success");
        mongoClient.close();
    }

    /**
     * 插入单条dialogue
     *
     * @throws IOException
     */
    public static void insertOneAnswer(String answer) throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(mongoServer);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("answer");
        Document doc;
        try {
            doc = Document.parse(JSONObject.toJSONString(new AnswerEntity(qid, answer)));
            collection.insertOne(doc);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        System.out.println("insertOneAnswer success");
        mongoClient.close();
    }
    private static void saveEs(EsAddress esAddress) throws IOException {
        TransportClient client = EsClient.getClient(esAddress);
        XContentBuilder doc = XContentFactory.jsonBuilder()
                .startObject()
                .field("question",question)
                .field("table_name","chat_question")
                .field("id",qid)
                .endObject();
        client.prepareUpdate("nlp_chat_19-03-12","doc",null).setDoc(doc).execute();
        System.out.println("saveEs success");
        client.close();
    }

//    public void deleteByQuery(EsAddress esAddress) {
//        TransportClient client = EsClient.getClient(esAddress);
//        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE
//                .newRequestBuilder(client)
//                .filter(QueryBuilders.matchQuery("gender", "male"))
//                .source("persons")
//                .get();
//        long deleted = response.getDeleted();
//    }

    public static void main(String[] args) throws IOException {
        // save dialogue
        insertOneDialogue();
        // save answers
        for(String answer:answers){
            insertOneAnswer(answer);
        }
        // save es
//        for(EsAddress esAddress:esAddresses){
//            saveEs(esAddress);
//        }
    }
}
