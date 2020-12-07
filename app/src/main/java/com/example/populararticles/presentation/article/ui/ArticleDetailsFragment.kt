package com.example.populararticles.presentation.article.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.populararticles.R

class ArticleDetailsFragment : Fragment() {

    val args: ArticleDetailsFragmentArgs by navArgs()

    lateinit var iv_article_image: AppCompatImageView
    lateinit var tv_title: TextView
    lateinit var tv_description: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)

        args.article?.apply {

            media?.apply {
                if (isNotEmpty()) get(0).apply {
                    Glide.with(requireContext())
                        .load(mediaMetadata?.get(0)?.url ?: "")
                        /* .apply( RequestOptions().override(width, height))*/
                        .into(iv_article_image)
                }
            }

            tv_title.text = title
            tv_description.text = nytdsection
        }
    }

    fun bindViews(view: View) {
        view.apply {
            iv_article_image = findViewById<AppCompatImageView>(R.id.iv_article_image)
            tv_title = findViewById<TextView>(R.id.tv_title)
            tv_description = findViewById<TextView>(R.id.tv_description)
        }
    }

}