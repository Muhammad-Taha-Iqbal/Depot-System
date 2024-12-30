package model;

import java.util.LinkedList;
import java.util.Queue;

public class QueueofCustomer {
    private Queue<Customer> customerQueue;

    public QueueofCustomer() {
        customerQueue = new LinkedList<>();
    }

    public void addCustomer(Customer customer) {
        customerQueue.add(customer);
    }

    public Customer removeCustomer() {
        return customerQueue.poll();
    }

    public Queue<Customer> getQueue() {
        return customerQueue;
    }
}
