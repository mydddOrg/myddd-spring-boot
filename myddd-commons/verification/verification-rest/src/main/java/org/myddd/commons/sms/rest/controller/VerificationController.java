package org.myddd.commons.sms.rest.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.myddd.commons.sms.rest.VerificationVO;
import org.myddd.commons.verification.EmailVerificationCodeApplication;
import org.myddd.commons.verification.MobileVerificationCodeApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class VerificationController {


    @Inject
    private EmailVerificationCodeApplication emailVerificationCodeApplication;

    @Inject
    private MobileVerificationCodeApplication mobileVerificationCodeApplication;

    @PostMapping("/verification-code")
    public ResponseEntity<Void> sendVerification(@RequestBody VerificationVO verificationVO){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(verificationVO.getMobile()) || !Strings.isNullOrEmpty(verificationVO.getEmail()),"手机号和邮箱不能同时为空");
        if(!Strings.isNullOrEmpty(verificationVO.getMobile())){
            mobileVerificationCodeApplication.sendCode(verificationVO.getMobile());
        }else if(!Strings.isNullOrEmpty(verificationVO.getEmail())){
            emailVerificationCodeApplication.sendEmailCode(verificationVO.getEmail());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/verification-code/validation")
    public ResponseEntity<Void> validVerificationCode(@RequestBody VerificationVO verificationVO){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(verificationVO.getMobile()) || !Strings.isNullOrEmpty(verificationVO.getEmail()),"手机号和邮箱不能同时为空");
        if(!Strings.isNullOrEmpty(verificationVO.getMobile())){
            mobileVerificationCodeApplication.validCode(verificationVO.getMobile(), verificationVO.getCode());
        }else if(!Strings.isNullOrEmpty(verificationVO.getEmail())){
            emailVerificationCodeApplication.validEmailCode(verificationVO.getEmail(), verificationVO.getCode());
        }

        return ResponseEntity.ok().build();
    }
}
