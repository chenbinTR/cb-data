package chat;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class ExportChatCopy {
    private static List<String> keys = Arrays.asList("小宝贝儿","小主人儿","小主人","小朋友","小宝宝","小宝贝");
    private static final String PATH = "/home/developer/bat/chat_sensitive/%s_child_kv_prod_20200312.txt";
    private static final String[] collections = {
            "answer",
            "dialogue",
            "center_word",
            "guide_answer",
            "guide_intent",
            "guide_recommendation",
            "high_answer",
            "high_dialogue",
            "kv",
            "kv_config",
            "kv_data",
            "phrase",
            "template",
            "linked_answer",
            "modify_answer",
            "noun_category",
            "sensitive_answer",
            "sensitive_dialogue"
    };
    public static void processDialogue(String path, String collectionName) throws IOException {
        int count=0;
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.DEV);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        System.out.println(collection.count());
        try {
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            Document document;
            while (mongoCursor.hasNext()) {
                try{
                    count++;
                    document = mongoCursor.next();
                    JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                    int state = jsonObject.getInteger("state");
                    if(0 == state){
                        continue;
                    }
                    String question = jsonObject.getString("question");
                    for(String key:keys){
                        if(StringUtils.isBlank(key)){
                            continue;
                        }
                        if(question.indexOf(key)>-1){
                            jsonObject.put("sensitive_word",key);
                            UtilsMini.writeToTxt(path, jsonObject.toJSONString());
                            break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongoClient.close();
    }
    public static void processAnswer(String path, String collectionName) throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        System.out.println(collection.count());
        try {
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            Document document;
//            List<String> documentList  = new ArrayList<>();
//            int count = 0;
            while (mongoCursor.hasNext()) {
                try{
                    document = mongoCursor.next();
                    JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                    int state = jsonObject.getInteger("state");
                    if(0 == state){
                        continue;
                    }
//                    Integer channel = jsonObject.getJSONObject("tags").getInteger("channel");
//                    if(channel!=null && channel==1){
//                        continue;
//                    }
                    String answer = jsonObject.getJSONArray("infos").getJSONObject(0).getString("content");
                    for(String key:keys){
                        if(StringUtils.isBlank(key)){
                            continue;
                        }
                        if(answer.indexOf(key)>-1){
                            jsonObject.put("sensitive_word",key);
                            UtilsMini.writeToTxt(path, jsonObject.toJSONString());
                            break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

//                documentList.add(document.toJson());
//                if(documentList.size() == 100000){
//                    UtilsMini.writeToTxt(path, StringUtils.join(documentList.toArray(), "\r\n"));
//                    documentList.clear();
//                }
            }
//            UtilsMini.writeToTxt(path, StringUtils.join(documentList.toArray(), "\r\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongoClient.close();
    }

    public static void main(String[] args) throws IOException {
        for(String collectionName:collections){
            if(collectionName.equals("answer")||collectionName.equals("kv_data")){
                processAnswer(String.format(PATH, collectionName), collectionName);
                break;
            }
            if(collectionName.equals("dialogue")||collectionName.equals("phrase")){
                processDialogue(String.format(PATH, collectionName), collectionName);
            }
        }
    }
}
