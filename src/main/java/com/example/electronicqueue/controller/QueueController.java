package com.example.electronicqueue.controller;

import com.example.electronicqueue.service.QueueService;
import com.example.electronicqueue.model.Queue;
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

    @GetMapping("/create")
    public String showCreateForm() {
        return "create-queue";
    }

    @PostMapping("/create")
    public String createQueue(@RequestParam String name, Model model) {
        Queue queue = queueService.createQueue(name);
        model.addAttribute("queue", queue);
        return "queue";
    }
    @PostMapping("/add")
    public String addUser(@RequestParam String queueId, @RequestParam String userName) {
        queueService.addUserToQueue(queueId, userName);
        return "redirect:/queue/view?queueId=" + queueId;
    }

    @GetMapping("/view")
    public String viewQueue(@RequestParam String queueId, Model model) {
        Queue queue = queueService.getQueue(queueId).orElseThrow();
        model.addAttribute("queue", queue);
        return "queue";
    }

    @PostMapping("/next")
    public String moveToNext(@RequestParam String queueId) {
        queueService.moveToNext(queueId);
        return "redirect:/queue/view?queueId=" + queueId;
    }

    @PostMapping("/remove")
    public String removeUser(@RequestParam String queueId, @RequestParam String userName) {
        queueService.removeUser(queueId, userName);
        return "redirect:/queue/view?queueId=" + queueId;
    }

    @PostMapping("/close")
    public String closeQueue(@RequestParam String queueId) {
        queueService.closeQueue(queueId);
        return "redirect:/queue/view?queueId=" + queueId;
    }
}

