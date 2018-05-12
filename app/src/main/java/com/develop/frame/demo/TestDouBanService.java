package com.develop.frame.demo;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by yangjh on 2018/4/26.
 */

public interface TestDouBanService {

//    @GET("discover/articles")
//    Observable<BaseResponse<List<DiscoverArticle>>> getDiscoverArticles(@QueryMap Map<String, Object> options);

    /*  http://52.221.216.250:8080/v2/articles?start=1521185093&offset=0&address=0x437e62D4f2Cc3B20eD3e2B2A5F535A7Fe1D18E3A*/
    @GET("book/1220562")
    Observable<String> getFollowingArticles();



}
