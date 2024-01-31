package com.motormt.admin.checkout;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motormt.common.entity.Cart;

@Service
public class CheckoutService {

	public CheckoutInfo prepareCheckout(List<Cart> cartItems) {
		CheckoutInfo checkoutInfo = new CheckoutInfo();
		int serTotal = calculateSerTotal(cartItems);
		checkoutInfo.setTotal(serTotal);
		return checkoutInfo;
	}
	
	private int calculateSerTotal(List<Cart> cartItems) {
		int total = 0;
		for(Cart item : cartItems) {
			total += item.getSubTotal();
		}
		return total;
	}
}
