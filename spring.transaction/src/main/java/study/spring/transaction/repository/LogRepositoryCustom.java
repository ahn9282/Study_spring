package study.spring.transaction.repository;

import study.spring.transaction.domain.Log;

public interface LogRepositoryCustom {

    Log saveLogTx(Log log);
}
