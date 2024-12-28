package com.example.electronicqueue.controller;

import com.example.electronicqueue.model.Queue;
import com.example.electronicqueue.service.QueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import java.util.List;

@RestController
@RequestMapping("/api/queues")
public class QueueRestController {

    private final QueueService queueService;

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
    public ResponseEntity<Integer> createQueue(@RequestParam @Schema(required = true) String name, @RequestParam boolean isOpen) {
        int id = queueService.createQueue(name, isOpen);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
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
    public ResponseEntity<Queue> getQueueById(@PathVariable int id) {
        Queue queue = queueService.getQueueById(id);
        return queue != null ? ResponseEntity.ok(queue) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Оновлення черги", description = "Оновлює дані черги за заданим ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Чергу успішно оновлено"),
            @ApiResponse(responseCode = "404", description = "Черга не знайдена")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateQueue(@PathVariable int id, @RequestParam String name, @RequestParam boolean isOpen) {
        int updatedRows = queueService.updateQueue(id, name, isOpen);
        return updatedRows > 0 ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Видалення черги", description = "Видаляє чергу за заданим ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Чергу успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Черга не знайдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQueue(@PathVariable int id) {
        int deletedRows = queueService.deleteQueue(id);
        return deletedRows > 0 ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Видалення черги разом із місцями",
            description = "Видаляє чергу за ID і всі пов’язані з нею місця."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Чергу та місця успішно видалено"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Черга не знайдена"
            )
    })
    @DeleteMapping("/{id}/with-places")
    public ResponseEntity<Void> deleteQueueAndPlaces(@PathVariable int id) {
        queueService.deleteQueueAndPlaces(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Фільтрація черг за статусом",
            description = "Повертає список черг, які відповідають заданому статусу (відкриті або закриті)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список черг отримано успішно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Queue.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Неправильний запит")
    })
    @GetMapping("/filter/status")
    public ResponseEntity<List<Queue>> filterQueuesByStatus(@RequestParam boolean isOpen) {
        List<Queue> queues = queueService.filterQueuesByStatus(isOpen);
        return ResponseEntity.ok(queues);
    }


//    @GetMapping("/filter")
//    public ResponseEntity<List<Queue>> filterQueuesByName(@RequestParam String name) {
//        List<Queue> filteredQueues = queueService.filterQueuesByName(name);
//        return ResponseEntity.ok(filteredQueues);
//    }
//
//    @GetMapping("/page")
//    public ResponseEntity<List<Queue>> getQueuesByPage(@RequestParam int page, @RequestParam int size) {
//        List<Queue> paginatedQueues = queueService.getQueuesByPage(page, size);
//        return ResponseEntity.ok(paginatedQueues);
//    }
}
