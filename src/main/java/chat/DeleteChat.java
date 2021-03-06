package chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import utils.CbMongoClient;
import utils.UtilsMini;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class DeleteChat {
    private static void delete() throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("answer");
        System.out.println(collection.count());
        try {
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            Document document;
            while (mongoCursor.hasNext()) {
                document = mongoCursor.next();
                int qid = document.getInteger("qid");
                if(qid>4994039){
                    JSONObject jsonObject = JSON.parseObject(document.toJson());
                    JSONObject createAtJson = jsonObject.getJSONObject("create_at");
                    if(null == createAtJson){
                        System.err.println("error: "+jsonObject.toJSONString());
                    }else{
                        long createAt = createAtJson.getLong("$date");
                        if(createAt > 1594915200000L){
                            String objectId = jsonObject.getJSONObject("_id").getString("$oid");
                            Bson filter = Filters.eq("_id", new ObjectId(objectId));
                            collection.deleteOne(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongoClient.close();
    }

    public static void main(String[] args) throws IOException {
        delete();
    }
}
