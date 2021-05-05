package com.zcx.pic

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Description:
 * @Author: zcx
 * @Copyright: 浙江集商优选电子商务有限公司
 * @CreateDate: 2020/8/21
 */
interface UnsplashApi {
    companion object {
        const val BASE_URL = "https://source.unsplash.com"
    }

    interface Collections {
        /**
         * Get a single page from the list of all collections.
         *
         * @page    Page number to retrieve. (Optional; default: 1)
         * @per_page    Number of items per page. (Optional; default: 10)
         */
        @GET("/collections")
        fun collections(@Query("page") page: Int = 1, @Query("per_page") per_page: Int = 10)

        /**
         * Get a single page from the list of featured collections.
         *
         * @page    Page number to retrieve. (Optional; default: 1)
         * @per_page    Number of items per page. (Optional; default: 10)
         */
        @GET("/collections/featured")
        fun featuredCollections(@Query("page") page: Int = 1, @Query("per_page") per_page: Int = 10)

        /**
         * Retrieve a single collection. To view a user’s private collections, the read_collections scope is required.
         *
         * @id    The collections’s ID. Required.
         */
        @GET("/collections/:id")
        fun collectionById(@Query("id") id: Int)

        /**
         * Retrieve a collection’s photos.
         *
         * @id    The collections’s ID. Required.
         * @page    Page number to retrieve. (Optional; default: 1)
         * @per_page    Number of items per page. (Optional; default: 10)
         * @orientation    Filter by photo orientation. Optional. (Valid values: landscape, portrait, squarish)
         */
        @GET("/collections/:id/photos")
        fun photoOfCollection(
            @Query("id") id: Int,
            @Query("page") page: Int = 1,
            @Query("per_page") per_page: Int = 10,
            @Query("orientation") orientation: String = "",
        )

        /**
         * Retrieve a list of collections related to this one.
         *
         * @id    The collections’s ID. Required.
         */
        @GET("/collections/:id/related")
        fun relatedCollection(@Query("id") id: Int)
    }

    interface Search {
        /**
         * Get a single page of photo results for a query.
         *
         * @query    Search terms.
         * @page    Page number to retrieve. (Optional; default: 1)
         * @per_page    Number of items per page. (Optional; default: 10)
         * @order_by    How to sort the photos. (Optional; default: relevant). Valid values are latest and relevant.
         * @collections    Collection ID(‘s) to narrow search. Optional. If multiple, comma-separated.
         * @content_filter    Limit results by content safety. (Optional; default: low). Valid values are low and high.
         * @color    Filter results by color. Optional. Valid values are: black_and_white, black, white, yellow, orange, red, purple, magenta, green, teal, and blue.
         * @orientation    Filter by photo orientation. Optional. (Valid values: landscape, portrait, squarish)
         */
        @GET("/search/photos")
        fun searchPhoto(
            @Query("query") query: String,
            @Query("page") page: Int = 1,
            @Query("per_page") per_page: Int = 10,
            @Query("order_by") order_by: String = "relevant",
            @Query("collections") collectionIds: IntArray? = null,
            @Query("content_filter") content_filter: String = "low",
            @Query("color") color: String = "",
            @Query("orientation") orientation: String = "",
        )

        /**
         * Get a single page of collection results for a query.
         *
         * @query   Search terms.
         * @page    Page number to retrieve. (Optional; default: 1)
         * @per_page    Number of items per page. (Optional; default: 10)
         */
        @GET("/search/collections")
        fun collections(
            @Query("query") query: String,
            @Query("page") page: Int = 1,
            @Query("per_page") per_page: Int = 10,
        )
    }

    interface Photos {
        /**
         * Get a single page from the list of all photos.
         *
         * @page    Page number to retrieve. (Optional; default: 1)
         * @per_page    Number of items per page. (Optional; default: 10)
         * @order_by    How to sort the photos. Optional. (Valid values: latest, oldest, popular; default: latest)
         */
        @GET("/photos")
        fun photos(
            @Query("page") page: Int = 1,
            @Query("per_page") per_page: Int = 10,
            @Query("order_by") order_by: String = "latest",
        )

        /**
         * Retrieve a single photo.
         *
         * @id    The photo’s ID. Required.
         */
        @GET("/photos/:id")
        fun photoById(@Query("id") id: Int)

        /**
         * Retrieve a single random photo, given optional filters.
         *
         * @query    Limit selection to photos matching a search term.
         * @collections    Collection ID(‘s) to narrow search. Optional. If multiple, comma-separated.
         * @content_filter    Limit results by content safety. (Optional; default: low). Valid values are low and high.
         * @orientation    Filter by photo orientation. Optional. (Valid values: landscape, portrait, squarish)
         * count	The number of photos to return. (Default: 1; max: 30)
         */
        @GET("/photos/random")
        fun searchPhoto(
            @Query("query") query: String,
            @Query("collections") collectionIds: IntArray? = null,
            @Query("featured") featured: Int = 0,
            @Query("content_filter") content_filter: String = "low",
            @Query("orientation") orientation: String = "",
            @Query("count") count: Int = 1,
        )

        /**
         * Retrieve total number of downloads, views and likes of a single photo, as well as
         * the historical breakdown of these stats in a specific timeframe (default is 30 days).
         *
         * Currently, the only resolution param supported is “days”. The quantity param can be any number between 1 and 30.
         *
         * @id    The public id of the photo. Required.
         * @resolution    The frequency of the stats. (Optional; default: “days”)
         * @quantity    The amount of for each stat. (Optional; default: 30)
         */
        @GET("/photos/:id/statistics")
        fun statisticsOfPhoto(
            @Query("id") id: Int,
            @Query("resolution") resolution: String = "days",
            @Query("quantity") quantity: Int = 30,
        )
    }

}