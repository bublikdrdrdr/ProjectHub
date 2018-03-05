package app.service.implementation;

import app.exception.ForbiddenException;
import app.repository.dao.BlockRepository;
import app.repository.dao.ImageRepository;
import app.repository.dao.UserRepository;
import app.repository.dto.BlockDTO;
import app.repository.dto.BlockSearchRequestDTO;
import app.repository.dto.BlockSearchResponseDTO;
import app.repository.dto.UserDTO;
import app.repository.entity.User;
import app.repository.entity.UserBlock;
import app.repository.etc.BlockSearchParams;
import app.service.AuthenticationService;
import app.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static app.util.ServiceUtils.now;

@Service
public class BlockServiceImpl implements BlockService {

    private BlockRepository blockRepository;
    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    private ImageRepository imageRepository;

    @Autowired
    public BlockServiceImpl(BlockRepository blockRepository, AuthenticationService authenticationService, UserRepository userRepository, ImageRepository imageRepository) {
        this.blockRepository = blockRepository;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public long blockUser(BlockDTO block) {
        User admin = authenticationService.getAuthenticatedUser();
        User blockedUser = userRepository.get(block.user_id);
        UserBlock userBlock = new UserBlock(null, new Timestamp(block.start), new Timestamp(block.end), blockedUser, admin, block.comment, false, null);
        return blockRepository.save(userBlock);
    }

    @Override
    public void updateBlock(BlockDTO block) {
        User admin = authenticationService.getAuthenticatedUser();
        UserBlock userBlock = blockRepository.get(block.id);
        if (block.canceled==null && !Objects.equals(userBlock.getAdmin().getId(), admin.getId()))
            throw new ForbiddenException("User is not an admin of this block");
        if (block.start!=null) userBlock.setStart(new Timestamp(block.start));
        if (block.end!=null) userBlock.setEnd(new Timestamp(block.end));
        if (block.comment!=null) userBlock.setComment(block.comment);
        if (block.canceled!=null){
            userBlock.setCanceled(block.canceled);
            if (block.canceled) userBlock.setCanceledBy(admin);
        }
        blockRepository.save(userBlock);
    }

    @Override
    public BlockSearchResponseDTO search(BlockSearchRequestDTO request) {
        User admin = authenticationService.getAuthenticatedUser();
        EnumSet<UserRepository.Include> includes = EnumSet.of(UserRepository.Include.IMAGES);
        User searchAdmin = (request.admin==null)?null:userRepository.get(request.admin, includes);
        User searchUser = (request.user==null)?null:userRepository.get(request.user, includes);
        BlockSearchParams searchParams = request.getSearchParams(searchAdmin, searchUser);
        long count = blockRepository.count(searchParams);
        List<BlockDTO> resultList = blockRepository.search(searchParams).stream().map(block ->
                new BlockDTO(block.getId(), block.getStart().getTime(), block.getEnd().getTime(),
                        getUserDTO(block.getUser()),
                        getUserDTO(block.getAdmin()),
                        block.getCanceledBy()==null?null:getUserDTO(block.getCanceledBy()),
                        block.getComment(),
                        (!block.getCanceled() && block.getStart().before(now()) && block.getEnd().after(now()))))
                .collect(Collectors.toList());
        return new BlockSearchResponseDTO(count, resultList);
    }



    @Override
    public boolean isUserBlocked(long id) {
        User user = userRepository.get(id);
        return blockRepository.isBlocked(user);
    }

    private UserDTO getUserDTO(User user){
        return new UserDTO(user, imageRepository.getUserImage(user, false).getId(), blockRepository.isBlocked(user));
    }
}
