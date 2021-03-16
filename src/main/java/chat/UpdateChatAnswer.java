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
import java.util.List;

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
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("answer");
        System.out.println(collection.count());
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", 0);
            jsonObject.put("content", "");

            JSONArray infos = new JSONArray();
            infos.add(jsonObject);
            //筛选条件
            Bson filter = Filters.eq("_id", new ObjectId("595db107c3666eca267f196b"));

            //需改内容
            Document document = new Document("$set", new Document("infos", infos));
            //执行修改
//            collection.updateOne(filter, document);
            collection.deleteOne(filter);
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
