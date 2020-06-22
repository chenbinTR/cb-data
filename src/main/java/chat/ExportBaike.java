package chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import utils.CbMongoClient;
import utils.UtilsMini;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2020-05-22
 * @see
 * @since
 */
public class ExportBaike {
    private static String file = "/home/developer/bat/chat/%s"+"_"+ LocalDate.now()+".txt";
    private static final String[] collections = {
//            "answer",
//            "dialogue",
//            "center_word",
//            "guide_answer",
//            "guide_intent",
//            "guide_recommendation",
//            "high_answer",
//            "high_dialogue",
//            "kv",
//            "kv_config",
//            "kv_data",
//            "phrase",
//            "template",
//            "linked_answer",
//            "modify_answer",
//            "noun_category",
//            "sensitive_answer",
//            "sensitive_dialogue",
//            "humor_high_dialogue20180708",
//            "humor_high_answer20180708"
            "baidu"
    };
    public static void main(String[] args) throws IOException {
        for(String collectionName:collections){
            exportDb(collectionName);
        }
    }
    private static void exportDb(String collectionName) throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.Data_PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("knowledgegraph");
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        String path = String.format(file, collectionName);
        System.out.println(collection.count());
        try {
//            BasicDBObject query = new BasicDBObject();
            // 检索条件
//                query.put("qid", jsonObject.getInteger("db_q_id"));
//            query.put("state", 1);
//                query.put("type","chat_random_hint");
//                query.put("qid", 84);
//            FindIterable<Document> findIterable = collection.find(query);
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            Document document;
            List<String> documentList  = new ArrayList<>();
            int count = 0;
            while (mongoCursor.hasNext()) {
                document = mongoCursor.next();
                try{
                    JSONObject jsonObject = JSON.parseObject(document.toJson());
                    JSONObject type = jsonObject.getJSONObject("type");
                    if(type.toString().indexOf("人物")>-1 || type.toString().indexOf("历史")>-1 || type.toString().indexOf("地理")>-1){
                        UtilsMini.writeToTxt(path, jsonObject.toJSONString());
                    }
//                    documentList.add(document.toJson());
//                    if(documentList.size() == 10000){
//                        UtilsMini.writeToTxt(path, StringUtils.join(documentList.toArray(), "\r\n"));
//                        documentList.clear();
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
//            UtilsMini.writeToTxt(path, StringUtils.join(documentList.toArray(), "\r\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongoClient.close();
    }
}
