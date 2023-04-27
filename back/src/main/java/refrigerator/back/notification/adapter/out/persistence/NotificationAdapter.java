package refrigerator.back.notification.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;
import refrigerator.back.notification.adapter.mapper.NotificationMapper;
import refrigerator.back.notification.adapter.out.repository.NotificationRepository;
import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notifications;
import refrigerator.back.notification.application.port.out.ReadNotificationPort;
import refrigerator.back.notification.application.port.out.WriteNotificationPort;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NotificationAdapter implements ReadNotificationPort, WriteNotificationPort {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper mapper;

    // 알림 복수 조회
    @Override
    public List<NotificationResponseDTO> getNotificationList(String email, int page, int size) {
        return notificationRepository.findByEmailOrderByCreateTimeDesc(email, PageRequest.of(page, size))
                .stream()
                .map(notifications -> mapper.toNotificationResponseDTO(notifications))
                .collect(Collectors.toList());
    }

    // 멤버 알림 조회
    @Override
    public MemberNotification getMemberNotification(String email) {
        return notificationRepository.findMemberNotification(email);
    }

    // 알림 단건 조회
    @Override
    public Notifications getNotification(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    // 멤버 알림 저장 (수정, 생성)
    @Override
    public Long saveMemberNotification(MemberNotification memberNotification) {
        return notificationRepository.saveMemberNotification(memberNotification);
    }

    // 알림 저장 (수정, 생성)
    @Override
    public Long saveNotification(Notifications notifications) {
        notificationRepository.save(notifications);
        return notifications.getId();
    }
}
