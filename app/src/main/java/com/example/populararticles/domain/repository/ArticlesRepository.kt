package com.example.populararticles.domain.repository

import com.example.populararticles.domain.data.ArticlesApis
import com.example.populararticles.domain.data.HtmlApi
import com.example.populararticles.domain.data.Result
import com.example.populararticles.entities.ArticlesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element


class ArticlesRepository
constructor(
    private val service: ArticlesApis,
    private val htmlService: HtmlApi

) {

    fun getArticlesWithin(period: String) : Flow<ArticlesResponse> =
        flow<ArticlesResponse> {
            val articlesResponse = service.getArticlesWithin(period)
            when (articlesResponse) {
                is Result.Success -> {

                    emit(articlesResponse.data!!)
                }
                is Result.Failure -> {
                }
            }

        }

    fun getArticlefor(url: String) =
        flow<String> {
            val response = htmlService.fetchUrl(url)
            when (response) {
                is Result.Success -> {
                    var responseString: String = response.data?:""
                    val doc: Document = Jsoup.parse(responseString)

                    val docc = doc.select("body").select("section")
                    val finalElement = mutableListOf<Element>()
                        docc.forEach {
                       if (it.getElementsByAttributeValue("name" , "articleBody").size>0) {
                           finalElement.add(it)
                       }
                        }
                    val htmlText =  "<!DOCTYPE html>" +
                            "<<html lang=\"en\" class=\"story\"  xmlns:og=\"http://opengraphprotocol.org/schema/\">"+finalElement.get(0)+ "</html>"
                    emit(htmlText)

                }
                is Result.Failure -> {
                }
            }

        }

   /* fun sortByValueDesc(map: Map<String?, Int?>): HashMap<String, Int>? {
        val list: List<Map.Entry<String, Int>?> = LinkedList<Any?>(map.entries)
        Collections.sort(
            list
        ) { o1, o2 -> o2.value.compareTo(o1.value) }
        val result: HashMap<String, Int> = LinkedHashMap()
        for (entry in list) {
            result[entry!!.key] = entry.value
        }
        return result
    }*/

}
