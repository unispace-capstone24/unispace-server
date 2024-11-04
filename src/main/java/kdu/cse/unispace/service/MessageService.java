package kdu.cse.unispace.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.domain.chat.Message;
import kdu.cse.unispace.domain.chat.Room;
import kdu.cse.unispace.exception.chat.RoomNotFoundException;
import kdu.cse.unispace.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final RoomService roomService;
    private final MemberService memberService;
    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public Message makeMessage(Member member, Room room, String content) {
        //TEST DB에서만 쓰임(추후 삭제)
        Message message = new Message(member, room, content);
        messageRepository.save(message);
        return message;
    }

    @Transactional
    public void makeMessage(String message, String roomId, String memberId) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            String content = jsonNode.get("message").asText();

            Member member = memberService.findOne(Long.valueOf(memberId));
            Room room = roomService.findOne(Long.valueOf(roomId));

            Message entityMessage = new Message(member, room, content);
            messageRepository.save(entityMessage);

            log.trace("메시지 엔티티 저장 완료: " + jsonNode);

        } catch (Exception e) {
            log.error("JSON 파싱 오류: " + e.getMessage());
        }

    }


//    public Message findOne(Long messageId) {
//        return messageRepository.findById(messageId)
//                .orElseThrow(() -> new MessageNotFoundException("메세지를 찾을 수 없습니다."));
//    }
}
