package com.ajouin.ajouin_be.domain.post.domain

import com.ajouin.ajouin_be.domain.member.domain.Member
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime
import java.util.*

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    var tag: PostTag,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    val writer: Member,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.REMOVE])
    val preferences: MutableList<Preference>? = mutableListOf(),

    @ColumnDefault("0")
    @Column(name = "view_cnt", nullable = false)
    var viewCount: Int,

    var isDeleted: Boolean = false,

    var isAnonymous: Boolean = true,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun fixture(
            id: Long? = null,
            title: String = "postTest",
            content: String = "postContentTest",
            tagId: PostTag = PostTag.fixture(
                tagName = "testTag"
            ),
            writerId: Member = Member.fixture(),
            preferences: MutableList<Preference> = mutableListOf(),
            viewCount: Int = 0,
        ): Post {
            return Post(id, title, content, tagId, writerId, preferences, viewCount)
        }
    }
}