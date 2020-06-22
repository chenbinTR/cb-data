package chat;

import com.alibaba.fastjson.JSONArray;
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
public class ImportKvData {
    private static final String path = "/home/developer/bat/classes/chat/";
//    private static final String path = "C:\\Users\\cb\\Downloads\\";

    public static void readFileToList() throws IOException {
        List<String> contents = UtilsMini.readFileToList(path+"random.txt");
        JSONObject jsonObject = JSONObject.parseObject("{\"create_by\" : \"贾志强\",\"score\" : 100.0,\"_class\" : \"com.turing.chat.entity.KVDataEntity\",\"state\" : 1,\"type\" : \"chat_random_hint\",\"create_at\" : 123456789,\"qid\" : 81,\"aid\" : -1278278615,\"infos\" : [ {\"type\" : 0,\"content\" : \"别人笑我太疯癫，我笑别人看不穿。\"}],\"tags\" : {\"sentence_form_score\" : \"0.9513517022132874\",\"sentence_form\" : \"陈述句\"}}");
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("kv_data");
        for(String content:contents){
            String[] strs = content.split("\t");
            jsonObject.put("qid", Integer.valueOf(strs[0]));

//            JSONObject tags = jsonObject.getJSONObject("tags");
//            tags.put("channel", Integer.valueOf(strs[2]));
//            jsonObject.put("tags", tags);

            JSONObject date = new JSONObject();
            date.put("$date", System.currentTimeMillis());
            jsonObject.put("create_at",date);

            jsonObject.put("aid", strs[1].hashCode());

            JSONArray ja = new JSONArray();
            JSONObject temp = new JSONObject();
            temp.put("type",0);
            temp.put("content", strs[1]);
            ja.add(temp);
            jsonObject.put("infos", ja);

            Document doc = Document.parse(jsonObject.toJSONString());
            System.out.println(jsonObject.toJSONString());
            collection.insertOne(doc);
        }

        mongoClient.close();
    }

    public static void main(String[] args) throws IOException {
        readFileToList();
    }
}
