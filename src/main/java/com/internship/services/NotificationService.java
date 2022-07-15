package com.internship.services;

import com.internship.models.Notification;
import com.internship.models.Status;
import com.internship.models.repo.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Paged<Notification> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by("id").descending());
        Page<Notification> notificationPage = notificationRepository.findAll(request);

        return new Paged<>(notificationPage, Paging.of(notificationPage.getTotalPages(), pageNumber, size));
    }

    public Paged<Notification> getStatusPage(Status status, int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by("id").descending());

        Page<Notification> notificationPage = notificationRepository.findByStatus(status, request);

        return new Paged<>(notificationPage, Paging.of(notificationPage.getTotalPages(), pageNumber, size));
    }

    public Paged<Notification> getProductNamePage(String productName, int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by("id").descending());

        Page<Notification> notificationPage = notificationRepository.findDistinctByProductNameContaining(productName, request);

        return new Paged<>(notificationPage, Paging.of(notificationPage.getTotalPages(), pageNumber, size));
    }

    public Paged<Notification> getDateNamePage(String startDate, String endDate, int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by("id").descending());


        Page<Notification> notificationPage = notificationRepository.findByDateOfReceiptBetween(startDate,endDate,request);

        return new Paged<>(notificationPage, Paging.of(notificationPage.getTotalPages(), pageNumber, size));
    }
}

