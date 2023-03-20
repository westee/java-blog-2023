package hello.service;

import hello.entity.BlogListResult;
import hello.mapper.BlogDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

public class BlogServiceTest {
    @Mock
    BlogDao blogDao;

    @InjectMocks
    BlogService blogService;

    @Test
    void testGetBlogs() {
        blogService.getBlogs(1, 10, null);
        Mockito.verify(blogDao).getBlogs(1, 10, null);
    }

    @Test
    void throwExceptionWhenFail() {
        Mockito.when(blogService.getBlogs(1, 10, null)).thenThrow(RuntimeException.class);

        BlogListResult result = blogService.getBlogs(1, 10, null);

        Assertions.assertEquals("系统异常", result.getMsg());
        Assertions.assertEquals("fail", result.getStatus());
    }
}
