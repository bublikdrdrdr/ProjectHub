package app.repository.dao;

import app.repository.entity.User;
import app.repository.entity.UserBlock;
import app.repository.etc.BlockSearchParams;
import app.repository.etc.SearchParams;

import java.util.List;

public interface BlockRepository extends CrudRepository<UserBlock>, Searchable<UserBlock, BlockSearchParams> {

}