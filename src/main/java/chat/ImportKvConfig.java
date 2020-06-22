package chat;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
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
public class ImportKvConfig {
    private static final String path = "/mnt/work/snd-faq/bat/classes/chat/";
//    private static final String path = "C:\\Users\\cb\\Downloads\\";

    public static void readFileToList() throws IOException {
        List<String> contents = UtilsMini.readFileToList(path+"mingan-0.txt");
        JSONObject jsonObject = JSONObject.parseObject("{\"_class\" : \"com.turing.chat.entity.KVConfigEntity\", \"qid\" : 1031, \"type\" : \"name\", \"code\" : \"302\", \"question\" : \"name\", \"description\" : \"询问未知名字\", \"create_at\" : { \"$date\" : 1559273552247 }, \"batch_num\" : \"201906040101\", \"state\" : 1 }");
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("kv_config");
        int qid = 1031;
        for(String content:contents){
            jsonObject.put("qid", qid);
            jsonObject.put("code", content.split("\t")[1]);
            jsonObject.put("description", content.split("\t")[3]);

            JSONObject date = new JSONObject();
            date.put("$date", System.currentTimeMillis());
            jsonObject.put("create_at",date);


            Document doc = Document.parse(jsonObject.toJSONString());
            System.out.println(jsonObject.toJSONString());
            collection.insertOne(doc);
            qid++;
        }

        mongoClient.close();
    }

    public static void main(String[] args) throws IOException {
        readFileToList();
    }
}
