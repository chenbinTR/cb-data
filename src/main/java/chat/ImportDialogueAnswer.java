package chat;

import chat.bean.AnswerEntity;
import chat.bean.DialogueEntity;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import utils.CbMongoClient;
import utils.Utils;
import utils.UtilsMini;

import java.io.IOException;
import java.util.List;

/**
 * 文件格式-单行：qid\t问题\t答案1|答案2|答案3
 *
 * @author ChenOT
 * @date 2019-04-18
 * @see
 */
public class ImportDialogueAnswer {
    /**
     * 数据路径
     */
//    private static final String rootPath = "/home/developer/bat/";
    private static final String rootPath = "E:\\";
    private static final String path = rootPath + "data.txt";
    private static final String path_qid = rootPath + "data_qid.txt";
    /**
     * channel:0  通用语料
     * channel:1  语料只适用于儿童，优先选取
     * channel:-1 语料不适用于儿童
     */
    private static final int channel = 0;
    /**
     * 起始qid
     */
    private static final int start_qid = 5001187;

    /**
     * 补全qid字段（添加es数据要用）
     */
    private static void createQid() {
        int qid = start_qid;
        List<String> contents = UtilsMini.readFileToList(path);
        for (String content : contents) {
            Utils.writeToTxt(path_qid, qid + "", content);
            qid++;
        }
    }

    /**
     * 插入单条dialogue
     *
     * @throws IOException
     */
    private static void createDialogueAnswer() throws IOException {
        List<String> contents = UtilsMini.readFileToList(path_qid);
        for (String content : contents) {
            String[] items = content.split("\t");
            int qid = Integer.valueOf(items[0]);
            String question = items[1].trim();
            String[] answers = items[2].trim().split("\\|");
            // 保存dialogue
            insertDialogue(qid, question);
            // 保存answer
            insertAnswers(qid, answers);
        }
    }

    /**
     * 插入单条dialogue
     *
     * @throws IOException
     */
    private static void insertDialogue(int qid, String question) throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("dialogue");
        try {
            Document doc = Document.parse(JSONObject.toJSONString(new DialogueEntity(qid, question)));
            collection.insertOne(doc);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        System.out.println("success: " + question);
        mongoClient.close();
    }

    /**
     * 插入answers
     *
     * @throws IOException
     */
    private static void insertAnswers(int qid, String[] answers) throws IOException {
        MongoClient mongoClient = CbMongoClient.getMongoClientInstance(CbMongoClient.MongoServer.PROD);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("nlp_chat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("answer");
        for (String content : answers) {
            try {
                Document doc = Document.parse(JSONObject.toJSONString(new AnswerEntity(qid, content, channel)));
                collection.insertOne(doc);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("success-answer: " + answers[0]);
        mongoClient.close();
    }


    public static void main(String[] args) throws IOException {
        // 先补全qid列
        createQid();
//        createDialogueAnswer();
//        insertOneAnswer();
//        ChatEs.saveEs(EsAddress.PROD_ES_1, "nlp_chat_19-03-12", path + "data_new.txt");
//        saveEs(EsAddress.PROD_ES_2, "nlp_chat_19-03-12");
//        deleteEs(EsAddress.PROD_ES_2);
    }
}
