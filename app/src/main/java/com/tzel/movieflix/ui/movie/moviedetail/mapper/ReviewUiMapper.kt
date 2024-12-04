package com.tzel.movieflix.ui.movie.moviedetail.mapper

import com.tzel.movieflix.domain.core.Mapper
import com.tzel.movieflix.domain.movie.entity.Review
import com.tzel.movieflix.ui.movie.moviedetail.model.ReviewUi
import javax.inject.Inject

class ReviewUiMapper @Inject constructor() : Mapper {
    operator fun invoke(reviews: List<Review>?, page: String = ""): List<ReviewUi> {
        return reviews?.map {
            ReviewUi(
                id = it.id,
                key = "${it.id}$page",
                author = it.author,
                content = it.content,
                url = it.url
            )
        }?.distinctBy { it.key } ?: emptyList()
    }
}