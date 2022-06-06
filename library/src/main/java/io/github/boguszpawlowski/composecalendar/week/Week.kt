package io.github.boguszpawlowski.composecalendar.week

import androidx.compose.runtime.Immutable
import io.github.boguszpawlowski.composecalendar.day.Day

@Immutable
public data class Week(
  val isFirstWeekOfTheMonth: Boolean = false,
  val days: List<Day>,
)
