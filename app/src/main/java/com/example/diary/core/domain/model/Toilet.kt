package com.example.diary.core.domain.model

import java.time.LocalTime

data class Toilet(
    val id: Long = 0,
    val toiletLevel: ToiletLevel,
    val time: LocalTime,
    val dayId: Long = 0
)

enum class ToiletLevel(val text: String) {
    HARD_SAUSAGE("Твердая сарделька"),
    HARD_LUMPY_SAUSAGE("Твердая кусковая сарделька"),
    SAUSAGE_WITH_CRACKS("Сарделька с трещинами"),
    SMOOTH_SOFT_SAUSAGE("Гладкая сарделька"),
    SOFT_BLOBS_WITH_EDGES("Мягкие шарики с краями"),
    FLUFFY_MUSHY_PIECES("Кашицеобразные кусочки"),
    WATERY("Водянистый")
}