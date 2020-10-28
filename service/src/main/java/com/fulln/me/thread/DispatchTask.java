package com.fulln.me.thread;



import com.fulln.me.service.search.SearchService;

import static me.fulln.base.common.threadconfig.ThreadQueue.setTask;

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
        return  GlobalEnums.QUERY_SUCCESS.results(searchService.findAll(text));
    }


}
