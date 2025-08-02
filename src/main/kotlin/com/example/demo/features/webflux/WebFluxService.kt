package com.example.demo.features.webflux

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class JsonPlaceholderService(
    private val webClient: WebClient
) {
    // A single, shared, continuously running stream of data.
    private val sharedDataStream: Flux<CombinedDataResponse> =
        // FIX APPLIED HERE: Start with ZERO delay, then poll every 15 seconds.
        Flux.interval(Duration.ZERO, Duration.ofSeconds(5))
            .flatMap { _ ->
                println("--- Polling shared data from third-party API ---")
                // Fetch with some reasonable defaults (e.g., 20 posts, 5 comments)
                fetchPostsAndComments(20, 5)
                    // If an API call fails, log it but don't terminate the shared stream
                    .onErrorResume { error ->
                        println("Error fetching shared data: ${error.message}")
                        Mono.empty() // Skips this emission on error and continues the stream
                    }
            }
            .share()

    /**
     * The public method called by the controller.
     * It allows clients to tap into the shared stream for a limited duration.
     */
    fun streamCombinedData(): Flux<CombinedDataResponse> {
        val streamDuration = Duration.ofSeconds(12)

        return sharedDataStream
            .take(streamDuration)
            .doOnSubscribe { println("New client subscribed to the shared stream.") }
            .doOnCancel { println("Client disconnected or stream duration ended.") }
    }

    /**
     * Fetches posts and comments from the third-party API ONCE.
     * This is an internal helper method used by our shared poller.
     */
    private fun fetchPostsAndComments(postsLimit: Int?, commentsLimit: Int?): Mono<CombinedDataResponse> {
        val postsMono = fetchAllPosts(postsLimit)
        val commentsMono = fetchCommentsForPostOne(commentsLimit)

        return Mono.zip(postsMono, commentsMono)
            .map { tuple -> CombinedDataResponse(posts = tuple.t1, comments = tuple.t2) }
    }

    private fun fetchAllPosts(limit: Int?): Mono<List<Post>> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/posts")
                limit?.takeIf { it > 0 }?.let { uriBuilder.queryParam("_limit", it) }
                uriBuilder.build()
            }
            .retrieve()
            .bodyToFlux(Post::class.java)
            .collectList()
    }

    private fun fetchCommentsForPostOne(limit: Int?): Mono<List<Comment>> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/posts/1/comments")
                limit?.takeIf { it > 0 }?.let { uriBuilder.queryParam("_limit", it) }
                uriBuilder.build()
            }
            .retrieve()
            .bodyToFlux(Comment::class.java)
            .collectList()
    }
}