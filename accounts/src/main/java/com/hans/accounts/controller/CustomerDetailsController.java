package com.hans.accounts.controller;

import com.hans.accounts.dto.CustomerDetailsDto;
import com.hans.accounts.dto.ErrorResponseDto;
import com.hans.accounts.service.ICustomerDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST APIs for Customer Details in HansBank",
        description = "REST APIs in HansBank to fetch Customer,Accounts,Loans,Cards Detail"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
//@AllArgsConstructor
@Validated
public class CustomerDetailsController {

    private final ICustomerDetailsService customerDetailsService;

    public CustomerDetailsController(ICustomerDetailsService customerDetailsService){
        this.customerDetailsService=customerDetailsService;
    }

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch Customer details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestParam
                                                                   @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                                   String mobileNumber){

        CustomerDetailsDto customerDetailsDto = customerDetailsService.fetchCustomerDetails(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }

}
