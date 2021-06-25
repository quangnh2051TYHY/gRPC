package org.example.grpc.protobuf;

import org.example.entity.Credential;
import org.example.entity.EmailCredential;
import org.example.entity.PhoneOTP;

public class InterfaceDemo {
  public static void main(String[] args) {
    EmailCredential emailCredential = EmailCredential.newBuilder()
        .setEmail("admin@admin.com")
        .setPassword("123")
        .build();

    PhoneOTP phoneOTP = PhoneOTP.newBuilder()
        .setCode(123)
        .setNumber(342569884)
        .build();

    Credential credential = Credential.newBuilder()
        .setPhoneMode(phoneOTP)
        .setEmailMode(emailCredential)
        .build();

    Credential credentialPhone = Credential.newBuilder()
        .setPhoneMode(phoneOTP)
        .build();

    login(credential);
    login(credentialPhone);
  }

  private static void login(Credential credential) {

    switch (credential.getModeCase()) {
      case EMAILMODE: {
        System.out.println(credential.getEmailMode());
        break;
      }
      case PHONEMODE: {
        System.out.println(credential.getPhoneMode());
        break;
      }
      case MODE_NOT_SET: {
        System.out.println("Nothing {}");
      }
    }
  }
}
