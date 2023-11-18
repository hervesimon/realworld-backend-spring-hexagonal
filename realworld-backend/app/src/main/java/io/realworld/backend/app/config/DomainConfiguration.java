package io.realworld.backend.app.config;

import io.realworld.backend.domain.repository.ArticleFavouriteRepository;
import io.realworld.backend.domain.repository.ArticleRepository;
import io.realworld.backend.domain.repository.CommentRepository;
import io.realworld.backend.domain.repository.FollowRelationRepository;
import io.realworld.backend.domain.repository.UserRepository;
import io.realworld.backend.domain.service.ArticleService;
import io.realworld.backend.domain.service.ProfilService;
import io.realworld.backend.domain.service.UserService;
import io.realworld.backend.domain.service.impl.ArticleImplService;
import io.realworld.backend.domain.service.impl.ProfilServiceImpl;
import io.realworld.backend.domain.service.impl.UserImplService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

  @Bean
  public ArticleService articleService(
      final ArticleFavouriteRepository articleFavouriteRepository,
      final ArticleRepository articleRepository,
      final CommentRepository commentRepository) {

    return new ArticleImplService(articleFavouriteRepository, articleRepository, commentRepository);
  }

    @Bean
    public ProfilService profilService(
            final FollowRelationRepository followRelationRepository,
            final UserRepository userRepository) {
        return new ProfilServiceImpl(followRelationRepository, userRepository);
    }

    @Bean
    public UserService userService(
            final UserRepository userRepository) {
    return new UserImplService(userRepository);
    }


}
