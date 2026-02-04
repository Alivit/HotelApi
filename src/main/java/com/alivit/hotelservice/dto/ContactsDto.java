package com.alivit.hotelservice.dto;

import com.alivit.hotelservice.validation.annotation.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactsDto(
        @Phone
        @NotBlank
        String phone,

        @Email
        String email
) {
}
