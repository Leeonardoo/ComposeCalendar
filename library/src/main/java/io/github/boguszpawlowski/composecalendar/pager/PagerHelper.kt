package io.github.boguszpawlowski.composecalendar.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import io.github.boguszpawlowski.composecalendar.month.PageCount

internal const val PagerItemCount = 20_000
internal const val StartIndex = PagerItemCount / 2

internal fun Int.toIndex(startIndex: Int = StartIndex, pageCount: Int = PageCount) =
  ((this - startIndex).floorMod(pageCount) + 1).mod(pageCount)

private fun Int.floorMod(other: Int): Int = when (other) {
  0 -> this
  else -> this - floorDiv(other) * other
}

@OptIn(ExperimentalFoundationApi::class)
internal val PagerState.currentIndex
  get() = currentPage.toIndex()
