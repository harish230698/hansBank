package com.hans.accounts.service.impl;

import com.hans.accounts.dto.AccountsDto;
import com.hans.accounts.dto.CardsDto;
import com.hans.accounts.dto.CustomerDetailsDto;
import com.hans.accounts.dto.LoansDto;
import com.hans.accounts.entity.Accounts;
import com.hans.accounts.entity.Customer;
import com.hans.accounts.exception.ResourceNotFoundException;
import com.hans.accounts.mapper.AccountsMapper;
import com.hans.accounts.mapper.CustomerMapper;
import com.hans.accounts.repository.AccountsRepository;
import com.hans.accounts.repository.CustomerRepository;
import com.hans.accounts.service.ICustomerDetailsService;
import com.hans.accounts.service.client.CardsFeignClient;
import com.hans.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerDetailsServiceImpl implements ICustomerDetailsService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","MobileNumber",mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts","CustomerId",customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));

        ResponseEntity<CardsDto> cardsDetails = cardsFeignClient.fetchCardDetails(mobileNumber);
        ResponseEntity<LoansDto> loansDetails = loansFeignClient.fetchLoanDetails(mobileNumber);

        customerDetailsDto.setCardsDto(cardsDetails.getBody());
        customerDetailsDto.setLoansDto(loansDetails.getBody());

        return customerDetailsDto;
    }
}
