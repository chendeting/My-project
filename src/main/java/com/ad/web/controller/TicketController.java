package com.ad.web.controller;

import com.ad.dao.AdminUserDao;
import com.ad.dao.OrderDao;
import com.ad.dao.TrainDao;
import com.ad.dao.TripsDao;
import com.ad.entity.AdminUser;
import com.ad.entity.Order;
import com.ad.entity.Train;
import com.ad.entity.Trips;
import com.ad.entity.filed.OrderTicketResult;
import com.ad.entity.filed.SeatType;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/train")
public class TicketController {
    private static final String MODE = "yyyy-MM-dd";
    @Autowired
    private TripsDao tripsDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private TrainDao trainDao;
    @Autowired
    private AdminUserDao adminUserDao;


    //我们通过依赖式注入获取到所有Repository的列表。
    // 当用户访问list，系统将根据传进来的type遍历所有Repository，找到对应的Repository，再调用findOne(id)方法找到对应的对象。
    // 这样我们就不需要一个一个的去获取Repository实例，当领域对象越来越多时，通过这种方式是一种更加高效的对象管理方法
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String ticket() {
        return "train_list";
    }

    @RequestMapping(value = "query-ticket", method = RequestMethod.POST)
    public String queryTicket(String departure, String begin, String destination, Model model) {
        DateTime dateTime = DateTime.parse(begin, DateTimeFormat.forPattern(MODE));
        Date beginDate = dateTime.withTime(0, 0, 0, 0).toDate();
        Date endDate = dateTime.withTime(23, 59, 59, 999).toDate();
        List<Trips> trips = tripsDao.findAllByDepartureAndDestinationAndDepartureDateBetween(departure, destination, beginDate, endDate);
        if (trips == null) {
            trips = new ArrayList<Trips>();
        }
        model.addAttribute("trips", trips);
        return "_ticket_form";
    }

    @RequestMapping(value = "order", method = RequestMethod.POST)
    @ResponseBody
    public OrderTicketResult orderTicket(Order order, Principal principal) {
        OrderTicketResult result = new OrderTicketResult();
        AdminUser user = adminUserDao.findByUserName(principal.getName());
        try {
            Trips trips = tripsDao.findOne(order.getTrainId());
            order.setUserId(user.getId());
            order.setDeparture(trips.getDeparture());
            order.setDestination(trips.getDestination());
            order.setDepartureDate(trips.getDepartureDate());
            order.setTrainNo(trips.getTrainNo());
            if (order.getSeatType().equals(SeatType.hard_seat)) {
                Integer number = trips.getTrain().getHardSeat();
                if (number == 0) {
                    throw new RuntimeException("硬座车票已售完，请选择其他车票");
                }
                trips.getTrain().setHardSeat(number - 1);
            }
            if (order.getSeatType().equals(SeatType.hard_sleeper)) {
                Integer number = trips.getTrain().getHardSleeper();
                if (number == 0) {
                    throw new RuntimeException("硬卧车票已售完，请选择其他车票");
                }
                trips.getTrain().setHardSleeper(number - 1);
            }
            if (order.getSeatType().equals(SeatType.soft_seat)) {
                Integer number = trips.getTrain().getSoftSeat();
                if (number == 0) {
                    throw new RuntimeException("软座车票已售完，请选择其他车票");
                }
                trips.getTrain().setSoftSeat(number - 1);
            }
            if (order.getSeatType().equals(SeatType.soft_sleeper)) {
                Integer number = trips.getTrain().getSoftSleeper();
                if (number == 0) {
                    throw new RuntimeException("软卧车票已售完，请选择其他车票");
                }
                trips.getTrain().setSoftSleeper(number - 1);
            }
            if (order.getSeatType().equals(SeatType.no_seat)) {
                Integer number = trips.getTrain().getNoSeat();
                if (number == 0) {
                    throw new RuntimeException("无座车票已售完，请选择其他车票");
                }
                trips.getTrain().setNoSeat(number - 1);
            }
            tripsDao.save(trips);
            orderDao.save(order);
            result.setStatus("success");
        } catch (Exception e) {
            result.setStatus("failed");
            result.setReason(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "order", method = RequestMethod.GET)
    public String order(Model model, Principal principal) {
        AdminUser user = adminUserDao.findByUserName(principal.getName());
        model.addAttribute("orders", orderDao.findAllByUserId(user.getId()));
        return "order";
    }

    @RequestMapping(value = "cancel-booking", method = RequestMethod.POST)
    @ResponseBody
    public String cancelBooking(Integer id) {
        try {
            Order order = orderDao.findOne(id);
            if (order != null) {
                Train train = trainDao.findOne(order.getTrainId());
                if (order.getSeatType().equals(SeatType.hard_seat)) {
                    train.setHardSeat(train.getHardSeat() + 1);
                }
                if (order.getSeatType().equals(SeatType.hard_sleeper)) {
                    train.setHardSleeper(train.getHardSleeper() + 1);
                }
                if (order.getSeatType().equals(SeatType.soft_seat)) {
                    train.setSoftSeat(train.getSoftSeat() + 1);
                }
                if (order.getSeatType().equals(SeatType.soft_sleeper)) {
                    train.setSoftSleeper(train.getSoftSleeper() + 1);
                }
                if (order.getSeatType().equals(SeatType.no_seat)) {
                    train.setNoSeat(train.getNoSeat() + 1);
                }
                trainDao.save(train);
                orderDao.delete(id);
            }
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }
}
