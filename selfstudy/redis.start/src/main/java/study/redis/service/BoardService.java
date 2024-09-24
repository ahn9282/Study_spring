package study.redis.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.redis.domain.Board;
import study.redis.repostory.BoardRepository;

import java.util.List;

@Service
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //해당 어노테이션 시 CAche REdis 전략으로 캐싱 적용
    //해당 메서드 호출 시 레디스를 확인 후 없으면 DB 접근하여 데이터를 가져온 후 레디스에 저장한다.
    @Cacheable(cacheNames="getBoards", key = "'boards:page:' + #page + ':size:' + #size", cacheManager = "boardCacheManager")
    public List<Board> getBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> pageOfBoards = boardRepository.findAllByOrderByCreatedAtDesc(pageable);
        return pageOfBoards.getContent();
    }
}
