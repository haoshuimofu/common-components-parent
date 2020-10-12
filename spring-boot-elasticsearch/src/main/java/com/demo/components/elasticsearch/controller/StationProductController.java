package com.demo.components.elasticsearch.controller;

import com.demo.components.elasticsearch.JsonResult;
import com.demo.components.elasticsearch.Pagation;
import com.demo.components.elasticsearch.model.StationProduct;
import com.demo.components.elasticsearch.repositories.StationProductRepository;
import com.demo.components.elasticsearch.request.SearchOptions;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author wude
 * @date 2020/10/9 14:43
 */
@RestController
@RequestMapping("/station/product/")
public class StationProductController {

    @Autowired(required = false)
    private StationProductRepository stationProductRepository;

    @RequestMapping("count")
    public JsonResult<Long> countAll() throws IOException {
        CountRequest request = new CountRequest();
        request.query(QueryBuilders.matchAllQuery());
        return JsonResult.success(stationProductRepository.count(request));
    }

    @RequestMapping("search")
    public JsonResult<Pagation<StationProduct>> search(@RequestParam String keyword) throws Exception {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name_analyze", keyword))
                .should(QueryBuilders.matchQuery("search_tags_analyzer", keyword))
                .should(QueryBuilders.wildcardQuery("name", "*" + keyword + "*"));

        FunctionScoreQueryBuilder.FilterFunctionBuilder filterFunctionBuilder = new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("total_sales").lte(0)),
                ScoreFunctionBuilders.weightFactorFunction(0.01f));

        FunctionScoreQueryBuilder scoreQueryBuilder = new FunctionScoreQueryBuilder(queryBuilder, new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{filterFunctionBuilder});
        SearchOptions searchOptions = SearchOptions.instance().setFrom(5000).setSize(1000);
        return JsonResult.success(stationProductRepository.search(scoreQueryBuilder, searchOptions));
    }

}