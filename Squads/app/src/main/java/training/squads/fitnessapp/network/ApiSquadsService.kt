package training.squads.fitnessapp.network

import kotlinx.coroutines.Deferred
import retrofit2.http.*


interface ApiSquadsService {
    @GET("session")
    @Throws(Exception::class)
    fun getAllSessionsAsync(): Deferred<List<SessionProperty>>

    @GET("session/{id}")
    @Throws(Exception::class)
    fun getSessionById(@Path("id") id: String): Deferred<SessionProperty>

    @POST("session")
    @Throws(Exception::class)
    fun postSession(@Body groupLesson: SessionProperty): Deferred<SessionProperty>

    // @PUT("session/{id}")
    @PUT("session")
    @Throws(Exception::class)
    fun putSession(/*@Path("id") id: String, */@Body session: SessionProperty): Deferred<SessionProperty>

    @DELETE("session/{id}")
    @Throws(Exception::class)

    fun deleteGroupLesson(@Path("id") id: String): Deferred<SessionProperty>

    @GET("user/{email}")
    @Throws(Exception::class)
    fun getUserByEmail(@Path("email") email: String): Deferred<UserProperty>

    @GET("user/{email}/sessionpasses")
    @Throws(Exception::class)
    fun getSessionPassesOfUserAsync(@Path("email") email: String): Deferred<List<UserProperty>>

    @DELETE("session/{id}")
    fun deleteSession(@Path("id") id: String): Deferred<SessionProperty>

}
