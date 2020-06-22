package chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class UpdateTags {
    public static void exportDb() throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.DEV);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("answer");
        System.out.println(collection.count());
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        Document document;
        List<String> documentList  = new ArrayList<>();
        int count = 0;
        while (mongoCursor.hasNext()) {
            document =  mongoCursor.next();
            JSONObject tags = JSON.parseObject(document.toJson()).getJSONObject("tags");
            ObjectId objectId = document.getObjectId("_id");
            if(tags == null){
                tags = new JSONObject();
                tags.put("emotion",10301);
            }else{
                tags.put("emotion",10301);
            }

            //筛选条件
            Bson filter = Filters.eq("_id",objectId);
            //需改内容
            Document documentNew = new Document("$set", new Document("tags", tags));
            collection.updateOne(filter, documentNew);
        }
//        for (String item : list) {
//            try {
//                JSONObject jsonObject = JSONObject.parseObject(item);
//                int qid = jsonObject.getInteger("qid");
//                int aid = jsonObject.getInteger("aid");
//
//                String type = jsonObject.getString("type");
                //筛选条件
//                Bson filter = Filters.eq("_id", new ObjectId(item));
                //需改内容
//                Document document = new Document("$set", new Document("state", 0));
                //执行修改
//                collection.updateOne(filter, document);
//                BasicDBObject query = new BasicDBObject();
//                query.put("type", type);
//                query.put("qid", qid);
//                query.put("aid", aid);
//                FindIterable<Document> findIterable = collection.find(query);
//                MongoCursor<Document> mongoCursor = findIterable.iterator();
//                Document document;
//                String answer="";
//                while (mongoCursor.hasNext()) {
//                    document = mongoCursor.next();
//                    JSONObject jsonObjectTemp = JSONObject.parseObject(document.toJson());
//                    String id = jsonObjectTemp.getJSONObject("_id").getString("$oid");
//                    System.out.println(id);
//                    UtilsMini.writeToTxt(path+"obj_id.txt", id);
//                }
//            } catch (Exception e1) {
//                e1.printStackTrace();
//                UtilsMini.writeToTxt(path+"answer_delete_error.txt", item);
//            }
//        }
        mongoClient.close();
    }

    public static void main(String[] args) throws IOException {
        exportDb();
    }
}
