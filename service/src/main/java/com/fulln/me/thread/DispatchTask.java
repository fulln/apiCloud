package com.fulln.me.thread;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.threadconfig.AbstractThreadStartFactory;
import com.fulln.me.config.enums.GlobalEnums;
import com.fulln.me.service.search.SearchService;

import static com.fulln.me.api.common.threadconfig.ThreadQueue.setTask;

public class DispatchTask extends AbstractThreadStartFactory {


    private SearchService searchService;
    private String text;

    public DispatchTask(SearchService searchService, String text) {
        this.searchService = searchService;
        this.text = text;
        setTask(this);
    }

    @Override
    public GlobalResult call() {
        return GlobalEnums.QUERY_SUCCESS.results(searchService.findAll(text));
    }


}
