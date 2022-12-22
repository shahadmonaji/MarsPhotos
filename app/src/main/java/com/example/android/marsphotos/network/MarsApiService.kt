import com.example.android.marsphotos.network.MarsPhoto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

//بتحلول jison الى kotlin object
//
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
// مهمته يتواصل مع ال URL

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//ليش ماقلنا getmarshPhotos وحطينا getPhotos؟ لان طبيعه الطلب اللي ابي اطلبه صور لذلك نحط getPhotos
//بترجع لي لسته في الريسبونس List<MarsPhoto>>
interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos() : List<MarsPhoto>
}
//مهمته تفعيل الاتصال
object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}