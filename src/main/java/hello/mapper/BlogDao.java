package hello.mapper;

import hello.entity.Blog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogDao {
    public List<Blog> getBlogs(int page, int pageNum, Integer userId) {

        return null;
    }

    public int countBlog(){
        return 10;
    }
}
