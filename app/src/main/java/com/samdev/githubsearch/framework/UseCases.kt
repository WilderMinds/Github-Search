package com.samdev.githubsearch.framework

import com.samdev.githubsearch.core.usecases.*

/**
 * @author Sam
 * Created 14/12/2021 at 3:49 PM
 */
data class UseCases(
    val getAllRepositoryLanguages: GetAllRepositoryLanguages,
    val getRepositoryContributors: GetRepositoryContributors,
    val getUser: GetUser,
    val searchRepositories: SearchRepositories,
    val sortRepositories: SortRepositories
)
