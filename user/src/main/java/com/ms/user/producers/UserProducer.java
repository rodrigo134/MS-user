package com.ms.user.producers;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;


    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email}")
    private String routingKey;

    public void publishMessageEmail(UserModel userModel) {
        System.out.println("⚡ Entrou em publishMessageEmail");
        var emailDto = new EmailDto();
        emailDto.setUserId(userModel.getUserId());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject("Cadastro realizado com sucesso!");
        emailDto.setText(userModel.getName() + ", seja bem vindo(a)! \nAgradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma!");
        System.out.println("🔹 Enviando para exchange='', routingKey=" + routingKey);

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
        System.out.println("Mensagem enviada!");


    }



}
