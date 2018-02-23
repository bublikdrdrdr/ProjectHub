package app.service;

import app.repository.dto.*;
import app.repository.entity.Message;
import app.repository.entity.Report;
import app.repository.etc.ReportSearchParams;
import app.repository.etc.SearchParams;

import java.util.List;

public interface MessageService {

    MessageDTO get(long id);
    long send(MessageDTO message);
    void remove(long id);
    void restore(long id);
    byte[] getAttachmentFromMessage(long id);
    MessageSearchResponseDTO get(MessageSearchRequestDTO request);
    MessageSearchResponseDTO getDialogs(MessageSearchRequestDTO request);
    long report(ReportDTO report);
    ReportSearchResponseDTO search(ReportSearchRequestDTO request);
    void saveReport(ReportDTO reportDTO);
}
