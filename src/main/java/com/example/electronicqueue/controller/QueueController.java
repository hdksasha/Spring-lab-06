package com.example.electronicqueue.controller;

import com.example.electronicqueue.model.Queue;
import com.example.electronicqueue.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    @Autowired
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    // Показати форму для створення черги
    @GetMapping("/create")
    public String showCreateForm() {
        return "create-queue";
    }

    // Створення нової черги
    @PostMapping("/create")
    public String createQueue(@RequestParam String name, Model model) {
        Queue queue = queueService.createQueueAndReturn(name, true); // Статус "відкрита" за замовчуванням
        model.addAttribute("queue", queue);
        return "redirect:/queue/view?queueId=" + queue.getId();
    }

    // Додати користувача до черги
    @PostMapping("/add")
    public String addUser(@RequestParam int queueId, @RequestParam String userName) {
        queueService.addUserToQueue(queueId, userName);
        return "redirect:/queue/view?queueId=" + queueId;
    }

    // Показати чергу
    @GetMapping("/view")
    public String viewQueue(@RequestParam int queueId, Model model) {
        Queue queue = queueService.getQueueById(queueId);
        model.addAttribute("queue", queue);
        return "queue";
    }

    // Перемістити до наступного користувача
    @PostMapping("/next")
    public String moveToNext(@RequestParam int queueId) {
        queueService.moveToNext(queueId);
        return "redirect:/queue/view?queueId=" + queueId;
    }

    // Видалити користувача з черги
    @PostMapping("/remove")
    public String removeUser(@RequestParam int queueId, @RequestParam String userName) {
        queueService.removeUserFromQueue(queueId, userName);
        return "redirect:/queue/view?queueId=" + queueId;
    }

    // Закрити чергу
    @PostMapping("/close")
    public String closeQueue(@RequestParam int queueId) {
        queueService.closeQueue(queueId);
        return "redirect:/queue/view?queueId=" + queueId;
    }
}
