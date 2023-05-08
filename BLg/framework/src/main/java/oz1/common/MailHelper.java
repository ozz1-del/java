/*
 * @Author: ykk
 * @Date: 2023-04-27 15:20:39
 * @LastEditTime: 2023-04-29 19:42:31
 * @LastEditors:  
 */
package oz1.common;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import oz1.domain.entity.Comment;
import oz1.service.BlogService;

@Component
public class MailHelper {

  private JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String from;

  public MailHelper(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  /**
   * 
   * @param toSend  收件人
   * @param subject 主题
   * @param text    内容
   */

  private void sendMail(String toSend, String subject, String text) {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    try {
      helper.setSentDate(new Date());
      helper.setFrom(from);
      helper.setTo(toSend);
      helper.setSubject(subject);
      helper.setText(text, true);
      javaMailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  @Autowired
  private BlogService blogService;

  @Async
  public void sendToMe(Comment comment) {
    String toSend = "1939077633@qq.com";
    String subject = "博客：<" + blogService.getBlogById(comment.getBlog()).getTitle() + "> 有新评论";
    String text = comment.getName() + "(" + comment.getEmail() + "):" + comment.getContent();
    sendMail(toSend, subject, text);
  }
}
