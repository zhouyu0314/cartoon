package com.cartoon.controller;

import com.cartoon.serivce.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accountRecord")
public class AccountRecordController {
    @Autowired
    private AccountRecordService accountRecordService;


}
