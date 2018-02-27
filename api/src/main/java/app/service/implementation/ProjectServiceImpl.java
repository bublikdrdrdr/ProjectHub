package app.service.implementation;

import app.exception.ForbiddenException;
import app.exception.SetValueException;
import app.repository.dao.*;
import app.repository.dto.*;
import app.repository.entity.*;
import app.repository.etc.CommentSearchParams;
import app.repository.etc.LikeSearchParams;
import app.repository.etc.ProjectSearchParams;
import app.service.AuthenticationService;
import app.service.ProjectService;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static app.util.ServiceUtils.now;


public class ProjectServiceImpl implements ProjectService {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private AuthenticationService authenticationService;
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;
    private AttachmentRepository attachmentRepository;



    @Override
    public ProjectDTO get(long id) {
        Project project = projectRepository.get(id, true);
        return new ProjectDTO(project.getId(),
                project.getCreated().getTime(),
                project.getPosted()==null?null:project.getPosted().getTime(),
                project.getAuthor().getId(),
                project.getSubject(),
                project.getContent(),
                project.getAttachments().stream().map(projectAttachment -> new ProjectAttachmentDTO(projectAttachment.getId(), project.getId(), projectAttachment.getTextValue(), projectAttachment.getAttachmentType().getId())).collect(Collectors.toList()),
                getProjectLikesCount(project, LikedProject.MarkType.LIKE),
                getProjectLikesCount(project, LikedProject.MarkType.DISLIKE),
                getProjectCommentsCount(project));
    }

    @Override
    public byte[] getProjectAttachment(long id) {
        return attachmentRepository.get(id).getBlobValue();
    }

    @Override
    public ProjectSearchResponseDTO getProjects(ProjectSearchRequestDTO request) {
        ProjectSearchParams searchParams = request.getSearchParams();
        return new ProjectSearchResponseDTO(projectRepository.count(searchParams),
                projectRepository.search(searchParams).stream().map(project -> new ProjectDTO(project.getId(), project.getCreated().getTime(),
                        project.getPosted()==null?null:project.getPosted().getTime(),
                        project.getAuthor().getId(), project.getSubject(), null, null,
                        getProjectLikesCount(project, LikedProject.MarkType.LIKE),
                        getProjectLikesCount(project, LikedProject.MarkType.DISLIKE),
                        getProjectCommentsCount(project))).collect(Collectors.toList()));
    }

    @Override
    public List<ProjectAttachmentTypeDTO> getAttachmentTypes() {
        return attachmentRepository.getAttachmentTypes().stream().
                map(attachmentType -> new ProjectAttachmentTypeDTO(attachmentType.getId(), attachmentType.getName().name())).
                collect(Collectors.toList());
    }

    @Override
    public long add(ProjectDTO dto) {
        User author = authenticationService.getAuthenticatedUser();
        Project project = new Project(null, now(), null, author, dto.subject, dto.content);
        return projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {
        Project project = getProjectIfOwner(dto.id);
        if (dto.content!=null) project.setContent(dto.content);
        if (dto.subject!=null) project.setSubject(dto.subject);
        projectRepository.save(project);
    }

    @Override
    public void post(long id) {
        Project project = getProjectIfOwner(id);
        if (project.getPosted()!=null) throw new SetValueException("Project is already posted");
        project.setPosted(now());
        projectRepository.save(project);
    }

    @Override
    public void remove(long id) {
        Project project = getProjectIfOwner(id);
        projectRepository.remove(project);
    }

    @Override
    public long addAttachment(long projectId, byte[] value, long attachmentTypeId) {
        Project project = getProjectIfOwner(projectId);
        AttachmentType type = attachmentRepository.getAttachmentType(attachmentTypeId);
        ProjectAttachment attachment = new ProjectAttachment(null, project, type, null, value);
        return attachmentRepository.save(attachment);
    }

    @Override
    public void removeAttachment(long id) {
        ProjectAttachment attachment = attachmentRepository.get(id, false, true);
        getProjectIfOwner(attachment.getProject().getId());
        attachmentRepository.remove(attachment);
    }

    //TODO: add values checking
    @Override
    public long comment(ProjectCommentDTO commentDTO) {
        User user = authenticationService.getAuthenticatedUser();
        Project project = projectRepository.get(commentDTO.project);
        ProjectComment comment = new ProjectComment(null, user, project, now(), commentDTO.message);
        return commentRepository.save(comment);
    }

    @Override
    public void removeComment(long id) {
        User user = authenticationService.getAuthenticatedUser();
        
    }

    @Override
    public ProjectCommentSearchResponseDTO getComments(ProjectCommentSearchRequestDTO request) {
        return null;
    }

    @Override
    public void like(long id) {

    }

    @Override
    public void dislike(long id) {

    }

    @Override
    public void unlike(long id) {

    }

    private long getProjectLikesCount(Project project, LikedProject.MarkType markType){
        return likeRepository.count(new LikeSearchParams(null, false, 0,1,project, null, markType));
    }

    private long getProjectCommentsCount(Project project){
        return commentRepository.count(new CommentSearchParams(false, 0,1,project, null));
    }

    private Project getProjectIfOwner(long projectId){
        Project project = projectRepository.get(projectId);
        if (project==null) throw new NullPointerException("Project not found");
        User author = authenticationService.getAuthenticatedUser();
        if (!Objects.equals(author.getId(), project.getAuthor().getId())) throw new ForbiddenException("User is not an owner of this project");
        return project;
    }
}
