package com.example.rdk.dto.web.company;

import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

@Data
public class UpdateCompanyRequestDto {
    private int id;
    @NotEmpty(message = "поле с именем должно быть заполнено")
    @Size(max = 100, message = "максимально допустимое число символов 100")
    private String name;
    @NotEmpty(message = "поле с инн должно быть заполнено")
    @Size(max = 100, message = "максимально допустимое число символов 100")
    private String inn;
    @Size(max = 10000, message = "максимально допустимое число символов 10000")
    private String note;
    @NotEmpty(message = "поле логин должно быть заполнено")
    @Size(max = 100, message = "максимально допустимое число символов 100")
    private String login;
    @NotEmpty(message = "поле пароль должно быть заполнено")
    @Size(max = 100, message = "максимально допустимое число символов 100")
    private String password;

    private int isActive;
}
