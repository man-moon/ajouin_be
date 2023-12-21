package com.ajouin.ajouin_be.domain.post.service

import com.ajouin.ajouin_be.domain.post.domain.Preference
import com.ajouin.ajouin_be.domain.post.repository.PostCommentRepository
import com.ajouin.ajouin_be.domain.post.repository.PreferenceRepository
import com.ajouin.ajouin_be.domain.post.repository.PostRepository
import com.ajouin.ajouin_be.domain.member.repository.MemberRepository
import com.ajouin.ajouin_be.global.error.exception.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PreferenceServiceImpl (
    val postRepository: PostRepository,
    val preferenceRepository: PreferenceRepository,
    val memberRepository: MemberRepository,
    val postCommentRepository: PostCommentRepository,
) {
    @Transactional
    fun postPreferenceManage(postId: Long, memberId: UUID): Int {

        val savedPost = postRepository.findByIdOrNull(postId) ?: throw EntityNotFoundException()
        val savedMember = memberRepository.findById(memberId) ?: throw EntityNotFoundException()

        val existingPreference = preferenceRepository.findByPostAndMember(savedPost, savedMember)

        if (existingPreference != null) {
            preferenceRepository.delete(existingPreference)
            savedPost.preferences!!.removeIf { it.id == existingPreference.id }
        } else {
            val newPreference = Preference(post = savedPost, member = savedMember)
            savedPost.preferences!!.add(newPreference)
            preferenceRepository.save(newPreference)
        }

        return savedPost.preferences.size
    }

    @Transactional
    fun commentPreferenceManage(commentId: Long, memberId: UUID): Int {
        //댓글 검증
        val savedComment = postCommentRepository.findByIdOrNull(commentId) ?: throw EntityNotFoundException()
        //멤버 호출
        val savedMember = memberRepository.findById(memberId) ?: throw EntityNotFoundException()
        //댓글 좋아요 유저 검증
        if (preferenceRepository.existsByCommentAndMember(savedComment, savedMember)){
            val savedPreference = preferenceRepository.findByCommentAndMember(savedComment, savedMember)
            preferenceRepository.delete(savedPreference!!)
            savedComment.preferences.removeIf{ it.id == savedPreference.id}
            postCommentRepository.save(savedComment)
        } else {
            val savePreference = preferenceRepository.save(Preference(comment = savedComment, member = savedMember))
            savedComment.preferences.add(savePreference)
            postCommentRepository.save(savedComment)
        }
        val resultComment = postCommentRepository.findByIdOrNull(commentId) ?: throw EntityNotFoundException()
        return resultComment.preferences.size
    }
}