package com.hans.accounts.service;

import com.hans.accounts.dto.CustomerDetailsDto;
import com.hans.accounts.dto.CustomerDto;

public interface ICustomerDetailsService {

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
