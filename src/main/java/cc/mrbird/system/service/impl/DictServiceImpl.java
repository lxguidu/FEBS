package cc.mrbird.system.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.StringUtils;
import cc.mrbird.system.dao.DictMapper;
import cc.mrbird.system.domain.Dict;
import cc.mrbird.system.service.DictService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("dictService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictServiceImpl extends BaseService<Dict> implements DictService {

	@Autowired
	private DictMapper dictMapper;

	@Override
	public List<Dict> findAllDicts(Dict dict) {
		Example example = new Example(Dict.class);
		Criteria criteria = example.createCriteria();
		if (StringUtils.hasValue(dict.getKey())) {
			criteria.andCondition("key=", dict.getKey());
		}
		if (StringUtils.hasValue(dict.getValue())) {
			criteria.andCondition("value=", dict.getValue());
		}
		if (StringUtils.hasValue(dict.getTableName())) {
			criteria.andCondition("table_name=", dict.getTableName());
		}
		if (StringUtils.hasValue(dict.getFieldName())) {
			criteria.andCondition("field_name=", dict.getFieldName());
		}
		example.setOrderByClause("dict_id");
		return this.selectByExample(example);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDict(Dict dict) {
		dict.setDictId(this.getSequence(Dict.SEQ));
		this.dictMapper.insert(dict);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteDicts(String dictIds) {
		List<String> list = Arrays.asList(dictIds.split(","));
		this.batchDelete(list, "dictId", Dict.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateDict(Dict dict) {
		this.updateNotNull(dict);
	}

	@Override
	public Dict findById(Long dictId) {
		return this.selectByKey(dictId);
	}

}
