package com.example.diary.home.domain.model

data class Toilet(
    val toilet: ToiletLevels,
    val date: String
)

enum class ToiletLevels {
    HARD_SAUSAGE,
    HARD_LUMPY_SAUSAGE,
    SAUSAGE_WITH_CRACKS,
    SMOOTH_SOFT_SAUSAGE,
    SOFT_BLOBS_WITH_EDGES,
    FLUFFY_MUSHY_PIECES,
    WATERY
}