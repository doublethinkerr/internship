package com.internship.models.repo;

import com.internship.models.Notification;
import com.internship.models.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByStatus(Status status, Pageable pageable);

    Page<Notification> findDistinctByProductNameContaining(String productName, Pageable pageable);

    Page<Notification> findByDateOfReceiptBetween(String startDate, String endDate, Pageable pageable);

}
