package com.ajouin.ajouin_be.domain

import com.ajouin.ajouin_be.domain.post.domain.Post
import com.ajouin.ajouin_be.domain.post.repository.PostRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class PostTest(
    private val postRepository: PostRepository,
) {

    @Transactional
    @Test
    fun `객체가 영속화되면 타임스탬프가 초기화된다`() {
        val post = Post.fixture()
        val savedPost = postRepository.save(post)
        println(savedPost.createdAt)
    }
}