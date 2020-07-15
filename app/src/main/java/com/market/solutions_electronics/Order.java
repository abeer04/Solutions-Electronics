package com.market.solutions_electronics;

public class Order {
    String address;
    String customer_name;
    String order_time;
    String contact;
    String orderno;
    String totalamount;
    String payment_type;
    String  status;

    public void Order(String address,String payment_type, String customer_name, String order_time, String contact, String status,String orderno,String totalamount)
    {
        this.address=address;
        this.customer_name=customer_name;
        this.order_time=order_time;
        this.contact=contact;
        this.status=status;
        this.orderno=orderno;
        this.payment_type=payment_type;
        this.totalamount=totalamount;
    }
}
