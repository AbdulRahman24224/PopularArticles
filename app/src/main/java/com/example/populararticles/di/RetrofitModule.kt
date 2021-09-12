package com.example.populararticles.di

import com.example.populararticles.domain.data.ArticlesApis
import com.example.populararticles.domain.data.HtmlApi
import com.example.populararticles.domain.data.MyCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


private const val SERVER_BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/viewed/"
private const val API_KEY = "api-key"
private const val API_KEY_VALUE = "k32jrKem6G6bfDG15r1AVUtwCs0DIqNh"
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

   /* @Singleton
    @Provides
    fun provideLoggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder().setLevel(Level.BODY).tag("Logging")
            .request("Retrofit_Request").response("Retrofit_Response").build()
    }*/
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder().setLevel(Level.BODY).tag("Logging")
            .request("Retrofit_Request").response("Retrofit_Response").build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(logging: LoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Named("Json_Converter")
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(MyCallAdapterFactory())
    }

    @Singleton
    @Named("HTML_Converter")
    @Provides
    fun provideRetrofitWithHtmlConverter( client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(MyCallAdapterFactory())

    }

    @Singleton
    @Provides
    fun provideBlogService( @Named("Json_Converter") retrofit: Retrofit.Builder): ArticlesApis {
        return retrofit
            .build()
            .create(ArticlesApis::class.java)
    }

    @Singleton
    @Provides
    fun provideHtmlService(@Named("HTML_Converter")retrofit: Retrofit.Builder): HtmlApi {
        return retrofit
            .build()
            .create(HtmlApi::class.java)
    }

}

