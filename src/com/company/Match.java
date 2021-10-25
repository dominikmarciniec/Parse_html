package com.company;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Match {
    int guestPoints;
    int hostPoints;
    Date LocalDateTime;
    Team guest;
    Team host;
}
