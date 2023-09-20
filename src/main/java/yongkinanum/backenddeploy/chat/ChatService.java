package yongkinanum.backenddeploy.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.chat.member.Member;
import yongkinanum.backenddeploy.chat.member.MemberJPARepository;
import yongkinanum.backenddeploy.chat.message.Message;
import yongkinanum.backenddeploy.chat.message.MessageJPARepository;
import yongkinanum.backenddeploy.core.error.exception.Exception409;
import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserJPARepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {
    private final UserJPARepository userJPARepository;
    private final ChatJPARepository chatJPARepository;
    private final MemberJPARepository memberJPARepository;
    private final MessageJPARepository messageJPARepository;

    @Transactional
    public ChatResponse.SendDTO receiveAndSendChat(Long idx, ChatRequest.ReceiveDTO receiveDTO) {
        Chat findChat = chatJPARepository.findByChatIdx(idx);
        User findUser = userJPARepository.findByUserName(receiveDTO.getUserName());

        Message message = Message.builder()
                .content(receiveDTO.getContent())
                .chat(findChat)
                .user(findUser)
                .createAt(new Date())
                .build();

        messageJPARepository.save(message);

        return new ChatResponse.SendDTO(message);
    }

    @Transactional
    public void enterChat(ChatRequest.EnterDTO enterDTO, User user) {
        Chat findChat = chatJPARepository.findChatByPostIdx(enterDTO.getIdx());
        User findUser = userJPARepository.findByUserId(user.getUserId());

        List<Member> members = memberJPARepository.findMemberByChatIdx(findChat.getIdx());

        for(Member member : members) {
            if(member.getUser().getIdx().equals(findUser.getIdx())) {
                throw new Exception409("이미 참여중인 채팅방입니다.");
            }
        }

        Member member = Member.builder()
                .chat(findChat)
                .user(findUser)
                .build();

        memberJPARepository.save(member);
    }

    public ChatResponse.FindAllDTO findAllChats(User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        List<Member> members = memberJPARepository.findMemberByUserIdx(findUser.getIdx());

        List<Chat> chats = members.stream()
                .map(member -> chatJPARepository.findByChatIdx(member.getChat().getIdx()))
                .collect(Collectors.toList());

        Map<Long, String> lastMsgs = new HashMap<>();

        for(Chat chat : chats) {
            List<Message> messages = messageJPARepository.findMessageByChatIdx(chat.getIdx());

            if(!messages.isEmpty()) {
                lastMsgs.put(chat.getIdx(), messages.get(messages.size() - 1).getContent());
            }
        }

        return new ChatResponse.FindAllDTO(chats, lastMsgs);
    }

    public ChatResponse.FindDTO findChat(Long idx) {
        List<Message> messages = messageJPARepository.findMessageByChatIdx(idx);

        return new ChatResponse.FindDTO(messages);
    }

    @Transactional
    public void deleteChat(Long idx) {
        Chat findChat = chatJPARepository.findByChatIdx(idx);

        List<Member> members = memberJPARepository.findMemberByChatIdx(findChat.getIdx());
        memberJPARepository.deleteAll(members);

        List<Message> messages = messageJPARepository.findMessageByChatIdx(findChat.getIdx());
        messageJPARepository.deleteAll(messages);

        chatJPARepository.delete(findChat);
    }
}
