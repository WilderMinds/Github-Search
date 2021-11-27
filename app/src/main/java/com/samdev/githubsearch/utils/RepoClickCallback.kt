package com.samdev.githubsearch.utils

import com.samdev.githubsearch.data.models.Repo

/**
 * @author Sam
 * Created 27/11/2021 at 8:20 PM
 */
interface RepoClickCallback {
    fun onUserImageClicked(repo: Repo)
    fun onListItemClicked(repo: Repo)
}