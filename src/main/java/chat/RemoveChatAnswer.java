package chat;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
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
public class RemoveChatAnswer {
    private static String path = "/home/developer/bat/";
    public static void exportDb() throws IOException {
//        List<String> list = UtilsMini.readFileToList(path+"id.txt");
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("answer");
        System.out.println(collection.count());
        int count = 0;
//        for (String item : list) {
            try {
//                JSONObject jsonObject = JSONObject.parseObject(item);
//                int qid = jsonObject.getInteger("qid");
//                int aid = jsonObject.getInteger("aid");
//
//                String type = jsonObject.getString("type");
                //筛选条件
                Bson filter = Filters.eq("_id", new ObjectId("59e850e87b199a0aa9affc69"));

                //需改内容
                Document document = new Document("$set", new Document("state", 0));
                //执行修改
//                System.out.println(collection.updateOne(filter, document));
                System.out.println(collection.deleteOne(filter));
            } catch (Exception e1) {
                e1.printStackTrace();
//                UtilsMini.writeToTxt(path+"answer_delete_error.txt", item);
            }
//        }
        mongoClient.close();
    }

    public static void main(String[] args) throws IOException {
        exportDb();
    }
}
