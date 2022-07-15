package com.internship.controllers;

import com.internship.models.repo.CheckpointRepository;
import com.internship.models.repo.NotificationRepository;
import com.internship.models.repo.ProductRepository;
import com.internship.models.repo.UnitRepository;
import com.internship.services.NotificationService;
import com.internship.models.Notification;
import com.internship.models.Product;
import com.internship.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CheckpointRepository checkpointRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UnitRepository unitRepository;

    private final int countOfNotification = 3;
    private String statusMessage="";

    @GetMapping("/notification")
    public String notificationMain(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber, Model model) {
        model.addAttribute("nots", notificationService.getPage(pageNumber, countOfNotification));
        model.addAttribute("statusOptions", Status.values());
        model.addAttribute("title", "Список уведомлений");
        model.addAttribute("statusMessage", statusMessage);
        statusMessage="";

        return "notificationPage";
    }

    @GetMapping("/notification/add")
    public String notificationAddGet(Model model){

        List<Product> products = new ArrayList<>();
        for (Product p : productRepository.findAll()){
            if (!p.isBusy()) products.add(p);
        }
        model.addAttribute("products", products);
        model.addAttribute("statusOptions", Status.values());
        model.addAttribute("checkpointOptions", checkpointRepository.findAll());
        model.addAttribute("notification", new Notification());
        model.addAttribute("title", "Добавление уведомления");

        return "notificationAdd";
    }

    @PostMapping("/notification/add")
    public String notificationAddPost(Notification notification, @RequestParam(value="checkBoxList[]", required = false) Set<Product> myParams, Model model){

        if (notification.getId()==null){
            notification.setProduct(myParams);
            for (Product p : notification.getProduct()){
                p.setBusy(true);
            }
            notificationRepository.save(notification);
        }
        else
        {
            for (Product p : notificationRepository.findById(notification.getId()).get().getProduct()){
                p.setBusy(false);
            }
            Notification notificationUpdate = notification;
            notificationUpdate.setProduct(myParams);
            for (Product p : notificationUpdate.getProduct()){
                p.setBusy(true);
            }
            notificationRepository.save(notificationUpdate);
        }
        return "redirect:/notification";
    }

    @GetMapping("/notification/{id}/edit")
    public String notificationEdit(@PathVariable(value = "id") long id, Model model){
        try {
            Notification notification = notificationRepository.findById(id).orElseThrow(() -> new notificationException("Несуществующее уведомление нельзя редактировать", id));

            if (notification.getStatus() != Status.CREATED) throw new notificationException("Данное уведомление нельзя редактировать (не CREATED)", id);

            List<Product> products = new ArrayList<>();
            for (Product p : productRepository.findAll()){
                if (!p.isBusy()) products.add(p);
            }
            model.addAttribute("products", products);
            model.addAttribute("checkpointOptions", checkpointRepository.findAll());
            model.addAttribute("statusOptions", Status.values());
            model.addAttribute("notification",notification);
            model.addAttribute("title", "Редактирование уведомления");
            return "notificationAdd";
        }
        catch (notificationException ex){

            statusMessage = ex.getMsg();
            return "redirect:/notification";
        }
    }

    @GetMapping("/notification/{id}")
    public String notificationDetails(@PathVariable(value = "id") long id, HttpServletRequest request, Model model){
        try {
            Notification notification = notificationRepository.findById(id).orElseThrow(() -> new notificationException("Такого уведомления нет", id));
            model.addAttribute("backLink", request.getHeader("Referer"));
            model.addAttribute("title", notification.getSender());
            model.addAttribute("notification",notification);
            return "notificationDetails";
        }
        catch (notificationException ex) {
            statusMessage = ex.getMsg();
            return "redirect:/notification";
        }
    }

    @PostMapping("/notification/{id}/remove")
    public String notificationDelete(@PathVariable(value = "id") long id, Model model) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.setCheckpoint(null);
        notificationRepository.delete(notification);
        return "redirect:/notification";
    }

    @PostMapping("/notification/statusSearch")
    public String notificationStatusSearch(@RequestParam(value = "status") Status status, @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber, Model model){

        model.addAttribute("nots", notificationService.getStatusPage(status, pageNumber, countOfNotification));
        model.addAttribute("statusOptions", Status.values());
        model.addAttribute("linkForPaging", "statusSearch");

        return "notificationPage";
    }

    @PostMapping("/notification/productSearch")
    public String notificationProductSearch(@RequestParam(value = "productName") String productName, @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber, Model model){

        model.addAttribute("nots", notificationService.getProductNamePage(productName, pageNumber, countOfNotification));
        model.addAttribute("statusOptions", Status.values());
        model.addAttribute("linkForPaging", "productSearch");

        return "notificationPage";
    }

    @PostMapping("/notification/dateSearch")
    public String notificationDateSearch(@RequestParam(value = "startDate") String startDate,
                                         @RequestParam(value = "endDate") String endDate,
                                         @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber, Model model){
        if (startDate == "" || endDate == ""){
            statusMessage="Укажите обе даты";
            return "redirect:/notification";
        }

        model.addAttribute("nots", notificationService.getDateNamePage(startDate, endDate, pageNumber, countOfNotification));
        model.addAttribute("statusOptions", Status.values());
        model.addAttribute("linkForPaging", "dateSearch");

        return "notificationPage";
    }

    @GetMapping("/notification/addProduct")
    public String notificationAddProductGet(Model model){

        model.addAttribute("unitOptions", unitRepository.findAll());
        model.addAttribute("title", "Создание продукта");

        return "notificationAddProduct";
    }

    @PostMapping("/notification/addProduct")
    public String notificationAddProductPost(Product product, Model model){

        productRepository.save(product);

        return "redirect:/notification";
    }

}
