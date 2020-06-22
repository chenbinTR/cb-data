package es;

import com.alibaba.fastjson.JSONObject;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import java.util.Iterator;

/**
 * @author ChenOT
 * @date 2017-04-26
 * @see
 * @since
 */
public class ESSekectTest {
    @Test
    public void insertData() {
        JSONObject json = new JSONObject();
        json.put("userName","张三");
        json.put("sendDate", "2017-11-30");
        json.put("msg","我很好");
        json.put("status","我非常好");
        IndexResponse response = EsClient.getClient(EsAddress.ALPHA_ES).prepareIndex("nlp_book_search", "doc").setSource(json, XContentType.JSON).get();
    }

    @Test
    public void searchAll() {
        SearchResponse searchResponse = EsClient.getClient(EsAddress.ALPHA_ES).prepareSearch("nlp_chat")
                .setTypes("doc")
                .setQuery(QueryBuilders.matchAllQuery())
                .get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println("name : " + next.getSourceAsMap().get("question"));
        }
    }

    /**
     * 关键词查询
     * 全文检索，检索所有字段，分词，支持全部的Apache Lucene语法
     */
    @Test
    public void searcQueryString() {
        SearchResponse searchResponse = EsClient.getClient(EsAddress.ALPHA_ES).prepareSearch("nlp_book_search")
                .setTypes("doc")
                .setQuery(QueryBuilders.queryStringQuery("生活习惯好故事"))
                .get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("name"));
        }
    }

    /**
     * 通配符查询
     */
    @Test
    public void searcWildcardQuery() {
        SearchResponse searchResponse = EsClient.getClient(EsAddress.ALPHA_ES).prepareSearch("nlp_chat")
                .setTypes("doc")
                .setQuery(QueryBuilders.wildcardQuery("question", "*小学英语*"))
                .get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("question"));
        }
    }

    /**
     * 字段匹配查询
     * match query搜索的时候，首先会解析查询字符串，进行分词，然后查询，
     * 而term query,输入的查询内容是什么，就会按照什么去查询，并不会解析查询内容，对它分词。
     */
    @Test
    public void matchQuery() {
        MultiMatchQueryBuilder multiMatch = QueryBuilders.multiMatchQuery(QueryParser.escape("点！点！点！"));
        multiMatch.field("msg", 100).field("status");

        SearchResponse searchResponse = EsClient.getClient(EsAddress.ALPHA_ES).prepareSearch("nlp_book_search")
                .setTypes("doc")
                .setQuery(QueryBuilders.matchQuery("name", "生活习惯好故事"))
//                .setQuery(multiMatch)
                .get();
        SearchHits hits = searchResponse.getHits();
//        System.out.println(searchResponse.toString());
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("name"));
//            System.out.println(next.toString());
        }
    }

    /**
     * 词条查询（仅匹配在给定字段中含有该词条的文档，而且是确切的、未经分析的词条）
     * termQuery("key", obj) 完全匹配
     * termsQuery("key", obj1, obj2..)
     */
    @Test
    public void searcTermQuery() {
        SearchResponse searchResponse = EsClient.getClient(EsAddress.ALPHA_ES).prepareSearch("nlp_chat")
                .setTypes("doc")
//                .setQuery(QueryBuilders.termQuery("question", "小学英语"))
                .setQuery(QueryBuilders.termsQuery("question", "小学", "英语"))
                .get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("question"));
        }
    }

    /**
     * 相似度查询（它基于编辑距离算法来匹配文档）
     */
    @Test
    public void searcfuzzy() {
        SearchResponse searchResponse = EsClient.getClient(EsAddress.ALPHA_ES).prepareSearch("nlp_chat")
                .setTypes("doc")
                .setQuery(QueryBuilders.fuzzyQuery("question", "小学英语"))
                .get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("question"));
        }
    }

    /**
     * 复杂查询
     * must(QueryBuilders) : AND
     * mustNot(QueryBuilders): NOT
     * should(QueryBuilders):OR
     */
    @Test
    public void boolQuery() {
        SearchResponse searchResponse = EsClient.getClient(EsAddress.ALPHA_ES).prepareSearch("nlp_chat")
                .setTypes("doc")
                .setQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery("question", "小学英语"))
                        .should(QueryBuilders.matchQuery("question", "和珅和大人"))
                )
                .get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("question"));
        }
    }
}
