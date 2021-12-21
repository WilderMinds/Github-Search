package com.samdev.githubsearch.core.usecases

import com.samdev.githubsearch.core.domain.*
import java.util.*

/**
 * @author Sam
 * Created 14/12/2021 at 2:07 PM
 */
class SortRepositories {

    operator fun invoke(sortState: SortState, originalList: List<Repo>): List<Repo> {
        return when (sortState) {
            SortState.STARS -> applySortComparator(RepoStarsComparator(), originalList)
            SortState.FORKS -> applySortComparator(RepoForksComparator(), originalList)
            SortState.UPDATED -> applySortComparator(RepoUpdatedComparator(), originalList)
            SortState.NONE -> originalList
        }
    }


    /**
     * Apply relevant sort comparator and return the sorted list
     */
    private fun applySortComparator(
        comparator: Comparator<Repo>,
        originalList: List<Repo>
    ): List<Repo> {
        val sortedList: List<Repo> = originalList.toList()
        Collections.sort(sortedList, comparator)
        return sortedList
    }

}