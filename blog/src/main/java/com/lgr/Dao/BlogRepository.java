package com.lgr.dao;

import com.lgr.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/09 16:58
 * Description:
 * Version: V1.0
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

	@Query(" select b from t_blog b where b.recommend=true ")
	List<Blog> findTop(Pageable pageable);


	//select * from t_blog where title like ?1
	//select * from t_blog where title like '% ?1 %‘
	@Query("select b from t_blog b where b.title like ?1 or b.content like ?1")
	Page<Blog> findByQuery(String query,Pageable pageable);
}
