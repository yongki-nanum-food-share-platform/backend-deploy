package yongkinanum.backenddeploy.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserJPARepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeJPARepository noticeJPARepository;
    private final UserJPARepository userJPARepository;

    @Transactional
    public void writeNotice(NoticeRequest.WriteDTO writeDTO, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        Notice notice = Notice.builder()
                .content(writeDTO.getContent())
                .createAt(new Date())
                .user(findUser)
                .build();

        noticeJPARepository.save(notice);
    }

    public NoticeResponse.FindAllDTO findAllNotices() {
        List<Notice> notices = noticeJPARepository.findAll();

        return new NoticeResponse.FindAllDTO(notices);
    }
}
