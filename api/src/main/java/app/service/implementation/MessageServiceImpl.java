package app.service.implementation;

import app.exception.ForbiddenException;
import app.repository.dao.MessageRepository;
import app.repository.dao.ReportRepository;
import app.repository.dao.UserRepository;
import app.repository.dto.*;
import app.repository.entity.Message;
import app.repository.entity.Report;
import app.repository.entity.User;
import app.repository.etc.MessageSearchParams;
import app.repository.etc.ReportSearchParams;
import app.service.AuthenticationService;
import app.service.MessageService;
import app.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static app.util.ServiceUtils.now;

@Service
public class MessageServiceImpl implements MessageService {

    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private ReportRepository reportRepository;

    @Override
    public MessageDTO get(long id) {
        Message message = getMessageAndCheckOwner(id);
        User user = authenticationService.getAuthenticatedUser();
        long interlocutorId = message.getReceiver().getId();
        if (interlocutorId==user.getId()) interlocutorId = message.getSender().getId();
        return new MessageDTO(message, interlocutorId);
    }

    @Override
    public long send(MessageDTO messageDTO) {
        User sender = authenticationService.getAuthenticatedUser();
        User receiver = userRepository.get(messageDTO.interlocutor);
        return messageRepository.save(new Message(null, sender, receiver, false, false, messageDTO.message, now(), false, null));
    }

    @Override
    public void remove(long id) {
        Message message = getMessageAndCheckOwner(id);
        User user = authenticationService.getAuthenticatedUser();
        if (message.getReceiver().getId().equals(user.getId())) message.setDeletedByReceiver(true);
        if (message.getSender().getId().equals(user.getId())) message.setDeletedBySender(true);
        messageRepository.save(message);
    }

    @Override
    public void restore(long id) {
        Message message = getMessageAndCheckOwner(id);
        User user = authenticationService.getAuthenticatedUser();
        if (message.getSender().getId().equals(user.getId())) message.setDeletedBySender(false);
        if (message.getReceiver().getId().equals(user.getId())) message.setDeletedByReceiver(false);
        messageRepository.save(message);
    }

    @Override
    public byte[] getAttachmentFromMessage(long id) {
        Message message = getMessageAndCheckOwner(id);
        return message.getImage();
    }

    @Override
    public MessageSearchResponseDTO get(MessageSearchRequestDTO request) {
        return search(request, false);
    }

    @Override
    public MessageSearchResponseDTO getDialogs(MessageSearchRequestDTO request) {
        return search(request, true);
    }

    private MessageSearchResponseDTO search(MessageSearchRequestDTO request, boolean dialogs){
        User owner = authenticationService.getAuthenticatedUser();
        User interlocutor = dialogs?null:userRepository.get(request.interlocutor);
        MessageSearchParams searchParams = new MessageSearchParams(request.desc, request.first, request.count, owner, interlocutor, false, request.unreadOnly, request.message);
        long count = messageRepository.count(searchParams);
        List<MessageDTO> list = messageRepository.search(searchParams).stream().map(message -> new MessageDTO(message, dialogs?message.getInterlocutor(owner).getId():interlocutor.getId())).collect(Collectors.toList());
        return new MessageSearchResponseDTO(count, list);
    }

    @Override
    public long report(ReportDTO reportDTO) {
        User user = authenticationService.getAuthenticatedUser();
        Report report = new Report(null, reportDTO.getReportTypeValue(), user, reportDTO.url, reportDTO.message, now(), null, false);
        return reportRepository.save(report);
    }

    @Override
    public ReportSearchResponseDTO search(ReportSearchRequestDTO request) {
        User admin = request.admin==null?null:userRepository.get(request.admin);
        User sender = request.sender==null?null:userRepository.get(request.sender);
        Timestamp before = request.before==null?null:new Timestamp(request.before);
        Timestamp after = request.after==null?null:new Timestamp(request.after);
        ReportSearchParams searchParams = new ReportSearchParams(request.getSortValue(), request.desc, request.first,
                request.count, admin, sender, request.getReportType(), request.message, before, after, request.active_only);
        return new ReportSearchResponseDTO(reportRepository.count(searchParams), reportRepository.search(searchParams)
                .stream().map(report -> new ReportDTO(report.getId(), report.getType().ordinal(), report.getSender().getId(),
                        report.getMessage(), report.getUrl(), report.getSent().getTime(), report.getAdmin().getId(),
                        report.isClosed())).collect(Collectors.toList()));
    }

    @Override
    public void saveReport(ReportDTO reportDTO) {
        User admin = authenticationService.getAuthenticatedUser();
        Report report = reportRepository.get(reportDTO.id);
        if (report.getAdmin()==null) report.setAdmin(admin);
        if (!report.getAdmin().getId().equals(admin.getId())) throw new ForbiddenException("User is not an admin of this report");
        if (reportDTO.closed!=null) report.setClosed(reportDTO.closed);
        if (reportDTO.message!=null) report.setMessage(reportDTO.message);
        reportRepository.save(report);
    }

    private boolean isOwner(Message message, User user){
        return user.getId().equals(message.getReceiver().getId())||user.getId().equals(message.getSender().getId());
    }

    private Message getMessageAndCheckOwner(long id){
        Message message = messageRepository.get(id);
        if (!isOwner(message, authenticationService.getAuthenticatedUser())) throw new ForbiddenException("User is not an owner of this message");
        return message;
    }
}
