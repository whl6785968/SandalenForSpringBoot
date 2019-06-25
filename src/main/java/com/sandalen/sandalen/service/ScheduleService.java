package com.sandalen.sandalen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import java.io.File;
import java.io.IOException;

@Service
public class ScheduleService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Scheduled(cron = "* 0/1 * * * MON-SAT")
    public void hello() throws MessagingException {
        System.out.println("开始发送邮件");
       /* SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("我是你爸爸");
        simpleMailMessage.setText("儿子，爸爸来看你了");

        simpleMailMessage.setFrom("806403789@qq.com");
        simpleMailMessage.setTo("18245803818@163.com");

        javaMailSender.send(simpleMailMessage);*/
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("806403789@qq.com");
        mimeMessageHelper.setTo("18245803818@163.com");
        mimeMessageHelper.setSubject("爸爸来看你了");

        mimeMessageHelper.setText("<img src='./timg.jpg'><b>我的乖儿子呀</b>",true);
        mimeMessageHelper.setText("<html><head></head><body><h1>乖儿子~</h1>"+ "<img src=\"cid:image\"/></body></html>",true);
        FileSystemResource fileSystemResource = new FileSystemResource(new File("timg.jpg"));
        mimeMessageHelper.addInline("image",fileSystemResource);

//        MimeMultipart mimeMultipart = new MimeMultipart("related");
//        MimeBodyPart mimeBodyPart = new MimeBodyPart();
//        mimeMultipart.addBodyPart(mimeBodyPart);
//        mimeMessage.setContent(mimeMultipart);
//
//        FileDataSource fileDataSource = new FileDataSource(new File("time.jpg"));
//        DataHandler dataHandler = new DataHandler(fileDataSource);
//        mimeBodyPart.setDataHandler(dataHandler);
//        mimeBodyPart.setContentID("time.jpg");
//
//        MimeMultipart alternative = new MimeMultipart("alternative");
//        MimeBodyPart htmlBody = new MimeBodyPart();
//
//        htmlBody.setContent("<span style='color:red;'>这是带内嵌图片的HTML邮件哦！！！<img src=\"cid:time.png\" /></span>","text/html;charset=utf-8");
//        alternative.addBodyPart(htmlBody);
//        mimeBodyPart.setContent(alternative);

//        mimeMessage.saveChanges();

       javaMailSender.send(mimeMessage);
    }

}
