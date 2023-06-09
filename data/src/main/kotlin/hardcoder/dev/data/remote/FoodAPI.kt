package hardcoder.dev.data.remote

import hardcoder.dev.data.remote.entities.AllCategoriesResponse
import hardcoder.dev.data.remote.entities.AllDishesResponse
import retrofit2.http.GET

interface FoodAPI {

    @GET("aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun getAllDishes(): AllDishesResponse

    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getAllCategories(): AllCategoriesResponse
}