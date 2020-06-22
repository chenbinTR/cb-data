package es;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-08-01
 * @see
 * @since
 */
public class EsTest {
    public static void main(String[] args) {
        String question = "你就是个神经病";

        TimeValue clientTimeout = TimeValue.timeValueMillis(700L);
        TimeValue serverTimeout = TimeValue.timeValueMillis(300L);

        String[] searchFields = new String[]{"question^4", "question_preprocessed^5"};
        MultiMatchQueryBuilder multiMatch = QueryBuilders.multiMatchQuery(QueryParser.escape(question))
                .type("best_fields")
                .tieBreaker(0.0f)
                .minimumShouldMatch("60%");
        for (String searchField : searchFields) {
            String[] fs = searchField.split("\\^");
            if (fs.length == 1){
                multiMatch.field(fs[0]);
            }else {
                multiMatch.field(fs[0], Float.valueOf(fs[1]));
            }
        }
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(multiMatch);
        String[] tableNames = {"chat_question"};
        //过滤表
        if (tableNames != null && tableNames.length > 0) {
            BoolQueryBuilder tableNamesQueryBuild = QueryBuilders.boolQuery();
            for (String tableName : tableNames){
                QueryBuilder termBuilder = QueryBuilders.termQuery("table_name", tableName);
                tableNamesQueryBuild.should(termBuilder);
            }
            boolBuilder.must(tableNamesQueryBuild);
        }

        // 索引
        Indices indices = Indices.getIndicesByType(RequestType.CHAT);
        // 创建client
        SearchResponse response = getClient(RequestType.CHAT).prepareSearch(indices.getIndex())
//                .setRouting(userAccount)
                .setTimeout(serverTimeout)
                .setQuery(boolBuilder)
                .setFrom(0)
                .setSize(100)
                .get(clientTimeout);

        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }
    private static Client getClient(final RequestType type) {
        if (type == null) {
            return null;
        }
        TransportClient client = null;
        List<EsAddress> addresses = new ArrayList<>();
//        addresses.add(new EsAddress("47.94.57.146", 9301));
//        addresses.add(new Address("101.201.140.59", 9301));
//        addresses.add(new Address("101.201.143.57", 9301));
//        addresses.add(new Address("101.201.145.52", 9301));
//        addresses.add(new Address("118.178.94.127", 9301));
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .build();
        client = new PreBuiltTransportClient(settings);
        for (EsAddress address : addresses) {
            TransportAddress transportAddress = null;
            try {
                transportAddress = new TransportAddress(
                        InetAddress.getByName(address.getHost()), address.getPort());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            client.addTransportAddress(transportAddress);
        }
        return client;
    }
}
