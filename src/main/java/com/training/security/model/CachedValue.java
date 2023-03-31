package com.training.security.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CachedValue {

    int attempts;
    LocalDateTime blockedTimeStamp;

}
