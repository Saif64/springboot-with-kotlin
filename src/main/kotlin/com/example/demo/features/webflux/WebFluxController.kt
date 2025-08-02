package com.example.demo.features.webflux

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/web")
class CombinedDataController(
    private val jsonPlaceholderService: JsonPlaceholderService
) {

    /**
     * An endpoint that streams combined data using Server-Sent Events (SSE).
     * Clients will receive updates based on a shared, periodic poller in the service.
     */
    @GetMapping("/combined-data-stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamPostsAndComments(): Flux<CombinedDataResponse> {
        return jsonPlaceholderService.streamCombinedData()
    }
}