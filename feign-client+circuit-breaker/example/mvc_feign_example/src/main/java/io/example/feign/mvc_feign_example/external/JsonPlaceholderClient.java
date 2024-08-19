package io.example.feign.mvc_feign_example.external;


import feign.Param;
import feign.RequestLine;
import io.example.feign.mvc_feign_example.external.jsonplaceholder.dto.CommentDto;
import io.example.feign.mvc_feign_example.external.jsonplaceholder.dto.PostDto;
import java.util.List;

public interface JsonPlaceholderClient {
  @RequestLine("GET /posts")
  List<PostDto> getPosts();

  @RequestLine("GET /posts/{id}")
  PostDto getPostById(@Param("id") Long id);

  @RequestLine("GET /posts/{postId}/comments")
  List<CommentDto> getCommentsByPostId(@Param("postId") Long postId);

  @RequestLine("GET /comments/?postId={postId}")
  List<CommentDto> getCommentsByPostIdQuery(@Param("postId") Long postId);

  @RequestLine("POST /posts")
  PostDto createPost(PostDto post);

  @RequestLine("PUT /posts/{id}")
  PostDto updatePost(@Param("id") Long id, PostDto postDto);

  @RequestLine("PATCH /posts/{id}")
  PostDto patchPost(@Param("id") Long id, PostDto postDto);

  @RequestLine("DELETE /posts/1")
  PostDto deletePost(@Param("id") Long id);
}
