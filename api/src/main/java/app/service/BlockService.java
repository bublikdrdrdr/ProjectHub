package app.service;

import app.repository.dao.BlockRepository;
import app.repository.dto.BlockDTO;
import app.repository.dto.BlockSearchRequestDTO;
import app.repository.dto.BlockSearchResponseDTO;
import app.repository.entity.UserBlock;
import app.repository.etc.BlockSearchParams;

import java.util.List;

public interface BlockService {

    long blockUser(BlockDTO block);
    void updateBlock(BlockDTO block);
    BlockSearchResponseDTO search(BlockSearchRequestDTO request);
    boolean isUserBlocked(long id);

    /*
    if (checkNothingNull(new Object[]{block.start, block.end, block.user, block.admin, block.comment})) throw new NullPointerException("Required filed is null");
        User user = userRepository.get(block.user_id);
        User admin = userRepository.get(block.admin_id);//TODO:check admin
        Timestamp startTimestamp = new Timestamp(block.start);
        Timestamp endTimestamp = new Timestamp(block.end);
        if (startTimestamp.after(endTimestamp)) throw new SetValueException("End date is before start date");
        if (user==null || admin==null) throw new NullPointerException("Id is incorrect");
        UserBlock userBlock = new UserBlock(null, startTimestamp, endTimestamp, user, admin, block.comment, false, null);
        //TODO: save in blockRepository
     */
}
