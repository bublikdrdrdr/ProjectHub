package app.repository.dao;

import app.repository.entity.LikedProject;
import app.repository.etc.LikeSearchParams;

public interface LikeRepository extends CrudRepository<LikedProject>, Searchable<LikedProject, LikeSearchParams> {

}
