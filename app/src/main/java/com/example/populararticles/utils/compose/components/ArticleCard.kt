/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.populararticles.utils.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.populararticles.R
import com.example.populararticles.entities.Article
import com.google.accompanist.coil.rememberCoilPainter
import com.surrus.bikeshare.utils.compose.text_bold
import grayBlack

@Composable
fun ArticleCard(
    article: Article,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {

    Card(modifier = modifier) {
        Column(
            modifier = if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
        ) {
            article?.apply {
                media?.apply {
                    val url = if (isNotEmpty()) get(0)?.let {
                        it.mediaMetadata?.get(2)?.url ?: ""
                    } ?: "" else ""

                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = rememberCoilPainter(request = url),
                        contentDescription = stringResource(R.string.daily)
                    )
                }


                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = title ?: "No title",
                        style = MaterialTheme.typography.body2,
                        maxLines = 3,
                        modifier = Modifier
                            .requiredHeight(90.dp)
                            .padding(8.dp)/*.align(Alignment.CenterStart)*/
                    )

                    Row(
                        Modifier.fillMaxWidth(),
                    ) {
                        val center_padding = Modifier
                            .padding(4.dp)
                            .align(Alignment.CenterVertically)
                        Text(
                            text = section ?: "No title",
                            style = MaterialTheme.typography.caption.merge(text_bold),
                            color = Color.Black,
                            modifier = center_padding
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = published_date ?: "",
                            style = MaterialTheme.typography.caption,
                            color = grayBlack(),
                            modifier = center_padding
                        )

                    }

                }
            }


        }
    }

}

@Preview
@Composable
private fun PreviewArticleCard() {
    ArticleCard(
        article = Article(title = "Piden just became president" , section = "Politics"),

        )
}

@Composable
fun PlaceholderPosterCard(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Box {
            // TODO: display something better
        }
    }
}
