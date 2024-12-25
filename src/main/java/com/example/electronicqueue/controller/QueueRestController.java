package com.example.electronicqueue.controller;

import com.example.electronicqueue.model.Queue;
import com.example.electronicqueue.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/queues")
public class QueueRestController {

    private final QueueService queueService;

    @Autowired
    public QueueRestController(QueueService queueService) {
        this.queueService = queueService;
    }

    @Operation(summary = "Створення нової черги", description = "Додає нову чергу із заданою назвою.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Чергу успішно створено",
                    content = @Content(schema = @Schema(implementation = Queue.class))),
            @ApiResponse(responseCode = "400", description = "Неправильний запит")
    })
    @PostMapping
    public ResponseEntity<Queue> createQueue(@RequestBody Queue queue) {
        Queue createdQueue = queueService.createQueue(queue.getName());
        return new ResponseEntity<>(createdQueue, HttpStatus.CREATED);
    }

    @Operation(summary = "Отримання списку всіх черг", description = "Повертає список усіх черг у системі.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список черг отримано",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    @GetMapping
    public ResponseEntity<List<Queue>> getAllQueues() {
        List<Queue> queues = queueService.getAllQueues();
        return ResponseEntity.ok(queues);
    }

    @Operation(summary = "Отримання черги за ID", description = "Повертає чергу за заданим ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Черга знайдена",
                    content = @Content(schema = @Schema(implementation = Queue.class))),
            @ApiResponse(responseCode = "404", description = "Черга не знайдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Queue> getQueueById(@PathVariable String id) {
        Optional<Queue> queue = queueService.getQueueById(id);
        return queue.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Оновлення черги", description = "Оновлює дані черги за заданим ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Чергу успішно оновлено"),
            @ApiResponse(responseCode = "404", description = "Черга не знайдена")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Queue> updateQueue(@PathVariable String id, @RequestBody Queue updatedQueue) {
        Optional<Queue> queue = queueService.updateQueue(id, updatedQueue.getName());
        return queue.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Видалення черги", description = "Видаляє чергу за заданим ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Чергу успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Черга не знайдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQueue(@PathVariable String id) {
        boolean deleted = queueService.deleteQueue(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Queue>> filterQueuesByName(@RequestParam String name) {
        List<Queue> filteredQueues = queueService.filterQueuesByName(name);
        return ResponseEntity.ok(filteredQueues);
    }

    // Pagination
    @GetMapping("/page")
    public ResponseEntity<List<Queue>> getQueuesByPage(@RequestParam int page, @RequestParam int size) {
        List<Queue> paginatedQueues = queueService.getQueuesByPage(page, size);
        return ResponseEntity.ok(paginatedQueues);
    }

}
