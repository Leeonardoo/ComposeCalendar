package io.github.boguszpawlowski.composecalendar.month

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.pager.PagerItemCount
import io.github.boguszpawlowski.composecalendar.pager.toIndex
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.week.Week
import io.github.boguszpawlowski.composecalendar.week.getWeeks
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

internal const val DaysOfWeek = 7

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun <T : SelectionState> MonthPager(
  showAdjacentMonths: Boolean,
  selectionState: T,
  monthState: MonthState,
  daysOfWeek: List<DayOfWeek>,
  alwaysSameWeekCount: Boolean,
  today: LocalDate,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekContent: @Composable ColumnScope.(Week, T, @Composable BoxScope.(DayState<T>) -> Unit) -> Unit,
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
  val startIndex = PagerItemCount / 2
  val pagerState = rememberPagerState(initialPage = startIndex)
  val coroutineScope = rememberCoroutineScope()

  val monthPagerState = remember {
    MonthPagerState(
      coroutineScope = coroutineScope,
      monthState = monthState,
      pagerState = pagerState,
    )
  }

  HorizontalPager(
    count = PagerItemCount,
    modifier = modifier.testTag("MonthPager"),
    state = pagerState,
    verticalAlignment = Alignment.Top,
  ) { index ->
    MonthContent(
      showAdjacentMonths = showAdjacentMonths,
      selectionState = selectionState,
      currentMonth = monthPagerState.getMonthForPage(index.toIndex()),
      today = today,
      daysOfWeek = daysOfWeek,
      alwaysSameWeekCount = alwaysSameWeekCount,
      dayContent = dayContent,
      weekContent = weekContent,
      weekHeader = weekHeader,
      monthContainer = monthContainer
    )
  }
}

@Composable
internal fun <T : SelectionState> MonthContent(
  showAdjacentMonths: Boolean,
  selectionState: T,
  currentMonth: YearMonth,
  daysOfWeek: List<DayOfWeek>,
  alwaysSameWeekCount: Boolean,
  today: LocalDate,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekContent: @Composable ColumnScope.(Week, T, @Composable BoxScope.(DayState<T>) -> Unit) -> Unit,
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
  Column {
    Box(
      modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight(),
      content = { weekHeader(daysOfWeek) },
    )

    monthContainer { paddingValues ->
      Column(
        modifier = Modifier
          .wrapContentWidth()
          .padding(paddingValues)
      ) {
        val weeks = remember(currentMonth) {
          currentMonth.getWeeks(
            includeAdjacentMonths = showAdjacentMonths,
            alwaysSameWeekCount = alwaysSameWeekCount,
            firstDayOfTheWeek = daysOfWeek.first(),
            today = today,
          )
        }

        weeks.forEach { week ->
          weekContent(week, selectionState, dayContent)
        }
      }
    }
  }
}
