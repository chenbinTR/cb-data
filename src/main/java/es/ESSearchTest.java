package es;

import com.alibaba.fastjson.JSONObject;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
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
public class ESSearchTest {

    private static final SearchRequestBuilder searchRequestBuilder = EsClient.getClient(EsAddress.ALPHA_ES).prepareSearch("nlp_poem")
            .setTypes("doc");

    @Test
    public void insertData() {
        JSONObject json = new JSONObject();
        json.put("poem_id", "abc");
        json.put("content", "关关雎鸠，在河之洲，窈窕淑女，君子好逑");
        IndexResponse response = EsClient.getClient(EsAddress.ALPHA_ES).prepareIndex("nlp_poem", "doc").setSource(json, XContentType.JSON).get();
    }

    @Test
    public void searchAll() {
        SearchResponse searchResponse = EsClient.getClient(EsAddress.ALPHA_ES).prepareSearch("nlp_poem")
                .setTypes("doc")
                .setQuery(QueryBuilders.matchAllQuery())
                .get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("content"));
        }
    }

    /**
     * 1、对查询条件内容进行分词
     * 2、可以指定一个或多个字段
     * 3、可以用or或and为条件
     * 4、进行每个分词匹配
     */
    @Test
    public void queryStringQuery() {
        SearchResponse searchResponse = searchRequestBuilder.setQuery(QueryBuilders.queryStringQuery("关关").field("content").defaultOperator(Operator.OR)).get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("content"));
            System.out.println(next.getScore());
        }
    }

    /**
     * termQuery("key", obj)
     * 1、不对查询条件obj分词
     * 2、完全匹配存储中分词的单个词
     * 例如：中/国/人 obj=中，则匹配到，obj=中国，则匹配不到
     *
     * termsQuery("key", obj1, obj2..)同上，obj之间是或的关系
     *
     */
    @Test
    public void termQuery() {
//        SearchResponse searchResponse = searchRequestBuilder.setQuery(QueryBuilders.termQuery("content", "")).get();
        SearchResponse searchResponse = searchRequestBuilder.setQuery(QueryBuilders.termsQuery("content", "关","大")).get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("content"));
            System.out.println(next.getScore());
        }
    }

    /**
     * 模糊查询，类似mysql like
     * 1、查询条件内容不进行分词
     * 2、? 表示单个字符
     * 3、* 表示多个字符
     * 4、content.keyword 写法，表示不对存储内容进行分词
     */
    @Test
    public void wildcardQuery() {
        SearchResponse searchResponse = searchRequestBuilder.setQuery(QueryBuilders.wildcardQuery("content.keyword", "*在河之洲*")).get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("content"));
            System.out.println(next.getScore());
        }
    }

    /**
     * 字段匹配查询
     * match query搜索的时候，首先会解析查询字符串，进行分词，然后查询，
     */
    @Test
    public void matchQuery() {
        MultiMatchQueryBuilder multiMatch = QueryBuilders.multiMatchQuery(QueryParser.escape("点！点！点！"));
        multiMatch.field("msg", 100).field("status");

        SearchResponse searchResponse = searchRequestBuilder.setQuery(multiMatch).get();
        SearchHits hits = searchResponse.getHits();
//        System.out.println(searchResponse.toString());
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("name"));
            System.out.println(next.getScore());
        }
    }


    /**
     * 相似查询（基于编辑距离）
     * 1、查询条件内容不分词
     * 2、
     */
    @Test
    public void searcfuzzy() {
        SearchResponse searchResponse = searchRequestBuilder.setQuery(QueryBuilders.fuzzyQuery("content", "关雎鸠关")).get();
        SearchHits hits = searchResponse.getHits();
        // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSourceAsMap().get("content"));
            System.out.println(next.getScore());
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
        SearchResponse searchResponse = EsClient.getClient(EsAddress.LOCAL).prepareSearch("test")
                .setTypes("book")
                .setQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.termQuery("interests", "小飞侠"))
                )
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
}
