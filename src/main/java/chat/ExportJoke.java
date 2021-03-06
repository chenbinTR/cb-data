package chat;

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
 * 导出所有聊天数据 mongo
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class ExportJoke {
    private static String file = "/home/developer/bat/chat/%s"+"_"+ LocalDate.now()+".txt";
    private static final String[] collections = {
            "joke"
    };
    public static void main(String[] args) throws IOException {
        for(String collectionName:collections){
            exportDb(collectionName);
        }
    }
    private static void exportDb(String collectionName) throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PLATFORM_PROD);
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
            while (mongoCursor.hasNext()) {
                document = mongoCursor.next();
                documentList.add(document.toJson());
                if(documentList.size() == 10000){
                    UtilsMini.writeToTxt(path, StringUtils.join(documentList.toArray(), "\r\n"));
                    documentList.clear();
                }
            }
            UtilsMini.writeToTxt(path, StringUtils.join(documentList.toArray(), "\r\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongoClient.close();
    }


}
