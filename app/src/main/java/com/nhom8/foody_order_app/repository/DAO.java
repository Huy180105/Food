package com.nhom8.foody_order_app.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.nhom8.foody_order_app.model.Food;
import com.nhom8.foody_order_app.model.FoodSaved;
import com.nhom8.foody_order_app.model.FoodSize;
import com.nhom8.foody_order_app.model.Notify;
import com.nhom8.foody_order_app.model.NotifyToUser;
import com.nhom8.foody_order_app.model.Order;
import com.nhom8.foody_order_app.model.OrderDetail;
import com.nhom8.foody_order_app.model.Restaurant;
import com.nhom8.foody_order_app.model.RestaurantSaved;
import com.nhom8.foody_order_app.model.User;
import com.nhom8.foody_order_app.repository.food.FoodDAO;
import com.nhom8.foody_order_app.repository.food.FoodSaveDAO;
import com.nhom8.foody_order_app.repository.food.FoodSizeDAO;
import com.nhom8.foody_order_app.repository.notify.NotifyDAO;
import com.nhom8.foody_order_app.repository.notify.NotifyToUserDAO;
import com.nhom8.foody_order_app.repository.order.OrderDAO;
import com.nhom8.foody_order_app.repository.order.OrderDetailDAO;
import com.nhom8.foody_order_app.repository.restaurant.RestaurantDAO;
import com.nhom8.foody_order_app.repository.restaurant.RestaurantSavedDAO;
import com.nhom8.foody_order_app.repository.user.UserDAO;

import java.util.ArrayList;
import java.util.Calendar;

public class DAO {
    private final RestaurantDAO restaurantDAO;
    private final RestaurantSavedDAO restaurantSavedDAO;
    private final OrderDAO orderDAO;
    private final OrderDetailDAO orderDetailDAO;
    private final NotifyDAO notifyDAO;
    private final NotifyToUserDAO notifyToUserDAO;
    private final UserDAO userDAO;
    private final FoodDAO foodDAO;
    private final FoodSaveDAO foodSaveDAO;
    private final FoodSizeDAO foodSizeDAO;

    public DAO(Context context) {
        restaurantDAO = new RestaurantDAO(context);
        restaurantSavedDAO = new RestaurantSavedDAO(context);
        orderDAO = new OrderDAO(context);
        orderDetailDAO = new OrderDetailDAO(context);
        notifyDAO = new NotifyDAO(context);
        notifyToUserDAO = new NotifyToUserDAO(context);
        userDAO = new UserDAO(context);
        foodDAO = new FoodDAO(context);
        foodSaveDAO = new FoodSaveDAO(context);
        foodSizeDAO = new FoodSizeDAO(context);
    }

    public Restaurant getRestaurantInformation(Integer restaurantId) {
        return restaurantDAO.getRestaurantById(restaurantId);
    }

    public Restaurant getRestaurantByName(String restaurantName) {
        return restaurantDAO.getRestaurantByName(restaurantName);
    }

    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantDAO.getAllRestaurant();
    }

    public boolean addRestaurantSaved(RestaurantSaved restaurantSaved) {
        return restaurantSavedDAO.addRestaurantSaved(restaurantSaved);
    }

    public boolean deleteRestaurantSaved(RestaurantSaved restaurantSaved) {
        return restaurantSavedDAO.deleteRestaurantSaved(
                restaurantSaved.getRestaurantId(),
                restaurantSaved.getUserId()
        );
    }

    public ArrayList<RestaurantSaved> getRestaurantSavedList(Integer userId) {
        return restaurantSavedDAO.getRestaurantSavedList(userId);
    }

    public Integer quantityOfOrder() {
        return orderDAO.countDeliveredOrders();
    }

    public void addOrder(Order order) {
        orderDAO.addOrder(order);
    }

    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    public ArrayList<Order> getOrderOfUser(Integer userId, String status) {
        return orderDAO.getOrdersByUser(userId, status);
    }

    public OrderDetail getExistOrderDetail(Integer orderId, FoodSize foodSize) {
        return orderDetailDAO.getExistOrderDetail(orderId, foodSize);
    }

    public boolean addOrderDetail(OrderDetail od) {
        return orderDetailDAO.addOrderDetail(od);
    }

    public boolean deleteOrderDetailByOrderIdAndFoodId(Integer orderId, Integer foodId) {
        return orderDetailDAO.deleteOrderDetail(orderId, foodId);
    }

    public Cursor getCart(Integer userId) {
        Integer cartId = orderDetailDAO.getCartOrderId(userId);
        return new SingleIntCursor(cartId);
    }

    public ArrayList<OrderDetail> getCartDetailList(Integer orderId) {
        return orderDetailDAO.getCartDetailList(orderId);
    }

    public boolean updateQuantity(OrderDetail orderDetail) {
        return orderDetailDAO.updateQuantity(orderDetail);
    }

    public void addNotify(Notify n) {
        notifyDAO.addNotify(n);
    }

    public void addNotifyToUser(NotifyToUser notifyToUser) {
        notifyToUserDAO.addNotifyToUser(notifyToUser);
    }

    public Integer getNewestNotifyId() {
        return notifyDAO.getNewestNotifyId();
    }

    public ArrayList<Notify> getSystemNotify() {
        return notifyDAO.getSystemNotify();
    }

    public ArrayList<Notify> getUserNotify(Integer userId) {
        return notifyToUserDAO.getUserNotify(userId);
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public Integer getNewestUserId() {
        return userDAO.getNewestUserId();
    }

    public boolean UserExited(String username) {
        return userDAO.userExists(username);
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    public boolean checkUsername(String username) {
        return userDAO.checkUsername(username);
    }

    public boolean checkPasswordToCurrentUsername(String username, String password) {
        return userDAO.checkPasswordToCurrentUsername(username, password);
    }

    public boolean signIn(User user) {
        return userDAO.signIn(user);
    }

    public FoodSize getFoodDefaultSize(Integer foodId) {
        return foodSizeDAO.getDefaultSize(foodId);
    }

    public FoodSize getFoodSize(Integer foodId, Integer size) {
        return foodSizeDAO.getFoodSize(foodId, size);
    }

    public ArrayList<FoodSize> getAllFoodSize(Integer foodId) {
        return foodSizeDAO.getAllSize(foodId);
    }

    public Food getFoodById(Integer id) {
        return foodDAO.getFoodById(id);
    }

    public ArrayList<Food> getFoodByKeyWord(String keyword, String categoryId) {
        return foodDAO.searchFood(keyword, categoryId);
    }

    public ArrayList<Food> getAllFood() {
        return foodDAO.getAllFood();
    }

    public ArrayList<Food> getFoodByType(String type) {
        return foodDAO.getFoodByType(type);
    }

    public ArrayList<Food> getFoodByRestaurant(int restaurantId) {
        return foodDAO.getFoodByRestaurant(restaurantId);
    }

    public ArrayList<FoodSaved> getFoodSaveList(Integer userId) {
        return foodSaveDAO.getFoodSavedList(userId);
    }

    public boolean addFoodSaved(FoodSaved foodSaved) {
        return foodSaveDAO.addFoodSaved(foodSaved);
    }

    public void deleteFoodSavedByFoodIdAndSize(Integer foodId, Integer size) {
        foodSaveDAO.deleteFoodSaved(foodId, size);
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return day + "/" + month + "/" + year;
    }

    private static final class SingleIntCursor extends MatrixCursor {
        private SingleIntCursor(Integer value) {
            super(new String[]{"id"});
            if (value != null) {
                addRow(new Object[]{value});
                moveToFirst();
            }
        }
    }
}

