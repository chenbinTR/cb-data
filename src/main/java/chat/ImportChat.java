package chat;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import utils.CbMongoClient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class ImportChat {
//    private static final String path = "/mnt/work/snd-faq/bat/classes/chat/emotion.txt";
    private static final String path = "C:\\Users\\cb\\Downloads\\emotion.txt";

    public static void readFileToList(String filePath) throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.ALPHA);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("kv_data");
        int count = 0;
        File file = new File(filePath);
        BufferedReader reader = null;
        if (file.exists()) {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF-8"));
            String temp;
            Document doc;
            List<Document> documents = new ArrayList<>();
            while (null != (temp = reader.readLine())) {
                try {
                    System.out.println(++count);
                    String origin = "{\"create_by\" : \"张清\", \"score\" : 100.0, \"_class\" : \"com.turing.chat.entity.KVDataEntity\", \"state\" : 1, \"type\" : \"chat_random_hint\", \"create_at\" : { \"$date\" : %d }, \"qid\" : 81, \"aid\" : 919624217, \"infos\" : [{ \"type\" : 0, \"content\" : \"%s\" }], \"tags\" : { \"sentence_form_score\" : \"0.9513517022132874\", \"sentence_form\" : \"陈述句\" } }";
                    origin = String.format(origin, System.currentTimeMillis(), temp.trim());
                    JSONObject jsonObject = JSONObject.parseObject(origin);
                    jsonObject.put("aid", temp.trim().hashCode());
                    System.out.println(jsonObject.toJSONString());
                    doc = Document.parse(jsonObject.toJSONString());
                    collection.insertOne(doc);
//                    documents.add(doc);
//
//                    if(documents.size() == 1000){
//                        collection.insertMany(documents);
//                        documents.clear();
//                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
//            collection.insertMany(documents);
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mongoClient.close();
    }

    public static void main(String[] args) throws IOException {
        readFileToList(path );
    }
}
