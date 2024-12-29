package com.example.electronicqueue.controller;

import com.example.electronicqueue.entity.Place;
import com.example.electronicqueue.entity.Queue;
import com.example.electronicqueue.service.PlaceService;
import com.example.electronicqueue.service.QueueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/queues")
public class QueueRestController {
    private final QueueService queueService;
    private final PlaceService placeService;

    public QueueRestController(QueueService queueService, PlaceService placeService) {
        this.queueService = queueService;
        this.placeService = placeService;
    }

    @Operation(summary = "Створення нової черги", description = "Додає нову чергу із заданою назвою та статусом.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Черга успішно створена",
                    content = @Content(schema = @Schema(implementation = Queue.class))),
            @ApiResponse(responseCode = "400", description = "Некоректний запит")
    })
    @PostMapping("/queue")
    public Queue createQueue(@RequestParam String name, @RequestParam boolean isOpen) {
        return queueService.createQueue(name, isOpen);
    }

    @Operation(summary = "Отримання всіх черг", description = "Повертає список усіх черг у системі.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список черг отримано",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    @GetMapping
    public ResponseEntity<List<Queue>> getAllQueues() {
        List<Queue> queues = queueService.getAllQueues();
        return ResponseEntity.ok(queues);
    }

    @Operation(summary = "Отримання черги за ID", description = "Повертає чергу за її унікальним ідентифікатором.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Черга знайдена",
                    content = @Content(schema = @Schema(implementation = Queue.class))),
            @ApiResponse(responseCode = "404", description = "Черга не знайдена")
    })
    @GetMapping("/queue/{id}")
    public Queue getQueueById(@PathVariable int id) {
        return queueService.getQueueById(id).orElse(null);
    }

    @GetMapping("/status")
    public List<Queue> getQueuesByStatus(@RequestParam boolean isOpen) {
        return queueService.getQueuesByStatus(isOpen);
    }

    @Operation(summary = "Оновлення черги", description = "Оновлює інформацію про чергу за ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Черга успішно оновлена",
                    content = @Content(schema = @Schema(implementation = Queue.class))),
            @ApiResponse(responseCode = "404", description = "Черга не знайдена")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Queue> updateQueue(@PathVariable Integer id, @RequestParam String name, @RequestParam boolean isOpen) {
        return queueService.updateQueue(id, name, isOpen)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Видалення черги", description = "Видаляє чергу за її ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Черга успішно видалена"),
            @ApiResponse(responseCode = "404", description = "Черга не знайдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQueue(@PathVariable Integer id) {
        boolean deleted = queueService.deleteQueue(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Додавання користувача до черги", description = "Додає нового користувача до вказаної черги.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Користувача успішно додано",
                    content = @Content(schema = @Schema(implementation = Place.class))),
            @ApiResponse(responseCode = "404", description = "Черга не знайдена")
    })
    @PostMapping("/{queueId}/places")
    public ResponseEntity<Place> addUserToQueue(@PathVariable Integer queueId, @RequestParam String userName) {
        Place place = placeService.addUserToQueue(queueId, userName);
        return ResponseEntity.ok(place);
    }

    @Operation(summary = "Отримання всіх місць у черзі", description = "Повертає список усіх користувачів у вказаній черзі.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список місць отримано",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "Черга не знайдена")
    })
    @GetMapping("/{queueId}/places")
    public ResponseEntity<List<Place>> getPlacesInQueue(@PathVariable Integer queueId) {
        List<Place> places = placeService.getPlacesInQueue(queueId);
        return ResponseEntity.ok(places);
    }

    @Operation(summary = "Видалення користувача з черги", description = "Видаляє користувача за його ID з вказаної черги.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Користувача успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Користувача або чергу не знайдено")
    })
    @DeleteMapping("/{queueId}/places/{placeId}")
    public ResponseEntity<Void> removeUserFromQueue(@PathVariable Integer queueId, @PathVariable Integer placeId) {
        boolean removed = placeService.removeUserFromQueue(queueId, placeId);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public List<Queue> searchQueuesByName(@RequestParam String name) {
        return queueService.searchQueuesByName(name);
    }
}
