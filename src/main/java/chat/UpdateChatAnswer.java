package chat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import utils.CbMongoClient;
import utils.UtilsMini;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class UpdateChatAnswer {
    private static String path = "/home/developer/bat/";

    //    private static String path = "Q:\\";

    public static void exportDb() throws IOException {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("595dacf3c3666eca2675befd", "阿杜唱歌还是很好听的。");
        dataMap.put("595dae4dc3666eca2678c50b", "阿杜唱过很多好听的歌。");
        dataMap.put("595daeb0c3666eca2679991f", "阿杜唱歌很有特点。");
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("answer");
        try {
            for (Map.Entry<String, String> dataEntry : dataMap.entrySet()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", 0);
                jsonObject.put("content", dataEntry.getValue());
                JSONArray infos = new JSONArray();
                infos.add(jsonObject);
                //筛选条件
                Bson filter = Filters.eq("_id", new ObjectId(dataEntry.getKey()));
                //需改内容
                Document document = new Document("$set", new Document("infos", infos));
                //执行修改
                collection.updateOne(filter, document);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }

    public static void main(String[] args) throws IOException {
        exportDb();
    }
}
