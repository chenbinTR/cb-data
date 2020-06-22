package chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
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
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class SelectChat {
    private static final String path = "/home/developer/bat/";
    public static void exportDb() throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("dialogue");
        System.out.println(collection.count());

        List<String> lines = UtilsMini.readFileToList(path+"answer_sensitive_20200121.txt") ;
        for (String line : lines) {
            String question = "";
            try{
                String[] items = line.split("\t");
                String type = items[1];
                String qid = items[2];
                if ("dialogue".equals(type)) {
                    BasicDBObject query = new BasicDBObject();
                    // 检索条件
                    query.put("qid", Integer.valueOf(qid));
                    FindIterable<Document> findIterable = collection.find(query);
                    MongoCursor<Document> mongoCursor = findIterable.iterator();
                    Document document;
                    if (mongoCursor.hasNext()) {
                        document = mongoCursor.next();
                        JSONObject jsonObject = JSON.parseObject(document.toJson());
                        question = jsonObject.getString("question");
                    }
                    UtilsMini.writeToTxt(path+"t_dialogue.txt", question+"\t"+line);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        mongoClient.close();
    }

    public static void main(String[] args) throws IOException {
        exportDb();
    }
}
