package com.example.rdk.dto.web.authorization;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
@Data
@Builder
public class AuthRequestDto{
    @NotEmpty(message = "поле логин должно быть заполнено")
    @Size(max = 100, message = "максимально допустимое число символов 100")
    private String login;
    @NotEmpty(message = "поле пароль должно быть заполнено")
    @Size(max = 100, message = "максимально допустимое число символов 100")
    private String password;
}
