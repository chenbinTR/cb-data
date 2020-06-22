package platform;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class UpdateStackPackage {
    private static String path = "Q:\\";
    private static String apiKyes = "dc2c3d0a888e4834bcc8437fd71ac6bf|0b8d57741b804a0bb6c9dbe300c3efd7";
//    private static String path = "/mnt/work/snd-faq/bat/classes/chat/";
    public static void exportDb() throws IOException {
//        List<String> list = UtilsMini.readFileToList(path+"answer_turingshield_20190816.txt");
        String[] keys = apiKyes.split("\\|");
        Set<String> apiKeySet = new HashSet<>(Arrays.asList(keys));
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PLATFORM_PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("turing_platform");
        MongoCollection<Document> collection = mongoDatabase.getCollection("stackPackage");
        System.out.println(collection.count());
        for (String item : apiKeySet) {
            try {
                //筛选条件
//                Bson filter = Filters.eq("_id", new ObjectId(item));
//                Bson filter = Filters.eq("apikey", item);
                //需改内容
//                Document document = new Document("$set", new Document("state", 0));
                //执行修改
//                collection.updateOne(filter, document);
                BasicDBObject query = new BasicDBObject();
                query.put("apikey", item);
                FindIterable<Document> findIterable = collection.find(query);
                MongoCursor<Document> mongoCursor = findIterable.iterator();
                Document document;
                int count = 0;
                while (mongoCursor.hasNext()) {
                    count++;
                    document = mongoCursor.next();
                    UtilsMini.writeToTxt("/home/developer/bat/stackPackage_20200304.txt", document.toJson());
                    ObjectId objectId = document.getObjectId("_id");
                    JSONObject jsonObjectTemp = JSONObject.parseObject(document.toJson());
                    int capacity = jsonObjectTemp.getInteger("capacity")+50000;
                    Bson filter = Filters.eq("_id", objectId);
                    Document documentTemp = new Document("$set", new Document("capacity",capacity));
                    collection.updateOne(filter, documentTemp);
                }
                if(count == 0){
                    System.out.println(item);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        mongoClient.close();
    }

    public static void main(String[] args) throws IOException {
        exportDb();
    }
}
