package kdu.cse.unispace.responsedto.chat;

import kdu.cse.unispace.domain.chat.Message;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponseDto {
    private String senderName;
    private Long senderId;
    private LocalDateTime sendTime;
    private String content;

    public MessageResponseDto(Message message) {
        this.senderName = message.getSenderName();
        this.senderId = message.getSenderId();
        this.sendTime = message.getSendTime();
        this.content = message.getContent();

    }
}
