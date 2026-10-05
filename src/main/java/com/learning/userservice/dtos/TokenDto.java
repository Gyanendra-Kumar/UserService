package com.learning.userservice.dtos;

import com.learning.userservice.models.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TokenDto {
    private String tokenValue;
    private Date expiryDate;
    private String email;

    public static TokenDto from(Token token){
        if(token == null) return null;

        TokenDto tokenDto = new TokenDto();

        tokenDto.setExpiryDate(token.getExpiryAt());
        tokenDto.setTokenValue(token.getTokenValue());
        tokenDto.setEmail(token.getUser().getEmail());

        return tokenDto;
    }
}
