package com.alivit.hotelservice.dto;

import com.alivit.hotelservice.validation.annotation.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ContactsDto(
        @Phone
        @NotBlank
        String phone,

        @Email
        @NotBlank
        String email
) {
}
