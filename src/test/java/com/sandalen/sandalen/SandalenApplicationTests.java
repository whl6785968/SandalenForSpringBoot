package com.sandalen.sandalen;

import
        com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sandalen.sandalen.dao.Article;
import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.core.search.aggregation.Aggregation;
import io.searchbox.core.search.aggregation.AggregationField;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.aliases.AddAliasMapping;
import io.searchbox.indices.aliases.AliasExists;
import io.searchbox.indices.aliases.GetAliases;
import io.searchbox.indices.aliases.ModifyAliases;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SandalenApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Autowired
    JestClient jestClient;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testSg(){
        stringRedisTemplate.opsForValue().set("1","hello");
    }

    @Test
    public void tstRmq(){

        Map map = new HashMap<String,Object >();
        map.put("msg","hello");
        String[] array = {"a","b"};
        map.put("array",array);
        //Message需要自己构造一个，定义消息体内容和消息头
        //rabbitTemplate.send(exchange,routeKey,message)

        //rabbitTemplate.convertAndSend(exchange,routeKey,message)
        //Object 默认当成小西天，只需要传入要发送的对象，自动序列化给rabbitmq
        rabbitTemplate.convertAndSend("exchange.direct","atguigu.emps",map);
    }

    @Test
    public void receive(){
        Object atguigu = rabbitTemplate.receiveAndConvert("atguigu");

//        System.out.println(atguigu);
    }

    @Test
    public void tstEmail(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setSubject("帅哥，约吗");
        simpleMailMessage.setText("好玩的哦");

        simpleMailMessage.setTo("437918310@qq.com");
        simpleMailMessage.setFrom("806403789@qq.com");

        javaMailSender.send(simpleMailMessage);
    }

    @Test
    public void tstAmqAdmin(){
        amqpAdmin.declareExchange(new DirectExchange("amqAdmin"));
        amqpAdmin.declareQueue(new Queue("amqQue",true));
        amqpAdmin.declareBinding(new Binding("amqQue", Binding.DestinationType.QUEUE,"amqAdmin","amqAdminBinding",null));
    }

    @Test
    public void tstJest(){
        Article article = new Article();
        article.setId(4);
        article.setAuthor("胡彦斌");
        article.setTitle("月光");
        article.setContent("lajies");

        Index index = new Index.Builder(article).index("articles").type("emotion").build();

        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tstJestSearch(){

        Map query = new HashMap<String,Map>();
        Map match = new HashMap<String,Map>();
        Map matchItem = new HashMap<String,Map>();

        match.put("match_all",matchItem);
        query.put("query",match);

       /* String s = JSON.toJSONString(query);
        System.out.println(s);*/

       //查询全部
        String json = "{\"query\":{\"match_all\":{}}}";
        //匹配查询
        String json2 = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"last_name\" : \"Smith\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        //复杂查询
        String json3 = "{\n" +
                "    \"query\" : {\n" +
                "        \"bool\": {\n" +
                "            \"must\": {\n" +
                "                \"match\" : {\n" +
                "                    \"last_name\" : \"smith\" \n" +
                "                }\n" +
                "            },\n" +
                "            \"filter\": {\n" +
                "                \"range\" : {\n" +
                "                    \"age\" : { \"gt\" : 20 } \n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        String json4 = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"about\" : \"rock climbing\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        //短语搜索
        String json5 = "{\n" +
                "    \"query\" : {\n" +
                "        \"match_phrase\" : {\n" +
                "            \"about\" : \"rock climbing\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Search search = new Search.Builder("").addType("emp").addIndex("group").build();

        try {
            SearchResult result = jestClient.execute(search);
//            System.out.println(result.getJsonString());

            List<SearchResult.Hit<JsonObject, Void>> hits = result.getHits(JsonObject.class);
            for(SearchResult.Hit<JsonObject, Void> hit: hits){
                JsonObject source = hit.source;
                System.out.println("source = " + source);
                JsonPrimitive title = source.getAsJsonPrimitive("title");
                JsonPrimitive content = source.getAsJsonPrimitive("content");
                System.out.println("title = " + title);
                System.out.println(" content = "+content);


            }
            } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void tstGet(){
        Get group = new Get.Builder("group", "1").build();
        Gson gson = new Gson();
        System.out.println("group = " + group.getData(gson));
        try {
            DocumentResult result = jestClient.execute(group);
            String jsonString = result.getJsonString();
            System.out.println("jsonString = " + jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TstAlias(){
        ArrayList<String> objects = new ArrayList<>();
        objects.add("group");
        AddAliasMapping addAliasMapping = new AddAliasMapping.Builder(objects, "groups").build();
        try {
            JestResult execute = jestClient.execute(new ModifyAliases.Builder(addAliasMapping).build());
            String jsonString = execute.getJsonString();
            System.out.println("jsonString = " + jsonString);
        } catch (IOException e) {


        }

    }

    @Test
    public void tstGetAlias() throws IOException {
        String alias = "groups";
        GetAliases getAliases = new GetAliases.Builder().addIndex(alias).build();
        JestResult result = jestClient.execute(getAliases);
        System.out.println("result.getJsonString() = " + result.getJsonString());

    }

    @Test
    public void tstIndexExist() throws IOException {
        String index = "group";
        IndicesExists indicesExists = new IndicesExists.Builder(index).build();
        JestResult result = jestClient.execute(indicesExists);
        System.out.println("result.getJsonString() = " + result.isSucceeded());
    }

    @Test
    public void tstDoc(){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("_id",1));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(2);
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("group").addType("emp").build();

        try {
            SearchResult result = jestClient.execute(search);
            List<SearchResult.Hit<JsonObject, Void>> hits = result.getHits(JsonObject.class);
            List<String> sourceAsStringList = result.getSourceAsStringList();
            System.out.println("sourceAsStringList = " + sourceAsStringList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tstGetMapping(){
        GetMapping getMapping = new GetMapping.Builder().build();
        try {
            JestResult result = jestClient.execute(getMapping);
            System.out.println("result.getJsonString() = " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tstMakePhraseAndHighLight() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("about","rock climbing"));
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("about");
        searchSourceBuilder.highlighter(highlightBuilder);
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("group").addType("emp").build();

        SearchResult result = jestClient.execute(search);
        List<SearchResult.Hit<JsonObject, Void>> hitList = result.getHits(JsonObject.class);
        for (SearchResult.Hit<JsonObject, Void> hit: hitList) {
            JsonObject source = hit.source;
            System.out.println("source.getAsString() = " + source.toString());
            Map<String, List<String>> highlight = hit.highlight;
            Set<String> keySet = highlight.keySet();
            for(String key : keySet){
                System.out.println("key = " + key);
                List<String> list = highlight.get(key);
                for(String l : list){
                    System.out.println("l = " + l);
                }
            }
        }
        System.out.println(result.getHits(JsonObject.class).toString());


    }

    @Test
    public void tstBoolSearch() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //创建复杂查询字符串连接
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //创建match query
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("last_name", "smith");
        //must
        boolQueryBuilder.must(matchQueryBuilder);

        //filter部分
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
        rangeQueryBuilder.gt("20");
        boolQueryBuilder.filter(rangeQueryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("group").addType("emp").build();

        SearchResult searchResult = jestClient.execute(search);

        System.out.println("searchResult.getSourceAsStringList() = " + searchResult.getSourceAsStringList());
    }

    @Test
    public void tstAggQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder interests = AggregationBuilders.terms("all_interests").field("interests.keyword");
        searchSourceBuilder.aggregation(interests);
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("group").addType("emp").build();

        SearchResult result = jestClient.execute(search);

        System.out.println("result.toString() = " + result.toString());
    }

    @Test
    public void tstAnalyzer() throws Exception{
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        DisMaxQueryBuilder disMaxQueryBuilder = strutureQuery("HUYANBIN");
        searchSourceBuilder.query(disMaxQueryBuilder);
        Search search = new Search.Builder(searchSourceBuilder.toString()).build();
        SearchResult result = jestClient.execute(search);
        System.out.println("result.getSourceAsStringList() = " + result.getSourceAsStringList());
    }

    public static DisMaxQueryBuilder strutureQuery(String content){
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        MatchQueryBuilder name = QueryBuilders.matchQuery("author",content).boost(2f);
        MatchQueryBuilder query = QueryBuilders.matchQuery("author.pinyin",content);

        disMaxQueryBuilder.add(name);
        disMaxQueryBuilder.add(query);

        return disMaxQueryBuilder;

    }

    @Test
    public void tstJson() throws IOException {
        File file = ResourceUtils.getFile("classpath:json/emotion-mapping.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        String s = new String(bytes, 0, bytes.length);

        JSONObject jsonObject = (JSONObject) JSON.parse(s);

        System.out.println(jsonObject);

        File file1 = new File("/json/emotion-mapping.json");
        System.out.println(file1.exists());

        PutMapping build = new PutMapping.Builder("articles","_doc", jsonObject).build();

        JestResult execute = jestClient.execute(build);

        if(!execute.isSucceeded()){
            System.out.println(execute.getErrorMessage());
        }
    }

    @Test
    public void getFile() throws IOException {
        File file = new File("timg.jpg");
        file.createNewFile();

    }
}
