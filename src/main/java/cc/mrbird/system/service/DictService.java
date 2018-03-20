package cc.mrbird.system.service;

import java.util.List;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.Dict;

public interface DictService extends IService<Dict> {

	List<Dict> findAllDicts(Dict dict);

	Dict findById(Long dictId);

	void addDict(Dict dict);

	void deleteDicts(String dictIds);

	void updateDict(Dict dicdt);
}
