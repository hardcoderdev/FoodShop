package hardcoder.dev.foodshop.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hardcoder.dev.data.remote.FoodAPI
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

const val BASE_URL = "https://run.mocky.io/v3/"

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single {
        get<Retrofit>().create(FoodAPI::class.java)
    }
}