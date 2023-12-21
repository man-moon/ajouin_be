package com.ajouin.ajouin_be.domain.post.domain

import com.ajouin.ajouin_be.domain.member.domain.Member
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
class PostComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post,

    @ManyToOne
    @JoinColumn(name = "writer_id")
    val writer: Member,

    @ManyToOne
    @JoinColumn(name = "parent_id")
    val parent: PostComment? = null,

    @Column(nullable = false)
    var content: String,

    @OneToMany(mappedBy = "comment", cascade = [CascadeType.REMOVE])
    val preferences: MutableList<Preference> = mutableListOf(),

    val isAnonymous: Boolean = true,

    var isDeleted: Boolean = false,

    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun fixture(
            id: Long? = null,
            post: Post = Post.fixture(),
            member: Member = Member.fixture(),
            parent: PostComment? = null,
            content: String = "testComment",
            preferences: MutableList<Preference> = mutableListOf(),
        ): PostComment {
            return PostComment(id, post, member, parent, content, preferences)
        }
    }
}