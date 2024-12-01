package kdu.cse.unispace.requestdto.chat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateMessageRequestDto {
    private Long memberId;  // 메시지를 보내는 회원의 ID
    private String content; // 메시지 내용

}
