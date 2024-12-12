package com.LMS.LibraryManagementSystem.DTO;

import com.LMS.LibraryManagementSystem.model.Users;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidityResponse {

    public boolean isValid = false;
    public Users users ;
}
