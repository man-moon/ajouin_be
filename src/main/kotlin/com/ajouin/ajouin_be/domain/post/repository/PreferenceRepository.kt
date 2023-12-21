package com.ajouin.ajouin_be.domain.post.repository

import com.ajouin.ajouin_be.domain.member.domain.Member
import com.ajouin.ajouin_be.domain.post.domain.Post
import com.ajouin.ajouin_be.domain.post.domain.Preference
import com.ajouin.ajouin_be.domain.post.domain.PostComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferenceRepository : JpaRepository<Preference, Long>{
    fun existsByPostAndMember(post: Post, member: Member) : Boolean
    fun findByPostAndMember(post: Post, member: Member) : Preference?
    fun existsByCommentAndMember(comment: PostComment, member: Member) : Boolean
    fun findByCommentAndMember(comment: PostComment, member: Member) : Preference?
}