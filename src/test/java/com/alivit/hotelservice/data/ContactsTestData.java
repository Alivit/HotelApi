package com.alivit.hotelservice.data;

import com.alivit.hotelservice.dto.ContactsDto;
import com.alivit.hotelservice.model.Contacts;

public final class ContactsTestData {

    private final static Long ID = 1L;
    private final static String PHONE = "+375 17 309-80-00";
    private final static String EMAIL = "doubletreeminsk.info@hilton.com";

    private ContactsTestData() {}

    public static ContactsDto getContactsDto() {
        return ContactsDto.builder()
                .phone(PHONE)
                .email(EMAIL)
                .build();
    }

    public static Contacts getContacts() {
        return Contacts.builder()
                .id(ID)
                .phone(PHONE)
                .email(EMAIL)
                .build();
    }
}
