package training.squads.fitnessapp.network

import UnsafeOkHttpClient
import androidx.databinding.ktx.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
// import training.squads.fitnessapp.BuildConfig
import training.squads.fitnessapp.network.jsonAdapters.GroupLessonTypeEnumJsonAdapter
// import training.squads.fitnessapp.network.jsonAdapters.SessionTypeEnumJsonAdapter
import training.squads.fitnessapp.network.jsonAdapters.UuidJsonAdapter
import training.squads.fitnessapp.network.jsonAdapters.localDateTimeJsonAdapter


object ApiSquads {
    private const val BASE_URL = "https://192.168.0.241:45455/api/"
    // private const val BASE_URL = "https://10.0.2.2:7155/api/"

    private var unsafeOkHttpClient : UnsafeOkHttpClient = UnsafeOkHttpClient
    val unsafeClient = unsafeOkHttpClient.getUnsafeOkHttpClient()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(localDateTimeJsonAdapter())
        .add(UuidJsonAdapter())
        .add(/*Session*/GroupLessonTypeEnumJsonAdapter())
        .build()

    val client = OkHttpClient.Builder()
        .build()

    val contentType = "application/json".toMediaType()
    val converterFactory = Json.asConverterFactory(contentType)

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(getCorrectClient())
        .build()

    val retrofitService: ApiSquadsService by lazy {
        retrofit.create(ApiSquadsService::class.java)
    }

    private fun getCorrectClient(): OkHttpClient?{
        if (BuildConfig.DEBUG) {
           return unsafeClient
        }else{
            return client
        }
    }

}
