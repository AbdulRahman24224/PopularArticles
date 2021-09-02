package com.example.populararticles.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticlesResponse(
    val copyright: String = "",
    val num_results: Int = 0,
    @SerializedName("results")
    val results: List<Article> = listOf(),
    val status: String = "",
    val error: String = ""
) : Serializable

data class Article(
    val `abstract`: String="",
    val adx_keywords: String="",
    val asset_id: Long=0,
    val byline: String="",
    val column: Any?=null,
    val des_facet: List<String>?=null,
    val eta_id: Int=0,
    val geo_facet: List<String>?=null,
    val id: Long=0,
    val media: List<Media>? = null,
    val nytdsection: String="",
    val org_facet: List<String>?=null,
    val per_facet: List<String>?=null,
    val published_date: String="",
    val section: String="",
    val source: String="",
    val subsection: String="",
    val title: String="",
    val type: String="",
    val updated: String="",
    val uri: String="",
    val url: String ="",
    var details: String =""
) : Serializable

data class Media(
    val approved_for_syndication: Int,
    val caption: String,
    val copyright: String,
    @SerializedName("media-metadata")
    val mediaMetadata: List<MediaMetadata>? = null,
    val subtype: String,
    val type: String
) : Serializable

data class MediaMetadata(
    val format: String,
    val height: Int,
    val url: String,
    val width: Int
) : Serializable