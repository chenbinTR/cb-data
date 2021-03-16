package chat;

import es.EsAddress;
import es.EsClient;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.python.apache.xerces.dom.PSVIAttrNSImpl;
import utils.UtilsMini;

import java.io.IOException;
import java.util.List;

/**
 * @author ChenOT
 * @date 2020-07-17
 * @see
 * @since
 */
public class ChatEs {
    public static void saveEs(EsAddress esAddress, String indexName, String filePath) throws IOException {
        TransportClient client = EsClient.getClient(esAddress);
        List<String> contents = UtilsMini.readFileToList(filePath);
        for (String content : contents) {
            try {
                String[] items = content.split("\t");
                int qid = Integer.valueOf(items[0]);
                String question = items[1];
                XContentBuilder doc = XContentFactory.jsonBuilder()
                        .startObject()
                        .field("question", question)
                        .field("table_name", "chat_question")
                        .field("id", qid)
                        .endObject();
                client.prepareIndex(indexName, "doc", null).setSource(doc).execute();
                System.out.println(content);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(content);
            }
        }
        System.out.println("saveEs success");
        client.close();
    }

    /**
     * 导出es数据
     *
     * @param esAddress
     * @param indexName
     * @throws IOException
     */
    public static void exportEs(EsAddress esAddress, String indexName) {
        TransportClient client = EsClient.getClient(esAddress);
        //指定一个index和type
        SearchRequestBuilder search = client.prepareSearch(indexName).setTypes("doc");
        //使用原生排序优化性能
//        search.addSort("_doc", SortOrder.ASC);
        //设置每批读取的数据量,此处为测试环境为了测试批次设置值较小，生产可根据实际情况调参
        search.setSize(1000);
        //默认是查询所有
//        search.setQuery(QueryBuilders.queryStringQuery("*:*"));
        //设置 search context 维护1分钟的有效期
        search.setScroll(TimeValue.timeValueMinutes(60));

        //获得首次的查询结果
        SearchResponse scrollResp = search.get();
        //打印命中数量
        System.out.println("命中总数量：" + scrollResp.getHits().getTotalHits());
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //System.out.println(hit.getSourceAsString());
                //获取ES数据
                String esData = hit.getSourceAsString();
                UtilsMini.writeToTxt("E:\\logs\\es_prod.txt", esData);
            }
            //将scorllId循环传递
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(TimeValue.timeValueMinutes(60)).execute().actionGet();
            //当searchHits的数组为空的时候结束循环，至此数据全部读取完毕
        } while (scrollResp.getHits().getHits().length != 0);
        client.close();
    }
//
//    private static void deleteEs(EsAddress esAddress) throws IOException {
//        TransportClient client = EsClient.getClient(esAddress);
//        List<String> contents = UtilsMini.readFileToList(path + "delete.txt");
//        for (String content : contents) {
//            try{
//                String[] items = content.split("\t");
//                int qid = Integer.valueOf(items[0]);
//                BulkByScrollResponse response =
//                        DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
//                                .filter(QueryBuilders.matchQuery("id", qid))
//                                .source("nlp_chat_19-04-03")
//                                .get();
//
//                long deleted = response.getDeleted();
//                System.out.println("总共删除时间："+deleted);
//            }catch (Exception e){
//                e.printStackTrace();
//                System.err.println(content);
//            }
//        }
//        System.out.println("saveEs success");
//        client.close();
//    }

    public static void main(String[] args) {
//        try {
//            saveEs(EsAddress.PROD_ES_1, "nlp_chat_19-03-12", "E:\\data_qid.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        exportEs(EsAddress.ALPHA_ES, "nlp_attr");
    }
}
