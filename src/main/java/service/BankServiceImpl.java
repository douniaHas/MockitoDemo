package service;

public class BankServiceImpl implements BankService {
    public double pay(String id, double amount){
        System.out.println("id = " + id + " receives this amount " + amount);
        return amount;
    }
}
