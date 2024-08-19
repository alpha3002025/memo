package io.example.feign.mvc_feign_circuit_breaker_example.external.jsonplaceholder;

import io.example.feign.mvc_feign_circuit_breaker_example.external.JsonPlaceholderClient;
import io.example.feign.mvc_feign_circuit_breaker_example.external.jsonplaceholder.dto.CommentDto;
import io.example.feign.mvc_feign_circuit_breaker_example.external.jsonplaceholder.dto.PostDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonPlaceholderClientTest {
  @Autowired
  private JsonPlaceholderClient jsonPlaceholderClient;

  private Logger logger = LoggerFactory.getLogger(JsonPlaceholderClientTest.class);

  @Test
  public void test__jsonplaceholder__get_posts(){
    List<PostDto> posts = jsonPlaceholderClient.getPosts();
    posts.stream().forEach(postDto -> logger.info("post = {}", postDto));
  }

  @Test
  public void test__jsonplaceholder__get_posts_by_id(){
    PostDto post = jsonPlaceholderClient.getPostById(1L);
    logger.info("post = {}", post);
  }

  @Test
  public void test__jsonplaceholder__get_comments_by_post_id(){
    List<CommentDto> comments = jsonPlaceholderClient.getCommentsByPostId(1L);
    logger.info("comments = {}", comments);
  }

  @Test
  public void test__jsonplaceholder__get_comments_by_post_id_query(){
    List<CommentDto> comments = jsonPlaceholderClient.getCommentsByPostIdQuery(1L);
    logger.info("comments = {}", comments);
  }

  @Test
  public void test__jsonplaceholder__post_one(){
    PostDto postDto = PostDto.ofAll(1L, "hello", "hello world, it's great.");
    PostDto created = jsonPlaceholderClient.createPost(postDto);
    logger.info("created = {}", created);
  }

  @Test
  public void test__jsonplaceholder__update_post(){
    PostDto postDto = PostDto.ofAll(1L, "hello", "hello world, it's great.");
    PostDto updatePost = jsonPlaceholderClient.updatePost(1L, postDto);
    logger.info("updated = {}", updatePost);
  }

//  @Test
//  public void test__jsonplaceholder__patch_post(){
//    PostDto postDto = PostDto.ofAll(1L, "hello", "hello world, it's great.");
//    PostDto patchedPost = jsonPlaceholderClient.patchPost(1L, postDto);
//    logger.info("created = {}", patchedPost);
//  }

  @Test
  public void test__jsonplaceholder__delete_post(){
    PostDto deletedPost = jsonPlaceholderClient.deletePost(1L);
    logger.info("deleted = {}", deletedPost);
  }
}
