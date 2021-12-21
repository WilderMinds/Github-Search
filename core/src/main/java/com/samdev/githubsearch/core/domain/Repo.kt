package com.samdev.githubsearch.core.domain

import com.samdev.githubsearch.core.utils.DateUtils
import java.io.Serializable
import java.util.*

/**
 * @author Sam
 * Created 27/11/2021 at 1:38 PM
 */
data class Repo(
    var id: Int? = 0,
    var node_id: String? = "",
    var name: String? = "",
    var full_name: String? = "",
    var private: Boolean = false,
    var owner: Owner? = Owner(),
    var html_url: String? = "",
    var description: String? = "",
    var fork: Boolean = false,
    var url: String? = "",
    var forks_url: String? = "",
    var keys_url: String? = "",
    var collaborators_url: String? = "",
    var teams_url: String? = "",
    var hooks_url: String? = "",
    var issue_events_url: String? = "",
    var events_url: String? = "",
    var assignees_url: String? = "",
    var branches_url: String? = "",
    var tags_url: String? = "",
    var blobs_url: String? = "",
    var git_tags_url: String? = "",
    var git_refs_url: String? = "",
    var trees_url: String? = "",
    var statuses_url: String? = "",
    var languages_url: String? = "",
    var stargazers_url: String? = "",
    var contributors_url: String? = "",
    var subscribers_url: String? = "",
    var subscription_url: String? = "",
    var commits_url: String? = "",
    var git_commits_url: String? = "",
    var comments_url: String? = "",
    var issue_comment_url: String? = "",
    var contents_url: String? = "",
    var compare_url: String? = "",
    var merges_url: String? = "",
    var archive_url: String? = "",
    var downloads_url: String? = "",
    var issues_url: String? = "",
    var pulls_url: String? = "",
    var milestones_url: String? = "",
    var notifications_url: String? = "",
    var labels_url: String? = "",
    var releases_url: String? = "",
    var deployments_url: String? = "",
    var created_at: String? = "",
    var updated_at: String? = "",
    var pushed_at: String? = "",
    var git_url: String? = "",
    var ssh_url: String? = "",
    var clone_url: String? = "",
    var svn_url: String? = "",
    var homepage: String? = "",
    var size: Int? = 0,
    var stargazers_count: Int? = 0,
    var watchers_count: Int? = 0,
    var language: String? = "",
    var has_issues: Boolean = false,
    var has_projects: Boolean = false,
    var has_downloads: Boolean = false,
    var has_wiki: Boolean = false,
    var has_pages: Boolean = false,
    var forks_count: Int? = 0,
    var mirror_url: String? = "",
    var archived: Boolean = false,
    var disabled: Boolean = false,
    var open_issues_count: Int? = 0,
    var license: License? = null,
    var allow_forking: Boolean = false,
    var is_template: Boolean = false,
    var topics: List<String> = arrayListOf(),
    var visibility: String? = "",
    var forks: Int? = 0,
    var open_issues: Int? = 0,
    var watchers: Int? = 0,
    var default_branch: String? = "",
    var temp_clone_token: String? = "",
    var network_count: Int? = 0,
    var subscribers_count: Int? = 0
) : Serializable


/**
 * Sorted in descending order
 */
class RepoStarsComparator : Comparator<Repo> {
    override fun compare(o1: Repo, o2: Repo): Int {
        val stars1 = o1.stargazers_count ?: 0
        val stars2 = o2.stargazers_count ?: 0
        return when {
            stars1 == stars2 -> 0
            stars1 > stars2 -> -1
            else -> 1
        }
    }
}


/**
 * Sorted in descending order
 */
class RepoForksComparator : Comparator<Repo> {
    override fun compare(o1: Repo, o2: Repo): Int {
        val forks1 = o1.forks_count ?: 0
        val forks2 = o2.forks_count ?: 0
        return when {
            forks1 == forks2 -> 0
            forks1 > forks2 -> -1
            else -> 1
        }
    }
}


/**
 * Sorted in descending order
 */
class RepoUpdatedComparator : Comparator<Repo> {
    override fun compare(o1: Repo, o2: Repo): Int {
        val date1 = DateUtils.stringToDate(o1.updated_at) ?: Date()
        val date2 = DateUtils.stringToDate(o2.updated_at) ?: Date()
        return date2.compareTo(date1)
    }
}
