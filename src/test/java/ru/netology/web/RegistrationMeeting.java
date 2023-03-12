package ru.netology.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;

  @Data
  @RequiredArgsConstructor
  public class RegistrationMeeting {
    private final String city;
    private final String name;
    private final String phone;
  }

